package com.boot.kaizen.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.boot.kaizen.client.conf.ClientConf;

/**
 * kaizen系统微服务客户端
 * 
 * @author weichengz
 * @date 2019年10月31日 上午11:14:14
 */
@FeignClient(value = "kaizenLog", configuration = ClientConf.class, url = "http://ihandle.huanuo-nsb.com")
public interface LogClient {

	@RequestMapping(value = "/gis/indexIf", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public Object indexIf(@RequestParam("index") String index, @RequestParam("type") String type);

	@RequestMapping(value = "/gis/queryPage", method = RequestMethod.POST)
	public Object queryPage(@RequestBody String queryParamData);

	@RequestMapping(value = "/gis/queryById", method = RequestMethod.POST)
	public Object queryById(@RequestParam("index") String index, @RequestParam("type") String type,
			@RequestParam("id") String id);
}
