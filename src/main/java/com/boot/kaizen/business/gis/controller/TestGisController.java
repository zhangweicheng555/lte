package com.boot.kaizen.business.gis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.boot.kaizen.client.KaizenClient;

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

	/**
	 * 测试
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月31日 上午11:15:48
	 */
	@ResponseBody
	@PostMapping(value = "/findById")
	public Object analyzeLteAllAndComplete() {
		System.out.println("-------------------------------------");
		return kaizenClient.findById("weicheng","123465");
	}

	
}
