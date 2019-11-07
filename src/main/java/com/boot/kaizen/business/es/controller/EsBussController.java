package com.boot.kaizen.business.es.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.business.es.service.Esutil;

/**
 * ES 业务控制层
 * 
 * @author weichengz
 * @date 2019年11月5日 上午11:44:28
 */
@Controller
@RequestMapping("/es/buss")
public class EsBussController {

	@Autowired
	private TransportClient transportClient;

	private static final Long AFTER_FIVE_MINUTE = 300000L;
	private static final Long BEFORE_FIVE_MINUTE = -300000L;

	/**
	 * 根据主得时间戳 查询信令 前后十分钟得数据 取著主经纬度得最大时间得前后五分钟
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月6日 下午4:11:42
	 */
	@ResponseBody
	@PostMapping(value = "/scrollMsgByTarTime")
	public Map<String, Object> scrollMsgByTarTime(@RequestBody QueryParamData queryParamData) {
		
		Map<String, Object> resultMap=new HashMap<>();
		resultMap.put("code", 0);
		resultMap.put("msg", "查询成功");
		resultMap.put("count", 0L);
		resultMap.put("data", new ArrayList<>());
		
		try {
			System.out.println(queryParamData.getTargetTime() + "-----------------------");
			// 根据这个时间抽取 上下5分钟得数据
			Long targetTime = queryParamData.getTargetTime();
			if (targetTime == null) {
				throw new IllegalArgumentException("未传入时间戳");
			}
			Long beforeFiveMinute = targetTime + BEFORE_FIVE_MINUTE;
			Long afterFiveMinute = targetTime + AFTER_FIVE_MINUTE;
			// 构建大于等于数据
			Map<String, Map<String, Long>> rangeMap = new HashMap<String, Map<String, Long>>();
			Map<String, Long> mapRange = new HashMap<>();
			mapRange.put("LTE", afterFiveMinute);
			mapRange.put("GTE", beforeFiveMinute);
			rangeMap.put("testTime", mapRange);
			// 查询出来了 主log得符合条件得id
			List<Map<String, Object>> logMainIds = Esutil.scrollQuery(queryParamData);

			List<Map<String, Object>> finalData = new ArrayList<>();// 查询返回得数据
			
			List<String> ids = new ArrayList<>();
			// 便利取信令
			if (logMainIds != null && logMainIds.size() > 0) {
				for (Map<String, Object> map : logMainIds) {
					String id = map.get("id").toString();
					ids.add(id);
				}
			}
			if (ids != null && ids.size() > 0) {
				for (String idModel : ids) {
					List<String> revelFields = Arrays.asList("mSingaType","mMessageType","mChannelType","mTimestamp");
					Map<String, Object> termMap = new HashMap<>();
					termMap.put("pid.keyword", idModel);
					QueryParamData queryParamData2 = new QueryParamData("logmsg", "logmsg", termMap, true, revelFields,1000);
					List<Map<String,Object>> msgData = Esutil.scrollQuery(queryParamData2);
					if (msgData !=null && msgData.size()>0) {
						for (Map<String, Object> map : msgData) {
							finalData.add(map);
						}
					}
					
				}
			}
			if (finalData !=null && finalData.size()>0) {
				resultMap.put("count", finalData.size());
				resultMap.put("data", finalData);
			}

		} catch (Exception e) {
			resultMap.put("code", 1);
		}
		
		
		return resultMap;
	}

}
