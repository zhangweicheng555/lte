package com.boot.kaizen.dao.log;

import org.apache.ibatis.annotations.Mapper;

import com.boot.kaizen.model.log.LoginLog;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */
@Mapper
public interface SysLoginLogDao {

	/**
	 * @Description: 保存信息
	 * @author weichengz
	 * @date 2018年11月25日 上午1:46:59
	 */
	public void save(LoginLog loginLog);

}
