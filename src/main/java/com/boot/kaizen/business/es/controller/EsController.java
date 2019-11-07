package com.boot.kaizen.business.es.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.business.es.model.EventLogModel;
import com.boot.kaizen.business.es.model.MainLogModel;
import com.boot.kaizen.business.es.model.MsgLogModel;
import com.boot.kaizen.business.es.model.OtherLogModel;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.business.es.model.logModel.MSignaBean;
import com.boot.kaizen.business.es.model.logModel.MSignaEventBean;
import com.boot.kaizen.business.es.model.logModel.SignalDataBean;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.util.JsonMsgUtil;
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
	public Object importCsvDateTime() throws Exception {
		BufferedReader bufferedReader = null;
		File file = ResourceUtils.getFile("classpath:123.txt");
		bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		BulkRequest request = new BulkRequest();
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {

			String id = UUID.randomUUID().toString().replace("-", "");
			IndexRequest indexRequestMain = new IndexRequest("logmain", "logmain", id);
			SignalDataBean signalDataBean = JSONObject.parseObject(str, SignalDataBean.class);
			signalDataBean.setId(id);/** 赋值 注意这个很重要 一定要先赋值 */

			// 主表信息转移
			MainLogModel mainLogModel = new MainLogModel(signalDataBean);
			indexRequestMain.source(JSONObject.toJSONString(mainLogModel), XContentType.JSON);
			request.add(indexRequestMain);

			/*// 事件表表转移
			EventLogModel eventLogModel = new EventLogModel(signalDataBean);
			handleEventTOrequest(eventLogModel, request);

			// 信令表转移
			MsgLogModel msgLogModel = new MsgLogModel(signalDataBean);
			handleMsgTOrequest(msgLogModel, request);*/

			// 其他信息直接存入 不做处理
			IndexRequest indexRequestOther = new IndexRequest("logother", "logother");
			OtherLogModel otherLogModel = new OtherLogModel(signalDataBean);
			indexRequestOther.source(JSONObject.toJSONString(otherLogModel), XContentType.JSON);
			request.add(indexRequestOther);
		}

		/** 分批的添加进去 */
		BulkResponse bulkResponse = transportClient.bulk(request).get();
		if (bufferedReader != null) {
			bufferedReader.close();
		}
		return "success";
	}

	/**
	 * 
	 * @Description: 处理 信令表的请求
	 * @author weichengz
	 * @date 2019年11月5日 下午2:33:18
	 */
	private void handleMsgTOrequest(MsgLogModel msgLogModel, BulkRequest request) {
		if (msgLogModel != null) {
			ArrayList<MSignaBean> mSignaEventBeans = msgLogModel.getmSignaBean();
			if (mSignaEventBeans != null && mSignaEventBeans.size() > 0) {
				for (MSignaBean mSignaEventBean : mSignaEventBeans) {
					IndexRequest indexRequestMsg = new IndexRequest("logmsg", "logmsg");
					mSignaEventBean.setPid(msgLogModel.getPid());
					indexRequestMsg.source(JSONObject.toJSONString(mSignaEventBean), XContentType.JSON);
					request.add(indexRequestMsg);
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 处理事件的表请求
	 * @author weichengz
	 * @date 2019年11月5日 下午2:20:48
	 */
	private void handleEventTOrequest(EventLogModel eventLogModel, BulkRequest request) {
		if (eventLogModel != null) {
			ArrayList<MSignaEventBean> mSignaEventBeans = eventLogModel.getmSignaEventBean();
			if (mSignaEventBeans != null && mSignaEventBeans.size() > 0) {
				for (MSignaEventBean mSignaEventBean : mSignaEventBeans) {
					IndexRequest indexRequestEvent = new IndexRequest("logevent", "logevent");
					mSignaEventBean.setPid(eventLogModel.getPid());
					indexRequestEvent.source(JSONObject.toJSONString(mSignaEventBean), XContentType.JSON);
					request.add(indexRequestEvent);
				}
			}
		}
	}
}
