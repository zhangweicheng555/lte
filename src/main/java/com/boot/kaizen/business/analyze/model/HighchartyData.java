package com.boot.kaizen.business.analyze.model;

import java.io.Serializable;

/**
 * data: [{"y":1}, {y:9}, {y:4,extra:'vvvv', suffix: '$'}],
 * 这种方式可以将后台的附加信息传给前端的方式 这个里面可以扩展加上很多别的数据 highchart y轴数据 实体类设计
 * 
 * @author weichengz
 * @date 2019年2月1日 上午11:49:04
 */
public class HighchartyData implements Serializable {

	private static final long serialVersionUID = 2936944471409913859L;

	private Long y;// y轴数据
	// private String extra;//扩展的数据

	public Long getY() {
		return y;
	}

	public void setY(Long y) {
		this.y = y;
	}

	public HighchartyData(Long y) {
		this.y = y;
	}

	public HighchartyData() {
	}

}
