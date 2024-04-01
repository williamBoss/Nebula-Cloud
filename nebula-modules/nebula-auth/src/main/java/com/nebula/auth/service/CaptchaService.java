package com.nebula.auth.service;

import java.io.IOException;
import java.util.Map;

/**
 * 验证码处理
 *
 * @author KING
 */
public interface CaptchaService {

    /**
     * 生成验证码
     */
    Map<String, Object> createCaptcha() throws IOException;

    /**
     * 校验验证码
     */
    void checkCaptcha(String code, String uuid);

}
