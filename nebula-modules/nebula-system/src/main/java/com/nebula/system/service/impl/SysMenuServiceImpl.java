package com.nebula.system.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysMenu;
import com.nebula.system.domain.vo.SysMenuVO;
import com.nebula.system.mapper.SysMenuMapper;
import com.nebula.system.service.ISysMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nebula.system.domain.table.SysMenuTableDef.SYS_MENU;
import static com.nebula.system.domain.table.SysRoleMenuTableDef.SYS_ROLE_MENU;

/**
 * 菜单权限表 服务层实现。
 *
 * @author William
 * @since 1.0
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

	@Resource
	private SysMenuMapper sysMenuMapper;

	@Override
	public Page<SysMenuVO> selectMenuPageList(String searchKey, PageQuery page) {
		QueryWrapper queryWrapper = QueryWrapper.create()
			.select(SYS_MENU.ALL_COLUMNS)
			.from(SYS_MENU)
			.where(SYS_MENU.MENU_NAME.like(searchKey))
			.or(SYS_MENU.PATH.like(searchKey));
		return sysMenuMapper.paginateAs(page.getPageNum(), page.getPageSize(), queryWrapper, SysMenuVO.class);
	}

	@Override
	public List<SysMenuVO> selectMenuListByRoleId(Long roleId) {
		QueryWrapper queryWrapper = QueryWrapper.create()
			.select(SYS_MENU.ALL_COLUMNS)
			.from(SYS_MENU.as("m"))
			.innerJoin(SYS_ROLE_MENU.as("rm"))
			.on(SYS_MENU.MENU_ID.eq(SYS_ROLE_MENU.MENU_ID))
			.where(SYS_ROLE_MENU.ROLE_ID.eq(roleId))
			.orderBy(SYS_MENU.ORDER_NUM.asc());
		return sysMenuMapper.selectListByQueryAs(queryWrapper, SysMenuVO.class);
	}

	@Override
	public List<SysMenuVO> buildMenuTree(List<SysMenuVO> menus) {
		Map<Long, List<SysMenuVO>> menuMap = menus.stream().collect(Collectors.groupingBy(SysMenuVO::getParentId));
		menus.forEach(menu -> menu.setChildren(menuMap.get(menu.getMenuId())));
		return menus.stream()
			.filter(menu -> menu.getParentId() == null || menu.getParentId() == 0)
			.collect(Collectors.toList());
	}

}