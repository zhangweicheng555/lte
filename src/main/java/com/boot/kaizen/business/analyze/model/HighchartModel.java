package com.boot.kaizen.business.analyze.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * highchart最后返回的实体类
 * 
 * @author weichengz
 * @date 2019年2月1日 下午12:06:31
 * 
 *       返回的实体 categories:['','',''] series:[ {}, {}, ]
 */
public class HighchartModel extends Highchartx implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Highcharty> series = new ArrayList<Highcharty>();

	@SuppressWarnings("unused")
	private Integer size;

	private String beginTime;// 开始时间
	private String endTime;// 结束时间

	public Integer getSize() {
		List<String> categories2 = this.getCategories();
		if (categories2 != null && categories2.size() > 0) {
			return categories2.size() / 8 == 0 ? 1 : categories2.size() / 8;
		}
		return 1;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<Highcharty> getSeries() {
		return series;
	}

	public void setSeries(List<Highcharty> series) {
		this.series = series;
	}

	public HighchartModel(List<String> categories, List<Highcharty> series) {
		this.series = series;
	}

	public HighchartModel(List<String> categories) {
	}

	public HighchartModel() {
	}

}
