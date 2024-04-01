package com.nebula.common.log.config;

import com.nebula.common.log.client.LogClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Slf4j
@Configuration
public class LogWebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    WebClient systemWebClient() {
        return webClientBuilder().baseUrl("http://nebula-system/").build();
    }

    @SneakyThrows
    @Bean
    LogClient logClient(WebClient systemWebClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(
            WebClientAdapter.create(systemWebClient)).build();
        return httpServiceProxyFactory.createClient(LogClient.class);
    }

}
