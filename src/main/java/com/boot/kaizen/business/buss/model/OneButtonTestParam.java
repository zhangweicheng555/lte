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

	private String user;// ihandle用户账号 不建议汉字
	private String city;// 城市名字 注意不要带【市】 例如【上海 】 而不是【上海市】
	private String operators;// 运营商 【联通、电信、移动 三者其一】
	private List<OneButtonTest> datas;

	public OneButtonTestParam(String user, String city, String operators, List<OneButtonTest> datas) {
		super();
		this.user = user;
		this.city = city;
		this.operators = operators;
		this.datas = datas;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public List<OneButtonTest> getDatas() {
		return datas;
	}

	public void setDatas(List<OneButtonTest> datas) {
		this.datas = datas;
	}

	public OneButtonTestParam() {
		super();
	}

}
