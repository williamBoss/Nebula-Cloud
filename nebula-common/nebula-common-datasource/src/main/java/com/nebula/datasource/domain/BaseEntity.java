package com.nebula.datasource.domain;

import com.mybatisflex.annotation.Column;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity基类
 *
 * @author KING
 */
@Data
@NoArgsConstructor
public class BaseEntity {

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @Column(value = "del_flag", isLogicDelete = true)
    private Integer delFlag;

    /**
     * 创建者id
     */
    @Column(value = "create_id")
    private Long createId;

    /**
     * 创建者
     */
    @Column(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(value = "create_time", onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 更新者id
     */
    @Column(value = "update_id")
    private Long updateId;

    /**
     * 更新者
     */
    @Column(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(value = "update_time", onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Column(value = "remark")
    private String remark;
    
}
