package com.nebula.common.core.handler;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.nebula.common.constants.TokenConstants;
import com.nebula.common.core.utils.SecurityUtils;
import com.nebula.common.redis.utils.RedisUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class StpInterfaceHandler implements StpInterface {

	private final RedisUtils redisUtils;

	public StpInterfaceHandler(RedisUtils redisUtils) {
		this.redisUtils = redisUtils;
	}

	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		return Optional.ofNullable(SecurityUtils.getRoleKey())
			.map(String::toLowerCase)
			.filter(roleKey -> roleKey.equals("superadmin"))
			.map(roleKey -> Collections.singletonList("*"))
			.orElseGet(
				() -> new ArrayList<>(redisUtils.getList(TokenConstants.PERMISSIONS_KEY + StpUtil.getTokenValue())));
	}

	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		List<String> roleList = new ArrayList<>();
		roleList.add(SecurityUtils.getRoleKey());
		return roleList;
	}
}
