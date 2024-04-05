package com.nebula.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.nebula.common.log.annotation.OperLog;
import com.nebula.common.log.enums.BusinessType;
import com.nebula.datasource.page.PageQuery;
import com.nebula.system.domain.SysConfig;
import com.nebula.system.domain.vo.SysConfigVO;
import com.nebula.system.service.ISysConfigService;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 参数配置 控制层。
 *
 * @author William
 * @since 1.0
 */
@Tag(name = "参数配置", description = "参数配置")
@RestController
@RequestMapping("/sysConfig")
public class SysConfigController {

	private final ISysConfigService sysConfigService;

	private final Converter converter;

	public SysConfigController(ISysConfigService sysConfigService, Converter converter) {
		this.sysConfigService = sysConfigService;
		this.converter = converter;
	}

	/**
	 * 分页查询参数配置
	 *
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@Parameter(name = "searchKey", description = "", in = ParameterIn.QUERY, required = true)
	@Operation(summary = "分页查询参数配置", description = "分页查询参数配置")
	@SaCheckPermission(value = "sys:config:list")
	@GetMapping("/list")
	public Page<SysConfigVO> list(@RequestParam(required = false) String searchKey, PageQuery page) {
		return sysConfigService.selectConfigList(searchKey, page);
	}

	/**
	 * 查询所有参数配置
	 *
	 * @return 所有数据
	 */
	@Operation(summary = "查询所有参数配置", description = "查询所有参数配置")
	@GetMapping("/all/list")
	public List<SysConfigVO> allList() {
		return sysConfigService.listAs(new QueryWrapper(), SysConfigVO.class);
	}

	/**
	 * 根据参数配置主键获取详细信息。
	 *
	 * @param id sysConfig主键
	 * @return 参数配置详情
	 */
	@Parameter(name = "id", description = "sysConfig主键", in = ParameterIn.PATH, required = true)
	@Operation(summary = "根据参数配置主键获取详细信息。", description = "根据参数配置主键获取详细信息。")
	@GetMapping("/{id}")
	public SysConfigVO getInfo(@PathVariable Integer id) {
		return converter.convert(sysConfigService.getById(id), SysConfigVO.class);
	}

	/**
	 * 添加 参数配置
	 *
	 * @param sysConfig 参数配置
	 * @return {@code true} 添加成功，{@code false} 添加失败
	 */
	@Operation(summary = "添加 参数配置", description = "添加 参数配置")
	@SaCheckPermission(value = "sys:config:save")
	@OperLog(title = "配置管理", businessType = BusinessType.INSERT)
	@PostMapping
	public boolean save(@RequestBody SysConfigVO sysConfig) {
		return sysConfigService.save(converter.convert(sysConfig, SysConfig.class));
	}

	/**
	 * 根据主键更新参数配置
	 *
	 * @param sysConfig 参数配置
	 * @return {@code true} 更新成功，{@code false} 更新失败
	 */
	@Operation(summary = "根据主键更新参数配置", description = "根据主键更新参数配置")
	@SaCheckPermission(value = "sys:config:update")
	@OperLog(title = "配置管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public boolean update(@RequestBody SysConfigVO sysConfig) {
		return sysConfigService.updateById(converter.convert(sysConfig, SysConfig.class));
	}

	/**
	 * 根据主键删除参数配置
	 *
	 * @param id 主键
	 * @return {@code true} 删除成功，{@code false} 删除失败
	 */
	@Parameter(name = "id", description = "主键", in = ParameterIn.PATH, required = true)
	@Operation(summary = "根据主键删除参数配置", description = "根据主键删除参数配置")
	@SaCheckPermission(value = "sys:config:remove")
	@OperLog(title = "配置管理", businessType = BusinessType.DELETE)
	@DeleteMapping("{id}")
	public boolean remove(@PathVariable Integer id) {
		return sysConfigService.removeById(id);
	}

	/**
	 * 根据参数键名查询参数配置
	 *
	 * @param configKey 参数键名
	 * @return 参数配置
	 */
	@Parameter(name = "configKey", description = "参数键名", in = ParameterIn.PATH, required = true)
	@Operation(summary = "根据参数键名查询参数配置", description = "根据参数键名查询参数配置")
	@GetMapping(value = "/configKey/{configKey}")
	public SysConfigVO getConfigByKey(@PathVariable String configKey) {
		return sysConfigService.selectConfigByKey(configKey);
	}

}