package com.boot.kaizen.service.log;

import com.boot.kaizen.model.log.LoginLog;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:30:36
 */
public interface ISysLogService {

	/**
	 * 
	 * @Description: 保存信息
	 * @author weichengz
	 * @date 2018年11月25日 上午1:49:00
	 */
	public void save(LoginLog log);
}
