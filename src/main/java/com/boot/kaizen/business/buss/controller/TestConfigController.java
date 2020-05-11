package com.boot.kaizen.business.buss.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.boot.kaizen.business.buss.model.RequestParamConfig;
import com.boot.kaizen.business.buss.model.TestConfig;
import com.boot.kaizen.business.buss.service.ITestConfigService;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 测试配置项
 * 
 * @author weichengz
 * @date 2020年1月14日 下午4:37:15
 */
@Controller
@RequestMapping("/buss/testConfig")
public class TestConfigController {

	@Autowired
	private ITestConfigService testConfigService;

	/**
	 * 
	 * @Description: 编辑
	 * @author weichengz
	 * @date 2020年1月14日 下午4:59:02
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JsonMsgUtil edit(@RequestBody List<RequestParamConfig> requestParamConfigs) throws JsonProcessingException {
		Integer projId = Integer.valueOf(UserUtil.getLoginUser().getProjId().toString());
		if (requestParamConfigs != null && requestParamConfigs.size() > 0) {
			for (RequestParamConfig requestParamConfig : requestParamConfigs) {
				if (requestParamConfig != null) {
					requestParamConfig.validateItem();
					String jsonStr = new ObjectMapper().writeValueAsString(requestParamConfig);

					Map<String, Object> paramMap = new HashMap<>();
					paramMap.put("projId", projId);
					paramMap.put("item", requestParamConfig.getItem());
					List<TestConfig> testConfigs = testConfigService.selectByMap(paramMap);

					TestConfig data = null;
					if (testConfigs != null && testConfigs.size() > 0) {
						data = testConfigs.get(0);
						data.setJsonStr(jsonStr);
					} else {
						data = new TestConfig(projId, new Date(), requestParamConfig.getItem(), jsonStr);
					}
					if (data != null) {
						testConfigService.insertOrUpdate(data);
					}
				} else {
					return new JsonMsgUtil(false, "编辑失败:请求体接收参数为空", "");
				}
			}
			return new JsonMsgUtil(true, "编辑成功", "");
		} else {
			return new JsonMsgUtil(true, "编辑失败，保存数据为空", "");
		}
	}

	

	/**
	 * 查询
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年1月14日 下午4:59:17
	 */
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public JsonMsgUtil queryByItem(@RequestParam(value = "item", required = false) String item) {
		Integer projId = Integer.valueOf(UserUtil.getLoginUser().getProjId().toString());
		return new JsonMsgUtil(true, "查询成功", testConfigService.queryItemAll(item,projId));
	}
	

}
