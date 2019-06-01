package com.boot.kaizen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.kaizen.dao.SysLoginServiceDao;
import com.boot.kaizen.model.log.LoginService;
import com.boot.kaizen.service.SysLoginServiceService;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:28:49
 */
@Service
public class SysLoginServiceServiceImpl implements SysLoginServiceService {

	@Autowired
	private SysLoginServiceDao sysLoginServiceDao;

	@Override
	public Long findProjByUserName(String username) {
		return sysLoginServiceDao.findProjByUserName(username);
	}

	@Override
	public void insert(LoginService loginService) {
		sysLoginServiceDao.insert(loginService);
	}

}
