package com.nebula.system.domain.vo;

import com.nebula.system.domain.SysMenu;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单权限表
 */
@Schema(description = "菜单")
@Data
@AllArgsConstructor
@NoArgsConstructor
@AutoMapper(target = SysMenu.class)
public class SysMenuVO {

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    private Long menuId;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String menuName;

    /**
     * 父菜单ID
     */
    @Schema(description = "父菜单ID")
    private Long parentId;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer orderNum;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址")
    private String path;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;

    /**
     * 路由参数
     */
    @Schema(description = "路由参数")
    private String queryParam;

    /**
     * 是否为外链（0是 1否）
     */
    @Schema(description = "是否为外链（0是 1否）")
    private Integer isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    @Schema(description = "是否缓存（0缓存 1不缓存）")
    private Integer isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @Schema(description = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    /**
     * 显示状态（0显示 1隐藏）
     */
    @Schema(description = "显示状态（0显示 1隐藏）")
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    @Schema(description = "菜单状态（0正常 1停用）")
    private String status;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    private String perms;

    /**
     * 图标类型 sl:自定义图标 el：el-icon
     */
    @Schema(description = "图标类型 sl:自定义图标 el：el-icon")
    private String iconType;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 子菜单
     */
    @Schema(description = "子菜单")
    private List<SysMenuVO> children;

}