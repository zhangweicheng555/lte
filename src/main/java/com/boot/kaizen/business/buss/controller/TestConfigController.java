package com.boot.kaizen.business.buss.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
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
	public JsonMsgUtil edit(@RequestBody RequestParamConfig requestParamConfig) throws JsonProcessingException {
		Integer projId = Integer.valueOf(UserUtil.getLoginUser().getProjId().toString());
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
			return new JsonMsgUtil(true, "编辑成功", jsonStr);
		} else {
			return new JsonMsgUtil(false, "编辑失败:请求体接收参数为空", "");
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
		List<RequestParamConfig> datas = new ArrayList<>();

		Integer projId = Integer.valueOf(UserUtil.getLoginUser().getProjId().toString());
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projId", projId);
		if (StringUtils.isNotBlank(item)) {
			paramMap.put("item", item);
		}
		List<TestConfig> testConfigs = testConfigService.selectByMap(paramMap);
		if (testConfigs != null && testConfigs.size() > 0) {
			for (TestConfig testConfig : testConfigs) {
				String jsonStr = testConfig.getJsonStr();
				RequestParamConfig requestParamConfig = JSONObject.parseObject(jsonStr, RequestParamConfig.class);
				if (requestParamConfig != null) {
					datas.add(requestParamConfig);
				}
			}
		}
		return new JsonMsgUtil(true, "查询成功", datas);
	}

}
