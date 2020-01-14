package com.boot.kaizen.business.buss.model;

import java.io.Serializable;

/**
 * 配置项得实体类
 * 
 * @author weichengz
 * @date 2020年1月14日 下午4:32:39
 */
public class ItemModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private String minVal;
	private String maxVal;
	private String colorVal;

	public String getMinVal() {
		return minVal;
	}

	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}

	public String getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}

	public String getColorVal() {
		return colorVal;
	}

	public void setColorVal(String colorVal) {
		this.colorVal = colorVal;
	}

	public ItemModel(String minVal, String maxVal, String colorVal) {
		super();
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.colorVal = colorVal;
	}

	public ItemModel() {
		super();
	}

}
