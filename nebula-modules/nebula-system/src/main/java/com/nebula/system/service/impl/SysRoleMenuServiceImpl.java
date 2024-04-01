package com.nebula.system.service.impl;

import static com.nebula.system.domain.table.SysRoleMenuTableDef.SYS_ROLE_MENU;
import static com.nebula.system.domain.table.SysRoleTableDef.SYS_ROLE;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.nebula.system.domain.SysRoleMenu;
import com.nebula.system.mapper.SysRoleMenuMapper;
import com.nebula.system.service.ISysRoleMenuService;
import jakarta.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色和菜单关联表 服务层实现。
 *
 * @author William
 * @since 1.0
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<Long> getMenuListByRoleKey(String roleKey) {
        QueryWrapper queryWrapper = QueryWrapper.create()
            .select(SYS_ROLE_MENU.MENU_ID)
            .from(SYS_ROLE_MENU)
            .as("rm")
            .innerJoin(SYS_ROLE)
            .as("r")
            .on(SYS_ROLE_MENU.ROLE_ID.eq(SYS_ROLE.ROLE_ID))
            .where(SYS_ROLE.ROLE_KEY.eq(roleKey));
        return sysRoleMenuMapper.selectObjectListByQueryAs(queryWrapper, Long.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleMenu(Long roleId, Long[] menuIds) {
        sysRoleMenuMapper.deleteByCondition(SYS_ROLE_MENU.ROLE_ID.eq(roleId));
        if (ArrayUtils.isNotEmpty(menuIds)) {
            List<SysRoleMenu> roleMenus = Arrays.stream(menuIds)
                .map(menuId -> new SysRoleMenu(roleId, menuId))
                .collect(Collectors.toList());
            Db.executeBatch(roleMenus, 1000, SysRoleMenuMapper.class, BaseMapper::insertWithPk);
        }
    }

}