package com.boot.kaizen.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.boot.kaizen.client.conf.ClientConf;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * kaizen系统微服务客户端
 * 
 * @author weichengz
 * @date 2019年10月31日 上午11:14:14
 */
@Component
//@FeignClient(value = "kaizen", configuration = ClientConf.class, url = "http://140.206.187.251:50123")
@FeignClient(value = "kaizen", configuration = ClientConf.class, url = "http://10.255.255.133:1234")
public interface KaizenClient {

	@RequestMapping(value = "/app/appUser/login", method = RequestMethod.GET)
	public Object login(@RequestParam("jName") String jName, @RequestParam("jPaw") String jPaw);

	@RequestMapping(value = "/5g/check/gc/findById", method = RequestMethod.POST)
	public Object findById(@RequestParam("id") String id);

	@RequestMapping(value = "/5g/check/gc/delete", method = RequestMethod.POST)
	public Object delete(@RequestParam("ids") String ids);

	/** 更新和刷新token */
	@RequestMapping(value = "/token/checkAndRefreshToken", method = RequestMethod.POST)
	public JsonMsgUtil checkAndRefreshToken(@RequestParam("token") String token);

}
