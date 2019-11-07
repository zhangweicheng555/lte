package com.boot.kaizen.business.es.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.business.es.model.logModel.SignalDataBean;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.util.SpringUtil;

@Controller
@RestController
@RequestMapping(value = "/es/query")
public class EsQueryController {

	@Autowired
	private TransportClient transportClient;

	@RequestMapping(value = "/create")
	public Object test1() throws InterruptedException, ExecutionException {
		Map<String, Object> source = new HashMap<>();
		source.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
		source.put("name", "张丽丽");
		source.put("address", "山东潍坊");
		source.put("age", 16);

		Esutil.insert("zwc", "zwc", source);
		return "success";
	}

	@RequestMapping(value = "/queryList")
	public Object queryList(@RequestBody QueryParamData queryParamData)
			throws InterruptedException, ExecutionException {
		return Esutil.queryList(queryParamData);
	}

	@RequestMapping(value = "/update")
	public Object update() throws InterruptedException, ExecutionException {
		Map<String, Object> source = new HashMap<>();
		source.put("id1", "id12121212");
		source.put("address", "gggg");
		Esutil.updateById("zwc", "zwc", "Wi833WkB6py9cyoKB6wl", source);
		return "cuccess";
	}

	@RequestMapping(value = "/deleteById")
	public Object deleteById() throws InterruptedException, ExecutionException {
		DeleteRequest request = new DeleteRequest("zwc", "zwc", "XC853WkB6py9cyoKTqyz");
		transportClient.delete(request);
		return "cuccess";
	}

	@RequestMapping(value = "/deleteIndex")
	public Object deleteIndex(@RequestParam("index") String index) throws InterruptedException, ExecutionException {
		IndicesExistsRequest request = new IndicesExistsRequest(index);
		IndicesExistsResponse existsResponse = transportClient.admin().indices().exists(request).actionGet();
		if (existsResponse.isExists()) {
			transportClient.admin().indices().delete(new DeleteIndexRequest(index)).actionGet();
		}
		return existsResponse;
	}

	

	/**
	 * 
	 * 2019-01-22 02:00:00---109 2019-01-22 02:00:00-----109------1----109
	 * 
	 * @Description: 以时间分组之后在以别的分组/其他的操作类推 ["测试1","测试2"] 注意这种格式的是也可以进行聚合分析的
	 * @author weichengz
	 * @date 2019年4月5日 下午8:48:11
	 */
	@RequestMapping(value = "/aggTwo")
	public Object aggTwo() throws InterruptedException, ExecutionException, ParseException {
		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		TermQueryBuilder termQuery = QueryBuilders.termQuery("state", "1");
		SearchResponse searchResponse = transportClient.prepareSearch("zwp").setTypes("zwp")
				// .setQuery(termQuery)
				.addAggregation(AggregationBuilders//
						.dateHistogram("dateAgg")//
						.field("createdate")//
						.dateHistogramInterval(DateHistogramInterval.HOUR)//
						.order(BucketOrder.key(true))//
						.subAggregation(AggregationBuilders.terms("state").field("state").order(BucketOrder.key(true)))//
				).get();
		Histogram timeAgg = searchResponse.getAggregations().get("dateAgg");
		System.out.println("----聚合结束" + timeAgg.getBuckets().size());
		for (Bucket bucket : timeAgg.getBuckets()) {
			String key = bucket.getKey().toString();
			key = df1.format(df2.parse(key));
			System.out.println(key + "---" + bucket.getDocCount());
			LongTerms longTerms = bucket.getAggregations().get("state");
			List<org.elasticsearch.search.aggregations.bucket.terms.LongTerms.Bucket> buckets = longTerms.getBuckets();
			for (org.elasticsearch.search.aggregations.bucket.terms.LongTerms.Bucket bucket2 : buckets) {
				System.out.println(key + "-----" + bucket.getDocCount() + "------" + bucket2.getKey() + "----"
						+ bucket2.getDocCount());
			}
		}
		return "success";
	}

	/**
	 * 日志文件的内部导入
	* @Description: TODO
	* @author weichengz
	* @date 2019年11月5日 上午11:42:36
	 */
	@RequestMapping(value = "importModel")
	public @ResponseBody Object importCsvDateTime() throws Exception {
		BufferedReader bufferedReader = null;
		File file = ResourceUtils.getFile("classpath:123.txt");
		bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

		BulkRequest request = new BulkRequest();
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			String id = UUID.randomUUID().toString().replace("-", "") + "_zwc";
			IndexRequest indexRequest = new IndexRequest("es", "es", id);
			SignalDataBean signalDataBean = JSONObject.parseObject(str, SignalDataBean.class);
			signalDataBean.setId(id);
			indexRequest.source(JSONObject.toJSONString(signalDataBean), XContentType.JSON);
			request.add(indexRequest);
		}

		BulkResponse bulkResponse = transportClient.bulk(request).get();
		if (bufferedReader != null) {
			bufferedReader.close();
		}
		return "success";
	}
	
}
