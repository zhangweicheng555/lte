package com.boot.kaizen.business.gis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.client.KaizenClient;
import com.boot.kaizen.client.LogClient;

/**
 * 测试
 * 
 * @author weichengz
 * @date 2019年2月1日 上午10:13:24
 */
@Controller
@RequestMapping("/gis/test")
public class TestGisController {

	@Autowired
	private KaizenClient kaizenClient;
	@Autowired
	private LogClient logClient;

	/**
	 * 测试
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月31日 上午11:15:48
	 */
	@ResponseBody
	@PostMapping(value = "/findById")
	public Object analyzeLteAllAndComplete(@RequestParam("index") String index, @RequestParam("type") String type) {
		System.out.println("-------------------------------------");
		return logClient.indexIf(index, type);

	}

	@ResponseBody
	@PostMapping(value = "/queryPage")
	public Object queryPage() {
		return logClient.queryPage(JSONObject.toJSONString(new QueryParamData()));

	}

	@ResponseBody
	@PostMapping(value = "/queryById")
	public Object queryById(@RequestParam("index") String index, @RequestParam("type") String type,
			@RequestParam("id") String id) {
		return logClient.queryById(index, type, id);

	}

	@ResponseBody
	@PostMapping(value = "/login")
	public Object login(@RequestParam("jName") String jName, @RequestParam("jPaw") String jPaw) {
		return kaizenClient.login(jName, jPaw);

	}

	@ResponseBody
	@PostMapping(value = "/findByIdFiveCheck")
	public Object login(@RequestParam("id") String id) {
		System.out.println("------------------------------"+id);
		return kaizenClient.findById(id);

	}
	@ResponseBody
	@PostMapping(value = "/delete")
	public Object delete(@RequestParam("ids") String ids) {
		System.out.println("------------------------------"+ids);
		return kaizenClient.delete(ids);
		
	}

}
