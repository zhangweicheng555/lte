package com.boot.kaizen.business.es.model.fiveg.model;

import java.io.Serializable;

/**
 * 5G无线指标[对象]
 * 
 * @author weichengz
 * @date 2020年4月26日 上午10:01:25
 */
public class FootNrwxzbBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String kssj; // 测试起始时间
	private String jssj; // 测试结束时间
	private String cesc; // 测试时长（s）
	private String nrcyds; // NR5G采样点数
	private String ltecyds; // LTE锚点采样点数
	private String ssrsrp; // SSB RSRP Avg
	private String sssinr; // SSB SINR Avg
	private String rsrp; // RSRP Avg
	private String sinr; // SINR Avg
	private String nrfgl;// NR5G覆盖率 （%）SSB RSRP>=-105 & SSB SINR>=-3
	private String ltefgl; // LTE锚点覆盖率（%）RSRP>=-105 & SINR>=-3
	private String nrzlsczb; // NR5G驻留时长占比（%）
	private String gnbtjcgl; // SgNB 添加成功率（%）
	private String nsadxl; // NSA 掉线率（%）
	private String nsaqhcgl;// NSA 切换成功率（%）

	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}

	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	public String getCesc() {
		return cesc;
	}

	public void setCesc(String cesc) {
		this.cesc = cesc;
	}

	public String getNrcyds() {
		return nrcyds;
	}

	public void setNrcyds(String nrcyds) {
		this.nrcyds = nrcyds;
	}

	public String getLtecyds() {
		return ltecyds;
	}

	public void setLtecyds(String ltecyds) {
		this.ltecyds = ltecyds;
	}

	public String getSsrsrp() {
		return ssrsrp;
	}

	public void setSsrsrp(String ssrsrp) {
		this.ssrsrp = ssrsrp;
	}

	public String getSssinr() {
		return sssinr;
	}

	public void setSssinr(String sssinr) {
		this.sssinr = sssinr;
	}

	public String getRsrp() {
		return rsrp;
	}

	public void setRsrp(String rsrp) {
		this.rsrp = rsrp;
	}

	public String getSinr() {
		return sinr;
	}

	public void setSinr(String sinr) {
		this.sinr = sinr;
	}

	public String getNrfgl() {
		return nrfgl;
	}

	public void setNrfgl(String nrfgl) {
		this.nrfgl = nrfgl;
	}

	public String getLtefgl() {
		return ltefgl;
	}

	public void setLtefgl(String ltefgl) {
		this.ltefgl = ltefgl;
	}

	public String getNrzlsczb() {
		return nrzlsczb;
	}

	public void setNrzlsczb(String nrzlsczb) {
		this.nrzlsczb = nrzlsczb;
	}

	public String getGnbtjcgl() {
		return gnbtjcgl;
	}

	public void setGnbtjcgl(String gnbtjcgl) {
		this.gnbtjcgl = gnbtjcgl;
	}

	public String getNsadxl() {
		return nsadxl;
	}

	public void setNsadxl(String nsadxl) {
		this.nsadxl = nsadxl;
	}

	public String getNsaqhcgl() {
		return nsaqhcgl;
	}

	public void setNsaqhcgl(String nsaqhcgl) {
		this.nsaqhcgl = nsaqhcgl;
	}

	public FootNrwxzbBean(String kssj, String jssj, String cesc, String nrcyds, String ltecyds, String ssrsrp,
			String sssinr, String rsrp, String sinr, String nrfgl, String ltefgl, String nrzlsczb, String gnbtjcgl,
			String nsadxl, String nsaqhcgl) {
		super();
		this.kssj = kssj;
		this.jssj = jssj;
		this.cesc = cesc;
		this.nrcyds = nrcyds;
		this.ltecyds = ltecyds;
		this.ssrsrp = ssrsrp;
		this.sssinr = sssinr;
		this.rsrp = rsrp;
		this.sinr = sinr;
		this.nrfgl = nrfgl;
		this.ltefgl = ltefgl;
		this.nrzlsczb = nrzlsczb;
		this.gnbtjcgl = gnbtjcgl;
		this.nsadxl = nsadxl;
		this.nsaqhcgl = nsaqhcgl;
	}

	public FootNrwxzbBean() {
		super();
	}

}
