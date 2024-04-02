package com.nebula.common.constants;

/**
 * 通用常量信息
 *
 * @author KING
 */
public class Constants {

	/**
	 * 成功标记
	 */
	public static final Integer SUCCESS = 200;

	/**
	 * 失败标记
	 */
	public static final Integer FAIL = 500;

	/**
	 * 验证码 redis key
	 */
	public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

	/**
	 * 验证码有效期（分钟）
	 */
	public static final long CAPTCHA_EXPIRATION = 2;

	/**
	 * 令牌有效期（秒）
	 */
	public final static long TOKEN_EXPIRE = 720 * 60;

	/**
	 * 禁用状态
	 */
	public static final String DISABLE = "1";
}
