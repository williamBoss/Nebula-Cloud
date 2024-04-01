package com.nebula.gateway.filter;

import java.util.StringJoiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 日志过滤器
 *
 * @author KING
 */
@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logRequest(exchange);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("RequestTask");
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            stopWatch.stop();
            log.info("[PLUS]Outgoing response => [{}],耗时:[{}]毫秒", exchange.getResponse().getStatusCode(),
                stopWatch.getTotalTimeSeconds());
        }));
    }

    private void logRequest(ServerWebExchange exchange) {
        StringJoiner requestLog = new StringJoiner(" ");
        requestLog.add("请求地址[").add(exchange.getRequest().getURI().toString()).add("]");
        requestLog.add("请求方法[").add(exchange.getRequest().getMethod().toString()).add("]");
        requestLog.add("请求参数[").add(exchange.getRequest().getQueryParams().toString()).add("]");
        log.info("[PLUS]Incoming request => [{}]", requestLog);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
