package com.nebula.system.service;

import com.mybatisflex.core.service.IService;
import com.nebula.system.domain.SysRoleMenu;
import java.util.List;

/**
 * 角色和菜单关联表 服务层。
 *
 * @author William
 * @since 1.0
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    List<Long> getMenuListByRoleKey(String roleKey);

    void saveRoleMenu(Long roleId, Long[] menuIds);
}