package com.boot.kaizen.config;

import java.text.ParseException;
import org.springframework.context.annotation.Configuration;

/**
 * 定时器使用
 * 
 * @author weichengz
 * @date 2018年9月1日 上午8:16:37
 */
@Configuration
// @EnableScheduling
public class ScheduledConfig {

	/**
	 * 下午2点到晚上8点 每5分钟执行一次
	 */
	// @Scheduled(cron = "0 0/5 17-20 * * ?")
	public void scheduledByFiveMinute() throws ParseException {
		
	}

	/**
	 * 每天21点执行
	 */
	// @Scheduled(cron = "0 0 21 * * ?")
	public void dateTask() {
	}
}
