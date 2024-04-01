package com.nebula.system.domain;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户和角色关联表 实体类。
 *
 * @author William
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(value = "sys_user_role")
public class SysUserRole {

    /**
     * 用户ID
     */
    @Id(keyType = KeyType.None)
    private Long userId;

    /**
     * 角色ID
     */
    @Id(keyType = KeyType.None)
    private Long roleId;
}
