package com.nebula.system.service.impl;

import static com.nebula.system.domain.table.SysDeptTableDef.SYS_DEPT;
import static com.nebula.system.domain.table.SysUserTableDef.SYS_USER;

import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.nebula.common.constants.UserConstants;
import com.nebula.system.domain.SysDept;
import com.nebula.system.domain.vo.SysDeptVO;
import com.nebula.system.mapper.SysDeptMapper;
import com.nebula.system.service.ISysDeptService;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 部门表 服务层实现。
 *
 * @author William
 * @since 1.0
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDeptVO> selectDeptList(SysDeptVO dept) {
        return sysDeptMapper.selectListByQueryAs(buildQueryWrapper(dept), SysDeptVO.class);
    }

    /**
     * 构建查询条件。
     *
     * @param dept 部门信息
     * @return 查询条件
     */
    private QueryWrapper buildQueryWrapper(SysDeptVO dept) {
        return QueryWrapper.create()
            .select(SYS_DEPT.ALL_COLUMNS, SYS_USER.NICK_NAME.as("leader_name"))
            .from(SYS_DEPT.as("d"))
            .leftJoin(SYS_USER.as("u"))
            .on(SYS_DEPT.LEADER.eq(SYS_USER.USER_ID))
            .where(SYS_DEPT.DEPT_ID.eq(dept.getDeptId()))
            .and(SYS_DEPT.PARENT_ID.eq(dept.getParentId()))
            .and(SYS_DEPT.DEPT_NAME.like(dept.getDeptName()))
            .and(SYS_DEPT.STATUS.eq(dept.getStatus()))
            .orderBy(SYS_DEPT.PARENT_ID.asc(), SYS_DEPT.DEPT_ID.asc());
    }

    @Override
    public SysDeptVO selectDeptById(Long deptId) {
        return sysDeptMapper.selectOneByQueryAs(
            QueryWrapper.create().select().from(SYS_DEPT).where(SYS_DEPT.DEPT_ID.eq(deptId)), SysDeptVO.class);
    }

    @Override
    public boolean checkDeptNameUnique(SysDeptVO sysDept) {
        QueryCondition condition = SYS_DEPT.DEPT_NAME.eq(sysDept.getDeptName())
            .and(SYS_DEPT.PARENT_ID.eq(sysDept.getParentId()))
            .and(SYS_DEPT.DEPT_ID.ne(sysDept.getDeptId()));
        return this.exists(condition);
    }

    @Override
    public boolean hasChildByDeptId(Long deptId) {
        return this.exists(SYS_DEPT.PARENT_ID.eq(deptId));
    }

    @Override
    public boolean selectNormalChildrenDeptById(Long deptId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
            .from(SYS_DEPT)
            .where(SYS_DEPT.STATUS.eq(UserConstants.NORMAL))
            .and(QueryMethods.findInSet(deptId.toString(), "ancestors").gt(0));
        return this.exists(queryWrapper);
    }

    @Override
    public List<SysDeptVO> buildDeptTreeSelect(List<SysDeptVO> depts) {
        if (CollectionUtils.isEmpty(depts)) {
            return new ArrayList<>();
        }
        Map<Long, List<SysDeptVO>> deptMap = depts.stream().collect(Collectors.groupingBy(SysDeptVO::getParentId));
        depts.forEach(dept -> dept.setChildren(deptMap.get(dept.getDeptId())));
        return depts.stream()
            .filter(dept -> dept.getParentId() == null || dept.getParentId() == 0)
            .collect(Collectors.toList());
    }

}