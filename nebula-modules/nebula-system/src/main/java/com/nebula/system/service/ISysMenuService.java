package com.nebula.system.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysMenu;
import com.nebula.system.domain.vo.SysMenuVO;

import java.util.List;

/**
 * 菜单权限表 服务层。
 *
 * @author William
 * @since 1.0
 */
public interface ISysMenuService extends IService<SysMenu> {

	Page<SysMenuVO> selectMenuPageList(String searchKey, PageQuery page);

	List<SysMenuVO> selectMenuListByRoleId(Long roleId);

	List<SysMenuVO> buildMenuTree(List<SysMenuVO> menus);
}