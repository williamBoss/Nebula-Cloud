package com.nebula.system.controller;

import static com.mybatisflex.core.query.QueryMethods.notExists;
import static com.mybatisflex.core.query.QueryMethods.select;
import static com.nebula.system.domain.table.SysUserRoleTableDef.SYS_USER_ROLE;
import static com.nebula.system.domain.table.SysUserTableDef.SYS_USER;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.nebula.common.constants.UserConstants;
import com.nebula.common.core.exception.UserException;
import com.nebula.common.log.annotation.OperLog;
import com.nebula.common.log.enums.BusinessType;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysRole;
import com.nebula.system.domain.SysUser;
import com.nebula.system.domain.vo.SysUserVO;
import com.nebula.system.service.ISysRoleService;
import com.nebula.system.service.ISysUserService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息表 控制层。
 *
 * @author William
 * @since 1.0
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    private final ISysUserService sysUserService;

    private final ISysRoleService roleService;

    public SysUserController(ISysUserService sysUserService, ISysRoleService roleService) {
        this.sysUserService = sysUserService;
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public Page<SysUserVO> list(SysUserVO user, PageQuery pageQuery) {
        return sysUserService.selectUserPageList(user, pageQuery);
    }

    @GetMapping(value = {"/list/nickName/", "/list/nickName/{nickName}", "/list/userId/{userId}"})
    public List<SysUserVO> listByParam(@PathVariable(value = "nickName", required = false) String nickName,
        @PathVariable(value = "userId", required = false) String userId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
            .select(SYS_USER.USER_ID, SYS_USER.NICK_NAME)
            .from(SYS_USER)
            .where(SYS_USER.USER_ID.eq(userId))
            .and(SYS_USER.NICK_NAME.like(nickName))
            .and(SYS_USER.STATUS.eq(UserConstants.NORMAL));
        return sysUserService.listAs(queryWrapper, SysUserVO.class);
    }

    @GetMapping(value = {"/list/role/nickName/", "/list/role/nickName/{nickName}"})
    public List<SysUserVO> listByNickNameAndRoleKey(@PathVariable(required = false) String nickName) {
        QueryWrapper queryWrapper = QueryWrapper.create()
            .select(SYS_USER.USER_ID, SYS_USER.NICK_NAME, SYS_USER.USER_NAME, SYS_USER.PHONENUMBER, SYS_USER.SEX)
            .from(SYS_USER)
            .as("u")
            .where(SYS_USER.NICK_NAME.like(nickName))
            .and(SYS_USER.STATUS.eq(UserConstants.NORMAL))
            .and(notExists(select(SYS_USER_ROLE.USER_ID).from(SYS_USER_ROLE.as("ur")).where("ur.user_id = u.user_id")));
        return sysUserService.listAs(queryWrapper, SysUserVO.class);
    }

    /**
     * 根据用户编号获取详细信息
     *
     * @param userId 用户ID
     */
    @GetMapping(value = {"/", "/{userId}"})
    public Map<String, Object> getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        Map<String, Object> data = new HashMap<>();
        List<SysRole> roles = roleService.list();
        data.put("roles",
            SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        if (ObjectUtils.isNotEmpty(userId)) {
            data.put("data", sysUserService.selectUserById(userId));
            data.put("roleId", roleService.selectRoleListByUserId(userId));
        }
        return data;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/info/{username}")
    public SysUserVO getInfo(@PathVariable String username) {
        SysUserVO sysUser = sysUserService.selectUserByUserName(username);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new UserException("用户名或密码错误");
        }
        // todo: 权限集合
        return sysUser;
    }

    /**
     * 新增用户
     */
    @SaCheckPermission(value = "sys:user:save", orRole = "admin")
    @OperLog(title = "用户管理-新增用户", businessType = BusinessType.INSERT, excludeParamNames = {"password"})
    @PostMapping
    public void add(@RequestBody SysUserVO user) {
        checkUserInfo(user);
        sysUserService.save(user);
    }

    /**
     * 修改用户
     */
    @PutMapping
    public void edit(@RequestBody SysUserVO user) {
        checkUserInfo(user);
        sysUserService.update(user);
    }

    /**
     * 校验用户信息
     *
     * @param user
     */
    private void checkUserInfo(SysUserVO user) {
        if (sysUserService.checkUserNameUnique(user)) {
            throw new UserException("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && sysUserService.checkPhoneUnique(user)) {
            throw new UserException("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && sysUserService.checkEmailUnique(user)) {
            throw new UserException("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
    }

    /**
     * 删除用户
     *
     * @param userIds 角色ID串
     */
    @DeleteMapping("/{userIds}")
    public void remove(@PathVariable Long[] userIds) {
        List<Long> ids = Arrays.asList(userIds);
        if (ids.contains(UserConstants.SUPER_ADMIN_ID)) {
            throw new UserException("不允许删除超级管理员用户");
        }
        sysUserService.removeByIds(ids);
    }

    /**
     * 重置密码
     */
    @PutMapping("/resetPwd")
    public void resetPwd(@RequestBody SysUserVO user) {
        sysUserService.resetUserPwd(user);
    }

    /**
     * 状态修改
     */
    @OperLog(title = "用户管理-修改状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public void changeStatus(@RequestBody SysUserVO user) {
        sysUserService.updateStatus(user);
    }

}