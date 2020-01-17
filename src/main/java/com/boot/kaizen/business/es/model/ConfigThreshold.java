package com.boot.kaizen.business.es.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.boot.kaizen.config.SuperEntity;

/**
 * 阈值配置
 * 
 * @author weichengz
 * @date 2019年10月21日 下午1:48:05
 */
@TableName("config_threshold")
public class ConfigThreshold extends SuperEntity<ConfigThreshold> {

	private static final long serialVersionUID = 1L;
	private String color; // 颜色
	private Double bigValue;// 最大值
	private Double minValue;// 最小值
	private String itemName;// 阈值类型
	/** common */
	private Integer projId;
	private Date createTime;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getBigValue() {
		return bigValue;
	}

	public void setBigValue(Double bigValue) {
		this.bigValue = bigValue;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getProjId() {
		return projId;
	}

	public void setProjId(Integer projId) {
		this.projId = projId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public ConfigThreshold(String color, Double bigValue, Double minValue, String itemName, Integer projId,
			Date createTime) {
		super();
		this.color = color;
		this.bigValue = bigValue;
		this.minValue = minValue;
		this.itemName = itemName;
		this.projId = projId;
		this.createTime = createTime;
	}

	public ConfigThreshold() {
		super();
	}

}
