package com.boot.kaizen.business.analyze.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * highchart图表展示实体类
 * 
 * @author weichengz
 * @date 2019年2月1日 上午11:45:08
 * 
 *       { name: '小蓝', type: 'column' , data: [{"y":1}, {y:9}, {y:4,extra:'vvvv', suffix: '$'}], //这种方式可以将后台的附加信息传给前端的方式
 *       这个里面可以扩展加上很多别的数据 tooltip: {//这种方式是针对不同的数据做不同的后缀处理 公共的处理方式在上面的tooltip
 *       里面设置即可 valueSuffix: '°C' //这个是在数字的后面追加符号 valuePrefix:'',
 *       valueDecimals:0 //设置小数部分 } }
 */
public class Highcharty implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;// 图例的名字
	private List<HighchartyData> data = new ArrayList<HighchartyData>(); // y轴数据
	private String type = "column";// 类型 column line pie
	private Tooltip tooltip = new Tooltip();// 单独设置tooltip 不设置 就默认

	public Highcharty(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<HighchartyData> getData() {
		return data;
	}

	public void setData(List<HighchartyData> data) {
		this.data = data;
	}

	public Tooltip getTooltip() {
		return tooltip;
	}

	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}

	public Highcharty(String name, String type, List<HighchartyData> data, Tooltip tooltip) {
		super();
		this.name = name;
		this.type = type;
		this.data = data;
		this.tooltip = tooltip;
	}

	public Highcharty() {
		super();
	}

}
