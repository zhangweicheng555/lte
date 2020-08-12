package com.boot.kaizen.business.es.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.util.FileUtil;
import com.boot.kaizen.util.JsonMsgUtil;

@Controller
@RequestMapping("/app")
public class TestController {

	@Autowired
	private TransportClient transportClient;

	@Value("${project.version}")
	private String version;
	
	/**
	 * 创建映射
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年4月14日 上午10:14:39
	 */
	@ResponseBody
	@GetMapping(value = "/createIndex")
	public JsonMsgUtil createIndex() throws IOException {

		CreateIndexRequestBuilder cibSimgc = transportClient.admin().indices().prepareCreate("simgc");

		XContentBuilder mapping = XContentFactory.jsonBuilder()//
				.startObject()//
				.startObject("properties")//

				.startObject("cityId").field("type", "text").startObject("fields").startObject("keyword")
				.field("type", "keyword").field("ignore_above", 256).endObject().endObject()//
				.endObject()//

				.startObject("location").field("type", "geo_point").endObject()//

				.startObject("lte_address").field("type", "text").endObject()//

				.startObject("lte_azimuth").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_site_name").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_cell").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_ci").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_city_name").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_derrick_type").field("type", "text").endObject()//

				.startObject("lte_earfcn").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//
				
				
	

				.startObject("lte_ecgi").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_electronic_downdip").field("type", "text").endObject()//

				.startObject("lte_enodebid").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_firm").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_grid").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_latitude").field("type", "text").endObject()//

				.startObject("lte_latitude2").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_longitude").field("type", "text").endObject()//

				.startObject("lte_longitude2").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_mechanical_downdip").field("type", "text").endObject()//

				.startObject("lte_net").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_phycellid").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_scene").field("type", "text").endObject()//

				.startObject("lte_sector_id").field("type", "text").endObject()//

				.startObject("lte_site_tall").field("type", "text").endObject()//

				.startObject("lte_site_type").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_sys").field("type", "text").endObject()//

				.startObject("lte_tac").field("type", "text").endObject()//

				.startObject("lte_total_downdip").field("type", "text").endObject()//

				.endObject()//
				.endObject();//

		cibSimgc.addMapping("simgc", mapping);

		cibSimgc.execute().actionGet();

		// 设置查询数量限制的问题
		transportClient.admin().indices().prepareUpdateSettings("simgc").setSettings(Settings.builder() //
				.put("index.max_result_window", 10000000)//
				// .put("index.number_of_shards", 3)// 这个东西不能动态的更新 只能在创建映射的时候设置
				.put("index.number_of_replicas", 0)// 到只有一台机器的时候 设置为0
		).get();//

		/**
		 * logmessage
		 */
		CreateIndexRequestBuilder cibMessage = transportClient.admin().indices().prepareCreate("logmessage");

		XContentBuilder mappingMessage = XContentFactory.jsonBuilder()//
				.startObject()//
				.startObject("properties")//

				.startObject("id").field("type", "text").startObject("fields").startObject("keyword")
				.field("type", "keyword").field("ignore_above", 256).endObject().endObject()//
				.endObject()//

				.startObject("latitude").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("longitude").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("message").field("type", "text").endObject()//

				.startObject("pid").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("ppid").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.endObject()//
				.endObject();//

		cibMessage.addMapping("logmessage", mappingMessage);
		cibMessage.execute().actionGet();

		// 设置查询数量限制的问题
		transportClient.admin().indices().prepareUpdateSettings("logmessage").setSettings(Settings.builder() //
				.put("index.max_result_window", 10000000)//
				.put("index.number_of_replicas", 0)// 到只有一台机器的时候 设置为0
		).get();//

		/**
		 * logmain
		 */
		CreateIndexRequestBuilder cibLogMain = transportClient.admin().indices().prepareCreate("logmain");

		XContentBuilder mappingLogMain = XContentFactory.jsonBuilder()//
				.startObject()//
				.startObject("properties")//

				.startObject("abNormalEventType").field("type", "text").startObject("fields").startObject("keyword")
				.field("type", "keyword").field("ignore_above", 256).endObject().endObject()//
				.endObject()//

				.startObject("angle").field("type", "long").endObject()//

				.startObject("cELLID").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("cI").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("cellName").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				// =================================

				.startObject("proIndicators").startObject("properties").startObject("servingCellPccTac")
				.field("type", "text").endObject()//
				.startObject("servingCellPccFreqDl").field("type", "text").endObject()//
				.startObject("servingCellPccEnodebId").field("type", "text").endObject()//
				.startObject("servingCellPccSiteEci").field("type", "text").endObject()//
				.startObject("servingCellPccRsrp").field("type", "text").endObject()//
				.startObject("servingCellPccRsrq").field("type", "text").endObject()//
				.startObject("servingCellPccWidebandCqi").field("type", "text").endObject()//
				.startObject("servingCellPccPucchTxpower").field("type", "text").endObject()//
				.startObject("servingCellPccULBLER").field("type", "text").endObject()//
				.startObject("servingCellPccBandIndex").field("type", "text").endObject()//
				.startObject("servingCellPccEarfcnDl").field("type", "text").endObject()//
				.startObject("servingCellPccCellId").field("type", "text").endObject()//
				.startObject("servingCellPccPci").field("type", "text").endObject()//
				.startObject("servingCellPccSinr").field("type", "text").endObject()//
				.startObject("servingCellPccRssi").field("type", "text").endObject()//
				.startObject("servingCellPccRankIndex").field("type", "text").endObject()//
				.startObject("servingCellPccPuschTxpower").field("type", "text").endObject()//
				.startObject("servingCellPccDLBLER").field("type", "text").endObject()//

				.startObject("throughputPccPdcpDl").field("type", "text").endObject()//
				.startObject("throughputPccRlcDl").field("type", "text").endObject()//
				.startObject("throughputPccMacDl").field("type", "text").endObject()//
				.startObject("throughputPccPhyDl").field("type", "text").endObject()//

				.startObject("throughputPccPdcpUl").field("type", "text").endObject()//
				.startObject("throughputPccRlcUl").field("type", "text").endObject()//
				.startObject("throughputPccMacUl").field("type", "text").endObject()//
				.startObject("throughputPccPhyUl").field("type", "text").endObject()//

				.endObject()//
				.endObject()//

				// =================================

				.startObject("doorDataInfoBeans").startObject("properties")

				.startObject("ci").field("type", "text").endObject()//

				.startObject("dl").field("type", "float").endObject()//
				.startObject("earfcn").field("type", "long").endObject()//

				.startObject("mLteNeighborhoodInfos")//
				.startObject("properties")//

				.startObject("mBand")//
				.field("type", "text")//
				.endObject() //

				.startObject("mLteNeighborhoodCI")//
				.field("type", "text")//
				.endObject() //

				.startObject("mLteNeighborhoodEarfcn")//
				.field("type", "text")//
				.endObject() //

				.startObject("mLteNeighborhoodPCI")//
				.field("type", "text")//
				.endObject() //

				.startObject("mLteNeighborhoodRSRPOrSINR")//
				.field("type", "text")//
				.endObject() //

				.startObject("mLteNeighborhoodRsrq")//
				.field("type", "text")//
				.endObject() //

				.startObject("mLteNeighborhoodTAC")//
				.field("type", "text")//
				.endObject() //

				.startObject("mRsrpLevel").field("type", "long").endObject()//

				.startObject("siteName")//
				.field("type", "text")//
				.endObject()//

				.endObject()//
				.endObject()//

				.startObject("pci")//
				.field("type", "text")//
				.endObject()//

				.startObject("rsrp")//
				.field("type", "text")//
				.endObject()//

				.startObject("rsrq")//
				.field("type", "text")//
				.endObject()//

				.startObject("sinr").field("type", "long").endObject()//

				.startObject("time")//
				.field("type", "text")//
				.endObject()//

				.startObject("ul").field("type", "float").endObject()//

				.endObject()//
				.endObject()//

				.startObject("downLoadSpeed").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject()//
				.endObject()//
				.endObject()//

				.startObject("eNB").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("earfcn").field("type", "text")//
				.endObject()//

				.startObject("rootSupport").field("type", "text")//
				.endObject()//

				.startObject("eventType").field("type", "long").endObject()//

				.startObject("formatTimestamp").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("ftpType").field("type", "long").endObject()//

				.startObject("id").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("latitude").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("longitude").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("mSignaBean").startObject("properties")//

				.startObject("ci")//
				.field("type", "long")//
				.endObject()//

				/*
				 * .startObject("formatTimestamp") .field("type", "text")//
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject()// .endObject()//
				 * .endObject()//
				 */
				.startObject("mChannelType").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject()// .endObject()//
				 */ .endObject()//

				.startObject("mLatitude")//
				.field("type", "float")//
				.endObject()//

				.startObject("mLongitude")//
				.field("type", "float")//
				.endObject()//

				.startObject("mMeaasge").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject()//
				.endObject()//
				.endObject()//

				.startObject("mMessageType").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject()// .endObject()//
				 */ .endObject()//

				.startObject("mSignaTimestamp").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject()// .endObject()//
				 */ .endObject()//

				.startObject("mSingaType").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject()// .endObject()//
				 */ .endObject()//

				.startObject("mTimestamp")//
				.field("type", "long")//
				.endObject()//

				.endObject()//
				.endObject()//

				.startObject("mSignaEventBean").startObject("properties").startObject("ci").field("type", "long")//
				.endObject()//

				.startObject("formatTimestamp").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject()// .endObject()//
				 */ .endObject()//

				.startObject("mEvent").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject()// .endObject()//
				 */ .endObject()//

				.startObject("mEventType").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject()// .endObject()//
				 */ .endObject()//

				.startObject("mLatitude").field("type", "float")//
				.endObject()//

				.startObject("mLongitude").field("type", "float")//
				.endObject()//

				.startObject("mSignaTimestamp").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject()// .endObject()//
				 */ .endObject()//

				.startObject("mTimestamp").field("type", "long")//
				.endObject()//

				.endObject()//
				.endObject()//

				.startObject("netWorkType").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("normalEventType").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("pci").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("pid").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("rsrp").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("sPEED").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject().endObject()//
				 */ .endObject()//

				.startObject("sinr").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("siteLat").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject().endObject()//
				 */ .endObject()//

				.startObject("siteLng").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject().endObject()//
				 */ .endObject()//

				.startObject("tAC").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject().endObject()//
				 */ .endObject()//

				.startObject("testTime").field("type", "long").endObject()//

				.startObject("upLoadSpeed").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.endObject()//
				.endObject();//

		cibLogMain.addMapping("logmain", mappingLogMain);
		cibLogMain.execute().actionGet();

		// 设置查询数量限制的问题
		transportClient.admin().indices().prepareUpdateSettings("logmain").setSettings(Settings.builder() //
				.put("index.max_result_window", 10000000)//
				.put("index.number_of_replicas", 0)// 到只有一台机器的时候 设置为0
		).get();//

		return new JsonMsgUtil(true);
	}

	/**
	 * @Description: 5g主log 日志实体映射
	 * @author weichengz
	 * @date 2020年5月11日 下午2:06:43
	 */
	@ResponseBody
	@GetMapping(value = "/create5gIndex")
	public JsonMsgUtil create5gIndex() throws IOException {

		CreateIndexRequestBuilder cibLogMain = transportClient.admin().indices().prepareCreate("logmain5g");

		XContentBuilder mappingLogMain = XContentFactory.jsonBuilder()//
				.startObject()//
				.startObject("properties")//

				.startObject("pid").field("type", "text").startObject("fields").startObject("keyword")
				.field("type", "keyword").field("ignore_above", 256).endObject().endObject()//
				.endObject()//

				.startObject("id").field("type", "text").startObject("fields").startObject("keyword")
				.field("type", "keyword").field("ignore_above", 256).endObject().endObject()//
				.endObject()//

				.startObject("logversion").field("type", "text").endObject()//
				.startObject("dualSimSupport").field("type", "text").endObject()//
				.startObject("operatorCompareSupport").field("type", "text").endObject()//
				.startObject("rootSupport").field("type", "text").endObject()//

				.startObject("phone").field("type", "text").startObject("fields").startObject("keyword")
				.field("type", "keyword").field("ignore_above", 256).endObject().endObject()//
				.endObject()//

				.startObject("operator").field("type", "text").startObject("fields").startObject("keyword")
				.field("type", "keyword").field("ignore_above", 256).endObject().endObject()//
				.endObject()//

				.startObject("operator_y").field("type", "text").endObject()//

				.startObject("latitude").field("type", "text").endObject()//
				.startObject("longitude").field("type", "text").endObject()//
				.startObject("speed").field("type", "text").endObject()//
				.startObject("height").field("type", "text").endObject()//
				.startObject("testTime").field("type", "text").endObject()//

				.startObject("testTimeMill").field("type", "long").endObject()//

				.startObject("downLoadSpeed").field("type", "text").endObject()//
				.startObject("upLoadSpeed").field("type", "text").endObject()//
				.startObject("normalEventType").field("type", "text").endObject()//
				.startObject("abNormalEventType").field("type", "text").endObject()//
				.startObject("rsrp").field("type", "text").endObject()//
				.startObject("sinr").field("type", "text").endObject()//
				.startObject("ssrsrp").field("type", "text").endObject()//
				.startObject("sssinr").field("type", "text").endObject()//

				// 这个是复杂属性开始
				.startObject("nrDataInfoBean")//
				.startObject("properties")//

				.startObject("nrARFCN").field("type", "text").endObject()//
				.startObject("nrPCI").field("type", "text").endObject()//
				.startObject("nrCellName").field("type", "text").endObject()//
				.startObject("ssRSRP").field("type", "text").endObject()//
				.startObject("ssSINR").field("type", "text").endObject()//
				.startObject("ssRSRQ").field("type", "text").endObject()//

				// 集合属性开始
				.startObject("nrNeighborhoodInfos")//
				.startObject("properties")//
				.startObject("nrNeighborhoodNRARFCN").field("type", "text").endObject() //
				.startObject("nrNeighborhoodPCI").field("type", "text").endObject() //
				.startObject("nrNeighborhoodSSRSRP").field("type", "text").endObject() //
				.startObject("nrNeighborhoodSSRSRQ").field("type", "text").endObject() //
				.startObject("nrNeighborhoodSSSINR").field("type", "text").endObject() //
				.endObject()//
				.endObject()//

				// 集合属性结束
				.endObject()//
				.endObject()//
				// 这个是复杂属性结束

				// 这个是复杂属性开始
				.startObject("lteDataInfoBean")//
				.startObject("properties")//

				.startObject("lteTAC").field("type", "text").endObject()//
				.startObject("lteEARFCN").field("type", "text").endObject()//
				.startObject("ltePCI").field("type", "text").endObject()//
				.startObject("lteENB").field("type", "text").endObject()//
				.startObject("lteCellID").field("type", "text").endObject()//
				.startObject("lteCellName").field("type", "text").endObject()//
				.startObject("lteRSRP").field("type", "text").endObject()//
				.startObject("lteRSRQ").field("type", "text").endObject()//
				.startObject("lteSINR").field("type", "text").endObject()//
				.startObject("lteRSSI").field("type", "text").endObject()//

				// 集合属性开始
				.startObject("LteNeighborhoodInfos")//
				.startObject("properties")//
				.startObject("lteNeighbirhoodEARFCN").field("type", "text").endObject() //
				.startObject("lteNeighbirhoodPCI").field("type", "text").endObject() //
				.startObject("lteNeighbirhoodRSRP").field("type", "text").endObject() //
				.startObject("lteNeighbirhoodRSRQ").field("type", "text").endObject() //
				.startObject("lteNeighbirhoodRSSI").field("type", "text").endObject() //
				.endObject()//
				.endObject()//

				// 集合属性结束
				.endObject()//
				.endObject()//
				// 这个是复杂属性结束

				// 这个是复杂属性开始
				.startObject("proNrDataInfoBean")//
				.startObject("properties")//

				.startObject("band").field("type", "text").endObject()//
				.startObject("BandWidth").field("type", "text").endObject()//
				.startObject("FrequencyPointA").field("type", "text").endObject()//
				.startObject("FrequencyDL").field("type", "text").endObject()//
				.startObject("GSCN").field("type", "text").endObject()//
				.startObject("SubCarrierSpace").field("type", "text").endObject()//
				.startObject("PCI").field("type", "text").endObject()//
				.startObject("SSBIndex").field("type", "text").endObject()//
				.startObject("ssRSRP").field("type", "text").endObject()//
				.startObject("ssSINR").field("type", "text").endObject()//
				.startObject("ssRSRQ").field("type", "text").endObject()//
				.startObject("puschTxPower").field("type", "text").endObject()//
				.startObject("pucchTxPower").field("type", "text").endObject()//
				.startObject("srsTxPower").field("type", "text").endObject()//

				.startObject("cqi").field("type", "text").endObject()//
				.startObject("mcsUL").field("type", "text").endObject()//
				.startObject("mcsDL").field("type", "text").endObject()//
				.startObject("modUL").field("type", "text").endObject()//
				.startObject("modDL").field("type", "text").endObject()//

				.startObject("puschBler").field("type", "text").endObject()//
				.startObject("pdschBler").field("type", "text").endObject()//
				.startObject("grantULNum").field("type", "text").endObject()//
				.startObject("grantDLNum").field("type", "text").endObject()//
				.startObject("riNumDL").field("type", "text").endObject()//

				.startObject("pdcpULThr").field("type", "text").endObject()//
				.startObject("pdcpDLThr").field("type", "text").endObject()//
				.startObject("rlcULThr").field("type", "text").endObject()//
				.startObject("rlcDLThr").field("type", "text").endObject()//
				.startObject("macULThr").field("type", "text").endObject()//

				.startObject("macDLThr").field("type", "text").endObject()//
				.startObject("puschRB").field("type", "text").endObject()//
				.startObject("pdschRB").field("type", "text").endObject()//
				.startObject("slotConfigDLUL").field("type", "text").endObject()//

				.startObject("_16qamNum").field("type", "text").endObject()//
				.startObject("_64qamNum").field("type", "text").endObject()//
				.startObject("_256qamNum").field("type", "text").endObject()//
				.startObject("_16qamUlNum").field("type", "text").endObject()//
				.startObject("_16qamDlNum").field("type", "text").endObject()//

				.startObject("_64qamUlNum").field("type", "text").endObject()//
				.startObject("_64qamDlNum").field("type", "text").endObject()//
				.startObject("_256qamUlNum").field("type", "text").endObject()//
				.startObject("_256qamDlNum").field("type", "text").endObject()//

				.startObject("qpskUlNum").field("type", "text").endObject()//
				.startObject("qpskDlNum").field("type", "text").endObject()//
				.startObject("ssARFCN").field("type", "text").endObject()//

				// 集合属性开始
				.startObject("proNrNeighborhoodInfos")//
				.startObject("properties")//
				.startObject("pNrNeighborhoodNRARFCN").field("type", "text").endObject() //
				.startObject("pNrNeighborhoodPCI").field("type", "text").endObject() //
				.startObject("pNrNeighborhoodBeam").field("type", "text").endObject() //
				.startObject("pNrNeighborhoodSSRSRP").field("type", "text").endObject() //
				.startObject("pNrNeighborhoodSSSINR").field("type", "text").endObject() //
				.startObject("pNrNeighborhoodSSRSRQ").field("type", "text").endObject() //
				.endObject()//
				.endObject()//

				// 集合属性结束
				.endObject()//
				.endObject()//
				// 这个是复杂属性结束

				// 这个是复杂属性开始
				.startObject("proLteDataInfoBean")//
				.startObject("properties")//

				.startObject("servingCellPccMcc").field("type", "text").endObject()//
				.startObject("servingCellPccMnc").field("type", "text").endObject()//
				.startObject("servingCellPccSiteEci").field("type", "text").endObject()//
				.startObject("servingCellPccBandIndex").field("type", "text").endObject()//
				.startObject("servingCellPccBwDl").field("type", "text").endObject()//

				.startObject("servingCellPccFreqDl").field("type", "text").endObject()//
				.startObject("servingCellPccTac").field("type", "text").endObject()//
				.startObject("servingCellPccSsp").field("type", "text").endObject()//
				.startObject("servingCellPccSa").field("type", "text").endObject()//
				.startObject("servingCellPccCellId").field("type", "text").endObject()//

				.startObject("servingCellPccEarfcnDl").field("type", "text").endObject()//
				.startObject("servingCellPccPci").field("type", "text").endObject()//
				.startObject("servingCellPccRsrp").field("type", "text").endObject()//
				.startObject("servingCellPccSinr").field("type", "text").endObject()//
				.startObject("servingCellPccRsrq").field("type", "text").endObject()//

				.startObject("servingCellPccRssi").field("type", "text").endObject()//
				.startObject("servingCellPccPuschTxpower").field("type", "text").endObject()//
				.startObject("servingCellPccPucchTxpower").field("type", "text").endObject()//
				.startObject("servingCellPccEnodebId").field("type", "text").endObject()//
				.startObject("servingCellPccWidebandCqi").field("type", "text").endObject()//

				.startObject("servingCellPccMcsul").field("type", "text").endObject()//
				.startObject("servingCellPccMcsdl").field("type", "text").endObject()//
				.startObject("servingCellPccModul").field("type", "text").endObject()//
				.startObject("servingCellPccModdl").field("type", "text").endObject()//
				.startObject("servingCellPccULBLER").field("type", "text").endObject()//

				.startObject("servingCellPccDLBLER").field("type", "text").endObject()//
				.startObject("servingCellPccGrantulnum").field("type", "text").endObject()//
				.startObject("servingCellPccGrantdlnum").field("type", "text").endObject()//
				.startObject("servingCellPccRankIndex").field("type", "text").endObject()//
				.startObject("throughputPccPdcpUl").field("type", "text").endObject()//

				.startObject("throughputPccPdcpDl").field("type", "text").endObject()//
				.startObject("throughputPccRlcUl").field("type", "text").endObject()//
				.startObject("throughputPccRlcDl").field("type", "text").endObject()//
				.startObject("throughputPccMacUl").field("type", "text").endObject()//
				.startObject("throughputPccMacDl").field("type", "text").endObject()//

				.startObject("servingCellPccPuschRbs").field("type", "text").endObject()//
				.startObject("servingCellPccPdschRbs").field("type", "text").endObject()//
				.startObject("modulationPcc16qam").field("type", "text").endObject()//
				.startObject("modulationPcc64qam").field("type", "text").endObject()//
				.startObject("modulationPcc256qam").field("type", "text").endObject()//

				.startObject("modulationPcc16qamUl").field("type", "text").endObject()//
				.startObject("modulationPcc16qamDl").field("type", "text").endObject()//
				.startObject("modulationPcc64qamUl").field("type", "text").endObject()//
				.startObject("modulationPcc64qamDl").field("type", "text").endObject()//
				.startObject("modulationPcc256qamUl").field("type", "text").endObject()//

				.startObject("modulationPcc256qamDl").field("type", "text").endObject()//
				.startObject("modulationPccQPSKUl").field("type", "text").endObject()//
				.startObject("modulationPccQPSKDl").field("type", "text").endObject()//

				// 集合属性开始
				.startObject("proLteNeighborhoodInfos")//
				.startObject("properties")//
				.startObject("pLteNeighbirhoodEARFCN").field("type", "text").endObject() //
				.startObject("pLteNeighbirhoodPCI").field("type", "text").endObject() //
				.startObject("pLteNeighbirhoodRSRP").field("type", "text").endObject() //
				.startObject("pLteNeighbirhoodRSRQ").field("type", "text").endObject() //
				.startObject("pLteNeighbirhoodRSSI").field("type", "text").endObject() //
				.endObject()//
				.endObject()//

				// 集合属性结束
				.endObject()//
				.endObject()//
				// 这个是复杂属性结束

				// 这个是复杂属性开始
				.startObject("signalEventBeans")//
				.startObject("properties")//
				.startObject("mTimestamp").field("type", "long").endObject()//
				.startObject("mEventType").field("type", "text").endObject()//
				.startObject("mid").field("type", "text").endObject()//
				.startObject("mEvent").field("type", "text").endObject()//
				.startObject("mLongitude").field("type", "float").endObject().startObject("mLatitude")
				.field("type", "float").endObject().endObject()//
				.endObject()//
				// 这个是复杂属性结束

				// 这个是复杂属性开始
				.startObject("signalBeans")//
				.startObject("properties")//
				.startObject("mTimestamp").field("type", "long").endObject()//
				.startObject("mSingaType").field("type", "text").endObject()//
				.startObject("mMessageType").field("type", "text").endObject()//
				.startObject("mid").field("type", "text").endObject()//
				.startObject("mChannelType").field("type", "text").endObject()//
				.startObject("mMeaasge").field("type", "text").endObject()//
				.startObject("mLongitude").field("type", "float").endObject().startObject("mLatitude")
				.field("type", "float").endObject().endObject()//
				.endObject()//
				// 这个是复杂属性结束

				.endObject()//
				.endObject();//

		cibLogMain.addMapping("logmain5g", mappingLogMain);
		cibLogMain.execute().actionGet();

		// 设置查询数量限制的问题
		transportClient.admin().indices().prepareUpdateSettings("logmain5g").setSettings(Settings.builder() //
				.put("index.max_result_window", 10000000)//
				.put("index.number_of_replicas", 0)// 到只有一台机器的时候 设置为0
		).get();//

		return new JsonMsgUtil(true);
	}

	/**
	 * 5g工参的映射
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年5月11日 下午4:24:20
	 */
	@ResponseBody
	@GetMapping(value = "/create5gGc")
	public JsonMsgUtil create5gGc() throws IOException {
		CreateIndexRequestBuilder cibSimgc = transportClient.admin().indices().prepareCreate("simgc5g");

		XContentBuilder mapping = XContentFactory.jsonBuilder()//
				.startObject()//
				.startObject("properties")//

				.startObject("cityId").field("type", "text").startObject("fields").startObject("keyword")
				.field("type", "keyword").field("ignore_above", 256).endObject().endObject()//
				.endObject()//

				.startObject("location").field("type", "geo_point").endObject()//

				.startObject("lte_address").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject().endObject()//
				 */ .endObject()//

				.startObject("lte_azimuth").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_site_name").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_cell").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_ci").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_city_name").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_derrick_type").field("type", "text").endObject()//

				.startObject("lte_earfcn").field("type", "text")//
			    .startObject("fields")// 
			    .startObject("keyword")// 
			    .field("type","keyword")// 
			    .field("ignore_above", 256)// 
			    .endObject()
			    .endObject()//
			    .endObject()//

				.startObject("lte_ecgi").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_electronic_downdip").field("type", "text").endObject()//

				.startObject("lte_enodebid").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_firm").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_grid").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_latitude").field("type", "text").endObject()//

				.startObject("lte_latitude2").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_longitude").field("type", "text").endObject()//

				.startObject("lte_longitude2").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_mechanical_downdip").field("type", "text").endObject()//

				.startObject("lte_net").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_phycellid").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_scene").field("type", "text").endObject()//

				.startObject("lte_sector_id").field("type", "text")//
				/*
				 * .startObject("fields")// .startObject("keyword")// .field("type",
				 * "keyword")// .field("ignore_above", 256)// .endObject().endObject()//
				 */ .endObject()//

				.startObject("lte_site_tall").field("type", "text").endObject()//

				.startObject("lte_site_type").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("lte_sys").field("type", "text").endObject()//

				.startObject("lte_tac").field("type", "text").endObject()//

				.startObject("lte_total_downdip").field("type", "text").endObject()//

				.endObject()//
				.endObject();//

		cibSimgc.addMapping("simgc5g", mapping);

		cibSimgc.execute().actionGet();

		// 设置查询数量限制的问题
		transportClient.admin().indices().prepareUpdateSettings("simgc5g").setSettings(Settings.builder() //
				.put("index.max_result_window", 10000000)//
				// .put("index.number_of_shards", 3)// 这个东西不能动态的更新 只能在创建映射的时候设置
				.put("index.number_of_replicas", 0)// 到只有一台机器的时候 设置为0
		).get();//

		return new JsonMsgUtil(true);
	}

	/**
	 *
	 * @Description: 添加带有keyword的字段类型,注意已经存在的字段在调用这个方法的时候不起作用,就是不能修改存在的字段类型,但是可以增加属性，你像你以前没有keyword，在调用这个方法的时候会给这个字段增加keyword字段的属性
	 * @author weichengz
	 * @date 2020年4月14日 上午10:14:39
	 */
	@ResponseBody
	@GetMapping(value = "/addIndexKeyword")
	public JsonMsgUtil addIndexKeyword(@RequestParam("index") String index, @RequestParam("type") String type,
			@RequestParam("newField") String newField, @RequestParam("fieldType") String fieldType) throws IOException {

		String addFieldMapping = "{        \"properties\" : {          \"" + newField
				+ "\" : {            \"type\" : \"" + fieldType
				+ "\",            \"fields\" : {              \"keyword\" : {                \"type\" : \"keyword\",                \"ignore_above\" : 256              }            }          }        }      }";

		transportClient.admin().indices().preparePutMapping(index).setType(type)
				.setSource(addFieldMapping, XContentType.JSON).get();

		return new JsonMsgUtil(true);
	}

	/**
	 * 添加不带有keyword的字段，注意已经存在的字段在调用这个方法的时候不起作用,就是不能修改存在的字段类型
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年4月14日 上午10:54:43
	 */
	@ResponseBody
	@GetMapping(value = "/addIndexWithOutKeyword")
	public JsonMsgUtil addIndexWithOutKeyword(@RequestParam("index") String index, @RequestParam("type") String type,
			@RequestParam("newField") String newField, @RequestParam("fieldType") String fieldType) throws IOException {

		String addFieldMapping = "{        \"properties\" : {          \"" + newField
				+ "\" : {            \"type\" : \"" + fieldType + "\"          }        }      }";

		transportClient.admin().indices().preparePutMapping(index).setType(type)
				.setSource(addFieldMapping, XContentType.JSON).get();

		return new JsonMsgUtil(true);
	}

	// 索引列表
	public static final List<String> INDEX_LIST = Arrays.asList("simgc", "logmain", "logmessage", "logmain5g");

	/**
	 * 获取mapping信息
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年4月14日 下午2:29:12
	 */
	@ResponseBody
	@GetMapping(value = "/mappings")
	public Map<String, Object> settings(@RequestParam(value = "index", required = false) String indexVal)
			throws IOException {
		Map<String, Object> resultMap = new HashMap<>();
		if (StringUtils.isNotBlank(indexVal)) {
			ImmutableOpenMap<String, MappingMetaData> mappings = transportClient.admin().cluster().prepareState()
					.execute().actionGet().getState().getMetaData().getIndices().get(indexVal).getMappings();
			String mapping = mappings.get(indexVal).source().toString();
			resultMap.put(indexVal, JSONObject.parseObject(mapping));
		} else {
			for (String index : INDEX_LIST) {
				ImmutableOpenMap<String, MappingMetaData> mappings = transportClient.admin().cluster().prepareState()
						.execute().actionGet().getState().getMetaData().getIndices().get(index).getMappings();
				String mapping = mappings.get(index).source().toString();

				resultMap.put(index, JSONObject.parseObject(mapping));
			}
		}

		return resultMap;
	}

	/**
	 * 删除索引
	 */
	@ResponseBody
	@GetMapping(value = "/delIndex")
	public JsonMsgUtil delIndex(@RequestParam("index") String index) throws IOException {
		Esutil.deleteIndex(index);
		return new JsonMsgUtil(true);
	}

	/**
	 * 
	 * @Description: 解除查询数量约束
	 * @author weichengz
	 * @date 2020年4月14日 下午5:23:52
	 */
	@ResponseBody
	@GetMapping(value = "/killLimitNum")
	public JsonMsgUtil killLimitNum(@RequestParam("index") String index) throws IOException {
		// 设置查询数量限制的问题
		transportClient.admin().indices().prepareUpdateSettings(index).setSettings(Settings.builder() //
				.put("index.max_result_window", 10000000)//
				.put("index.number_of_replicas", 0)// 到只有一台机器的时候 设置为0
		).get();//
		return new JsonMsgUtil(true);
	}

	/**
	 * 下载本地文件
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年4月30日 上午10:35:37
	 */
	@ResponseBody
	@GetMapping(value = "/downFile")
	public JsonMsgUtil downFile(HttpServletResponse response) throws IOException {
		FileUtil.downLocalFile(response, "C:\\kaizensoft\\kaizen\\doc.zip");
		return new JsonMsgUtil(true);
	}

	/**
	 *
	 * @Description: 添加测试数据
	 * @author weichengz
	 * @date 2020年6月30日 上午9:22:49
	 */
	@ResponseBody
	@RequestMapping(value = "/testData")
	public JsonMsgUtil testData() throws Exception {

		BulkRequest request = new BulkRequest();
		Boolean flag = false;
		for (int i = 0; i < 10; i++) {
			if ((i % 2) == 0) {
				flag = true;
			} else {
				flag = false;
			}
			TestModel testModel = new TestModel(UUID.randomUUID().toString().replace("-", ""), "张维程" + i,
					new Date().getTime(), i, i * 12.23, flag);

			IndexRequest indexRequestOther = new IndexRequest("test", "test", testModel.getId());
			indexRequestOther.source(JSONObject.toJSONString(testModel), XContentType.JSON);
			request.add(indexRequestOther);
		}

		/** 分批的添加进去 */
		transportClient.bulk(request).get();

		/** for循环请求数据结束 **/
		return new JsonMsgUtil(true, "添加测试数据成功", "");
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteBatchByCondition")
	public JsonMsgUtil deleteBatchByCondition(@RequestParam("index") String index,@RequestParam("type") String type) throws Exception {
		QueryParamData queryParamData=new QueryParamData(index, type);
		Esutil.deleteBatchByCondition(queryParamData);
		/** for循环请求数据结束 **/
		return new JsonMsgUtil(true, "添加测试数据成功", "");
	}
	
	@ResponseBody
	@RequestMapping(value = "/version")
	public JsonMsgUtil version() throws Exception {
	
		return new JsonMsgUtil(true, "添加测试数据成功", version);
	}

	@ResponseBody
	@GetMapping(value = "/createTest")
	public JsonMsgUtil createTest() throws IOException {
		CreateIndexRequestBuilder cibMessageTest1 = transportClient.admin().indices().prepareCreate("test");

		XContentBuilder mappingMessageTest1 = XContentFactory.jsonBuilder()//
				.startObject()//
				.startObject("properties")//

				.startObject("id").field("type", "text").startObject("fields").startObject("keyword")
				.field("type", "keyword").field("ignore_above", 256).endObject().endObject()//
				.endObject()//

				.startObject("name").field("type", "text").endObject()//
				.startObject("nowdate").field("type", "long").endObject()//
				.startObject("age").field("type", "integer").endObject()//
				.startObject("money").field("type", "double").endObject()//
				.startObject("useable").field("type", "boolean").endObject()//


				
				

				.endObject()//
				.endObject();//

		cibMessageTest1.addMapping("test", mappingMessageTest1);
		cibMessageTest1.execute().actionGet();

		/** for循环请求数据结束 **/
		return new JsonMsgUtil(true, "添加测试数据成功", "");
	}

}
