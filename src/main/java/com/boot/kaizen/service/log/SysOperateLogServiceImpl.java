package com.boot.kaizen.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.kaizen.dao.log.SysOperateLogDao;
import com.boot.kaizen.model.log.OperateLog;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:30:48
 */
@Service
class SysOperateLogServiceImpl implements ISysOperateLogService {

	@Autowired
	private SysOperateLogDao operateLogDao;

	@Override
	public void save(OperateLog operateLog) {
		operateLogDao.save(operateLog);
	}

}
