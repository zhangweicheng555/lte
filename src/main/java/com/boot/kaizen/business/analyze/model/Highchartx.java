package com.boot.kaizen.business.analyze.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * highchart x轴设置集合实体类
 * 
 * @author weichengz
 * @date 2019年2月1日 下午12:04:07
 */
public class Highchartx implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> categories = new ArrayList<>();

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Highchartx(List<String> categories) {
		this.categories = categories;
	}

	public Highchartx() {
	}

}
