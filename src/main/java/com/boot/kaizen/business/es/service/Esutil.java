package com.boot.kaizen.business.es.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.cache.annotation.Cacheable;

import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.util.SpringUtil;

/**
 * elasticsearch 工具类
 * 
 * @author weichengz
 * @date 2019年4月2日 下午4:35:23
 */
public class Esutil {

	/**
	 * 添加
	 * 
	 * @Description: 根据索引/类型/数据 添加数据
	 * @author weichengz
	 * @date 2019年4月2日 下午4:52:24
	 */
	public static void insert(String index, String type, Map<String, Object> dataMap) {
		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
		IndexRequest request = new IndexRequest(index, type);
		request.source(dataMap);
		try {
			transportClient.index(request).get();
		} catch (Exception e) {
			throw new IllegalArgumentException("添加失败：" + e.getMessage());
		}
	}

	/**
	 * 添加
	 * 
	 * @Description: 对象json串的形式添加
	 * @author weichengz
	 * @date 2019年10月17日 下午5:15:45
	 */
	public static void insert(String index, String type, String jsonData) {
		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
		IndexRequest request = new IndexRequest(index, type);
		request.source(jsonData, XContentType.JSON);
		try {
			transportClient.index(request).get();
		} catch (Exception e) {
			throw new IllegalArgumentException("添加失败：" + e.getMessage());
		}
	}

	/**
	 * 
	 * @Description: 根据文档的id 批量查询数据
	 * @author weichengz
	 * @date 2019年11月14日 上午11:40:58
	 */
	public static List<String> queryBatchByIds(String index, String type, String[] ids) {
		List<String> jsonResult = new ArrayList<>();
		try {
			TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
			MultiGetRequestBuilder multiGetRequestBuilder = transportClient.prepareMultiGet();
			multiGetRequestBuilder.add(index, type, ids);
			/*
			 * for (String id : jsonResult) { multiGetRequestBuilder =
			 * multiGetRequestBuilder.add(index, type, id); }
			 */
			MultiGetResponse multiGetItemResponses = multiGetRequestBuilder.get();
			for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
				GetResponse response = itemResponse.getResponse();
				if (response.isExists()) {
					String json = response.getSourceAsString();
					jsonResult.add(json);
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("根据id批量查询异常：" + e.getMessage());
		}
		return jsonResult;
	}

	@Cacheable(value = "queryPeopleNumByTimeRange")
	public String testCache(String pid) {
		System.out.println("--------------------------");
		return "success";
	}

	/**
	 * 滚动查询 注意在一次性查询很多数据的时候 采用这种方式
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月17日 下午2:34:27
	 */
	public static List<Map<String, Object>> scrollQuery(QueryParamData queryParamData) {
		TransportClient client = SpringUtil.getBean(TransportClient.class);
		// 校验
		queryParamData.verificationIndexType();
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(queryParamData.getIndex())
				.setTypes(queryParamData.getType());
		// 查询条件
		SearchSourceBuilder sourceBuilder = createSourceBuilder(queryParamData);

		searchRequestBuilder.setSource(sourceBuilder);
		// 设置 search context 维护1分钟的有效期
		searchRequestBuilder.setScroll(TimeValue.timeValueMinutes(1));
		List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();
		// 获得首次的查询结果
		SearchResponse scrollResp = searchRequestBuilder.get();
		// 处理首次查询的结果
		SearchHits hits = scrollResp.getHits();
		if (hits != null) {
			SearchHit[] hits2 = hits.getHits();
			if (hits2 != null && hits2.length != 0) {
				for (SearchHit searchHit : scrollResp.getHits().getHits()) {
					if (queryParamData.isRevelPk()) {
						searchHit.getSourceAsMap().put("pk", searchHit.getId());
					}
					sourceList.add(searchHit.getSourceAsMap());
				}
			}
		}

		do {
			// 将scorllId循环传递
			scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(TimeValue.timeValueMinutes(1))
					.execute().actionGet();
			for (SearchHit searchHit : scrollResp.getHits().getHits()) {
				if (queryParamData.isRevelPk()) {
					searchHit.getSourceAsMap().put("pk", searchHit.getId());
				}
				sourceList.add(searchHit.getSourceAsMap());
			}
			// 当searchHits的数组为空的时候结束循环，至此数据全部读取完毕
		} while (scrollResp.getHits().getHits().length != 0);
		// 删除scroll
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
		clearScrollRequest.addScrollId(scrollResp.getScrollId());
		client.clearScroll(clearScrollRequest).actionGet();
		return sourceList;
	}

	/**
	 * 根据查询的参数 拼接查询的查询条件语句
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月17日 下午3:30:36
	 */
	public static SearchSourceBuilder createSourceBuilder(QueryParamData queryParamData) {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		BoolQueryBuilder boolFilterBuilder = QueryBuilders.boolQuery();
		// 处理参数问题
		if (queryParamData != null) {

			Map<String, List<Object>> orMap = queryParamData.getOrMap();
			if (orMap != null && !orMap.isEmpty()) {// or查询的时候 仅仅支持 termMap联合使用 其余的不支持
				TermQueryBuilder orTermQuery = null;
				for (Entry<String, List<Object>> entry : orMap.entrySet()) {
					List<Object> kvs = entry.getValue();
					if (kvs != null && kvs.size() > 0) {
						for (Object object : kvs) {
							BoolQueryBuilder bb = QueryBuilders.boolQuery();
							orTermQuery = QueryBuilders.termQuery(entry.getKey(), object);
							bb.must(orTermQuery);
							// 处理term条件
							Map<String, Object> termMapModel = queryParamData.getTermMap();
							if (termMapModel != null && termMapModel.size() > 0) {
								for (Entry<String, Object> entryModel : termMapModel.entrySet()) {
									orTermQuery = QueryBuilders.termQuery(entryModel.getKey(), entryModel.getValue());
									bb.must(orTermQuery);
								}
							}
							boolQueryBuilder.should(bb);
						}
					}
				}
			} else {// 非or查询
				createQueryBooleanBuilder(queryParamData, boolQueryBuilder, boolFilterBuilder);
			}
		}

		sourceBuilder.query(boolQueryBuilder);// 与条件集合

		sourceBuilder.postFilter(boolFilterBuilder);// 过滤集合

		// 排序
		Map<String, Object> sortMap = queryParamData.getSortMap();
		if (sortMap != null && !sortMap.isEmpty()) {
			for (Entry<String, Object> entry : sortMap.entrySet()) {
				String val = entry.getValue().toString().toUpperCase();
				if ("DESC".equals(val)) {
					sourceBuilder.sort(entry.getKey(), SortOrder.DESC);
				} else {
					sourceBuilder.sort(entry.getKey(), SortOrder.ASC);
				}
			}
		}

		// 排序 1 地理查询的时候 距离的排序 这里仅仅支持 ASC
		Map<String, GeoPoint> sortGeoMap = queryParamData.getSortGeoMap();
		if (sortGeoMap != null && !sortGeoMap.isEmpty()) {
			for (Entry<String, GeoPoint> entry : sortGeoMap.entrySet()) {
				if (StringUtils.isNotBlank(entry.getKey()) && entry.getValue() != null) {
					GeoDistanceSortBuilder geoDistanceSortBuilder = SortBuilders
							.geoDistanceSort(entry.getKey(), entry.getValue()).unit(DistanceUnit.KILOMETERS)
							.order(SortOrder.ASC);
					sourceBuilder.sort(geoDistanceSortBuilder);
				}
			}
		}

		// 分页
		sourceBuilder.from((queryParamData.getPage() - 1) * queryParamData.getLimit());
		sourceBuilder.size(queryParamData.getLimit());

		// 显示指定字段
		List<String> revelFields = queryParamData.getRevelFields();
		if (revelFields != null && revelFields.size() > 0) {
			sourceBuilder.fetchSource(revelFields.toArray(new String[revelFields.size()]), null);
		}
		// 派出字段
		List<String> excludeFields = queryParamData.getExcludeFields();
		if (excludeFields != null && excludeFields.size() > 0) {
			sourceBuilder.fetchSource(null, excludeFields.toArray(new String[excludeFields.size()]));
		}
		return sourceBuilder;
	}

	private static void createQueryBooleanBuilder(QueryParamData queryParamData, BoolQueryBuilder boolQueryBuilder,
			BoolQueryBuilder boolFilterBuilder) {
		Map<String, Object> matchMap = queryParamData.getMatchMap();
		if (matchMap != null && matchMap.size() > 0) {
			for (Entry<String, Object> entry : matchMap.entrySet()) {
				MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(entry.getKey(), entry.getValue());
				boolQueryBuilder.must(matchQuery);
			}
		}

		Map<String, Object> termMap = queryParamData.getTermMap();
		if (termMap != null && termMap.size() > 0) {
			for (Entry<String, Object> entry : termMap.entrySet()) {
				boolQueryBuilder.must(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
			}
		}

		Map<String, Object> phraseMap = queryParamData.getPhraseMap();
		if (phraseMap != null && phraseMap.size() > 0) {
			for (Entry<String, Object> entry : phraseMap.entrySet()) {
				boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(entry.getKey(), entry.getValue()));
			}
		}

		Map<String, Object> filterMap = queryParamData.getFilterMap();
		if (filterMap != null && filterMap.size() > 0) {
			for (Entry<String, Object> entry : filterMap.entrySet()) {
				boolFilterBuilder.must(QueryBuilders.termQuery(entry.getKey(), entry.getValue()));
			}
		}

		// 注意 中心范围查询 通用版本仅仅支持 公里 为单位
		Map<String, Map<GeoPoint, Double>> diatinceGeoMap = queryParamData.getDiatinceGeoMap();
		if (diatinceGeoMap != null && diatinceGeoMap.size() > 0) {
			for (Entry<String, Map<GeoPoint, Double>> entry : diatinceGeoMap.entrySet()) {
				Map<GeoPoint, Double> value = entry.getValue();
				if (value != null && value.size() > 0) {
					for (Entry<GeoPoint, Double> model : value.entrySet()) {
						GeoDistanceQueryBuilder geoDistance = QueryBuilders.geoDistanceQuery(entry.getKey())
								.point(model.getKey()).distance(model.getValue(), DistanceUnit.KILOMETERS)
								.geoDistance(GeoDistance.ARC);
						boolFilterBuilder.must(geoDistance);
					}
				}
			}
		}

		Map<String, List<GeoPoint>> geoMap = queryParamData.getGeoMap();
		if (geoMap != null && geoMap.size() > 0) {
			for (Entry<String, List<GeoPoint>> entry : geoMap.entrySet()) {
				List<GeoPoint> value = entry.getValue();
				if (value != null && value.size() == 2) {// 这个是 对角查询
					boolFilterBuilder.must(
							QueryBuilders.geoBoundingBoxQuery(entry.getKey()).setCorners(value.get(0), value.get(1)));
				} else {
					boolFilterBuilder.must(QueryBuilders.geoPolygonQuery(entry.getKey(), value));
				}
			}
		}
		// 范围过滤
		handleRangeQuery(queryParamData, boolFilterBuilder);
	}

	/**
	 * 
	 * @Description: 得到查询条件
	 * @author weichengz
	 * @date 2019年11月24日 下午1:20:30
	 */
	public static BoolQueryBuilder getBoolQueryBuilder(QueryParamData queryParamData) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		// 处理参数问题
		if (queryParamData != null) {
			Map<String, List<Object>> orMap = queryParamData.getOrMap();
			if (orMap != null && !orMap.isEmpty()) {// or查询的时候 仅仅支持 termMap联合使用 其余的不支持
				TermQueryBuilder orTermQuery = null;
				for (Entry<String, List<Object>> entry : orMap.entrySet()) {
					List<Object> kvs = entry.getValue();
					if (kvs != null && kvs.size() > 0) {
						for (Object object : kvs) {
							BoolQueryBuilder bb = QueryBuilders.boolQuery();
							orTermQuery = QueryBuilders.termQuery(entry.getKey(), object);
							bb.must(orTermQuery);
							// 处理term条件
							Map<String, Object> termMapModel = queryParamData.getTermMap();
							if (termMapModel != null && termMapModel.size() > 0) {
								for (Entry<String, Object> entryModel : termMapModel.entrySet()) {
									orTermQuery = QueryBuilders.termQuery(entryModel.getKey(), entryModel.getValue());
									bb.must(orTermQuery);
								}
							}
							boolQueryBuilder.should(bb);
						}
					}
				}
			} else {// 非or查询
				createQueryBooleanBuilder(queryParamData, boolQueryBuilder, boolQueryBuilder);
			}
		}
		return boolQueryBuilder;
	}

	/**
	 * 
	 * @Description: 判断索引是不是存在
	 * @author weichengz
	 * @date 2019年10月16日 上午11:11:05
	 */
	public static Boolean indexIfExists(String index) {
		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
		IndicesExistsResponse response = transportClient.admin().indices()
				.exists(new IndicesExistsRequest().indices(new String[] { index })).actionGet();
		return response.isExists();
	}

	/**
	 * 
	 * @Description: 判断索引、类型是不是存在
	 * @author weichengz
	 * @date 2019年10月16日 上午11:11:05
	 */
	public static Boolean typeIfExists(String index, String type) {
		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
		TypesExistsResponse response = transportClient.admin().indices()
				.typesExists(new TypesExistsRequest(new String[] { index }, type)).actionGet();
		return response.isExists();
	}

	/**
	 * 查询
	 * 
	 * @Description: 根据索引/类型/数据ID 查询数据
	 * @author weichengz
	 * @date 2019年4月2日 下午5:09:08
	 */
	public static Map<String, Object> queryById(String index, String type, String id) {
		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
		GetRequest request = new GetRequest(index, type, id);
		GetResponse actionGet = transportClient.get(request).actionGet();
		Map<String, Object> sourceAsMap = actionGet.getSourceAsMap();
		if (sourceAsMap == null || sourceAsMap.isEmpty()) {
			return new HashMap<>();
		}
		sourceAsMap.put("pk", actionGet.getId());
		return sourceAsMap;
	}

	/**
	 * 查询
	 * 
	 * @Description: 根据索引/类型 分页查询所有的记录
	 * @author weichengz
	 * @date 2019年4月3日 上午9:16:03
	 */
	public static List<Map<String, Object>> queryList(QueryParamData queryParamData) {
		queryParamData.verificationIndexType();
		SearchResponse searchResponse = queryDataResponseByCondition(queryParamData.getIndex(),
				queryParamData.getType(), queryParamData);
		SearchHits hits = searchResponse.getHits();
		List<Map<String, Object>> maps = new ArrayList<>();
		for (SearchHit searchHit : hits) {
			Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
			if (queryParamData.isRevelPk()) {
				sourceAsMap.put("pk", searchHit.getId());
			}
			maps.add(sourceAsMap);
		}
		return maps;
	}

	/**
	 * 查询 分页注意控制每页的数量 这个size注意控制在1万以下
	 * 
	 * @Description: 根据索引/类型 分页查询所有的记录 数据+总数量
	 * @author weichengz
	 * @date 2019年4月3日 上午9:16:03
	 */
	public static QueryParamData queryPage(QueryParamData queryParamData) {
		queryParamData.verificationIndexType();
		SearchResponse searchResponse = queryDataResponseByCondition(queryParamData.getIndex(),
				queryParamData.getType(), queryParamData);
		SearchHits hits = searchResponse.getHits();
		queryParamData.setTotalNums(hits.getTotalHits());
		List<Map<String, Object>> maps = new ArrayList<>();
		for (SearchHit searchHit : hits) {
			Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
			sourceAsMap.put("pk", searchHit.getId());
			maps.add(sourceAsMap);
		}
		queryParamData.setRows(maps);
		return queryParamData;
	}

	/**
	 * 
	 * @Description: 根据条件查询数据 返回response
	 * @author weichengz
	 * @date 2019年4月5日 上午11:33:57
	 */
	private static SearchResponse queryDataResponseByCondition(String index, String type,
			QueryParamData queryParamData) {
		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
		SearchRequest searchRequest = new SearchRequest(index);
		searchRequest.types(type);

		SearchSourceBuilder sourceBuilder = createSourceBuilder(queryParamData);
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = transportClient.search(searchRequest).actionGet();
		return searchResponse;
	}

	/**
	 * 处理范围查询 支持 let gte lt gt 组合
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年4月5日 下午1:37:07
	 */
	private static void handleRangeQuery(QueryParamData queryParamData, BoolQueryBuilder boolFilterBuilder) {
		Map<String, Map<String, Long>> rangeMap = queryParamData.getRangeMap();
		if (rangeMap != null && rangeMap.size() > 0) {
			for (Entry<String, Map<String, Long>> entry : rangeMap.entrySet()) {
				String key = entry.getKey();
				Map<String, Long> glteParam = entry.getValue();
				if (glteParam != null && !glteParam.isEmpty()) {
					if (glteParam.size() == 1) {
						for (Entry<String, Long> kv : glteParam.entrySet()) {
							Long value = kv.getValue();
							if (value != null) {
								if (("LTE").equals(kv.getKey().toUpperCase())) {
									boolFilterBuilder.must(QueryBuilders.rangeQuery(key).lte(kv.getValue()));
								}
								if (("LT").equals(kv.getKey().toUpperCase())) {
									boolFilterBuilder.must(QueryBuilders.rangeQuery(key).lt(kv.getValue()));
								}
								if (("GT").equals(kv.getKey().toUpperCase())) {
									boolFilterBuilder.must(QueryBuilders.rangeQuery(key).gt(kv.getValue()));
								}
								if (("GTE").equals(kv.getKey().toUpperCase())) {
									boolFilterBuilder.must(QueryBuilders.rangeQuery(key).gte(kv.getValue()));
								}
							}
						}
					}
					if (glteParam.size() == 2) {
						Long lteNum = null;
						Long ltNum = null;
						Long gteNum = null;
						Long gtNum = null;
						for (Entry<String, Long> kv : glteParam.entrySet()) {
							if (("LTE").equals(kv.getKey().toUpperCase())) {
								lteNum = kv.getValue();
							}
							if (("LT").equals(kv.getKey().toUpperCase())) {
								ltNum = kv.getValue();
							}
							if (("GT").equals(kv.getKey().toUpperCase())) {
								gtNum = kv.getValue();
							}
							if (("GTE").equals(kv.getKey().toUpperCase())) {
								gteNum = kv.getValue();
							}
						}
						if (lteNum != null && gteNum != null) {
							boolFilterBuilder.must(QueryBuilders.rangeQuery(key).gte(gteNum).lte(lteNum));
						}
						if (lteNum != null && gtNum != null) {
							boolFilterBuilder.must(QueryBuilders.rangeQuery(key).gt(gtNum).lte(lteNum));
						}
						if (ltNum != null && gtNum != null) {
							boolFilterBuilder.must(QueryBuilders.rangeQuery(key).gt(gtNum).lt(ltNum));
						}
						if (ltNum != null && gteNum != null) {
							boolFilterBuilder.must(QueryBuilders.rangeQuery(key).gte(gteNum).lt(ltNum));
						}
					}
				}
			}
		}
	}

	/**
	 * 更新
	 * 
	 * @Description: 根据索引/类型/ 修改数据--->字段原则：存在则修改不存在添加
	 * @author weichengz
	 * @date 2019年4月2日 下午5:09:08
	 */
	public static void updateById(String index, String type, String id, Map<String, Object> fieldsMap) {
		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
		UpdateRequest updateRequest = new UpdateRequest(index, type, id);
		updateRequest.doc(fieldsMap);
		transportClient.update(updateRequest).actionGet();
	}

	/**
	 * 删除
	 * 
	 * @Description: 根据索引删除索引以及数据
	 * @author weichengz
	 * @date 2019年4月3日 上午9:44:30
	 */
	public static void deleteIndex(String index) {
		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
		IndicesExistsResponse existsResponse = transportClient.admin().indices().exists(new IndicesExistsRequest(index))
				.actionGet();
		if (existsResponse.isExists()) {
			transportClient.admin().indices().delete(new DeleteIndexRequest(index)).actionGet();
		}
	}

	/**
	 * 
	 * @Description: 通过文档id删除
	 * @author weichengz
	 * @date 2019年4月5日 上午11:03:22
	 */
	public static void deleteByDocId(String index, String type, String docId) {
		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
		DeleteRequest request = new DeleteRequest(index, type, docId);
		try {
			transportClient.delete(request).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description: 通过文档id删除 这个暂时不用
	 * @author weichengz
	 * @date 2019年4月5日 上午11:03:22
	 */
	public static void deleteByBussId(String index, String type, String bussId) {
		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
		QueryParamData queryParamData = new QueryParamData();
		queryParamData.setType(type);
		queryParamData.setIndex(index);
		Map<String, Object> matchMap = queryParamData.getMatchMap();
		matchMap.put("id", bussId);
		List<Map<String, Object>> queryList = queryList(queryParamData);
		if (queryList != null && queryList.size() > 0) {
			for (Map<String, Object> map : queryList) {
				transportClient.delete(new DeleteRequest(index, type, map.get("pk").toString())).actionGet();
			}
		}
	}

	/***
	 * 字段聚合 PUT /zwp/zwp/_mapping { "properties": { "tags":{ "type":
	 * "text","fielddata": true} }
	 */

	/**
	 * 以时间分组注意几点： 1.最后产生的时间格式是：yyyy-MM-dd'T'HH:mm:ss.SSS'Z',所以需要转换
	 * 2.产生的时间只有数据库中存在的，不存在的不展示
	 * 
	 * @Description: ["测试1","测试2"] 注意这种格式的是也可以进行聚合分析的
	 * @author weichengz
	 * @date 2019年4月5日 下午8:48:11
	 */
	public Object agg() throws InterruptedException, ExecutionException, ParseException {
		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		SearchResponse searchResponse = transportClient.prepareSearch("zwp").setTypes("zwp")
				.addAggregation(AggregationBuilders//
						.dateHistogram("dateAgg")//
						.field("createdate")//
						.dateHistogramInterval(DateHistogramInterval.HOUR)//
						.order(BucketOrder.key(true))//
				).get();
		Histogram timeAgg = searchResponse.getAggregations().get("dateAgg");
		for (Bucket bucket : timeAgg.getBuckets()) {
			String key = bucket.getKey().toString();
			key = df1.format(df2.parse(key));
			System.out.println(key + "---" + bucket.getDocCount());
		}
		return "success";
	}

	/**
	 * 可添加过滤条件 2019-01-22 02:00:00---109 2019-01-22 02:00:00-----109------1----109
	 * 
	 * @Description: 以时间分组之后在以别的分组/其他的操作类推 ["测试1","测试2"] 注意这种格式的是也可以进行聚合分析的
	 * @author weichengz
	 * @date 2019年4月5日 下午8:48:11
	 */
	public Object aggTwo() throws InterruptedException, ExecutionException, ParseException {
		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		// TermQueryBuilder termQuery = QueryBuilders.termQuery("state", "1");
		SearchResponse searchResponse = transportClient.prepareSearch("zwp").setTypes("zwp")
				// .setQuery(termQuery)
				.addAggregation(AggregationBuilders//
						.dateHistogram("dateAgg")//
						.field("createdate")//
						.dateHistogramInterval(DateHistogramInterval.HOUR)//
						.order(BucketOrder.key(true))//
						.subAggregation(AggregationBuilders.terms("state").field("state").order(BucketOrder.key(true)))//
				).get();
		Histogram timeAgg = searchResponse.getAggregations().get("dateAgg");// 日期的都用 这个Histogram
		for (Bucket bucket : timeAgg.getBuckets()) {
			String key = bucket.getKey().toString();
			key = df1.format(df2.parse(key));
			System.out.println(key + "---" + bucket.getDocCount());
			LongTerms longTerms = bucket.getAggregations().get("state"); // terms 都用LongTerms
			List<org.elasticsearch.search.aggregations.bucket.terms.LongTerms.Bucket> buckets = longTerms.getBuckets();
			for (org.elasticsearch.search.aggregations.bucket.terms.LongTerms.Bucket bucket2 : buckets) {
				System.out.println(key + "-----" + bucket.getDocCount() + "------" + bucket2.getKey() + "----"
						+ bucket2.getDocCount());
			}
		}
		return "success";
	}

	/**
	 * 根据查询条件批量查询数据 matchMap;termMap; phraseMap; //目前仅仅支持这三种查询
	 */
	public static Long deleteBatchByCondition(QueryParamData queryParamData) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		BoolQueryBuilder boolFilterBuilder = QueryBuilders.boolQuery();

		TransportClient transportClient = SpringUtil.getBean(TransportClient.class);

		// 校验
		queryParamData.verificationIndexType();
		createQueryBooleanBuilder(queryParamData, boolQueryBuilder, null);

		BulkByScrollResponse deleteBatchResponse = DeleteByQueryAction.INSTANCE.newRequestBuilder(transportClient)
				.filter(boolFilterBuilder).source(queryParamData.getIndex()).get();
		long deletedNum = deleteBatchResponse.getDeleted();
		return deletedNum;
	}

}
