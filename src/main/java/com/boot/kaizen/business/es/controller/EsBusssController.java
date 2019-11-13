package com.boot.kaizen.business.es.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.business.es.model.OneButtonTest;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.business.es.service.IEsBussService;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.MyDateUtil;
import com.boot.kaizen.util.MyUtil;
import com.boot.kaizen.util.TableResultUtil;

/**
 * ES 业务控制层
 * 
 * @author weichengz
 * @date 2019年11月5日 上午11:44:28
 */
@Controller
@RequestMapping("/es/buss")
public class EsBusssController {

	@Autowired
	private IEsBussService esBussService;
	@Autowired
	private TransportClient transportClient;

	/**
	 * 
	 * @Description: 室外测试查询
	 * @author weichengz
	 * @date 2019年11月11日 上午11:21:32
	 */
	@ResponseBody
	@PostMapping(value = "/queryOutHome")
	public TableResultUtil analyzeLteAllAndComplete(@RequestBody QueryParamData queryParamData) {

		Map<String, Object> clearMapEmptyVal = MyUtil.clearMapEmptyVal(queryParamData.getTermMap());
		queryParamData.setTermMap(clearMapEmptyVal);// 精确查询
		queryParamData.handleFieldRange("beginTime", queryParamData.getBeginTime(), null);
		queryParamData.handleFieldRange("endTime", null, queryParamData.getEndTime());

		QueryParamData paramData = Esutil.queryPage(queryParamData);
		return new TableResultUtil(0L, "操作成功", paramData.getTotalNums(), paramData.getRows());
	}

	/**
	 * 地图页面加载全部的数据
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月12日 上午10:27:04
	 */
	@ResponseBody
	@PostMapping(value = "/queryMainLog")
	public List<Map<String, Object>> queryMainLog(@RequestBody QueryParamData queryParamData) {
		return esBussService.queryMainLog(queryParamData.getPid(), queryParamData);
	}

	/**
	 * 一键测试 添加
	 */
	@ResponseBody
	@PostMapping(value = "/oneButtonTestAdd")
	public JsonMsgUtil oneButtonTestAdd(OneButtonTest oneButtonTest) {
		oneButtonTest.setId(MyUtil.getUuid());
		Date date = MyDateUtil.stringToDate(oneButtonTest.getTestTime(), "yyyy-MM-dd HH:mm:ss");
		if (date != null) {
			oneButtonTest.setTestTimeQuery(date.getTime());
		}
		Esutil.insert("onebuttontest", "onebuttontest", JSONObject.toJSONString(oneButtonTest));
		return new JsonMsgUtil(true, "添加成功", "");
	}

	/**
	 * 根据es自己的主键删出
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月13日 上午10:50:44
	 */
	@ResponseBody
	@PostMapping(value = "/delete")
	public JsonMsgUtil delete(@RequestParam("index") String index, @RequestParam("type") String type,
			@RequestParam("ids") String ids) {
		if (StringUtils.isNoneBlank(index) && StringUtils.isNoneBlank(type) && StringUtils.isNoneBlank(ids)) {
			String[] idsArray = ids.trim().split(",");
			for (String id : idsArray) {
				Esutil.deleteByDocId(index, type, id);
			}
		} else {
			return new JsonMsgUtil(false, "索引、类型、文档ids不能为空", "");
		}
		return new JsonMsgUtil(true, "删出成功", "");
	}

	/**
	 * 
	 * @Description: 室外测试的删出 这个ids是 室外测试的id 非文档id
	 * @author weichengz
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @date 2019年11月13日 上午11:27:46
	 */

	@ResponseBody
	@PostMapping(value = "/deleteOutHome")
	public JsonMsgUtil deleteOutHome(@RequestParam("ids") String ids) throws InterruptedException, ExecutionException {
		if (StringUtils.isNoneBlank(ids)) {
			String[] idsArray = ids.trim().split(",");

			BulkRequestBuilder request = transportClient.prepareBulk();
			for (String id : idsArray) {
				// 查询出主log的文档id
				QueryParamData queryParamData = new QueryParamData("logouthome", "logouthome",
						MyUtil.createHashMap("id.keyword~" + id), Arrays.asList("pk"), 1, 1);
				List<Map<String, Object>> datas = Esutil.scrollQuery(queryParamData);
				if (datas != null && datas.size() > 0) {
					Map<String, Object> resultMap = datas.get(0);
					// 查询主日志
					QueryParamData queryParamDataMsg = new QueryParamData("logmain", "logmain",
							MyUtil.createHashMap("pid.keyword~" + id), Arrays.asList("pk"), 1000);
					List<Map<String, Object>> datasMsg = Esutil.scrollQuery(queryParamDataMsg);
					addDeleteRequest(request, datasMsg, "logmain", "logmain");

					// 查询大信息字段
					QueryParamData queryParamDataEvent = new QueryParamData("logmessage", "logmessage",
							MyUtil.createHashMap("ppid.keyword~" + id), Arrays.asList("pk"), 1000);
					List<Map<String, Object>> datasEvent = Esutil.scrollQuery(queryParamDataEvent);
					addDeleteRequest(request, datasEvent, "logmessage", "logmessage");

					// 其他
					QueryParamData queryParamDataOther = new QueryParamData("logother", "logother",
							MyUtil.createHashMap("ppid.keyword~" + id), Arrays.asList("pk"), 1000);
					List<Map<String, Object>> datasOther = Esutil.scrollQuery(queryParamDataOther);
					addDeleteRequest(request, datasOther, "logother", "logother");
					// 删出室外测试
					Esutil.deleteByDocId("logouthome", "logouthome", resultMap.get("pk").toString());
				}
			}
			request.get();// 执行
		} else {
			return new JsonMsgUtil(false, "索引、类型、文档ids不能为空", "");
		}
		return new JsonMsgUtil(true, "删出成功", "");
	}

	/*
	 * @ResponseBody
	 * 
	 * @PostMapping(value = "/deleteOutHome") public JsonMsgUtil
	 * deleteOutHome(@RequestParam("ids") String ids) throws InterruptedException,
	 * ExecutionException { if (StringUtils.isNoneBlank(ids)) { String[] idsArray =
	 * ids.trim().split(","); BulkRequest request = new BulkRequest(); // 批量删出 for
	 * (String id : idsArray) { // 查询出主log的文档id QueryParamData queryParamData = new
	 * QueryParamData("logouthome", "logouthome", MyUtil.createHashMap("id.keyword~"
	 * + id), Arrays.asList("pk"), 1, 1); List<Map<String, Object>> datas =
	 * Esutil.queryList(queryParamData); if (datas != null && datas.size() > 0) {
	 * Map<String, Object> resultMap = datas.get(0); // 查询主日志 QueryParamData
	 * queryParamDataMsg = new QueryParamData("logmain", "logmain",
	 * MyUtil.createHashMap("pid.keyword~" + id), Arrays.asList("pk"), 1000);
	 * List<Map<String, Object>> datasMsg = Esutil.queryList(queryParamDataMsg);
	 * addDeleteRequest(request, datasMsg, "logmain", "logmain");
	 * 
	 * // 查询大信息字段 QueryParamData queryParamDataEvent = new
	 * QueryParamData("logmessage", "logmessage",
	 * MyUtil.createHashMap("ppid.keyword~" + id), Arrays.asList("pk"), 1000);
	 * List<Map<String, Object>> datasEvent = Esutil.queryList(queryParamDataEvent);
	 * addDeleteRequest(request, datasEvent, "logmessage", "logmessage");
	 * 
	 * // 其他 QueryParamData queryParamDataOther = new QueryParamData("logother",
	 * "logother", MyUtil.createHashMap("ppid.keyword~" + id), Arrays.asList("pk"),
	 * 1000); List<Map<String, Object>> datasOther =
	 * Esutil.queryList(queryParamDataOther); addDeleteRequest(request, datasOther,
	 * "logother", "logother"); // 删出室外测试 Esutil.deleteByDocId("logouthome",
	 * "logouthome", resultMap.get("pk").toString()); } }
	 * transportClient.bulk(request); } else { return new JsonMsgUtil(false,
	 * "索引、类型、文档ids不能为空", ""); } return new JsonMsgUtil(true, "删出成功", ""); }
	 */
	/**
	 * 添加删出请求
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月13日 下午12:01:45
	 */
	private void addDeleteRequest(BulkRequestBuilder request, List<Map<String, Object>> datasMsg, String index,
			String type) {
		if (datasMsg != null && datasMsg.size() > 0) {
			for (Map<String, Object> map : datasMsg) {
				request.add(transportClient.prepareDelete(index, type, map.get("pk").toString()).request());
			}
		}
	}

	/**
	 *
	 * @Description: 一键测试列表查询
	 * @author weichengz
	 * @date 2019年11月13日 上午9:59:12
	 */
	@ResponseBody
	@PostMapping(value = "/queryOneButtonTest")
	public TableResultUtil queryOneButtonTest(@RequestBody QueryParamData queryParamData) {
		Map<String, Object> clearMapEmptyVal = MyUtil.clearMapEmptyVal(queryParamData.getTermMap());
		queryParamData.setTermMap(clearMapEmptyVal);// 精确查询
		queryParamData.handleFieldRange("testTimeQuery", queryParamData.getBeginTime(), null);
		queryParamData.handleFieldRange("testTimeQuery", null, queryParamData.getEndTime());
		QueryParamData paramData = Esutil.queryPage(queryParamData);
		return new TableResultUtil(0L, "操作成功", paramData.getTotalNums(), paramData.getRows());
	}

	/**
	 * 
	 * @Description: 一键测试根据条件非分页查询
	 * @author weichengz
	 * @date 2019年11月13日 下午4:38:33
	 */
	@ResponseBody
	@PostMapping(value = "/queryOneButtonTestNoPage")
	public List<Map<String, Object>> queryOneButtonTestNoPage(@RequestBody QueryParamData queryParamData) {
		Map<String, Object> clearMapEmptyVal = MyUtil.clearMapEmptyVal(queryParamData.getTermMap());
		queryParamData.setTermMap(clearMapEmptyVal);// 精确查询
		queryParamData.handleFieldRange("testTimeQuery", queryParamData.getBeginTime(), null);
		queryParamData.handleFieldRange("testTimeQuery", null, queryParamData.getEndTime());
		return Esutil.scrollQuery(queryParamData);
	}

}
