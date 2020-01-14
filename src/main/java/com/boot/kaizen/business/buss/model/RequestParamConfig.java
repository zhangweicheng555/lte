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

	private String item; // RSRP 、SINR、DL、UL 这个是固定的
	private List<ItemModel> content;

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

	public void validateItem() {//RSRP 、SINR、DL、UL
		if (("RSRP").equals(item) || ("SINR").equals(item)  || ("UL").equals(item)  || ("DL").equals(item)) {
		}else {
			throw new IllegalArgumentException("配置项类型不符合要求，可选值为：RSRP、SINR、UL、DL");
		}
	}

}
