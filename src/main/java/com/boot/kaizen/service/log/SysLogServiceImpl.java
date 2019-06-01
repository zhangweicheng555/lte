package com.boot.kaizen.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.kaizen.dao.log.SysLoginLogDao;
import com.boot.kaizen.model.log.LoginLog;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:30:44
 */
@Service
class SysLogServiceImpl implements ISysLogService {

	@Autowired
	private SysLoginLogDao loginLogDao;

	@Override
	public void save(LoginLog log) {
		loginLogDao.save(log);
	}

}
