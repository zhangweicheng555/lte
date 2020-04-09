package com.boot.kaizen.business.buss.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import com.boot.kaizen.business.buss.entity.RequestParamConfigEntity;
import com.boot.kaizen.business.buss.model.ItemModel;
import com.boot.kaizen.business.buss.model.LogAnaLyze;
import com.boot.kaizen.business.buss.model.RequestParamConfig;
import com.boot.kaizen.business.buss.service.ILogAnaLyzeService;
import com.boot.kaizen.business.buss.service.ITestConfigService;
import com.boot.kaizen.business.es.model.logModel.SignalDataBean;
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
	public JsonMsgUtil queryAnalyzeData(@RequestParam("pid") String pid) {
		
		Map<String, Object> resultMap=new HashMap<>();//最终返回的结果
		
		//查询图例的配置项问题
		Integer projId = Integer.valueOf(UserUtil.getLoginUser().getProjId().toString());
		List<RequestParamConfig> datasConfigs = testConfigService.queryItemAll(null, projId);
		
		if (datasConfigs ==null || datasConfigs.size()==0) {
			throw new IllegalArgumentException("该项目下测试配置项不存在");
		}
		
		
		RequestParamEntity requestParamEntity=new RequestParamEntity();
		Map<String, Object> mapAnd = new HashMap<String, Object>();//这是精确匹配的查询条件
		mapAnd.put("pid", pid);
		requestParamEntity.setMapAnd(mapAnd);
		
		
		List<RequestParamConfigEntity> requestParamConfigEntities=new ArrayList<>();
		
		//这是无限指标的  统计rsrp  sinr在图里范围内的数量
		for (RequestParamConfig requestParamConfig : datasConfigs) {
			Map<String, Object> mapBetween = new HashMap<String, Object>();//查询rsrp的范围
			String item = requestParamConfig.getItem();
			List<ItemModel> content = requestParamConfig.getContent();
			if (("RSRP").equals(item)) {
				RequestParamConfigEntity model=new RequestParamConfigEntity(item);
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
				RequestParamConfigEntity model=new RequestParamConfigEntity(item);
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
				RequestParamConfigEntity model=new RequestParamConfigEntity(item);
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
				RequestParamConfigEntity model=new RequestParamConfigEntity(item);
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
