package com.boot.kaizen.business.buss.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 接口配置值得实体类
 * 
 * @author weichengz
 * @date 2020年1月14日 下午4:29:34
 */
public class RequestParamConfigEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String item; // RSRP 、SINR、DL、UL 这个是固定的
	private List<ItemModelEntity> content;
	
	public String getItem() {
		return item;
	}

	
	public RequestParamConfigEntity(String item) {
		super();
		this.item = item;
	}

	

	public void setItem(String item) {
		this.item = item;
	}

	public List<ItemModelEntity> getContent() {
		return content;
	}

	public void setContent(List<ItemModelEntity> content) {
		this.content = content;
	}

	public RequestParamConfigEntity(String item, List<ItemModelEntity> content) {
		super();
		this.item = item;
		this.content = content;
	}

	public RequestParamConfigEntity() {
		super();
	}

	public void validateItem() {//RSRP 、SINR、DL、UL
		if (("RSRP").equals(item) || ("SINR").equals(item)  || ("UL").equals(item)  || ("DL").equals(item)) {
		}else {
			throw new IllegalArgumentException("配置项类型不符合要求，可选值为：RSRP、SINR、UL、DL");
		}
	}

}
