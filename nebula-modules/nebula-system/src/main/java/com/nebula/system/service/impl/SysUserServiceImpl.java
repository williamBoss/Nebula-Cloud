package com.nebula.system.service.impl;

import static com.mybatisflex.core.query.QueryMethods.noCondition;
import static com.nebula.system.domain.table.SysDeptTableDef.SYS_DEPT;
import static com.nebula.system.domain.table.SysRoleTableDef.SYS_ROLE;
import static com.nebula.system.domain.table.SysUserRoleTableDef.SYS_USER_ROLE;
import static com.nebula.system.domain.table.SysUserTableDef.SYS_USER;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.nebula.common.core.utils.security.BCryptUtil;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysUser;
import com.nebula.system.domain.SysUserRole;
import com.nebula.system.domain.vo.SysUserVO;
import com.nebula.system.mapper.SysUserMapper;
import com.nebula.system.mapper.SysUserRoleMapper;
import com.nebula.system.service.ISysUserService;
import io.github.linpeilie.Converter;
import jakarta.annotation.Resource;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 用户信息表 服务层实现。
 *
 * @author William
 * @since 1.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    private final Converter converter;

    public SysUserServiceImpl(Converter converter) {
        this.converter = converter;
    }

    @Override
    public Page<SysUserVO> selectUserPageList(SysUserVO user, PageQuery pageQuery) {
        return sysUserMapper.paginateAs(pageQuery.getPageNum(), pageQuery.getPageSize(),
            buildQueryWrapper(user, user.getParams()), SysUserVO.class);
    }

    /**
     * 构建查询条件。
     *
     * @param user   用户信息。
     * @param params 参数。
     * @return 查询条件。
     */
    private QueryWrapper buildQueryWrapper(SysUserVO user, Map<String, Object> params) {
        return QueryWrapper.create()
            .select(SYS_USER.USER_ID, SYS_USER.NICK_NAME, SYS_USER.USER_NAME, SYS_USER.EMAIL, SYS_USER.AVATAR,
                SYS_USER.PHONENUMBER, SYS_USER.SEX, SYS_USER.STATUS, SYS_USER.LOGIN_IP, SYS_USER.LOGIN_DATE,
                SYS_USER.DEPT_ID, SYS_DEPT.DEPT_NAME)
            .from(SYS_USER.as("u"))
            .leftJoin(SYS_DEPT)
            .as("d")
            .on(SYS_DEPT.DEPT_ID.eq(SYS_USER.DEPT_ID))
            .and(SYS_DEPT.DEPT_ID.eq(user.getDeptId()))
            .where(SYS_USER.USER_ID.eq(user.getUserId()))
            .and(SYS_USER.USER_NAME.like(user.getUserName()))
            .and(SYS_USER.STATUS.eq(user.getStatus()))
            .and(SYS_USER.PHONENUMBER.like(user.getPhonenumber()))
            .and(CollectionUtils.isEmpty(params) ? noCondition()
                : SYS_USER.CREATE_TIME.between(params.get("beginTime"), params.get("endTime")))
            .orderBy(SYS_USER.USER_ID, true);
    }

    @Override
    public SysUserVO selectUserById(Long userId) {
        QueryWrapper queryWrapper = getQueryWrapper(SYS_USER.USER_ID.eq(userId));
        return sysUserMapper.selectOneByQueryAs(queryWrapper, SysUserVO.class);
    }

    @Override
    public SysUserVO selectUserByUserName(String username) {
        QueryWrapper queryWrapper = getQueryWrapper(SYS_USER.USER_NAME.eq(username));
        return sysUserMapper.selectOneByQueryAs(queryWrapper, SysUserVO.class);
    }

    private static QueryWrapper getQueryWrapper(QueryCondition queryCondition) {
        return QueryWrapper.create()
            .select(SYS_USER.USER_ID, SYS_USER.NICK_NAME, SYS_USER.USER_NAME, SYS_USER.PASSWORD, SYS_USER.EMAIL,
                SYS_USER.AVATAR, SYS_USER.PHONENUMBER, SYS_USER.SEX, SYS_USER.STATUS, SYS_USER.LOGIN_IP,
                SYS_USER.LOGIN_DATE, SYS_USER.DEPT_ID, SYS_DEPT.DEPT_NAME, SYS_DEPT.PARENT_ID, SYS_DEPT.ANCESTORS,
                SYS_ROLE.ROLE_ID, SYS_ROLE.ROLE_NAME, SYS_ROLE.ROLE_KEY, SYS_ROLE.DATA_SCOPE)
            .from(SYS_USER)
            .as("u")
            .leftJoin(SYS_DEPT)
            .as("d")
            .on(SYS_DEPT.DEPT_ID.eq(SYS_USER.DEPT_ID))
            .leftJoin(SYS_USER_ROLE)
            .as("sur")
            .on(SYS_USER_ROLE.USER_ID.eq(SYS_USER.USER_ID))
            .leftJoin(SYS_ROLE)
            .as("r")
            .on(SYS_ROLE.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
            .where(queryCondition);
    }

    @Override
    public boolean checkUserNameUnique(SysUserVO user) {
        return this.exists(SYS_USER.USER_ID.ne(user.getUserId()).and(SYS_USER.USER_NAME.eq(user.getUserName())));
    }

    @Override
    public boolean checkPhoneUnique(SysUserVO user) {
        return this.exists(SYS_USER.USER_ID.ne(user.getUserId()).and(SYS_USER.PHONENUMBER.eq(user.getPhonenumber())));
    }

    @Override
    public boolean checkEmailUnique(SysUserVO user) {
        return this.exists(SYS_USER.USER_ID.ne(user.getUserId()).and(SYS_USER.EMAIL.eq(user.getEmail())));
    }

    @Override
    public boolean checkDeptExistUser(Long deptId) {
        return this.exists(SYS_USER.DEPT_ID.eq(deptId));
    }

    @Override
    @Transactional
    public void save(SysUserVO user) {
        SysUser sysUser = converter.convert(user, SysUser.class);
        sysUser.setPassword(BCryptUtil.encryptPassword(user.getPassword()));
        sysUserMapper.insert(sysUser);
        SysUserRole sysUserRole = new SysUserRole().setUserId(sysUser.getUserId()).setRoleId(user.getRoleId());
        sysUserRoleMapper.insertWithPk(sysUserRole);
    }

    @Override
    public void update(SysUserVO user) {
        SysUser sysUser = converter.convert(user, SysUser.class);
        sysUserMapper.update(sysUser);
        sysUserRoleMapper.deleteByCondition(
            SYS_USER_ROLE.USER_ID.eq(user.getUserId()).and(SYS_USER_ROLE.ROLE_ID.eq(user.getRoleId())));
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(user.getUserId());
        sysUserRole.setRoleId(user.getRoleId());
        sysUserRoleMapper.insertWithPk(sysUserRole);
    }

    @Override
    public void resetUserPwd(SysUserVO user) {
        SysUser sysUser = UpdateEntity.of(SysUser.class, user.getUserId());
        sysUser.setPassword(BCryptUtil.encryptPassword(user.getPassword()));
        sysUserMapper.update(sysUser);
    }

    @Override
    public void updateStatus(SysUserVO user) {
        SysUser sysUser = UpdateEntity.of(SysUser.class, user.getUserId());
        sysUser.setStatus(user.getStatus());
        sysUserMapper.update(sysUser);
    }
}