package com.boot.kaizen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 */
//@EnableEurekaClient
//@EnableFeignClients
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling
@EnableCaching
public class KaizenApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaizenApplication.class, args);
	}

}
