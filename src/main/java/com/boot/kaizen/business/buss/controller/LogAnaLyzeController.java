package com.boot.kaizen.business.buss.controller;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.boot.kaizen.business.buss.model.LogAnaLyze;
import com.boot.kaizen.business.buss.service.ILogAnaLyzeService;
import com.boot.kaizen.entity.RequestParamEntity;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.MyUtil;

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
	public JsonMsgUtil queryAnalyzeData(@RequestParam("id") String id) {
		
		RequestParamEntity requestParamEntity=new RequestParamEntity();
		Map<String, Object> mapAnd = new HashMap<String, Object>();
		mapAnd.put("pid", "4fb7419352284adf9f27d389eb8ec5d1");
		requestParamEntity.setMapAnd(mapAnd);
		
		Map<String, Object> mapBetween = new HashMap<String, Object>();
		mapBetween.put("rsrp", "-100_100");
		requestParamEntity.setMapBetween(mapBetween);
		
		int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
		System.out.println("rsrp______________"+selectCount);
		
		mapBetween.remove("rsrp");
		mapBetween.put("sinr", "20_100");
		requestParamEntity.setMapBetween(mapBetween);
		int  selectCountsinr= logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
		System.out.println("sinr______________"+selectCountsinr);
		
		requestParamEntity.setMapBetween(null);
		mapBetween.remove("sinr");
		mapBetween.put("uniqueRecord", "0");
		
		requestParamEntity.setMapBetween(new HashMap<>());
		
		requestParamEntity.setMapNo(mapBetween);
		int  selectCountsinrModel= logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
		System.out.println("查询唯一的那个数据++++"+selectCountsinrModel);
		return new JsonMsgUtil(true, "删出成功,共删出12条数据", "");
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
