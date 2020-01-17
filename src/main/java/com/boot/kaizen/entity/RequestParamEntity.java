package com.boot.kaizen.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private Map<String, Object> mapLike = new HashMap<String, Object>();/**模糊匹配的字段*/
	private Map<String, Object> mapAnd = new HashMap<String, Object>();/**非模糊匹配的字段*/
	private Map<String, Object> mapNo = new HashMap<String, Object>();/**匹配不等于的字段*/
	private List<String> orders = new ArrayList<String>();/**排序字段多个按顺序*/
	private Map<String, Map<String, Object>> mapRange = new HashMap<>();/**  仅仅支持 LTE  GTE  范围排序的字段   key  是数据库字段   key是符号 va是值*/
	
	
	public Integer getPage() {
		return page;
	}

	
	public RequestParamEntity() {
		super();
	}


	public RequestParamEntity(Integer page, Integer limit, Map<String, Object> map, Map<String, Object> mapLike,
			Map<String, Object> mapAnd, Map<String, Object> mapNo, List<String> orders,
			Map<String, Map<String, Object>> mapRange) {
		super();
		this.page = page;
		this.limit = limit;
		this.map = map;
		this.mapLike = mapLike;
		this.mapAnd = mapAnd;
		this.mapNo = mapNo;
		this.orders = orders;
		this.mapRange = mapRange;
	}

	public Map<String, Object> getMapLike() {
		return mapLike;
	}

	public void setMapLike(Map<String, Object> mapLike) {
		this.mapLike = mapLike;
	}

	public Map<String, Object> getMapAnd() {
		return mapAnd;
	}

	public void setMapAnd(Map<String, Object> mapAnd) {
		this.mapAnd = mapAnd;
	}

	public Map<String, Object> getMapNo() {
		return mapNo;
	}

	public void setMapNo(Map<String, Object> mapNo) {
		this.mapNo = mapNo;
	}

	public List<String> getOrders() {
		return orders;
	}

	public void setOrders(List<String> orders) {
		this.orders = orders;
	}

	public Map<String, Map<String, Object>> getMapRange() {
		return mapRange;
	}

	public void setMapRange(Map<String, Map<String, Object>> mapRange) {
		this.mapRange = mapRange;
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
