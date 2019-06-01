package com.boot.kaizen.business.analyze.model;

import java.io.Serializable;

/**
 * 提示框的单独设置方式
 * 
 * @author weichengz
 * @date 2019年2月1日 上午11:53:26
 */
public class Tooltip implements Serializable {

	private static final long serialVersionUID = 1L;

	private String valueSuffix = "";// 数据的前缀显示的东西
	private String valuePrefix = ""; // 数据的后缀显示的东西
	private Long valueDecimals = 0L; // 数据要保留的小数

	public String getValueSuffix() {
		return valueSuffix;
	}

	public void setValueSuffix(String valueSuffix) {
		this.valueSuffix = valueSuffix;
	}

	public String getValuePrefix() {
		return valuePrefix;
	}

	public void setValuePrefix(String valuePrefix) {
		this.valuePrefix = valuePrefix;
	}

	public Long getValueDecimals() {
		return valueDecimals;
	}

	public void setValueDecimals(Long valueDecimals) {
		this.valueDecimals = valueDecimals;
	}

	public Tooltip(String valueSuffix, String valuePrefix, Long valueDecimals) {
		super();
		this.valueSuffix = valueSuffix;
		this.valuePrefix = valuePrefix;
		this.valueDecimals = valueDecimals;
	}

	public Tooltip() {
		super();
	}

}
