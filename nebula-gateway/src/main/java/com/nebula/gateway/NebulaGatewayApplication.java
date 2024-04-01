package com.nebula.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.nebula.**", exclude = {DataSourceAutoConfiguration.class})
public class NebulaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(NebulaGatewayApplication.class, args);
        log.info("Nebula Gateway is running...");
    }
}
