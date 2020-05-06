package com.boot.kaizen.business.es.model.fiveg.model;

import java.io.Serializable;

/**
 * ftp指标【对象】
 * 
 * @author weichengz
 * @date 2020年4月26日 上午10:07:37
 */
public class FootFtpzbBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String qqcs;// 请求次数
	private String cgcs;// 成功次数
	private String dxcs;// 掉线次数
	private String dxl;// 掉线率（%）
	private String xzcl;// 下载总量（GB）
	private String sczl;// 上传总量（GB）
	private String ulAvg;// 下载平均速率（Mbps）
	private String dlAvg;// 上传平均速率（Mbps）
	private String dlMax;// 下载峰值速率（Mbps）
	private String ulMax;// 上传峰值速率（Mbps）
	private String dlLowRatio;// 下载低速率占比（%）
	private String ulLowRatio;// 上传低速率占比（%）
	private String macDL;// MAC DL Thr Avg (Mbps)
	private String macUL;// MAC UL Thr Avg (Mbps)
	private String mcsDL;// MCS DL Avg
	private String mcsUL;// MCS UL Avg
	private String dl64QAM;// DL 64QAM 占比（%）
	private String dl256QAM;// DL 256QAM 占比（%）
	private String ul64QAM;// UL 64QAM 占比（%）
	private String ul256QAM;// UL 256QAM 占比（%）
	private String grantDLnum;// Grant DL Num Avg
	private String grantULnum;// Grant UL Num Avg
	private String pdschRBnum;// PDSCH RBs Num Avg
	private String puschRBnum;// PUSCH RBs Num Avg
	private String cqi;// CQI Avg
	private String rankDL;// Rank Indicator DL Avg

	

	public String getUlAvg() {
		return ulAvg;
	}

	public void setUlAvg(String ulAvg) {
		this.ulAvg = ulAvg;
	}

	public String getDlAvg() {
		return dlAvg;
	}

	public void setDlAvg(String dlAvg) {
		this.dlAvg = dlAvg;
	}

	public FootFtpzbBean(String qqcs, String cgcs, String dxcs, String dxl, String xzcl, String sczl, String ulAvg,
			String dlAvg, String dlMax, String ulMax, String dlLowRatio, String ulLowRatio, String macDL, String macUL,
			String mcsDL, String mcsUL, String dl64qam, String dl256qam, String ul64qam, String ul256qam,
			String grantDLnum, String grantULnum, String pdschRBnum, String puschRBnum, String cqi, String rankDL) {
		super();
		this.qqcs = qqcs;
		this.cgcs = cgcs;
		this.dxcs = dxcs;
		this.dxl = dxl;
		this.xzcl = xzcl;
		this.sczl = sczl;
		this.ulAvg = ulAvg;
		this.dlAvg = dlAvg;
		this.dlMax = dlMax;
		this.ulMax = ulMax;
		this.dlLowRatio = dlLowRatio;
		this.ulLowRatio = ulLowRatio;
		this.macDL = macDL;
		this.macUL = macUL;
		this.mcsDL = mcsDL;
		this.mcsUL = mcsUL;
		dl64QAM = dl64qam;
		dl256QAM = dl256qam;
		ul64QAM = ul64qam;
		ul256QAM = ul256qam;
		this.grantDLnum = grantDLnum;
		this.grantULnum = grantULnum;
		this.pdschRBnum = pdschRBnum;
		this.puschRBnum = puschRBnum;
		this.cqi = cqi;
		this.rankDL = rankDL;
	}

	public FootFtpzbBean() {
		super();
	}

	public String getQqcs() {
		return qqcs;
	}

	public void setQqcs(String qqcs) {
		this.qqcs = qqcs;
	}

	public String getCgcs() {
		return cgcs;
	}

	public void setCgcs(String cgcs) {
		this.cgcs = cgcs;
	}

	public String getDxcs() {
		return dxcs;
	}

	public void setDxcs(String dxcs) {
		this.dxcs = dxcs;
	}

	public String getDxl() {
		return dxl;
	}

	public void setDxl(String dxl) {
		this.dxl = dxl;
	}

	public String getXzcl() {
		return xzcl;
	}

	public void setXzcl(String xzcl) {
		this.xzcl = xzcl;
	}

	public String getSczl() {
		return sczl;
	}

	public void setSczl(String sczl) {
		this.sczl = sczl;
	}

	

	public String getDlMax() {
		return dlMax;
	}

	public void setDlMax(String dlMax) {
		this.dlMax = dlMax;
	}

	public String getUlMax() {
		return ulMax;
	}

	public void setUlMax(String ulMax) {
		this.ulMax = ulMax;
	}

	public String getDlLowRatio() {
		return dlLowRatio;
	}

	public void setDlLowRatio(String dlLowRatio) {
		this.dlLowRatio = dlLowRatio;
	}

	public String getUlLowRatio() {
		return ulLowRatio;
	}

	public void setUlLowRatio(String ulLowRatio) {
		this.ulLowRatio = ulLowRatio;
	}

	public String getMacDL() {
		return macDL;
	}

	public void setMacDL(String macDL) {
		this.macDL = macDL;
	}

	public String getMacUL() {
		return macUL;
	}

	public void setMacUL(String macUL) {
		this.macUL = macUL;
	}

	public String getMcsDL() {
		return mcsDL;
	}

	public void setMcsDL(String mcsDL) {
		this.mcsDL = mcsDL;
	}

	public String getMcsUL() {
		return mcsUL;
	}

	public void setMcsUL(String mcsUL) {
		this.mcsUL = mcsUL;
	}

	public String getDl64QAM() {
		return dl64QAM;
	}

	public void setDl64QAM(String dl64qam) {
		dl64QAM = dl64qam;
	}

	public String getDl256QAM() {
		return dl256QAM;
	}

	public void setDl256QAM(String dl256qam) {
		dl256QAM = dl256qam;
	}

	public String getUl64QAM() {
		return ul64QAM;
	}

	public void setUl64QAM(String ul64qam) {
		ul64QAM = ul64qam;
	}

	public String getUl256QAM() {
		return ul256QAM;
	}

	public void setUl256QAM(String ul256qam) {
		ul256QAM = ul256qam;
	}

	public String getGrantDLnum() {
		return grantDLnum;
	}

	public void setGrantDLnum(String grantDLnum) {
		this.grantDLnum = grantDLnum;
	}

	public String getGrantULnum() {
		return grantULnum;
	}

	public void setGrantULnum(String grantULnum) {
		this.grantULnum = grantULnum;
	}

	public String getPdschRBnum() {
		return pdschRBnum;
	}

	public void setPdschRBnum(String pdschRBnum) {
		this.pdschRBnum = pdschRBnum;
	}

	public String getPuschRBnum() {
		return puschRBnum;
	}

	public void setPuschRBnum(String puschRBnum) {
		this.puschRBnum = puschRBnum;
	}

	public String getCqi() {
		return cqi;
	}

	public void setCqi(String cqi) {
		this.cqi = cqi;
	}

	public String getRankDL() {
		return rankDL;
	}

	public void setRankDL(String rankDL) {
		this.rankDL = rankDL;
	}

}
