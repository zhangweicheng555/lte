package com.boot.kaizen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling
public class KaizenApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaizenApplication.class, args);
	}

}
