package com.boot.kaizen.business.es.controller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.boot.kaizen.business.buss.model.LogAnaLyze;
import com.boot.kaizen.business.buss.service.ILogAnaLyzeService;
import com.boot.kaizen.business.buss.service.IOneButtonTestService;
import com.boot.kaizen.business.buss.service.IOutHomeService;
import com.boot.kaizen.business.es.model.MainLogModel;
import com.boot.kaizen.business.es.model.OneButtonTest;
import com.boot.kaizen.business.es.model.OutHomeLogModel;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.business.es.model.logModel.HttpzbBean;
import com.boot.kaizen.business.es.model.logModel.MLteNeighborhoodInfo;
import com.boot.kaizen.business.es.model.logModel.MSignaBean;
import com.boot.kaizen.business.es.model.logModel.OurDoorDataInfoBean;
import com.boot.kaizen.business.es.model.logModel.PingzbBean;
import com.boot.kaizen.business.es.model.logModel.ProIndicatorsSimple;
import com.boot.kaizen.business.es.model.logModel.SignalDataBean;
import com.boot.kaizen.business.es.model.logModel.YyzbBean;
import com.boot.kaizen.business.es.model.sim.CommonModel;
import com.boot.kaizen.business.es.model.sim.GcModel;
import com.boot.kaizen.business.es.model.sim.ResultModel;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.business.es.service.IEsBussService;
import com.boot.kaizen.business.student.model.UserManager;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.RequestParamEntity;
import com.boot.kaizen.model.SysProject;
import com.boot.kaizen.service.SysProjectService;
import com.boot.kaizen.util.AEStest;
import com.boot.kaizen.util.CsvUtils;
import com.boot.kaizen.util.FileUtil;
import com.boot.kaizen.util.GeoLngLatUtil;
import com.boot.kaizen.util.HttpUtil;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.MyDateUtil;
import com.boot.kaizen.util.MyUtil;
import com.boot.kaizen.util.TableResultUtil;
import com.boot.kaizen.util.UserUtil;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.URLUtil;

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
	@Autowired
	private SysProjectService sysProjectService;

	@Autowired
	private IOutHomeService outHomeService;

	@Value("${sim.gcCityPermission}")
	private String gcCityPermission;
	@Value("${sim.gcAllPermission}")
	private String gcAllPermission;

	@Value("${files.path}")
	private String filesCommonPath;

	@Autowired
	private ILogAnaLyzeService logAnaLyzeService;
	@Autowired
	private IOneButtonTestService oneButtonTestService;

	private static final double MINT_UNIT = 0.01;// 千米

	/**
	 * 
	 * page: 1 limit: 10 endTime: "" beginTime: "" index: "logouthome" type:
	 * "logouthome" + termMap
	 * 
	 * @Description: 室外测试查询20220410调整为mysql数据库版本
	 * @author weichengz
	 * @date 2019年11月11日 上午11:21:32
	 */
	@ResponseBody
	@PostMapping(value = "/queryOutHome")
	public TableResultUtil analyzeLteAllAndComplete(@RequestBody QueryParamData queryParamData) {

		Map<String, Object> clearMapEmptyVal = MyUtil.clearMapEmptyValRemoveKeyword(queryParamData.getTermMap());
		clearMapEmptyVal.put("logType", "0");

		LoginUser loginUser = UserUtil.getLoginUser();
		if (loginUser == null) {
			throw new IllegalArgumentException("当前登陆用户失效");
		}
		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于任何地市");
		} else {
			if (StringUtils.isBlank(sysProject.getProjCode())) {
				throw new IllegalArgumentException("该地市【" + sysProject.getProjName() + "】对应的sim地市不存在");
			}
		}
		clearMapEmptyVal.put("cityId", sysProject.getId() + "");
		// 处理精确查询的问题
		queryParamData.setTermMap(clearMapEmptyVal);// 精确查询

		// 处理时间范围的字段
		String beginTime = queryParamData.getBeginTime();
		String endTime = queryParamData.getEndTime();
		Map<String, Map<String, Object>> mapRange = new HashMap<>();
		if (StringUtils.isNotBlank(beginTime)) {
			mapRange.put("beginTime",
					MyUtil.createHashMap("GTE~" + MyDateUtil.stringToDate(beginTime, null).getTime()));
		}
		if (StringUtils.isNotBlank(endTime)) {
			mapRange.put("endTime", MyUtil.createHashMap("LTE~" + MyDateUtil.stringToDate(endTime, null).getTime()));
		}
		// 将查询条件给查询参数
		PageInfo<UserManager> pageInfo = PageHelper.startPage(queryParamData.getPage(), queryParamData.getLimit())
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						RequestParamEntity requestParamEntity = new RequestParamEntity();
						requestParamEntity.setMapAnd(clearMapEmptyVal);
						requestParamEntity.setMapRange(mapRange);
						outHomeService.selectList(MyUtil.createQueryPlus(requestParamEntity));
					}
				});
		return new TableResultUtil(0L, "操作成功", pageInfo.getTotal(), pageInfo.getList());
	}

	/**
	 * 
	 * @Description: 左下角邻区信息查询，这里将邻区的小区名字也做返回，注意只返回唯一 这个地方必须要传递经度、纬度
	 * @author weichengz
	 * @date 2020年4月28日 上午10:18:17
	 */
	@ResponseBody
	@PostMapping(value = "/queryList")
	public Object queryList(@RequestBody QueryParamData queryParamData) {

		LoginUser loginUser = UserUtil.getLoginUser();
		if (loginUser == null) {
			throw new IllegalArgumentException("当前登陆用户失效");
		}

		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于任何地市");
		} else {
			if (StringUtils.isBlank(sysProject.getProjCode())) {
				throw new IllegalArgumentException("该地市【" + sysProject.getProjName() + "】对应的sim地市不存在");
			}
		}

		List<String> revelFields = queryParamData.getRevelFields();
		if (revelFields == null || revelFields.size() == 0) {
			throw new IllegalArgumentException("要显示的字段不能为空");
		}

		if (!revelFields.contains("longitude") || !revelFields.contains("latitude")
				|| !revelFields.contains("doorDataInfoBeans")) {
			throw new IllegalArgumentException("查询的字段(longitude、latitude、doorDataInfoBeans)不能为空");
		}

		if (queryParamData != null) {
			queryParamData.setLimit(1);//
			List<Map<String, Object>> queryList = Esutil.queryList(queryParamData);
			if (queryList != null && queryList.size() > 0) {
				List<Map<String, Object>> result = new ArrayList<>();// 返回的结果

				Map<String, Object> map = queryList.get(0);
				MainLogModel mainLogModel = JSONObject.parseObject(JSONObject.toJSONString(map), MainLogModel.class);
				Object doorDataInfoBeans = map.get("doorDataInfoBeans");
				if (doorDataInfoBeans != null) {
					OurDoorDataInfoBean ourDoorDataInfoBean = JSONObject
							.parseObject(JSONObject.toJSONString(doorDataInfoBeans), OurDoorDataInfoBean.class);
					if (ourDoorDataInfoBean != null) {
						ArrayList<MLteNeighborhoodInfo> mLteNeighborhoodInfos = ourDoorDataInfoBean
								.getmLteNeighborhoodInfos();
						if (mLteNeighborhoodInfos != null && mLteNeighborhoodInfos.size() > 0) {
							QueryParamData queryParamData2 = new QueryParamData("simgc", "simgc");
							queryParamData2.setLimit(1);
							// 处理 这个经纬度的问题
							GeoPoint ceterPoint = new GeoPoint(Double.valueOf(mainLogModel.getLatitude()),
									Double.valueOf(mainLogModel.getLatitude()));
							queryParamData2.dealGeoDiatanceBuss(ceterPoint, 10000D, "location");

							for (MLteNeighborhoodInfo mLteNeighborhoodInfo : mLteNeighborhoodInfos) {
								String mLteNeighborhoodPCI = mLteNeighborhoodInfo.getMLteNeighborhoodPCI();
								if (StringUtils.isNotBlank(mLteNeighborhoodPCI)) {
									// 处理该地市的查询条件
									Map<String, Object> termMap = queryParamData2.getTermMap();
									if (termMap == null) {
										termMap = new HashMap<>();
									}
									termMap.put("lte_city_name.keyword", sysProject.getProjCode() + "");
									termMap.put("lte_phycellid.keyword", mLteNeighborhoodPCI);
									queryParamData2.setTermMap(termMap);

									List<Map<String, Object>> queryList2 = Esutil.queryList(queryParamData2);
									if (queryList2 != null && queryList2.size() > 0) {
										Map<String, Object> cellMap = queryList2.get(0);
										Object lte_site_name = cellMap.get("lte_site_name");
										mLteNeighborhoodInfo.setSiteName("");
										if (lte_site_name != null) {
											mLteNeighborhoodInfo.setSiteName(lte_site_name.toString());
										}
									}
								}
							}
						}
					}
					map.put("doorDataInfoBeans", ourDoorDataInfoBean);
				}
				result.add(map);
				return result;
			}
		}
		return new ArrayList<>();
	}

	/**
	 * 室外测试导出csv
	 * 
	 * @Description: 20200410导出csv
	 * @author weichengz
	 * @date 2020年4月10日 上午11:03:25
	 */
	@ResponseBody
	@RequestMapping(value = "/exportOutHomeCsv",method= {RequestMethod.GET,RequestMethod.POST})
	public void exportOutHomeCsv(HttpServletResponse response, @RequestParam(value = "id") String id)
			throws IOException {

		if (StringUtils.isBlank(id)) {
			throw new IllegalArgumentException("室外测试的id不能为空");
		}
		OutHomeLogModel outHomeLogModel = outHomeService.selectById(id);

		if (outHomeLogModel == null) {
			throw new IllegalArgumentException("室外测试信息不存在");
		}
		String filePath = outHomeLogModel.getFilePath();
		if (StringUtils.isBlank(filePath)) {// /outhomelog/20200114110619_d41fbf38-98b4-427f-ba18-2ca1903e85d1.txt
			throw new IllegalArgumentException("filePath参数不存在");
		}
		String outHomePath = filePath.toString();
		File file = new File(filesCommonPath + outHomePath);

		if (file != null && file.exists() && file.length() > 0) {
			String fileName = MyDateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
			// csv处理代码
			ServletOutputStream csvResult = response.getOutputStream();
			response.setContentType("multipart/form-data");
			//response.setCharacterEncoding("utf-8");
			response.setHeader("Content-disposition",
					"attachment;filename=" + URLUtil.encode(fileName, StringUtil.UTF8) + ".csv");
			String[] head = new String[] { "时间", "手机型号", "运营商", "MCC", "MNC", "网络类型", "经度", "纬度", "速度", "高度", "应用层事件",
					"上行速率", "下行速率", "Ping时延", "Http时延"//
					, "CGI", "基站号", "小区中文名", "小区号", "TAC", "频点", "PCI", "RSRQ", "RSRP", "SINR"//
					, "邻区1频点", "邻区1PCI", "邻区1RSRQ", "邻区1RSRP"//
					, "邻区2频点", "邻区2PCI", "邻区2RSRQ", "邻区2RSRP"//
					, "邻区3频点", "邻区3PCI", "邻区3RSRQ", "邻区3RSRP"//
					, "邻区4频点", "邻区4PCI", "邻区4RSRQ", "邻区4RSRP"//
					, "邻区5频点", "邻区5PCI", "邻区5RSRQ", "邻区5RSRP"//
					, "邻区6频点", "邻区6PCI", "邻区6RSRQ", "邻区6RSRP"//
			};
			List<Object[]> result = new ArrayList<>();

			BufferedReader bufferedReader = null;
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String str = null;// 文件里面一行记录
			while ((str = bufferedReader.readLine()) != null) {
				SignalDataBean signalDataBean = JSONObject.parseObject(str, SignalDataBean.class);
				// 先将百度经纬度转为wgs84
				signalDataBean.dealLngLatBdToWgs84();
				Object[] objects = new Object[49];// 参考这个类SignalCsvLogBean
				objects[0] = MyUtil.nullToEmpty(signalDataBean.getTestTime());
				objects[1] = MyUtil.nullToEmpty(null);
				objects[2] = MyUtil.nullToEmpty(null);
				objects[3] = MyUtil.nullToEmpty(null);
				objects[4] = MyUtil.nullToEmpty(null);
				objects[5] = MyUtil.nullToEmpty(signalDataBean.getNetWorkType());
				objects[6] = MyUtil.nullToEmpty(signalDataBean.getLongitude());
				objects[7] = MyUtil.nullToEmpty(signalDataBean.getLatitude());
				objects[8] = MyUtil.nullToEmpty(signalDataBean.getsPEED());
				objects[9] = MyUtil.nullToEmpty(null);
				objects[10] = MyUtil.nullToEmpty(signalDataBean.getNormalEventType()) + "/"
						+ MyUtil.nullToEmpty(signalDataBean.getAbNormalEventType());

				objects[11] = MyUtil.nullToEmpty(signalDataBean.getUpLoadSpeed());
				objects[12] = MyUtil.nullToEmpty(signalDataBean.getDownLoadSpeed());
				objects[13] = MyUtil.nullToEmpty(null);// 先设置为""
				PingzbBean pingzbBean = signalDataBean.getPingzbBean();
				if (pingzbBean != null) {
					String pjsy = pingzbBean.getPjsy();
					objects[13] = MyUtil.nullToEmpty(pjsy);// 先设置为""
				}
				objects[14] = MyUtil.nullToEmpty(null);
				HttpzbBean httpzbBean = signalDataBean.getHttpzbBean();
				if (httpzbBean != null) {
					String pjsy = httpzbBean.getPjsy();
					objects[14] = MyUtil.nullToEmpty(pjsy);
				}
				objects[15] = MyUtil.nullToEmpty(signalDataBean.getcI());
				objects[16] = MyUtil.nullToEmpty(signalDataBean.geteNB());
				objects[17] = MyUtil.nullToEmpty(signalDataBean.getCellName());
				objects[18] = MyUtil.nullToEmpty(signalDataBean.getcELLID());
				objects[19] = MyUtil.nullToEmpty(signalDataBean.gettAC());
				objects[20] = MyUtil.nullToEmpty(signalDataBean.getEarfcn());
				objects[21] = MyUtil.nullToEmpty(signalDataBean.getPci());

				objects[22] = MyUtil.nullToEmpty(null);
				objects[23] = MyUtil.nullToEmpty(null);
				objects[24] = MyUtil.nullToEmpty(null);

				objects[25] = MyUtil.nullToEmpty(null);
				objects[26] = MyUtil.nullToEmpty(null);
				objects[27] = MyUtil.nullToEmpty(null);
				objects[28] = MyUtil.nullToEmpty(null);

				objects[29] = MyUtil.nullToEmpty(null);
				objects[30] = MyUtil.nullToEmpty(null);
				objects[31] = MyUtil.nullToEmpty(null);
				objects[32] = MyUtil.nullToEmpty(null);

				objects[33] = MyUtil.nullToEmpty(null);
				objects[34] = MyUtil.nullToEmpty(null);
				objects[35] = MyUtil.nullToEmpty(null);
				objects[36] = MyUtil.nullToEmpty(null);

				objects[37] = MyUtil.nullToEmpty(null);
				objects[38] = MyUtil.nullToEmpty(null);
				objects[39] = MyUtil.nullToEmpty(null);
				objects[40] = MyUtil.nullToEmpty(null);

				objects[41] = MyUtil.nullToEmpty(null);
				objects[42] = MyUtil.nullToEmpty(null);
				objects[43] = MyUtil.nullToEmpty(null);
				objects[44] = MyUtil.nullToEmpty(null);

				objects[45] = MyUtil.nullToEmpty(null);
				objects[46] = MyUtil.nullToEmpty(null);
				objects[47] = MyUtil.nullToEmpty(null);
				objects[48] = MyUtil.nullToEmpty(null);

				OurDoorDataInfoBean doorDataInfoBeans = signalDataBean.getDoorDataInfoBeans();
				if (doorDataInfoBeans != null) {
					String rsrq = doorDataInfoBeans.getRsrq();
					objects[22] = MyUtil.nullToEmpty(rsrq);
					String rsrp = doorDataInfoBeans.getRsrp();
					objects[23] = MyUtil.nullToEmpty(rsrp);
					int sinr = doorDataInfoBeans.getSinr();
					objects[24] = MyUtil.nullToEmpty((sinr + "").toString());

					ArrayList<MLteNeighborhoodInfo> getmLteNeighborhoodInfos = doorDataInfoBeans
							.getmLteNeighborhoodInfos();
					if (getmLteNeighborhoodInfos != null && getmLteNeighborhoodInfos.size() > 0) {
						int num = 25;
						for (int i = 0; i < getmLteNeighborhoodInfos.size(); i++) {
							MLteNeighborhoodInfo model = getmLteNeighborhoodInfos.get(i);
							if (i <= 5) {
								objects[num] = MyUtil.nullToEmpty(model.getmLteNeighborhoodEarfcn());
								objects[num + 1] = MyUtil.nullToEmpty(model.getMLteNeighborhoodPCI());
								objects[num + 2] = MyUtil.nullToEmpty(model.getMLteNeighborhoodRsrq());
								objects[num + 3] = MyUtil.nullToEmpty(model.getmLteNeighborhoodRSRPOrSINR());
								num = num + 4;
							}
						}
					}
				}
				result.add(objects);
			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			CsvUtils.simpleExport(true, "\n", head, result, fileName, csvResult);
		}
	}

	/**
	 * sim工参查询
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月29日 上午11:29:10
	 */
	@ResponseBody
	@PostMapping(value = "/querySimGc")
	public TableResultUtil querySimGc(@RequestBody QueryParamData queryParamData) {
		LoginUser loginUser = UserUtil.getLoginUser();
		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于当前项目");
		}
		Map<String, Object> clearMapEmptyVal = MyUtil.clearMapEmptyVal(queryParamData.getTermMap());

		boolean hasPermission = UserUtil.hasPermission(gcAllPermission);
		if (hasPermission) {
			if (StringUtils.isBlank(sysProject.getId().toString())) {
				throw new IllegalArgumentException("该用户不属于任何地市");
			}
			clearMapEmptyVal.put("cityId.keyword", sysProject.getId() + "");
		} else {
			clearMapEmptyVal.put("lte_city_name.keyword", sysProject.getProjCode() + "");
		}

		queryParamData.setTermMap(clearMapEmptyVal);// 精确查询
		QueryParamData paramData = Esutil.queryPage(queryParamData);
		return new TableResultUtil(0L, "操作成功", paramData.getTotalNums(), paramData.getRows());
	}

	/**
	 * 查询工参里面得去重经纬度信息
	 */
	@ResponseBody
	@RequestMapping(value = "/distinctGcLonLat")
	public List<Map<String, Object>> distinctGcLonLat(@RequestBody QueryParamData queryParamData) {

		List<Map<String, Object>> results = new ArrayList<>();// 查询返回得结果
		// 校验
		queryParamData.verificationIndexType();

		LoginUser loginUser = UserUtil.getLoginUser();
		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于当前项目");
		}
		Map<String, Object> clearMapEmptyVal = MyUtil.clearMapEmptyVal(queryParamData.getTermMap());
		clearMapEmptyVal.put("cityId.keyword", sysProject.getId() + "");
		queryParamData.setTermMap(clearMapEmptyVal);

		SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(queryParamData.getIndex())
				.setTypes(queryParamData.getType());
		// 查询条件
		BoolQueryBuilder boolQueryBuilder = Esutil.getBoolQueryBuilder(queryParamData);

		SearchResponse searchResponse = searchRequestBuilder
				.setQuery(boolQueryBuilder).addAggregation(AggregationBuilders//
						.terms("lte_longitude2")//
						.field("lte_longitude2.keyword")//
						.order(BucketOrder.key(true))//
						.subAggregation(AggregationBuilders.terms("lte_latitude2").field("lte_latitude2.keyword")
								.size(1000000000))// 一亿
						.size(1000000000))// 一亿
				.get();

		Aggregations aggregations = searchResponse.getAggregations();
		StringTerms aggTerms = aggregations.get("lte_longitude2");

		List<org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket> buckets = aggTerms.getBuckets();

		for (org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket bucket : buckets) {
			String lonKey = bucket.getKey().toString();
			if (StringUtils.isNoneBlank(lonKey)) {
				StringTerms latTerms = bucket.getAggregations().get("lte_latitude2");
				if (latTerms != null) {
					List<org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket> buckets2 = latTerms
							.getBuckets();
					if (buckets2 != null && buckets2.size() > 0) {
						for (org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket bucket2 : buckets2) {
							String latKey = bucket2.getKey().toString();
							if (StringUtils.isNotBlank(latKey)) {
								Map<String, Object> latLonMap = new HashMap<>();
								latLonMap.put("lte_longitude2", lonKey);
								latLonMap.put("lte_latitude2", latKey);
								results.add(latLonMap);
							}
						}
					}
				}
			}
		}
		return results;
	}

	/**
	 * 滚动查询sim工参
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年1月7日 上午9:44:02
	 */
	@ResponseBody
	@PostMapping(value = "/scrollQuerySimGc")
	public List<Map<String, Object>> scrollQuerySimGc(@RequestBody QueryParamData queryParamData) {

		LoginUser loginUser = UserUtil.getLoginUser();
		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于当前项目");
		}
		Map<String, Object> clearMapEmptyVal = MyUtil.clearMapEmptyVal(queryParamData.getTermMap());

		// clearMapEmptyVal.put("cityId.keyword", sysProject.getId() + ""); 这个是查询全国的
		queryParamData.setTermMap(clearMapEmptyVal);// 精确查询

		List<Map<String, Object>> scrollQuery = Esutil.scrollQuery(queryParamData);
		return scrollQuery;
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
	 * 一键测试 app上传添加
	
	@ResponseBody
	@PostMapping(value = "/addOneButtonTest")
	public JsonMsgUtil oneButtonTestAdd(@RequestBody OneButtonTestParam oneButtonTestParam) {

		if (oneButtonTestParam == null) {
			throw new IllegalArgumentException("接收的数据为空");
		}

		String projId = oneButtonTestParam.getProjId();
		if (StringUtils.isBlank(projId)) {
			throw new IllegalArgumentException("未传入项目号项目");
		}

		List<OneButtonTest> datas = oneButtonTestParam.getDatas();
		if (datas == null || datas.size() == 0) {
			throw new IllegalArgumentException("上传的数据为空");
		}

		for (OneButtonTest oneButtonTest : datas) {
			oneButtonTest.setId(MyUtil.getUuid());
			oneButtonTest.setCityId(projId);
			Date date = MyDateUtil.stringToDate(oneButtonTest.getTestTime(), "yyyy-MM-dd HH:mm:ss");
			if (date != null) {
				oneButtonTest.setTestTimeQuery(date.getTime());
			}
			oneButtonTest.dealLngLatBdToWgs84();
			oneButtonTestService.insert(oneButtonTest);
			// Esutil.insert("onebuttontest", "onebuttontest",
			// JSONObject.toJSONString(oneButtonTest));
		}
		// oneButtonTestService.insertBatch(datas, 50);

		return new JsonMsgUtil(true, "添加成功", "");
	} */

	/**
	 * 
	 * @Description: 室外测试的删出 这个ids是 室外测试的id 非文档id 仅仅支持单个删出
	 * @author weichengz
	 * @date 2019年11月13日 上午11:27:46
	 */
	@ResponseBody
	@PostMapping(value = "/deleteOutHome")
	public JsonMsgUtil deleteOutHome(@RequestParam("ids") String ids) {
		if (StringUtils.isNoneBlank(ids)) {
			String[] idsArray = ids.trim().split(",");
			for (String id : idsArray) {

				OutHomeLogModel outHomeLogModel = outHomeService.selectById(id);

				if (outHomeLogModel != null) {
					// 删除主日志
					QueryParamData queryParamDataMsg = new QueryParamData("logmain", "logmain",
							MyUtil.createHashMap("pid.keyword~" + id), null, 1000);
					Esutil.deleteBatchByCondition(queryParamDataMsg);

					// 删除大信息字段
					QueryParamData queryParamDataEvent = new QueryParamData("logmessage", "logmessage",
							MyUtil.createHashMap("ppid.keyword~" + id), null, 1000);
					Esutil.deleteBatchByCondition(queryParamDataEvent);

					// 删除室外测试的log文件
					String filePath = outHomeLogModel.getFilePath();
					if (StringUtils.isNotBlank(filePath)) {
						File file = new File(filesCommonPath + filePath);
						if (file.exists()) {
							file.delete();
						}
					}

					// 删出室外测试记录
					outHomeService.deleteById(id);

					// 删除室外测试测统计分析的数据
					RequestParamEntity param = new RequestParamEntity();
					param.setMapAnd(MyUtil.createHashMap("pid~" + id));
					EntityWrapper<LogAnaLyze> entityWrapper = MyUtil.createQueryPlus(param);
					logAnaLyzeService.delete(entityWrapper);
				}
			}
			try {
				Thread.sleep(800);// 休眠
			} catch (InterruptedException e) {
				return new JsonMsgUtil(false, "error:" + e.getMessage(), "");
			}
		} else {
			return new JsonMsgUtil(false, "索引、类型、文档ids不能为空", "");
		}
		return new JsonMsgUtil(true, "删出成功", "");
	}

	/**
	 * 
	 * age: 1 limit: 10 endTime: "" beginTime: "2020-04-06 00:00:00" index:
	 * "onebuttontest" type: "onebuttontest"
	 * 
	 * termMap: {operatorService.keyword: "", city.keyword: "", testPerson.keyword:
	 * ""} operatorService.keyword: "" city.keyword: "" testPerson.keyword: ""
	 * 
	 * @Description: 一键测试列表查询
	 * @author weichengz
	 * @date 2019年11月13日 上午9:59:12
	 */
	@ResponseBody
	@PostMapping(value = "/queryOneButtonTest")
	public TableResultUtil queryOneButtonTest(@RequestBody QueryParamData queryParamData) {
		// 模糊查询
		Map<String, Object> clearMapEmptyVal = MyUtil.clearMapEmptyValRemoveKeyword(queryParamData.getTermMap());
		LoginUser loginUser = UserUtil.getLoginUser();
		if (loginUser == null) {
			throw new IllegalArgumentException("当前登陆用户失效");
		}
		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于任何地市");
		}

		// 精确查询
		Map<String, Object> mapCity = new HashMap<>();
		mapCity.put("cityId", sysProject.getId() + "");

		// 处理时间范围的字段
		String beginTime = queryParamData.getBeginTime();
		String endTime = queryParamData.getEndTime();
		Map<String, Map<String, Object>> mapRange = new HashMap<>();
		if (StringUtils.isNotBlank(beginTime)) {
			mapRange.put("testTime", MyUtil.createHashMap("GTE~" + beginTime));
		}
		if (StringUtils.isNotBlank(endTime)) {
			mapRange.put("testTime", MyUtil.createHashMap("LTE~" + endTime));
		}

		// 将查询条件给查询参数
		PageInfo<OneButtonTest> pageInfo = PageHelper.startPage(queryParamData.getPage(), queryParamData.getLimit())
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						RequestParamEntity requestParamEntity = new RequestParamEntity();
						requestParamEntity.setMapAnd(mapCity);
						requestParamEntity.setMapLike(clearMapEmptyVal);
						requestParamEntity.setMapRange(mapRange);
						oneButtonTestService.selectList(MyUtil.createQueryPlus(requestParamEntity));
					}
				});
		return new TableResultUtil(0L, "操作成功", pageInfo.getTotal(), pageInfo.getList());
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

	/**
	 * 
	 * @Description: 一键测试导出列表
	 * @author weichengz
	 * @date 2019年11月14日 上午10:01:48
	 */
	@ResponseBody
	@RequestMapping(value = "/exportOneButtonTest", method = RequestMethod.POST)
	public void exportOneButtonTest(HttpServletResponse response,
			@RequestParam(value = "ids", required = false) String ids) throws Exception {

		LoginUser loginUser = UserUtil.getLoginUser();
		if (loginUser == null) {
			throw new IllegalArgumentException("当前登陆用户失效");
		}
		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于任何地市");
		}

		// 精确查询
		Map<String, Object> mapCity = new HashMap<>();
		mapCity.put("cityId", sysProject.getId() + "");

		List<OneButtonTest> datas = new ArrayList<>();
		if (StringUtils.isBlank(ids)) {// 查询全部
			datas = oneButtonTestService.selectByMap(mapCity);
		} else {
			datas = oneButtonTestService.selectBatchIds(Arrays.asList(ids.trim().split(",")));
		}

		Collection<OneButtonTest> collection = new ArrayList<OneButtonTest>();
		if (datas != null && datas.size() > 0) {
			for (OneButtonTest jsonStr : datas) {
				collection.add(jsonStr);
			}
		}

		// 存入 变
		String[] headers = { "运营商", "网络类型", "城市", "区县", "测试人员", "手机型号", "手机号码", "IMSI", "测试时间", "纬度", "经度", "测试位置",
				"下载速度", "上传速度", "PIng", "http", "平均RSRP", "平均SINR", "小区名字", "站号", "CI", "频段", "PCI", "TAC" };
		esBussService.exportOneButtonTest(headers, collection, "一键测试信息", response);

	}

	/**
	 * 
	 * @Description: 室外测试导出列表
	 * @author weichengz
	 * @date 2019年11月14日 上午10:01:48
	 */
	@ResponseBody
	@RequestMapping(value = "/exportOutHomeTest", method = RequestMethod.POST)
	public void exportOutHomeTest(HttpServletResponse response,
			@RequestParam(value = "ids", required = false) String ids) throws Exception {

		List<OutHomeLogModel> outHomeLogModels = new ArrayList<>();

		if (StringUtils.isBlank(ids)) {// 查询全部
			Map<String, Object> paramMap=new HashMap<>();
			paramMap.put("logType", "0");
			outHomeLogModels = outHomeService.selectByMap(paramMap);
		} else {
			outHomeLogModels = outHomeService.selectBatchIds(Arrays.asList(ids.trim().split(",")));
		}

		Collection<OutHomeLogModel> collection = new ArrayList<OutHomeLogModel>();
		if (outHomeLogModels != null && outHomeLogModels.size() > 0) {
			collection.addAll(outHomeLogModels);
		}

		// 存入 变
		String[] headers = { "文件名", "文件上传日期", "运营商", "网络类型", "城市", "地市", "测试人员", "手机型号", "IMSI", "测试时长", "开始时间", "结束时间",
				"总里程", "覆盖率", "平均RSRP", "平均SINR", "下载平均速率", "上传平均速率" };
		esBussService.exportLogoutHomeTest(headers, collection, "室外测试信息", response);

	}

	/**
	 * 主服务小区查询 注意返回的经纬度 是根据方位角偏移后的经纬度
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月20日 下午2:39:20
	 */
	@ResponseBody
	@PostMapping(value = "/queryMainService")
	public List<Map<String, Object>> queryMainService(@RequestParam(value = "longitude") String longitude,
			@RequestParam(value = "latitude") String latitude, @RequestParam(value = "mainLogId") String mainLogId) {

		LoginUser loginUser = UserUtil.getLoginUser();
		if (loginUser == null) {
			throw new IllegalArgumentException("当前登陆用户失效");
		}

		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于任何地市");
		} else {
			if (StringUtils.isBlank(sysProject.getProjCode())) {
				throw new IllegalArgumentException("该地市【" + sysProject.getProjName() + "】对应的sim地市不存在");
			}
		}

		// 查询主log的 cELLID cI eNB
		Map<String, Object> termMainMap = new HashMap<>();
		termMainMap.put("longitude", longitude);
		termMainMap.put("latitude", latitude);
		termMainMap.put("pid", mainLogId);

		QueryParamData queryMainParamData = new QueryParamData("logmain", "logmain", termMainMap, null, 1);
		List<Map<String, Object>> queryList = Esutil.queryList(queryMainParamData);
		if (queryList != null && queryList.size() > 0) {

			Map<String, Object> map = queryList.get(0);
			String ci = hanleMapParamToString(map, "cI");

			Map<String, Object> termMap = new HashMap<String, Object>();
			termMap.put("lte_city_name.keyword", sysProject.getProjCode() + "");
			termMap.put("lte_ecgi.keyword", ci);
			QueryParamData queryParamData = new QueryParamData("simgc", "simgc", termMap,
					Arrays.asList("lte_longitude2", "lte_latitude2", "lte_azimuth"), 1);
			List<Map<String, Object>> datas = Esutil.queryList(queryParamData);
			if (datas != null && datas.size() > 0) {
				for (Map<String, Object> map2 : datas) {
					Object lte_longitude2 = map2.get("lte_longitude2");
					Object lte_latitude2 = map2.get("lte_latitude2");
					Object lte_azimuth = map2.get("lte_azimuth");
					try {
						if (lte_longitude2 != null && lte_latitude2 != null && lte_azimuth != null) {
							String convertDistanceToLogLat = GeoLngLatUtil.ConvertDistanceToLogLat(
									Double.valueOf(lte_longitude2.toString()), Double.valueOf(lte_latitude2.toString()),
									MINT_UNIT, Double.valueOf(lte_azimuth.toString()));
							if (convertDistanceToLogLat != null) {
								String[] split = convertDistanceToLogLat.split(",");
								if (split != null && split.length == 2) {
									map2.put("lte_longitude2", split[0]);
									map2.put("lte_latitude2", split[1]);
								}
							}
						}
					} catch (Exception e) {
						continue;
					}
				}
				return datas;
			} else {
				return new ArrayList<>();
			}
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * LTE Info、LTE Throughput 查询方法
	 * 
	 * @Description: mainLogId 室外测试的log ID
	 * @author weichengz
	 * @date 2020年4月28日 下午3:21:38
	 */
	@ResponseBody
	@PostMapping(value = "/queryLteInfoThrput")
	public Map<String, Object> queryLteInfoThrput(@RequestParam(value = "longitude") String longitude,
			@RequestParam(value = "latitude") String latitude, @RequestParam(value = "mainLogId") String mainLogId) {

		Map<String, Object> termMainMap = new HashMap<>();
		termMainMap.put("longitude", longitude);
		termMainMap.put("latitude", latitude);
		termMainMap.put("pid", mainLogId);

		QueryParamData queryMainParamData = new QueryParamData("logmain", "logmain", termMainMap, null, 1);

		List<Map<String, Object>> queryList = Esutil.queryList(queryMainParamData);
		Map<String, Object> resultMap = new HashMap<>();
		// 先赋值LTE Info、LTE Throughput
		resultMap.put("lteInfo", "");
		resultMap.put("lteThroughput", "");

		if (queryList != null && queryList.size() > 0) {
			Map<String, Object> map = queryList.get(0);
			MainLogModel mainLogModel = JSONObject.parseObject(JSONObject.toJSONString(map), MainLogModel.class);
			if (mainLogModel != null) {
				ProIndicatorsSimple proIndicators = mainLogModel.getProIndicators();
				String rootSupport = mainLogModel.getRootSupport();
				LinkedHashMap<Object, Object> lteInfo = new LinkedHashMap<>();
				if (("1").equals(rootSupport)) {// root的
					if (proIndicators != null) {
						lteInfo.put("Cell Name", mainLogModel.getCellName());
						lteInfo.put("TAC", proIndicators.getServingCellPccTac());
						lteInfo.put("Fequency DL", proIndicators.getServingCellPccFreqDl());
						lteInfo.put("eNodeBID", proIndicators.getServingCellPccEnodebId());
						lteInfo.put("ECI", proIndicators.getServingCellPccSiteEci());
						lteInfo.put("RSRP", proIndicators.getServingCellPccRsrp());
						lteInfo.put("RSRQ", proIndicators.getServingCellPccRsrq());
						lteInfo.put("CQI", proIndicators.getServingCellPccWidebandCqi());
						lteInfo.put("PUCCH TxPower", proIndicators.getServingCellPccPucchTxpower());
						lteInfo.put("PUSCH BLER", proIndicators.getServingCellPccULBLER());
						lteInfo.put("Band", proIndicators.getServingCellPccBandIndex());
						lteInfo.put("EARFCN", proIndicators.getServingCellPccEarfcnDl());
						lteInfo.put("CI", proIndicators.getServingCellPccCellId());
						lteInfo.put("PCI", proIndicators.getServingCellPccPci());
						lteInfo.put("SINR", proIndicators.getServingCellPccSinr());
						lteInfo.put("RSSI", proIndicators.getServingCellPccRssi());
						lteInfo.put("RI Num DL", proIndicators.getServingCellPccRankIndex());
						lteInfo.put("PUSCH TxPower", proIndicators.getServingCellPccPuschTxpower());
						lteInfo.put("PDSCH BLER", proIndicators.getServingCellPccDLBLER());
					}
				} else {// 非root的
					lteInfo.put("Cell Name", mainLogModel.getCellName());
					lteInfo.put("EARFCN", mainLogModel.getEarfcn());
					lteInfo.put("TAC", mainLogModel.gettAC());
					lteInfo.put("ECI", mainLogModel.getcI());
					lteInfo.put("eNodeB ID", "");
					lteInfo.put("CI", mainLogModel.getcELLID());
					lteInfo.put("PCI", mainLogModel.getPci());
					lteInfo.put("RSRP", mainLogModel.getRsrp());
					lteInfo.put("SINR", mainLogModel.getSinr());
					lteInfo.put("RSRQ", "");
					OurDoorDataInfoBean doorDataInfoBeans = mainLogModel.getDoorDataInfoBeans();
					if (doorDataInfoBeans != null) {
						lteInfo.put("RSRQ", doorDataInfoBeans.getRsrq());
					}
				}
				resultMap.put("lteInfo", lteInfo);

				LinkedHashMap<Object, Object> lteThroughput = new LinkedHashMap<>();
				if (proIndicators != null) {
					lteThroughput.put("APP", mainLogModel.getDownLoadSpeed() + " ~ " + mainLogModel.getUpLoadSpeed());
					lteThroughput.put("PDCP",
							proIndicators.getThroughputPccPdcpDl() + " ~ " + proIndicators.getThroughputPccPdcpUl());
					lteThroughput.put("RLC",
							proIndicators.getThroughputPccRlcDl() + " ~ " + proIndicators.getThroughputPccRlcUl());
					lteThroughput.put("MAC",
							proIndicators.getThroughputPccMacDl() + " ~ " + proIndicators.getThroughputPccMacUl());
					lteThroughput.put("PHY",
							proIndicators.getThroughputPccPhyDl() + " ~ " + proIndicators.getThroughputPccPhyUl());
				}

				resultMap.put("lteThroughput", lteThroughput);
			}
			return resultMap;
		}
		return new HashMap<>();
	}

	private String hanleMapParamToString(Map<String, Object> dataMap, String key) {
		if (StringUtils.isNotBlank(key)) {
			Object object = dataMap.get(key);
			if (object != null) {
				return object.toString();
			}
		}
		return "";
	}

	/**
	 * 对角坐标查询工参信息
	 */
	@ResponseBody
	@PostMapping(value = "/boundingBoxQuery")
	public List<Map<String, Object>> boundingBoxQuery(@RequestBody QueryParamData queryParamData) {
		LoginUser loginUser = UserUtil.getLoginUser();
		if (loginUser == null) {
			throw new IllegalArgumentException("当前登陆用户失效");
		}

		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于任何地市");
		} else {
			if (StringUtils.isBlank(sysProject.getProjCode())) {
				throw new IllegalArgumentException("该地市【" + sysProject.getProjName() + "】对应的sim地市不存在");
			}
		}

		// 处理一下对角坐标 这里面pid接收
		String pid = queryParamData.getPid();
		if (StringUtils.isNoneBlank(pid)) {
			String[] pointArray = pid.trim().split("_");// pid接收
			if (pointArray != null && pointArray.length == 4) {
				List<GeoPoint> points = new ArrayList<>();
				GeoPoint topLeft = new GeoPoint(Double.valueOf(pointArray[1]), Double.valueOf(pointArray[0]));
				GeoPoint bottomRight = new GeoPoint(Double.valueOf(pointArray[3]), Double.valueOf(pointArray[2]));
				points.add(topLeft);
				points.add(bottomRight);// 注意这个顺序

				Map<String, List<GeoPoint>> geoMap = new HashMap<>();
				geoMap.put("location", points);
				queryParamData.setGeoMap(geoMap);
			}

			// 处理本地市的查询条件
			Map<String, Object> termMap = queryParamData.getTermMap();
			if (termMap == null) {
				termMap = new HashMap<>();
			}

			termMap.put("cityId.keyword", sysProject.getProjCode() + "");// 加地市过滤的条件
			
			queryParamData.setTermMap(termMap);
			List<Map<String, Object>> scrollQueryList = Esutil.scrollQuery(queryParamData);
			return scrollQueryList;
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * 先找出这个可视化内的100个经纬度
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年3月27日 下午3:13:27
	 */
	@ResponseBody
	@PostMapping(value = "/boundingBoxQueryNew")
	public List<Map<String, Object>> boundingBoxQueryNew(@RequestBody QueryParamData queryParamData) {
		LoginUser loginUser = UserUtil.getLoginUser();
		if (loginUser == null) {
			throw new IllegalArgumentException("当前登陆用户失效");
		}

		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于任何地市");
		} else {
			if (StringUtils.isBlank(sysProject.getProjCode())) {
				throw new IllegalArgumentException("该地市【" + sysProject.getProjName() + "】对应的sim地市不存在");
			}
		}

		// 处理一下对角坐标 这里面pid接收
		String pid = queryParamData.getPid();
		if (StringUtils.isNoneBlank(pid)) {
			String[] pointArray = pid.trim().split("_");// pid接收
			if (pointArray != null && pointArray.length == 4) {
				List<GeoPoint> points = new ArrayList<>();
				GeoPoint topLeft = new GeoPoint(Double.valueOf(pointArray[1]), Double.valueOf(pointArray[0]));
				GeoPoint bottomRight = new GeoPoint(Double.valueOf(pointArray[3]), Double.valueOf(pointArray[2]));
				points.add(topLeft);
				points.add(bottomRight);// 注意这个顺序

				Map<String, List<GeoPoint>> geoMap = new HashMap<>();
				geoMap.put("location", points);
				queryParamData.setGeoMap(geoMap);
			}

			// 处理本地市的查询条件
			Map<String, Object> termMap = queryParamData.getTermMap();
			if (termMap == null) {
				termMap = new HashMap<>();
			}

			termMap.put("lte_city_name.keyword", sysProject.getProjCode() + "");// 加地市过滤的条件
			queryParamData.setTermMap(termMap);

			queryParamData.setLimit(400);
			List<Map<String, Object>> scrollQueryList = Esutil.queryList(queryParamData);
			if (scrollQueryList != null && scrollQueryList.size() > 0) {
				Set<String> sets = new HashSet<>();
				for (Map<String, Object> map : scrollQueryList) {
					Object lte_longitude2 = map.get("lte_longitude2");
					Object lte_latitude2 = map.get("lte_latitude2");
					if (lte_longitude2 != null && lte_latitude2 != null) {
						sets.add(lte_longitude2.toString() + "-" + lte_latitude2.toString());
					}
				}
				if (sets != null && sets.size() > 0) {
					List<Map<String, Object>> dataResult = new ArrayList<>();

					// 根据经纬度去查询共餐信息表
					QueryParamData queryParamData2Model = new QueryParamData(queryParamData.getIndex(),
							queryParamData.getType());
					queryParamData2Model.setRevelFields(queryParamData.getRevelFields());
					queryParamData2Model.setRevelPk(queryParamData.isRevelPk());
					queryParamData2Model.setLimit(20);

					Integer numFlag = 0;
					for (String logLat : sets) {
						if (numFlag > 21) {
							break;
						}

						String[] split = logLat.toString().split("-");
						Map<String, Object> termMapModel = termMap = new HashMap<>();
						;
						termMapModel.put("lte_city_name.keyword", sysProject.getProjCode() + "");// 加地市过滤的条件
						termMapModel.put("lte_longitude2.keyword", split[0]);
						termMapModel.put("lte_latitude2.keyword", split[1]);
						queryParamData2Model.setTermMap(termMapModel);

						List<Map<String, Object>> queryList = Esutil.queryList(queryParamData2Model);
						if (queryList != null && queryList.size() > 0) {
							dataResult.addAll(queryList);
						}

						numFlag++;
					}
					return dataResult;
				}
				return new ArrayList<>();

			}
			return new ArrayList<>();
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * 查询邻区的拉线问题 只查询距离最近的一个 如果一个站点 有多个邻区 那么就会有多个线
	 * 
	 * @Description: 查询距离某个最近的点 这里默认是 公里为单位 传入的是经纬度
	 * @author weichengz
	 * @date 2019年11月22日 下午2:37:43
	 */
	@ResponseBody
	@PostMapping(value = "/queryNearPoint")
	public List<Map<String, Object>> queryNearPoint(@RequestBody QueryParamData queryParamData) {

		LoginUser loginUser = UserUtil.getLoginUser();
		if (loginUser == null) {
			throw new IllegalArgumentException("当前登陆用户失效");
		}

		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于任何地市");
		} else {
			if (StringUtils.isBlank(sysProject.getProjCode())) {
				throw new IllegalArgumentException("该地市【" + sysProject.getProjName() + "】对应的sim地市不存在");
			}
		}

		// 这里用pid接收 经纬度
		String pid = queryParamData.getPid();
		if (StringUtils.isNoneBlank(pid)) {
			String[] pointArray = pid.trim().split("_");// pid接收
			if (pointArray != null && pointArray.length == 2) {
				GeoPoint ceterPoint = new GeoPoint(Double.valueOf(pointArray[1]), Double.valueOf(pointArray[0]));
				// 处理
				queryParamData.dealGeoDiatanceBuss(ceterPoint, 10000D, "location");

				// 根据经纬度 查询本log对应的pci
				QueryParamData qpdModel = new QueryParamData("logmain", "logmain");
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("latitude", Double.valueOf(pointArray[1]));
				resultMap.put("longitude", Double.valueOf(pointArray[0]));
				qpdModel.setTermMap(resultMap);
				List<Map<String, Object>> queryList = Esutil.queryList(qpdModel);
				if (queryList != null && queryList.size() > 0) {
					Map<String, Object> datasPci = queryList.get(0);

					SignalDataBean signalDataBean = JSONObject.parseObject(JSONObject.toJSONString(datasPci),
							SignalDataBean.class);
					if (signalDataBean != null) {
						OurDoorDataInfoBean doorDataInfoBeans = signalDataBean.getDoorDataInfoBeans();
						if (doorDataInfoBeans != null) {
							ArrayList<MLteNeighborhoodInfo> getmLteNeighborhoodInfos = doorDataInfoBeans
									.getmLteNeighborhoodInfos();
							List<Map<String, Object>> resultDataMap = new ArrayList<>();
							if (getmLteNeighborhoodInfos != null && getmLteNeighborhoodInfos.size() > 0) {
								for (MLteNeighborhoodInfo mLteNeighborhoodInfo : getmLteNeighborhoodInfos) {
									String mLteNeighborhoodPCI = mLteNeighborhoodInfo.getMLteNeighborhoodPCI();
									if (StringUtils.isNotBlank(mLteNeighborhoodPCI)) {
										// 处理该地市的查询条件
										Map<String, Object> termMap = queryParamData.getTermMap();
										if (termMap == null) {
											termMap = new HashMap<>();
										}
										termMap.put("lte_phycellid.keyword", mLteNeighborhoodPCI);
										termMap.put("lte_city_name.keyword", sysProject.getProjCode() + "");
										queryParamData.setTermMap(termMap);
										List<Map<String, Object>> queryList2 = Esutil.queryList(queryParamData);
										if (queryList2 != null && queryList2.size() > 0) {
											Map<String, Object> map = queryList2.get(0);
											Object lte_latitude2 = map.get("lte_latitude2");
											Object lte_longitude2 = map.get("lte_longitude2");
											Object lte_azimuth = map.get("lte_azimuth");
											if (lte_latitude2 != null && lte_longitude2 != null
													&& lte_azimuth != null) {

												try {
													Map<String, Object> latLon = new HashMap<>();

													String convertDistanceToLogLat = GeoLngLatUtil
															.ConvertDistanceToLogLat(
																	Double.valueOf(lte_longitude2.toString()),
																	Double.valueOf(lte_latitude2.toString()), MINT_UNIT,
																	Double.valueOf(lte_azimuth.toString()));
													if (convertDistanceToLogLat != null) {
														String[] split = convertDistanceToLogLat.split(",");
														if (split != null && split.length == 2) {
															latLon.put("lte_longitude2", split[0]);
															latLon.put("lte_latitude2", split[1]);
															latLon.put("lte_azimuth", lte_azimuth);
															resultDataMap.add(latLon);
														}
													}
												} catch (Exception e) {
													continue;
												}
											}
										}
									}
								}
							}
							return resultDataMap;
						} else {
							return new ArrayList<>();
						}
					}

					return new ArrayList<>();
				} else {
					return new ArrayList<>();
				}
			}
			return new ArrayList<>();
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * @Description: 更新sim工参信息
	 * @author weichengz
	 * @date 2019年12月17日 下午5:26:47
	 */
	@ResponseBody
	@RequestMapping(value = "/freshSimGc")
	public JsonMsgUtil freshSimGc() throws Exception {

		LoginUser loginUser = UserUtil.getLoginUser();
		if (loginUser == null) {
			throw new IllegalArgumentException("当前登陆用户失效");
		}

		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		String httpType = "http";
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于任何地市");
		} else {
			if (StringUtils.isBlank(sysProject.getProjCode())) {
				throw new IllegalArgumentException("该地市【" + sysProject.getProjName() + "】对应的sim地市不存在");
			}
			if (StringUtils.isBlank(sysProject.getProProvice())) {
				throw new IllegalArgumentException("该地市【" + sysProject.getProjName() + "】对应的地市省份不存在");
			}

			if (StringUtils.isBlank(sysProject.getProjSimName())) {
				throw new IllegalArgumentException("项目【" + sysProject.getProjName() + "】不存在SIM ProjectName名称");
			}
			if (StringUtils.isBlank(sysProject.getHostAp())) {
				throw new IllegalArgumentException("项目【" + sysProject.getProjName() + "】不存在sim工参地址");
			} else {
				String hostAp = sysProject.getHostAp().toLowerCase();
				if (hostAp.contains("https")) {
					httpType = "https";
				}
			}
			if (StringUtils.isBlank(sysProject.getProjOperator())) {
				throw new IllegalArgumentException("项目【" + sysProject.getProjName() + "】对应的运营商不存在");
			}
		}

		String projectLevelFlag = "3";// 默认是查询地市级别的工参
		boolean hasPermission = UserUtil.hasPermission(gcAllPermission);
		if (hasPermission) {
			projectLevelFlag = "2";
		}

		/** for循环请求数据开始 **/
		for (int i = 0; i < 100000000; i++) {
			System.out.println("----------------------第【" + i + "】批开始添加---------------------");

			Integer limit = 5000;// 每次拿3000条数据

			BulkRequest request = new BulkRequest();

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("projectName", sysProject.getProjSimName());
			paramMap.put("netId", "1");// 先写死1
			paramMap.put("projectLevel", projectLevelFlag);// 3是查询本市 2是查询全省的
			paramMap.put("provinceName", sysProject.getProProvice());
			paramMap.put("operator", sysProject.getProjOperator());
			paramMap.put("fields", // 这个顺序 要和 实体类的顺序一致
					"lte_city_name,lte_net,lte_enodebid,lte_sector_id,lte_cell,lte_ci,lte_ecgi,lte_phycellid,lte_longitude2,lte_latitude2,lte_longitude,lte_latitude,lte_site_tall,lte_azimuth,lte_mechanical_downdip,lte_electronic_downdip,lte_total_downdip,lte_tac,lte_sys,lte_site_type,lte_earfcn,lte_derrick_type,lte_address,lte_scene,lte_grid,lte_firm,lte_site_name");

			paramMap.put("limit", "" + i * limit + "," + limit); // 不限制就是全部的

			String token = AEStest.encrypt(JSONObject.toJSONString(paramMap), "zcto8k3i*a2c6");

			Map<String, Object> param = new HashMap<>();
			param.put("askJson", token);
			// https://218.65.240.119:8443/

			String url = sysProject.getHostAp() + "/SIM/ihandle!getParamSync.action";
			// String url = "https://218.65.240.119:8443/SIM/ihandle!getParamSync.action";
			String responseResult = HttpUtil.sendPostRequest(url, param, httpType);
			
			ResultModel resultModel = JSONObject.parseObject(responseResult, ResultModel.class);
			if (("0").equals(resultModel.getFlag())) {// 如果查询失败了 这里不再弹出 而是直接退出
				// throw new IllegalArgumentException("sim查询失败：" + resultModel.getMsg());
				break;
			}
			List<List<String>> datas = resultModel.getData();
			if (datas == null) {
				break;
			} else if (datas.size() == 0) {
				break;
			}
			for (List<String> data : datas) {
				try {
					CommonModel commonModel = CommonModel.changeStrToObj(data);
					
					//处理基站类型   转为indoor  outdoor
					commonModel.dealStationType();
					//处理基站类型   转为indoor  outdoor
					
					GcModel model = new GcModel(commonModel);
					// 将经纬度处理为WGS84
					model.dealLngLatBdToWgs84();
					model.setCityId(sysProject.getId() == null ? "" : sysProject.getId().toString());
					/*
					 * IndexRequest indexRequestOther = new IndexRequest("simgc", "simgc",
					 * sysProject.getId() + "_" + model.getLte_city_name() + "_" +
					 * model.getLte_ci());
					 */

					// 以地市和ci为主键
					IndexRequest indexRequestOther = new IndexRequest("simgc", "simgc",
							model.getLte_city_name() + "_" + model.getLte_ci());
					indexRequestOther.source(JSONObject.toJSONString(model), XContentType.JSON);
					request.add(indexRequestOther);
				} catch (Exception e) {
					continue;
				}
				
			}

			/** 分批的添加进去 */
			transportClient.bulk(request).get();

			try {// 休息一秒 防止立马查询不起作用
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		/** for循环请求数据结束 **/
		return new JsonMsgUtil(true, "SIM工参更新成功", "");
	}

	/**
	 * 根据[地市]条件批量删出数据
	 * 
	 * @Description: 仅仅支持 matchMap;termMap; phraseMap;
	 * @author weichengz
	 * @date 2019年12月19日 上午10:25:53
	 */
	@ResponseBody
	@PostMapping(value = "/deleteBatchByCity")
	public JsonMsgUtil deleteBatchByCity(@RequestBody QueryParamData queryParamData) {

		LoginUser loginUser = UserUtil.getLoginUser();
		if (loginUser == null) {
			throw new IllegalArgumentException("当前登陆用户失效");
		}

		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于任何地市");
		}

		if (queryParamData != null) {
			Map<String, Object> termMap = queryParamData.getTermMap();
			if (termMap == null) {
				termMap = new HashMap<>();
			}

			boolean hasPermission = UserUtil.hasPermission(gcAllPermission);
			if (hasPermission) {
				if (StringUtils.isBlank(sysProject.getId().toString())) {
					throw new IllegalArgumentException("该用户不属于任何地市");
				}
				termMap.put("cityId.keyword", sysProject.getId().toString());
			} else {
				termMap.put("lte_city_name.keyword", sysProject.getProjCode().toString());
			}
			queryParamData.setTermMap(termMap);
		}

		Long deleteBatchNum = Esutil.deleteBatchByCondition(queryParamData);
		try {// 休息一秒 防止立马查询不起作用
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new JsonMsgUtil(true, "删出成功,共删出【" + deleteBatchNum + "】条数据", "");
	}

	

	/**
	 * 
	 * @Description: 室外测试的文件导入
	 * @author weichengz
	 * @date 2019年11月5日 上午11:45:35
	 */
	@ResponseBody
	@RequestMapping(value = "/importOutHomeLog")
	public JsonMsgUtil importOutHomeLog(@RequestParam(value = "file", required = false) MultipartFile file)
			throws Exception {
		LoginUser loginUser = UserUtil.getLoginUser();
		if (loginUser == null) {
			throw new IllegalArgumentException("当前登陆用户失效");
		}

		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于任何地市");
		}

		if (file != null && StringUtils.isNoneBlank(file.getOriginalFilename()) && !file.isEmpty()) {

			String isMsgEvent = "0";// 是否具备信令 事件 0是不具备

			String outHomeTestId = MyUtil.getUuid();// 室外测试列表的id 后续所有的操作 都会以这个为索引主键
			BufferedReader bufferedReader = null;
			bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
			BulkRequest request = new BulkRequest();
			String str = null;// 文件里面一行记录
			String beginTime = null;// 日志测试的开始事件
			int num = 0;
			SignalDataBean signalDataBeanFinal = null;// 记录最后一跳记录 室外测试

			List<LogAnaLyze> logAnaLyzes = new ArrayList<>();// 存储Log分析信息

			while ((str = bufferedReader.readLine()) != null) {
				String id = MyUtil.getUuid();
				SignalDataBean signalDataBean = JSONObject.parseObject(str, SignalDataBean.class);

				// 先将百度经纬度转为wgs84
				signalDataBean.dealLngLatBdToWgs84();

				if (num == 0) {// 用于记录第一条记录
					beginTime = signalDataBean.getTestTime();
					num++;
				}
				IndexRequest indexRequestMain = new IndexRequest("logmain", "logmain", id);
				signalDataBean.setPid(outHomeTestId);// 室外测试的主键id
				signalDataBean.setId(id);/** 赋值 注意这个很重要 一定要先赋值 */

				// 添加日志分析信息
				addLogAnalyzeInfo(logAnaLyzes, signalDataBean);

				// 信令 大字段信息表得 存储
				String isMsgEventModel = handleMsgInfoField(signalDataBean, request, isMsgEvent);
				if (("1").equals(isMsgEventModel)) {
					isMsgEvent = isMsgEventModel;
				}
				// 主表信息转移
				MainLogModel mainLogModel = new MainLogModel(signalDataBean);

				indexRequestMain.source(JSONObject.toJSONString(mainLogModel), XContentType.JSON);
				request.add(indexRequestMain);
				signalDataBeanFinal = signalDataBean;// 记录最后一条记录
			}
			/** 分批的添加进去 */
			transportClient.bulk(request).get();

			// 最后处理统计分析的数据信息
			finalAnalyzeLogData(logAnaLyzes, signalDataBeanFinal);

			// 批量保存logAnaLyzes
			logAnaLyzeService.insertBatch(logAnaLyzes, 500);

			// 存储文件
			String fileStorePath = FileUtil.upFileNew(file, filesCommonPath, "outhomelog");
			// 处理最后一条记录的 就是 室外测试 列表
			handleOutHomeTest(signalDataBeanFinal, request, beginTime,
					FileUtil.getFilenameByOriginal(file.getOriginalFilename()), fileStorePath, sysProject.getId() + "",
					isMsgEvent);

			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
		return new JsonMsgUtil(true, "导入成功", "");
	}

	

	/**
	 * 添加要统计的信息
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年1月16日 下午5:28:07
	 */
	private void addLogAnalyzeInfo(List<LogAnaLyze> logAnaLyzes, SignalDataBean signalDataBean) {
		LogAnaLyze model = new LogAnaLyze();
		model.setPid(signalDataBean.getPid());
		model.setSinr(formatStringToDouble(signalDataBean.getSinr()));
		model.setRsrp(formatStringToDouble(signalDataBean.getRsrp()));

		model.setDownLoadSpeed(formatStringToDouble(signalDataBean.getDownLoadSpeed()));
		model.setUpLoadSpeed(formatStringToDouble(signalDataBean.getUpLoadSpeed()));

		model.setCreateTime(new Date());
		model.setPid(signalDataBean.getPid());

		logAnaLyzes.add(model);
	}

	private Double formatStringToDouble(String sinr) {
		if (StringUtils.isNotBlank(sinr)) {
			try {
				if (Double.valueOf(sinr) != null) {
					return Double.valueOf(sinr);
				}
			} catch (Exception e) {
				return 0D;
			}
		} else {
			return 0D;
		}
		return 0D;
	}

	/**
	 * 
	 * @Description: 处理最后的统计信息
	 * @author weichengz
	 * @date 2020年1月16日 下午5:27:36
	 */
	private void finalAnalyzeLogData(List<LogAnaLyze> logAnaLyzes, SignalDataBean signalDataBeanFinal) {
		if (logAnaLyzes != null && logAnaLyzes.size() > 0) {
			LogAnaLyze logAnaLyze = logAnaLyzes.get(0);
			if (signalDataBeanFinal != null) {

				logAnaLyze.setUniqueRecord(MyUtil.getUuid());// UUID
				logAnaLyze.setFinalLogData(JSONObject.toJSONString(signalDataBeanFinal));

				YyzbBean yyzbbean = signalDataBeanFinal.getYyzbbean();
				if (yyzbbean != null) {
					logAnaLyze.setZjcs(formatStringToDouble(yyzbbean.getZjcs()));
					logAnaLyze.setWjtcs(formatStringToDouble(yyzbbean.getWjtcs()));
					logAnaLyze.setDhcs(formatStringToDouble(yyzbbean.getDhcs()));
					logAnaLyze.setZhcs(logAnaLyze.getZjcs() - logAnaLyze.getWjtcs() - logAnaLyze.getDhcs());
				}

				PingzbBean pingzbBean = signalDataBeanFinal.getPingzbBean();
				if (pingzbBean != null) {
					logAnaLyze.setPingQqcs(formatStringToDouble(pingzbBean.getQqcs()));
					logAnaLyze.setPingCgch(formatStringToDouble(pingzbBean.getCgcs()));
					logAnaLyze.setPingSbch(logAnaLyze.getPingQqcs() - logAnaLyze.getPingCgch());
				}

				HttpzbBean httpzbBean = signalDataBeanFinal.getHttpzbBean();
				if (httpzbBean != null) {
					logAnaLyze.setHttpQqcs(formatStringToDouble(httpzbBean.getQqcs()));
					logAnaLyze.setHttpCgch(formatStringToDouble(httpzbBean.getCgcs()));
					logAnaLyze.setHttpSbch(logAnaLyze.getHttpQqcs() - logAnaLyze.getHttpCgch());
				}
			}
		}
	}

	/**
	 *
	 * @Description: 室外测试的列表添加记录 处理最后一条记录的
	 * @author weichengz
	 * @date 2019年11月11日 上午10:25:31
	 */
	private void handleOutHomeTest(SignalDataBean signalDataBeanFinal, BulkRequest request, String beginTime,
			String fileName, String filePath, String cityId, String isMsgEvent) {
		OutHomeLogModel outHomeLogModel = new OutHomeLogModel(signalDataBeanFinal, beginTime);
		outHomeLogModel.setCityId(cityId);
		String netWorkType = outHomeLogModel.getNetWorkType();
		if (StringUtils.isBlank(netWorkType)) {//默认是4G
			outHomeLogModel.setNetWorkType("LTE");
		}
		outHomeLogModel.setIsMsgEvent(isMsgEvent);
		outHomeLogModel.setFileName(fileName);
		outHomeLogModel.setFileUpTime(new Date().getTime());// 文件上传日期
		outHomeLogModel.setFilePath(filePath);
		outHomeLogModel.setLogType("0");//原来4G的log模型
		// 存储室外测试列表
		outHomeService.insert(outHomeLogModel);
	}

	/**
	 * 处理信令里面得大字段信息存储
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月7日 下午4:23:50
	 */
	private String handleMsgInfoField(SignalDataBean signalDataBean, BulkRequest request, String isMsgEvent) {
		ArrayList<MSignaBean> signaBeans = signalDataBean.getmSignaBean();
		if (signaBeans != null && signaBeans.size() > 0) {
			isMsgEvent = "1";// 设置具备信令事件日志

			for (MSignaBean mSignaBean : signaBeans) {
				String id = UUID.randomUUID().toString().replace("-", "");
				String msg = mSignaBean.getmMeaasge();
				IndexRequest indexRequestEvent = new IndexRequest("logmessage", "logmessage");
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("id", id); // 单条信令得id
				paramMap.put("ppid", signalDataBean.getPid()); // 室外测试列表的id
				paramMap.put("pid", signalDataBean.getId()); // 主log得id
				paramMap.put("message", msg);// 信令得信息 大字段信息
				paramMap.put("latitude", signalDataBean.getLatitude()); // 主 纬度
				paramMap.put("longitude", signalDataBean.getLongitude()); // 主经度
				mSignaBean.setmMeaasge(id);
				indexRequestEvent.source(JSONObject.toJSONString(paramMap), XContentType.JSON);
				request.add(indexRequestEvent);
			}
		}
		return isMsgEvent;
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

		try {// 休息一秒 防止立马查询不起作用
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new JsonMsgUtil(true, "删出成功", "");
	}
	
	

}
