package com.boot.kaizen.service;
import com.boot.kaizen.model.log.LoginService;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:26:11
 */
public interface SysLoginServiceService {

	/**
	 * 
	 * @Description: 根据登陆的用户名字加载登陆最后一次的项目id
	 * @author weichengz
	 * @date 2018年10月27日 下午7:20:01
	 */
	Long findProjByUserName(String username);

	/**
	 * 
	 * @Description: 添加登陆服务信息
	 * @author weichengz
	 * @date 2018年10月27日 下午7:20:25
	 */
	void insert(LoginService loginService);
	
	void deleteByUserName(String username);
}
