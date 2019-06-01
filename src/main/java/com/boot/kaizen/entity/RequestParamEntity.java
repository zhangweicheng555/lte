package com.boot.kaizen.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 接受Table请求参数的实体类
 * 
 * @author weichengz
 * @date 2018年9月9日 上午8:29:26
 */
public class RequestParamEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer page=1;
	private Integer limit=10;
	/** 其他附件参数*/
	private Map<String, Object> map = new HashMap<String, Object>();

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
}
