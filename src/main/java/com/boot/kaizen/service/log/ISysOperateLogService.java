package com.boot.kaizen.service.log;

import com.boot.kaizen.model.log.OperateLog;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:30:40
 */
public interface ISysOperateLogService {

	/**
	 * 
	* @Description: 操作日志信息保存
	* @author weichengz
	* @date 2018年11月25日 上午2:54:28
	 */
	public void save(OperateLog operateLog);
}
