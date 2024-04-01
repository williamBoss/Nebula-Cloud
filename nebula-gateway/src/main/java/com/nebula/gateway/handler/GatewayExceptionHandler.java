package com.nebula.gateway.handler;

import com.nebula.common.json.utils.JacksonUtil;
import com.nebula.gateway.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Order(-1)
@Component
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        String msg;
        HttpStatusCode httpStatusCode;
        if (ex instanceof NotFoundException) {
            msg = "服务未找到";
            httpStatusCode = HttpStatus.NOT_FOUND;
        } else if (ex instanceof ResponseStatusException responseStatusException) {
            msg = responseStatusException.getMessage();
            httpStatusCode = responseStatusException.getStatusCode();
        } else {
            msg = "内部服务器错误";
            httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        log.error("[网关异常处理]请求路径:{},异常信息:{}", exchange.getRequest().getPath(), ex.getMessage());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(httpStatusCode);
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            R fail = R.fail(httpStatusCode, msg);
            return bufferFactory.wrap(JacksonUtil.toJsonString(fail).getBytes());
        }));
    }
}
