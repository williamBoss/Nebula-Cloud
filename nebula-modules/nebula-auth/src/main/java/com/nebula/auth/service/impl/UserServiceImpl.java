package com.nebula.auth.service.impl;

import com.nebula.auth.client.UserClient;
import com.nebula.auth.domain.LoginUser;
import com.nebula.auth.service.UserService;
import com.nebula.common.constants.Constants;
import com.nebula.common.constants.TokenConstants;
import com.nebula.common.core.exception.ServiceException;
import com.nebula.common.core.utils.security.BCryptUtil;
import com.nebula.common.redis.utils.RedisUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserClient userClient;

    private final RedisUtils redisUtils;

    private final static String ACCESS_TOKEN = TokenConstants.LOGIN_TOKEN_KEY;

    public UserServiceImpl(UserClient userClient, RedisUtils redisUtils) {
        this.userClient = userClient;
        this.redisUtils = redisUtils;
    }

    @Override
    public LoginUser login(String username, String password) {
        // 查询用户信息
        return userClient.getInfo(username).flatMap(userResult -> {
            if (Constants.FAIL.equals(userResult.getCode())) {
                // 当结果码表示失败时，返回一个包含ServiceException的Mono
                return Mono.error(new ServiceException(userResult.getMsg()));
            }
            LoginUser userInfo = userResult.getData();
            if (userInfo == null) {
                // 当用户信息为空时，也返回一个错误的Mono
                return Mono.error(new ServiceException("登录用户：" + username + " 不存在"));
            }
            // 当一切验证通过时，返回包含LoginUser的Mono
            return Mono.just(userInfo);
        }).flatMap(user -> {
            if (Constants.DISABLE.equals(user.getStatus())) {
                return Mono.error(new ServiceException("对不起，您的账号：" + username + " 已停用"));
            }
            if (!BCryptUtil.matchesPassword(password, user.getPassword())) {
                return Mono.error(new ServiceException("用户不存在/密码错误"));
            }
            user.setPassword(null);
            return Mono.just(user);
        }).block();
    }

}
