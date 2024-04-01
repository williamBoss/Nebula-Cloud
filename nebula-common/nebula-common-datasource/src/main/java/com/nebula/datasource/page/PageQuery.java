package com.nebula.datasource.page;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 分页查询实体类
 *
 * @author Lion Li
 */
@Data
public class PageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 当前页数
     */
    private Integer pageNum = 1;

    /**
     * 分页大小
     */
    private Integer pageSize = 10;

    /**
     * 排序列
     */
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    private String isAsc;
}
