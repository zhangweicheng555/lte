package com.boot.kaizen.business.es.model.fiveg.model;

import java.io.Serializable;

/**
 * 语音指标【对象】
 * 
 * @author weichengz
 * @date 2020年4月26日 上午10:11:26
 */
public class FootYyzbBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String zjcs;// 主叫次数
	private String bjcs;// 被叫次数
	private String jtcs;// 接通次数
	private String jtl;// 接通率
	private String dhcs;// 掉话次数
	private String dhl;// 掉话率
	private String hjsyd;// 呼叫时延最大（s）
	private String hjsyx;// 呼叫时延最小（s）
	private String hjsyp;// 呼叫时延平均（s）

	public String getZjcs() {
		return zjcs;
	}

	public void setZjcs(String zjcs) {
		this.zjcs = zjcs;
	}

	public String getBjcs() {
		return bjcs;
	}

	public void setBjcs(String bjcs) {
		this.bjcs = bjcs;
	}

	public String getJtcs() {
		return jtcs;
	}

	public void setJtcs(String jtcs) {
		this.jtcs = jtcs;
	}

	public String getJtl() {
		return jtl;
	}

	public void setJtl(String jtl) {
		this.jtl = jtl;
	}

	public String getDhcs() {
		return dhcs;
	}

	public void setDhcs(String dhcs) {
		this.dhcs = dhcs;
	}

	public String getDhl() {
		return dhl;
	}

	public void setDhl(String dhl) {
		this.dhl = dhl;
	}

	public String getHjsyd() {
		return hjsyd;
	}

	public void setHjsyd(String hjsyd) {
		this.hjsyd = hjsyd;
	}

	public String getHjsyx() {
		return hjsyx;
	}

	public void setHjsyx(String hjsyx) {
		this.hjsyx = hjsyx;
	}

	public String getHjsyp() {
		return hjsyp;
	}

	public void setHjsyp(String hjsyp) {
		this.hjsyp = hjsyp;
	}

	public FootYyzbBean(String zjcs, String bjcs, String jtcs, String jtl, String dhcs, String dhl, String hjsyd,
			String hjsyx, String hjsyp) {
		super();
		this.zjcs = zjcs;
		this.bjcs = bjcs;
		this.jtcs = jtcs;
		this.jtl = jtl;
		this.dhcs = dhcs;
		this.dhl = dhl;
		this.hjsyd = hjsyd;
		this.hjsyx = hjsyx;
		this.hjsyp = hjsyp;
	}

	public FootYyzbBean() {
		super();
	}

}
