package com.boot.kaizen.business.buss.entity;

import java.io.Serializable;

/**
 * 配置项得实体类
 * 
 * @author weichengz
 * @date 2020年1月14日 下午4:32:39
 */
public class ItemModelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String minVal;
	private String maxVal;
	private String colorVal;
	private Integer num;//数量
	private String numPerRatio;//百分比
	
	
	

	public String getNumPerRatio() {
		return numPerRatio;
	}

	public void setNumPerRatio(String numPerRatio) {
		this.numPerRatio = numPerRatio;
	}

	public ItemModelEntity(String minVal, String maxVal, Integer num, String colorVal, String numPerRatio) {
		super();
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.colorVal = colorVal;
		this.num = num;
		this.numPerRatio = numPerRatio;
	}

	public ItemModelEntity(String minVal, String maxVal, Integer num, String colorVal) {
		super();
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.num = num;
		this.colorVal = colorVal;
	}

	public String getColorVal() {
		return colorVal;
	}

	public void setColorVal(String colorVal) {
		this.colorVal = colorVal;
	}

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

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public ItemModelEntity(String minVal, String maxVal, Integer num) {
		super();
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.num = num;
	}

	public ItemModelEntity() {
		super();
	}

}
