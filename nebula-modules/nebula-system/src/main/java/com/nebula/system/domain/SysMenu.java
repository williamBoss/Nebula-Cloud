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
 * 菜单权限表 实体类。
 *
 * @author William
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(value = "sys_menu")
public class SysMenu extends BaseEntity {

    /**
     * 菜单ID
     */
    @Id(keyType = KeyType.Auto)
    private Integer menuId;

    /**
     * 菜单名称
     */
    @Column(value = "menu_name")
    private String menuName;

    /**
     * 父菜单ID
     */
    @Column(value = "parent_id")
    private Long parentId;

    /**
     * 显示顺序
     */
    @Column(value = "order_num")
    private Integer orderNum;

    /**
     * 路由地址
     */
    @Column(value = "path")
    private String path;

    /**
     * 组件路径
     */
    @Column(value = "component")
    private String component;

    /**
     * 路由参数
     */
    @Column(value = "query_param")
    private String queryParam;

    /**
     * 是否为外链（0是 1否）
     */
    @Column(value = "is_frame")
    private Integer isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    @Column(value = "is_cache")
    private Integer isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @Column(value = "menu_type")
    private String menuType;

    /**
     * 显示状态（0显示 1隐藏）
     */
    @Column(value = "visible")
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    @Column(value = "status")
    private String status;

    /**
     * 权限标识
     */
    @Column(value = "perms")
    private String perms;

    /**
     * 图标类型 sl:自定义图标 el：el-icon
     */
    @Column(value = "icon_type")
    private String iconType;

    /**
     * 菜单图标
     */
    @Column(value = "icon")
    private String icon;

}
