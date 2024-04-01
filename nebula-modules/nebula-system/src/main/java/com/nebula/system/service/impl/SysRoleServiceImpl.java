package com.nebula.system.service.impl;

import static com.nebula.system.domain.table.SysRoleTableDef.SYS_ROLE;
import static com.nebula.system.domain.table.SysUserRoleTableDef.SYS_USER_ROLE;
import static com.nebula.system.domain.table.SysUserTableDef.SYS_USER;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.nebula.system.domain.SysRole;
import com.nebula.system.mapper.SysRoleMapper;
import com.nebula.system.service.ISysRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 角色信息表 服务层实现。
 *
 * @author William
 * @since 1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private SysRoleMapper roleMapper;

    @Override
    public boolean checkRoleKey(SysRole role) {
        QueryCondition whereCondition = SYS_ROLE.ROLE_ID.ne(role.getRoleId())
            .and(SYS_ROLE.ROLE_KEY.eq(role.getRoleKey()));
        return roleMapper.selectCountByCondition(whereCondition) > 0;
    }

    @Override
    public Long selectRoleListByUserId(Long userId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
            .select(SYS_ROLE.ROLE_ID)
            .from(SYS_ROLE)
            .as("r")
            .leftJoin(SYS_USER_ROLE)
            .as("ur")
            .on(SYS_USER_ROLE.ROLE_ID.eq(SYS_ROLE.ROLE_ID))
            .leftJoin(SYS_USER)
            .as("u")
            .on(SYS_USER_ROLE.USER_ID.eq(SYS_USER.USER_ID))
            .where(SYS_USER.USER_ID.eq(userId));
        return roleMapper.selectObjectByQueryAs(queryWrapper, Long.class);
    }
}