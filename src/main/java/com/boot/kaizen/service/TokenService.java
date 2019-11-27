package com.boot.kaizen.service;

import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.Token;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:26:11
 */
public interface TokenService {

	Token saveToken(LoginUser loginUser);

	void refresh(LoginUser loginUser);

	LoginUser getLoginUser(String token);

	boolean deleteToken(String token);

	/**
	 * 检验或刷新token使用
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月3日 下午5:39:11
	 */
	JsonMsgUtil checkAndRefreshToken(String token);
}
