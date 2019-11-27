package com.boot.kaizen.business.es.controller;

import java.io.BufferedReader;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram.Bucket;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
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
import com.boot.kaizen.business.es.model.sim.CommonModel;
import com.boot.kaizen.business.es.model.sim.GcModel;
import com.boot.kaizen.business.es.model.sim.GcModelList;
import com.boot.kaizen.business.es.model.sim.ResultModel;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.business.es.service.GcModelService;
import com.boot.kaizen.util.AEStest;
import com.boot.kaizen.util.HttpUtil;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.MyUtil;
import com.boot.kaizen.util.SpringUtil;
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
			gcModel.dealLocation();
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

	/**
	 * es地理坐标查询 矩形坐标查询
	 * 
	 * //bssw.lng,bsne.lat 108.37550228611097,22.825263470505483 //bsne.lng,bssw.lat
	 * 108.37695754103666,22.82323569809738
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/esLqQuery")
	public Object esLqQuery() throws Exception {
		GeoPoint topLeft = new GeoPoint(22.825263470505483d, 108.37550228611097d);
		GeoPoint bottomRight = new GeoPoint(22.82323569809738d, 108.37695754103666);

		Double splitMi = 200d; // 单位是米
		GeoDistanceQueryBuilder distanceQueryBuilder = QueryBuilders.geoDistanceQuery("location").point(topLeft)
				.distance(splitMi, DistanceUnit.METERS);
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		boolQueryBuilder.must(QueryBuilders.termQuery("lte_city_name.keyword", "南宁"));

		SearchResponse searchResponse = transportClient.prepareSearch("simgc").setTypes("simgc")
				.setQuery(distanceQueryBuilder).setPostFilter(boolQueryBuilder).get();
		SearchHits hits = searchResponse.getHits();
		for (SearchHit searchHit : hits) {
			Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
			System.out.println(JSONObject.toJSONString(sourceAsMap));
		}
		return "success";
	}

	/**
	 * 查询到指定点的距离 按升序的距离排序
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月22日 上午10:01:54
	 */
	@ResponseBody
	@RequestMapping(value = "/esQueryDiatance")
	public Object esQueryDiatance(@RequestParam("lat") Double lat, @RequestParam("lng") Double lng) throws Exception {
		GeoPoint topLeft = new GeoPoint(lat, lng);

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); // 100千米

		GeoDistanceQueryBuilder geoDistance = QueryBuilders.geoDistanceQuery("location").point(topLeft)
				.distance(10000, DistanceUnit.KILOMETERS).geoDistance(GeoDistance.ARC);

		GeoDistanceSortBuilder geoDistanceSortBuilder = SortBuilders.geoDistanceSort("location", topLeft)
				.unit(DistanceUnit.KILOMETERS).order(SortOrder.ASC);

		sourceBuilder.sort(geoDistanceSortBuilder);
		sourceBuilder.query(geoDistance);

		SearchRequest searchRequest = new SearchRequest("simgc");
		searchRequest.types("simgc");
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = transportClient.search(searchRequest).actionGet();
		SearchHits hits = searchResponse.getHits();
		for (SearchHit searchHit : hits) {
			Object obj = "";
			Object[] sortValues = searchHit.getSortValues();
			System.out.println(JSONObject.toJSONString(sortValues));
			if (sortValues != null && sortValues.length > 0) {
				obj = sortValues[0];
			}
			Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
			System.out.println(JSONObject.toJSONString(sourceAsMap) + "-----------" + obj);
		}
		return "success";
	}

	/**
	 * or查询 where (a=b and bc=c) or (d=c and f=m)
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月22日 上午10:01:54
	 */
	@ResponseBody
	@RequestMapping(value = "/esOrQuery")
	public Object esOrQuery() throws Exception {

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); // 100千米
		BoolQueryBuilder orBuilder = QueryBuilders.boolQuery();

		BoolQueryBuilder bqb1 = QueryBuilders.boolQuery();

		BoolQueryBuilder bqb2 = QueryBuilders.boolQuery();

		BoolQueryBuilder bqb3 = QueryBuilders.boolQuery();

		BoolQueryBuilder bqb4 = QueryBuilders.boolQuery();

		TermQueryBuilder toQuery = null;
		TermQueryBuilder fromQuery = null;

		toQuery = QueryBuilders.termQuery("lte_city_name.keyword", "湖南");
		fromQuery = QueryBuilders.termQuery("lte_ci.keyword", "10371011");
		bqb1.must(toQuery);
		bqb1.must(fromQuery);

		toQuery = QueryBuilders.termQuery("lte_city_name.keyword", "湖南");
		fromQuery = QueryBuilders.termQuery("lte_ci.keyword", "10371012");
		bqb2.must(toQuery);
		bqb2.must(fromQuery);

		toQuery = QueryBuilders.termQuery("lte_city_name.keyword", "湖南");
		fromQuery = QueryBuilders.termQuery("lte_ci.keyword", "10371021");
		bqb3.must(toQuery);
		bqb3.must(fromQuery);

		toQuery = QueryBuilders.termQuery("lte_city_name.keyword", "湖南");
		fromQuery = QueryBuilders.termQuery("lte_ecgi.keyword", "7749412");
		bqb4.must(toQuery);
		bqb4.must(fromQuery);

		orBuilder.should(bqb3);
		orBuilder.should(bqb2);
		orBuilder.should(bqb1);
		orBuilder.should(bqb4);

		sourceBuilder.postFilter(orBuilder);

		SearchRequest searchRequest = new SearchRequest("simgc");
		searchRequest.types("simgc");
		searchRequest.source(sourceBuilder);

		SearchResponse searchResponse = transportClient.search(searchRequest).actionGet();
		SearchHits hits = searchResponse.getHits();
		for (SearchHit searchHit : hits) {
			Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
			System.out.println(JSONObject.toJSONString(sourceAsMap));
		}
		return "success";
	}

	/**
	 * 根据条件查询之后的聚合分析 这个可用于下拉框的检索 某个下拉框需要某些信息的时候 可以这样检索内容
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月24日 下午12:43:06
	 */
	@RequestMapping(value = "/testAggr")
	public Object testAggr(@RequestBody QueryParamData queryParamData)
			throws InterruptedException, ExecutionException, ParseException {
		// 校验
		queryParamData.verificationIndexType();
		SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(queryParamData.getIndex())
				.setTypes(queryParamData.getType());
		// 查询条件
		BoolQueryBuilder boolQueryBuilder = Esutil.getBoolQueryBuilder(queryParamData);
		SearchResponse searchResponse = searchRequestBuilder.setQuery(boolQueryBuilder)
				.addAggregation(AggregationBuilders//
						.terms("city")//
						.field("lte_city_name.keyword")//
						.order(BucketOrder.key(true))//
						.size(10000))
				.get();
		Aggregations aggregations = searchResponse.getAggregations();
		StringTerms aggTerms = aggregations.get("city");

		List<org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket> buckets = aggTerms.getBuckets();

		for (org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket bucket : buckets) {
			String key = bucket.getKey().toString();
			System.out.println(key + "---" + bucket.getDocCount());
		}
		return "success";
	}

	/**
	 * 
	 * @Description: 拉取sim工参的问题解决了
	 * @author weichengz
	 * @throws Exception
	 * @date 2019年11月26日 下午2:32:03
	 */
	public static void main(String[] args) throws Exception {

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectName", "上海联通");
		paramMap.put("netId", "1");
		paramMap.put("projectLevel", "3");
		paramMap.put("provinceName", "上海");
		paramMap.put("operator", "联通");
		paramMap.put("fields", // 这个顺序 要和 实体类的顺序一致
				"lte_city_name,lte_net,lte_enodebid,lte_sector_id,lte_cell,lte_ci,lte_ecgi,lte_phycellid,lte_longitude2,lte_latitude2,lte_longitude,lte_latitude,lte_site_tall,lte_azimuth,lte_mechanical_downdip,lte_electronic_downdip,lte_total_downdip,lte_tac,lte_sys,lte_site_type,lte_earfcn,lte_derrick_type,lte_address,lte_scene,lte_grid,lte_firm");
		paramMap.put("limit", "10000000");

		String token = AEStest.encrypt(JSONObject.toJSONString(paramMap), "zcto8k3i*a2c6");

		Map<String, Object> param = new HashMap<>();
		param.put("askJson", token);

		String url = "http://61.132.73.61:8012/SIM/ihandle!getParamSync.action";
		String responseResult = HttpUtil.sendPostRequest(url, param);
		// System.out.println(responseResult);
		ResultModel resultModel = JSONObject.parseObject(responseResult, ResultModel.class);
		List<List<String>> datas = resultModel.getData();
		for (List<String> data : datas) {
			CommonModel commonModel = CommonModel.changeStrToObj(data);
			System.out.println(JSONObject.toJSONString(commonModel));
		}
	}
}
