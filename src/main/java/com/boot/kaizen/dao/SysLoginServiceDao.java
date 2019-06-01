package com.boot.kaizen.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.kaizen.model.log.LoginService;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */

@Mapper
public interface SysLoginServiceDao {

	
	Long findProjByUserName(@Param("username") String username);
	
	void insert(LoginService loginService);

}
