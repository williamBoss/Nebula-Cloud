package com.nebula.wechat.domain.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 微信用户信息
 */
@Data
public class WechatUserInfoVO {

	@JsonProperty(value = "openid")
	private String openid;

	@JsonProperty(value = "nickName")
	@JsonAlias(value = "nickname")
	private String nickName;

	@JsonProperty(value = "sex")
	private Integer sex;

	@JsonProperty(value = "province")
	private String province;

	@JsonProperty(value = "city")
	private String city;

	@JsonProperty(value = "country")
	private String country;

	@JsonProperty(value = "headImgUrl")
	@JsonAlias(value = "headimgurl")
	private String headImgUrl;

	@JsonProperty(value = "unionId")
	private String unionId;
}
