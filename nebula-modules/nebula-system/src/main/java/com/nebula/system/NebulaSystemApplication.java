package com.nebula.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.nebula.**"})
public class NebulaSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(NebulaSystemApplication.class, args);
        log.info("==========Nebula-System 启动成功==========");
    }
}
