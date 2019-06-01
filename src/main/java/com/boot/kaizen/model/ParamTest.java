package com.boot.kaizen.model;

/**
 * 测试pagehelper用pojo接收参数的方式
 * 
 * @author weichengz
 * @date 2018年9月1日 上午11:33:18
 */
public class ParamTest {

	private Integer pageNum;
	private Integer pageSize;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public ParamTest(Integer pageNum, Integer pageSize) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	public ParamTest() {
	}

}
