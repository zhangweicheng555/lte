package com.boot.kaizen.business.es.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.business.es.model.MainLogModel;
import com.boot.kaizen.business.es.model.OneButtonTest;
import com.boot.kaizen.business.es.model.OtherLogModel;
import com.boot.kaizen.business.es.model.OutHomeLogModel;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.business.es.model.logModel.MSignaBean;
import com.boot.kaizen.business.es.model.logModel.SignalDataBean;
import com.boot.kaizen.business.es.model.sim.GcModel;
import com.boot.kaizen.business.es.model.sim.GcModelList;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.business.es.service.GcModelService;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.MyUtil;
import com.boot.kaizen.util.TableResultUtil;

/**
 * ES控制层
 * 
 * @author weichengz
 * @date 2019年11月5日 上午11:44:28
 */
@Controller
@RequestMapping("/es")
public class EsController {

	@Autowired
	private TransportClient transportClient;

	/**
	 * 查询索引是不是存在
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月5日 上午11:43:29
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
	public String insert(@RequestParam("index") String index, @RequestParam("type") String type,
			@RequestParam("jsonStr") String jsonStr) {
		Map<String, Object> parseObject = JSON.parseObject(jsonStr, Map.class);
		Map<String, Object> map = new HashMap<String, Object>();
		String jsonId = "";
		for (int i = 0; i < 80000; i++) {
			jsonId += UUID.randomUUID().toString();
		}
		map.put("name", jsonId);
		map.put("list", Arrays.asList("1", "2", "3"));
		parseObject.put("tags", map);
		Esutil.insert(index, type, parseObject);
		return "success";
	}

	@ResponseBody
	@PostMapping(value = "/queryById")
	public Map<String, Object> queryById(@RequestParam("index") String index, @RequestParam("type") String type,
			@RequestParam("id") String id) {
		return Esutil.queryById(index, type, id);
	}

	@ResponseBody
	@PostMapping(value = "/scrollQuery")
	public List<Map<String, Object>> scrollQuery(@RequestBody QueryParamData queryParamData) {
		return Esutil.scrollQuery(queryParamData);
	}

	@ResponseBody
	@PostMapping(value = "/queryList")
	public List<Map<String, Object>> queryList(@RequestBody QueryParamData queryParamData) {
		return Esutil.queryList(queryParamData);
	}

	@ResponseBody
	@PostMapping(value = "/testCache")
	public String testCache(@RequestParam(value = "id") String id) {
		Esutil esutil = new Esutil();
		return esutil.testCache(id);
	}

	@ResponseBody
	@PostMapping(value = "/queryPage")
	public QueryParamData queryPage(@RequestBody QueryParamData queryParamData) {
		return Esutil.queryPage(queryParamData);
	}

	@ResponseBody
	@PostMapping(value = "/queryPageMsg")
	public TableResultUtil queryPageMsg(@RequestBody QueryParamData queryParamData) {

		QueryParamData queryPage = Esutil.queryPage(queryParamData);
		if (queryPage != null) {
			return new TableResultUtil(0L, "操作成功", queryPage.getTotalNums(), queryPage.getRows());
		}
		return new TableResultUtil(1L, "查询失败", 0L, new ArrayList<>());
	}

	@ResponseBody
	@PostMapping(value = "/updateById")
	public Object updateById(@RequestParam("index") String index, @RequestParam("id") String id,
			@RequestParam("type") String type, @RequestParam("jsonStr") String jsonStr) {
		@SuppressWarnings("unchecked")
		Map<String, Object> parseObject = JSON.parseObject(jsonStr, Map.class);
		Esutil.updateById(index, type, id, parseObject);
		return "success";
	}

	@ResponseBody
	@PostMapping(value = "/deleteIndex")
	public Object deleteIndex(@RequestParam("index") String index) {
		Boolean indexIfExists = Esutil.indexIfExists(index);
		if (indexIfExists) {
			Esutil.deleteIndex(index);
		} else {
			return "索引不存在";
		}
		return "success";
	}

	@ResponseBody
	@PostMapping(value = "/deleteByDocId")
	public Object deleteByDocId(@RequestParam("index") String index, @RequestParam("id") String id,
			@RequestParam("type") String type) {
		Esutil.deleteByDocId(index, type, id);
		return "success";
	}

	/**
	 * 
	 * @Description: 日志文件的内部导入
	 * @author weichengz
	 * @date 2019年11月5日 上午11:45:35
	 */
	@ResponseBody
	@RequestMapping(value = "importModel")
	public Object importCsvDateTime(@RequestParam("fileName") String fileName) throws Exception {
		String outHomeTestId = MyUtil.getUuid();// 室外测试列表的id 后续所有的操作 都会以这个为索引主键

		BufferedReader bufferedReader = null;
		File file = ResourceUtils.getFile("classpath:" + fileName + "");
		bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		BulkRequest request = new BulkRequest();
		String str = null;

		String beginTime = null;// 日志测试的开始事件
		int num = 0;
		SignalDataBean signalDataBeanFinal = null;// 记录最后一跳记录 室外测试

		while ((str = bufferedReader.readLine()) != null) {

			String id = MyUtil.getUuid();
			SignalDataBean signalDataBean = JSONObject.parseObject(str, SignalDataBean.class);
			if (num == 0) {// 用于记录第一条记录
				beginTime = signalDataBean.getTestTime();
				num++;
			}

			IndexRequest indexRequestMain = new IndexRequest("logmain", "logmain", id);
			signalDataBean.setPid(outHomeTestId);// 室外测试的主键id
			signalDataBean.setId(id);/** 赋值 注意这个很重要 一定要先赋值 */

			// 信令 大字段信息表得 存储
			handleMsgInfoField(signalDataBean, request);

			// 主表信息转移
			MainLogModel mainLogModel = new MainLogModel(signalDataBean);
			indexRequestMain.source(JSONObject.toJSONString(mainLogModel), XContentType.JSON);
			request.add(indexRequestMain);

			// 其他信息直接存入 不做处理
			IndexRequest indexRequestOther = new IndexRequest("logother", "logother");
			OtherLogModel otherLogModel = new OtherLogModel(signalDataBean);

			indexRequestOther.source(JSONObject.toJSONString(otherLogModel), XContentType.JSON);
			request.add(indexRequestOther);

			signalDataBeanFinal = signalDataBean;// 记录最后一条记录
		}

		// 处理最后一条记录的 就是 室外测试 列表
		handleOutHomeTest(signalDataBeanFinal, request, beginTime, file);

		/** 分批的添加进去 */
		BulkResponse bulkResponse = transportClient.bulk(request).get();
		if (bufferedReader != null) {
			bufferedReader.close();
		}
		return "success";
	}

	@Autowired
	private GcModelService gcModelService;
	
	/**
	 * 
	 * @Description: 日志文件的内部导入
	 * @author weichengz
	 * @date 2019年11月5日 上午11:45:35
	 */
	@ResponseBody
	@RequestMapping(value = "importLqModel")
	public Object importLqModel() throws Exception {
		BulkRequest request = new BulkRequest();
		List<GcModel> find = gcModelService.find();
		for (GcModel gcModel : find) {
			IndexRequest indexRequestOther = new IndexRequest("simgc", "simgc");
			indexRequestOther.source(JSONObject.toJSONString(gcModel), XContentType.JSON);
			request.add(indexRequestOther);
		}

		/** 分批的添加进去 */
		BulkResponse bulkResponse = transportClient.bulk(request).get();
		System.out.println("---------------------------");
		System.out.println(find.size());
		return "success";
	}


	/**
	 * 一键测试数据的导入
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月13日 下午3:02:19
	 */
	@ResponseBody
	@RequestMapping(value = "importOneBtnTestModel")
	public Object importOneBtnTestModel() throws Exception {
		File file = ResourceUtils.getFile("classpath:OneKeyTestRecord.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		BulkRequest request = new BulkRequest();
		String str = null;

		while ((str = bufferedReader.readLine()) != null) {
			String id = MyUtil.getUuid();

			JSONObject jsonObject = JSONObject.parseObject(str);
			OneButtonTest oneButtonTest = new OneButtonTest(id, jsonObject.getString("operatorName"),
					jsonObject.getString("netType"), jsonObject.getString("city"), jsonObject.getString("addr"), "",
					jsonObject.getString("phoneType"), "", "", jsonObject.getString("timeStamp"),
					jsonObject.getString("lat"), jsonObject.getString("lng"), jsonObject.getString("addr"), "", "", "",
					"", "", "", "", "", "", "", "", "");
			IndexRequest indexRequestMain = new IndexRequest("onebuttontest", "onebuttontest", id);
			indexRequestMain.source(JSONObject.toJSONString(oneButtonTest), XContentType.JSON);
			request.add(indexRequestMain);
		}
		/** 分批的添加进去 */
		transportClient.bulk(request).get();
		return "success";
	}

	/**
	 *
	 * @Description: 室外测试的列表添加记录 处理最后一条记录的
	 * @author weichengz
	 * @date 2019年11月11日 上午10:25:31
	 */
	private void handleOutHomeTest(SignalDataBean signalDataBeanFinal, BulkRequest request, String beginTime,
			File file) {
		IndexRequest indexRequestOutHome = new IndexRequest("logouthome", "logouthome", signalDataBeanFinal.getPid());
		OutHomeLogModel outHomeLogModel = new OutHomeLogModel(signalDataBeanFinal, beginTime);
		outHomeLogModel.setFileName(file.getName());
		outHomeLogModel.setFileUpTime(new Date().getTime());// 文件上传日期
		outHomeLogModel.setFilePath(file.getAbsolutePath());
		indexRequestOutHome.source(JSONObject.toJSONString(outHomeLogModel), XContentType.JSON);
		request.add(indexRequestOutHome);
	}

	/**
	 * 处理信令里面得大字段信息存储
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月7日 下午4:23:50
	 */
	private void handleMsgInfoField(SignalDataBean signalDataBean, BulkRequest request) {
		ArrayList<MSignaBean> signaBeans = signalDataBean.getmSignaBean();
		for (MSignaBean mSignaBean : signaBeans) {
			String id = UUID.randomUUID().toString().replace("-", "");
			String msg = mSignaBean.getmMeaasge();
			IndexRequest indexRequestEvent = new IndexRequest("logmessage", "logmessage");
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("id", id); // 单条信令得id
			paramMap.put("ppid", signalDataBean.getPid()); // 室外测试列表的id
			paramMap.put("pid", signalDataBean.getId()); // 主log得id
			paramMap.put("message", msg);// 信令得信息 大字段信息
			paramMap.put("latitude", signalDataBean.getLatitude()); // 主 经度
			paramMap.put("longitude", signalDataBean.getLongitude()); // 主纬度
			mSignaBean.setmMeaasge(id);
			indexRequestEvent.source(JSONObject.toJSONString(paramMap), XContentType.JSON);
			request.add(indexRequestEvent);
		}
	}

}
