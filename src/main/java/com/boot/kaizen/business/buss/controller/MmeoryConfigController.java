package com.boot.kaizen.business.buss.controller;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.boot.kaizen.business.buss.model.MemoryConfig;
import com.boot.kaizen.business.buss.service.IMemoryConfigService;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 记忆模块的配置项
 * 
 * @author weichengz
 * @date 2020年6月28日 上午10:03:17
 */
@Controller
@RequestMapping("/buss/memoryConfig")
public class MmeoryConfigController {

	@Autowired
	private IMemoryConfigService memoryConfigService;

	/**
	 * 
	 * @Description: 编辑
	 * @author weichengz
	 * @date 2020年1月14日 下午4:59:02
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JsonMsgUtil edit(MemoryConfig memoryConfig) throws JsonProcessingException {
		Integer projId = Integer.valueOf(UserUtil.getLoginUser().getProjId().toString());
		Long userId = UserUtil.getLoginUser().getId();

		if (memoryConfig != null) {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("projId", projId+"");
			paramMap.put("userId", userId+"");
			List<MemoryConfig> memoryConfigs = memoryConfigService.selectByMap(paramMap);

			MemoryConfig data = null;
			if (memoryConfigs != null && memoryConfigs.size() > 0) {
				data = memoryConfigs.get(0);
				memoryConfig.setId(data.getId());
			} else {
				memoryConfig.setCreateTime(new Date());
				memoryConfig.setProjId(projId+"");
				memoryConfig.setUserId(userId+"");
			}
			memoryConfigService.insertOrUpdate(memoryConfig);
		} else {
			return new JsonMsgUtil(false, "编辑失败:请求体接收参数为空", "");
		}
		return new JsonMsgUtil(true, "编辑成功", "");

	}

	/**
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2020年1月14日 下午4:59:17
	 */
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public JsonMsgUtil query() {

		Integer projId = Integer.valueOf(UserUtil.getLoginUser().getProjId().toString());
		Long userId = UserUtil.getLoginUser().getId();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("projId", projId+"");
		paramMap.put("userId", userId+"");
		List<MemoryConfig> memoryConfigs = memoryConfigService.selectByMap(paramMap);
		MemoryConfig memoryConfig=null;
		if (memoryConfigs !=null && memoryConfigs.size()>0) {
			memoryConfig=memoryConfigs.get(0);
		}else {
			memoryConfig=new MemoryConfig(projId+"", userId+"", new Date(), "1", "1", "0", "1", "1", "1", "1", "1", "1", "3", "1","0");
		}
		return new JsonMsgUtil(true, "查询成功", memoryConfig);
	}

}
