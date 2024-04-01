package com.nebula.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nebula.system.domain.SysDept;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门表
 */
@Schema(description = "部门表")
@Data
@NoArgsConstructor
@AutoMapper(target = SysDept.class)
public class SysDeptVO implements Serializable {

    @Serial
    private final static long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 父部门id
     */
    @Schema(description = "父部门id")
    private Long parentId;

    /**
     * 祖级列表
     */
    @Schema(description = "祖级列表")
    private String ancestors;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 负责人
     */
    @Schema(description = "负责人")
    private Long leader;

    /**
     * 负责人
     */
    @Schema(description = "负责人姓名")
    private String leaderName;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 部门状态（0正常 1停用）
     */
    @Schema(description = "部门状态（0正常 1停用）")
    private String status;

    /**
     * 子节点
     */
    @Schema(description = "子节点")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SysDeptVO> children;

}