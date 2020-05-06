package com.boot.kaizen.business.es.model.fiveg;

import java.io.Serializable;
/**
 * 5Glog  头信息
 * @author weichengz
 * @date 2020年4月26日 上午9:45:51
 */
public class LogHeader implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer logversion;//0  LTE   ; 1 NR5G NSA
	private Integer dualSimSupport;  //0: Single SIM ； 1: Dual SIM
	private Integer operatorCompareSupport;  //0: 非运营商竟对测试 ； 1: 运营商竟对测试
	private Integer  rootSupport;  //0: None Root ； 1: Root
	private String  phone;  //手机型号
	private String  operator;  //运营商（主卡）
	
	
	
	
	
	
	public Integer getLogversion() {
		return logversion;
	}
	public void setLogversion(Integer logversion) {
		this.logversion = logversion;
	}
	public Integer getDualSimSupport() {
		return dualSimSupport;
	}
	public void setDualSimSupport(Integer dualSimSupport) {
		this.dualSimSupport = dualSimSupport;
	}
	public Integer getOperatorCompareSupport() {
		return operatorCompareSupport;
	}
	public void setOperatorCompareSupport(Integer operatorCompareSupport) {
		this.operatorCompareSupport = operatorCompareSupport;
	}
	public Integer getRootSupport() {
		return rootSupport;
	}
	public void setRootSupport(Integer rootSupport) {
		this.rootSupport = rootSupport;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public LogHeader(Integer logversion, Integer dualSimSupport, Integer operatorCompareSupport, Integer rootSupport,
			String phone, String operator) {
		super();
		this.logversion = logversion;
		this.dualSimSupport = dualSimSupport;
		this.operatorCompareSupport = operatorCompareSupport;
		this.rootSupport = rootSupport;
		this.phone = phone;
		this.operator = operator;
	}
	public LogHeader() {
		super();
	}
	

	
	
	
	
}
