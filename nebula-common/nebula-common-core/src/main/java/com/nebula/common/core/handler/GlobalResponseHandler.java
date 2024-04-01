package com.nebula.common.core.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nebula.common.core.domain.R;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一返回
 */
@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    public GlobalResponseHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 用于判断是否需要做处理
     *
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * 处理返回值
     *
     * @param o                  controller 返回的值
     * @param methodParameter    具体执行的方法和返回值的封装
     * @param mediaType          请求头中的Content-Type
     * @param converterType      请求头中的Accept
     * @param serverHttpRequest  ServerHttpRequest对象
     * @param serverHttpResponse ServerHttpResponse对象
     * @return
     */
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
        Class<? extends HttpMessageConverter<?>> converterType, ServerHttpRequest serverHttpRequest,
        ServerHttpResponse serverHttpResponse) {
        String requestUrl = serverHttpRequest.getURI().getPath();
        if (requestUrl.contains("/v3/api-docs")) {
            // 如果请求的 URL 包含 /v3/api-docs，则不进行统一返回值处理
            return o;
        }
        // o is null -> return response
        if (o == null) {
            return R.success();
        }
        // o is instanceof R -> return o
        if (o instanceof R<?>) {
            return o;
        }
        return R.success().setData(o);
    }
}
