package com.nebula.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.nebula.auth.domain.LoginBody;
import com.nebula.auth.domain.LoginUser;
import com.nebula.auth.service.CaptchaService;
import com.nebula.auth.service.TokenService;
import com.nebula.auth.service.UserService;
import com.nebula.common.constants.TokenConstants;
import com.nebula.common.constants.UserConstants;
import com.nebula.common.core.exception.ServiceException;
import com.nebula.common.log.annotation.LoginLog;
import com.nebula.common.log.client.LogClient;
import com.nebula.common.log.event.LogininforEvent;
import com.nebula.common.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * token 控制
 *
 * @author KING
 */
@Slf4j
@RestController
public class LoginController {

	private final UserService userService;

	private final CaptchaService captchaService;

	private final TokenService tokenService;

	private final LogClient logClient;

	private final RedisUtils redisUtils;

	public LoginController(UserService userService, CaptchaService captchaService, TokenService tokenService,
		LogClient logClient, RedisUtils redisUtils) {
		this.userService = userService;
		this.captchaService = captchaService;
		this.tokenService = tokenService;
		this.logClient = logClient;
		this.redisUtils = redisUtils;
	}

	@GetMapping("captcha")
	public Map<String, Object> captcha() throws IOException {
		// 生成验证码
		return captchaService.createCaptcha();
	}

	@LoginLog(loginType = "登录", username = "#loginUser.username")
	@PostMapping("login")
	public Map<String, Object> login(@RequestBody LoginBody loginUser) {
		// 校验验证码
		captchaService.checkCaptcha(loginUser.getCaptcha(), loginUser.getUuid());
		// 用户名或密码为空 错误
		if (StringUtils.isAnyBlank(loginUser.getUsername(), loginUser.getPassword())) {
			throw new ServiceException("用户/密码必须填写");
		}
		// 密码如果不在指定范围内 错误
		if (loginUser.getPassword().length() < UserConstants.PASSWORD_MIN_LENGTH
			|| loginUser.getPassword().length() > UserConstants.PASSWORD_MAX_LENGTH) {
			throw new ServiceException("用户密码不在指定范围");
		}
		// 用户名不在指定范围内 错误
		if (loginUser.getUsername().length() < UserConstants.USERNAME_MIN_LENGTH
			|| loginUser.getUsername().length() > UserConstants.USERNAME_MAX_LENGTH) {
			throw new ServiceException("用户名不在指定范围");
		}
		// 用户登录
		LoginUser userInfo = userService.login(loginUser.getUsername(), loginUser.getPassword());
		// 获取登录token
		return tokenService.createToken(userInfo);
	}

	@LoginLog(loginType = "登出")
	@DeleteMapping("logout")
	public void logout() {
		StpUtil.logout();
		redisUtils.delete(TokenConstants.USER_INFO_KEY + StpUtil.getTokenValue());
		redisUtils.delete(TokenConstants.ROLE_KEY + StpUtil.getTokenValue());
		// 记录用户退出日志
		LogininforEvent event = new LogininforEvent(this);
		logClient.loginLog(event);
	}
}
