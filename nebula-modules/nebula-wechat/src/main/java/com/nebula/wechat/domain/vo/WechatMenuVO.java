package com.nebula.wechat.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信菜单
 */
@Schema(description = "微信菜单")
@Data
@NoArgsConstructor
public class WechatMenuVO {
	@Schema(description = "")
	private Integer id;

	/**
	 * 父级id
	 */
	@Schema(description = "父级id")
	private Integer pid;

	/**
	 * 菜单响应动作 view:网页 click:点击 miniprogram:小程序
	 */
	@Schema(description = "菜单响应动作 view:网页 click:点击 miniprogram:小程序")
	private String menuType;

	/**
	 * 菜单标题
	 */
	@Schema(description = "菜单标题")
	private String menuName;

	/**
	 * 菜单KEY值，用于消息接口推送，不超过128字节
	 */
	@Schema(description = "菜单KEY值，用于消息接口推送，不超过128字节")
	private String menuKey;

	/**
	 * 网页 链接，用户点击菜单可打开链接，不超过1024字节。 type为miniprogram时，不支持小程序的老版本客户端将打开本url
	 */
	@Schema(
		description = "网页 链接，用户点击菜单可打开链接，不超过1024字节。 type为miniprogram时，不支持小程序的老版本客户端将打开本url")
	private String linkUrl;

	/**
	 * 小程序的appid（仅认证公众号可配置）
	 */
	@Schema(description = "小程序的appid（仅认证公众号可配置）")
	private String appid;

	/**
	 * 小程序的页面路径
	 */
	@Schema(description = "小程序的页面路径")
	private String pagepath;
}