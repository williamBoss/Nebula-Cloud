package com.nebula.common.redis.utils;

import com.nebula.common.json.utils.JacksonUtil;
import org.redisson.api.RKeys;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Redis工具类
 *
 * @author KING
 */
@Component
public class RedisUtils {

	private final RedissonClient redissonClient;

	/**
	 * 默认过期时长，单位：秒
	 */
	public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

	/**
	 * 不设置过期时长
	 */
	public final static long NOT_EXPIRE = -1;

	public RedisUtils(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}

	/**
	 * 获得缓存的基本对象列表
	 *
	 * @param pattern 字符串前缀
	 * @return 对象列表
	 */
	public Collection<String> keys(final String pattern) {
		RKeys rKeys = redissonClient.getKeys();
		Collection<String> keysList = new ArrayList<>();
		rKeys.getKeysByPattern(pattern).forEach(keysList::add);
		return keysList;
	}

	/**
	 * 判断 key是否存在
	 *
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public Boolean hasKey(String key) {
		RKeys rKeys = redissonClient.getKeys();
		return rKeys.countExists(key) > 0;
	}

	/**
	 * 插入缓存默认时间
	 *
	 * @param key   键
	 * @param value 值
	 * @author zmr
	 */
	public void set(String key, Object value) {
		set(key, value, DEFAULT_EXPIRE);
	}

	/**
	 * 插入缓存
	 *
	 * @param key    键
	 * @param value  值
	 * @param expire 过期时间(s)
	 * @author zmr
	 */
	public void set(String key, Object value, long expire) {
		set(key, value, expire, ChronoUnit.SECONDS);
	}

	/**
	 * 插入缓存
	 *
	 * @param key      键
	 * @param value    值
	 * @param expire   过期时间
	 * @param timeUnit 过期时间颗粒度（单位）
	 */
	public void set(String key, Object value, long expire, TemporalUnit timeUnit) {
		redissonClient.getBucket(key).set(toJson(value), Duration.of(expire, timeUnit));
	}

	/**
	 * 插入缓存对象类型
	 *
	 * @param key      键
	 * @param value    值
	 * @param expire   过期时间
	 * @param timeUnit 过期时间颗粒度（单位）
	 */
	public void setObjectValue(String key, Object value, long expire, TemporalUnit timeUnit) {
		redissonClient.getBucket(key).set(value, Duration.of(expire, timeUnit));
	}

	/**
	 * 设置有效时间
	 *
	 * @param key     Redis键
	 * @param timeout 超时时间
	 * @return true=设置成功；false=设置失败
	 */
	public Boolean expire(final String key, final long timeout) {
		return expire(key, timeout, ChronoUnit.SECONDS);
	}

	/**
	 * 设置有效时间
	 *
	 * @param key     Redis键
	 * @param timeout 超时时间
	 * @param unit    时间单位
	 * @return true=设置成功；false=设置失败
	 */
	public Boolean expire(final String key, final long timeout, final TemporalUnit unit) {
		return redissonClient.getBucket(key).expireIfSet(Duration.of(timeout, unit));
	}

	/**
	 * 获取有效时间
	 *
	 * @param key Redis键
	 * @return 有效时间
	 */
	public Long getExpire(final String key) {
		return redissonClient.getBucket(key).remainTimeToLive();
	}

	/**
	 * 返回字符串结果
	 *
	 * @param key 键
	 * @return
	 * @author zmr
	 */
	public String get(String key) {
		Object value = redissonClient.getBucket(key).get();
		return value == null ? null : value.toString();
	}

	/**
	 * 返回指定类型结果
	 *
	 * @param key   键
	 * @param clazz 类型class
	 * @return
	 * @author zmr
	 */
	public <T> T get(String key, Class<T> clazz) {
		String value = get(key);
		return value == null ? null : fromJson(value, clazz);
	}

	/**
	 * 获取Set集合
	 *
	 * @param key
	 * @return
	 */
	public Set<?> getSet(String key) {
		RSet<Object> set = redissonClient.getSet(key);
		return set.readAll();
	}

	/**
	 * 删除缓存
	 *
	 * @param key 键
	 * @author zmr
	 */
	public void delete(String key) {
		redissonClient.getBucket(key).delete();
	}

	/**
	 * Object转成JSON数据
	 */
	public String toJson(Object object) {
		if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double
			|| object instanceof Boolean || object instanceof String) {
			return String.valueOf(object);
		}
		return JacksonUtil.toJsonString(object);
	}

	/**
	 * JSON数据，转成Object
	 */
	private <T> T fromJson(String json, Class<T> clazz) {
		return JacksonUtil.parseObject(clazz, json);
	}

}