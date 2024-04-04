package com.nebula.common.constants;

/**
 * Token的Key常量
 *
 * @author KING
 */
public class TokenConstants {

	/**
	 * 令牌自定义标识
	 */
	public static final String AUTHENTICATION = SecurityConstants.AUTHORIZATION_HEADER;

	/**
	 * 令牌前缀
	 */
	public static final String PREFIX = "Bearer";

	/**
	 * 令牌秘钥
	 */
	public final static String SECRET = "B1138FD85F144210BE849354A49CBFD6";

	/**
	 * 用户信息缓存前缀
	 */
	public final static String USER_INFO_KEY = SecurityConstants.AUTHORIZATION_HEADER + ":user:info:";

	/**
	 * 角色信息缓存前缀
	 */
	public final static String ROLE_KEY = SecurityConstants.AUTHORIZATION_HEADER + ":role:info:";

	/**
	 * 权限缓存前缀
	 */
	public final static String PERMISSIONS_KEY = SecurityConstants.AUTHORIZATION_HEADER + ":permission:list:";

}
