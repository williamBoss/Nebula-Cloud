package com.nebula.wechat.mapper;

import com.nebula.wechat.domain.entity.WechatMenu;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信菜单 映射层。
 *
 * @author William
 * @since 1.0
 */
@Mapper
public interface WechatMenuMapper extends BaseMapper<WechatMenu> {

}
