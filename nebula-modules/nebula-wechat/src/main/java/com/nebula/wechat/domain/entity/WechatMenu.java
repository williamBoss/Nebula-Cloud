package com.nebula.wechat.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.nebula.datasource.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 微信菜单 实体类。
 *
 * @author William
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "微信菜单")
@Table(value = "wechat_menu")
public class WechatMenu extends BaseEntity {

	@Id(keyType = KeyType.Auto)
	private Integer id;

	/**
	 * 父级id
	 */
	@Schema(description = "父级id")
	@Column(value = "pid")
	private Integer pid;

	/**
	 * 菜单响应动作 view:网页 click:点击 miniprogram:小程序
	 */
	@Schema(description = "菜单响应动作 view:网页 click:点击 miniprogram:小程序")
	@Column(value = "menu_type")
	@JsonProperty("type")
	private String menuType;

	/**
	 * 菜单标题
	 */
	@Schema(description = "菜单标题")
	@Column(value = "menu_name")
	@JsonProperty("name")
	private String menuName;

	/**
	 * 菜单KEY值，用于消息接口推送，不超过128字节
	 */
	@Schema(description = "菜单KEY值，用于消息接口推送，不超过128字节")
	@Column(value = "menu_key")
	@JsonProperty("key")
	private String menuKey;

	/**
	 * 网页 链接，用户点击菜单可打开链接，不超过1024字节。 type为miniprogram时，不支持小程序的老版本客户端将打开本url
	 */
	@Schema(
		description = "网页 链接，用户点击菜单可打开链接，不超过1024字节。 type为miniprogram时，不支持小程序的老版本客户端将打开本url")
	@Column(value = "link_url")
	@JsonProperty("url")
	private String linkUrl;

	/**
	 * 小程序的appid（仅认证公众号可配置）
	 */
	@Schema(description = "小程序的appid（仅认证公众号可配置）")
	@Column(value = "appid")
	private String appid;

	/**
	 * 小程序的页面路径
	 */
	@Schema(description = "小程序的页面路径")
	@Column(value = "pagepath")
	private String pagepath;

	/**
	 * 二级菜单数组
	 */
	@JsonProperty("sub_button")
	private transient List<WechatMenu> subButton;
}
