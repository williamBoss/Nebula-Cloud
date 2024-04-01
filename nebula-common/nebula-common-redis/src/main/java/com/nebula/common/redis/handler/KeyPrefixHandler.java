package com.nebula.common.redis.handler;

import org.redisson.api.NameMapper;
import org.springframework.util.StringUtils;

/**
 * redis缓存key前缀处理
 */
public class KeyPrefixHandler implements NameMapper {

    private final String keyPrefix;

    public KeyPrefixHandler(String keyPrefix) {
        // 前缀为空 则返回空前缀
        this.keyPrefix = StringUtils.hasText(keyPrefix) ? keyPrefix + ":" : "";
    }

    /**
     * 增加前缀
     */
    @Override
    public String map(String name) {
        return StringUtils.hasText(name) && !name.startsWith(keyPrefix) ? keyPrefix + name : name;
    }

    /**
     * 去除前缀
     */
    @Override
    public String unmap(String name) {
        return StringUtils.hasText(name) ? (name.startsWith(keyPrefix) ? name.substring(keyPrefix.length()) : name)
            : null;
    }
}
