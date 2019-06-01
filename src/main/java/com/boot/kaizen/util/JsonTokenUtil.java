package com.boot.kaizen.util;
import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 前端存储token
 * 
 * @author weichengz
 * @date 2018年9月2日 上午2:24:48
 */
public class JsonTokenUtil implements Serializable {

	private static final long serialVersionUID = 6314027741784310221L;

	private String token;
	/** 登陆时间戳（毫秒） */
	@JSONField(serialize=false)  
	private Long loginTime;

	/*** app使用 */
	private Integer resultCode;
	private String mMessage;
	private Long newTimestamp = System.currentTimeMillis();
	private Object dataSource;

	public JsonTokenUtil(String token, Long loginTime, Integer resultCode, String mMessage,
			Object dataSource) {
		super();
		this.token = token;
		this.loginTime = loginTime;
		this.resultCode = resultCode;
		this.mMessage = mMessage;
		this.dataSource = dataSource;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getmMessage() {
		return mMessage;
	}

	public void setmMessage(String mMessage) {
		this.mMessage = mMessage;
	}

	public Long getNewTimestamp() {
		return newTimestamp;
	}

	public void setNewTimestamp(Long newTimestamp) {
		this.newTimestamp = newTimestamp;
	}

	public Object getDataSource() {
		return dataSource;
	}

	public void setDataSource(Object dataSource) {
		this.dataSource = dataSource;
	}

	public JsonTokenUtil(String token, Long loginTime) {
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
