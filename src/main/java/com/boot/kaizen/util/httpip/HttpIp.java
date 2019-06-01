package com.boot.kaizen.util.httpip;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:38:17
 */
public class HttpIp {

	private Integer code;
	private HttpIpDataInfo data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public HttpIpDataInfo getData() {
		return data;
	}

	public void setData(HttpIpDataInfo data) {
		this.data = data;
	}

	public HttpIp(Integer code, HttpIpDataInfo data) {
		super();
		this.code = code;
		this.data = data;
	}

	public HttpIp() {
		super();
	}

}
