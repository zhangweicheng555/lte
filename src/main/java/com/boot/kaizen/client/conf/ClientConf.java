package com.boot.kaizen.client.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Request;

/**
 * 微服务客户端超时配置
 * 
 * @author weichengz
 * @date 2019年10月31日 上午11:30:47
 */
@Configuration
public class ClientConf {

	@Bean
	Request.Options feignOptions() {
		return new Request.Options(/** connectTimeoutMillis **/
				60 * 60 * 1000, /** readTimeoutMillis 毫秒 **/
				60 * 60 * 1000);
	}
}
