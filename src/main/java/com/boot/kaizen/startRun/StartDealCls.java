package com.boot.kaizen.startRun;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * 程序启动成功后执行  
 * @author weichengz
 * @date 2018年9月1日 上午8:23:41
 */
@Order(value = 1)
@Component
public class StartDealCls implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("---------------------------------------------");
		System.out.println("-------------Kaizen Log Start Finish-------------");
		System.out.println("---------------------------------------------");
	}

}
