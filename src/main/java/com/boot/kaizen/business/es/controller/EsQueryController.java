package com.boot.kaizen.business.es.controller;

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
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.util.SpringUtil;

@RestController
@RequestMapping(value = "/es")
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

/*	@RequestMapping(value = "/queryById")
	public Object queryById(@RequestBody QueryParamData queryParamData) throws InterruptedException, ExecutionException {
		return Esutil.queryList("dbtest2index", "dbtest2index", queryParamData);
	}*/

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
	public Object deleteIndex() throws InterruptedException, ExecutionException {
		IndicesExistsRequest request = new IndicesExistsRequest("zwc");
		IndicesExistsResponse existsResponse = transportClient.admin().indices().exists(request).actionGet();
		if (existsResponse.isExists()) {
			transportClient.admin().indices().delete(new DeleteIndexRequest("zwc")).actionGet();
		}
		return existsResponse;
	}
/*
	@RequestMapping(value = "importModel")
	public Object importCsvDateTime() throws Exception {
		InputStream inputStream = null;
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
			IndexRequest indexRequest = new IndexRequest("zwp", "zwp");

			int x = (int) (Math.random() * 10);
			int y = (int) (Math.random() * 10);
			taskModel.setTags(Arrays.asList(x + "", y + ""));
			System.out.println(JSONObject.toJSONString(taskModel));
			indexRequest.source(JSONObject.toJSONString(taskModel), XContentType.JSON);
			request.add(indexRequest);
		}

		BulkResponse bulkResponse = transportClient.bulk(request).get();

		if (inputStream != null) {
			inputStream.close();
		}
		if (bufferedReader != null) {
			bufferedReader.close();
		}
		return "success";
	}*/

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

}
