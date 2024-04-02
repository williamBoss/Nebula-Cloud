package com.nebula.common.core.config;

import com.nebula.common.constants.Constants;
import com.nebula.common.constants.TokenConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * sa-token配置
 *
 * @author KING
 */
@Configuration
public class SaTokenConfig {

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
