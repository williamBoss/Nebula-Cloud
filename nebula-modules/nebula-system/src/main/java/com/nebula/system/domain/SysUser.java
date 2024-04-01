package com.nebula.system.domain;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.nebula.datasource.domain.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户信息表 实体类。
 *
 * @author William
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(value = "sys_user")
public class SysUser extends BaseEntity {

    /**
     * 用户ID
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long userId;

    /**
     * 部门ID
     */
    @Column(value = "dept_id")
    private Long deptId;

    /**
     * 用户账号
     */
    @Column(value = "user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @Column(value = "nick_name")
    private String nickName;

    /**
     * 用户类型（sys_user系统用户）
     */
    @Column(value = "user_type")
    private String userType;

    /**
     * 用户邮箱
     */
    @Column(value = "email")
    private String email;

    /**
     * 手机号码
     */
    @Column(value = "phonenumber")
    private String phonenumber;

    /**
     * 用户性别（0未知 1男 2女）
     */
    @Column(value = "sex")
    private String sex;

    /**
     * 头像地址
     */
    @Column(value = "avatar")
    private String avatar;

    /**
     * 密码
     */
    @Column(value = "password")
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Column(value = "status")
    private String status;

    /**
     * 最后登录IP
     */
    @Column(value = "login_ip")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Column(value = "login_date")
    private LocalDateTime loginDate;

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
    
}
