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
     * 权限缓存前缀
     */
    public final static String LOGIN_TOKEN_KEY = SecurityConstants.AUTHORIZATION_HEADER + ":login:token:";

    /**
     * 用户信息缓存前缀
     */
    public final static String USER_INFO = SecurityConstants.AUTHORIZATION_HEADER + ":user:info:";
}
