package com.nebula.wechat.controller;

import com.mybatisflex.core.query.QueryWrapper;
import com.nebula.common.json.utils.JacksonUtil;
import com.nebula.wechat.component.WechatComponent;
import com.nebula.wechat.domain.entity.WechatMenu;
import com.nebula.wechat.domain.vo.WechatMenuVO;
import com.nebula.wechat.domain.vo.WechatUserInfoVO;
import com.nebula.wechat.service.IWechatMenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.nebula.wechat.domain.entity.table.WechatMenuTableDef.WECHAT_MENU;

/**
 * 微信菜单
 *
 * @author William
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/wechat/config")
@Tag(name = "微信配置")
public class WechatMenuController {

	@Value("${wechat.token}")
	private String token;

	@Value("${wechat.appid}")
	private String appid;

	@Value("${wechat.appsecret}")
	private String appsecret;

	private final IWechatMenuService wechatMenuService;

	private final WechatComponent wechatComponent;

	public WechatMenuController(IWechatMenuService wechatMenuService, WechatComponent wechatComponent) {
		this.wechatMenuService = wechatMenuService;
		this.wechatComponent = wechatComponent;
	}

	/**
	 * 回调地址验证
	 */
	@GetMapping(value = "/notify")
	public String wechatNotify(HttpServletRequest request,
		@RequestParam(value = "signature", required = false) String signature,
		@RequestParam(value = "timestamp", required = false) String timestamp,
		@RequestParam(value = "nonce", required = false) String nonce,
		@RequestParam(value = "echostr", required = false) String echostr) {
		log.info("接收微信数据,signature={},timestamp={},nonce={},echostr={}", signature, timestamp, nonce, echostr);
		if (StringUtils.isNotBlank(echostr)) {
			String[] sign = new String[] {token, timestamp, nonce};
			Arrays.sort(sign);
			try {
				if (signature.equalsIgnoreCase(wechatComponent.createSignature(sign[0] + sign[1] + sign[2]))) {
					return echostr;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		try {
			request.setCharacterEncoding("UTF-8");
			byte[] reqBodyBytes = readBytes(request.getInputStream(), request.getContentLength());
			String requestBody = new String(reqBodyBytes);
			log.info("monitor: {}", requestBody);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	/**
	 * 获取微信用户信息
	 *
	 * @param code 微信code
	 */
	@GetMapping("/user-info")
	public WechatUserInfoVO getWechatUserInfo(@RequestParam("code") String code) {
		Map<String, String> accessToken = wechatComponent.getAccessTokenBySns(appid, appsecret, code);
		WechatUserInfoVO wechatUserInfo =
			wechatComponent.getWeiXinUserInfo(accessToken.get("openid"), accessToken.get("token"));
		// todo 保存用户信息
		return wechatUserInfo;
	}

	/**
	 * 添加微信菜单
	 */
	@PostMapping("/add/menu")
	public void addWechatMenu(@RequestBody WechatMenuVO wechatMenu) {
		// 一级菜单数组，个数应为1~3个
		// 二级菜单数组，个数应为1~5个
		// 菜单标题，不超过16个字节，子菜单不超过60个字节
	}

	/**
	 * 发布菜单
	 */
	@PostMapping("/publish/menu")
	public void publishWechatMenu() {
		QueryWrapper queryWrapper = QueryWrapper.create()
			.select(WECHAT_MENU.ID, WECHAT_MENU.PID, WECHAT_MENU.MENU_TYPE, WECHAT_MENU.MENU_NAME, WECHAT_MENU.MENU_KEY,
				WECHAT_MENU.LINK_URL)
			.from(WECHAT_MENU);
		List<WechatMenu> wechatMenus = wechatMenuService.list(queryWrapper);
		List<WechatMenu> menus = wechatMenus.stream()
			.filter(menu -> menu.getPid() == 0)
			.peek(menu -> menu.setSubButton(wechatMenus.stream()
				.filter(m -> Objects.equals(m.getPid(), menu.getId()))
				.collect(Collectors.toList())))
			.toList();
		Map<String, List<WechatMenu>> wechatMenuMap = new HashMap<>(1);
		wechatMenuMap.put("button", menus);
		String accessToken = wechatComponent.getAccessToken(appid, appsecret);
		wechatComponent.createMenu(accessToken, JacksonUtil.toJsonString(wechatMenuMap));
	}

	/**
	 * 解析request中body数据
	 */
	private byte[] readBytes(InputStream is, int contentLen) {
		if (contentLen > 0) {
			int readLen = 0;
			int readLengthThisTime = 0;
			byte[] message = new byte[contentLen];
			try {
				while (readLen != contentLen) {
					readLengthThisTime = is.read(message, readLen, contentLen - readLen);
					if (readLengthThisTime == -1) {
						break;
					}
					readLen += readLengthThisTime;
				}
				return message;
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return new byte[] {};
	}
}