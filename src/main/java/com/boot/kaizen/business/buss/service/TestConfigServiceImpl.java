package com.boot.kaizen.business.buss.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.boot.kaizen.business.buss.dao.TestConfigMapper;
import com.boot.kaizen.business.buss.model.RequestParamConfig;
import com.boot.kaizen.business.buss.model.TestConfig;

/**
 * 
 * @author weichengz
 * @date 2019年10月21日 上午10:32:39
 */
@Service
public class TestConfigServiceImpl extends ServiceImpl<TestConfigMapper, TestConfig> implements ITestConfigService {

	@Override
	public List<RequestParamConfig> queryItemAll(String item, Integer projId,String type) {
		List<RequestParamConfig> datas = new ArrayList<>();

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projId", projId+"");
		
		if (StringUtils.isNotBlank(item)) {
			paramMap.put("item", item);
		}
		
		if (StringUtils.isBlank(type)) {
			type="0";
		}
		
		paramMap.put("configType", type);

		List<TestConfig> testConfigs = this.selectByMap(paramMap);
		if (testConfigs == null || testConfigs.size() == 0) {// 返回默认
			paramMap.put("projId", "1111111");
			testConfigs = this.selectByMap(paramMap);
		}

		if (testConfigs != null && testConfigs.size() > 0) {
			for (TestConfig testConfig : testConfigs) {
				String jsonStr = testConfig.getJsonStr();
				RequestParamConfig requestParamConfig = JSONObject.parseObject(jsonStr, RequestParamConfig.class);
				if (requestParamConfig != null) {
					datas.add(requestParamConfig);
				}
			}
		}
		return datas;
	}

}
