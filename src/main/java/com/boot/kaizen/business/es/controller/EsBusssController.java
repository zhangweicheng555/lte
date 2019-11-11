package com.boot.kaizen.business.es.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.boot.kaizen.business.es.model.QueryParamData;
import com.boot.kaizen.business.es.service.Esutil;
import com.boot.kaizen.util.MyUtil;
import com.boot.kaizen.util.TableResultUtil;

/**
 * ES 业务控制层
 * 
 * @author weichengz
 * @date 2019年11月5日 上午11:44:28
 */
@Controller
@RequestMapping("/es/buss")
public class EsBusssController {

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

}
