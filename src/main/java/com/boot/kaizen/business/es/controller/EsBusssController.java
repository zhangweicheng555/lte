package com.boot.kaizen.business.es.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.business.es.model.OneButtonTest;
import com.boot.kaizen.business.es.model.OutHomeLogModel;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.business.es.model.sim.CommonModel;
import com.boot.kaizen.business.es.model.sim.GcModel;
import com.boot.kaizen.business.es.model.sim.ResultModel;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.business.es.service.IEsBussService;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.model.SysProject;
import com.boot.kaizen.model.SysProjectMapper;
import com.boot.kaizen.service.IProjectMapperService;
import com.boot.kaizen.service.SysProjectService;
import com.boot.kaizen.util.AEStest;
import com.boot.kaizen.util.HttpUtil;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.MyDateUtil;
import com.boot.kaizen.util.MyUtil;
import com.boot.kaizen.util.TableResultUtil;
import com.boot.kaizen.util.UserUtil;

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
	private IProjectMapperService projectMapperService;

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
		// clearMapEmptyVal.put("lte_city_name.keyword", sysProject.getProjCode());
		queryParamData.setTermMap(clearMapEmptyVal);// 精确查询
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

		try {// 休息一秒 防止立马查询不起作用
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
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

		List<String> datas = new ArrayList<>();
		if (StringUtils.isBlank(ids)) {// 查询全部
			QueryParamData queryParamData = new QueryParamData("onebuttontest", "onebuttontest", false, 1000);
			List<Map<String, Object>> dataMaps = Esutil.scrollQuery(queryParamData);
			if (dataMaps != null && dataMaps.size() > 0) {
				for (Map<String, Object> map : dataMaps) {
					datas.add(JSONObject.toJSONString(map));
				}
			}
		} else {
			datas = Esutil.queryBatchByIds("onebuttontest", "onebuttontest", ids.split(","));
		}

		Collection<OneButtonTest> collection = new ArrayList<OneButtonTest>();
		if (datas != null && datas.size() > 0) {
			for (String jsonStr : datas) {
				collection.add(JSONObject.parseObject(jsonStr, OneButtonTest.class));
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

		List<String> datas = new ArrayList<>();
		if (StringUtils.isBlank(ids)) {// 查询全部
			QueryParamData queryParamData = new QueryParamData("logouthome", "logouthome", false, 1000);
			List<Map<String, Object>> dataMaps = Esutil.scrollQuery(queryParamData);
			if (dataMaps != null && dataMaps.size() > 0) {
				for (Map<String, Object> map : dataMaps) {
					datas.add(JSONObject.toJSONString(map));
				}
			}
		} else {
			datas = Esutil.queryBatchByIds("logouthome", "logouthome", ids.split(","));
		}

		Collection<OutHomeLogModel> collection = new ArrayList<OutHomeLogModel>();
		if (datas != null && datas.size() > 0) {
			for (String jsonStr : datas) {
				collection.add(JSONObject.parseObject(jsonStr, OutHomeLogModel.class));
			}
		}

		// 存入 变
		String[] headers = { "文件名", "文件上传日期", "运营商", "网络类型", "城市", "地市", "测试人员", "手机型号", "IMSI", "测试时长", "开始时间", "结束时间",
				"总里程", "覆盖率", "平均RSRP", "平均SINR", "下载平均速率", "上传平均速率" };
		esBussService.exportLogoutHomeTest(headers, collection, "室外测试信息", response);

	}

	/**
	 * 主服务小区查询
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月20日 下午2:39:20
	 */
	@ResponseBody
	@PostMapping(value = "/queryMainService")
	public List<Map<String, Object>> queryMainService(@RequestParam(value = "longitude") String longitude,
			@RequestParam(value = "latitude") String latitude) {

		// 查询主log的 cELLID cI eNB
		Map<String, Object> termMainMap = new HashMap<>();
		termMainMap.put("longitude", longitude);
		termMainMap.put("latitude", latitude);

		QueryParamData queryMainParamData = new QueryParamData("logmain", "logmain", termMainMap,
				Arrays.asList("cI", "cELLID", "eNB"), 1);
		List<Map<String, Object>> queryList = Esutil.queryList(queryMainParamData);
		if (queryList != null && queryList.size() > 0) {
			Map<String, Object> map = queryList.get(0);
			String ci = map.get("cI").toString();
			String cellId = map.get("cELLID").toString();
			String enb = map.get("eNB").toString();

			Map<String, Object> termMap = new HashMap<String, Object>();
			termMap.put("lte_ecgi.keyword", cellId);
			QueryParamData queryParamData = new QueryParamData("simgc", "simgc", termMap,
					Arrays.asList("lte_longitude2", "lte_latitude2"), 1);
			List<Map<String, Object>> datas = Esutil.queryList(queryParamData);
			if (datas != null && datas.size() > 0) {
				return datas;
			} else {
				termMap.remove("lte_ecgi.keyword");
				termMap.put("lte_ci.keyword", enb + ci);
				QueryParamData queryParamDataOne = new QueryParamData("simgc", "simgc", termMap,
						Arrays.asList("lte_longitude2", "lte_latitude2"), 1);
				List<Map<String, Object>> dataSecond = Esutil.queryList(queryParamDataOne);
				return dataSecond;
			}
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * 对角坐标查询
	 */
	@ResponseBody
	@PostMapping(value = "/boundingBoxQuery")
	public List<Map<String, Object>> boundingBoxQuery(@RequestBody QueryParamData queryParamData) {
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
			return Esutil.scrollQuery(queryParamData);
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * @Description: 查询距离某个最近的点 这里默认是 公里为单位 传入的是经纬度
	 * @author weichengz
	 * @date 2019年11月22日 下午2:37:43
	 */
	@ResponseBody
	@PostMapping(value = "/queryNearPoint")
	public List<Map<String, Object>> queryNearPoint(@RequestBody QueryParamData queryParamData) {
		// 这里用pid接收 经纬度
		String pid = queryParamData.getPid();
		if (StringUtils.isNoneBlank(pid)) {
			String[] pointArray = pid.trim().split("_");// pid接收
			if (pointArray != null && pointArray.length == 2) {
				GeoPoint ceterPoint = new GeoPoint(Double.valueOf(pointArray[1]), Double.valueOf(pointArray[0]));
				// 处理
				queryParamData.dealGeoDiatanceBuss(ceterPoint, 10000D, "location");
				return Esutil.queryList(queryParamData);
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
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于任何地市");
		} else {
			if (StringUtils.isBlank(sysProject.getProjCode())) {
				throw new IllegalArgumentException("该地市【" + sysProject.getProjName() + "】对应的sim地市不存在");
			}
		}

		SysProjectMapper sysProjectMapper = projectMapperService.selectById(sysProject.getProjIntro());
		if (sysProjectMapper == null) {
			throw new IllegalArgumentException("该用户不属于任何项目");
		} else {
			if (StringUtils.isBlank(sysProjectMapper.getProjSimName())) {
				throw new IllegalArgumentException("项目【" + sysProjectMapper.getProjName() + "】不存在sim项目的名字");
			}
			if (StringUtils.isBlank(sysProjectMapper.getProjOperator())) {
				throw new IllegalArgumentException("项目【" + sysProjectMapper.getProjName() + "】对应的运营商不存在");
			}
		}

		BulkRequest request = new BulkRequest();

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projectName", sysProjectMapper.getProjSimName());
		paramMap.put("netId", "1");// 先写死1
		paramMap.put("projectLevel", "3");// 3是查询本市 2是查询全省的
		paramMap.put("provinceName", sysProject.getProjCode());
		paramMap.put("operator", sysProjectMapper.getProjOperator());
		paramMap.put("fields", // 这个顺序 要和 实体类的顺序一致
				"lte_city_name,lte_net,lte_enodebid,lte_sector_id,lte_cell,lte_ci,lte_ecgi,lte_phycellid,lte_longitude2,lte_latitude2,lte_longitude,lte_latitude,lte_site_tall,lte_azimuth,lte_mechanical_downdip,lte_electronic_downdip,lte_total_downdip,lte_tac,lte_sys,lte_site_type,lte_earfcn,lte_derrick_type,lte_address,lte_scene,lte_grid,lte_firm");
		// paramMap.put("limit", "1"); 不限制就是全部的

		String token = AEStest.encrypt(JSONObject.toJSONString(paramMap), "zcto8k3i*a2c6");

		Map<String, Object> param = new HashMap<>();
		param.put("askJson", token);

		String url = "http://61.132.73.61:8012/SIM/ihandle!getParamSync.action";
		String responseResult = HttpUtil.sendPostRequest(url, param);
		ResultModel resultModel = JSONObject.parseObject(responseResult, ResultModel.class);
		List<List<String>> datas = resultModel.getData();

		for (List<String> data : datas) {
			CommonModel commonModel = CommonModel.changeStrToObj(data);
			GcModel model = new GcModel(commonModel);
			model.setCityId(sysProject.getId() == null ? "" : sysProject.getId().toString());

			IndexRequest indexRequestOther = new IndexRequest("simgc", "simgc",
					model.getLte_city_name() + "_" + model.getLte_ci());
			indexRequestOther.source(JSONObject.toJSONString(model), XContentType.JSON);
			request.add(indexRequestOther);
		}

		/** 分批的添加进去 */
		BulkResponse bulkResponse = transportClient.bulk(request).get();
		if (bulkResponse.hasFailures()) {
			throw new IllegalArgumentException("出现异常：");
		}
		return new JsonMsgUtil(true, "sim工参更新成功成功", "");
	}
}
