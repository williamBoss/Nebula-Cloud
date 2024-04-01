package com.nebula.auth.config;

import com.nebula.auth.client.UserClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Slf4j
@Configuration
public class WebConfig {

    private final WebClient systemWebClient;

    public WebConfig(WebClient systemWebClient) {
        this.systemWebClient = systemWebClient;
    }

    @SneakyThrows
    @Bean
    UserClient userClient() {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(
            WebClientAdapter.create(systemWebClient)).build();
        return httpServiceProxyFactory.createClient(UserClient.class);
    }

}
