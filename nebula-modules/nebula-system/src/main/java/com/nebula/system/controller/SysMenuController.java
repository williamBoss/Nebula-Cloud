package com.nebula.system.controller;

import com.mybatisflex.core.paginate.Page;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysMenu;
import com.nebula.system.domain.vo.SysMenuVO;
import com.nebula.system.service.ISysMenuService;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表 控制层。
 *
 * @author William
 * @since 1.0
 */
@Tag(name = "菜单", description = "菜单")
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {

	private final ISysMenuService sysMenuService;

	private final Converter converter;

	public SysMenuController(ISysMenuService sysMenuService, Converter converter) {
		this.sysMenuService = sysMenuService;
		this.converter = converter;
	}

	/**
	 * 分页查询菜单
	 *
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@Parameter(name = "searchKey", description = "关键字", in = ParameterIn.QUERY, required = true)
	@Operation(summary = "分页查询菜单", description = "分页查询菜单")
	@GetMapping("/list")
	public Page<SysMenuVO> page(@RequestParam("searchKey") String searchKey, PageQuery page) {
		return sysMenuService.selectMenuPageList(searchKey, page);
	}

	/**
	 * 查询所有菜单
	 *
	 * @return 所有数据
	 */
	@Operation(summary = "查询所有菜单", description = "查询所有菜单")
	@GetMapping("/all/list")
	public List<SysMenuVO> list() {
		List<SysMenuVO> menus = converter.convert(sysMenuService.list(), SysMenuVO.class);
		if (CollectionUtils.isEmpty(menus)) {
			return new ArrayList<>();
		}
		return sysMenuService.buildMenuTree(menus);
	}

	/**
	 * 根据菜单主键获取详细信息。
	 *
	 * @param id sysMenu主键
	 * @return 菜单详情
	 */
	@Parameter(name = "id", description = "sysMenu主键", in = ParameterIn.PATH, required = true)
	@Operation(summary = "根据菜单主键获取详细信息。", description = "根据菜单主键获取详细信息。")
	@GetMapping("/{id}")
	public SysMenuVO getInfo(@PathVariable Integer id) {
		return converter.convert(sysMenuService.getById(id), SysMenuVO.class);
	}

	/**
	 * 添加 菜单
	 *
	 * @param sysMenu 菜单
	 * @return {@code true} 添加成功，{@code false} 添加失败
	 */
	@Operation(summary = "添加 菜单", description = "添加 菜单")
	@PostMapping
	public boolean save(@RequestBody SysMenuVO sysMenu) {
		return sysMenuService.save(converter.convert(sysMenu, SysMenu.class));
	}

	/**
	 * 根据主键更新菜单
	 *
	 * @param sysMenu 菜单
	 * @return {@code true} 更新成功，{@code false} 更新失败
	 */
	@Operation(summary = "根据主键更新菜单", description = "根据主键更新菜单")
	@PutMapping
	public boolean update(@RequestBody SysMenuVO sysMenu) {
		return sysMenuService.updateById(converter.convert(sysMenu, SysMenu.class));
	}

	/**
	 * 根据主键删除菜单
	 *
	 * @param id 主键
	 * @return {@code true} 删除成功，{@code false} 删除失败
	 */
	@Parameter(name = "id", description = "主键", in = ParameterIn.PATH, required = true)
	@Operation(summary = "根据主键删除菜单", description = "根据主键删除菜单")
	@DeleteMapping("/{id}")
	public boolean remove(@PathVariable Integer id) {
		return sysMenuService.removeById(id);
	}

}