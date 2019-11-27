package com.boot.kaizen.business.es.model.sim;

import java.io.Serializable;
import java.util.List;

/**
 * 查询sim工参的时候返回的数据格式
 * 
 * @author weichengz
 * @date 2019年11月27日 上午10:47:10
 */
public class ResultModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String flag;// 1成功 0失败
	private String msg;// 失败的原因
	private List<List<String>> data;// 返回的数据 data是这种格式 [[22342,’测试站点’,120,121.221,30.222,……],[……]]

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<List<String>> getData() {
		return data;
	}

	public void setData(List<List<String>> data) {
		this.data = data;
	}

	public ResultModel(String flag, String msg, List<List<String>> data) {
		super();
		this.flag = flag;
		this.msg = msg;
		this.data = data;
	}

	public ResultModel() {
		super();
	}

}
