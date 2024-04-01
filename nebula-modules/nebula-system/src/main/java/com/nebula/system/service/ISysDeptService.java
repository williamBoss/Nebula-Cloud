package com.nebula.system.service;

import com.mybatisflex.core.service.IService;
import com.nebula.system.domain.SysDept;
import com.nebula.system.domain.vo.SysDeptVO;
import java.util.List;

/**
 * 部门表 服务层。
 *
 * @author William
 * @since 1.0
 */
public interface ISysDeptService extends IService<SysDept> {

    List<SysDeptVO> selectDeptList(SysDeptVO dept);

    SysDeptVO selectDeptById(Long deptId);

    boolean checkDeptNameUnique(SysDeptVO sysDept);

    boolean hasChildByDeptId(Long deptId);

    boolean selectNormalChildrenDeptById(Long deptId);

    List<SysDeptVO> buildDeptTreeSelect(List<SysDeptVO> depts);
}