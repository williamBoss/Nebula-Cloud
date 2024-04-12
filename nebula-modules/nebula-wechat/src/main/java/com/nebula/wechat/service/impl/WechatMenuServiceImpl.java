package com.nebula.wechat.service.impl;

import org.springframework.stereotype.Service;
import com.nebula.wechat.service.IWechatMenuService;
import com.nebula.wechat.domain.entity.WechatMenu;
import com.nebula.wechat.mapper.WechatMenuMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

/**
 * 微信菜单 服务层实现。
 *
 * @author William
 * @since 1.0
 */
@Service
public class WechatMenuServiceImpl extends ServiceImpl<WechatMenuMapper, WechatMenu>
	implements IWechatMenuService {

}