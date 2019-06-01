package com.boot.kaizen.business.analyze.common;

import java.io.Serializable;

/**
 * highchart统计分析 映射实体类
 * 
 * @author weichengz
 * @date 2019年2月1日 下午12:27:43
 */
public class HighchartCommon implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 分类名字*/
	private String name; 
	/**总数量*/
	private Long allNum; 
	/**完成的数量*/
	private Long finishNum; 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAllNum() {
		return allNum;
	}

	public void setAllNum(Long allNum) {
		this.allNum = allNum;
	}

	public Long getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(Long finishNum) {
		this.finishNum = finishNum;
	}

	public HighchartCommon(String name, Long allNum, Long finishNum) {
		super();
		this.name = name;
		this.allNum = allNum;
		this.finishNum = finishNum;
	}

	public HighchartCommon() {
		super();
	}

}
