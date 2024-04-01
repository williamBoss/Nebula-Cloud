package com.nebula.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 跨域配置
 *
 * @author KING
 */
@Configuration
public class CorsConfig {

    /**
     * 创建并配置CORS过滤器，以允许所有的跨域请求。
     *
     * @return CorsWebFilter CORS过滤器实例，用于处理跨域请求。
     */
    @Bean
    public CorsWebFilter corsFilter() {
        // 创建CORS配置并设置为允许所有请求方法
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        // 设置为允许所有请求来源
        config.addAllowedOrigin("*");
        // 设置为允许所有请求头
        config.addAllowedHeader("*");
        // 使用路径模式解析器创建并配置CORS配置源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        // 将配置注册到所有路径上
        source.registerCorsConfiguration("/**", config);
        // 返回CORS过滤器实例
        return new CorsWebFilter(source);
    }

}

