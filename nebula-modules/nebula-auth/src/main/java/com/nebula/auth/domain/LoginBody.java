package com.nebula.auth.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录请求参数
 *
 * @author KING
 */
@Schema(description = "登录请求参数")
@Data
@NoArgsConstructor
public class LoginBody {

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String captcha;

    /**
     * 唯一标识
     */
    @Schema(description = "唯一标识")
    private String uuid;
}
