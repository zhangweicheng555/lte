package com.boot.kaizen.business.buss.model;

import java.io.Serializable;
import java.util.List;

/**
 * 接口配置值得实体类
 * 
 * @author weichengz
 * @date 2020年1月14日 下午4:29:34
 */
public class RequestParamConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private String item; // RSRP 、SINR、DL、UL 、SSRSRP、SSSINR这个是固定的
	private String type; // 阈值配置的类型 0：4g的配置 1：5g的配置

	private String showFlag ;// 显示 默认是1 1是显示
	private String pointDetail ;// 采样点详情 默认是1 1是显示
	private String itemType ;// 阈值最后的类型
	
	
	private List<ItemModel> content;

	
	
	
	public RequestParamConfig(String item, String type, String showFlag, String pointDetail, String itemType,
			List<ItemModel> content) {
		super();
		this.item = item;
		this.type = type;
		this.showFlag = showFlag;
		this.pointDetail = pointDetail;
		this.itemType = itemType;
		this.content = content;
	}


	public String getItemType() {
		return itemType;
	}


	public void setItemType(String itemType) {
		this.itemType = itemType;
	}


	public RequestParamConfig(String item, String type, String showFlag, String pointDetail, List<ItemModel> content) {
		super();
		this.item = item;
		this.type = type;
		this.showFlag = showFlag;
		this.pointDetail = pointDetail;
		this.content = content;
	}
	

	public String getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}

	public String getPointDetail() {
		return pointDetail;
	}

	public void setPointDetail(String pointDetail) {
		this.pointDetail = pointDetail;
	}

	public RequestParamConfig(String item, String type, List<ItemModel> content) {
		super();
		this.item = item;
		this.type = type;
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public List<ItemModel> getContent() {
		return content;
	}

	public void setContent(List<ItemModel> content) {
		this.content = content;
	}

	public RequestParamConfig(String item, List<ItemModel> content) {
		super();
		this.item = item;
		this.content = content;
	}

	public RequestParamConfig() {
		super();
	}

	public void validateItem() {// RSRP 、SINR、DL、UL
		if (("RSRP").equals(item) || ("SINR").equals(item) || ("UL").equals(item) || ("DL").equals(item)
				|| ("SSRSRP").equals(item) || ("SSSINR").equals(item)) {
		} else {
			throw new IllegalArgumentException("配置项类型不符合要求，可选值为：RSRP、SINR、UL、DL、SSRSRP、SSSINR");
		}
	}

}
