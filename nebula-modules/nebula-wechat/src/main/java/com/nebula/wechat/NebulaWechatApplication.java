package com.nebula.wechat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.nebula.**"})
public class NebulaWechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(NebulaWechatApplication.class, args);
		log.info("==========Nebula-Wechat 启动成功=========");
	}
}
