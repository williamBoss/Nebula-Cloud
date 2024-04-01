package com.nebula.system.domain;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色和菜单关联表 实体类。
 *
 * @author William
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_role_menu")
public class SysRoleMenu {

    /**
     * 角色ID
     */
    @Id(keyType = KeyType.None)
    private Long roleId;

    /**
     * 菜单ID
     */
    @Id(keyType = KeyType.None)
    private Long menuId;
}
