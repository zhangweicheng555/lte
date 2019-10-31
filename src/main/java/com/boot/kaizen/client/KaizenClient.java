package com.boot.kaizen.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.kaizen.client.conf.ClientConf;

/**
 * kaizen系统微服务客户端
 * 
 * @author weichengz
 * @date 2019年10月31日 上午11:14:14
 */
@FeignClient(value = "kaizen", configuration = ClientConf.class)
public interface KaizenClient {

	@RequestMapping(value = "/kaizen/app/appUser/login")
	public Object findById(@RequestParam("jName") String jName, @RequestParam("jPaw") String jPaw);
}
