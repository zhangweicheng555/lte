package com.boot.kaizen.business.analyze.service;

import com.boot.kaizen.util.JsonMsgUtil;

public interface IAnalyzeService {

	/**
	 * @Description: lte单验 统计 总数 完成的数量
	 * @author weichengz
	 * @date 2019年2月1日 下午12:49:35
	 */
	public JsonMsgUtil analyzeLteAllAndComplete(String beginTime, String endTime, String type, Long projId);

	/**
	 * @Description: lte单验 统计 总数 完成的数量 按人
	 * @author weichengz
	 * @date 2019年2月2日 上午8:40:14
	 */
	public JsonMsgUtil analyzeLteAllAndCompleteByPerson(String beginTime, String endTime, Long projId);



}
