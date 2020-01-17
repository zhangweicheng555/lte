package com.boot.kaizen.business.buss.controller;
import java.util.Arrays;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.boot.kaizen.business.buss.model.LogAnaLyze;
import com.boot.kaizen.business.buss.service.ILogAnaLyzeService;
import com.boot.kaizen.util.JsonMsgUtil;

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
}
