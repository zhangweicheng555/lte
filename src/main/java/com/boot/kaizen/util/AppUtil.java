package com.boot.kaizen.util;

/**
 * app 返回数据工具类
 * 
 * @author a-zhangweicheng
 *
 *         resultCode:请求状态:0:失败;1:成功; 2:服务器异常 mMessage:提示信息 newTimestamp:时间戳
 *         dataSource:用户信息数据
 */
public class AppUtil {

	private Integer resultCode;
	private String mMessage;
	private Long newTimestamp = System.currentTimeMillis();
	private Object dataSource;
	private String token;

	public AppUtil(Integer resultCode, String mMessage, Object dataSource) {
		super();
		this.resultCode = resultCode;
		this.mMessage = mMessage;
		this.dataSource = dataSource;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public AppUtil(Integer resultCode, String mMessage, Long newTimestamp, Object dataSource) {
		super();
		this.resultCode = resultCode;
		this.mMessage = mMessage;
		this.newTimestamp = newTimestamp;
		this.dataSource = dataSource;
	}

	public AppUtil() {
		super();
	}

}
