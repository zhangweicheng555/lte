package com.boot.kaizen.business.buss.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.kaizen.business.buss.entity.ItemModelEntity;
import com.boot.kaizen.business.buss.entity.RequestParamConfigEntity;
import com.boot.kaizen.business.buss.model.ItemModel;
import com.boot.kaizen.business.buss.model.MemoryConfig;
import com.boot.kaizen.business.buss.model.RequestParamConfig;
import com.boot.kaizen.business.buss.model.TestConfig;
import com.boot.kaizen.business.buss.service.ILogAnaLyzeService;
import com.boot.kaizen.business.buss.service.IMemoryConfigService;
import com.boot.kaizen.business.buss.service.ITestConfigService;
import com.boot.kaizen.entity.RequestParamEntity;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.MyUtil;
import com.boot.kaizen.util.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 测试配置项
 * 
 * @author weichengz
 * @date 2020年1月14日 下午4:37:15
 */
@Controller
@RequestMapping("/buss/testConfig")
public class TestConfigController {

	
	
	@Autowired
	private ITestConfigService testConfigService;
	@Autowired
	private IMemoryConfigService memoryConfigService;
	@Autowired
	private ILogAnaLyzeService logAnaLyzeService;
	
	
	/**
	 * 支持4G，5g的阈值配置   默认编辑4G的
	 * @Description: 编辑
	 * @author weichengz
	 * @date 2020年1月14日 下午4:59:02
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JsonMsgUtil edit(@RequestBody List<RequestParamConfig> requestParamConfigs) throws JsonProcessingException {
		Integer projId = Integer.valueOf(UserUtil.getLoginUser().getProjId().toString());
		Long userId = UserUtil.getLoginUser().getId();
		
		if (requestParamConfigs != null && requestParamConfigs.size() > 0) {
			for (RequestParamConfig requestParamConfig : requestParamConfigs) {
				if (requestParamConfig != null) {
					requestParamConfig.validateItem();
					String jsonStr = new ObjectMapper().writeValueAsString(requestParamConfig);
					
					Map<String, Object> paramMap = new HashMap<>();
					paramMap.put("projId", projId);
					paramMap.put("item", requestParamConfig.getItem());
					String type = requestParamConfig.getType();
					if (StringUtils.isBlank(type)) {//如果没有传递就采用4G的配置项
						type="0";
					}
					paramMap.put("configType", type);
					List<TestConfig> testConfigs = testConfigService.selectByMap(paramMap);

					TestConfig data = null;
					if (testConfigs != null && testConfigs.size() > 0) {
						data = testConfigs.get(0);
						data.setJsonStr(jsonStr);
					} else {
						data = new TestConfig(projId+"", new Date(), requestParamConfig.getItem(), jsonStr,type);
					}
					if (data != null) {
						testConfigService.insertOrUpdate(data);
					}
					
					
					//保存记忆模块
					Map<String, Object> paramMapMem = new HashMap<>();
					paramMapMem.put("projId", projId+"");
					paramMapMem.put("userId", userId+"");
					List<MemoryConfig> memoryConfigs = memoryConfigService.selectByMap(paramMapMem);
					MemoryConfig memoryConfig=null;
					if (memoryConfigs !=null && memoryConfigs.size()>0) {
						memoryConfig=memoryConfigs.get(0);
					}else {
						memoryConfig=new MemoryConfig();
						memoryConfig.setCreateTime(new Date());
						memoryConfig.setProjId(projId+"");
						memoryConfig.setUserId(userId+"");
					}
					if (memoryConfig !=null) {
						String item = requestParamConfig.getItem();
						String pointDetail=requestParamConfig.getPointDetail()==null?"1":requestParamConfig.getPointDetail();
						String showFlag=requestParamConfig.getShowFlag()==null?"1":requestParamConfig.getShowFlag();
						memoryConfig.setPointDetail(pointDetail);
						memoryConfig.setShowFlag(showFlag);
						memoryConfig.setItemType(item);
						memoryConfigService.insertOrUpdate(memoryConfig);
					}
					
				} else {
					return new JsonMsgUtil(false, "编辑失败:请求体接收参数为空", "");
				}
			}
			return new JsonMsgUtil(true, "编辑成功", "");
		} else {
			return new JsonMsgUtil(true, "编辑失败，保存数据为空", "");
		}
	}

	

	/**
	 * 查询   默认查询的是4G的阈值配置
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年1月14日 下午4:59:17
	 */
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public JsonMsgUtil queryByItem(@RequestParam(value = "item", required = false) String item,@RequestParam(value = "type", required = false) String type,@RequestParam(value = "pid", required = false) String pid) {
		if (StringUtils.isBlank(pid)) {
			throw new IllegalArgumentException("室外测试列表的pid为空");
		}
		
		Integer projId = Integer.valueOf(UserUtil.getLoginUser().getProjId().toString());
		Long userId = UserUtil.getLoginUser().getId();
		List<RequestParamConfig> datasConfigs = testConfigService.queryItemAll(item,projId,type);
		if (datasConfigs ==null || datasConfigs.size()==0) {
			throw new IllegalArgumentException("该项目下测试配置项不存在");
		}

		RequestParamEntity requestParamEntity=new RequestParamEntity();
		Map<String, Object> mapAnd = new HashMap<String, Object>();//这是精确匹配的查询条件
		mapAnd.put("pid", pid);
		requestParamEntity.setMapAnd(mapAnd);
		//查询总数
		int allNum = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
		if (allNum ==0) {
			throw new IllegalArgumentException("该项目下室外测试信息为空");
		}
		
		
		//查询记忆模块的而数据
		//保存记忆模块
		Map<String, Object> paramMapMem = new HashMap<>();
		paramMapMem.put("projId", projId);
		paramMapMem.put("userId", userId+"");
		List<MemoryConfig> memoryConfigs = memoryConfigService.selectByMap(paramMapMem);
		String showFlag="1";
		String pointDetail="1";
		String itemType="RSRP";//以上设置默认的值
		
		if (memoryConfigs !=null && memoryConfigs.size()>0) {
			MemoryConfig memoryConfig=memoryConfigs.get(0);
			String showFlag2 = memoryConfig.getShowFlag();
			String pointDetail2 = memoryConfig.getPointDetail();
			String itemType2 = memoryConfig.getItemType();
			if (StringUtils.isNotBlank(showFlag2)) {
				showFlag=showFlag2;
			}
			if (StringUtils.isNotBlank(pointDetail2)) {
				pointDetail=pointDetail2;
			}
			if (StringUtils.isNotBlank(itemType2)) {
				itemType=itemType2;
			}
		}
		
		
		List<RequestParamConfigEntity> requestParamConfigEntities=new ArrayList<>();
		//这是无限指标的  统计rsrp  sinr在图里范围内的数量
		for (RequestParamConfig requestParamConfig : datasConfigs) {
			Map<String, Object> mapBetween = new HashMap<String, Object>();//查询rsrp的范围
			String item2 = requestParamConfig.getItem();
			List<ItemModel> content = requestParamConfig.getContent();
			if (("RSRP").equals(item2)) {
				RequestParamConfigEntity model=new RequestParamConfigEntity(item2);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("rsrp", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal(),MyUtil.numPercent(selectCount, allNum)));
				}
				model.setContent(itemModelEntities);
				model.setItemType(itemType);
				model.setPointDetail(pointDetail);
				model.setShowFlag(showFlag);
				requestParamConfigEntities.add(model);
			}
			if (("SSRSRP").equals(item2)) {
				RequestParamConfigEntity model=new RequestParamConfigEntity(item2);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("ssrsrp", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal(),MyUtil.numPercent(selectCount, allNum)));
				}
				model.setContent(itemModelEntities);
				model.setItemType(itemType);
				model.setPointDetail(pointDetail);
				model.setShowFlag(showFlag);
				requestParamConfigEntities.add(model);
			}
			if (("SINR").equals(item2)) {
				RequestParamConfigEntity model=new RequestParamConfigEntity(item2);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("sinr", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal(),MyUtil.numPercent(selectCount, allNum)));
				}
				model.setContent(itemModelEntities);
				model.setItemType(itemType);
				model.setPointDetail(pointDetail);
				model.setShowFlag(showFlag);
				requestParamConfigEntities.add(model);
			}
			if (("SSSINR").equals(item2)) {
				RequestParamConfigEntity model=new RequestParamConfigEntity(item2);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("sssinr", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal(),MyUtil.numPercent(selectCount, allNum)));
				}
				model.setContent(itemModelEntities);
				model.setItemType(itemType);
				model.setPointDetail(pointDetail);
				model.setShowFlag(showFlag);
				requestParamConfigEntities.add(model);
			}
			
			if (("UL").equals(item2)) {
				RequestParamConfigEntity model=new RequestParamConfigEntity(item2);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("upLoadSpeed", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal(),MyUtil.numPercent(selectCount, allNum)));
				}
				model.setContent(itemModelEntities);
				model.setItemType(itemType);
				model.setPointDetail(pointDetail);
				model.setShowFlag(showFlag);
				requestParamConfigEntities.add(model);
			}
			if (("DL").equals(item2)) {
				RequestParamConfigEntity model=new RequestParamConfigEntity(item2);
				List<ItemModelEntity> itemModelEntities=new ArrayList<>();
				for (ItemModel itemModel : content) {
					mapBetween.put("downLoadSpeed", itemModel.getMinVal()+"_"+itemModel.getMaxVal());
					requestParamEntity.setMapBetween(mapBetween);
					int selectCount = logAnaLyzeService.selectCount(MyUtil.createQueryPlus(requestParamEntity));
					itemModelEntities.add(new ItemModelEntity(itemModel.getMinVal(), itemModel.getMaxVal(), selectCount,itemModel.getColorVal(),MyUtil.numPercent(selectCount, allNum)));
				}
				model.setContent(itemModelEntities);
				model.setItemType(itemType);
				model.setPointDetail(pointDetail);
				model.setShowFlag(showFlag);
				requestParamConfigEntities.add(model);
			}
		}
		return new JsonMsgUtil(true, "查询成功", requestParamConfigEntities);
	}
	
	
	

}
