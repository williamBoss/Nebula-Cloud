package com.nebula.system.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.nebula.datasource.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 角色信息表 实体类。
 *
 * @author William
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(value = "sys_role")
public class SysRole extends BaseEntity {

    /**
     * 角色ID
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long roleId;

    /**
     * 角色名称
     */
    @Column(value = "role_name")
    private String roleName;

    /**
     * 角色Key字符串
     */
    @Column(value = "role_key")
    private String roleKey;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @Column(value = "data_scope")
    private String dataScope;

    /**
     * 角色状态（0正常 1停用）
     */
    @Column(value = "status")
    private String status;

    public boolean isAdmin() {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId) {
        return roleId != null && 1L == roleId;
    }
}
