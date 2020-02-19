package com.boot.kaizen.util;

/**
 * json结构实体类
 * 
 * @author weichengz
 * @date 2018年9月1日 上午8:24:45
 */
public class JsonMsgUtil {

	private boolean success = false;
	/** 提示信息 */
	private String message;
	/** 相应的时间 */
	private Long time = System.currentTimeMillis();
	/** 状态码 */
	private Integer code = 200;
	/** 返回的对象 这个就是返回的存储信息的地方 */
	private Object object = null;
	private String token = "";
	public String getToken() {
		return token;
	}

	
	public JsonMsgUtil(String message, Integer code) {
		super();
		this.message = message;
		this.code = code;
	}


	public void setToken(String token) {
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public JsonMsgUtil(Integer code, String msg) {
		super();
		this.message = msg;
		this.code = code;
	}

	public JsonMsgUtil(boolean success, String msg, Object object) {
		super();
		this.success = success;
		this.message = msg;
		this.object = object;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public JsonMsgUtil() {
		super();
	}

	public JsonMsgUtil(boolean success) {
		super();
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public JsonMsgUtil(boolean success, String message, Long time, Integer code, Object object, String token) {
		super();
		this.success = success;
		this.message = message;
		this.time = time;
		this.code = code;
		this.object = object;
		this.token = token;
	}

}
