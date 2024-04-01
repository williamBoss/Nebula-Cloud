package com.nebula.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 当前登录用户信息
 *
 * @author KING
 */
@Schema(description = "当前登录用户信息")
@Data
@NoArgsConstructor
public class LoginUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    private Long roleId;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 用户类型（sys_user系统用户）
     */
    @Schema(description = "用户类型（sys_user系统用户）")
    private String userType;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phonenumber;

    /**
     * 用户性别（0未知 1男 2女）
     */
    @Schema(description = "用户性别（0未知 1男 2女）")
    private String sex;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatar;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Schema(description = "帐号状态（0正常 1停用）")
    private String status;

    /**
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginDate;

    /**
     * 用户唯一标识
     */
    @Schema(description = "用户唯一标识")
    private String token;

    /**
     * 过期时间
     */
    @Schema(description = "过期时间")
    private Long expireTime;

    /**
     * 权限列表
     */
    @Schema(description = "权限列表")
    private Set<String> permissions;

}
