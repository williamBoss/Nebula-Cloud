package com.nebula.auth.service.impl;

import com.google.code.kaptcha.Producer;
import com.nebula.auth.service.CaptchaService;
import com.nebula.common.constants.Constants;
import com.nebula.common.core.exception.ServiceException;
import com.nebula.common.redis.utils.RedisUtils;
import jakarta.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    /**
     * 验证码类型
     */
    @Value("${captcha.type:char}")
    private String captchaType;

    private final RedisUtils redisUtils;

    public CaptchaServiceImpl(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    public Map<String, Object> createCaptcha() throws IOException {
        // 保存验证码信息
        String uuid = UUID.randomUUID().toString();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String capStr, code = null;
        BufferedImage image = null;
        // 生成验证码
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }
        redisUtils.set(verifyKey, code, Constants.CAPTCHA_EXPIRATION, ChronoUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            throw new IOException("生成验证码失败", e);
        }
        Map<String, Object> captchaMap = new HashMap<>(2);
        captchaMap.put("uuid", uuid);
        captchaMap.put("img", Base64.encode(os.toByteArray()));
        return captchaMap;
    }

    @Override
    public void checkCaptcha(String code, String uuid) {
        if (StringUtils.isEmpty(code)) {
            throw new ServiceException("验证码不能为空");
        }
        if (StringUtils.isEmpty(uuid)) {
            throw new ServiceException("验证码已失效");
        }
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisUtils.get(verifyKey);
        redisUtils.delete(verifyKey);
        if (!code.equalsIgnoreCase(captcha)) {
            throw new ServiceException("验证码错误");
        }
    }
}
