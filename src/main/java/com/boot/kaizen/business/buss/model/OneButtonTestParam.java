package com.boot.kaizen.business.buss.model;

import java.io.Serializable;
/**
 * 接收app上传的一键测试数据的实体类
 * @author weichengz
 * @date 2020年2月19日 下午8:41:11
 */
import java.util.List;

import com.boot.kaizen.business.es.model.OneButtonTest;

public class OneButtonTestParam implements Serializable {

	private static final long serialVersionUID = 1L;

	private String projId;
	private List<OneButtonTest> datas;

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public List<OneButtonTest> getDatas() {
		return datas;
	}

	public void setDatas(List<OneButtonTest> datas) {
		this.datas = datas;
	}

	public OneButtonTestParam(String projId, List<OneButtonTest> datas) {
		super();
		this.projId = projId;
		this.datas = datas;
	}

	public OneButtonTestParam() {
		super();
	}

}
