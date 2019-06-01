package com.boot.kaizen.util;

/**
 * table响应实体类
 * 
 * @author weichengz
 * @date 2018年10月6日 下午5:38:33
 */
public class TableResultUtil {
	/**0正常*/
	private Long code = 0L;
	private String msg;
	private Long count;
	private Object data;

	public TableResultUtil() {
	}

	public TableResultUtil(Long code, String msg, Long count, Object data) {
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.data = data;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
