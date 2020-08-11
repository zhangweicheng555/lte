package com.boot.kaizen.business.es.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.boot.kaizen.business.buss.model.fiveg.FiveLogMain;
import com.boot.kaizen.business.buss.model.fiveg.FootFtpzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootHttpzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootLtewxzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootNrwxzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootPingzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootYyzbBean;
import com.boot.kaizen.business.buss.model.fiveg.LogFoot;
import com.boot.kaizen.business.buss.model.fiveg.model.LteDataInfoBean;
import com.boot.kaizen.business.buss.model.fiveg.model.LteNeighborhoodInfo;
import com.boot.kaizen.business.buss.model.fiveg.model.NrDataInfoBean;
import com.boot.kaizen.business.buss.model.fiveg.model.NrLogBodyBean;
import com.boot.kaizen.business.buss.model.fiveg.model.NrLogHeadBean;
import com.boot.kaizen.business.buss.model.fiveg.model.NrNeighborhoodInfo;
import com.boot.kaizen.business.buss.model.fiveg.model.ProLteDataInfoBean;
import com.boot.kaizen.business.buss.model.fiveg.model.ProLteNeighborhoodInfo;
import com.boot.kaizen.business.buss.model.fiveg.model.ProNrDataInfoBean;
import com.boot.kaizen.business.buss.model.fiveg.model.ProNrNeighborhoodInfo;
import com.boot.kaizen.business.buss.model.fiveg.model.SignalBean;
import com.boot.kaizen.business.buss.model.fiveg.model.SignalEventBean;
import com.boot.kaizen.business.buss.service.ILogAnaLyzeService;
import com.boot.kaizen.business.buss.service.IOutHomeService;
import com.boot.kaizen.business.es.model.OutHomeLogModel;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.business.es.model.logModel.HttpzbBean;
import com.boot.kaizen.business.es.model.logModel.MLteNeighborhoodInfo;
import com.boot.kaizen.business.es.model.logModel.OurDoorDataInfoBean;
import com.boot.kaizen.business.es.model.logModel.PingzbBean;
import com.boot.kaizen.business.es.model.logModel.SignalDataBean;
import com.boot.kaizen.business.es.model.sim.CommonModel;
import com.boot.kaizen.business.es.model.sim.GcModel;
import com.boot.kaizen.business.es.model.sim.ResultModel;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.business.es.service.IEsBussService;
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
 * ES 业务控制层,5glog控制层
 * 
 * @author weichengz
 * @date 2019年11月5日 上午11:44:28
 */
@Controller
@RequestMapping("/es/buss/5g")
public class EsFiveBusssController {

	@Autowired
	private IEsBussService esBussService;
	@Autowired
	private ILogAnaLyzeService logAnaLyzeService;
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

	private static final double MINT_UNIT = 0.01;// 千米

	private static final double GEO_DISTANCE = 10;// 公里
	private static final double GEO_DISTANCE_5G = 5;// 公里
	private static final double DEFAAULT_NUM = -99999;// 统计分析的默认值

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

			String outHomeTestId = MyUtil.getUuid();// 室外测试列表的id 后续所有的操作 都会以这个为索引主键
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
			BulkRequest request = new BulkRequest();
			String str = null;// 文件里面一行记录

			int num = 0;// 读取文件的时候 记录游标使用
			NrLogHeadBean nrLogHeadBean = null;
			NrLogBodyBean nrLogBodyBean = null;
			String beginTime = null;
			String endTime = null;
			LogFoot logFoot = null;
			List<LogAnaLyze> logAnaLyzes = new ArrayList<>();// 存储Log分析信息

			String logType = "0";// log的格式 0代表4g的 1代表5g的 20200720

			SignalDataBean signalDataBean = null;// 为了处理最后的几位部分准备的

			while ((str = bufferedReader.readLine()) != null) {

				if (num == 0) {// 20200720
					if (str.contains("mSignaBean") || str.contains("mSignaEventBean") || str.contains("longitude")
							|| str.contains("latitude") || str.contains("doorDataInfoBeans")
							|| str.contains("doorDataInfoViceBeans")) {
						logType = "0";
					} else {
						logType = "1";// 5g
					}
				}

				if (("1").equals(logType)) {// 5g的处理方式

					String id = MyUtil.getUuid();// 每个mainLog的主键
					if (num == 0) {// 第一行的处理方式
						nrLogHeadBean = JSONObject.parseObject(str, NrLogHeadBean.class);
						// 校验头部的信息 不正确直接抛异常
						checkHeaderInfo(nrLogHeadBean);
						num++;
					} else {
						// 如果最后一行
						if (str.contains("httpzbBean") || str.contains("pingzbBean") || str.contains("ftpzbBean")
								|| str.contains("nrwxzbBean") || str.contains("ltewxzbBean")
								|| str.contains("yyzbBean")) {
							// 存储文件
							String fileStorePath = FileUtil.upFileNew(file, filesCommonPath, "outhomelog");
							logFoot = JSONObject.parseObject(str, LogFoot.class);
							// 处理最后一条记录的 就是 室外测试 列表
							handleOutHomeTest(logFoot, request, beginTime,
									FileUtil.getFilenameByOriginal(file.getOriginalFilename()), fileStorePath,
									sysProject.getId() + "", nrLogHeadBean, endTime, outHomeTestId);
							// 处理统计分析记录
							finalAnalyzeLogData(logAnaLyzes, logFoot, outHomeTestId);
						} else {// 其他行
							nrLogBodyBean = JSONObject.parseObject(str, NrLogBodyBean.class);
							if (nrLogBodyBean != null) {
								// 记录时间
								if (num == 1) {
									beginTime = nrLogBodyBean.dealLongTimeToStr(nrLogBodyBean.getTestTime());
									num++;
								}
								endTime = nrLogBodyBean.dealLongTimeToStr(nrLogBodyBean.getTestTime());
								nrLogBodyBean.setTestTime(endTime);// 重新设置时间为 yyyy-MM-dd HH:mm:ss的格式

								// 先将百度经纬度转为wgs84
								// nrLogBodyBean.dealLngLatBdToWgs84();
								nrLogBodyBean.setPid(outHomeTestId);// 室外测试的主键id
								nrLogBodyBean.setId(id);/** 赋值 注意这个很重要 一定要先赋值 */
								// 信令 大字段信息表得 存储
								handleMsgInfoField(nrLogBodyBean, request);
								// 主表信息转移
								IndexRequest indexRequestMain = new IndexRequest("logmain5g", "logmain5g", id);
								FiveLogMain mainLogModel = new FiveLogMain(nrLogBodyBean, nrLogHeadBean);
								indexRequestMain.source(JSONObject.toJSONString(mainLogModel), XContentType.JSON);
								request.add(indexRequestMain);
								// 添加日志分析信息
								addLogAnalyzeInfo(logAnaLyzes, nrLogBodyBean, nrLogHeadBean, mainLogModel);
							}
						}
					}
				} else {// 4g的处理方式 20200720

					String id = MyUtil.getUuid();// 每个mainLog的主键
					signalDataBean = JSONObject.parseObject(str, SignalDataBean.class);
					if (num == 0) {// 处理头部的信息
						nrLogHeadBean = signalDataBean.toNrLogHeadBean();
					}
					num++;
					nrLogBodyBean = signalDataBean.toNrLogBodyBean();
					if (nrLogBodyBean != null) {
						// 记录时间
						if (num == 1) {
							beginTime = nrLogBodyBean.dealLongTimeToStr(nrLogBodyBean.getTestTime());
							num++;
						}
						endTime = nrLogBodyBean.dealLongTimeToStr(nrLogBodyBean.getTestTime());
						nrLogBodyBean.setTestTime(endTime);// 调整一下他的时间格式为 yyyy-MM-dd HH:mm:ss
						// 先将百度经纬度转为wgs84
						// nrLogBodyBean.dealLngLatBdToWgs84();
						nrLogBodyBean.setPid(outHomeTestId);// 室外测试的主键id
						nrLogBodyBean.setId(id);/** 赋值 注意这个很重要 一定要先赋值 */
						// 信令 大字段信息表得 存储
						handleMsgInfoField(nrLogBodyBean, request);
						// 主表信息转移
						IndexRequest indexRequestMain = new IndexRequest("logmain5g", "logmain5g", id);
						FiveLogMain mainLogModel = new FiveLogMain(nrLogBodyBean, nrLogHeadBean);
						indexRequestMain.source(JSONObject.toJSONString(mainLogModel), XContentType.JSON);
						request.add(indexRequestMain);
						// 添加日志分析信息
						addLogAnalyzeInfo(logAnaLyzes, nrLogBodyBean, nrLogHeadBean, mainLogModel);
					}
				}
			}

			if (("0").equals(logType)) {// 4g的时候 处理统计分析使用
				// 存储文件
				String fileStorePath = FileUtil.upFileNew(file, filesCommonPath, "outhomelog");
				if (signalDataBean != null) {
					logFoot = signalDataBean.ToLogFoot();
					// 处理最后一条记录的 就是 室外测试 列表
					handleOutHomeTest(logFoot, request, beginTime,
							FileUtil.getFilenameByOriginal(file.getOriginalFilename()), fileStorePath,
							sysProject.getId() + "", nrLogHeadBean, endTime, outHomeTestId);
					// 处理统计分析记录
					finalAnalyzeLogData(logAnaLyzes, logFoot, outHomeTestId);
				}
			}

			/** 分批的添加进去 */
			transportClient.bulk(request).get();
			// 批量保存logAnaLyzes
			logAnaLyzeService.insertBatch(logAnaLyzes, 500);

			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
		return new JsonMsgUtil(true, "导入成功", "");
	}

	/**
	 * 
	 * @Description: 处理统计分析最后模块
	 * @author weichengz
	 * @date 2020年5月22日 下午2:27:31
	 */
	private void finalAnalyzeLogData(List<LogAnaLyze> logAnaLyzes, LogFoot logFoot, String outHomeTestId) {
		if (logAnaLyzes != null && logAnaLyzes.size() > 0) {
			LogAnaLyze logAnaLyze = logAnaLyzes.get(0);
			if (logFoot != null) {

				logAnaLyze.setUniqueRecord(MyUtil.getUuid());// UUID
				logAnaLyze.setFinalLogData(JSONObject.toJSONString(logFoot));

				FootYyzbBean yyzbBean = logFoot.getYyzbBean();
				if (yyzbBean != null) {
					logAnaLyze.setZjcs(formatStringToDouble(yyzbBean.getZjcs()));
					logAnaLyze.setDhcs(formatStringToDouble(yyzbBean.getDhcs()));
					logAnaLyze.setWjtcs(
							logAnaLyze.getZjcs() - logAnaLyze.getDhcs() - formatStringToDouble(yyzbBean.getJtcs()));
					logAnaLyze.setZhcs(logAnaLyze.getZjcs() - logAnaLyze.getWjtcs() - logAnaLyze.getDhcs());
				}

				FootPingzbBean pingzbBean = logFoot.getPingzbBean();
				if (pingzbBean != null) {
					logAnaLyze.setPingQqcs(formatStringToDouble(pingzbBean.getQqcs()));
					logAnaLyze.setPingCgch(formatStringToDouble(pingzbBean.getCgcs()));
					logAnaLyze.setPingSbch(logAnaLyze.getPingQqcs() - logAnaLyze.getPingCgch());
				}

				FootHttpzbBean httpzbBean = logFoot.getHttpzbBean();
				if (httpzbBean != null) {
					logAnaLyze.setHttpQqcs(formatStringToDouble(httpzbBean.getQqcs()));
					logAnaLyze.setHttpCgch(formatStringToDouble(httpzbBean.getCgcs()));
					logAnaLyze.setHttpSbch(logAnaLyze.getHttpQqcs() - logAnaLyze.getHttpCgch());
				}
			}
		}
	}

	/**
	 * 室外测试统计分析部分的统计
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年5月21日 下午4:10:55
	 */
	private void addLogAnalyzeInfo(List<LogAnaLyze> logAnaLyzes, NrLogBodyBean nrLogBodyBean,
			NrLogHeadBean nrLogHeadBean, FiveLogMain mainLogModel) {
		LogAnaLyze model = new LogAnaLyze();
		model.setDownLoadSpeed(formatStringToDouble(nrLogBodyBean.getDownLoadSpeed()));
		model.setUpLoadSpeed(formatStringToDouble(nrLogBodyBean.getUpLoadSpeed()));
		model.setPid(nrLogBodyBean.getPid());
		int rootSupport = nrLogHeadBean.getRootSupport();
		model.setCreateTime(new Date());
		model.setSinr(DEFAAULT_NUM);// 先设置为默认的值
		model.setRsrp(DEFAAULT_NUM);// 先设置为默认的值

		model.setSsrsrp(formatStringToDouble(mainLogModel.getSsrsrp()));// 设置ssrsrp sssinr的值
		model.setSssinr(formatStringToDouble(mainLogModel.getSssinr()));

		if (0 == rootSupport) {// 非root
			LteDataInfoBean lteDataInfoBean = nrLogBodyBean.getLteDataInfoBean();
			if (lteDataInfoBean != null) {
				model.setSinr(formatStringToDouble(lteDataInfoBean.getLteSINR()));
				model.setRsrp(formatStringToDouble(lteDataInfoBean.getLteRSRP()));
			}
		} else {// root的
			ProLteDataInfoBean proLteDataInfoBeans = nrLogBodyBean.getProLteDataInfoBean();
			if (proLteDataInfoBeans != null) {
				model.setSinr(formatStringToDouble(proLteDataInfoBeans.getServingCellPccSinr()));
				model.setRsrp(formatStringToDouble(proLteDataInfoBeans.getServingCellPccRsrp()));
			}
		}
		logAnaLyzes.add(model);

	}

	/**
	 * 不符合条件的都设置为默认的值
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年6月24日 上午11:08:46
	 */
	private Double formatStringToDouble(String sinr) {
		if (StringUtils.isNotBlank(sinr)) {
			try {
				if (Double.valueOf(sinr) != null) {
					return Double.valueOf(sinr);
				}
			} catch (Exception e) {
				return DEFAAULT_NUM;
			}
		} else {
			return DEFAAULT_NUM;
		}
		return DEFAAULT_NUM;
	}

	/**
	 * 
	 * @Description: 头部信息的校验
	 * @author weichengz
	 * @date 2020年5月6日 下午3:55:18
	 */
	private void checkHeaderInfo(NrLogHeadBean nrLogHeadBean) {
		int logversion = nrLogHeadBean.getLogversion();
		if (0 != logversion && 1 != logversion) {
			throw new IllegalArgumentException("Logversion值不符合要求：" + logversion);
		}
		int rootSupport = nrLogHeadBean.getRootSupport();
		if (0 != rootSupport && 1 != rootSupport) {
			throw new IllegalArgumentException("rootSupport值不符合要求：" + rootSupport);
		}
	}

	/**
	 *
	 * @Description: 室外测试的列表添加记录 处理最后一条记录的
	 * @author weichengz
	 * @date 2019年11月11日 上午10:25:31
	 */
	private void handleOutHomeTest(LogFoot logFoot, BulkRequest request, String beginTime, String fileName,
			String filePath, String cityId, NrLogHeadBean nrLogHeadBean, String endTime, String outHomeTestId) {
		OutHomeLogModel outHomeLogModel = new OutHomeLogModel(logFoot, beginTime, endTime);
		outHomeLogModel.setCityId(cityId);
		outHomeLogModel.setId(outHomeTestId);

		outHomeLogModel.setCity(nrLogHeadBean.getCity());
		outHomeLogModel.setTestPerson(nrLogHeadBean.getUser());
		outHomeLogModel.setPhoneType(nrLogHeadBean.getPhone());
		
		int logversion = nrLogHeadBean.getLogversion();
		if (1 == logversion) {// NR5G NSA
			outHomeLogModel.setNetWorkType("NR5G NSA");
			if (logFoot != null) {
				FootNrwxzbBean nrwxzbBean = logFoot.getNrwxzbBean();
				if (nrwxzbBean != null) {
					outHomeLogModel.setAvgRsrp(nrwxzbBean.getSsrsrp());
					outHomeLogModel.setAvgSinr(nrwxzbBean.getSssinr());
				}

				FootFtpzbBean ftpzbBean = logFoot.getFtpzbBean();
				if (ftpzbBean != null) {
					outHomeLogModel.setAvgDownRate(ftpzbBean.getDlAvg());
					outHomeLogModel.setAvgUpRate(ftpzbBean.getUlAvg());
				}
			}

		} else {// 0是 LTE
			outHomeLogModel.setNetWorkType("LTE");
			if (logFoot != null) {
				FootLtewxzbBean ltewxzbBean = logFoot.getLtewxzbBean();
				if (ltewxzbBean != null) {
					outHomeLogModel.setAvgRsrp(ltewxzbBean.getRsrpp());
					outHomeLogModel.setAvgSinr(ltewxzbBean.getSinrp());
				}

				FootFtpzbBean ftpzbBean = logFoot.getFtpzbBean();
				if (ftpzbBean != null) {
					outHomeLogModel.setAvgDownRate(ftpzbBean.getDlAvg());
					outHomeLogModel.setAvgUpRate(ftpzbBean.getUlAvg());
				}

			}
		}

		outHomeLogModel.setFileName(fileName);
		outHomeLogModel.setFileUpTime(new Date().getTime());// 文件上传日期
		outHomeLogModel.setFilePath(filePath);
		outHomeLogModel.setOperatorService(nrLogHeadBean.getOperator());
		outHomeLogModel.setLogType("1");
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
	private void handleMsgInfoField(NrLogBodyBean signalDataBean, BulkRequest request) {
		List<SignalBean> signalBeans = signalDataBean.getSignalBeans();
		if (signalBeans != null && signalBeans.size() > 0) {
			for (SignalBean mSignaBean : signalBeans) {
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
	}

	/**
	 * 5G室外测试log导出csv
	 * 
	 * @Description: id 室外测试的id
	 * @author weichengz
	 * @date 2020年5月20日 下午1:40:23
	 */
	@ResponseBody
	@RequestMapping(value = "/exportOutHomeCsv")
	public void exportOutHomeCsv(HttpServletResponse response, @RequestParam(value = "pid") String id)
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
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-disposition",
					"attachment;filename=" + URLUtil.encode(fileName, StringUtil.UTF8) + ".csv");

			String[] head = new String[] {};

			String[] headRoot = new String[] { // 176
					"mTimestamp1", "mSingaType", "mMessageType", "mChannelType", "mMeaasge", "mTimestamp2",
					"mEventType", //
					"mEvent", "Latitude", "Longitude", "Speed", "Height", "TestTime", "DownLoadSpeed", "UpLoadSpeed",
					"NormalEventType", //
					"AbNormalEventType", "Band", "BandWidth", "FrequencyPointA", "FrequencyDL", "GSCN",
					"SubCarrierSpace", "PCI", "SSBIndex", //
					"SSARFCN", "SSRSRP", "SSSINR", "SSRSRQ", "PUSCHTxPower", "PUCCHTxPower", "SRSTxPower", "CQI",
					"MCSUL", "MCSDL", "ModUL", "ModDL", //
					"PUSCHBLER", "PDSCHBLER", "GrantULNum", "GrantDLNum", "RINumDL", "PDCPULThr", "PDCPDLThr",
					"RLCULThr", "RLCDLThr", "MACULThr", //
					"MACDLThr", "PUSCHRB", "PDSCHRB", "SlotConfig(DL/UL)", "16qamnum", "64qamnum", "256qamnum",
					"16qamUlnum", "16qamDlnum", //
					"64qamUlnum", "64qamDlnum", "256qamUlnum", "256qamDlnum", "QPSKUlnum", "QPSKDlnum",
					"pNrNeighborhoodNRARFCN1", "pNrNeighborhoodPCI1", //
					"pNrNeighborhoodReam1", "pNrNeighborhoodSSRSRP1", "pNrNeighborhoodSSSINR1",
					"pNrNeighborhoodSSRSRQ1", "pNrNeighborhoodNRARFCN2", //
					"pNrNeighborhoodPCI2", "pNrNeighborhoodReam2", "pNrNeighborhoodSSRSRP2", "pNrNeighborhoodSSSINR2",
					"pNrNeighborhoodSSRSRQ2", //
					"pNrNeighborhoodNRARFCN3", "pNrNeighborhoodPCI3", "pNrNeighborhoodReam3", "pNrNeighborhoodSSRSRP3",
					"pNrNeighborhoodSSSINR3", //
					"pNrNeighborhoodSSRSRQ3", "pNrNeighborhoodNRARFCN4", "pNrNeighborhoodPCI4", "pNrNeighborhoodReam4",
					"pNrNeighborhoodSSRSRP4", //
					"pNrNeighborhoodSSSINR4", "pNrNeighborhoodSSRSRQ4", "pNrNeighborhoodNRARFCN5",
					"pNrNeighborhoodPCI5", "pNrNeighborhoodReam5", //
					"pNrNeighborhoodSSRSRP5", "pNrNeighborhoodSSSINR5", "pNrNeighborhoodSSRSRQ5",
					"pNrNeighborhoodNRARFCN6", "pNrNeighborhoodPCI6", //
					"pNrNeighborhoodReam6", "pNrNeighborhoodSSRSRP6", "pNrNeighborhoodSSSINR6",
					"pNrNeighborhoodSSRSRQ6", "servingCellPccMcc", //
					"servingCellPccMnc", "servingCellPccSiteEci", "servingCellPccBandIndex", "servingCellPccBwDl",
					"servingCellPccFreqDl", //
					"servingCellPccTac", "servingCellPccSsp", "servingCellPccSa", "servingCellPccCellId",
					"servingCellPccEarfcnDl", "servingCellPccPci", //
					"servingCellPccRsrp", "servingCellPccSinr", "servingCellPccRsrq", "servingCellPccRssi",
					"servingCellPccPuschTxpower", //
					"servingCellPccPucchTxpower", "servingCellPccEnodebId", "servingCellPccWidebandCqi",
					"servingCellPccMcsul", "servingCellPccMcsdl", //
					"servingCellPccModul", "servingCellPccModdl", "servingCellPccULBLER", "servingCellPccDLBLER",
					"servingCellPccGrantulnum", //
					"servingCellPccGrantdlnum", "servingCellPccRankIndex", "throughputPccPdcpUl", "throughputPccPdcpDl",
					"throughputPccRlcUl", //
					"throughputPccRlcDl", "throughputPccMacUl", "throughputPccMacDl", "servingCellPccPuschRbs",
					"servingCellPccPdschRbs", //
					"modulationPcc16qam", "modulationPcc64qam", "modulationPcc256qam", "modulationPcc16qamUl",
					"modulationPcc16qamDl", //
					"modulationPcc64qamUl", "modulationPcc64qamDl", "modulationPcc256qamUl", "modulationPcc256qamDl",
					"modulationPccQPSKUl", //
					"modulationPccQPSKDl", "pLteNeighbirhoodEARFCN1", "pLteNeighbirhoodPCI1", "pLteNeighbirhoodRSRP1",
					"pLteNeighbirhoodRSRQ1", //
					"pLteNeighbirhoodRSSI1", "pLteNeighbirhoodEARFCN2", "pLteNeighbirhoodPCI2", "pLteNeighbirhoodRSRP2",
					"pLteNeighbirhoodRSRQ2", //
					"pLteNeighbirhoodRSSI2", "pLteNeighbirhoodEARFCN3", "pLteNeighbirhoodPCI3", "pLteNeighbirhoodRSRP3",
					"pLteNeighbirhoodRSRQ3", //
					"pLteNeighbirhoodRSSI3", "pLteNeighbirhoodEARFCN4", "pLteNeighbirhoodPCI4", "pLteNeighbirhoodRSRP4",
					"pLteNeighbirhoodRSRQ4", //
					"pLteNeighbirhoodRSSI4", "pLteNeighbirhoodEARFCN5", "pLteNeighbirhoodPCI5", "pLteNeighbirhoodRSRP5",
					"pLteNeighbirhoodRSRQ5", //
					"pLteNeighbirhoodRSSI5", "pLteNeighbirhoodEARFCN6", "pLteNeighbirhoodPCI6", "pLteNeighbirhoodRSRP6",
					"pLteNeighbirhoodRSRQ6", //
					"pLteNeighbirhoodRSSI6"//
			};
			String[] headNoRoot = new String[] { // 85
					"Latitude", "Longitude", "Speed", "Height", "TestTime", "DownLoadSpeed", "UpLoadSpeed"//
					, "NormalEventType", "AbNormalEventType", "NrARFCN", "NrPCI", "NrCellName", "SSRSRP"//
					, "SSSINR", "SSRSRQ", "NrNeighborhoodNRARFCN1", "NrNeighborhoodPCI1", "NrNeighborhoodSSRSRP1"//
					, "NrNeighborhoodSSRSRQ1", "NrNeighborhoodSSSINR1", "NrNeighborhoodNRARFCN2", "NrNeighborhoodPCI2"//
					, "NrNeighborhoodSSRSRP2", "NrNeighborhoodSSRSRQ2", "NrNeighborhoodSSSINR2",
					"NrNeighborhoodNRARFCN3"//
					, "NrNeighborhoodPCI3", "NrNeighborhoodSSRSRP3", "NrNeighborhoodSSRSRQ3", "NrNeighborhoodSSSINR3"//
					, "NrNeighborhoodNRARFCN4", "NrNeighborhoodPCI4", "NrNeighborhoodSSRSRP4", "NrNeighborhoodSSRSRQ4"//
					, "NrNeighborhoodSSSINR4", "NrNeighborhoodNRARFCN5", "NrNeighborhoodPCI5", "NrNeighborhoodSSRSRP5"//
					, "NrNeighborhoodSSRSRQ5", "NrNeighborhoodSSSINR5", "NrNeighborhoodNRARFCN6", "NrNeighborhoodPCI6"//
					, "NrNeighborhoodSSRSRP6", "NrNeighborhoodSSRSRQ6", "NrNeighborhoodSSSINR6", "LteTAC", "LteEARFCN"//
					, "LtePCI", "LteENB", "LteCellID", "LteCellName", "LteRSRP", "LteRSRQ", "LteSINR", "LteRSSI",
					"LteNeighbirhoodEARFCN1"//
					, "LteNeighbirhoodPCI1", "LteNeighbirhoodRSRP1", "LteNeighbirhoodRSRQ1", "LteNeighbirhoodRSSI1",
					"LteNeighbirhoodEARFCN2"//
					, "LteNeighbirhoodPCI2", "LteNeighbirhoodRSRP2", "LteNeighbirhoodRSRQ2", "LteNeighbirhoodRSSI2",
					"LteNeighbirhoodEARFCN3"//
					, "LteNeighbirhoodPCI3", "LteNeighbirhoodRSRP3", "LteNeighbirhoodRSRQ3", "LteNeighbirhoodRSSI3",
					"LteNeighbirhoodEARFCN4", //
					"LteNeighbirhoodPCI4", "LteNeighbirhoodRSRP4", "LteNeighbirhoodRSRQ4", "LteNeighbirhoodRSSI4",
					"LteNeighbirhoodEARFCN5"//
					, "LteNeighbirhoodPCI5", "LteNeighbirhoodRSRP5", "LteNeighbirhoodRSRQ5", "LteNeighbirhoodRSSI5",
					"LteNeighbirhoodEARFCN6"//
					, "LteNeighbirhoodPCI6", "LteNeighbirhoodRSRP6", "LteNeighbirhoodRSRQ6", "LteNeighbirhoodRSSI6"//
			};

			// 4g的head
			String[] head4g = new String[] { "时间", "手机型号", "运营商", "MCC", "MNC", "网络类型", "经度", "纬度", "速度", "高度", "应用层事件",
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
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String str = null;// 文件里面一行记录
			Integer numFlag = 0;
			Integer rootSupport = 0;// 0 非root 1 root
			String logType = "1";// 默认是5g的log
			while ((str = bufferedReader.readLine()) != null) {

				if (numFlag == 0) {// 20200720
					if (str.contains("mSignaBean") || str.contains("mSignaEventBean") || str.contains("longitude")
							|| str.contains("latitude") || str.contains("doorDataInfoBeans")
							|| str.contains("doorDataInfoViceBeans")) {
						logType = "0";
						head=head4g;//设置4g的标题
					} else {
						logType = "1";// 5g
					}
				}
				
				if (("1").equals(logType)) {

					if (numFlag == 0) {
						NrLogHeadBean nrLogHeadBean = JSONObject.parseObject(str, NrLogHeadBean.class);
						rootSupport = nrLogHeadBean.getRootSupport();
						numFlag = numFlag + 1;
					} else {
						// 如果最后一行
						if (str.contains("httpzbBean") || str.contains("pingzbBean") || str.contains("ftpzbBean")
								|| str.contains("nrwxzbBean") || str.contains("ltewxzbBean")
								|| str.contains("yyzbBean")) {

						} else {// 转为csv
							NrLogBodyBean signalDataBean = JSONObject.parseObject(str, NrLogBodyBean.class);
							String testTime2 = signalDataBean.getTestTime();
							signalDataBean.setTestTime(MyDateUtil.formatDate(new Date(Long.valueOf(testTime2)), "yyyy-MM-dd HH:mm:ss"));
							
							if (signalDataBean != null) {
								if (rootSupport == 0) {// 非root
									head = headNoRoot;
									Object[] objects = new Object[85];
									objects[0] = MyUtil.nullToEmpty(signalDataBean.getLatitude());
									objects[1] = MyUtil.nullToEmpty(signalDataBean.getLongitude());
									objects[2] = MyUtil.nullToEmpty(signalDataBean.getSpeed());
									objects[3] = MyUtil.nullToEmpty(signalDataBean.getHeight());
									objects[4] = MyUtil.nullToEmpty(signalDataBean.getTestTime());
									objects[5] = MyUtil.nullToEmpty(signalDataBean.getDownLoadSpeed());
									objects[6] = MyUtil.nullToEmpty(signalDataBean.getUpLoadSpeed());
									objects[7] = MyUtil.nullToEmpty(signalDataBean.getNormalEventType());
									objects[8] = MyUtil.nullToEmpty(signalDataBean.getNormalEventType());

									for (int i = 9; i < 45; i++) {
										objects[i] = "";
									}

									NrDataInfoBean nrDataInfoBean = signalDataBean.getNrDataInfoBean();
									if (nrDataInfoBean != null) {
										objects[9] = MyUtil.nullToEmpty(nrDataInfoBean.getNrARFCN());
										objects[10] = MyUtil.nullToEmpty(nrDataInfoBean.getNrPCI());
										objects[11] = MyUtil.nullToEmpty(nrDataInfoBean.getNrCellName());
										objects[12] = MyUtil.nullToEmpty(nrDataInfoBean.getSsRSRP());
										objects[13] = MyUtil.nullToEmpty(nrDataInfoBean.getSsSINR());
										objects[14] = MyUtil.nullToEmpty(nrDataInfoBean.getSsRSRQ());
										List<NrNeighborhoodInfo> nrNeighborhoodInfos = nrDataInfoBean
												.getNrNeighborhoodInfos();
										if (nrNeighborhoodInfos != null && nrNeighborhoodInfos.size() > 0) {
											Integer cycleNum = 0;
											Integer beginNum = 15;
											for (NrNeighborhoodInfo nrNeighborhoodInfo : nrNeighborhoodInfos) {
												if (cycleNum > 5) {
													break;
												}

												objects[beginNum++] = MyUtil
														.nullToEmpty(nrNeighborhoodInfo.getNrNeighborhoodNRARFCN());
												objects[beginNum++] = MyUtil
														.nullToEmpty(nrNeighborhoodInfo.getNrNeighborhoodPCI());
												objects[beginNum++] = MyUtil
														.nullToEmpty(nrNeighborhoodInfo.getNrNeighborhoodSSRSRP());
												objects[beginNum++] = MyUtil
														.nullToEmpty(nrNeighborhoodInfo.getNrNeighborhoodSSRSRQ());
												objects[beginNum++] = MyUtil
														.nullToEmpty(nrNeighborhoodInfo.getNrNeighborhoodSSSINR());

												cycleNum++;
											}
										}
									}

									// 45-85
									for (int i = 45; i < 85; i++) {
										objects[i] = "";
									}

									LteDataInfoBean lteDataInfoBean = signalDataBean.getLteDataInfoBean();
									if (lteDataInfoBean != null) {
										objects[45] = MyUtil.nullToEmpty(lteDataInfoBean.getLteTAC());
										objects[46] = MyUtil.nullToEmpty(lteDataInfoBean.getLteEARFCN());
										objects[47] = MyUtil.nullToEmpty(lteDataInfoBean.getLtePCI());
										objects[48] = MyUtil.nullToEmpty(lteDataInfoBean.getLteENB());
										objects[49] = MyUtil.nullToEmpty(lteDataInfoBean.getLteCellID());
										objects[50] = MyUtil.nullToEmpty(lteDataInfoBean.getLteCellName());
										objects[51] = MyUtil.nullToEmpty(lteDataInfoBean.getLteRSRP());
										objects[52] = MyUtil.nullToEmpty(lteDataInfoBean.getLteRSRQ());
										objects[53] = MyUtil.nullToEmpty(lteDataInfoBean.getLteSINR());
										objects[54] = MyUtil.nullToEmpty(lteDataInfoBean.getLteRSSI());

										List<LteNeighborhoodInfo> lteNeighborhoodInfos = lteDataInfoBean
												.getLteNeighborhoodInfos();
										if (lteNeighborhoodInfos != null && lteNeighborhoodInfos.size() > 0) {
											Integer beginNum = 55;
											Integer cycleNum = 0;
											for (LteNeighborhoodInfo lteNeighborhoodInfo : lteNeighborhoodInfos) {
												if (cycleNum > 5) {
													break;
												}

												objects[beginNum++] = MyUtil
														.nullToEmpty(lteNeighborhoodInfo.getLteNeighbirhoodEARFCN());
												objects[beginNum++] = MyUtil
														.nullToEmpty(lteNeighborhoodInfo.getLteNeighbirhoodPCI());
												objects[beginNum++] = MyUtil
														.nullToEmpty(lteNeighborhoodInfo.getLteNeighbirhoodRSRP());
												objects[beginNum++] = MyUtil
														.nullToEmpty(lteNeighborhoodInfo.getLteNeighbirhoodRSRQ());
												objects[beginNum++] = MyUtil
														.nullToEmpty(lteNeighborhoodInfo.getLteNeighbirhoodRSSI());
												cycleNum++;
											}
										}
									}
									result.add(objects);
								} else {// root
									head = headRoot;
									List<String> timesList = new ArrayList<>();// 时间集合
									String testTime = signalDataBean.getTestTime();// 主的时间

									// 事件
									List<SignalEventBean> signalEventBeans = signalDataBean.getSignalEventBeans();
									if (signalEventBeans != null && signalEventBeans.size() > 0) {
										for (SignalEventBean signalEventBean : signalEventBeans) {
											long getmTimestamp = signalEventBean.getmTimestamp();
											String formatDate = MyDateUtil.formatDate(new Date(getmTimestamp),
													"yyyy-MM-dd HH:mm:ss");
											if (!timesList.contains(formatDate)) {
												timesList.add(formatDate);
											}
										}
									}
									// 信令
									List<SignalBean> signalBeans = signalDataBean.getSignalBeans();
									if (signalBeans != null && signalBeans.size() > 0) {
										for (SignalBean signalBean : signalBeans) {
											long getmTimestamp = signalBean.getmTimestamp();
											String formatDate = MyDateUtil.formatDate(new Date(getmTimestamp),
													"yyyy-MM-dd HH:mm:ss");
											if (!timesList.contains(formatDate)) {
												timesList.add(formatDate);
											}
										}
									}
									Collections.sort(timesList);// 时间排序
									Boolean mainCsvFlag = true;// 记录主记录是不是写入了 写入了之后立马设置为false
									if (timesList != null && timesList.size() > 0) {
										for (String time : timesList) {

											Object[] objects = new Object[176];
											for (int i = 0; i < 176; i++) {
												objects[i] = "";
											}

											// 存储当前时间的事件、信令
											List<SignalEventBean> eventDataByTime = new ArrayList<>();
											List<SignalBean> messageDataByTime = new ArrayList<>();

											// 找出时间相同的事件数据
											List<SignalEventBean> signalEventBeansModel = signalDataBean
													.getSignalEventBeans();
											if (signalEventBeansModel != null && signalEventBeansModel.size() > 0) {
												for (SignalEventBean signalEventBean : signalEventBeansModel) {
													long getmTimestamp = signalEventBean.getmTimestamp();
													String formatDate = MyDateUtil.formatDate(new Date(getmTimestamp),
															"yyyy-MM-dd HH:mm:ss");
													if (time.equals(formatDate)) {
														eventDataByTime.add(signalEventBean);
													}
													if (formatDate.compareTo(time) > 0) {
														break;
													}
												}
											}

											// 找出时间相同的信令数据
											List<SignalBean> signalBeansModel = signalDataBean.getSignalBeans();
											if (signalBeansModel != null && signalBeansModel.size() > 0) {
												for (SignalBean signalBean : signalBeansModel) {
													long getmTimestamp = signalBean.getmTimestamp();
													String formatDate = MyDateUtil.formatDate(new Date(getmTimestamp),
															"yyyy-MM-dd HH:mm:ss");
													if (time.equals(formatDate)) {
														messageDataByTime.add(signalBean);
													}
													if (formatDate.compareTo(time) > 0) {
														break;
													}
												}
											}

											if (eventDataByTime.size() == 0 && messageDataByTime.size() == 0) {// 只写入主
												if (mainCsvFlag) {
													mainCsvFlag = false;
													writeMainCsv(signalDataBean, objects);
													result.add(objects);
												}
											} else if (eventDataByTime.size() > 0 && messageDataByTime.size() == 0) {// 存在事件
																														// 、这个事件注意判断主
												for (SignalEventBean signalEventBean : eventDataByTime) {
													long getmTimestamp = signalEventBean.getmTimestamp();
													String formatDate = MyDateUtil.formatDate(new Date(getmTimestamp),
															"yyyy-MM-dd HH:mm:ss");
													if (formatDate.compareTo(testTime) == 0) {// 写入主和事件写在一个里面
														if (mainCsvFlag) {// 先写入主
															mainCsvFlag = false;
															writeMainCsv(signalDataBean, objects);
														}
														// 写入事件
														objects[5] = formatDate;
														objects[6] = MyUtil
																.nullToEmpty(signalEventBean.getmEventType());
														objects[7] = MyUtil.nullToEmpty(signalEventBean.getmEvent());
														result.add(objects);
													} else if (formatDate.compareTo(testTime) < 0) {// 只写事件
														// 写入事件
														objects[5] = formatDate;
														objects[6] = MyUtil
																.nullToEmpty(signalEventBean.getmEventType());
														objects[7] = MyUtil.nullToEmpty(signalEventBean.getmEvent());
														result.add(objects);
													} else if (formatDate.compareTo(testTime) > 0) {// 先判断主 是不是写入了
																									// 没写入写入主
																									// 在写入事件 两个object
														if (mainCsvFlag) {// 先写入主
															mainCsvFlag = false;
															Object[] objectsMain = new Object[176];
															for (int i = 0; i < 176; i++) {
																objectsMain[i] = "";
															}
															writeMainCsv(signalDataBean, objectsMain);
															result.add(objectsMain);
														}
														Object[] objectsEvent = new Object[176];
														for (int i = 0; i < 176; i++) {
															objectsEvent[i] = "";
														}
														objectsEvent[5] = formatDate;
														objectsEvent[6] = MyUtil
																.nullToEmpty(signalEventBean.getmEventType());
														objectsEvent[7] = MyUtil
																.nullToEmpty(signalEventBean.getmEvent());
														result.add(objectsEvent);
													}

												}

											} else if (eventDataByTime.size() == 0 && messageDataByTime.size() > 0) {// 存在信令
																														// 不存在事件
												for (SignalBean signalBean : messageDataByTime) {
													long getmTimestamp = signalBean.getmTimestamp();
													String formatDate = MyDateUtil.formatDate(new Date(getmTimestamp),
															"yyyy-MM-dd HH:mm:ss");
													if (formatDate.compareTo(testTime) == 0) {// 写入主和信令写在一起
														if (mainCsvFlag) {// 先写入主
															mainCsvFlag = false;
															writeMainCsv(signalDataBean, objects);
														}
														// 写入信令
														objects[0] = formatDate;
														objects[1] = MyUtil.nullToEmpty(signalBean.getmSingaType());
														objects[2] = MyUtil.nullToEmpty(signalBean.getmMessageType());
														objects[3] = MyUtil.nullToEmpty(signalBean.getmChannelType());
														result.add(objects);
													} else if (formatDate.compareTo(testTime) < 0) {// 只写信令
														// 写入信令
														objects[0] = formatDate;
														objects[1] = MyUtil.nullToEmpty(signalBean.getmSingaType());
														objects[2] = MyUtil.nullToEmpty(signalBean.getmMessageType());
														objects[3] = MyUtil.nullToEmpty(signalBean.getmChannelType());
														result.add(objects);
													} else if (formatDate.compareTo(testTime) > 0) {// 先判断主 是不是写入了
																									// 没写入写入主
																									// 在写入事件 两个object
														if (mainCsvFlag) {// 先写入主
															mainCsvFlag = false;
															Object[] objectsMain = new Object[176];
															for (int i = 0; i < 176; i++) {
																objectsMain[i] = "";
															}
															writeMainCsv(signalDataBean, objectsMain);
															result.add(objectsMain);
														}
														Object[] objectsEvent = new Object[176];
														for (int i = 0; i < 176; i++) {
															objectsEvent[i] = "";
														}
														// 写入信令
														objectsEvent[0] = formatDate;
														objectsEvent[1] = MyUtil
																.nullToEmpty(signalBean.getmSingaType());
														objectsEvent[2] = MyUtil
																.nullToEmpty(signalBean.getmMessageType());
														objectsEvent[3] = MyUtil
																.nullToEmpty(signalBean.getmChannelType());
														result.add(objectsEvent);
													}
												}

											} else if (eventDataByTime.size() > 0 && messageDataByTime.size() > 0) {// 都存在的情况
												Integer eventNum = eventDataByTime.size();
												Integer msgNum = messageDataByTime.size();
												int max = Math.max(eventNum, msgNum);
												for (int i = 0; i < max; i++) {// 注意 这里需要判断 是不是超过索引了 超过了就不要超过的部分了

													Object[] objectsLast = new Object[176];
													for (int w = 0; w < 176; w++) {
														objectsLast[w] = "";
													}

													if (time.compareTo(testTime) == 0) {// 写入主 主和 上面两个是一样的object
														if (mainCsvFlag) {
															mainCsvFlag = false;
															writeMainCsv(signalDataBean, objects);
														}
													} else if (time.compareTo(testTime) < 0) {// 只写入信令事件

													} else if (time.compareTo(testTime) > 0) {// 先判断主是不是写入了 没写入 独立写主
														if (mainCsvFlag) {
															mainCsvFlag = false;
															Object[] objectsFinal = new Object[176];
															for (int w = 0; w < 176; w++) {
																objectsFinal[w] = "";
															}
															writeMainCsv(signalDataBean, objectsFinal);
															result.add(objectsFinal);
														}
													}
													if (i < eventNum) {// 写事件
														SignalEventBean signalEventBean = eventDataByTime.get(i);
														objectsLast[5] = time;
														objectsLast[6] = MyUtil
																.nullToEmpty(signalEventBean.getmEventType());
														objectsLast[7] = MyUtil
																.nullToEmpty(signalEventBean.getmEvent());
													}
													if (i < msgNum) {// 写信令
														// 写入信令
														SignalBean signalBean = messageDataByTime.get(i);
														objectsLast[0] = time;
														objectsLast[1] = MyUtil.nullToEmpty(signalBean.getmSingaType());
														objectsLast[2] = MyUtil
																.nullToEmpty(signalBean.getmMessageType());
														objectsLast[3] = MyUtil
																.nullToEmpty(signalBean.getmChannelType());
													}
													result.add(objectsLast);
												}
											}
										}

										///
										// 循环完了之后判断是不是主写入了 没写入在最后写入
										if (mainCsvFlag) {
											mainCsvFlag = false;
											Object[] objectsFinal = new Object[176];
											for (int w = 0; w < 176; w++) {
												objectsFinal[w] = "";
											}
											writeMainCsv(signalDataBean, objectsFinal);
											result.add(objectsFinal);
										}
									}
								}
							}
						}
					}

				} else {// 4g的log导出
					numFlag++;
					SignalDataBean signalDataBean = JSONObject.parseObject(str, SignalDataBean.class);
					signalDataBean.setTestTime(MyDateUtil.formatDate(new Date(Long.valueOf(signalDataBean.getTestTime())), "yyyy-MM-dd HH:mm:ss"));
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
									objects[num + 1] = MyUtil.nullToEmpty(model.getmLteNeighborhoodPCI());
									objects[num + 2] = MyUtil.nullToEmpty(model.getmLteNeighborhoodRsrq());
									objects[num + 3] = MyUtil.nullToEmpty(model.getmLteNeighborhoodRSRPOrSINR());
									num = num + 4;
								}
							}
						}
					}
					result.add(objects);
				}

			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			
			CsvUtils.simpleExport(true, "\n", head, result, fileName, csvResult);
		}
	}

	private void writeMainCsv(NrLogBodyBean signalDataBean, Object[] objects) {
		objects[8] = MyUtil.nullToEmpty(signalDataBean.getLatitude());
		objects[9] = MyUtil.nullToEmpty(signalDataBean.getLongitude());
		objects[10] = MyUtil.nullToEmpty(signalDataBean.getSpeed());
		objects[11] = MyUtil.nullToEmpty(signalDataBean.getHeight());
		objects[12] = MyUtil.nullToEmpty(signalDataBean.getTestTime());
		objects[13] = MyUtil.nullToEmpty(signalDataBean.getDownLoadSpeed());
		objects[14] = MyUtil.nullToEmpty(signalDataBean.getUpLoadSpeed());
		objects[15] = MyUtil.nullToEmpty(signalDataBean.getNormalEventType());
		objects[16] = MyUtil.nullToEmpty(signalDataBean.getAbNormalEventType());

		ProNrDataInfoBean proNrDataInfoBean = signalDataBean.getProNrDataInfoBean();
		if (proNrDataInfoBean != null) {
			objects[17] = MyUtil.nullToEmpty(proNrDataInfoBean.getBand());
			objects[18] = MyUtil.nullToEmpty(proNrDataInfoBean.getBandWidth());
			objects[19] = MyUtil.nullToEmpty(proNrDataInfoBean.getFrequencyPointA());
			objects[20] = MyUtil.nullToEmpty(proNrDataInfoBean.getFrequencyDL());
			objects[21] = MyUtil.nullToEmpty(proNrDataInfoBean.getGSCN());
			objects[22] = MyUtil.nullToEmpty(proNrDataInfoBean.getSubCarrierSpace());
			objects[23] = MyUtil.nullToEmpty(proNrDataInfoBean.getPCI());
			objects[24] = MyUtil.nullToEmpty(proNrDataInfoBean.getSSBIndex());
			objects[25] = MyUtil.nullToEmpty(proNrDataInfoBean.getSsARFCN());
			objects[26] = MyUtil.nullToEmpty(proNrDataInfoBean.getSsRSRP());
			objects[27] = MyUtil.nullToEmpty(proNrDataInfoBean.getSsSINR());
			objects[28] = MyUtil.nullToEmpty(proNrDataInfoBean.getSsRSRQ());
			objects[29] = MyUtil.nullToEmpty(proNrDataInfoBean.getPuschTxPower());
			objects[30] = MyUtil.nullToEmpty(proNrDataInfoBean.getPucchTxPower());
			objects[31] = MyUtil.nullToEmpty(proNrDataInfoBean.getSrsTxPower());
			objects[32] = MyUtil.nullToEmpty(proNrDataInfoBean.getCqi());
			objects[33] = MyUtil.nullToEmpty(proNrDataInfoBean.getMcsUL());
			objects[34] = MyUtil.nullToEmpty(proNrDataInfoBean.getMcsDL());
			objects[35] = MyUtil.nullToEmpty(proNrDataInfoBean.getModUL());
			objects[36] = MyUtil.nullToEmpty(proNrDataInfoBean.getModDL());
			objects[37] = MyUtil.nullToEmpty(proNrDataInfoBean.getPuschBler());
			objects[38] = MyUtil.nullToEmpty(proNrDataInfoBean.getPdschBler());
			objects[39] = MyUtil.nullToEmpty(proNrDataInfoBean.getGrantULNum());
			objects[40] = MyUtil.nullToEmpty(proNrDataInfoBean.getGrantDLNum());
			objects[41] = MyUtil.nullToEmpty(proNrDataInfoBean.getRiNumDL());
			objects[42] = MyUtil.nullToEmpty(proNrDataInfoBean.getPdcpULThr());
			objects[43] = MyUtil.nullToEmpty(proNrDataInfoBean.getPdcpDLThr());
			objects[44] = MyUtil.nullToEmpty(proNrDataInfoBean.getRlcULThr());
			objects[45] = MyUtil.nullToEmpty(proNrDataInfoBean.getRlcDLThr());
			objects[46] = MyUtil.nullToEmpty(proNrDataInfoBean.getMacULThr());
			objects[47] = MyUtil.nullToEmpty(proNrDataInfoBean.getMacDLThr());
			objects[48] = MyUtil.nullToEmpty(proNrDataInfoBean.getPuschRB());
			objects[49] = MyUtil.nullToEmpty(proNrDataInfoBean.getPdschRB());
			objects[50] = MyUtil.nullToEmpty(proNrDataInfoBean.getSlotConfigDLUL());
			objects[51] = MyUtil.nullToEmpty(proNrDataInfoBean.get_16qamNum());
			objects[52] = MyUtil.nullToEmpty(proNrDataInfoBean.get_64qamNum());
			objects[53] = MyUtil.nullToEmpty(proNrDataInfoBean.get_256qamNum());
			objects[54] = MyUtil.nullToEmpty(proNrDataInfoBean.get_16qamUlNum());
			objects[55] = MyUtil.nullToEmpty(proNrDataInfoBean.get_16qamDlNum());
			objects[56] = MyUtil.nullToEmpty(proNrDataInfoBean.get_64qamUlNum());
			objects[57] = MyUtil.nullToEmpty(proNrDataInfoBean.get_64qamDlNum());
			objects[58] = MyUtil.nullToEmpty(proNrDataInfoBean.get_256qamUlNum());
			objects[59] = MyUtil.nullToEmpty(proNrDataInfoBean.get_256qamDlNum());
			objects[60] = MyUtil.nullToEmpty(proNrDataInfoBean.getQpskUlNum());
			objects[61] = MyUtil.nullToEmpty(proNrDataInfoBean.getQpskDlNum());

			List<ProNrNeighborhoodInfo> proNrNeighborhoodInfos = proNrDataInfoBean.getProNrNeighborhoodInfos();
			if (proNrNeighborhoodInfos != null && proNrNeighborhoodInfos.size() > 0) {
				Integer beninNum = 62;
				Integer cycleNum = 0;
				for (ProNrNeighborhoodInfo proLteNeighborhoodInfo : proNrNeighborhoodInfos) {
					if (cycleNum > 5) {
						break;
					}
					objects[beninNum++] = MyUtil.nullToEmpty(proLteNeighborhoodInfo.getpNrNeighborhoodNRARFCN());
					objects[beninNum++] = MyUtil.nullToEmpty(proLteNeighborhoodInfo.getpNrNeighborhoodPCI());
					objects[beninNum++] = MyUtil.nullToEmpty(proLteNeighborhoodInfo.getpNrNeighborhoodBeam());// pNrNeighborhoodReam1
					objects[beninNum++] = MyUtil.nullToEmpty(proLteNeighborhoodInfo.getpNrNeighborhoodSSRSRP());
					objects[beninNum++] = MyUtil.nullToEmpty(proLteNeighborhoodInfo.getpNrNeighborhoodSSSINR());
					objects[beninNum++] = MyUtil.nullToEmpty(proLteNeighborhoodInfo.getpNrNeighborhoodSSRSRQ());
					cycleNum++;
				}
			}
		}

		ProLteDataInfoBean proLteDataInfoBeans = signalDataBean.getProLteDataInfoBean();
		if (proLteDataInfoBeans != null) {

			objects[98] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccMcc());
			objects[99] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccMnc());
			objects[100] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccSiteEci());
			objects[101] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccBandIndex());
			objects[102] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccBwDl());
			objects[103] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccFreqDl());
			objects[104] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccTac());
			objects[105] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccSsp());
			objects[106] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccSa());
			objects[107] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccCellId());
			objects[108] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccEarfcnDl());
			objects[109] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccPci());
			objects[110] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccRsrp());
			objects[111] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccSinr());
			objects[112] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccRsrq());
			objects[113] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccRssi());
			objects[114] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccPuschTxpower());
			objects[115] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccPucchTxpower());
			objects[116] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccEnodebId());
			objects[117] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccWidebandCqi());
			objects[118] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccMcsul());
			objects[119] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccMcsdl());
			objects[120] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccModul());
			objects[121] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccModdl());
			objects[122] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccULBLER());
			objects[123] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccDLBLER());
			objects[124] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccGrantulnum());
			objects[125] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccGrantdlnum());
			objects[126] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccRankIndex());
			objects[127] = MyUtil.nullToEmpty(proLteDataInfoBeans.getThroughputPccPdcpUl());
			objects[128] = MyUtil.nullToEmpty(proLteDataInfoBeans.getThroughputPccPdcpDl());
			objects[129] = MyUtil.nullToEmpty(proLteDataInfoBeans.getThroughputPccRlcUl());
			objects[130] = MyUtil.nullToEmpty(proLteDataInfoBeans.getThroughputPccRlcDl());
			objects[131] = MyUtil.nullToEmpty(proLteDataInfoBeans.getThroughputPccMacUl());
			objects[132] = MyUtil.nullToEmpty(proLteDataInfoBeans.getThroughputPccMacDl());
			objects[133] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccPuschRbs());
			objects[134] = MyUtil.nullToEmpty(proLteDataInfoBeans.getServingCellPccPdschRbs());
			objects[135] = MyUtil.nullToEmpty(proLteDataInfoBeans.getModulationPcc16qam());
			objects[136] = MyUtil.nullToEmpty(proLteDataInfoBeans.getModulationPcc64qam());
			objects[137] = MyUtil.nullToEmpty(proLteDataInfoBeans.getModulationPcc256qam());
			objects[138] = MyUtil.nullToEmpty(proLteDataInfoBeans.getModulationPcc16qamUl());
			objects[139] = MyUtil.nullToEmpty(proLteDataInfoBeans.getModulationPcc16qamDl());
			objects[140] = MyUtil.nullToEmpty(proLteDataInfoBeans.getModulationPcc64qamUl());
			objects[141] = MyUtil.nullToEmpty(proLteDataInfoBeans.getModulationPcc64qamDl());
			objects[142] = MyUtil.nullToEmpty(proLteDataInfoBeans.getModulationPcc256qamUl());
			objects[143] = MyUtil.nullToEmpty(proLteDataInfoBeans.getModulationPcc256qamDl());
			objects[144] = MyUtil.nullToEmpty(proLteDataInfoBeans.getModulationPccQPSKUl());
			objects[145] = MyUtil.nullToEmpty(proLteDataInfoBeans.getModulationPccQPSKDl());

			List<ProLteNeighborhoodInfo> proLteNeighborhoodInfos = proLteDataInfoBeans.getProLteNeighborhoodInfos();
			if (proLteNeighborhoodInfos != null && proLteNeighborhoodInfos.size() > 0) {
				Integer beninNum = 146;
				Integer cycleNum = 0;
				for (ProLteNeighborhoodInfo proLteNeighborhoodInfo : proLteNeighborhoodInfos) {
					if (cycleNum > 5) {
						break;
					}
					objects[beninNum++] = MyUtil.nullToEmpty(proLteNeighborhoodInfo.getpLteNeighbirhoodEARFCN());
					objects[beninNum++] = MyUtil.nullToEmpty(proLteNeighborhoodInfo.getpLteNeighbirhoodPCI());
					objects[beninNum++] = MyUtil.nullToEmpty(proLteNeighborhoodInfo.getpLteNeighbirhoodRSRP());
					objects[beninNum++] = MyUtil.nullToEmpty(proLteNeighborhoodInfo.getpLteNeighbirhoodRSRQ());
					objects[beninNum++] = MyUtil.nullToEmpty(proLteNeighborhoodInfo.getpLteNeighbirhoodRSSI());
					cycleNum++;
				}
			}
		}
	}

	public String dealNormalEventType(Integer num) {

		String result = "";
		if (num == 0) {
			result = "FTPConnectionSuccess";
		} else if (num == 1) {
			result = "DownloadStart";
		} else if (num == 2) {
			result = "DownloadComplete";
		} else if (num == 3) {
			result = "UploadStart";
		} else if (num == 4) {
			result = "UploadComplete";
		} else if (num == 5) {
			result = "PingSuccess";
		} else if (num == 6) {
			result = "HttpSuccess";
		} else if (num == 7) {
			result = "CallInitiate";
		} else if (num == 8) {
			result = "CallStart";
		} else if (num == 8) {
			result = "CallEnd";
		}
		return result;
	}

	public String dealAbNormalEventType(Integer num) {
		String result = "";
		if (num == 0) {
			result = "FTPConnectionFailure";
		} else if (num == 1) {
			result = "DownloadFailure";
		} else if (num == 2) {
			result = "UploadFailure";
		} else if (num == 3) {
			result = "PingFailure";
		} else if (num == 4) {
			result = "HttpFailure";
		} else if (num == 5) {
			result = "BlockedCall";
		} else if (num == 6) {
			result = "DropedCall";
		}
		return result;
	}

	/**
	 * 
	 * @Description: 室外测试的删出 这个ids是 室外测试列表的id 非文档id 仅仅支持单个删出
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
					QueryParamData queryParamDataMsg = new QueryParamData("logmain5g", "logmain5g",
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
					// 删除统计分析的表信息
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
			return new JsonMsgUtil(false, "室外测试ids不能为空", "");
		}
		return new JsonMsgUtil(true, "删出成功", "");
	}

	/**
	 * 室外测试的查询
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年5月7日 下午1:57:22
	 */
	@ResponseBody
	@PostMapping(value = "/queryOutHome")
	public TableResultUtil analyzeLteAllAndComplete(@RequestBody QueryParamData queryParamData) {

		Map<String, Object> clearMapEmptyVal = MyUtil.clearMapEmptyValRemoveKeyword(queryParamData.getTermMap());
		clearMapEmptyVal.put("logType", "1");

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
		PageInfo<OutHomeLogModel> pageInfo = PageHelper.startPage(queryParamData.getPage(), queryParamData.getLimit())
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
	 * 4G的index/type是simgc 5G的index/type是simgc5g
	 * 
	 * @Description: sim工参查询，同时回兼容5G和4G的工参查询方法：就是 index type不一样其余的都一样
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
	 * 根据CI查询4g /5g对应的小区信息注意，4g/5g小区信息各只会存在一个
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年7月7日 上午11:04:37
	 */
	@ResponseBody
	@PostMapping(value = "/queryGcByCi")
	public List<Map<String, Object>> queryGcByCi(@RequestParam("ci") String ci) {
		List<Map<String, Object>> datas = new ArrayList<>();

		Map<String, Object> termMap = new HashMap<>();
		termMap.put("lte_ci.keyword", ci);

		QueryParamData queryParamData = new QueryParamData("simgc", "simgc", true, 1);
		queryParamData.setTermMap(termMap);// 精确查询

		// 显示的字段
		queryParamData.setRevelFields(Arrays.asList("lte_site_name", "lte_net", "lte_longitude", "lte_latitude",
				"lte_azimuth", "lte_enodebid", "lte_cell", "lte_ecgi", "lte_earfcn", "lte_tac", "lte_phycellid",
				"lte_site_tall", "lte_mechanical_downdip", "lte_electronic_downdip", "lte_ci", "lte_longitude2",
				"lte_latitude2"));
		// 查询4G
		List<Map<String, Object>> queryList = Esutil.queryList(queryParamData);
		if (queryList != null && queryList.size() > 0) {
			datas.add(queryList.get(0));
		}

		queryParamData.setIndex("simgc5g");
		queryParamData.setType("simgc5g");
		// 查询5g
		List<Map<String, Object>> queryList1 = Esutil.queryList(queryParamData);
		if (queryList1 != null && queryList1.size() > 0) {
			datas.add(queryList1.get(0));
		}
		return datas;
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
	 * 地图页面加载全部的数据 这个还是索引和index变化了
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
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("logType", "1");
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
	 * 对角坐标查询工参信息: 这个就是index/type不一样
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

			termMap.put("lte_city_name.keyword", sysProject.getProjCode() + "");// 加地市过滤的条件

			queryParamData.setTermMap(termMap);
			List<Map<String, Object>> scrollQueryList = Esutil.scrollQuery(queryParamData);
			return scrollQueryList;
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * 查询邻区的拉线问题 只查询距离最近的一个 如果一个站点 有多个邻区 那么就会有多个线 [针对 新的log] 可 兼容4G\5G工参
	 * 
	 * @Description: 查询距离某个最近的点 这里默认是 公里为单位 传入的是主题log的id \ 工参的类型gcType ： 04G 1 5G
	 * @author weichengz
	 * @date 2019年11月22日 下午2:37:43
	 */
	@ResponseBody
	@PostMapping(value = "/queryNearPoint")
	public List<Map<String, Object>> queryNearPoint(@RequestParam("id") String id,
			@RequestParam("gcType") String gcType) {

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

		// log中每个主题内容的id
		if (StringUtils.isNotBlank(id)) {
			// 查询这条id对应的主log信息
			QueryParamData queryParamDataParam = new QueryParamData("logmain5g", "logmain5g");
			queryParamDataParam.setTermMap(MyUtil.createHashMap("id~" + id));
			queryParamDataParam.setLimit(1);
			List<Map<String, Object>> queryList = Esutil.queryList(queryParamDataParam);
			if (queryList != null && queryList.size() > 0) {
				Map<String, Object> mapRes = queryList.get(0);
				FiveLogMain fiveLogMain = JSONObject.parseObject(JSONObject.toJSONString(mapRes), FiveLogMain.class);
				if (fiveLogMain != null) {
					String longitude = fiveLogMain.getLongitude();
					String latitude = fiveLogMain.getLatitude();// 经纬度
					// String logversion = fiveLogMain.getLogversion();// 0: LTE 1: NR5G NSA
					String rootSupport = fiveLogMain.getRootSupport();// 0: None Root1: Root
					if (StringUtils.isNotBlank(longitude) && StringUtils.isNotBlank(latitude)) {
						QueryParamData queryParamDataFinal = new QueryParamData();
						GeoPoint ceterPoint = new GeoPoint(Double.valueOf(latitude), Double.valueOf(longitude));
						queryParamDataFinal.dealGeoDiatanceBuss(ceterPoint, GEO_DISTANCE, "location");

						// 根据条件寻找PCI
						if (("1").equals(rootSupport)) {// root版本
							if (("0").equals(gcType)) {// 4G
								// 赋值索引类型
								QueryParamData queryParamDataGc = new QueryParamData("simgc", "simgc");
								// 地理位置的信息距离
								queryParamDataGc.setDiatinceGeoMap(queryParamDataFinal.getDiatinceGeoMap());
								queryParamDataGc.setSortGeoMap(queryParamDataFinal.getSortGeoMap());
								;

								ProLteDataInfoBean proLteDataInfoBeans = fiveLogMain.getProLteDataInfoBean();
								if (proLteDataInfoBeans != null) {
									List<ProLteNeighborhoodInfo> proLteNeighborhoodInfos = proLteDataInfoBeans
											.getProLteNeighborhoodInfos();

									List<Map<String, Object>> resultDataMap = new ArrayList<>();// 返回的结果

									if (proLteNeighborhoodInfos != null && proLteNeighborhoodInfos.size() > 0) {
										for (ProLteNeighborhoodInfo nrNeighborhoodInfo : proLteNeighborhoodInfos) {

											String mLteNeighborhoodPCI = nrNeighborhoodInfo.getpLteNeighbirhoodPCI();
											String getpLteNeighbirhoodEARFCN = nrNeighborhoodInfo
													.getpLteNeighbirhoodEARFCN();
											if (StringUtils.isNotBlank(mLteNeighborhoodPCI)
													&& StringUtils.isNotBlank(getpLteNeighbirhoodEARFCN)) {
												// 处理该地市的查询条件
												Map<String, Object> termMap = new HashMap<>();

												termMap.put("lte_earfcn.keyword", getpLteNeighbirhoodEARFCN);
												termMap.put("lte_phycellid.keyword", mLteNeighborhoodPCI);
												termMap.put("lte_city_name.keyword", sysProject.getProjCode() + "");
												queryParamDataGc.setTermMap(termMap);
												List<Map<String, Object>> queryList2 = Esutil
														.queryList(queryParamDataGc);
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
																			Double.valueOf(lte_latitude2.toString()),
																			MINT_UNIT,
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
								}
							} else {// 5g
								// 赋值索引类型
								QueryParamData queryParamDataGc = new QueryParamData("simgc5g", "simgc5g");
								// 地理位置的信息距离
								queryParamDataGc.setDiatinceGeoMap(queryParamDataFinal.getDiatinceGeoMap());
								queryParamDataGc.setSortGeoMap(queryParamDataFinal.getSortGeoMap());
								;

								ProNrDataInfoBean proNrDataInfoBean = fiveLogMain.getProNrDataInfoBean();
								if (proNrDataInfoBean != null) {
									List<ProNrNeighborhoodInfo> proNrNeighborhoodInfos = proNrDataInfoBean
											.getProNrNeighborhoodInfos();

									List<Map<String, Object>> resultDataMap = new ArrayList<>();// 返回的结果

									if (proNrNeighborhoodInfos != null && proNrNeighborhoodInfos.size() > 0) {
										for (ProNrNeighborhoodInfo nrNeighborhoodInfo : proNrNeighborhoodInfos) {

											String mLteNeighborhoodPCI = nrNeighborhoodInfo.getpNrNeighborhoodPCI();
											String getpNrNeighborhoodNRARFCN = nrNeighborhoodInfo
													.getpNrNeighborhoodNRARFCN();

											if (StringUtils.isNotBlank(mLteNeighborhoodPCI)
													&& StringUtils.isNotBlank(getpNrNeighborhoodNRARFCN)) {
												// 处理该地市的查询条件
												Map<String, Object> termMap = new HashMap<>();
												termMap.put("lte_earfcn.keyword", getpNrNeighborhoodNRARFCN);
												termMap.put("lte_phycellid.keyword", mLteNeighborhoodPCI);
												termMap.put("lte_city_name.keyword", sysProject.getProjCode() + "");
												queryParamDataGc.setTermMap(termMap);
												List<Map<String, Object>> queryList2 = Esutil
														.queryList(queryParamDataGc);
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
																			Double.valueOf(lte_latitude2.toString()),
																			MINT_UNIT,
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
									} //
									return resultDataMap;
								}
							}
						} else {// 非root
							if (("0").equals(gcType)) {// 4G

								// 赋值索引类型
								QueryParamData queryParamDataGc = new QueryParamData("simgc", "simgc");
								// 地理位置的信息距离
								queryParamDataGc.setDiatinceGeoMap(queryParamDataFinal.getDiatinceGeoMap());
								queryParamDataGc.setSortGeoMap(queryParamDataFinal.getSortGeoMap());

								LteDataInfoBean lteDataInfoBean = fiveLogMain.getLteDataInfoBean();
								if (lteDataInfoBean != null) {
									List<LteNeighborhoodInfo> lteNeighborhoodInfos = lteDataInfoBean
											.getLteNeighborhoodInfos();

									List<Map<String, Object>> resultDataMap = new ArrayList<>();// 返回的结果

									if (lteNeighborhoodInfos != null && lteNeighborhoodInfos.size() > 0) {
										for (LteNeighborhoodInfo nrNeighborhoodInfo : lteNeighborhoodInfos) {

											String mLteNeighborhoodPCI = nrNeighborhoodInfo.getLteNeighbirhoodPCI();
											String lteNeighbirhoodEARFCN = nrNeighborhoodInfo
													.getLteNeighbirhoodEARFCN();
											if (StringUtils.isNotBlank(mLteNeighborhoodPCI)
													&& StringUtils.isNotBlank(lteNeighbirhoodEARFCN)) {
												// 处理该地市的查询条件
												Map<String, Object> termMap = new HashMap<>();

												termMap.put("lte_earfcn.keyword", lteNeighbirhoodEARFCN);
												termMap.put("lte_phycellid.keyword", mLteNeighborhoodPCI);
												termMap.put("lte_city_name.keyword", sysProject.getProjCode() + "");
												queryParamDataGc.setTermMap(termMap);
												List<Map<String, Object>> queryList2 = Esutil
														.queryList(queryParamDataGc);
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
																			Double.valueOf(lte_latitude2.toString()),
																			MINT_UNIT,
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
								}
							} else {// 5g
								// 赋值索引类型
								QueryParamData queryParamDataGc = new QueryParamData("simgc5g", "simgc5g");
								// 地理位置的信息距离
								queryParamDataGc.setDiatinceGeoMap(queryParamDataFinal.getDiatinceGeoMap());
								queryParamDataGc.setSortGeoMap(queryParamDataFinal.getSortGeoMap());

								NrDataInfoBean nrDataInfoBean = fiveLogMain.getNrDataInfoBean();
								if (nrDataInfoBean != null) {
									List<NrNeighborhoodInfo> nrNeighborhoodInfos = nrDataInfoBean
											.getNrNeighborhoodInfos();

									List<Map<String, Object>> resultDataMap = new ArrayList<>();// 返回的结果

									if (nrNeighborhoodInfos != null && nrNeighborhoodInfos.size() > 0) {
										for (NrNeighborhoodInfo nrNeighborhoodInfo : nrNeighborhoodInfos) {

											String mLteNeighborhoodPCI = nrNeighborhoodInfo.getNrNeighborhoodPCI();
											String nrNeighborhoodNRARFCN = nrNeighborhoodInfo
													.getNrNeighborhoodNRARFCN();

											if (StringUtils.isNotBlank(mLteNeighborhoodPCI)
													&& StringUtils.isNotBlank(nrNeighborhoodNRARFCN)) {
												// 处理该地市的查询条件
												Map<String, Object> termMap = new HashMap<>();

												termMap.put("lte_earfcn.keyword", nrNeighborhoodNRARFCN);
												termMap.put("lte_phycellid.keyword", mLteNeighborhoodPCI);
												termMap.put("lte_city_name.keyword", sysProject.getProjCode() + "");
												queryParamDataGc.setTermMap(termMap);
												List<Map<String, Object>> queryList2 = Esutil
														.queryList(queryParamDataGc);
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
																			Double.valueOf(lte_latitude2.toString()),
																			MINT_UNIT,
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
									} //
									return resultDataMap;
								}
							}
						}
					}
				}
			}
		}
		return new ArrayList<>();
	}

	/**
	 * 小区拉线全网拉线
	 * 
	 * gcType: 0 4g 1 5g 不填写就是4g5g都连 前4个同时存在的时候 为4小区拉线 否则为全网拉线 对于全网拉线 gcType 可不填写
	 * 不填写就是拉取4g 5g
	 * 
	 * @Description: pid室外测试的id 这个必须要有
	 * @author weichengz
	 * @date 2020年7月7日 下午2:17:48
	 * 
	 *       返回的格式定制为：[{lnt: lat: points: [{lnt: lat:}]},] 前面是 扇区的，
	 */
	@ResponseBody
	@PostMapping(value = "/queryCellNetLine")
	public List<Map<String, Object>> queryCellNetLine(
			@RequestParam(value = "longitude", required = false) String longitude,
			@RequestParam(value = "latitude", required = false) String latitude,
			@RequestParam(value = "azimuth", required = false) String azimuth,

			@RequestParam(value = "gcType", required = false) String gcType,

			@RequestParam(value = "pid", required = false) String pid) {

		LoginUser loginUser = UserUtil.getLoginUser();
		if (loginUser == null) {
			throw new IllegalArgumentException("当前登陆用户失效");
		}

		if (StringUtils.isBlank(pid)) {
			throw new IllegalArgumentException("室外测试id不能为空");
		}

		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());
		if (sysProject == null) {
			throw new IllegalArgumentException("该用户不属于任何地市");
		} else {
			if (StringUtils.isBlank(sysProject.getProjCode())) {
				throw new IllegalArgumentException("该地市【" + sysProject.getProjName() + "】对应的sim地市不存在");
			}
		}

		List<Map<String, Object>> resultFinal = new ArrayList<Map<String, Object>>();// 返回的结果

		if (StringUtils.isNotBlank(longitude) && StringUtils.isNotBlank(latitude) && StringUtils.isNotBlank(azimuth)
				&& StringUtils.isNotBlank(gcType)) {// 小区拉线
			if (("1").equals(gcType)) {// 5g小区
				// 查询5g工参信息
				QueryParamData paramModel = new QueryParamData("simgc5g", "simgc5g");
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("lte_longitude2.keyword", longitude);
				paramMap.put("lte_latitude2.keyword", latitude);
				paramMap.put("lte_azimuth.keyword", azimuth);
				paramModel.setTermMap(paramMap);
				paramModel.setLimit(1);
				List<Map<String, Object>> datas5g = Esutil.queryList(paramModel);
				if (datas5g != null && datas5g.size() > 0) {
					for (Map<String, Object> map : datas5g) {
						Object objectlte_earfcn = map.get("lte_earfcn");
						Object objectlte_phycellid = map.get("lte_phycellid");
						String randStr = UUID.randomUUID().toString().replace("-", "");
						String objectlte_ci = map.get("pk") == null ? randStr : map.get("pk").toString();
						objectlte_ci = objectlte_ci.replace("_", "~");

						if (objectlte_earfcn != null && objectlte_phycellid != null) {
							String lteEarfcn = objectlte_earfcn.toString();
							String ltePhycellid = objectlte_phycellid.toString();
							if (StringUtils.isNotBlank(lteEarfcn) && StringUtils.isNotBlank(ltePhycellid)) {
								// 先查询出一条信息 root/非root
								QueryParamData queryParam = new QueryParamData("logmain5g", "logmain5g");
								Map<String, Object> termMap = new HashMap<>();
								termMap.put("pid.keyword", pid.trim());
								queryParam.setTermMap(termMap);
								queryParam.setLimit(1);
								queryParam.setRevelFields(Arrays.asList("logversion", "rootSupport"));
								List<Map<String, Object>> mainDtas = Esutil.queryList(queryParam);
								if (mainDtas != null && mainDtas.size() > 0) {
									Map<String, Object> map2 = mainDtas.get(0);
									Object logversion = map2.get("logversion");
									Object rootSupport = map2.get("rootSupport");
									if (rootSupport != null && logversion != null) {
										String rootSupportStr = rootSupport.toString();
										String logversionStr = logversion.toString();

										// 不同的类型下，根据数据的信息进行查询
										QueryParamData finalDatas = new QueryParamData("logmain5g", "logmain5g");
										Map<String, Object> finaltermMap = new HashMap<>();
										finaltermMap.put("pid.keyword", pid.trim());
										finalDatas.setTermMap(finaltermMap);
										finalDatas.setLimit(500);// 滚动数量
										finalDatas.setRevelFields(Arrays.asList("latitude", "longitude"));

										if (("1").equals(rootSupportStr)) {// root版本
											if (("1").equals(logversionStr)) {// 5G
												Map<String, Object> phraseMapMap = new HashMap<>();
												phraseMapMap.put("proNrDataInfoBean.ssARFCN", lteEarfcn);
												phraseMapMap.put("proNrDataInfoBean.PCI", ltePhycellid);
												finalDatas.setPhraseMap(phraseMapMap);
												List<Map<String, Object>> scrollQueryDatas = Esutil
														.scrollQuery(finalDatas);
												if (scrollQueryDatas != null && scrollQueryDatas.size() > 0) {
													Map<String, Object> mapSecond = new HashMap<>();

													// 便宜当前的经纬度
													String convertDistanceToLogLat = GeoLngLatUtil
															.ConvertDistanceToLogLat(Double.valueOf(longitude),
																	Double.valueOf(latitude), MINT_UNIT,
																	Double.valueOf(azimuth));
													if (convertDistanceToLogLat != null) {
														String[] split = convertDistanceToLogLat.split(",");
														if (split != null && split.length == 2) {
															mapSecond.put("ci", objectlte_ci);
															mapSecond.put("pci", ltePhycellid);
															mapSecond.put("lng", split[0]);
															mapSecond.put("lat", split[1]);
														}
													}

													List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
													// 添加对应的轨迹点
													for (Map<String, Object> map3 : scrollQueryDatas) {
														Object objectLatitude = map3.get("latitude");
														Object objectLongitude = map3.get("longitude");
														if (objectLatitude != null && objectLongitude != null) {
															Map<String, Object> mapModel = new HashMap<>();
															mapModel.put("lat", objectLatitude.toString());
															mapModel.put("lng", objectLongitude.toString());
															resultMap.add(mapModel);
														}
													}
													mapSecond.put("points", resultMap);
													resultFinal.add(mapSecond);
													return resultFinal;
												}

											}
										} else {// 非root
											if (("1").equals(logversionStr)) {// 5G
												Map<String, Object> phraseMapMap = new HashMap<>();
												phraseMapMap.put("nrDataInfoBean.nrARFCN", lteEarfcn);
												phraseMapMap.put("nrDataInfoBean.nrPCI", ltePhycellid);
												finalDatas.setPhraseMap(phraseMapMap);
												List<Map<String, Object>> scrollQueryDatas = Esutil
														.scrollQuery(finalDatas);
												if (scrollQueryDatas != null && scrollQueryDatas.size() > 0) {
													Map<String, Object> mapSecond = new HashMap<>();
													// 便宜当前的经纬度
													String convertDistanceToLogLat = GeoLngLatUtil
															.ConvertDistanceToLogLat(Double.valueOf(longitude),
																	Double.valueOf(latitude), MINT_UNIT,
																	Double.valueOf(azimuth));
													if (convertDistanceToLogLat != null) {
														String[] split = convertDistanceToLogLat.split(",");
														if (split != null && split.length == 2) {
															mapSecond.put("pci", ltePhycellid);
															mapSecond.put("ci", objectlte_ci);
															mapSecond.put("lng", split[0]);
															mapSecond.put("lat", split[1]);
														}
													}

													List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
													// 添加对应的轨迹点
													for (Map<String, Object> map3 : scrollQueryDatas) {
														Object objectLatitude = map3.get("latitude");
														Object objectLongitude = map3.get("longitude");
														if (objectLatitude != null && objectLongitude != null) {
															Map<String, Object> mapModel = new HashMap<>();
															mapModel.put("lat", objectLatitude.toString());
															mapModel.put("lng", objectLongitude.toString());
															resultMap.add(mapModel);
														}
													}
													mapSecond.put("points", resultMap);
													resultFinal.add(mapSecond);
													return resultFinal;
												}

											}
										}
									}
								}

								break;
							} else {
								continue;
							}
						} else {
							continue;
						}
					}
				}

			} else {// 4g小区
				QueryParamData paramModel = new QueryParamData("simgc", "simgc");
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("lte_longitude2.keyword", longitude);
				paramMap.put("lte_latitude2.keyword", latitude);
				paramMap.put("lte_azimuth.keyword", azimuth);
				paramModel.setTermMap(paramMap);
				paramModel.setLimit(1);
				List<Map<String, Object>> datas5g = Esutil.queryList(paramModel);
				if (datas5g != null && datas5g.size() > 0) {
					for (Map<String, Object> map : datas5g) {
						Object objectlte_cell = map.get("lte_cell");
						Object objectlte_enodebid = map.get("lte_enodebid");
						String randStr = UUID.randomUUID().toString().replace("-", "");
						String objectlte_ci = map.get("pk") == null ? randStr : map.get("pk").toString();
						String objectlte_phycellid = map.get("lte_phycellid") == null ? ""
								: map.get("lte_phycellid").toString();
						objectlte_ci = objectlte_ci.replace("_", "~");

						if (objectlte_cell != null && objectlte_enodebid != null) {
							String ltecell = objectlte_cell.toString();
							String lteEnodebid = objectlte_enodebid.toString();
							if (StringUtils.isNotBlank(ltecell) && StringUtils.isNotBlank(lteEnodebid)) {
								// 先查询出一条信息 root/非root
								QueryParamData queryParam = new QueryParamData("logmain5g", "logmain5g");
								Map<String, Object> termMap = new HashMap<>();
								termMap.put("pid.keyword", pid.trim());
								queryParam.setTermMap(termMap);
								queryParam.setLimit(1);
								queryParam.setRevelFields(Arrays.asList("logversion", "rootSupport"));
								List<Map<String, Object>> mainDtas = Esutil.queryList(queryParam);
								if (mainDtas != null && mainDtas.size() > 0) {
									Map<String, Object> map2 = mainDtas.get(0);
									Object logversion = map2.get("logversion");
									Object rootSupport = map2.get("rootSupport");
									if (rootSupport != null && logversion != null) {
										String rootSupportStr = rootSupport.toString();
										String logversionStr = logversion.toString();

										// 不同的类型下，根据数据的信息进行查询
										QueryParamData finalDatas = new QueryParamData("logmain5g", "logmain5g");
										Map<String, Object> finaltermMap = new HashMap<>();
										finaltermMap.put("pid.keyword", pid.trim());
										finalDatas.setTermMap(finaltermMap);
										finalDatas.setLimit(500);// 滚动数量
										finalDatas.setRevelFields(Arrays.asList("latitude", "longitude"));

										if (("1").equals(rootSupportStr)) {// root版本
											if (("0").equals(logversionStr)) {// 4G
												Map<String, Object> phraseMapMap = new HashMap<>();
												phraseMapMap.put("proLteDataInfoBeans.servingCellPccCellId", ltecell);
												phraseMapMap.put("proNrDataInfoBean.servingCellPccEnodebId",
														lteEnodebid);
												finalDatas.setPhraseMap(phraseMapMap);
												List<Map<String, Object>> scrollQueryDatas = Esutil
														.scrollQuery(finalDatas);
												if (scrollQueryDatas != null && scrollQueryDatas.size() > 0) {
													Map<String, Object> mapSecond = new HashMap<>();
													// 便宜当前的经纬度
													String convertDistanceToLogLat = GeoLngLatUtil
															.ConvertDistanceToLogLat(Double.valueOf(longitude),
																	Double.valueOf(latitude), MINT_UNIT,
																	Double.valueOf(azimuth));

													if (convertDistanceToLogLat != null) {
														String[] split = convertDistanceToLogLat.split(",");
														if (split != null && split.length == 2) {
															mapSecond.put("pci", objectlte_phycellid);
															mapSecond.put("ci", objectlte_ci);
															mapSecond.put("lng", split[0]);
															mapSecond.put("lat", split[1]);
														}
													}

													List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
													// 添加对应的轨迹点
													for (Map<String, Object> map3 : scrollQueryDatas) {
														Object objectLatitude = map3.get("latitude");
														Object objectLongitude = map3.get("longitude");
														if (objectLatitude != null && objectLongitude != null) {
															Map<String, Object> mapModel = new HashMap<>();
															mapModel.put("lat", objectLatitude.toString());
															mapModel.put("lng", objectLongitude.toString());
															resultMap.add(mapModel);
														}
													}
													mapSecond.put("points", resultMap);
													resultFinal.add(mapSecond);
													return resultFinal;
												}

											}
										} else {// 非root
											if (("0").equals(logversionStr)) {// 4G
												Map<String, Object> phraseMapMap = new HashMap<>();
												phraseMapMap.put("lteDataInfoBean.lteCellID", ltecell);
												phraseMapMap.put("lteDataInfoBean.lteENB", lteEnodebid);
												finalDatas.setPhraseMap(phraseMapMap);
												List<Map<String, Object>> scrollQueryDatas = Esutil
														.scrollQuery(finalDatas);
												if (scrollQueryDatas != null && scrollQueryDatas.size() > 0) {
													Map<String, Object> mapSecond = new HashMap<>();
													// 便宜当前的经纬度
													String convertDistanceToLogLat = GeoLngLatUtil
															.ConvertDistanceToLogLat(Double.valueOf(longitude),
																	Double.valueOf(latitude), MINT_UNIT,
																	Double.valueOf(azimuth));
													if (convertDistanceToLogLat != null) {
														String[] split = convertDistanceToLogLat.split(",");
														if (split != null && split.length == 2) {

															mapSecond.put("ci", objectlte_ci);
															mapSecond.put("pci", objectlte_phycellid);

															mapSecond.put("lng", split[0]);
															mapSecond.put("lat", split[1]);
														}
													}

													List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
													// 添加对应的轨迹点
													for (Map<String, Object> map3 : scrollQueryDatas) {
														Object objectLatitude = map3.get("latitude");
														Object objectLongitude = map3.get("longitude");
														if (objectLatitude != null && objectLongitude != null) {
															Map<String, Object> mapModel = new HashMap<>();
															mapModel.put("lat", objectLatitude.toString());
															mapModel.put("lng", objectLongitude.toString());
															resultMap.add(mapModel);
														}
													}
													mapSecond.put("points", resultMap);
													resultFinal.add(mapSecond);
													return resultFinal;
												}

											}
										}
									}
								}

								break;
							} else {
								continue;
							}
						} else {
							continue;
						}
					}
				}

				//
			}
		} else {// 全网拉线
			Map<String, List<String>> secondFinal = new HashMap<>();// 这个适用于最终转换为结果的map key为扇区经纬度 v为轨迹的经纬度

			// 查询出所有的轨迹信息
			QueryParamData queryParam = new QueryParamData("logmain5g", "logmain5g");
			Map<String, Object> termMap = new HashMap<>();
			termMap.put("pid.keyword", pid.trim());
			queryParam.setTermMap(termMap);
			queryParam.setLimit(500);

			if (StringUtils.isBlank(gcType)) {// 4g 5g的拉线都有
				/*****/
				// 4g的全网拉线
				queryParam.setRevelFields(Arrays.asList("logversion", "rootSupport", "latitude", "nrDataInfoBean",
						"proNrDataInfoBean", "longitude", "lteDataInfoBean", "proLteDataInfoBean"));
				List<Map<String, Object>> mainDtas = Esutil.scrollQuery(queryParam);
				if (mainDtas != null && mainDtas.size() > 0) {
					// 查询出root非root信息
					Map<String, Object> map2 = mainDtas.get(0);
					Object logversion = map2.get("logversion");
					Object rootSupport = map2.get("rootSupport");
					if (rootSupport != null && logversion != null) {
						String rootSupportStr = rootSupport.toString();
						if (("1").equals(rootSupportStr)) {// root
							// 循环处理每个一点的
							for (Map<String, Object> map : mainDtas) {
								Object longitudeMain = map.get("longitude");
								Object latitudeMain = map.get("latitude");

								FiveLogMain fiveLogMain = JSONObject.parseObject(JSONObject.toJSONString(map),
										FiveLogMain.class);
								if (fiveLogMain != null) {

									// 处理5g工参
									ProNrDataInfoBean proNrDataInfoBean = fiveLogMain.getProNrDataInfoBean();
									if (proNrDataInfoBean != null) {
										String arfcn = proNrDataInfoBean.getSsARFCN();
										String pci = proNrDataInfoBean.getPCI();
										if (StringUtils.isNotBlank(arfcn) && StringUtils.isNotBlank(pci)) {
											QueryParamData queryParamDataCell = new QueryParamData("simgc5g",
													"simgc5g");
											queryParamDataCell.setLimit(1);
											Map<String, Object> xiaoquTermMap = new HashMap<>();
											xiaoquTermMap.put("lte_earfcn.keyword", arfcn);
											xiaoquTermMap.put("lte_phycellid.keyword", pci);
											queryParamDataCell.setTermMap(xiaoquTermMap);
											List<Map<String, Object>> gcs4g = Esutil.queryList(queryParamDataCell);
											if (gcs4g != null && gcs4g.size() > 0) {

												Map<String, Object> map3 = gcs4g.get(0);
												Object lte_longitude2 = map3.get("lte_longitude2");
												Object lte_latitude2 = map3.get("lte_latitude2");
												Object lte_azimuth = map3.get("lte_azimuth");
												String pk = map3.get("pk") == null
														? UUID.randomUUID().toString().replace("-", "")
														: map3.get("pk").toString();
												pk = pk.replace("_", "~");
												String pciModel = map3.get("lte_phycellid") == null ? "0"
														: map3.get("lte_phycellid").toString();

												if (lte_longitude2 != null && lte_latitude2 != null) {
													String convertDistanceToLogLat = GeoLngLatUtil
															.ConvertDistanceToLogLat(
																	Double.valueOf(lte_longitude2.toString()),
																	Double.valueOf(lte_latitude2.toString()), MINT_UNIT,
																	Double.valueOf(lte_azimuth.toString()));
													if (convertDistanceToLogLat != null) {
														String[] split = convertDistanceToLogLat.split(",");
														if (split != null && split.length == 2) {
															String key = split[0] + "_" + split[1] + "_" + pciModel
																	+ "_" + pk;
															if (secondFinal.containsKey(key)) {
																List<String> list = secondFinal.get(key);
																if (!list
																		.contains(longitudeMain + "_" + latitudeMain)) {
																	list.add(longitudeMain + "_" + latitudeMain);
																}
																secondFinal.put(key, list);
															} else {
																List<String> datasmm = new ArrayList<>();
																datasmm.add(longitudeMain + "_" + latitudeMain);
																secondFinal.put(key, datasmm);
															}
														}
													}
												}
											}
										}
									}
									// 处理4g工参
									ProLteDataInfoBean proLteDataInfoBeans = fiveLogMain.getProLteDataInfoBean();
									if (proLteDataInfoBeans != null) {
										String servingCellPccCellId = proLteDataInfoBeans.getServingCellPccCellId();
										String servingCellPccEnodebId = proLteDataInfoBeans.getServingCellPccEnodebId();
										if (StringUtils.isNotBlank(servingCellPccCellId)
												&& StringUtils.isNotBlank(servingCellPccEnodebId)) {
											QueryParamData queryParamDataCell = new QueryParamData("simgc", "simgc");
											queryParamDataCell.setLimit(1);
											Map<String, Object> xiaoquTermMap = new HashMap<>();
											xiaoquTermMap.put("lte_cell.keyword", servingCellPccCellId);
											xiaoquTermMap.put("lte_enodebid.keyword", servingCellPccEnodebId);
											queryParamDataCell.setTermMap(xiaoquTermMap);
											List<Map<String, Object>> gcs4g = Esutil.queryList(queryParamDataCell);
											if (gcs4g != null && gcs4g.size() > 0) {

												Map<String, Object> map3 = gcs4g.get(0);
												Object lte_longitude2 = map3.get("lte_longitude2");
												Object lte_latitude2 = map3.get("lte_latitude2");
												Object lte_azimuth = map3.get("lte_azimuth");
												String pk = map3.get("pk") == null
														? UUID.randomUUID().toString().replace("-", "")
														: map3.get("lte_phycellid").toString();
												pk = pk.replace("_", "~");
												String pciModel = map3.get("lte_phycellid") == null ? "0"
														: map3.get("lte_phycellid").toString();

												if (lte_longitude2 != null && lte_latitude2 != null) {
													String convertDistanceToLogLat = GeoLngLatUtil
															.ConvertDistanceToLogLat(
																	Double.valueOf(lte_longitude2.toString()),
																	Double.valueOf(lte_latitude2.toString()), MINT_UNIT,
																	Double.valueOf(lte_azimuth.toString()));
													if (convertDistanceToLogLat != null) {
														String[] split = convertDistanceToLogLat.split(",");
														if (split != null && split.length == 2) {
															String key = split[0] + "_" + split[1] + "_" + pciModel
																	+ "_" + pk;
															if (secondFinal.containsKey(key)) {
																List<String> list = secondFinal.get(key);
																if (!list
																		.contains(longitudeMain + "_" + latitudeMain)) {
																	list.add(longitudeMain + "_" + latitudeMain);
																}
																secondFinal.put(key, list);
															} else {
																List<String> datasmm = new ArrayList<>();
																datasmm.add(longitudeMain + "_" + latitudeMain);
																secondFinal.put(key, datasmm);
															}
														}
													}
												}
											}
										}
									}

								}
							}
						} else {// 非root
								// root
								// 循环处理每个一点的
							for (Map<String, Object> map : mainDtas) {
								FiveLogMain fiveLogMain = JSONObject.parseObject(JSONObject.toJSONString(map),
										FiveLogMain.class);
								if (fiveLogMain != null) {
									Object longitudeMain = map.get("longitude");
									Object latitudeMain = map.get("latitude");
									// 处理5g工参
									NrDataInfoBean proNrDataInfoBean = fiveLogMain.getNrDataInfoBean();
									if (proNrDataInfoBean != null) {
										String arfcn = proNrDataInfoBean.getNrARFCN();
										String pci = proNrDataInfoBean.getNrPCI();
										if (StringUtils.isNotBlank(arfcn) && StringUtils.isNotBlank(pci)) {
											QueryParamData queryParamDataCell = new QueryParamData("simgc5g",
													"simgc5g");
											queryParamDataCell.setLimit(1);
											Map<String, Object> xiaoquTermMap = new HashMap<>();
											xiaoquTermMap.put("lte_earfcn.keyword", arfcn);
											xiaoquTermMap.put("lte_phycellid.keyword", pci);
											queryParamDataCell.setTermMap(xiaoquTermMap);
											List<Map<String, Object>> gcs4g = Esutil.queryList(queryParamDataCell);
											if (gcs4g != null && gcs4g.size() > 0) {

												Map<String, Object> map3 = gcs4g.get(0);
												Object lte_longitude2 = map3.get("lte_longitude2");
												Object lte_latitude2 = map3.get("lte_latitude2");
												Object lte_azimuth = map3.get("lte_azimuth");
												String pk = map3.get("pk") == null
														? UUID.randomUUID().toString().replace("-", "")
														: map3.get("pk").toString();
												pk = pk.replace("_", "~");
												String pciModel = map3.get("lte_phycellid") == null ? "0"
														: map3.get("lte_phycellid").toString();

												if (lte_longitude2 != null && lte_latitude2 != null) {
													String convertDistanceToLogLat = GeoLngLatUtil
															.ConvertDistanceToLogLat(
																	Double.valueOf(lte_longitude2.toString()),
																	Double.valueOf(lte_latitude2.toString()), MINT_UNIT,
																	Double.valueOf(lte_azimuth.toString()));
													if (convertDistanceToLogLat != null) {
														String[] split = convertDistanceToLogLat.split(",");
														if (split != null && split.length == 2) {
															String key = split[0] + "_" + split[1] + "_" + pciModel
																	+ "_" + pk;
															if (secondFinal.containsKey(key)) {
																List<String> list = secondFinal.get(key);
																if (!list
																		.contains(longitudeMain + "_" + latitudeMain)) {
																	list.add(longitudeMain + "_" + latitudeMain);
																}
																secondFinal.put(key, list);
															} else {
																List<String> datasmm = new ArrayList<>();
																datasmm.add(longitudeMain + "_" + latitudeMain);
																secondFinal.put(key, datasmm);
															}
														}
													}
												}
											}
										}
									}

									// 处理4g工参
									LteDataInfoBean proLteDataInfoBeans = fiveLogMain.getLteDataInfoBean();
									if (proLteDataInfoBeans != null) {
										String servingCellPccCellId = proLteDataInfoBeans.getLteCellID();
										String servingCellPccEnodebId = proLteDataInfoBeans.getLteENB();
										if (StringUtils.isNotBlank(servingCellPccCellId)
												&& StringUtils.isNotBlank(servingCellPccEnodebId)) {
											QueryParamData queryParamDataCell = new QueryParamData("simgc", "simgc");
											queryParamDataCell.setLimit(1);
											Map<String, Object> xiaoquTermMap = new HashMap<>();
											xiaoquTermMap.put("lte_cell.keyword", servingCellPccCellId);
											xiaoquTermMap.put("lte_enodebid.keyword", servingCellPccEnodebId);
											queryParamDataCell.setTermMap(xiaoquTermMap);
											List<Map<String, Object>> gcs4g = Esutil.queryList(queryParamDataCell);
											if (gcs4g != null && gcs4g.size() > 0) {

												Map<String, Object> map3 = gcs4g.get(0);
												Object lte_longitude2 = map3.get("lte_longitude2");
												Object lte_latitude2 = map3.get("lte_latitude2");
												Object lte_azimuth = map3.get("lte_azimuth");
												String pk = map3.get("pk") == null
														? UUID.randomUUID().toString().replace("-", "")
														: map3.get("pk").toString();
												pk = pk.replace("_", "~");
												String pciModel = map3.get("lte_phycellid") == null ? "0"
														: map3.get("lte_phycellid").toString();

												if (lte_longitude2 != null && lte_latitude2 != null) {
													String convertDistanceToLogLat = GeoLngLatUtil
															.ConvertDistanceToLogLat(
																	Double.valueOf(lte_longitude2.toString()),
																	Double.valueOf(lte_latitude2.toString()), MINT_UNIT,
																	Double.valueOf(lte_azimuth.toString()));
													if (convertDistanceToLogLat != null) {
														String[] split = convertDistanceToLogLat.split(",");
														if (split != null && split.length == 2) {
															String key = split[0] + "_" + split[1] + "_" + pciModel
																	+ "_" + pk;
															if (secondFinal.containsKey(key)) {
																List<String> list = secondFinal.get(key);
																if (!list
																		.contains(longitudeMain + "_" + latitudeMain)) {
																	list.add(longitudeMain + "_" + latitudeMain);
																}
																secondFinal.put(key, list);
															} else {
																List<String> datasmm = new ArrayList<>();
																datasmm.add(longitudeMain + "_" + latitudeMain);
																secondFinal.put(key, datasmm);
															}
														}
													}
												}
											}
										}
									}
								}
							}

						}
					}
				}
				/*****/
			} else if (("0").equals(gcType)) {// 4g的全网拉线
				queryParam.setRevelFields(Arrays.asList("logversion", "rootSupport", "latitude", "longitude",
						"lteDataInfoBean", "proLteDataInfoBean"));
				List<Map<String, Object>> mainDtas = Esutil.scrollQuery(queryParam);
				if (mainDtas != null && mainDtas.size() > 0) {
					// 查询出root非root信息
					Map<String, Object> map2 = mainDtas.get(0);
					Object logversion = map2.get("logversion");
					Object rootSupport = map2.get("rootSupport");
					if (rootSupport != null && logversion != null) {
						String rootSupportStr = rootSupport.toString();
						String logversionStr = logversion.toString();
						if (("1").equals(rootSupportStr)) {// root
							if (("0").equals(logversionStr)) {
								// 循环处理每个一点的
								for (Map<String, Object> map : mainDtas) {
									Object longitudeMain = map.get("longitude");
									Object latitudeMain = map.get("latitude");
									FiveLogMain fiveLogMain = JSONObject.parseObject(JSONObject.toJSONString(map),
											FiveLogMain.class);
									if (fiveLogMain != null) {
										ProLteDataInfoBean proLteDataInfoBeans = fiveLogMain.getProLteDataInfoBean();
										if (proLteDataInfoBeans != null) {
											String servingCellPccCellId = proLteDataInfoBeans.getServingCellPccCellId();
											String servingCellPccEnodebId = proLteDataInfoBeans
													.getServingCellPccEnodebId();
											if (StringUtils.isNotBlank(servingCellPccCellId)
													&& StringUtils.isNotBlank(servingCellPccEnodebId)) {
												QueryParamData queryParamDataCell = new QueryParamData("simgc",
														"simgc");
												queryParamDataCell.setLimit(1);
												Map<String, Object> xiaoquTermMap = new HashMap<>();
												xiaoquTermMap.put("lte_cell.keyword", servingCellPccCellId);
												xiaoquTermMap.put("lte_enodebid.keyword", servingCellPccEnodebId);
												queryParamDataCell.setTermMap(xiaoquTermMap);
												List<Map<String, Object>> gcs4g = Esutil.queryList(queryParamDataCell);
												if (gcs4g != null && gcs4g.size() > 0) {

													Map<String, Object> map3 = gcs4g.get(0);
													Object lte_longitude2 = map3.get("lte_longitude2");
													Object lte_latitude2 = map3.get("lte_latitude2");
													Object lte_azimuth = map3.get("lte_azimuth");
													String pk = map3.get("pk") == null
															? UUID.randomUUID().toString().replace("-", "")
															: map3.get("pk").toString();
													pk = pk.replace("_", "~");
													String pciModel = map3.get("lte_phycellid") == null ? "0"
															: map3.get("lte_phycellid").toString();

													if (lte_longitude2 != null && lte_latitude2 != null) {
														String convertDistanceToLogLat = GeoLngLatUtil
																.ConvertDistanceToLogLat(
																		Double.valueOf(lte_longitude2.toString()),
																		Double.valueOf(lte_latitude2.toString()),
																		MINT_UNIT,
																		Double.valueOf(lte_azimuth.toString()));
														if (convertDistanceToLogLat != null) {
															String[] split = convertDistanceToLogLat.split(",");
															if (split != null && split.length == 2) {
																String key = split[0] + "_" + split[1] + "_" + pciModel
																		+ "_" + pk;
																if (secondFinal.containsKey(key)) {
																	List<String> list = secondFinal.get(key);
																	if (!list.contains(
																			longitudeMain + "_" + latitudeMain)) {
																		list.add(longitudeMain + "_" + latitudeMain);
																	}
																	secondFinal.put(key, list);
																} else {
																	List<String> datasmm = new ArrayList<>();
																	datasmm.add(longitudeMain + "_" + latitudeMain);
																	secondFinal.put(key, datasmm);
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						} else {// 非root
							if (("0").equals(logversionStr)) {
								// 循环处理每个一点的
								for (Map<String, Object> map : mainDtas) {
									Object longitudeMain = map2.get("longitude");
									Object latitudeMain = map2.get("latitude");
									FiveLogMain fiveLogMain = JSONObject.parseObject(JSONObject.toJSONString(map),
											FiveLogMain.class);
									if (fiveLogMain != null) {
										LteDataInfoBean proLteDataInfoBeans = fiveLogMain.getLteDataInfoBean();
										if (proLteDataInfoBeans != null) {
											String servingCellPccCellId = proLteDataInfoBeans.getLteCellID();
											String servingCellPccEnodebId = proLteDataInfoBeans.getLteENB();
											if (StringUtils.isNotBlank(servingCellPccCellId)
													&& StringUtils.isNotBlank(servingCellPccEnodebId)) {
												QueryParamData queryParamDataCell = new QueryParamData("simgc",
														"simgc");
												queryParamDataCell.setLimit(1);
												Map<String, Object> xiaoquTermMap = new HashMap<>();
												xiaoquTermMap.put("lte_cell.keyword", servingCellPccCellId);
												xiaoquTermMap.put("lte_enodebid.keyword", servingCellPccEnodebId);
												queryParamDataCell.setTermMap(xiaoquTermMap);
												List<Map<String, Object>> gcs4g = Esutil.queryList(queryParamDataCell);
												if (gcs4g != null && gcs4g.size() > 0) {

													Map<String, Object> map3 = gcs4g.get(0);
													Object lte_longitude2 = map3.get("lte_longitude2");
													Object lte_latitude2 = map3.get("lte_latitude2");
													Object lte_azimuth = map3.get("lte_azimuth");
													String pk = map3.get("pk") == null
															? UUID.randomUUID().toString().replace("-", "")
															: map3.get("pk").toString();
													pk = pk.replace("_", "~");
													String pciModel = map3.get("lte_phycellid") == null ? "0"
															: map3.get("lte_phycellid").toString();

													if (lte_longitude2 != null && lte_latitude2 != null) {
														String convertDistanceToLogLat = GeoLngLatUtil
																.ConvertDistanceToLogLat(
																		Double.valueOf(lte_longitude2.toString()),
																		Double.valueOf(lte_latitude2.toString()),
																		MINT_UNIT,
																		Double.valueOf(lte_azimuth.toString()));
														if (convertDistanceToLogLat != null) {
															String[] split = convertDistanceToLogLat.split(",");
															if (split != null && split.length == 2) {
																String key = split[0] + "_" + split[1] + "_" + pciModel
																		+ "_" + pk;
																if (secondFinal.containsKey(key)) {
																	List<String> list = secondFinal.get(key);
																	if (!list.contains(
																			longitudeMain + "_" + latitudeMain)) {
																		list.add(longitudeMain + "_" + latitudeMain);
																	}
																	secondFinal.put(key, list);
																} else {
																	List<String> datasmm = new ArrayList<>();
																	datasmm.add(longitudeMain + "_" + latitudeMain);
																	secondFinal.put(key, datasmm);
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			} else if (("1").equals(gcType)) {// 5g的全网拉线
				queryParam.setRevelFields(Arrays.asList("logversion", "rootSupport", "latitude", "longitude",
						"nrDataInfoBean", "proNrDataInfoBean"));
				List<Map<String, Object>> mainDtas = Esutil.scrollQuery(queryParam);
				if (mainDtas != null && mainDtas.size() > 0) {
					// 查询出root非root信息
					Map<String, Object> map2 = mainDtas.get(0);
					Object logversion = map2.get("logversion");
					Object rootSupport = map2.get("rootSupport");
					if (rootSupport != null && logversion != null) {
						String rootSupportStr = rootSupport.toString();
						String logversionStr = logversion.toString();
						if (("1").equals(rootSupportStr)) {// root
							if (("1").equals(logversionStr)) {
								// 循环处理每个一点的
								for (Map<String, Object> map : mainDtas) {
									Object longitudeMain = map.get("longitude");
									Object latitudeMain = map.get("latitude");

									FiveLogMain fiveLogMain = JSONObject.parseObject(JSONObject.toJSONString(map),
											FiveLogMain.class);
									if (fiveLogMain != null) {
										ProNrDataInfoBean proLteDataInfoBeans = fiveLogMain.getProNrDataInfoBean();
										if (proLteDataInfoBeans != null) {
											String earfn = proLteDataInfoBeans.getSsARFCN();
											String pci = proLteDataInfoBeans.getPCI();
											if (StringUtils.isNotBlank(earfn) && StringUtils.isNotBlank(pci)) {
												QueryParamData queryParamDataCell = new QueryParamData("simgc5g",
														"simgc5g");
												queryParamDataCell.setLimit(1);
												Map<String, Object> xiaoquTermMap = new HashMap<>();
												xiaoquTermMap.put("lte_earfcn.keyword", earfn);
												xiaoquTermMap.put("lte_phycellid.keyword", pci);

												queryParamDataCell.setTermMap(xiaoquTermMap);
												List<Map<String, Object>> gcs4g = Esutil.queryList(queryParamDataCell);
												if (gcs4g != null && gcs4g.size() > 0) {

													Map<String, Object> map3 = gcs4g.get(0);
													Object lte_longitude2 = map3.get("lte_longitude2");
													Object lte_latitude2 = map3.get("lte_latitude2");
													Object lte_azimuth = map3.get("lte_azimuth");
													String pk = map3.get("pk") == null
															? UUID.randomUUID().toString().replace("-", "")
															: map3.get("pk").toString();
													pk = pk.replace("_", "~");
													String pciModel = map3.get("lte_phycellid") == null ? "0"
															: map3.get("lte_phycellid").toString();

													if (lte_longitude2 != null && lte_latitude2 != null) {
														String convertDistanceToLogLat = GeoLngLatUtil
																.ConvertDistanceToLogLat(
																		Double.valueOf(lte_longitude2.toString()),
																		Double.valueOf(lte_latitude2.toString()),
																		MINT_UNIT,
																		Double.valueOf(lte_azimuth.toString()));
														if (convertDistanceToLogLat != null) {
															String[] split = convertDistanceToLogLat.split(",");
															if (split != null && split.length == 2) {
																String key = split[0] + "_" + split[1] + "_" + pciModel
																		+ "_" + pk;
																if (secondFinal.containsKey(key)) {
																	List<String> list = secondFinal.get(key);
																	if (!list.contains(
																			longitudeMain + "_" + latitudeMain)) {
																		list.add(longitudeMain + "_" + latitudeMain);
																	}
																	secondFinal.put(key, list);
																} else {
																	List<String> datasmm = new ArrayList<>();
																	datasmm.add(longitudeMain + "_" + latitudeMain);
																	secondFinal.put(key, datasmm);
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						} else {// 非root
							if (("1").equals(logversionStr)) {
								// 循环处理每个一点的
								for (Map<String, Object> map : mainDtas) {

									Object longitudeMain = map.get("longitude");
									Object latitudeMain = map.get("latitude");

									FiveLogMain fiveLogMain = JSONObject.parseObject(JSONObject.toJSONString(map),
											FiveLogMain.class);
									if (fiveLogMain != null) {
										NrDataInfoBean proLteDataInfoBeans = fiveLogMain.getNrDataInfoBean();
										if (proLteDataInfoBeans != null) {
											String arfcn = proLteDataInfoBeans.getNrARFCN();
											String pci = proLteDataInfoBeans.getNrPCI();
											if (StringUtils.isNotBlank(arfcn) && StringUtils.isNotBlank(pci)) {
												QueryParamData queryParamDataCell = new QueryParamData("simgc5g",
														"simgc5g");
												queryParamDataCell.setLimit(1);
												Map<String, Object> xiaoquTermMap = new HashMap<>();
												xiaoquTermMap.put("lte_earfcn.keyword", arfcn);
												xiaoquTermMap.put("lte_phycellid.keyword", pci);

												queryParamDataCell.setTermMap(xiaoquTermMap);
												List<Map<String, Object>> gcs4g = Esutil.queryList(queryParamDataCell);
												if (gcs4g != null && gcs4g.size() > 0) {

													Map<String, Object> map3 = gcs4g.get(0);
													Object lte_longitude2 = map3.get("lte_longitude2");
													Object lte_latitude2 = map3.get("lte_latitude2");
													Object lte_azimuth = map3.get("lte_azimuth");
													String pk = map3.get("pk") == null
															? UUID.randomUUID().toString().replace("-", "")
															: map3.get("pk").toString();
													pk = pk.replace("_", "~");
													String pciModel = map3.get("lte_phycellid") == null ? "0"
															: map3.get("lte_phycellid").toString();

													if (lte_longitude2 != null && lte_latitude2 != null) {
														String convertDistanceToLogLat = GeoLngLatUtil
																.ConvertDistanceToLogLat(
																		Double.valueOf(lte_longitude2.toString()),
																		Double.valueOf(lte_latitude2.toString()),
																		MINT_UNIT,
																		Double.valueOf(lte_azimuth.toString()));
														if (convertDistanceToLogLat != null) {
															String[] split = convertDistanceToLogLat.split(",");
															if (split != null && split.length == 2) {
																String key = split[0] + "_" + split[1] + "_" + pciModel
																		+ "_" + pk;
																if (secondFinal.containsKey(key)) {
																	List<String> list = secondFinal.get(key);
																	if (!list.contains(
																			longitudeMain + "_" + latitudeMain)) {
																		list.add(longitudeMain + "_" + latitudeMain);
																	}
																	secondFinal.put(key, list);
																} else {
																	List<String> datasmm = new ArrayList<>();
																	datasmm.add(longitudeMain + "_" + latitudeMain);
																	secondFinal.put(key, datasmm);
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				/////
			}

			// 最终处理拉线的问题
			if (secondFinal != null && secondFinal.size() > 0) {
				Set<Entry<String, List<String>>> entrySet = secondFinal.entrySet();
				for (Entry<String, List<String>> entry : entrySet) {
					Map<String, Object> datasmap = new HashMap<>();

					String key = entry.getKey();
					datasmap.put("lat", key.split("_")[1]);
					datasmap.put("lng", key.split("_")[0]);
					datasmap.put("ci", key.split("_")[3]);
					datasmap.put("pci", key.split("_")[2]);
					List<Map<String, Object>> pointsFinal = new ArrayList<>();

					List<String> value = entry.getValue();
					for (String lnglat : value) {
						Map<String, Object> paramMapL = new HashMap<>();
						paramMapL.put("lng", lnglat.split("_")[0]);
						paramMapL.put("lat", lnglat.split("_")[1]);
						pointsFinal.add(paramMapL);
					}
					datasmap.put("points", pointsFinal);
					resultFinal.add(datasmap);
				}
			}
		}
		return resultFinal;
	}

	/**
	 * 查询主服务小区的问题 只查询5公里距离最近的一个 可 兼容4G\5G工参
	 * 
	 * @Description: 主服务小区的查询方式是 5公里内最近的 频点 pci分别对应相等
	 * @author weichengz
	 * @date 2019年11月22日 下午2:37:43
	 */

	@ResponseBody
	@PostMapping(value = "/queryMainService")
	public List<Map<String, Object>> queryMainService(@RequestParam("id") String id,
			@RequestParam("gcType") String gcType) {

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

		// log中每个主题内容的id
		if (StringUtils.isNotBlank(id)) {
			// 查询这条id对应的主log信息
			QueryParamData queryParamDataParam = new QueryParamData("logmain5g", "logmain5g");
			queryParamDataParam.setTermMap(MyUtil.createHashMap("id~" + id));
			queryParamDataParam.setLimit(1);
			List<Map<String, Object>> queryList = Esutil.queryList(queryParamDataParam);
			if (queryList != null && queryList.size() > 0) {
				Map<String, Object> mapRes = queryList.get(0);
				FiveLogMain fiveLogMain = JSONObject.parseObject(JSONObject.toJSONString(mapRes), FiveLogMain.class);
				if (fiveLogMain != null) {
					String longitude = fiveLogMain.getLongitude();
					String latitude = fiveLogMain.getLatitude();// 经纬度
					// String logversion = fiveLogMain.getLogversion();// 0: LTE 1: NR5G NSA
					String rootSupport = fiveLogMain.getRootSupport();// 0: None Root1: Root
					if (StringUtils.isNotBlank(longitude) && StringUtils.isNotBlank(latitude)) {
						QueryParamData queryParamDataFinal = new QueryParamData();
						GeoPoint ceterPoint = new GeoPoint(Double.valueOf(latitude), Double.valueOf(longitude));
						queryParamDataFinal.dealGeoDiatanceBuss(ceterPoint, GEO_DISTANCE_5G, "location");

						// 根据条件寻找PCI
						if (("1").equals(rootSupport)) {// root版本
							if (("0").equals(gcType)) {// 4G
								// 赋值索引类型
								QueryParamData queryParamDataGc = new QueryParamData("simgc", "simgc");
								// 地理位置的信息距离
								queryParamDataGc.setDiatinceGeoMap(queryParamDataFinal.getDiatinceGeoMap());
								queryParamDataGc.setSortGeoMap(queryParamDataFinal.getSortGeoMap());
								;

								ProLteDataInfoBean proLteDataInfoBeans = fiveLogMain.getProLteDataInfoBean();
								if (proLteDataInfoBeans != null) {
									String ci = proLteDataInfoBeans.getServingCellPccCellId();
									String enb = proLteDataInfoBeans.getServingCellPccEnodebId();

									List<Map<String, Object>> resultDataMap = new ArrayList<>();// 返回的结果

									if (StringUtils.isNotBlank(ci) && StringUtils.isNotBlank(enb)) {

										// 处理该地市的查询条件
										Map<String, Object> termMap = new HashMap<>();

										termMap.put("lte_enodebid.keyword", enb);
										termMap.put("lte_cell.keyword", ci);
										termMap.put("lte_city_name.keyword", sysProject.getProjCode() + "");
										queryParamDataGc.setTermMap(termMap);
										List<Map<String, Object>> queryList2 = Esutil.queryList(queryParamDataGc);
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
												}
											}
										}
									}
									return resultDataMap;
								}
							} else {// 5g
								// 赋值索引类型
								QueryParamData queryParamDataGc = new QueryParamData("simgc5g", "simgc5g");
								// 地理位置的信息距离
								queryParamDataGc.setDiatinceGeoMap(queryParamDataFinal.getDiatinceGeoMap());
								queryParamDataGc.setSortGeoMap(queryParamDataFinal.getSortGeoMap());

								ProNrDataInfoBean proNrDataInfoBean = fiveLogMain.getProNrDataInfoBean();
								if (proNrDataInfoBean != null) {
									String pci = proNrDataInfoBean.getPCI();
									String ssARFCN = proNrDataInfoBean.getSsARFCN();

									List<Map<String, Object>> resultDataMap = new ArrayList<>();// 返回的结果

									if (StringUtils.isNotBlank(pci) && StringUtils.isNotBlank(ssARFCN)) {

										// 处理该地市的查询条件
										Map<String, Object> termMap = new HashMap<>();

										termMap.put("lte_earfcn.keyword", ssARFCN);
										termMap.put("lte_phycellid.keyword", pci);
										termMap.put("lte_city_name.keyword", sysProject.getProjCode() + "");
										queryParamDataGc.setTermMap(termMap);
										List<Map<String, Object>> queryList2 = Esutil.queryList(queryParamDataGc);
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
												}
											}
										}
									} //
									return resultDataMap;
								}
							}
						} else {// 非root
							if (("0").equals(gcType)) {// 4G

								// 赋值索引类型
								QueryParamData queryParamDataGc = new QueryParamData("simgc", "simgc");
								// 地理位置的信息距离
								queryParamDataGc.setDiatinceGeoMap(queryParamDataFinal.getDiatinceGeoMap());
								queryParamDataGc.setSortGeoMap(queryParamDataFinal.getSortGeoMap());

								LteDataInfoBean lteDataInfoBean = fiveLogMain.getLteDataInfoBean();
								if (lteDataInfoBean != null) {
									String ci = lteDataInfoBean.getLteCellID();
									String enb = lteDataInfoBean.getLteENB();

									List<Map<String, Object>> resultDataMap = new ArrayList<>();// 返回的结果

									if (StringUtils.isNotBlank(ci) && StringUtils.isNotBlank(enb)) {
										// 处理该地市的查询条件
										Map<String, Object> termMap = new HashMap<>();

										termMap.put("lte_enodebid.keyword", enb);
										termMap.put("lte_cell.keyword", ci);
										termMap.put("lte_city_name.keyword", sysProject.getProjCode() + "");
										queryParamDataGc.setTermMap(termMap);
										List<Map<String, Object>> queryList2 = Esutil.queryList(queryParamDataGc);
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
												}
											}
										}
									}
									return resultDataMap;
								}
							} else {// 5g
								// 赋值索引类型
								QueryParamData queryParamDataGc = new QueryParamData("simgc5g", "simgc5g");
								// 地理位置的信息距离
								queryParamDataGc.setDiatinceGeoMap(queryParamDataFinal.getDiatinceGeoMap());
								queryParamDataGc.setSortGeoMap(queryParamDataFinal.getSortGeoMap());

								NrDataInfoBean nrDataInfoBean = fiveLogMain.getNrDataInfoBean();
								if (nrDataInfoBean != null) {
									String nrARFCN = nrDataInfoBean.getNrARFCN();
									String nrPCI = nrDataInfoBean.getNrPCI();

									List<Map<String, Object>> resultDataMap = new ArrayList<>();// 返回的结果

									if (StringUtils.isNotBlank(nrARFCN) && StringUtils.isNotBlank(nrPCI)) {

										// 处理该地市的查询条件
										Map<String, Object> termMap = new HashMap<>();
										termMap.put("lte_earfcn.keyword", nrARFCN);
										termMap.put("lte_phycellid.keyword", nrPCI);
										termMap.put("lte_city_name.keyword", sysProject.getProjCode() + "");
										queryParamDataGc.setTermMap(termMap);
										List<Map<String, Object>> queryList2 = Esutil.queryList(queryParamDataGc);
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
												}
											}
										}

									} //
									return resultDataMap;
								}
							}
						}
					}
				}
			}
		}
		return new ArrayList<>();
	}

	/**
	 * 
	 * @Description: 查询小区信息接口
	 * @author weichengz
	 * @date 2020年5月8日 上午10:11:43
	 */
	@ResponseBody
	@PostMapping(value = "/cellWindowInfo")
	public Map<String, Object> cellWindowInfo(@RequestParam("id") String id) {
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

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("5G", new HashMap<>());
		paramMap.put("4G", new HashMap<>());

		// log中每个主题内容的id
		if (StringUtils.isNotBlank(id)) {
			// 查询这条id对应的主log信息
			QueryParamData queryParamDataParam = new QueryParamData("logmain5g", "logmain5g");
			queryParamDataParam.setTermMap(MyUtil.createHashMap("id~" + id));
			queryParamDataParam.setLimit(1);
			List<Map<String, Object>> queryList = Esutil.queryList(queryParamDataParam);
			if (queryList != null && queryList.size() > 0) {
				Map<String, Object> mapRes = queryList.get(0);
				FiveLogMain fiveLogMain = JSONObject.parseObject(JSONObject.toJSONString(mapRes), FiveLogMain.class);
				if (fiveLogMain != null) {
					String rootSupport = fiveLogMain.getRootSupport();// 0: None Root1: Root
					LinkedHashMap<Object, Object> fiveGhashMap = new LinkedHashMap<>();
					LinkedHashMap<Object, Object> fourGhashMap = new LinkedHashMap<>();
					// 用于获取小区的信息

					NrDataInfoBean nrDataInfoBean = fiveLogMain.getNrDataInfoBean();
					LteDataInfoBean lteDataInfoBean = fiveLogMain.getLteDataInfoBean();

					if (("1").equals(rootSupport)) {// root版本 显示5G/4G窗口的信息

						// 4G
						ProLteDataInfoBean proLteDataInfoBeans = fiveLogMain.getProLteDataInfoBean();
						if (proLteDataInfoBeans != null) {
							// 4G
							fourGhashMap.put("Cell Name", "");
							if (lteDataInfoBean != null) {
								fourGhashMap.put("Cell Name", lteDataInfoBean.getLteCellName());
							}
							fourGhashMap.put("Band", proLteDataInfoBeans.getServingCellPccBandIndex());
							fourGhashMap.put("BandWidth(MHz)", proLteDataInfoBeans.getServingCellPccBwDl());
							fourGhashMap.put("Fequency DL", proLteDataInfoBeans.getServingCellPccFreqDl());
							fourGhashMap.put("EARFCN", proLteDataInfoBeans.getServingCellPccEarfcnDl());
							fourGhashMap.put("TAC", proLteDataInfoBeans.getServingCellPccTac());
							fourGhashMap.put("ECI", proLteDataInfoBeans.getServingCellPccSiteEci());
							fourGhashMap.put("eNodeB ID", proLteDataInfoBeans.getServingCellPccEnodebId());
							fourGhashMap.put("CI", proLteDataInfoBeans.getServingCellPccCellId());
							fourGhashMap.put("PCI", proLteDataInfoBeans.getServingCellPccCellId());
							fourGhashMap.put("RSRP(dBm)", proLteDataInfoBeans.getServingCellPccRsrp());
							fourGhashMap.put("SINR(dB)", proLteDataInfoBeans.getServingCellPccSinr());
							fourGhashMap.put("RSRQ(dB)", proLteDataInfoBeans.getServingCellPccRsrq());
							fourGhashMap.put("RSSI(dBm)", proLteDataInfoBeans.getServingCellPccRssi());
							fourGhashMap.put("SA/SSP", proLteDataInfoBeans.getServingCellPccSa());

							fourGhashMap.put("CQI", proLteDataInfoBeans.getServingCellPccWidebandCqi());
							fourGhashMap.put("RI", proLteDataInfoBeans.getServingCellPccBandIndex());
							fourGhashMap.put("PUSCH TxPower", proLteDataInfoBeans.getServingCellPccPuschTxpower());
							fourGhashMap.put("PUCCH TxPower", proLteDataInfoBeans.getServingCellPccPucchTxpower());
							fourGhashMap.put("Grant UL/s", proLteDataInfoBeans.getServingCellPccGrantulnum());
							fourGhashMap.put("Grant DL/s", proLteDataInfoBeans.getServingCellPccGrantdlnum());
							fourGhashMap.put("PUSCH BLER(%)", proLteDataInfoBeans.getServingCellPccULBLER());
							fourGhashMap.put("PDSCH BLER(%)", proLteDataInfoBeans.getServingCellPccDLBLER());
							fourGhashMap.put("PUSCH RB/s", proLteDataInfoBeans.getServingCellPccPdschRbs());
							fourGhashMap.put("PDSCH RB/s", proLteDataInfoBeans.getServingCellPccPdschRbs());
							fourGhashMap.put("MCS UL", proLteDataInfoBeans.getServingCellPccMcsul());
							fourGhashMap.put("MCS DL", proLteDataInfoBeans.getServingCellPccMcsdl());
							fourGhashMap.put("Mod UL", proLteDataInfoBeans.getServingCellPccModul());
							fourGhashMap.put("Mod DL", proLteDataInfoBeans.getServingCellPccModdl());

							paramMap.put("4G", fourGhashMap);
						}

						// 5g
						ProNrDataInfoBean proNrDataInfoBean = fiveLogMain.getProNrDataInfoBean();
						if (proNrDataInfoBean != null) {
							// 5G
							fiveGhashMap.put("Cell Name", "");
							if (nrDataInfoBean != null) {
								fiveGhashMap.put("Cell Name", nrDataInfoBean.getNrCellName());
							}
							fiveGhashMap.put("Band", proNrDataInfoBean.getBand());
							fiveGhashMap.put("Band Width(MHz)", proNrDataInfoBean.getBandWidth());
							fiveGhashMap.put("Frequency PointA", proNrDataInfoBean.getFrequencyPointA());
							fiveGhashMap.put("Frequency DL", proNrDataInfoBean.getFrequencyDL());
							fiveGhashMap.put("GSCN", proNrDataInfoBean.getGSCN());
							fiveGhashMap.put("SC Space(kHz)", proNrDataInfoBean.getSubCarrierSpace());
							fiveGhashMap.put("SS ARFCN", proNrDataInfoBean.getSsARFCN());
							fiveGhashMap.put("PCI", proNrDataInfoBean.getPCI());
							fiveGhashMap.put("SSB Index", proNrDataInfoBean.getSSBIndex());
							fiveGhashMap.put("SS RSRP(dBm)", proNrDataInfoBean.getSsRSRP());
							fiveGhashMap.put("SS SINR(dB)", proNrDataInfoBean.getSsSINR());
							fiveGhashMap.put("SS RSRQ(dB)", proNrDataInfoBean.getSsRSRQ());
							fiveGhashMap.put("CQI", proNrDataInfoBean.getCqi());
							fiveGhashMap.put("Slot Config(DL/UL)", proNrDataInfoBean.getSlotConfigDLUL());
							fiveGhashMap.put("RI", proNrDataInfoBean.getRiNumDL());
							fiveGhashMap.put("SRS TxPower", proNrDataInfoBean.getSrsTxPower());
							fiveGhashMap.put("PUSCH TxPower", proNrDataInfoBean.getPuschTxPower());
							fiveGhashMap.put("PUCCH TxPower", proNrDataInfoBean.getPucchTxPower());
							fiveGhashMap.put("Grant UL/s", proNrDataInfoBean.getGrantULNum());
							fiveGhashMap.put("Grant DL/s", proNrDataInfoBean.getGrantDLNum());
							fiveGhashMap.put("PUSCH BLER(%)", proNrDataInfoBean.getPuschBler());
							fiveGhashMap.put("PDSCH BLER(%)", proNrDataInfoBean.getPdschBler());
							fiveGhashMap.put("PUSCH RB/s", proNrDataInfoBean.getPuschRB());
							fiveGhashMap.put("PDSCH RB/s", proNrDataInfoBean.getPdschRB());
							fiveGhashMap.put("MCS UL", proNrDataInfoBean.getMcsUL());
							fiveGhashMap.put("MCS DL", proNrDataInfoBean.getMcsDL());
							fiveGhashMap.put("Mod UL", proNrDataInfoBean.getModUL());
							fiveGhashMap.put("Mod DL", proNrDataInfoBean.getModDL());

							paramMap.put("5G", fiveGhashMap);
						}

					} else {// 非root版本 显示5G/4G窗口的信息

						// 4G
						LteDataInfoBean proLteDataInfoBeans = fiveLogMain.getLteDataInfoBean();
						if (proLteDataInfoBeans != null) {
							// 4G
							fourGhashMap.put("Cell Name", "");
							if (lteDataInfoBean != null) {
								fourGhashMap.put("Cell Name", lteDataInfoBean.getLteCellName());
							}
							fourGhashMap.put("Band", "");
							fourGhashMap.put("BandWidth(MHz)", "");
							fourGhashMap.put("Fequency DL", "");
							fourGhashMap.put("EARFCN", proLteDataInfoBeans.getLteEARFCN());
							fourGhashMap.put("TAC", proLteDataInfoBeans.getLteEARFCN());
							fourGhashMap.put("ECI", "");
							fourGhashMap.put("eNodeB ID", proLteDataInfoBeans.getLteENB());
							fourGhashMap.put("CI", proLteDataInfoBeans.getLteCellID());
							fourGhashMap.put("PCI", proLteDataInfoBeans.getLtePCI());
							fourGhashMap.put("RSRP(dBm)", proLteDataInfoBeans.getLteRSRP());
							fourGhashMap.put("SINR(dB)", proLteDataInfoBeans.getLteSINR());
							fourGhashMap.put("RSRQ(dB)", proLteDataInfoBeans.getLteRSRQ());
							fourGhashMap.put("RSSI(dBm)", proLteDataInfoBeans.getLteRSSI());
							fourGhashMap.put("SA/SSP", "");

							fourGhashMap.put("CQI", "");
							fourGhashMap.put("RI", "");
							fourGhashMap.put("PUSCH TxPower", "");
							fourGhashMap.put("PUCCH TxPower", "");
							fourGhashMap.put("Grant UL/s", "");
							fourGhashMap.put("Grant DL/s", "");
							fourGhashMap.put("PUSCH BLER(%)", "");
							fourGhashMap.put("PDSCH BLER(%)", "");
							fourGhashMap.put("PUSCH RB/s", "");
							fourGhashMap.put("PDSCH RB/s", "");
							fourGhashMap.put("MCS UL", "");
							fourGhashMap.put("MCS DL", "");
							fourGhashMap.put("Mod UL", "");
							fourGhashMap.put("Mod DL", "");

							paramMap.put("4G", fourGhashMap);

						}

						// 5g
						NrDataInfoBean proNrDataInfoBean = fiveLogMain.getNrDataInfoBean();
						if (proNrDataInfoBean != null) {
							// 5G
							fiveGhashMap.put("Cell Name", "");
							if (nrDataInfoBean != null) {
								fiveGhashMap.put("Cell Name", nrDataInfoBean.getNrCellName());
							}
							fiveGhashMap.put("Band", "");
							fiveGhashMap.put("Band Width(MHz)", "");
							fiveGhashMap.put("Frequency PointA", "");
							fiveGhashMap.put("Frequency DL", "");
							fiveGhashMap.put("GSCN", "");
							fiveGhashMap.put("SC Space(kHz)", "");
							fiveGhashMap.put("SS ARFCN", proNrDataInfoBean.getNrARFCN());
							fiveGhashMap.put("PCI", proNrDataInfoBean.getNrPCI());
							fiveGhashMap.put("SSB Index", "");
							fiveGhashMap.put("SS RSRP(dBm)", proNrDataInfoBean.getSsRSRP());
							fiveGhashMap.put("SS SINR(dB)", proNrDataInfoBean.getSsSINR());
							fiveGhashMap.put("SS RSRQ(dB)", proNrDataInfoBean.getSsRSRQ());
							fiveGhashMap.put("CQI", "");
							fiveGhashMap.put("Slot Config(DL/UL)", "");
							fiveGhashMap.put("RI", "");
							fiveGhashMap.put("SRS TxPower", "");
							fiveGhashMap.put("PUSCH TxPower", "");
							fiveGhashMap.put("PUCCH TxPower", "");
							fiveGhashMap.put("Grant UL/s", "");
							fiveGhashMap.put("Grant DL/s", "");
							fiveGhashMap.put("PUSCH BLER(%)", "");
							fiveGhashMap.put("PDSCH BLER(%)", "");
							fiveGhashMap.put("PUSCH RB/s", "");
							fiveGhashMap.put("PDSCH RB/s", "");
							fiveGhashMap.put("MCS UL", "");
							fiveGhashMap.put("MCS DL", "");
							fiveGhashMap.put("Mod UL", "");
							fiveGhashMap.put("Mod DL", "");

							paramMap.put("5G", fiveGhashMap);
						}

					}
				}
			}
		}

		return paramMap;
	}

	/**
	 * 
	 * @Description: 查询左下角邻区和主服务小区接口 入参是主log的主键ID
	 * @author weichengz
	 * @date 2020年5月8日 上午10:11:43
	 */
	@ResponseBody
	@PostMapping(value = "/queryNearOrMainService")
	public Map<String, Object> queryNearOrMainService(@RequestParam("id") String id) {

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("4G", new ArrayList<>());
		paramMap.put("5G", new ArrayList<>());

		// log中每个主题内容的id
		if (StringUtils.isNotBlank(id)) {
			// 查询这条id对应的主log信息
			QueryParamData queryParamDataParam = new QueryParamData("logmain5g", "logmain5g");
			queryParamDataParam.setTermMap(MyUtil.createHashMap("id~" + id));
			queryParamDataParam.setLimit(1);
			List<Map<String, Object>> queryList = Esutil.queryList(queryParamDataParam);
			if (queryList != null && queryList.size() > 0) {
				Map<String, Object> mapRes = queryList.get(0);
				FiveLogMain fiveLogMain = JSONObject.parseObject(JSONObject.toJSONString(mapRes), FiveLogMain.class);
				if (fiveLogMain != null) {
					String rootSupport = fiveLogMain.getRootSupport();// 0: None Root1: Root
					// 用于获取小区的信息
					NrDataInfoBean nrDataInfoBean = fiveLogMain.getNrDataInfoBean();
					LteDataInfoBean lteDataInfoBean = fiveLogMain.getLteDataInfoBean();
					if (("1").equals(rootSupport)) {// root版本 显示5G/4G窗口的信息
						// 4G
						ProLteDataInfoBean proLteDataInfoBeans = fiveLogMain.getProLteDataInfoBean();
						if (proLteDataInfoBeans != null) {
							List<Map<String, Object>> datas = new ArrayList<>();

							// PCLL
							Map<String, Object> pcellMap = new HashMap<>();
							pcellMap.put("Type", "Pcell");
							// 4G
							pcellMap.put("CellName", "");
							if (lteDataInfoBean != null) {
								pcellMap.put("CellName", lteDataInfoBean.getLteCellName());
							}
							pcellMap.put("EARFCN", proLteDataInfoBeans.getServingCellPccEarfcnDl());
							pcellMap.put("PCI", proLteDataInfoBeans.getServingCellPccPci());
							pcellMap.put("RSRP", proLteDataInfoBeans.getServingCellPccRsrp());
							pcellMap.put("RSRQ", proLteDataInfoBeans.getServingCellPccRsrq());
							pcellMap.put("SINR", proLteDataInfoBeans.getServingCellPccSinr());
							pcellMap.put("RSSI", proLteDataInfoBeans.getServingCellPccRssi());
							datas.add(pcellMap);

							List<ProLteNeighborhoodInfo> proLteNeighborhoodInfos = proLteDataInfoBeans
									.getProLteNeighborhoodInfos();
							if (proLteNeighborhoodInfos != null && proLteNeighborhoodInfos.size() > 0) {
								for (ProLteNeighborhoodInfo proLteNeighborhoodInfo : proLteNeighborhoodInfos) {
									Map<String, Object> lteMap = new HashMap<>();

									lteMap.put("Type", "Ncell");
									// 4G
									lteMap.put("CellName", "");
									lteMap.put("EARFCN", proLteNeighborhoodInfo.getpLteNeighbirhoodEARFCN());
									lteMap.put("PCI", proLteNeighborhoodInfo.getpLteNeighbirhoodPCI());
									lteMap.put("RSRP", proLteNeighborhoodInfo.getpLteNeighbirhoodRSRP());
									lteMap.put("RSRQ", proLteNeighborhoodInfo.getpLteNeighbirhoodRSRQ());
									lteMap.put("SINR", "");
									lteMap.put("RSSI", proLteNeighborhoodInfo.getpLteNeighbirhoodRSSI());
									datas.add(lteMap);
								}
							}
							paramMap.put("4G", datas);
						}

						// 5g
						ProNrDataInfoBean proNrDataInfoBean = fiveLogMain.getProNrDataInfoBean();
						if (proNrDataInfoBean != null) {
							List<Map<String, Object>> datas = new ArrayList<>();
							Map<String, Object> pcellMap = new HashMap<>();

							// 5G
							pcellMap.put("Type", "Pcell");
							pcellMap.put("CellName", "");
							if (nrDataInfoBean != null) {
								pcellMap.put("CellName", nrDataInfoBean.getNrCellName());
							}
							pcellMap.put("SSARFCN", proNrDataInfoBean.getSsARFCN());
							pcellMap.put("PCI", proNrDataInfoBean.getPCI());
							pcellMap.put("Beam", proNrDataInfoBean.getSSBIndex());
							pcellMap.put("SSRSRP", proNrDataInfoBean.getSsRSRP());
							pcellMap.put("SSRSRQ", proNrDataInfoBean.getSsRSRQ());
							pcellMap.put("SSSINR", proNrDataInfoBean.getSsSINR());
							datas.add(pcellMap);

							List<ProNrNeighborhoodInfo> proNrNeighborhoodInfos = proNrDataInfoBean
									.getProNrNeighborhoodInfos();
							if (proNrNeighborhoodInfos != null && proNrNeighborhoodInfos.size() > 0) {
								for (ProNrNeighborhoodInfo proNrNeighborhoodInfo : proNrNeighborhoodInfos) {
									Map<String, Object> nellMap = new HashMap<>();
									// 5G
									nellMap.put("Type", "Ncell");
									nellMap.put("CellName", "");
									nellMap.put("SSARFCN", proNrNeighborhoodInfo.getpNrNeighborhoodNRARFCN());
									nellMap.put("PCI", proNrNeighborhoodInfo.getpNrNeighborhoodPCI());
									nellMap.put("Beam", proNrNeighborhoodInfo.getpNrNeighborhoodBeam());
									nellMap.put("SSRSRP", proNrNeighborhoodInfo.getpNrNeighborhoodSSRSRP());
									nellMap.put("SSRSRQ", proNrNeighborhoodInfo.getpNrNeighborhoodSSRSRQ());
									nellMap.put("SSSINR", proNrNeighborhoodInfo.getpNrNeighborhoodSSSINR());
									datas.add(nellMap);
								}
							}
							paramMap.put("5G", datas);
						}

					} else {// 非root版本 显示5G/4G窗口的信息

						// 5G
						NrDataInfoBean proLteDataInfoBeans = fiveLogMain.getNrDataInfoBean();
						if (proLteDataInfoBeans != null) {
							List<Map<String, Object>> datas = new ArrayList<>();

							// PCLL
							Map<String, Object> pcellMap = new HashMap<>();
							pcellMap.put("Type", "Pcell");
							// 5G
							pcellMap.put("CellName", "");
							if (lteDataInfoBean != null) {
								pcellMap.put("CellName", proLteDataInfoBeans.getNrCellName());
							}
							pcellMap.put("SSEARFCN", proLteDataInfoBeans.getNrARFCN());
							pcellMap.put("PCI", proLteDataInfoBeans.getNrPCI());
							pcellMap.put("Beam", "");
							pcellMap.put("SSRSRP", proLteDataInfoBeans.getSsRSRP());
							pcellMap.put("SSRSRQ", proLteDataInfoBeans.getSsRSRQ());
							pcellMap.put("SSSINR", proLteDataInfoBeans.getSsSINR());
							datas.add(pcellMap);

							List<NrNeighborhoodInfo> proLteNeighborhoodInfos = proLteDataInfoBeans
									.getNrNeighborhoodInfos();
							if (proLteNeighborhoodInfos != null && proLteNeighborhoodInfos.size() > 0) {
								for (NrNeighborhoodInfo proLteNeighborhoodInfo : proLteNeighborhoodInfos) {
									Map<String, Object> lteMap = new HashMap<>();

									lteMap.put("Type", "Ncell");
									// 5G
									lteMap.put("CellName", "");
									lteMap.put("SSEARFCN", proLteNeighborhoodInfo.getNrNeighborhoodNRARFCN());
									lteMap.put("PCI", proLteNeighborhoodInfo.getNrNeighborhoodPCI());
									lteMap.put("Beam", "");
									lteMap.put("SSRSRP", proLteNeighborhoodInfo.getNrNeighborhoodSSRSRP());
									lteMap.put("SSRSRQ", proLteNeighborhoodInfo.getNrNeighborhoodSSRSRQ());
									lteMap.put("SSSINR", proLteNeighborhoodInfo.getNrNeighborhoodSSSINR());
									datas.add(lteMap);
								}
							}
							paramMap.put("5G", datas);
						}

						// 4g
						LteDataInfoBean proNrDataInfoBean = fiveLogMain.getLteDataInfoBean();
						if (proNrDataInfoBean != null) {
							List<Map<String, Object>> datas = new ArrayList<>();
							Map<String, Object> pcellMap = new HashMap<>();

							// 4G
							pcellMap.put("Type", "Pcell");
							pcellMap.put("CellName", "");
							if (nrDataInfoBean != null) {
								pcellMap.put("CellName", proNrDataInfoBean.getLteCellName());
							}
							pcellMap.put("EARFCN", proNrDataInfoBean.getLteEARFCN());
							pcellMap.put("PCI", proNrDataInfoBean.getLtePCI());
							pcellMap.put("RSRP", proNrDataInfoBean.getLteRSRP());
							pcellMap.put("RSRQ", proNrDataInfoBean.getLteRSRQ());
							pcellMap.put("SINR", proNrDataInfoBean.getLteSINR());
							pcellMap.put("RSSI", proNrDataInfoBean.getLteRSSI());
							datas.add(pcellMap);

							List<LteNeighborhoodInfo> proNrNeighborhoodInfos = proNrDataInfoBean
									.getLteNeighborhoodInfos();
							if (proNrNeighborhoodInfos != null && proNrNeighborhoodInfos.size() > 0) {
								for (LteNeighborhoodInfo proNrNeighborhoodInfo : proNrNeighborhoodInfos) {
									Map<String, Object> nellMap = new HashMap<>();
									// 4G
									nellMap.put("Type", "Ncell");
									nellMap.put("CellName", "");
									nellMap.put("EARFCN", proNrNeighborhoodInfo.getLteNeighbirhoodEARFCN());
									nellMap.put("PCI", proNrNeighborhoodInfo.getLteNeighbirhoodPCI());
									nellMap.put("RSRP", proNrNeighborhoodInfo.getLteNeighbirhoodRSRP());
									nellMap.put("RSRQ", proNrNeighborhoodInfo.getLteNeighbirhoodRSRQ());
									nellMap.put("SINR", "");
									nellMap.put("RSSI", proNrNeighborhoodInfo.getLteNeighbirhoodRSSI());
									datas.add(nellMap);
								}
							}
							paramMap.put("4G", datas);
						}
					}
				}
			}
		}
		return paramMap;
	}

	/**
	 * 
	 * @Description: 六个窗口的集合
	 * @author weichengz
	 * @date 2020年5月8日 上午10:11:43
	 */
	@ResponseBody
	@PostMapping(value = "/querySiXWindowService")
	public Map<String, Object> querySiXWindowService(@RequestParam("id") String id) {
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

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("cellInfo5g", new HashMap<>());// 5g小区信息
		paramMap.put("cellInfo4g", new HashMap<>());

		paramMap.put("nearOrMain5g", new ArrayList<>());// 5g邻区和主服务小区
		paramMap.put("nearOrMain4g", new ArrayList<>());

		paramMap.put("grate4g", new HashMap<>());// 速率问题
		paramMap.put("grate5g", new HashMap<>());

		// log中每个主题内容的id
		if (StringUtils.isNotBlank(id)) {
			// 查询这条id对应的主log信息
			QueryParamData queryParamDataParam = new QueryParamData("logmain5g", "logmain5g");
			queryParamDataParam.setTermMap(MyUtil.createHashMap("id~" + id));
			queryParamDataParam.setLimit(1);
			List<Map<String, Object>> queryList = Esutil.queryList(queryParamDataParam);
			if (queryList != null && queryList.size() > 0) {
				Map<String, Object> mapRes = queryList.get(0);
				FiveLogMain fiveLogMain = JSONObject.parseObject(JSONObject.toJSONString(mapRes), FiveLogMain.class);
				if (fiveLogMain != null) {

					/*** 速率录入 */

					// 4g
					LinkedHashMap<String, Object> paramMapUlDl = new LinkedHashMap<>();
					ProLteDataInfoBean proNrDataInfoBeanm = fiveLogMain.getProLteDataInfoBean();
					if (proNrDataInfoBeanm != null) {
						paramMapUlDl.put("APP", fiveLogMain.getDownLoadSpeed() + " ~ " + fiveLogMain.getUpLoadSpeed());
						paramMapUlDl.put("PDCP", proNrDataInfoBeanm.getThroughputPccPdcpDl() + " ~ "
								+ proNrDataInfoBeanm.getThroughputPccPdcpUl());
						paramMapUlDl.put("RLC", proNrDataInfoBeanm.getThroughputPccRlcDl() + " ~ "
								+ proNrDataInfoBeanm.getThroughputPccRlcUl());
						paramMapUlDl.put("MAC", proNrDataInfoBeanm.getThroughputPccMacDl() + " ~ "
								+ proNrDataInfoBeanm.getThroughputPccMacUl());
						paramMapUlDl.put("PHY", " ~ ");
					}
					paramMap.put("grate4g", paramMapUlDl);

					// 5g
					LinkedHashMap<String, Object> paramMapUlDlm = new LinkedHashMap<>();
					ProNrDataInfoBean proNrDataInfoBean2 = fiveLogMain.getProNrDataInfoBean();
					if (proNrDataInfoBean2 != null) {
						paramMapUlDlm.put("APP", fiveLogMain.getDownLoadSpeed() + " ~ " + fiveLogMain.getUpLoadSpeed());
						paramMapUlDlm.put("PDCP",
								proNrDataInfoBean2.getPdcpDLThr() + " ~ " + proNrDataInfoBean2.getPdcpULThr());
						paramMapUlDlm.put("RLC",
								proNrDataInfoBean2.getRlcDLThr() + " ~ " + proNrDataInfoBean2.getRlcULThr());
						paramMapUlDlm.put("MAC",
								proNrDataInfoBean2.getMacDLThr() + " ~ " + proNrDataInfoBean2.getMacULThr());
						paramMapUlDlm.put("PHY", " ~ ");
					}
					paramMap.put("grate5g", paramMapUlDlm);

					/*** 速率录入 */

					String rootSupport = fiveLogMain.getRootSupport();// 0: None Root1: Root
					LinkedHashMap<Object, Object> fiveGhashMap = new LinkedHashMap<>();
					LinkedHashMap<Object, Object> fourGhashMap = new LinkedHashMap<>();
					// 用于获取小区的信息
					NrDataInfoBean nrDataInfoBean = fiveLogMain.getNrDataInfoBean();
					LteDataInfoBean lteDataInfoBean = fiveLogMain.getLteDataInfoBean();

					if (("1").equals(rootSupport)) {// root版本 显示5G/4G窗口的信息
						// 4G
						ProLteDataInfoBean proLteDataInfoBeans = fiveLogMain.getProLteDataInfoBean();
						if (proLteDataInfoBeans != null) {
							List<Map<String, Object>> datas = new ArrayList<>();

							// PCLL
							Map<String, Object> pcellMap = new HashMap<>();
							pcellMap.put("Type", "Pcell");
							// 4G
							pcellMap.put("CellName", "");
							if (lteDataInfoBean != null) {
								pcellMap.put("CellName", lteDataInfoBean.getLteCellName());
							}
							pcellMap.put("EARFCN", proLteDataInfoBeans.getServingCellPccEarfcnDl());
							pcellMap.put("PCI", proLteDataInfoBeans.getServingCellPccPci());
							pcellMap.put("RSRP", proLteDataInfoBeans.getServingCellPccRsrp());
							pcellMap.put("RSRQ", proLteDataInfoBeans.getServingCellPccRsrq());
							pcellMap.put("SINR", proLteDataInfoBeans.getServingCellPccSinr());
							pcellMap.put("RSSI", proLteDataInfoBeans.getServingCellPccRssi());
							datas.add(pcellMap);

							List<ProLteNeighborhoodInfo> proLteNeighborhoodInfos = proLteDataInfoBeans
									.getProLteNeighborhoodInfos();
							if (proLteNeighborhoodInfos != null && proLteNeighborhoodInfos.size() > 0) {
								for (ProLteNeighborhoodInfo proLteNeighborhoodInfo : proLteNeighborhoodInfos) {
									Map<String, Object> lteMap = new HashMap<>();

									lteMap.put("Type", "Ncell");
									// 4G
									lteMap.put("CellName", "");
									lteMap.put("EARFCN", proLteNeighborhoodInfo.getpLteNeighbirhoodEARFCN());
									lteMap.put("PCI", proLteNeighborhoodInfo.getpLteNeighbirhoodPCI());
									lteMap.put("RSRP", proLteNeighborhoodInfo.getpLteNeighbirhoodRSRP());
									lteMap.put("RSRQ", proLteNeighborhoodInfo.getpLteNeighbirhoodRSRQ());
									lteMap.put("SINR", "");
									lteMap.put("RSSI", proLteNeighborhoodInfo.getpLteNeighbirhoodRSSI());
									datas.add(lteMap);
								}
							}
							paramMap.put("nearOrMain4g", datas);
						}

						// 5g
						ProNrDataInfoBean proNrDataInfoBean = fiveLogMain.getProNrDataInfoBean();
						if (proNrDataInfoBean != null) {
							List<Map<String, Object>> datas = new ArrayList<>();
							Map<String, Object> pcellMap = new HashMap<>();

							// 5G
							pcellMap.put("Type", "Pcell");
							pcellMap.put("CellName", "");
							if (nrDataInfoBean != null) {
								pcellMap.put("CellName", nrDataInfoBean.getNrCellName());
							}
							pcellMap.put("SSARFCN", proNrDataInfoBean.getSsARFCN());
							pcellMap.put("PCI", proNrDataInfoBean.getPCI());
							pcellMap.put("Beam", proNrDataInfoBean.getSSBIndex());
							pcellMap.put("SSRSRP", proNrDataInfoBean.getSsRSRP());
							pcellMap.put("SSRSRQ", proNrDataInfoBean.getSsRSRQ());
							pcellMap.put("SSSINR", proNrDataInfoBean.getSsSINR());
							datas.add(pcellMap);

							List<ProNrNeighborhoodInfo> proNrNeighborhoodInfos = proNrDataInfoBean
									.getProNrNeighborhoodInfos();
							if (proNrNeighborhoodInfos != null && proNrNeighborhoodInfos.size() > 0) {
								for (ProNrNeighborhoodInfo proNrNeighborhoodInfo : proNrNeighborhoodInfos) {
									Map<String, Object> nellMap = new HashMap<>();
									// 5G
									nellMap.put("Type", "Ncell");
									nellMap.put("CellName", "");
									nellMap.put("SSARFCN", proNrNeighborhoodInfo.getpNrNeighborhoodNRARFCN());
									nellMap.put("PCI", proNrNeighborhoodInfo.getpNrNeighborhoodPCI());
									nellMap.put("Beam", proNrNeighborhoodInfo.getpNrNeighborhoodBeam());
									nellMap.put("SSRSRP", proNrNeighborhoodInfo.getpNrNeighborhoodSSRSRP());
									nellMap.put("SSRSRQ", proNrNeighborhoodInfo.getpNrNeighborhoodSSRSRQ());
									nellMap.put("SSSINR", proNrNeighborhoodInfo.getpNrNeighborhoodSSSINR());
									datas.add(nellMap);
								}
							}
							paramMap.put("nearOrMain5g", datas);
						}
						///////

						// 4G
						ProLteDataInfoBean proLteDataInfoBeans1 = fiveLogMain.getProLteDataInfoBean();
						if (proLteDataInfoBeans1 != null) {
							// 4G
							fourGhashMap.put("Cell Name", "");
							if (lteDataInfoBean != null) {
								fourGhashMap.put("Cell Name", lteDataInfoBean.getLteCellName());
							}
							fourGhashMap.put("Band", proLteDataInfoBeans1.getServingCellPccBandIndex());
							fourGhashMap.put("BandWidth(MHz)", proLteDataInfoBeans1.getServingCellPccBwDl());
							fourGhashMap.put("Fequency DL", proLteDataInfoBeans1.getServingCellPccFreqDl());
							fourGhashMap.put("EARFCN", proLteDataInfoBeans1.getServingCellPccEarfcnDl());
							fourGhashMap.put("TAC", proLteDataInfoBeans1.getServingCellPccTac());
							fourGhashMap.put("ECI", proLteDataInfoBeans1.getServingCellPccSiteEci());
							fourGhashMap.put("eNodeB ID", proLteDataInfoBeans1.getServingCellPccEnodebId());
							fourGhashMap.put("CI", proLteDataInfoBeans1.getServingCellPccCellId());
							fourGhashMap.put("PCI", proLteDataInfoBeans1.getServingCellPccCellId());
							fourGhashMap.put("RSRP(dBm)", proLteDataInfoBeans1.getServingCellPccRsrp());
							fourGhashMap.put("SINR(dB)", proLteDataInfoBeans1.getServingCellPccSinr());
							fourGhashMap.put("RSRQ(dB)", proLteDataInfoBeans1.getServingCellPccRsrq());
							fourGhashMap.put("RSSI(dBm)", proLteDataInfoBeans1.getServingCellPccRssi());
							fourGhashMap.put("SA/SSP", proLteDataInfoBeans1.getServingCellPccSa());

							fourGhashMap.put("CQI", proLteDataInfoBeans1.getServingCellPccWidebandCqi());
							fourGhashMap.put("RI", proLteDataInfoBeans1.getServingCellPccBandIndex());
							fourGhashMap.put("PUSCH TxPower", proLteDataInfoBeans1.getServingCellPccPuschTxpower());
							fourGhashMap.put("PUCCH TxPower", proLteDataInfoBeans1.getServingCellPccPucchTxpower());
							fourGhashMap.put("Grant UL/s", proLteDataInfoBeans1.getServingCellPccGrantulnum());
							fourGhashMap.put("Grant DL/s", proLteDataInfoBeans1.getServingCellPccGrantdlnum());
							fourGhashMap.put("PUSCH BLER(%)", proLteDataInfoBeans1.getServingCellPccULBLER());
							fourGhashMap.put("PDSCH BLER(%)", proLteDataInfoBeans1.getServingCellPccDLBLER());
							fourGhashMap.put("PUSCH RB/s", proLteDataInfoBeans1.getServingCellPccPdschRbs());
							fourGhashMap.put("PDSCH RB/s", proLteDataInfoBeans1.getServingCellPccPdschRbs());
							fourGhashMap.put("MCS UL", proLteDataInfoBeans1.getServingCellPccMcsul());
							fourGhashMap.put("MCS DL", proLteDataInfoBeans1.getServingCellPccMcsdl());
							fourGhashMap.put("Mod UL", proLteDataInfoBeans1.getServingCellPccModul());
							fourGhashMap.put("Mod DL", proLteDataInfoBeans1.getServingCellPccModdl());

							paramMap.put("cellInfo4g", fourGhashMap);
						}

						// 5g
						ProNrDataInfoBean proNrDataInfoBean1 = fiveLogMain.getProNrDataInfoBean();
						if (proNrDataInfoBean1 != null) {
							// 5G
							fiveGhashMap.put("Cell Name", "");
							if (nrDataInfoBean != null) {
								fiveGhashMap.put("Cell Name", nrDataInfoBean.getNrCellName());
							}
							fiveGhashMap.put("Band", proNrDataInfoBean1.getBand());
							fiveGhashMap.put("Band Width(MHz)", proNrDataInfoBean1.getBandWidth());
							fiveGhashMap.put("Frequency PointA", proNrDataInfoBean1.getFrequencyPointA());
							fiveGhashMap.put("Frequency DL", proNrDataInfoBean1.getFrequencyDL());
							fiveGhashMap.put("GSCN", proNrDataInfoBean1.getGSCN());
							fiveGhashMap.put("SC Space(kHz)", proNrDataInfoBean1.getSubCarrierSpace());
							fiveGhashMap.put("SS ARFCN", proNrDataInfoBean1.getSsARFCN());
							fiveGhashMap.put("PCI", proNrDataInfoBean1.getPCI());
							fiveGhashMap.put("SSB Index", proNrDataInfoBean1.getSSBIndex());
							fiveGhashMap.put("SS RSRP(dBm)", proNrDataInfoBean1.getSsRSRP());
							fiveGhashMap.put("SS SINR(dB)", proNrDataInfoBean1.getSsSINR());
							fiveGhashMap.put("SS RSRQ(dB)", proNrDataInfoBean1.getSsRSRQ());
							fiveGhashMap.put("CQI", proNrDataInfoBean1.getCqi());
							fiveGhashMap.put("Slot Config(DL/UL)", proNrDataInfoBean1.getSlotConfigDLUL());
							fiveGhashMap.put("RI", proNrDataInfoBean1.getRiNumDL());
							fiveGhashMap.put("SRS TxPower", proNrDataInfoBean1.getSrsTxPower());
							fiveGhashMap.put("PUSCH TxPower", proNrDataInfoBean1.getPuschTxPower());
							fiveGhashMap.put("PUCCH TxPower", proNrDataInfoBean1.getPucchTxPower());
							fiveGhashMap.put("Grant UL/s", proNrDataInfoBean1.getGrantULNum());
							fiveGhashMap.put("Grant DL/s", proNrDataInfoBean1.getGrantDLNum());
							fiveGhashMap.put("PUSCH BLER(%)", proNrDataInfoBean1.getPuschBler());
							fiveGhashMap.put("PDSCH BLER(%)", proNrDataInfoBean1.getPdschBler());
							fiveGhashMap.put("PUSCH RB/s", proNrDataInfoBean1.getPuschRB());
							fiveGhashMap.put("PDSCH RB/s", proNrDataInfoBean1.getPdschRB());
							fiveGhashMap.put("MCS UL", proNrDataInfoBean1.getMcsUL());
							fiveGhashMap.put("MCS DL", proNrDataInfoBean1.getMcsDL());
							fiveGhashMap.put("Mod UL", proNrDataInfoBean1.getModUL());
							fiveGhashMap.put("Mod DL", proNrDataInfoBean1.getModDL());

							paramMap.put("cellInfo5g", fiveGhashMap);
						}

					} else {// 非root版本 显示5G/4G窗口的信息

						// 非root版本 显示5G/4G窗口的信息

						// 5G
						NrDataInfoBean proLteDataInfoBeans = fiveLogMain.getNrDataInfoBean();
						if (proLteDataInfoBeans != null) {
							List<Map<String, Object>> datas = new ArrayList<>();

							// PCLL
							Map<String, Object> pcellMap = new HashMap<>();
							pcellMap.put("Type", "Pcell");
							// 5G
							pcellMap.put("CellName", "");
							if (lteDataInfoBean != null) {
								pcellMap.put("CellName", proLteDataInfoBeans.getNrCellName());
							}
							pcellMap.put("SSEARFCN", proLteDataInfoBeans.getNrARFCN());
							pcellMap.put("PCI", proLteDataInfoBeans.getNrPCI());
							pcellMap.put("Beam", "");
							pcellMap.put("SSRSRP", proLteDataInfoBeans.getSsRSRP());
							pcellMap.put("SSRSRQ", proLteDataInfoBeans.getSsRSRQ());
							pcellMap.put("SSSINR", proLteDataInfoBeans.getSsSINR());
							datas.add(pcellMap);

							List<NrNeighborhoodInfo> proLteNeighborhoodInfos = proLteDataInfoBeans
									.getNrNeighborhoodInfos();
							if (proLteNeighborhoodInfos != null && proLteNeighborhoodInfos.size() > 0) {
								for (NrNeighborhoodInfo proLteNeighborhoodInfo : proLteNeighborhoodInfos) {
									Map<String, Object> lteMap = new HashMap<>();

									lteMap.put("Type", "Ncell");
									// 5G
									lteMap.put("CellName", "");
									lteMap.put("SSEARFCN", proLteNeighborhoodInfo.getNrNeighborhoodNRARFCN());
									lteMap.put("PCI", proLteNeighborhoodInfo.getNrNeighborhoodPCI());
									lteMap.put("Beam", "");
									lteMap.put("SSRSRP", proLteNeighborhoodInfo.getNrNeighborhoodSSRSRP());
									lteMap.put("SSRSRQ", proLteNeighborhoodInfo.getNrNeighborhoodSSRSRQ());
									lteMap.put("SSSINR", proLteNeighborhoodInfo.getNrNeighborhoodSSSINR());
									datas.add(lteMap);
								}
							}
							paramMap.put("nearOrMain5g", datas);
						}

						// 4g
						LteDataInfoBean proNrDataInfoBean = fiveLogMain.getLteDataInfoBean();
						if (proNrDataInfoBean != null) {
							List<Map<String, Object>> datas = new ArrayList<>();
							Map<String, Object> pcellMap = new HashMap<>();

							// 4G
							pcellMap.put("Type", "Pcell");
							pcellMap.put("CellName", "");
							if (nrDataInfoBean != null) {
								pcellMap.put("CellName", proNrDataInfoBean.getLteCellName());
							}
							pcellMap.put("EARFCN", proNrDataInfoBean.getLteEARFCN());
							pcellMap.put("PCI", proNrDataInfoBean.getLtePCI());
							pcellMap.put("RSRP", proNrDataInfoBean.getLteRSRP());
							pcellMap.put("RSRQ", proNrDataInfoBean.getLteRSRQ());
							pcellMap.put("SINR", proNrDataInfoBean.getLteSINR());
							pcellMap.put("RSSI", proNrDataInfoBean.getLteRSSI());
							datas.add(pcellMap);

							List<LteNeighborhoodInfo> proNrNeighborhoodInfos = proNrDataInfoBean
									.getLteNeighborhoodInfos();
							if (proNrNeighborhoodInfos != null && proNrNeighborhoodInfos.size() > 0) {
								for (LteNeighborhoodInfo proNrNeighborhoodInfo : proNrNeighborhoodInfos) {
									Map<String, Object> nellMap = new HashMap<>();
									// 4G
									nellMap.put("Type", "Ncell");
									nellMap.put("CellName", "");
									nellMap.put("EARFCN", proNrNeighborhoodInfo.getLteNeighbirhoodEARFCN());
									nellMap.put("PCI", proNrNeighborhoodInfo.getLteNeighbirhoodPCI());
									nellMap.put("RSRP", proNrNeighborhoodInfo.getLteNeighbirhoodRSRP());
									nellMap.put("RSRQ", proNrNeighborhoodInfo.getLteNeighbirhoodRSRQ());
									nellMap.put("SINR", "");
									nellMap.put("RSSI", proNrNeighborhoodInfo.getLteNeighbirhoodRSSI());
									datas.add(nellMap);
								}
							}
							paramMap.put("nearOrMain4g", datas);
						}

						/////

						// 4G
						LteDataInfoBean proLteDataInfoBeans1 = fiveLogMain.getLteDataInfoBean();
						if (proLteDataInfoBeans1 != null) {
							// 4G
							fourGhashMap.put("Cell Name", "");
							if (lteDataInfoBean != null) {
								fourGhashMap.put("Cell Name", lteDataInfoBean.getLteCellName());
							}
							fourGhashMap.put("Band", "");
							fourGhashMap.put("BandWidth(MHz)", "");
							fourGhashMap.put("Fequency DL", "");
							fourGhashMap.put("EARFCN", proLteDataInfoBeans1.getLteEARFCN());
							fourGhashMap.put("TAC", proLteDataInfoBeans1.getLteEARFCN());
							fourGhashMap.put("ECI", "");
							fourGhashMap.put("eNodeB ID", proLteDataInfoBeans1.getLteENB());
							fourGhashMap.put("CI", proLteDataInfoBeans1.getLteCellID());
							fourGhashMap.put("PCI", proLteDataInfoBeans1.getLtePCI());
							fourGhashMap.put("RSRP(dBm)", proLteDataInfoBeans1.getLteRSRP());
							fourGhashMap.put("SINR(dB)", proLteDataInfoBeans1.getLteSINR());
							fourGhashMap.put("RSRQ(dB)", proLteDataInfoBeans1.getLteRSRQ());
							fourGhashMap.put("RSSI(dBm)", proLteDataInfoBeans1.getLteRSSI());
							fourGhashMap.put("SA/SSP", "");

							fourGhashMap.put("CQI", "");
							fourGhashMap.put("RI", "");
							fourGhashMap.put("PUSCH TxPower", "");
							fourGhashMap.put("PUCCH TxPower", "");
							fourGhashMap.put("Grant UL/s", "");
							fourGhashMap.put("Grant DL/s", "");
							fourGhashMap.put("PUSCH BLER(%)", "");
							fourGhashMap.put("PDSCH BLER(%)", "");
							fourGhashMap.put("PUSCH RB/s", "");
							fourGhashMap.put("PDSCH RB/s", "");
							fourGhashMap.put("MCS UL", "");
							fourGhashMap.put("MCS DL", "");
							fourGhashMap.put("Mod UL", "");
							fourGhashMap.put("Mod DL", "");
							paramMap.put("cellInfo4g", fourGhashMap);

						}

						// 5g
						NrDataInfoBean proNrDataInfoBean1 = fiveLogMain.getNrDataInfoBean();
						if (proNrDataInfoBean1 != null) {
							// 5G
							fiveGhashMap.put("Cell Name", "");
							if (nrDataInfoBean != null) {
								fiveGhashMap.put("Cell Name", nrDataInfoBean.getNrCellName());
							}
							fiveGhashMap.put("Band", "");
							fiveGhashMap.put("Band Width(MHz)", "");
							fiveGhashMap.put("Frequency PointA", "");
							fiveGhashMap.put("Frequency DL", "");
							fiveGhashMap.put("GSCN", "");
							fiveGhashMap.put("SC Space(kHz)", "");
							fiveGhashMap.put("SS ARFCN", proNrDataInfoBean1.getNrARFCN());
							fiveGhashMap.put("PCI", proNrDataInfoBean1.getNrPCI());
							fiveGhashMap.put("SSB Index", "");
							fiveGhashMap.put("SS RSRP(dBm)", proNrDataInfoBean1.getSsRSRP());
							fiveGhashMap.put("SS SINR(dB)", proNrDataInfoBean1.getSsSINR());
							fiveGhashMap.put("SS RSRQ(dB)", proNrDataInfoBean1.getSsRSRQ());
							fiveGhashMap.put("CQI", "");
							fiveGhashMap.put("Slot Config(DL/UL)", "");
							fiveGhashMap.put("RI", "");
							fiveGhashMap.put("SRS TxPower", "");
							fiveGhashMap.put("PUSCH TxPower", "");
							fiveGhashMap.put("PUCCH TxPower", "");
							fiveGhashMap.put("Grant UL/s", "");
							fiveGhashMap.put("Grant DL/s", "");
							fiveGhashMap.put("PUSCH BLER(%)", "");
							fiveGhashMap.put("PDSCH BLER(%)", "");
							fiveGhashMap.put("PUSCH RB/s", "");
							fiveGhashMap.put("PDSCH RB/s", "");
							fiveGhashMap.put("MCS UL", "");
							fiveGhashMap.put("MCS DL", "");
							fiveGhashMap.put("Mod UL", "");
							fiveGhashMap.put("Mod DL", "");

							paramMap.put("cellInfo5g", fiveGhashMap);
						}

					}
				}
			}
		}

		return paramMap;
	}

	/**
	 * itemType : UL\DL\RSRP\SINR\SSRSRP\SSSINR { time:['12:00:00', '12:00:30'],
	 * mid: [122434，111] 时间不存在的mid就设置未-1 itemOneData:{name: 'RSRP', data:[-40,-30]},
	 * itemTwoData:{name: 'SINR', data:[-40,-30]} }
	 * 
	 * @Description: 折现数据查询
	 * @author weichengz
	 * @throws ParseException
	 * @date 2020年5月8日 上午10:11:43
	 */
	@ResponseBody
	@PostMapping(value = "/queryAnalyzeLineData")
	public Map<String, Object> queryAnalyzeLineData(@RequestParam("pid") String pid,
			@RequestParam("itemTypeOne") String itemTypeOne,
			@RequestParam(value = "itemTypeTwo", required = false) String itemTypeTwo) throws ParseException {
		Map<String, Object> resultMap = new HashMap<>();
		/*
		 * resultMap.put("time", new ArrayList<>()); resultMap.put("mid", new
		 * ArrayList<>()); resultMap.put("itemOneData", new HashMap<>());
		 * resultMap.put("itemTwoData", new HashMap<>());
		 */

		if (StringUtils.isNotBlank(pid) && StringUtils.isNotBlank(itemTypeOne)) {
			List<String> items = Arrays.asList("RSRP", "SINR", "UL", "DL", "SSRSRP", "SSSINR");
			if (!items.contains(itemTypeOne)) {
				throw new IllegalArgumentException("阈值类型【RSRP、SINR、UL、DL、SSRSRP、SSSINR】不正确：" + itemTypeOne);
			}

			if (StringUtils.isNotBlank(itemTypeTwo)) {
				if (!items.contains(itemTypeTwo)) {
					throw new IllegalArgumentException("阈值类型【RSRP、SINR、UL、DL、SSRSRP、SSSINR】不正确：" + itemTypeTwo);
				}
			}

			// 查询这条id对应的主log信息
			QueryParamData queryParamDataParam = new QueryParamData("logmain5g", "logmain5g");
			queryParamDataParam.setTermMap(MyUtil.createHashMap("pid.keyword~" + pid));
			queryParamDataParam.setLimit(200);
			queryParamDataParam.setRevelFields(Arrays.asList("id", "testTime", "downLoadSpeed", "upLoadSpeed", "rsrp",
					"sinr", "ssrsrp", "sssinr"));
			queryParamDataParam.setSortMap(MyUtil.createHashMap("testTimeMill~ASC"));
			List<Map<String, Object>> queryList = Esutil.scrollQuery(queryParamDataParam);
			if (queryList != null && queryList.size() > 0) {
				// 得到开始的时间和结束的时间
				Map<String, Object> mapFirst = queryList.get(0);
				Map<String, Object> mapFinal = queryList.get(queryList.size() - 1);

				String beginTime = mapFirst.get("testTime").toString();// 记录开始的时间2020-05-12 08:01:14
				String endTime = mapFinal.get("testTime").toString();// 记录结束的时间2020-05-12 08:05:19
				if (StringUtils.isBlank(beginTime) || StringUtils.isBlank(endTime)) {
					throw new IllegalArgumentException("开始时间、结束时间范围不正确");
				}

				// 时间集合
				List<String> times = new ArrayList<>();
				// mid集合
				List<String> mids = new ArrayList<>();
				// 阈值1对应的数值
				List<String> itemOne = new ArrayList<>();
				// 阈值2对应的数值
				List<String> itemTwo = new ArrayList<>();

				// 根据开始时间结束时间计算出所有一秒为单位的时间序列
				List<String> dateTimes = MyDateUtil.getDateListStrMill(beginTime, endTime, 1);

				Integer nowNum = 0;// 用于记录内循环的下次开始位置
				for (String dateKey : dateTimes) {

					for (int i = nowNum; i < queryList.size(); i++) {
						Map<String, Object> mapRes = queryList.get(i);
						FiveLogMain fiveLogMain = JSONObject.parseObject(JSONObject.toJSONString(mapRes),
								FiveLogMain.class);
						if (fiveLogMain != null) {
							String id = fiveLogMain.getId();
							String testTime = fiveLogMain.getTestTime();
							String upLoadSpeed = fiveLogMain.getUpLoadSpeed() == null ? ""
									: fiveLogMain.getUpLoadSpeed();
							String downLoadSpeed = fiveLogMain.getDownLoadSpeed() == null ? ""
									: fiveLogMain.getDownLoadSpeed();
							String rsrp = fiveLogMain.getRsrp() == null ? "" : fiveLogMain.getRsrp();
							String sinr = fiveLogMain.getSinr() == null ? "" : fiveLogMain.getSinr();
							String ssrsrp = fiveLogMain.getSsrsrp() == null ? "" : fiveLogMain.getSsrsrp();
							String sssinr = fiveLogMain.getSssinr() == null ? "" : fiveLogMain.getSssinr();

							if (StringUtils.isNotBlank(testTime)) {

								if (!times.contains(testTime)) {

									if (testTime.compareTo(dateKey) <= 0) {
										nowNum++;

										times.add(testTime);
										mids.add(id);
										// "RSRP", "SINR", "UL", "DL", "SSRSRP", "SSSINR"
										// 开始判断类型
										if (("RSRP").equals(itemTypeOne)) {
											itemOne.add(rsrp);
										}
										if (("SINR").equals(itemTypeOne)) {
											itemOne.add(sinr);
										}
										if (("SSRSRP").equals(itemTypeOne)) {
											itemOne.add(ssrsrp);
										}
										if (("SSSINR").equals(itemTypeOne)) {
											itemOne.add(sssinr);
										}
										if (("UL").equals(itemTypeOne)) {
											itemOne.add(upLoadSpeed);
										}
										if (("DL").equals(itemTypeOne)) {
											itemOne.add(downLoadSpeed);
										}
										//
										if (StringUtils.isNotBlank(itemTypeTwo)) {
											if (("RSRP").equals(itemTypeTwo)) {
												itemTwo.add(rsrp);
											}
											if (("SINR").equals(itemTypeTwo)) {
												itemTwo.add(sinr);
											}
											if (("SSRSRP").equals(itemTypeTwo)) {
												itemTwo.add(ssrsrp);
											}
											if (("SSSINR").equals(itemTypeTwo)) {
												itemTwo.add(sssinr);
											}
											if (("UL").equals(itemTypeTwo)) {
												itemTwo.add(upLoadSpeed);
											}
											if (("DL").equals(itemTypeTwo)) {
												itemTwo.add(downLoadSpeed);
											}
										}

									} else {
										if (!times.contains(dateKey)) {

											times.add(dateKey);
											mids.add("-1");
											// "RSRP", "SINR", "UL", "DL", "SSRSRP", "SSSINR"
											// 开始判断类型
											if (("RSRP").equals(itemTypeOne)) {
												itemOne.add("");
											}
											if (("SINR").equals(itemTypeOne)) {
												itemOne.add("");
											}
											if (("SSRSRP").equals(itemTypeOne)) {
												itemOne.add("");
											}
											if (("SSSINR").equals(itemTypeOne)) {
												itemOne.add("");
											}
											if (("UL").equals(itemTypeOne)) {
												itemOne.add("");
											}
											if (("DL").equals(itemTypeOne)) {
												itemOne.add("");
											}
											//
											if (StringUtils.isNotBlank(itemTypeTwo)) {
												if (("RSRP").equals(itemTypeTwo)) {
													itemTwo.add("");
												}
												if (("SINR").equals(itemTypeTwo)) {
													itemTwo.add("");
												}
												if (("SSRSRP").equals(itemTypeTwo)) {
													itemTwo.add("");
												}
												if (("SSSINR").equals(itemTypeTwo)) {
													itemTwo.add("");
												}
												if (("UL").equals(itemTypeTwo)) {
													itemTwo.add("");
												}
												if (("DL").equals(itemTypeTwo)) {
													itemTwo.add("");
												}
											}
											break;

										}

									}

								}
								//////

							}

						}
					}
					//////// 结束
				}

				/*
				 * { time:['12:00:00', '12:00:30'], mid: [122434，111] 时间不存在的mid就设置未-1
				 * itemOneData:{name: 'RSRP', data:[-40,-30]}, itemTwoData:{name: 'SINR',
				 * data:[-40,-30]} }
				 */

				resultMap.put("time", times);
				resultMap.put("mid", mids);
				if (StringUtils.isNoneBlank(itemTypeOne)) {
					Map<String, Object> oneMap = new HashMap<>();
					oneMap.put("name", itemTypeOne);
					oneMap.put("data", itemOne);
					resultMap.put("itemOneData", oneMap);
				}
				if (StringUtils.isNoneBlank(itemTypeTwo)) {
					Map<String, Object> oneMap = new HashMap<>();
					oneMap.put("name", itemTypeTwo);
					oneMap.put("data", itemTwo);
					resultMap.put("itemTwoData", oneMap);
				}
			}
		} else {
			throw new IllegalArgumentException("未传入室外测试序号pid或者未传入阈值类型【RSRP、SINR、UL、DL、SSRSRP、SSSINR】");
		}
		return resultMap;
	}

	/**
	 * 
	 * @Description: 查询速率
	 * @author weichengz
	 * @date 2020年5月8日 上午10:11:43
	 */
	@ResponseBody
	@PostMapping(value = "/queryLogRate")
	public Map<String, Object> queryLogRate(@RequestParam("id") String id) {

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("4G", new HashMap<>());
		paramMap.put("5G", new HashMap<>());

		// log中每个主题内容的id
		if (StringUtils.isNotBlank(id)) {
			// 查询这条id对应的主log信息
			QueryParamData queryParamDataParam = new QueryParamData("logmain5g", "logmain5g");
			queryParamDataParam.setTermMap(MyUtil.createHashMap("id~" + id));
			queryParamDataParam.setLimit(1);
			List<Map<String, Object>> queryList = Esutil.queryList(queryParamDataParam);
			if (queryList != null && queryList.size() > 0) {
				Map<String, Object> mapRes = queryList.get(0);
				FiveLogMain fiveLogMain = JSONObject.parseObject(JSONObject.toJSONString(mapRes), FiveLogMain.class);
				if (fiveLogMain != null) {
					String logversion = fiveLogMain.getLogversion();
					if (("0").equals(logversion)) {// 4g
						LinkedHashMap<String, Object> paramMapUlDl = new LinkedHashMap<>();
						paramMapUlDl.put("APP", fiveLogMain.getDownLoadSpeed() + " ~ " + fiveLogMain.getUpLoadSpeed());

						ProLteDataInfoBean proNrDataInfoBean = fiveLogMain.getProLteDataInfoBean();
						if (proNrDataInfoBean != null) {
							paramMapUlDl.put("PDCP", proNrDataInfoBean.getThroughputPccPdcpDl() + " ~ "
									+ proNrDataInfoBean.getThroughputPccPdcpUl());
							paramMapUlDl.put("RLC", proNrDataInfoBean.getThroughputPccRlcDl() + " ~ "
									+ proNrDataInfoBean.getThroughputPccRlcUl());
							paramMapUlDl.put("MAC", proNrDataInfoBean.getThroughputPccMacDl() + " ~ "
									+ proNrDataInfoBean.getThroughputPccMacUl());
							paramMapUlDl.put("PHY", " ~ ");
						}
						paramMap.put("4G", paramMapUlDl);
					}
					if (("1").equals(logversion)) {// 5g
						LinkedHashMap<String, Object> paramMapUlDl = new LinkedHashMap<>();
						paramMapUlDl.put("APP", fiveLogMain.getDownLoadSpeed() + " ~ " + fiveLogMain.getUpLoadSpeed());

						ProNrDataInfoBean proNrDataInfoBean = fiveLogMain.getProNrDataInfoBean();
						if (proNrDataInfoBean != null) {
							paramMapUlDl.put("PDCP",
									proNrDataInfoBean.getPdcpDLThr() + " ~ " + proNrDataInfoBean.getPdcpULThr());
							paramMapUlDl.put("RLC",
									proNrDataInfoBean.getRlcDLThr() + " ~ " + proNrDataInfoBean.getRlcULThr());
							paramMapUlDl.put("MAC",
									proNrDataInfoBean.getMacDLThr() + " ~ " + proNrDataInfoBean.getMacULThr());
							paramMapUlDl.put("PHY", " ~ ");
						}
						paramMap.put("5G", paramMapUlDl);
					}
				}
			}
		}
		return paramMap;
	}

	/**
	 * 支持4G/5G的工参的拉取方法
	 * 
	 * @Description: 更新sim工参信息
	 * @author weichengz
	 * @date 2019年12月17日 下午5:26:47
	 */
	@ResponseBody
	@PostMapping(value = "/freshSimGc")
	public JsonMsgUtil freshSimGc(@RequestParam("index") String index, @RequestParam("type") String type,
			@RequestParam("netId") String netId) throws Exception {

		if (StringUtils.isBlank(netId) || StringUtils.isBlank(index) || StringUtils.isBlank(type)) {
			throw new IllegalArgumentException("索引类型index/type/netId不能为空");
		}

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
			paramMap.put("netId", netId);// 先写死1
			paramMap.put("projectLevel", projectLevelFlag);// 3是查询本市 2是查询全省的
			paramMap.put("provinceName", sysProject.getProProvice());
			paramMap.put("operator", sysProject.getProjOperator());
			if (("simgc").equals(index)) {
				paramMap.put("fields", // 这个顺序 要和 实体类的顺序一致
						"lte_city_name,lte_net,lte_enodebid,lte_sector_id,lte_cell,lte_ci,lte_ecgi,lte_phycellid,lte_longitude2,lte_latitude2,lte_longitude,lte_latitude,lte_site_tall,lte_azimuth,lte_mechanical_downdip,lte_electronic_downdip,lte_total_downdip,lte_tac,lte_sys,lte_site_type,lte_earfcn,lte_derrick_type,lte_address,lte_scene,lte_grid,lte_firm,lte_site_name");
			} else {
				paramMap.put("fields", // 这个顺序 要和 实体类的顺序一致
						"nr_city_name,1,nr_btsid,1,nr_lcrid,nr_ci,nr_ci,nr_phycellid,nr_longitude2,nr_latitude2,nr_longitude,nr_latitude,nr_site_tall,nr_azimuth,nr_mechanical_downdip,nr_electronic_downdip,nr_total_downdip,nr_tac,nr_sys,nr_site_type,nr_arfcn,nr_derrick_type,nr_address,nr_scene,nr_grid,nr_firm,nr_site_name");
			}

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
					CommonModel commonModel = null;
					if (("simgc").equals(index)) {
						commonModel = CommonModel.changeStrToObj(data);
					} else {
						commonModel = CommonModel.changeStrToObj5g(data);
					}
					// 处理基站类型 转为indoor outdoor
					commonModel.dealStationType();
					// 处理基站类型 转为indoor outdoor

					GcModel model = new GcModel(commonModel);
					// 将经纬度处理为WGS84
					model.dealLngLatBdToWgs84();
					model.setCityId(sysProject.getId() == null ? "" : sysProject.getId().toString());
					// 以地市和ci为主键
					IndexRequest indexRequestOther = new IndexRequest(index, type,
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
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年6月29日 上午10:36:28
	 */
	@ResponseBody
	@PostMapping(value = "/addSimGc")
	public JsonMsgUtil addSimGc() throws Exception {

		BulkRequest request = new BulkRequest();

		LoginUser loginUser = UserUtil.getLoginUser();
		if (loginUser == null) {
			throw new IllegalArgumentException("当前登陆用户失效");
		}

		SysProject sysProject = sysProjectService.selectById(loginUser.getProjId());

		DataInputStream in = new DataInputStream(new FileInputStream(new File("D:\\stsaz\\1234.csv")));
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "GBK"));// 这里如果csv文件编码格式是utf-8,改成utf-8即可

		String line = "";
		String everyLine = "";
		int num = 0;
		try {
			while ((line = br.readLine()) != null) // 读取到的内容给line变量
			{
				if (num > 0) {
					everyLine = line;
					if (StringUtils.isNotBlank(everyLine)) {// 不为空的情况下进行处理
						System.out.println(everyLine);
						String[] split = everyLine.split(",");
						GcModel model = new GcModel();
						// 上海,一号1,,,0,1,329991,3549.81,1,1,32999,121.5733457,31.23189923,2,,216,20,宏站,6265,FDD,3,1,,,

						model.setLte_city_name(split[0]);
						model.setLte_site_name(split[1]);
						model.setLte_firm(split[2]);
						model.setLte_grid(split[3]);
						model.setLte_azimuth(split[4]);
						model.setLte_cell(split[5]);
						model.setLte_ci(split[6]);
						model.setLte_earfcn(split[7]);
						model.setLte_ecgi(split[8]);
						model.setLte_electronic_downdip(split[9]);
						model.setLte_enodebid(split[10]);
						model.setLte_longitude2(split[11]);
						model.setLte_latitude2(split[12]);
						model.setLte_longitude(split[11]);
						model.setLte_latitude(split[12]);

						model.setLte_mechanical_downdip(split[13]);
						model.setLte_net(split[14]);
						model.setLte_phycellid(split[15]);
						model.setLte_site_tall(split[16]);
						model.setLte_site_type("outdoor");
						model.setLte_tac(split[18]);
						model.setLte_sys(split[19]);
						model.setLte_total_downdip(split[20]);
						model.setLte_sector_id(split[21]);
						model.setLte_derrick_type("");
						model.setLte_address("");
						model.setLte_scene("");

						// 处理空间索引
						model.dealLocation();
						// 将经纬度处理为WGS84
						model.dealLngLatBdToWgs84();
						model.setCityId(sysProject.getId() == null ? "" : sysProject.getId().toString());
						// 以地市和ci为主键

						// 以地市和ci为主键
						IndexRequest indexRequestOther = new IndexRequest("simgc5g", "simgc5g",
								model.getLte_city_name() + "_" + model.getLte_ci());

						indexRequestOther.source(JSONObject.toJSONString(model), XContentType.JSON);
						request.add(indexRequestOther);
					}
				}
				num++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 批量保存
		transportClient.bulk(request).get();
		return new JsonMsgUtil(true, "SIM工参更新成功", "");
	}

	/**
	 * 根据[地市]条件批量删出数据、这个就是index跟type不一样
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

}
