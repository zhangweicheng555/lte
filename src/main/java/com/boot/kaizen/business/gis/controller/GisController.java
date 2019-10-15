package com.boot.kaizen.business.gis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * GIS百度地图控制层
 * 
 * @author weichengz
 * @date 2019年2月1日 上午10:13:24
 */
@Controller
@RequestMapping("/gis")
public class GisController {

	//@Autowired
	//private IAnalyzeService analyzeService;

	/**
	 * 
	 * @Description: lte单验 汇总计划每月/天单验统计
	 * @author weichengz
	 * @date 2019年2月1日 下午1:16:02
	 */
	@ResponseBody
	@PostMapping(value = "/analyzeLteAllAndComplete")
	public JsonMsgUtil analyzeLteAllAndComplete() {
		
		return new JsonMsgUtil(true);
	}

	

}
