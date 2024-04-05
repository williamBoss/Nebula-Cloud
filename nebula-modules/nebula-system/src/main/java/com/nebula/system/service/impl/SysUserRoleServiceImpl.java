package com.nebula.system.service.impl;

import static com.nebula.system.domain.table.SysDeptTableDef.SYS_DEPT;
import static com.nebula.system.domain.table.SysRoleTableDef.SYS_ROLE;
import static com.nebula.system.domain.table.SysUserRoleTableDef.SYS_USER_ROLE;
import static com.nebula.system.domain.table.SysUserTableDef.SYS_USER;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysUserRole;
import com.nebula.system.domain.vo.SysUserVO;
import com.nebula.system.mapper.SysUserRoleMapper;
import com.nebula.system.service.ISysUserRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表 服务层实现。
 *
 * @author William
 * @since 1.0
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public boolean checkUserExist(Long id) {
        QueryCondition whereCondition = SYS_USER_ROLE.ROLE_ID.eq(id);
        return sysUserRoleMapper.selectCountByCondition(whereCondition) > 0;
    }

    @Override
    public Page<SysUserVO> getUserListByRoleKey(String roleKey, String searchKey, PageQuery pageQuery) {
        QueryWrapper queryWrapper = QueryWrapper.create()
            .select(SYS_USER.USER_ID, SYS_USER.NICK_NAME, SYS_USER.USER_NAME, SYS_USER.PHONENUMBER, SYS_USER.SEX,
                SYS_DEPT.DEPT_NAME)
            .from(SYS_USER_ROLE.as("ur"))
            .innerJoin(SYS_ROLE.as("r"))
            .on(SYS_ROLE.ROLE_ID.eq(SYS_USER_ROLE.ROLE_ID))
            .innerJoin(SYS_USER.as("u"))
            .on(SYS_USER_ROLE.USER_ID.eq(SYS_USER.USER_ID))
            .leftJoin(SYS_DEPT.as("d"))
            .on(SYS_USER.DEPT_ID.eq(SYS_DEPT.DEPT_ID))
            .where(SYS_ROLE.ROLE_KEY.eq(roleKey))
            .and(SYS_USER.USER_NAME.like(searchKey)
                .or(SYS_USER.NICK_NAME.like(searchKey))
                .or(SYS_USER.PHONENUMBER.like(searchKey)))
            .orderBy(SYS_USER_ROLE.USER_ID.asc());
        return sysUserRoleMapper.paginateAs(pageQuery.getPageNumber(), pageQuery.getPageSize(), queryWrapper,
            SysUserVO.class);
    }
}