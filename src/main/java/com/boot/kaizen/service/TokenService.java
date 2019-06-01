package com.boot.kaizen.service;

import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.Token;

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

}
