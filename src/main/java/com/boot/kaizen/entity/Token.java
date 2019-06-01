package com.boot.kaizen.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 前端存储token
 * @author weichengz
 * @date 2018年9月2日 上午2:24:48
 */
public class Token implements Serializable {

	private static final long serialVersionUID = 6314027741784310221L;

	private String token;
	/** 登陆时间戳（毫秒） */
	@JSONField(serialize=false)  
	private Long loginTime;

	public Token(String token, Long loginTime) {
		super();
		this.token = token;
		this.loginTime = loginTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

}
