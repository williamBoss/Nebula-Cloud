package com.nebula.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.nebula.**"})
public class NebulaAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(NebulaAuthApplication.class, args);
        log.info("Nebula-auth start success");
    }
}
