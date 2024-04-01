package com.nebula.gateway.filter;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.nebula.common.constants.Constants;
import com.nebula.common.constants.SecurityConstants;
import com.nebula.common.constants.TokenConstants;
import com.nebula.common.json.utils.JacksonUtil;
import com.nebula.common.redis.utils.RedisUtils;
import com.nebula.gateway.domain.R;
import com.nebula.gateway.properties.IgnoreWhiteProperties;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关鉴权
 *
 * @author KING
 */
@Slf4j
@Component
public class UserInfoFilter implements GlobalFilter, Ordered {

    /**
     * 排除过滤的 uri 地址，nacos自行添加
     */
    private final IgnoreWhiteProperties ignoreWhite;

    public final RedisUtils redisUtils;

    private final PathMatcher pathMatcher = new AntPathMatcher();

    public UserInfoFilter(IgnoreWhiteProperties ignoreWhite, RedisUtils redisUtils) {
        this.ignoreWhite = ignoreWhite;
        this.redisUtils = redisUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        // 跳过不需要验证的路径
        boolean isWhiteUrl = ignoreWhite.getWhites().stream().anyMatch(pattern -> pathMatcher.match(pattern, url));
        if (isWhiteUrl) {
            return chain.filter(exchange);
        }
        String tokenKey = """
            %s%s""".formatted(TokenConstants.USER_INFO, StpUtil.getTokenValue());
        String userStr = redisUtils.get(tokenKey);
        Map<String, Object> parseObject = JacksonUtil.parseObject(userStr);
        String userid = parseObject.get("userId").toString();
        String username = parseObject.get("userName").toString();
        if (StringUtils.isBlank(userid) || StringUtils.isBlank(username)) {
            return setUnauthorizedResponse(exchange, "令牌验证失败");
        }
        redisUtils.expire(TokenConstants.USER_INFO, Constants.TOKEN_EXPIRE * 60);
        // 设置用户信息到请求
        ServerHttpRequest mutableReq = exchange.getRequest()
            .mutate()
            .header(SecurityConstants.USER_ID, userid)
            .header(SecurityConstants.USERNAME, username)
            .build();
        return chain.filter(exchange.mutate().request(mutableReq).build());
    }

    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange, String msg) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            return bufferFactory.wrap(JSON.toJSONBytes(R.fail(HttpStatus.INTERNAL_SERVER_ERROR, msg)));
        }));
    }

    @Override
    public int getOrder() {
        return -200;
    }

}