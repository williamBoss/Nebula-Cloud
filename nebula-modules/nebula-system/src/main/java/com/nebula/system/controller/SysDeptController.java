package com.nebula.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.nebula.common.constants.UserConstants;
import com.nebula.common.core.exception.ServiceException;
import com.nebula.common.log.annotation.OperLog;
import com.nebula.common.log.enums.BusinessType;
import com.nebula.system.domain.SysDept;
import com.nebula.system.domain.vo.SysDeptVO;
import com.nebula.system.service.ISysDeptService;
import com.nebula.system.service.ISysUserService;
import io.github.linpeilie.Converter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门表 控制层。
 *
 * @author William
 * @since 1.0
 */
@RestController
@RequestMapping("/sysDept")
public class SysDeptController {

	private final ISysDeptService sysDeptService;

	private final ISysUserService sysUserService;

	private final Converter converter;

	public SysDeptController(ISysDeptService sysDeptService, ISysUserService sysUserService, Converter converter) {
		this.sysDeptService = sysDeptService;
		this.sysUserService = sysUserService;
		this.converter = converter;
	}

	/**
	 * 查询所有部门表
	 *
	 * @return 所有数据
	 */
	@SaCheckPermission(value = "sys:dept:list")
	@GetMapping("/list")
	public List<SysDeptVO> list(SysDeptVO dept) {
		List<SysDeptVO> depts = sysDeptService.selectDeptList(dept);
		return sysDeptService.buildDeptTreeSelect(depts);
	}

	/**
	 * 根据部门表主键获取详细信息。
	 *
	 * @param deptId sysDept主键
	 * @return 部门表详情
	 */
	@GetMapping(value = "/{deptId}")
	public SysDeptVO getInfo(@PathVariable Long deptId) {
		return sysDeptService.selectDeptById(deptId);
	}

	/**
	 * 添加 部门表
	 *
	 * @param sysDept 部门表
	 */
	@SaCheckPermission(value = "sys:dept:save")
	@OperLog(title = "部门管理", businessType = BusinessType.INSERT)
	@PostMapping
	public void save(@RequestBody SysDeptVO sysDept) {
		if (sysDeptService.checkDeptNameUnique(sysDept)) {
			throw new ServiceException("新增部门'" + sysDept.getDeptName() + "'失败，部门名称已存在");
		}
		sysDeptService.save(converter.convert(sysDept, SysDept.class));
	}

	/**
	 * 根据主键更新部门表
	 *
	 * @param sysDept 部门表
	 */
	@SaCheckPermission(value = "sys:dept:update")
	@OperLog(title = "部门管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public void update(@RequestBody SysDeptVO sysDept) {
		Long deptId = sysDept.getDeptId();
		if (sysDeptService.checkDeptNameUnique(sysDept)) {
			throw new ServiceException("修改部门'" + sysDept.getDeptName() + "'失败，部门名称已存在");
		}
		if (sysDept.getParentId().equals(deptId)) {
			throw new ServiceException("修改部门'" + sysDept.getDeptName() + "'失败，上级部门不能是自己");
		}
		if (StringUtils.equals(UserConstants.DISABLE, sysDept.getStatus())) {
			if (sysDeptService.selectNormalChildrenDeptById(deptId)) {
				throw new ServiceException("该部门包含未停用的子部门!");
			} else if (sysUserService.checkDeptExistUser(deptId)) {
				throw new ServiceException("该部门下存在已分配用户，不能禁用!");
			}
		}
		sysDeptService.updateById(converter.convert(sysDept, SysDept.class));
	}

	/**
	 * 根据主键删除部门表
	 *
	 * @param deptId 主键
	 */
	@SaCheckPermission(value = "sys:dept:remove")
	@OperLog(title = "部门管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deptId}")
	public void remove(@PathVariable Long deptId) {
		if (sysDeptService.hasChildByDeptId(deptId)) {
			throw new ServiceException("存在下级部门,不允许删除");
		}
		if (sysUserService.checkDeptExistUser(deptId)) {
			throw new ServiceException("部门存在用户,不允许删除");
		}
		sysDeptService.removeById(deptId);
	}

	/**
	 * 部门树结构
	 *
	 * @param dept 部门表
	 * @return 部门表树
	 */
	@GetMapping("/treeselect")
	public List<SysDeptVO> treeselect(SysDeptVO dept) {
		List<SysDeptVO> depts = sysDeptService.selectDeptList(dept);
		return sysDeptService.buildDeptTreeSelect(depts);
	}

}