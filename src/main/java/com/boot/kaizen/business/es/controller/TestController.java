package com.boot.kaizen.business.es.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.util.FileUtil;
import com.boot.kaizen.util.JsonMsgUtil;

@Controller
@RequestMapping("/app")
public class TestController {

	@Autowired
	private TransportClient transportClient;

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

				.startObject("lte_address").field("type", "text")//
				/*.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
*/				.endObject()//

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
			/*	.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
*/				.endObject()//

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
				/*.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
*/				.endObject()//

				.startObject("lte_site_tall").field("type", "text").endObject()//

				.startObject("lte_site_type").field("type", "text").endObject()//

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
				//.put("index.number_of_shards", 3)//  这个东西不能动态的更新 只能在创建映射的时候设置
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
				
				//=================================
				
				.startObject("proIndicators")
				      .startObject("properties")
				      		.startObject("servingCellPccTac").field("type", "text").endObject()//
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
					
				
				
				//=================================
				
				
				
				.startObject("doorDataInfoBeans")
					.startObject("properties")
					
						.startObject("ci").field("type", "text")
						.endObject()//
						
						.startObject("dl").field("type", "float").endObject()//
						.startObject("earfcn").field("type", "long").endObject()//
						
						.startObject("mLteNeighborhoodInfos")//
					   		.startObject("properties")//
						   		
						   		.startObject("mBand")//
						   			.field("type", "text")//
						   		.endObject()	//
						   		
						   		.startObject("mLteNeighborhoodCI")//
						   			.field("type", "text")//
						   		.endObject()	//
						   		
						   		.startObject("mLteNeighborhoodEarfcn")//
						   			.field("type", "text")//
						   		.endObject()	//
						   		
						   		.startObject("mLteNeighborhoodPCI")//
						   			.field("type", "text")//
						   		.endObject()	//
						   		
						   		.startObject("mLteNeighborhoodRSRPOrSINR")//
						   			.field("type", "text")//
						   		.endObject()	//
						   		
						   		.startObject("mLteNeighborhoodRsrq")//
						   			.field("type", "text")//
						   		.endObject()	//
						   		
						   		.startObject("mLteNeighborhoodTAC")//
						   			.field("type", "text")//
						   		.endObject()	//
						   		
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
						.endObject()
					.endObject()//
				.endObject()//

				.startObject("earfcn")
					.field("type", "text")//
				.endObject()//


				.startObject("rootSupport")
				.field("type", "text")//
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

				.startObject("mSignaBean")
					.startObject("properties")//
						
						.startObject("ci")//
							.field("type", "long")//
						.endObject()//
						
					/*	.startObject("formatTimestamp")
							.field("type", "text")//
							.startObject("fields")//
								.startObject("keyword")//
									.field("type", "keyword")//
									.field("ignore_above", 256)//
								.endObject()//
							.endObject()//
						.endObject()//
*/						
						.startObject("mChannelType")
							.field("type", "text")//
							/*.startObject("fields")//
								.startObject("keyword")//
									.field("type", "keyword")//
									.field("ignore_above", 256)//
								.endObject()//
							.endObject()//
*/						.endObject()//
						
						.startObject("mLatitude")//
							.field("type", "float")//
						.endObject()//
						
						.startObject("mLongitude")//
							.field("type", "float")//
						.endObject()//
						
						.startObject("mMeaasge")
							.field("type", "text")//
							.startObject("fields")//
								.startObject("keyword")//
									.field("type", "keyword")//
									.field("ignore_above", 256)//
								.endObject()//
							.endObject()//
						.endObject()//
						
						.startObject("mMessageType")
							.field("type", "text")//
							/*.startObject("fields")//
								.startObject("keyword")//
									.field("type", "keyword")//
									.field("ignore_above", 256)//
								.endObject()//
							.endObject()//
*/						.endObject()//
						
						
						.startObject("mSignaTimestamp")
							.field("type", "text")//
							/*.startObject("fields")//
								.startObject("keyword")//
									.field("type", "keyword")//
									.field("ignore_above", 256)//
								.endObject()//
							.endObject()//
*/						.endObject()//
						
						
						
						.startObject("mSingaType")
							.field("type", "text")//
							/*.startObject("fields")//
								.startObject("keyword")//
									.field("type", "keyword")//
									.field("ignore_above", 256)//
								.endObject()//
							.endObject()//
*/						.endObject()//
						
						.startObject("mTimestamp")//
							.field("type", "long")//
						.endObject()//
						
					.endObject()//
				.endObject()//

				.startObject("mSignaEventBean")
					.startObject("properties")
						.startObject("ci")
							.field("type", "long")//
						.endObject()//
						
						.startObject("formatTimestamp")
							.field("type", "text")//
						/*	.startObject("fields")//
								.startObject("keyword")//
									.field("type", "keyword")//
									.field("ignore_above", 256)//
								.endObject()//
							.endObject()//
*/						.endObject()//
						
						.startObject("mEvent")
							.field("type", "text")//
							/*.startObject("fields")//
								.startObject("keyword")//
									.field("type", "keyword")//
									.field("ignore_above", 256)//
								.endObject()//
							.endObject()//
*/						.endObject()//
						
						.startObject("mEventType")
							.field("type", "text")//
							/*.startObject("fields")//
								.startObject("keyword")//
									.field("type", "keyword")//
									.field("ignore_above", 256)//
								.endObject()//
							.endObject()//
*/						.endObject()//

						.startObject("mLatitude")
							.field("type", "float")//
						.endObject()//

						.startObject("mLongitude")
							.field("type", "float")//
						.endObject()//
						
						.startObject("mSignaTimestamp")
							.field("type", "text")//
							/*.startObject("fields")//
								.startObject("keyword")//
									.field("type", "keyword")//
									.field("ignore_above", 256)//
								.endObject()//
							.endObject()//
*/						.endObject()//
						
						.startObject("mTimestamp")
							.field("type", "long")//
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
				/*.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
*/				.endObject()//

				.startObject("sinr").field("type", "text")//
				.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
				.endObject()//

				.startObject("siteLat").field("type", "text")//
				/*.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
*/				.endObject()//

				.startObject("siteLng").field("type", "text")//
				/*.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
*/				.endObject()//

				.startObject("tAC").field("type", "text")//
				/*.startObject("fields")//
				.startObject("keyword")//
				.field("type", "keyword")//
				.field("ignore_above", 256)//
				.endObject().endObject()//
*/				.endObject()//

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
	public static final List<String> INDEX_LIST = Arrays.asList("simgc", "logmain", "logmessage");

	/**
	 * 获取mapping信息
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年4月14日 下午2:29:12
	 */
	@ResponseBody
	@GetMapping(value = "/mappings")
	public Map<String, Object> settings(@RequestParam(value="index") String indexVal) throws IOException {
		Map<String, Object> resultMap=new HashMap<>();
		if (StringUtils.isNotBlank(indexVal) ) {
			ImmutableOpenMap<String, MappingMetaData> mappings = transportClient.admin().cluster().prepareState()
					.execute().actionGet().getState().getMetaData().getIndices().get(indexVal).getMappings();
			String mapping = mappings.get(indexVal).source().toString();
			resultMap.put(indexVal, JSONObject.parseObject(mapping));
		}else {
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
	
}
