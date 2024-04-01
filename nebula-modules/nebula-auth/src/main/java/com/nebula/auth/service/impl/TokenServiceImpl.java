package com.nebula.auth.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.github.f4b6a3.ulid.UlidCreator;
import com.nebula.auth.domain.LoginUser;
import com.nebula.auth.service.TokenService;
import com.nebula.common.constants.Constants;
import com.nebula.common.constants.TokenConstants;
import com.nebula.common.core.utils.IpUtils;
import com.nebula.common.core.utils.ServletUtils;
import com.nebula.common.redis.utils.RedisUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private final RedisUtils redisUtils;

    private final static long EXPIRE_TIME = Constants.TOKEN_EXPIRE * 60;

    private final static String ACCESS_TOKEN = TokenConstants.USER_INFO;

    public TokenServiceImpl(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    public Map<String, Object> createToken(LoginUser userInfo) {
        // 生成token
        String token = UlidCreator.getMonotonicUlid().toString();
        userInfo.setToken(token);
        userInfo.setLoginIp(IpUtils.getIpAddr(Objects.requireNonNull(ServletUtils.getRequest())));
        // 保存或更新用户token
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("access_token", token);
        map.put("expires_in", EXPIRE_TIME);
        redisUtils.set(ACCESS_TOKEN + token, userInfo, EXPIRE_TIME);
        StpUtil.login(userInfo.getUserId(), new SaLoginModel().setToken(token).setExtra("expires_in", EXPIRE_TIME));
        return map;
    }

}
