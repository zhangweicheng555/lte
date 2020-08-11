package com.boot.kaizen.business.buss.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.business.buss.entity.ItemModelEntity;
import com.boot.kaizen.business.buss.entity.RequestParamConfigEntityAnalyze;
import com.boot.kaizen.business.buss.model.ItemModel;
import com.boot.kaizen.business.buss.model.LogAnaLyze;
import com.boot.kaizen.business.buss.model.RequestParamConfig;
import com.boot.kaizen.business.buss.model.fiveg.FootFtpzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootHttpzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootLtewxzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootNrwxzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootPingzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootYyzbBean;
import com.boot.kaizen.business.buss.model.fiveg.LogFoot;
import com.boot.kaizen.business.buss.service.ILogAnaLyzeService;
import com.boot.kaizen.business.buss.service.ITestConfigService;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.business.es.model.logModel.SignalDataBean;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.entity.RequestParamEntity;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.MyUtil;
import com.boot.kaizen.util.UserUtil;

/**
 * log日志的分析工具
 * 
 * @author weichengz
 * @date 2020年1月14日 下午4:37:15
 */
@Controller
@RequestMapping("/buss/analyze")
public class LogAnaLyzeController {

	@Autowired
	private ILogAnaLyzeService logAnaLyzeService;
	@Autowired
	private ITestConfigService testConfigService;

	/**
	 * 通过任务ID查询
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月21日 上午10:36:46
	 */
	@ResponseBody
	@RequestMapping(value = "/findById", method = RequestMethod.POST)
	public JsonMsgUtil findById(@RequestParam("id") String id) {
		LogAnaLyze logAnaLyze = logAnaLyzeService.selectById(id);
		return new JsonMsgUtil(true, "操作成功", logAnaLyze);
	}

	/**
	 * 任务IDs批量删出
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月21日 上午10:36:57
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JsonMsgUtil delete(@RequestParam("ids") String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new IllegalArgumentException("要删除的ID不存在");
		}
		logAnaLyzeService.deleteBatchIds(Arrays.asList(ids.split(",")));
		return new JsonMsgUtil(true, "操作成功", "");
	}

	/**
	 * 编辑
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月21日 上午10:36:32
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JsonMsgUtil edit(LogAnaLyze logAnaLyze) {
		if (StringUtils.isBlank(logAnaLyze.getId())) {
			logAnaLyze.setCreateTime(new Date());
		}
		logAnaLyzeService.insertOrUpdate(logAnaLyze);
		return new JsonMsgUtil(true, "操作成功", "");
	}
	
	
	/**
	 * 统计分析接口
	 */
	@ResponseBody
	@PostMapping(value = "/queryAnalyzeData")
	public JsonMsgUtil queryAnalyzeData(@RequestParam("pid") String pid,@RequestParam(value="type",required=false) String type) {
		
		Map<String, Object> resultMap=new HashMap<>();//最终返回的结果
		
		if (StringUtils.isBlank(type)) {
			type="0";
		}
		//查询图例的配置项问题
		Integer projId = Integer.valueOf(UserUtil.getLoginUser().getProjId().toString());
		List<RequestParamConfig> datasConfigs = testConfigService.queryItemAll(null, projId,type);
		
		if (datasConfigs ==null || datasConfigs.size()==0) {
			throw new IllegalArgumentException("该项目下测试配置项不存在");
		}
		
		
		RequestParamEntity requestParamEntity=new RequestParamEntity();
		Map<String, Object> mapAnd = new HashMap<String, Object>();//这是精确匹配的查询条件
		mapAnd.put("pid", pid);
		requestParamEntity.setMapAnd(mapAnd);
		
		
		List<RequestParamConfigEntityAnalyze> requestParamConfigEntities=new ArrayList<>();
		
		//这是无限指标的  统计rsrp  sinr在图里范围内的数量
		for (RequestParamConfig requestParamConfig : datasConfigs) {
			Map<String, Object> mapBetween = new HashMap<String, Object>();//查询rsrp的范围
			String item = requestParamConfig.getItem();
			List<ItemModel> content = requestParamConfig.getContent();
			if (("RSRP").equals(item)) {
				RequestParamConfigEntityAnalyze model=new RequestParamConfigEntityAnalyze(item);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("rsrp", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal()));
				}
				model.setContent(itemModelEntities);
				requestParamConfigEntities.add(model);
			}
			if (("SINR").equals(item)) {
				RequestParamConfigEntityAnalyze model=new RequestParamConfigEntityAnalyze(item);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("sinr", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal()));
				}
				model.setContent(itemModelEntities);
				requestParamConfigEntities.add(model);
			}
			
			if (("UL").equals(item)) {
				RequestParamConfigEntityAnalyze model=new RequestParamConfigEntityAnalyze(item);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("upLoadSpeed", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal()));
				}
				model.setContent(itemModelEntities);
				requestParamConfigEntities.add(model);
			}
			if (("DL").equals(item)) {
				RequestParamConfigEntityAnalyze model=new RequestParamConfigEntityAnalyze(item);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("downLoadSpeed", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal()));
				}
				model.setContent(itemModelEntities);
				requestParamConfigEntities.add(model);
			}
		}
		
		resultMap.put("range", requestParamConfigEntities);//将范围的注入
		
		//查询独立的模块  包括
		Map<String, Object> mapBetween = new HashMap<String, Object>();//查询rsrp的范围
		//mapBetween.remove("downLoadSpeed");
		mapBetween.put("uniqueRecord", "0");
		requestParamEntity.setMapBetween(new HashMap<>());
		requestParamEntity.setMapNo(mapBetween);
		List<LogAnaLyze> logAnaLyzes = logAnaLyzeService.selectList(MyUtil.createQueryPlus(requestParamEntity));
		if (logAnaLyzes !=null && logAnaLyzes.size()>0) {
			LogAnaLyze logAnaLyze = logAnaLyzes.get(0);
			//处理接通率
			//处理掉线率
			resultMap.put("CALLSUCC", logAnaLyze.getZhcs());
			resultMap.put("CALLFAIL", logAnaLyze.getWjtcs());
			resultMap.put("CALLUNLINE", logAnaLyze.getDhcs());
			//处理ping成功率
			resultMap.put("PINGSUCC", logAnaLyze.getPingCgch());
			resultMap.put("PINGFAIL", logAnaLyze.getPingSbch());
			//处理http成功率
			resultMap.put("HTTPSUCC", logAnaLyze.getHttpCgch());
			resultMap.put("HTTPFAIL", logAnaLyze.getHttpSbch());
			
			//将数据显示信息存入
			String finalLogData = logAnaLyze.getFinalLogData();
			if (StringUtils.isNotBlank(finalLogData)) {
				SignalDataBean signalDataBean = JSONObject.parseObject(finalLogData, SignalDataBean.class);
				resultMap.put("zbBean",signalDataBean.getZbBean());
				resultMap.put("powerBean",signalDataBean.getPowerBean());
				resultMap.put("wxzbBean",signalDataBean.getWxzbBean());
				resultMap.put("yyzbbean",signalDataBean.getYyzbbean());
				resultMap.put("ftpzbBean",signalDataBean.getFtpzbBean());
				resultMap.put("httpzbBean",signalDataBean.getHttpzbBean());
				resultMap.put("pingzbBean",signalDataBean.getPingzbBean());
			}
			
		}
		return new JsonMsgUtil(true, "查询成功", resultMap);
	}
	
	/**
	 * 统计分析接口
	 */
	@ResponseBody
	@PostMapping(value = "/queryAnalyzeData5g")
	public JsonMsgUtil queryAnalyzeData5g(@RequestParam("pid") String pid) {
		
		Map<String, Object> resultMap=new HashMap<>();//最终返回的结果
		String type="1";
		//查询图例的配置项问题
		Integer projId = Integer.valueOf(UserUtil.getLoginUser().getProjId().toString());
		List<RequestParamConfig> datasConfigs = testConfigService.queryItemAll(null, projId,type);
		
		if (datasConfigs ==null || datasConfigs.size()==0) {
			throw new IllegalArgumentException("该项目下测试配置项不存在");
		}
		
		
		RequestParamEntity requestParamEntity=new RequestParamEntity();
		Map<String, Object> mapAnd = new HashMap<String, Object>();//这是精确匹配的查询条件
		mapAnd.put("pid", pid);
		requestParamEntity.setMapAnd(mapAnd);
		
		
		List<RequestParamConfigEntityAnalyze> requestParamConfigEntities=new ArrayList<>();
		
		//这是无限指标的  统计rsrp  sinr在图里范围内的数量
		for (RequestParamConfig requestParamConfig : datasConfigs) {
			Map<String, Object> mapBetween = new HashMap<String, Object>();//查询rsrp的范围
			String item = requestParamConfig.getItem();
			List<ItemModel> content = requestParamConfig.getContent();
			if (("RSRP").equals(item)) {
				RequestParamConfigEntityAnalyze model=new RequestParamConfigEntityAnalyze(item);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("rsrp", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal()));
				}
				model.setContent(itemModelEntities);
				requestParamConfigEntities.add(model);
			}
			if (("SINR").equals(item)) {
				RequestParamConfigEntityAnalyze model=new RequestParamConfigEntityAnalyze(item);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("sinr", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal()));
				}
				model.setContent(itemModelEntities);
				requestParamConfigEntities.add(model);
			}
			
			if (("UL").equals(item)) {
				RequestParamConfigEntityAnalyze model=new RequestParamConfigEntityAnalyze(item);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("upLoadSpeed", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal()));
				}
				model.setContent(itemModelEntities);
				requestParamConfigEntities.add(model);
			}
			if (("DL").equals(item)) {
				RequestParamConfigEntityAnalyze model=new RequestParamConfigEntityAnalyze(item);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("downLoadSpeed", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal()));
				}
				model.setContent(itemModelEntities);
				requestParamConfigEntities.add(model);
			}
		}
		
		resultMap.put("range", requestParamConfigEntities);//将范围的注入
		
		//查询独立的模块  包括
		Map<String, Object> mapBetween = new HashMap<String, Object>();//查询rsrp的范围
		//mapBetween.remove("downLoadSpeed");
		mapBetween.put("uniqueRecord", "0");
		requestParamEntity.setMapBetween(new HashMap<>());
		requestParamEntity.setMapNo(mapBetween);
		List<LogAnaLyze> logAnaLyzes = logAnaLyzeService.selectList(MyUtil.createQueryPlus(requestParamEntity));
		if (logAnaLyzes !=null && logAnaLyzes.size()>0) {
			LogAnaLyze logAnaLyze = logAnaLyzes.get(0);
			//处理接通率
			//处理掉线率
			resultMap.put("CALLSUCC", logAnaLyze.getZhcs());
			resultMap.put("CALLFAIL", logAnaLyze.getWjtcs());
			resultMap.put("CALLUNLINE", logAnaLyze.getDhcs());
			//处理ping成功率
			resultMap.put("PINGSUCC", logAnaLyze.getPingCgch());
			resultMap.put("PINGFAIL", logAnaLyze.getPingSbch());
			//处理http成功率
			resultMap.put("HTTPSUCC", logAnaLyze.getHttpCgch());
			resultMap.put("HTTPFAIL", logAnaLyze.getHttpSbch());
			
			//将数据显示信息存入
			String finalLogData = logAnaLyze.getFinalLogData();
			if (StringUtils.isNotBlank(finalLogData)) {
				//查看数据的头部信息  获取 root / 竟对  /副卡的 信息标记
				QueryParamData queryParamDataParam = new QueryParamData("logmain5g", "logmain5g");
				queryParamDataParam.setTermMap(MyUtil.createHashMap("pid.keyword~" + pid));
				queryParamDataParam.setLimit(1);
				List<Map<String, Object>> queryList = Esutil.queryList(queryParamDataParam);
				if (queryList !=null && queryList.size()>0) {
					Map<String, Object> map = queryList.get(0);
					String logversion = map.get("logversion").toString();
					String rootSupport = map.get("rootSupport").toString();
					
					String dualSimSupport = map.get("dualSimSupport").toString();
					String operatorCompareSupport = map.get("operatorCompareSupport").toString();
					
					Map<String, Object> wxzbBeanMap=new LinkedHashMap<>();//5g无限指标
					Map<String, Object> wxzbBeanMap4g=new LinkedHashMap<>();//4g无限指标
					Map<String, Object> ftpzbBeanMap=new LinkedHashMap<>();//ftp指标
					Map<String, Object> httpzbBeanMap=new LinkedHashMap<>();//http指标
					Map<String, Object> pingBeanMap=new LinkedHashMap<>();//http指标
					Map<String, Object> yyBeanMap=new LinkedHashMap<>();//http指标

					
					
					LogFoot signalDataBean = JSONObject.parseObject(finalLogData, LogFoot.class);
					//http指标
					FootHttpzbBean httpzbBean = signalDataBean.getHttpzbBean();
					if (httpzbBean !=null) {
						httpzbBeanMap.put("请求次数", httpzbBean.getQqcs());
						httpzbBeanMap.put("成功次数", httpzbBean.getCgcs());
						httpzbBeanMap.put("成功率", httpzbBean.getCgl());
						httpzbBeanMap.put("最大时延（ms）", httpzbBean.getZdsy());
						httpzbBeanMap.put("最小时延（ms）", httpzbBean.getZxsy());
						httpzbBeanMap.put("平均时延（ms）", httpzbBean.getPjsy());
					}
					//ping指标
					FootPingzbBean pingzbBean = signalDataBean.getPingzbBean();
					if (pingzbBean !=null) {
						pingBeanMap.put("请求次数", pingzbBean.getQqcs());
						pingBeanMap.put("成功次数", pingzbBean.getCgcs());
						pingBeanMap.put("成功率", pingzbBean.getCgl());
						pingBeanMap.put("最大时延（ms）", pingzbBean.getZdsy());
						pingBeanMap.put("最小时延（ms）", pingzbBean.getZxsy());
						pingBeanMap.put("平均时延（ms）", pingzbBean.getPjsy());
					}
					//语音指标
					FootYyzbBean yyzbBean = signalDataBean.getYyzbBean();
					if (yyzbBean !=null) {
						yyBeanMap.put("主叫次数", yyzbBean.getZjcs());
						yyBeanMap.put("被叫次数", yyzbBean.getBjcs());
						yyBeanMap.put("接通次数", yyzbBean.getJtcs());
						yyBeanMap.put("接通率", yyzbBean.getJtl());
						yyBeanMap.put("掉话次数", yyzbBean.getDhcs());
						yyBeanMap.put("掉话率", yyzbBean.getDhl());
						yyBeanMap.put("呼叫时延最大（s）", yyzbBean.getHjsyd());
						yyBeanMap.put("呼叫时延最小（s）", yyzbBean.getHjsyx());
						yyBeanMap.put("呼叫时延平均（s）", yyzbBean.getHjsyp());
					}
					//ftp指标
					FootFtpzbBean ftpzbBean = signalDataBean.getFtpzbBean();
					if (ftpzbBean !=null) {
						ftpzbBeanMap.put("请求次数", ftpzbBean.getQqcs());
						ftpzbBeanMap.put("成功次数", ftpzbBean.getCgcs());
						ftpzbBeanMap.put("掉线次数", ftpzbBean.getDxcs());
						ftpzbBeanMap.put("掉线率（%）", ftpzbBean.getDxl());
						ftpzbBeanMap.put("下载总量（GB）", ftpzbBean.getXzcl());
						ftpzbBeanMap.put("上传总量（GB）", ftpzbBean.getSczl());
						ftpzbBeanMap.put("下载平均速率（Mbps）", ftpzbBean.getDlAvg());
						ftpzbBeanMap.put("上传平均速率（Mbps）", ftpzbBean.getDlAvg());
						ftpzbBeanMap.put("下载峰值速率（Mbps）", ftpzbBean.getDlMax());
						ftpzbBeanMap.put("上传峰值速率（Mbps）", ftpzbBean.getUlMax());
						ftpzbBeanMap.put("上传低速率占比（%）", ftpzbBean.getUlLowRatio());
						if (("1").equals(rootSupport)) {
							ftpzbBeanMap.put("MAC DL Thr Avg (Mbps)", ftpzbBean.getMacDL());
							ftpzbBeanMap.put("MAC UL Thr Avg (Mbps)", ftpzbBean.getMacUL());
							ftpzbBeanMap.put("MCS DL Avg", ftpzbBean.getMcsDL());
							ftpzbBeanMap.put("MCS UL Avg", ftpzbBean.getMcsUL());
							ftpzbBeanMap.put("DL 64QAM 占比（%）", ftpzbBean.getDl64QAM());
							ftpzbBeanMap.put("DL 256QAM 占比（%）", ftpzbBean.getDl256QAM());
							ftpzbBeanMap.put("UL 64QAM 占比（%）", ftpzbBean.getUl64QAM());
							ftpzbBeanMap.put("UL 256QAM 占比（%）", ftpzbBean.getUl256QAM());
							ftpzbBeanMap.put("Grant DL Num Avg", ftpzbBean.getGrantDLnum());
							ftpzbBeanMap.put("Grant UL Num Avg", ftpzbBean.getGrantULnum());
							ftpzbBeanMap.put("PDSCH RBs Num Avg", ftpzbBean.getPdschRBnum());
							ftpzbBeanMap.put("PUSCH RBs Num Avg", ftpzbBean.getPuschRBnum());
							ftpzbBeanMap.put("CQI Avg", ftpzbBean.getCqi());
							ftpzbBeanMap.put("Rank Indicator DL Avg", ftpzbBean.getRankDL());
						}
					}
					//无限指标相关的
					if (("1").equals(logversion)) {//5g
							//无限指标
							FootNrwxzbBean nrwxzbBean = signalDataBean.getNrwxzbBean();
							if (nrwxzbBean !=null) {
								wxzbBeanMap.put("测试起始时间", nrwxzbBean.getKssj());
								wxzbBeanMap.put("测试结束时间", nrwxzbBean.getJssj());
								wxzbBeanMap.put("测试时长（s）", nrwxzbBean.getJssj());
								wxzbBeanMap.put("NR5G采样点数", nrwxzbBean.getJssj());
								wxzbBeanMap.put("LTE锚点采样点数", nrwxzbBean.getLtecyds());
								wxzbBeanMap.put("SSB RSRP Avg", nrwxzbBean.getSsrsrp());
								wxzbBeanMap.put("SSB SINR Avg", nrwxzbBean.getSssinr());
								wxzbBeanMap.put("RSRP Avg", nrwxzbBean.getRsrp());
								wxzbBeanMap.put("SINR Avg", nrwxzbBean.getSinr());
								wxzbBeanMap.put("NR5G覆盖率(%)SSB RSRP>=-105&SSB SINR>=-3", nrwxzbBean.getNrfgl());
								wxzbBeanMap.put("LTE锚点覆盖率(%)RSRP>=-105&SINR>=-3", nrwxzbBean.getLtefgl());
								wxzbBeanMap.put("NR5G驻留时长占比（%）", nrwxzbBean.getNrzlsczb());
								if (("1").equals(rootSupport)) {
									wxzbBeanMap.put("SgNB 添加成功率（%）", nrwxzbBean.getGnbtjcgl());
									wxzbBeanMap.put("NSA 掉线率（%）", nrwxzbBean.getNsadxl());
									wxzbBeanMap.put("NSA 切换成功率（%）", nrwxzbBean.getNsaqhcgl());
								}
							}
							FootLtewxzbBean ltewxzbBean = signalDataBean.getLtewxzbBean();
							if (ltewxzbBean !=null) {
								wxzbBeanMap4g.put("rsrq最大值", ltewxzbBean.getRsrqd());
								wxzbBeanMap4g.put("rsrq最小值", ltewxzbBean.getRsrqx());
								wxzbBeanMap4g.put("rsrq平均值", ltewxzbBean.getRsrqp());
								wxzbBeanMap4g.put("rsrp最大值", ltewxzbBean.getRsrpd());
								wxzbBeanMap4g.put("rsrp最小值", ltewxzbBean.getRsrpx());
								wxzbBeanMap4g.put("rsrp平均值", ltewxzbBean.getRsrpp());
								wxzbBeanMap4g.put("sinr最大值", ltewxzbBean.getSinrd());
								wxzbBeanMap4g.put("sinr最小值", ltewxzbBean.getSinrx());
								wxzbBeanMap4g.put("sinr平均值", ltewxzbBean.getSinrp());
								wxzbBeanMap4g.put("测试时长", ltewxzbBean.getCssc());
								wxzbBeanMap4g.put("覆盖率", ltewxzbBean.getFgl());
								wxzbBeanMap4g.put("覆盖里程", ltewxzbBean.getFglc());
								wxzbBeanMap4g.put("总里程", ltewxzbBean.getZlc());
								wxzbBeanMap4g.put("里程覆盖率", ltewxzbBean.getLcfgl());
								if (("1").equals(dualSimSupport)) {//开启副卡
									wxzbBeanMap4g.put("副卡rsrq最大值", ltewxzbBean.getRsrqd_y());
									wxzbBeanMap4g.put("副卡rsrq最小值", ltewxzbBean.getRsrqx_y());
									wxzbBeanMap4g.put("副卡rsrq平均值", ltewxzbBean.getRsrqp_y());
									wxzbBeanMap4g.put("副卡rsrp最大值", ltewxzbBean.getRsrpd_y());
									wxzbBeanMap4g.put("副卡rsrp最小值", ltewxzbBean.getRsrpx_y());
									wxzbBeanMap4g.put("副卡rsrp平均值", ltewxzbBean.getRsrpp_y());
									wxzbBeanMap4g.put("副卡sinr最大值", ltewxzbBean.getSinrd_y());
									wxzbBeanMap4g.put("副卡sinr最小值", ltewxzbBean.getSinrx_y());
									wxzbBeanMap4g.put("副卡sinr平均值", ltewxzbBean.getSinrp_y());
									wxzbBeanMap4g.put("副卡测试时长", ltewxzbBean.getCssc_y());
									wxzbBeanMap4g.put("副卡覆盖率", ltewxzbBean.getFgl_y());
									wxzbBeanMap4g.put("副卡覆盖里程", ltewxzbBean.getFglc_y());
									wxzbBeanMap4g.put("副卡总里程", ltewxzbBean.getZlc_y());
									wxzbBeanMap4g.put("副卡里程覆盖率", ltewxzbBean.getLcfgl_y());
								}
								
								if (("1").equals(operatorCompareSupport)) {//开启警队测试
									wxzbBeanMap4g.put("竞对测试RSRP平均值（服务商）如CMCC,-90", ltewxzbBean.getComptestRsrp0());
									wxzbBeanMap4g.put("竞对测试RSRP平均值（竞对商）如CTC,-90", ltewxzbBean.getComptestRsrp1());
									wxzbBeanMap4g.put("竞对测试RSRP平均值（竞对商）如CUC,-90", ltewxzbBean.getComptestRsrp2());
								}
							}
							///
							
							
							
						}else {//4g
							
							//无限指标
							FootLtewxzbBean ltewxzbBean = signalDataBean.getLtewxzbBean();
							if (ltewxzbBean !=null) {
								wxzbBeanMap4g.put("rsrq最大值", ltewxzbBean.getRsrqd());
								wxzbBeanMap4g.put("rsrq最小值", ltewxzbBean.getRsrqx());
								wxzbBeanMap4g.put("rsrq平均值", ltewxzbBean.getRsrqp());
								wxzbBeanMap4g.put("rsrp最大值", ltewxzbBean.getRsrpd());
								wxzbBeanMap4g.put("rsrp最小值", ltewxzbBean.getRsrpx());
								wxzbBeanMap4g.put("rsrp平均值", ltewxzbBean.getRsrpp());
								wxzbBeanMap4g.put("sinr最大值", ltewxzbBean.getSinrd());
								wxzbBeanMap4g.put("sinr最小值", ltewxzbBean.getSinrx());
								wxzbBeanMap4g.put("sinr平均值", ltewxzbBean.getSinrp());
								wxzbBeanMap4g.put("测试时长", ltewxzbBean.getCssc());
								wxzbBeanMap4g.put("覆盖率", ltewxzbBean.getFgl());
								wxzbBeanMap4g.put("覆盖里程", ltewxzbBean.getFglc());
								wxzbBeanMap4g.put("总里程", ltewxzbBean.getZlc());
								wxzbBeanMap4g.put("里程覆盖率", ltewxzbBean.getLcfgl());
								if (("1").equals(dualSimSupport)) {//开启副卡
									wxzbBeanMap4g.put("副卡rsrq最大值", ltewxzbBean.getRsrqd_y());
									wxzbBeanMap4g.put("副卡rsrq最小值", ltewxzbBean.getRsrqx_y());
									wxzbBeanMap4g.put("副卡rsrq平均值", ltewxzbBean.getRsrqp_y());
									wxzbBeanMap4g.put("副卡rsrp最大值", ltewxzbBean.getRsrpd_y());
									wxzbBeanMap4g.put("副卡rsrp最小值", ltewxzbBean.getRsrpx_y());
									wxzbBeanMap4g.put("副卡rsrp平均值", ltewxzbBean.getRsrpp_y());
									wxzbBeanMap4g.put("副卡sinr最大值", ltewxzbBean.getSinrd_y());
									wxzbBeanMap4g.put("副卡sinr最小值", ltewxzbBean.getSinrx_y());
									wxzbBeanMap4g.put("副卡sinr平均值", ltewxzbBean.getSinrp_y());
									wxzbBeanMap4g.put("副卡测试时长", ltewxzbBean.getCssc_y());
									wxzbBeanMap4g.put("副卡覆盖率", ltewxzbBean.getFgl_y());
									wxzbBeanMap4g.put("副卡覆盖里程", ltewxzbBean.getFglc_y());
									wxzbBeanMap4g.put("副卡总里程", ltewxzbBean.getZlc_y());
									wxzbBeanMap4g.put("副卡里程覆盖率", ltewxzbBean.getLcfgl_y());
								}
								
								if (("1").equals(operatorCompareSupport)) {//开启警队测试
									wxzbBeanMap4g.put("竞对测试RSRP平均值（服务商）如CMCC,-90", ltewxzbBean.getComptestRsrp0());
									wxzbBeanMap4g.put("竞对测试RSRP平均值（竞对商）如CTC,-90", ltewxzbBean.getComptestRsrp1());
									wxzbBeanMap4g.put("竞对测试RSRP平均值（竞对商）如CUC,-90", ltewxzbBean.getComptestRsrp2());
								}
							}
							//
						}
					
					resultMap.put("wxzbBean5g",wxzbBeanMap);
					resultMap.put("wxzbBean4g",wxzbBeanMap4g);
					resultMap.put("ftpzbBean",ftpzbBeanMap);
					resultMap.put("httpzbBean",httpzbBeanMap);
					resultMap.put("pingzbBean",pingBeanMap);
					resultMap.put("yyzbbean",yyBeanMap);
				}
				
			}
			
		}
		return new JsonMsgUtil(true, "查询成功", resultMap);
	}
	
	
	
	
	
	/**
		 
		 查询的sql语句：
		 
		select count(1)
		from buss_es_log_analyze p 
		where p.pid='226267ed482643cf92300526c5155cd1'
		and p.sinr between 20 and 100;
		
		select count(1)
		from buss_es_log_analyze p 
		where p.pid='226267ed482643cf92300526c5155cd1'
		and p.rsrp between -100 and 100;
		
		select count(1)
		from buss_es_log_analyze p 
		where p.pid='226267ed482643cf92300526c5155cd1'
		and p.downLoadSpeed between -100 and 100;
		
		select count(1)
		from buss_es_log_analyze p 
		where p.pid='226267ed482643cf92300526c5155cd1'
		and p.upLoadSpeed between -100 and 100;
		
		select p.pingCgch,p.pingSbch,p.httpCgch,p.httpSbch,p.wjtcs,p.dhcs,p.zhcs
		from buss_es_log_analyze p 
		where p.uniqueRecord is not null;
		 
	 
	 */
}
