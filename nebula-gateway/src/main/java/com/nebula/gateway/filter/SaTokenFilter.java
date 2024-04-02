package com.nebula.gateway.filter;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.nebula.common.constants.Constants;
import com.nebula.common.constants.TokenConstants;
import com.nebula.common.json.utils.JacksonUtil;
import com.nebula.gateway.domain.R;
import com.nebula.gateway.properties.IgnoreWhiteProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * [Sa-Token 权限认证] 配置类
 *
 * @author KING
 */
@Slf4j
@Component
public class SaTokenFilter {

    private final IgnoreWhiteProperties ignoreWhite;

    public SaTokenFilter(IgnoreWhiteProperties ignoreWhite) {
        this.ignoreWhite = ignoreWhite;
    }

    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
            // 拦截地址
            .addInclude("/**")
            // 放行地址
            .addExclude("/favicon.ico", "/error", "/auth/captcha", "/auth/login")
            // 放行地址
            .setExcludeList(ignoreWhite.getWhites())
            // 鉴权方法：每次访问进入
            .setAuth(obj -> {
                // 登录校验 -- 拦截所有路由
                SaRouter.match("/**").check(r -> {
                    // 检查是否登录
                    StpUtil.checkLogin();
                    // 刷新token有效期
                    StpUtil.renewTimeout(Constants.TOKEN_EXPIRE);
                });
            }).setError(e -> {
                log.error("认证失败，无法访问系统资源: ", e);
                if (e instanceof NotLoginException) {
                    return JacksonUtil.toJsonString(R.fail(HttpStatus.UNAUTHORIZED, e.getMessage()));
                }
                return JacksonUtil.toJsonString(R.fail(HttpStatus.UNAUTHORIZED, "认证失败，无法访问系统资源"));
            });
    }

    @Bean
    @Primary
    public cn.dev33.satoken.config.SaTokenConfig getSaTokenConfigPrimary() {
        cn.dev33.satoken.config.SaTokenConfig config = new cn.dev33.satoken.config.SaTokenConfig();
        config.setTokenName(TokenConstants.AUTHENTICATION);
        config.setTokenPrefix(TokenConstants.PREFIX);
        // token 有效期（单位：秒），默认30天，-1:代表永不过期
        config.setTimeout(Constants.TOKEN_EXPIRE);
        // token 最低活跃频率（单位：秒），如果 token 超过此时间没有访问系统就会被冻结，默认-1 代表不限制，永不冻结
        config.setActiveTimeout(-1);
        config.setIsReadBody(false);
        config.setIsReadCookie(false);
        return config;
    }

}
