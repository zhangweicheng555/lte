package com.boot.kaizen.business.es.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.business.es.model.TaskModel2;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * ES测试控制层
 * 
 * @author weichengz
 * @date 2019年10月16日 上午10:26:26
 */
@Controller
@RequestMapping("/gis")
public class EsTestController {

	@Autowired
	private TransportClient transportClient;

	/**
	 * 
	 * @Description: lte单验 汇总计划每月/天单验统计
	 * @author weichengz
	 * @date 2019年2月1日 下午1:16:02
	 */
	@ResponseBody
	@PostMapping(value = "/indexIf")
	public JsonMsgUtil analyzeLteAllAndComplete(@RequestParam("index") String index,
			@RequestParam("type") String type) {
		System.out.println(Esutil.indexIfExists(index));
		System.out.println(Esutil.typeIfExists(index, type));
		return new JsonMsgUtil(true);
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@PostMapping(value = "/insert")
	public String insert(@RequestParam("index") String index,
			@RequestParam("type") String type,@RequestParam("jsonStr")  String jsonStr) {
		Map<String,Object> parseObject = JSON.parseObject(jsonStr, Map.class);
		Map<String, Object> map=new HashMap<String, Object>();
		String jsonId="";
		for (int i = 0; i < 80000; i++) {
			jsonId +=UUID.randomUUID().toString();
		}
		map.put("name", jsonId);
		map.put("list", Arrays.asList("1","2","3"));
		parseObject.put("tags", map);
		Esutil.insert(index, type, parseObject);
		return "success";
	}
	
	@ResponseBody
	@PostMapping(value = "/queryById")
	public Map<String, Object> queryById(@RequestParam("index") String index,
			@RequestParam("type") String type,@RequestParam("id")  String id) {
		return Esutil.queryById(index, type, id);
	}
	
	@ResponseBody
	@GetMapping(value = "/scrollQuery")
	public List<Map<String, Object>> scrollQuery(@RequestBody QueryParamData queryParamData) {
		return Esutil.scrollQuery(queryParamData);
	}
	
	@ResponseBody
	@PostMapping(value = "/queryList")
	public List<Map<String, Object>> queryList(@RequestBody QueryParamData queryParamData) {
		return Esutil.queryList(queryParamData);
	}
	
	@ResponseBody
	@PostMapping(value = "/queryPage")
	public QueryParamData queryPage(@RequestBody QueryParamData queryParamData) {
		return Esutil.queryPage(queryParamData);
	}
	
	@ResponseBody
	@PostMapping(value = "/updateById")
	public Object updateById(@RequestParam("index") String index,@RequestParam("id") String id,
			@RequestParam("type") String type,@RequestParam("jsonStr") String jsonStr) {
		@SuppressWarnings("unchecked")
		Map<String,Object> parseObject = JSON.parseObject(jsonStr, Map.class);
		Esutil.updateById(index, type, id, parseObject);
		return "success";
	}
	@ResponseBody
	@PostMapping(value = "/deleteIndex")
	public Object deleteIndex(@RequestParam("index") String index) {
		Boolean indexIfExists = Esutil.indexIfExists(index);
		if (indexIfExists) {
			Esutil.deleteIndex(index);
		}else {
			return "索引不存在";
		}
		return "success";
	}
	@ResponseBody
	@PostMapping(value = "/deleteByDocId")
	public Object deleteByDocId(@RequestParam("index") String index,@RequestParam("id") String id,
			@RequestParam("type") String type) {
		Esutil.deleteByDocId(index, type, id);
		return "success";
	}
	
	

	/**
	 * 导入时间戳数据
	 * 
	 * @Description: 导入数据的时候 需要指定索引跟类型 如果不存在的话 会自动创建
	 * @author weichengz
	 * @date 2019年10月16日 上午11:00:05
	 */
	@ResponseBody
	@PostMapping(value = "/importCsvDateTime")
	public Object importCsvDateTime() throws Exception {
		BufferedReader bufferedReader = null;
		File file = ResourceUtils.getFile("classpath:taskModel.csv");
		bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

		BulkRequest request = new BulkRequest();
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			String[] strArray = str.split(",");
			TaskModel2 taskModel = new TaskModel2(Integer.valueOf(strArray[0]), Integer.valueOf(strArray[1]),
					strArray[2], Integer.valueOf(strArray[3]), strArray[4], Long.valueOf(strArray[5].toString()),
					strArray[6], Long.valueOf(strArray[7].toString()), Integer.valueOf(strArray[8]),
					Integer.valueOf(strArray[9]), Integer.valueOf(strArray[10]), Integer.valueOf(strArray[11]),
					Integer.valueOf(strArray[12]));
			IndexRequest indexRequest = new IndexRequest("test", "test");
			System.out.println(JSONObject.toJSONString(taskModel));
			indexRequest.source(JSONObject.toJSONString(taskModel), XContentType.JSON);
			request.add(indexRequest);
		}
		BulkResponse bulkResponse = transportClient.bulk(request).get();
		if (bulkResponse.hasFailures()) {
			System.out.println("添加失败了");
		} else {
			System.out.println("添加成功了");
		}
		if (bufferedReader != null) {
			bufferedReader.close();
		}
		return "success";
	}
	
	
	
}
