package com.nebula.system.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.nebula.datasource.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 部门表 实体类。
 *
 * @author William
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(value = "sys_dept")
public class SysDept extends BaseEntity {

    /**
     * 部门id
     */
    @Id(keyType = KeyType.Auto)
    private Long deptId;

    /**
     * 父部门id
     */
    @Column(value = "parent_id")
    private Long parentId;

    /**
     * 祖级列表
     */
    @Column(value = "ancestors")
    private String ancestors;

    /**
     * 部门名称
     */
    @Column(value = "dept_name")
    private String deptName;

    /**
     * 负责人
     */
    @Column(value = "leader")
    private Long leader;

    /**
     * 联系电话
     */
    @Column(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @Column(value = "email")
    private String email;

    /**
     * 部门状态（0正常 1停用）
     */
    @Column(value = "status")
    private String status;
}
