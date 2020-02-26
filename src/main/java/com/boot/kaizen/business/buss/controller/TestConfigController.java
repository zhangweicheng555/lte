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
	
	/**
	 * 导入默认的测试配置项
	 */
	/*@ResponseBody
	@RequestMapping(value = "/addTestData", method = RequestMethod.POST)
	public JsonMsgUtil addTestData() throws JsonProcessingException {
		Integer projId = Integer.valueOf(UserUtil.getLoginUser().getProjId().toString());
		
		String msg = "{\"success\":true,\"message\":\"查询成功\",\"time\":1579240609604,\"code\":200,\"object\":[{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"FTPDOWN\",\"color\":\"#FF0000\",\"createTime\":\"2019-12-04 15:08:27.98\",\"id\":\"358236a6192a4b31988e3551338f586d\",\"bigValue\":10.0},{\"projId\":111111111,\"minValue\":10.0,\"itemName\":\"FTPDOWN\",\"color\":\"#FF00FF\",\"createTime\":\"2019-12-04 15:08:27.982\",\"id\":\"65492c14637c44418674d59ec8a7f349\",\"bigValue\":50.0},{\"projId\":111111111,\"minValue\":50.0,\"itemName\":\"FTPDOWN\",\"color\":\"#FFFF00\",\"createTime\":\"2019-12-04 15:08:27.985\",\"id\":\"a5e95d6011d84f39b48acbfaa93ebad5\",\"bigValue\":100.0},{\"projId\":111111111,\"minValue\":100.0,\"itemName\":\"FTPDOWN\",\"color\":\"#0000FF\",\"createTime\":\"2019-12-04 15:08:27.988\",\"id\":\"ca31d580bf5d413fa6f7b64b33b28d79\",\"bigValue\":300.0},{\"projId\":111111111,\"minValue\":300.0,\"itemName\":\"FTPDOWN\",\"color\":\"#00FF00\",\"createTime\":\"2019-12-04 15:08:27.99\",\"id\":\"63600af6c7e642e3ac2285addd67fa1b\",\"bigValue\":600.0},{\"projId\":111111111,\"minValue\":600.0,\"itemName\":\"FTPDOWN\",\"color\":\"#00AC00\",\"createTime\":\"2019-12-04 15:08:27.992\",\"id\":\"d70ff9a6618e40c78b1599b3690c4238\",\"bigValue\":10000.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"FTPUP\",\"color\":\"#FF0000\",\"createTime\":\"2019-12-04 15:08:27.993\",\"id\":\"29aed36e98f246cd86700ad5561d8ba6\",\"bigValue\":10.0},{\"projId\":111111111,\"minValue\":10.0,\"itemName\":\"FTPUP\",\"color\":\"#FF00FF\",\"createTime\":\"2019-12-04 15:08:27.995\",\"id\":\"6461f5e872fd4c5596a6c9ec2feccaae\",\"bigValue\":50.0},{\"projId\":111111111,\"minValue\":50.0,\"itemName\":\"FTPUP\",\"color\":\"#FFFF00\",\"createTime\":\"2019-12-04 15:08:27.997\",\"id\":\"3855396cb55747f5b2ca77220a187ca6\",\"bigValue\":100.0},{\"projId\":111111111,\"minValue\":100.0,\"itemName\":\"FTPUP\",\"color\":\"#0000FF\",\"createTime\":\"2019-12-04 15:08:27.999\",\"id\":\"d22d88c266bd4d5b8018467e1abd105a\",\"bigValue\":300.0},{\"projId\":111111111,\"minValue\":300.0,\"itemName\":\"FTPUP\",\"color\":\"#00FF00\",\"createTime\":\"2019-12-04 15:08:28.0\",\"id\":\"107360c64f35418791a251e9f078621e\",\"bigValue\":600.0},{\"projId\":111111111,\"minValue\":600.0,\"itemName\":\"FTPUP\",\"color\":\"#00AC00\",\"createTime\":\"2019-12-04 15:08:28.002\",\"id\":\"8283840e18ca4883bea731221a67892e\",\"bigValue\":10000.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#66ACFF\",\"createTime\":\"2019-12-04 15:08:28.041\",\"id\":\"1938e4567400407cb2ff2f38c1947ff5\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#00AC2B\",\"createTime\":\"2019-12-04 15:08:28.005\",\"id\":\"234773c737714e77af8130146f6132e3\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#FFFF00\",\"createTime\":\"2019-12-04 15:08:28.007\",\"id\":\"2b5114a1beb34809bcf0f38c9715498e\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#FF81AC\",\"createTime\":\"2019-12-04 15:08:28.023\",\"id\":\"7d0ba48eee9f48c49b1640d690ee3547\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#00FF00\",\"createTime\":\"2019-12-04 15:08:28.013\",\"id\":\"990887184e404aa29beaaddd0d5c98c6\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#FF0000\",\"createTime\":\"2019-12-04 15:08:28.004\",\"id\":\"99b6d93a97114ade9ff0b5fbc2473f65\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#FFAC56\",\"createTime\":\"2019-12-04 15:08:28.015\",\"id\":\"b0cf9d9f837c43d7a10abe8737b989e9\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#66812B\",\"createTime\":\"2019-12-04 15:08:28.021\",\"id\":\"bcae23c657c64e328a529970c6e74d69\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#6656AC\",\"createTime\":\"2019-12-04 15:08:28.02\",\"id\":\"bec0e7b33f6c409c8c8884b55be4c5a2\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#0000FF\",\"createTime\":\"2019-12-04 15:08:28.009\",\"id\":\"c5f789e278584b4aa6c18d8873701c06\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#66FFAC\",\"createTime\":\"2019-12-04 15:08:28.017\",\"id\":\"da9fddac251a474296863f43d9705b49\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#66002B\",\"createTime\":\"2019-12-04 15:08:28.039\",\"id\":\"ddcf1e67ab564cd8a9e088181d341f73\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#332B56\",\"createTime\":\"2019-12-04 15:08:28.027\",\"id\":\"e383b6c848b04ff39d9e554ddfdc6093\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#818181\",\"createTime\":\"2019-12-04 15:08:28.025\",\"id\":\"ee45d3e5e9284beb8c74dce448a30bb3\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"PCI\",\"color\":\"#FF2BFF\",\"createTime\":\"2019-12-04 15:08:28.011\",\"id\":\"f166422926f3427083cdc91334b67014\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":-140.0,\"itemName\":\"RSRP\",\"color\":\"#FF0000\",\"createTime\":\"2019-12-04 15:08:27.956\",\"id\":\"26e9a5ae0df54a12b81f25d956612d7a\",\"bigValue\":-110.0},{\"projId\":111111111,\"minValue\":-110.0,\"itemName\":\"RSRP\",\"color\":\"#FF00FF\",\"createTime\":\"2019-12-04 15:08:27.958\",\"id\":\"7a87012e7ce040b992a7b04a4a0946d0\",\"bigValue\":-100.0},{\"projId\":111111111,\"minValue\":-100.0,\"itemName\":\"RSRP\",\"color\":\"#FFFF00\",\"createTime\":\"2019-12-04 15:08:27.96\",\"id\":\"f1c1589439d44d3b965093873bf7157d\",\"bigValue\":-90.0},{\"projId\":111111111,\"minValue\":-90.0,\"itemName\":\"RSRP\",\"color\":\"#0000FF\",\"createTime\":\"2019-12-04 15:08:27.962\",\"id\":\"c2c6b779a16d493eb9b0c714e0f3aee5\",\"bigValue\":-80.0},{\"projId\":111111111,\"minValue\":-80.0,\"itemName\":\"RSRP\",\"color\":\"#33FF2B\",\"createTime\":\"2019-12-04 15:08:27.964\",\"id\":\"56daab985ca44c26ad21936d973f2b6b\",\"bigValue\":-70.0},{\"projId\":111111111,\"minValue\":-70.0,\"itemName\":\"RSRP\",\"color\":\"#00AC2B\",\"createTime\":\"2019-12-04 15:08:27.966\",\"id\":\"6b274d49f42c4010a32b4c88bf62e964\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":-30.0,\"itemName\":\"SINR\",\"color\":\"#FF0000\",\"createTime\":\"2019-12-04 15:08:27.968\",\"id\":\"a39d36edd633449898b26327fc59ec6d\",\"bigValue\":-5.0},{\"projId\":111111111,\"minValue\":-5.0,\"itemName\":\"SINR\",\"color\":\"#FF00FF\",\"createTime\":\"2019-12-04 15:08:27.97\",\"id\":\"c4e1428110cf46ec87e3aeba8544b101\",\"bigValue\":0.0},{\"projId\":111111111,\"minValue\":0.0,\"itemName\":\"SINR\",\"color\":\"#FFFF00\",\"createTime\":\"2019-12-04 15:08:27.972\",\"id\":\"47c6ae2ba7e04484afe7a674e9904686\",\"bigValue\":5.0},{\"projId\":111111111,\"minValue\":5.0,\"itemName\":\"SINR\",\"color\":\"#0000FF\",\"createTime\":\"2019-12-04 15:08:27.974\",\"id\":\"8c1c7276e3d14744a87d184c0a846f6a\",\"bigValue\":10.0},{\"projId\":111111111,\"minValue\":10.0,\"itemName\":\"SINR\",\"color\":\"#00FF00\",\"createTime\":\"2019-12-04 15:08:27.976\",\"id\":\"d35bbe87290e48778f96e92d7d2dc32b\",\"bigValue\":20.0},{\"projId\":111111111,\"minValue\":20.0,\"itemName\":\"SINR\",\"color\":\"#00AC00\",\"createTime\":\"2019-12-04 15:08:27.978\",\"id\":\"c366a753a5204e41903a9d86324b0b06\",\"bigValue\":50.0}],\"resultCode\":\"\",\"mMessage\":\"\",\"newTimestamp\":1579240609604,\"dataSource\":\"\",\"token\":\"\",\"msg\":\"查询成功\"}";
		JsonMsgUtil jsonMsgUtil = JSONObject.parseObject(msg, JsonMsgUtil.class);
		List<ConfigThreshold> configThresholds = JSONObject.parseArray(jsonMsgUtil.getObject().toString(),
				ConfigThreshold.class);
		List<RequestParamConfig> requestParamConfigs=new ArrayList<>();
		if (configThresholds != null && configThresholds.size()>0) {
			for (ConfigThreshold configThreshold : configThresholds) {
				String itemName = configThreshold.getItemName();
				if (("FTPDOWN").equals(itemName)) {
					itemName="DL";
				}
				else if (("FTPUP").equals(itemName)) {
					itemName="UL";
				}
				else if (("RSRP").equals(itemName)) {
					itemName="RSRP";
				}
				else if (("SINR").equals(itemName)) {
					itemName="SINR";
				}else {
					continue;
				}
				if (requestParamConfigs !=null && requestParamConfigs.size()>0) {
					boolean flag=false;
					for (RequestParamConfig requestParamConfig : requestParamConfigs) {
						String item = requestParamConfig.getItem();
						if (item.equals(itemName)) {
							flag=true;
							List<ItemModel> content = requestParamConfig.getContent();
							content.add(new ItemModel(configThreshold.getMinValue()+"", configThreshold.getBigValue()+"", configThreshold.getColor()));
							break;
						}
					}
					if (!flag) {
						RequestParamConfig requestParamConfig=new RequestParamConfig();
						requestParamConfig.setItem(itemName);
						List<ItemModel> content = new ArrayList<>();
						content.add(new ItemModel(configThreshold.getMinValue()+"", configThreshold.getBigValue()+"", configThreshold.getColor()));
						requestParamConfig.setContent(content);
						requestParamConfigs.add(requestParamConfig);
					}
				}else {
					RequestParamConfig requestParamConfig=new RequestParamConfig();
					requestParamConfig.setItem(itemName);
					List<ItemModel> content = new ArrayList<>();
					content.add(new ItemModel(configThreshold.getMinValue()+"", configThreshold.getBigValue()+"", configThreshold.getColor()));
					requestParamConfig.setContent(content);
					requestParamConfigs.add(requestParamConfig);
				}
			}//FTPDOWN  、 FTPUP 、RSRP、SINR
		}
		
		List<TestConfig> testConfigs=new ArrayList<>();
		if (requestParamConfigs !=null && requestParamConfigs.size()>0) {
			for (RequestParamConfig requestParamConfig : requestParamConfigs) {
				testConfigs.add(new TestConfig(1111111, new Date(), requestParamConfig.getItem(),  new ObjectMapper().writeValueAsString(requestParamConfig)));
			}
		}
		if (testConfigs !=null && testConfigs.size()>0) {
			testConfigService.insertBatch(testConfigs, 200);
		}
		return new JsonMsgUtil(true);
	}*/

}
