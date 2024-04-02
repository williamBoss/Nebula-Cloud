package com.nebula.common.core.utils;

import com.nebula.common.constants.SecurityConstants;
import com.nebula.common.constants.TokenConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 权限获取工具类
 *
 * @author KING
 */
public class SecurityUtils {

	/**
	 * 获取用户
	 */
	public static String getUsername() {
		String username = Objects.requireNonNull(ServletUtils.getRequest()).getHeader(SecurityConstants.USERNAME);
		return ServletUtils.urlDecode(username);
	}

	/**
	 * 获取用户ID
	 */
	public static Long getUserId() {
		return Long.valueOf(Objects.requireNonNull(ServletUtils.getRequest()).getHeader(SecurityConstants.USER_ID));
	}

	/**
	 * 获取当前用户权限ID
	 *
	 * @return
	 */
	public static String getRoleId() {
		return Objects.requireNonNull(ServletUtils.getRequest()).getHeader(SecurityConstants.ROLE_ID);
	}

	/**
	 * 获取请求token
	 */
	public static String getToken() {
		return getToken(Objects.requireNonNull(ServletUtils.getRequest()));
	}

	/**
	 * 根据request获取请求token
	 */
	public static String getToken(HttpServletRequest request) {
		String token = request.getHeader(TokenConstants.AUTHENTICATION);
		if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
			token = token.replace(TokenConstants.PREFIX, "");
		}
		return token;
	}

	/**
	 * 是否为管理员
	 *
	 * @param userId 用户ID
	 * @return 结果
	 */
	public static boolean isAdmin(Long userId) {
		return userId != null && 1L == userId;
	}

}
