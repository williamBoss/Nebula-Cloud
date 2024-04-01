package com.nebula.common.constants;

/**
 * 用户常量信息
 *
 * @author KING
 */
public class UserConstants {

    /**
     * 平台内系统用户的唯一标志
     */
    public static final String SYS_USER = "SYS_USER";

    /**
     * 正常状态
     */
    public static final String NORMAL = "0";

    /**
     * 异常状态
     */
    public static final String DISABLE = "1";

    /**
     * 是否为系统默认（是）
     */
    public static final String YES = "Y";

    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 2;

    public static final int USERNAME_MAX_LENGTH = 20;

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 5;

    public static final int PASSWORD_MAX_LENGTH = 20;

    /**
     * 超级管理员ID
     */
    public static final Long SUPER_ADMIN_ID = 1L;
}
