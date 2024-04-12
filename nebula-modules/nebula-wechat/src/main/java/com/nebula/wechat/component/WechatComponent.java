package com.nebula.wechat.component;

import com.nebula.common.json.utils.JacksonUtil;
import com.nebula.common.redis.utils.RedisUtils;
import com.nebula.wechat.constant.Constants;
import com.nebula.wechat.domain.vo.WechatUserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 微信帮助类
 */
@Slf4j
@Component
public class WechatComponent {

	private final RedisUtils redisUtils;

	public WechatComponent(RedisUtils redisUtils) {
		this.redisUtils = redisUtils;
	}

	/**
	 * 创建微信加密签名
	 *
	 * @param sign 签名字符串
	 */
	public String createSignature(String sign) throws Exception {
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		// 对string1 字符串进行SHA-1加密处理
		crypt.update(sign.getBytes(StandardCharsets.UTF_8));
		// 对加密后字符串转成16进制
		Formatter formatter = new Formatter();
		for (byte b : crypt.digest()) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/**
	 * 获取微信accessToken
	 */
	public String getAccessToken(String appid, String secret) {
		String accessToken = redisUtils.get(Constants.WX_ACCESS_TOKEN);
		if (StringUtils.isNotBlank(accessToken)) {
			log.info("从缓存中获取token,token={}", accessToken);
			return accessToken;
		}
		log.info("重新从微信获取access_token");
		AtomicReference<String> newAccessToken = new AtomicReference<>();
		Mono<String> response = WebClient.create("https://api.weixin.qq.com")
			.get()
			.uri(uriBuilder -> uriBuilder.path("/cgi-bin/token")
				.queryParam("grant_type", "client_credential")
				.queryParam("appid", appid)
				.queryParam("secret", secret)
				.build())
			.retrieve()
			.bodyToMono(String.class);
		String res = response.block();
		log.info("微信access_token返回结果,{}", res);
		Map<String, Object> map = JacksonUtil.parseObject(res);
		// 获取令牌
		newAccessToken.set(map.get("access_token").toString());
		if (StringUtils.isNotBlank(newAccessToken.get())) {
			redisUtils.set(Constants.WX_ACCESS_TOKEN, accessToken, (Long)map.get("expires_in"));
			log.info("access_token:{}", newAccessToken.get());
		}
		return newAccessToken.get();
	}

	/**
	 * 微信网页授权access_token
	 */
	public Map<String, String> getAccessTokenBySns(String appid, String secret, String code) {
		Map<String, String> data = new HashMap<>(2);
		Mono<String> response = WebClient.create("https://api.weixin.qq.com")
			.get()
			.uri(uriBuilder -> uriBuilder.path("/sns/oauth2/access_token")
				.queryParam("appid", appid)
				.queryParam("secret", secret)
				.queryParam("code", code)
				.queryParam("grant_type", "authorization_code")
				.build())
			.retrieve()
			.bodyToMono(String.class);
		String res = response.block();
		log.info("微信网页授权access_token，{}", res);
		Map<String, Object> obj = JacksonUtil.parseObject(res);
		data.put("openid", obj.get("openid").toString());
		data.put("token", obj.get("access_token").toString());
		return data;
	}

	/**
	 * 获取微信用户信息
	 */
	public WechatUserInfoVO getWeiXinUserInfo(String openid, String token) {
		AtomicReference<WechatUserInfoVO> wechatUserInfo = new AtomicReference<>();
		// 获取access token
		Mono<String> response = WebClient.create("https://api.weixin.qq.com")
			.get()
			.uri(uriBuilder -> uriBuilder.path("/sns/userinfo")
				.queryParam("access_token", token)
				.queryParam("openid", openid)
				.queryParam("lang", "zh_CN")
				.build())
			.retrieve()
			.bodyToMono(String.class);
		String res = response.block();
		log.info("微信用户信息，{}", res);
		wechatUserInfo.set(JacksonUtil.parseObject(WechatUserInfoVO.class, res));
		return wechatUserInfo.get();
	}

	/**
	 * 发送自定义菜单
	 *
	 * @param accessToken token
	 * @param menuJson    菜单json
	 */
	public Boolean createMenu(String accessToken, String menuJson) {
		Mono<String> response = WebClient.create("https://api.weixin.qq.com")
			.post()
			.uri(uriBuilder -> uriBuilder.path("/cgi-bin/menu/create").queryParam("access_token", accessToken).build())
			.bodyValue(menuJson)
			.retrieve()
			.bodyToMono(String.class);
		response.subscribe(res -> log.info("微信自定义菜单，{}", res),
			error -> log.error("微信自定义菜单Error: ", error));
		return true;
	}

}
