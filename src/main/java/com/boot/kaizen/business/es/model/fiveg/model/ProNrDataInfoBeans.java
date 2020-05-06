package com.boot.kaizen.business.es.model.fiveg.model;

import java.io.Serializable;
import java.util.List;

/**
 * 测试指标【对象】 root 5g
 * 
 * @author weichengz
 * @date 2020年4月26日 下午1:50:49
 */
public class ProNrDataInfoBeans implements Serializable {

	private static final long serialVersionUID = 1L;

	private String band;// 频段
	private String bandWidth;// 带宽
	private String frequencyPointA;// PointA频率
	private String frequencyDL;// 下行频率
	private String gscn;// 全局同步信道
	private String subCarrierSpace;// 子载波间隔
	private String pci;// PCI
	private String ssbIndex;// SSB号
	private String ssArfcn;// 频点
	private String ssRsrp;// RSRP
	private String ssSinr;// SINR
	private String ssRsrq;// RSRQ
	private String pushTxPower;// 上行共享信道发射功率
	private String pucchTxPower;// 上行控制信道发射功率
	private String srsTxPower;// 探测信号发射功率
	private String cqi;// CQI
	private String mcsUl;// 上行MCS
	private String mcsDl;// 下行MCS
	private String modUl;// 上行调制方式
	private String modDl;// 下行调制方式
	private String puschBler;// 上行误块率
	private String pdschBler;// 下行误块率
	private String grantUlNum;// 上行RB授权次数
	private String grantDLNum;// 下行RB授权次数
	private String riNumDl;// 下行秩指示
	private String pdcpUlThr;// PDCP层上行吞吐率
	private String pdcpDlThr;// PDCP层下行吞吐率
	private String rlcUlThr;// RLC层上行吞吐率
	private String rlcDlThr;// RLC层下行吞吐率
	private String macUlThr;// MAC层上行吞吐率
	private String macDlThr;// MAC层下行吞吐率
	private String puschRb;// 上行RB调度次数
	private String pdschRb;// 下行RB调度次数
	private String slotConfig;// 时隙配置
	private String qamnum16;// 16QAM 调制次数
	private String qamnum64;// 64QAM 调制次数
	private String qamnum256;// 256QAM 调制次数
	private String qamUlnum16;// 16QAM UL调制次数
	private String qamDlnum16;// 16QAM DL 调制次数
	private String qamUlnum64;// 64QAM UL 调制次数
	private String qamDlnum64;// 64QAM DL 调制次数
	private String qamUlnum256;// 256QAM UL 调制次数
	private String qamDlnum256;// 256QAM DL 调制次数
	private String qpskulnum;// QPSKUL 调制次数

	private List<ProNrNeighborhoodInfos> proNrNeighborhoodInfos;

	public List<ProNrNeighborhoodInfos> getProNrNeighborhoodInfos() {
		return proNrNeighborhoodInfos;
	}

	public void setProNrNeighborhoodInfos(List<ProNrNeighborhoodInfos> proNrNeighborhoodInfos) {
		this.proNrNeighborhoodInfos = proNrNeighborhoodInfos;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getBandWidth() {
		return bandWidth;
	}

	public void setBandWidth(String bandWidth) {
		this.bandWidth = bandWidth;
	}

	public String getFrequencyPointA() {
		return frequencyPointA;
	}

	public void setFrequencyPointA(String frequencyPointA) {
		this.frequencyPointA = frequencyPointA;
	}

	public String getFrequencyDL() {
		return frequencyDL;
	}

	public void setFrequencyDL(String frequencyDL) {
		this.frequencyDL = frequencyDL;
	}

	public String getGscn() {
		return gscn;
	}

	public void setGscn(String gscn) {
		this.gscn = gscn;
	}

	public String getSubCarrierSpace() {
		return subCarrierSpace;
	}

	public void setSubCarrierSpace(String subCarrierSpace) {
		this.subCarrierSpace = subCarrierSpace;
	}

	public String getPci() {
		return pci;
	}

	public void setPci(String pci) {
		this.pci = pci;
	}

	public String getSsbIndex() {
		return ssbIndex;
	}

	public void setSsbIndex(String ssbIndex) {
		this.ssbIndex = ssbIndex;
	}

	public String getSsArfcn() {
		return ssArfcn;
	}

	public void setSsArfcn(String ssArfcn) {
		this.ssArfcn = ssArfcn;
	}

	public String getSsRsrp() {
		return ssRsrp;
	}

	public void setSsRsrp(String ssRsrp) {
		this.ssRsrp = ssRsrp;
	}

	public String getSsSinr() {
		return ssSinr;
	}

	public void setSsSinr(String ssSinr) {
		this.ssSinr = ssSinr;
	}

	public String getSsRsrq() {
		return ssRsrq;
	}

	public void setSsRsrq(String ssRsrq) {
		this.ssRsrq = ssRsrq;
	}

	public String getPushTxPower() {
		return pushTxPower;
	}

	public void setPushTxPower(String pushTxPower) {
		this.pushTxPower = pushTxPower;
	}

	public String getPucchTxPower() {
		return pucchTxPower;
	}

	public void setPucchTxPower(String pucchTxPower) {
		this.pucchTxPower = pucchTxPower;
	}

	public String getSrsTxPower() {
		return srsTxPower;
	}

	public void setSrsTxPower(String srsTxPower) {
		this.srsTxPower = srsTxPower;
	}

	public String getCqi() {
		return cqi;
	}

	public void setCqi(String cqi) {
		this.cqi = cqi;
	}

	public String getMcsUl() {
		return mcsUl;
	}

	public void setMcsUl(String mcsUl) {
		this.mcsUl = mcsUl;
	}

	public String getMcsDl() {
		return mcsDl;
	}

	public void setMcsDl(String mcsDl) {
		this.mcsDl = mcsDl;
	}

	public String getModUl() {
		return modUl;
	}

	public void setModUl(String modUl) {
		this.modUl = modUl;
	}

	public String getModDl() {
		return modDl;
	}

	public void setModDl(String modDl) {
		this.modDl = modDl;
	}

	public String getPuschBler() {
		return puschBler;
	}

	public void setPuschBler(String puschBler) {
		this.puschBler = puschBler;
	}

	public String getPdschBler() {
		return pdschBler;
	}

	public void setPdschBler(String pdschBler) {
		this.pdschBler = pdschBler;
	}

	public String getGrantUlNum() {
		return grantUlNum;
	}

	public void setGrantUlNum(String grantUlNum) {
		this.grantUlNum = grantUlNum;
	}

	public String getGrantDLNum() {
		return grantDLNum;
	}

	public void setGrantDLNum(String grantDLNum) {
		this.grantDLNum = grantDLNum;
	}

	public String getRiNumDl() {
		return riNumDl;
	}

	public void setRiNumDl(String riNumDl) {
		this.riNumDl = riNumDl;
	}

	public String getPdcpUlThr() {
		return pdcpUlThr;
	}

	public void setPdcpUlThr(String pdcpUlThr) {
		this.pdcpUlThr = pdcpUlThr;
	}

	public String getPdcpDlThr() {
		return pdcpDlThr;
	}

	public void setPdcpDlThr(String pdcpDlThr) {
		this.pdcpDlThr = pdcpDlThr;
	}

	public String getRlcUlThr() {
		return rlcUlThr;
	}

	public void setRlcUlThr(String rlcUlThr) {
		this.rlcUlThr = rlcUlThr;
	}

	public String getRlcDlThr() {
		return rlcDlThr;
	}

	public void setRlcDlThr(String rlcDlThr) {
		this.rlcDlThr = rlcDlThr;
	}

	public String getMacUlThr() {
		return macUlThr;
	}

	public void setMacUlThr(String macUlThr) {
		this.macUlThr = macUlThr;
	}

	public String getMacDlThr() {
		return macDlThr;
	}

	public void setMacDlThr(String macDlThr) {
		this.macDlThr = macDlThr;
	}

	public String getPuschRb() {
		return puschRb;
	}

	public void setPuschRb(String puschRb) {
		this.puschRb = puschRb;
	}

	public String getPdschRb() {
		return pdschRb;
	}

	public void setPdschRb(String pdschRb) {
		this.pdschRb = pdschRb;
	}

	public String getSlotConfig() {
		return slotConfig;
	}

	public void setSlotConfig(String slotConfig) {
		this.slotConfig = slotConfig;
	}

	public String getQamnum16() {
		return qamnum16;
	}

	public void setQamnum16(String qamnum16) {
		this.qamnum16 = qamnum16;
	}

	public String getQamnum64() {
		return qamnum64;
	}

	public void setQamnum64(String qamnum64) {
		this.qamnum64 = qamnum64;
	}

	public String getQamnum256() {
		return qamnum256;
	}

	public void setQamnum256(String qamnum256) {
		this.qamnum256 = qamnum256;
	}

	public String getQamUlnum16() {
		return qamUlnum16;
	}

	public void setQamUlnum16(String qamUlnum16) {
		this.qamUlnum16 = qamUlnum16;
	}

	public String getQamDlnum16() {
		return qamDlnum16;
	}

	public void setQamDlnum16(String qamDlnum16) {
		this.qamDlnum16 = qamDlnum16;
	}

	public String getQamUlnum64() {
		return qamUlnum64;
	}

	public void setQamUlnum64(String qamUlnum64) {
		this.qamUlnum64 = qamUlnum64;
	}

	public String getQamDlnum64() {
		return qamDlnum64;
	}

	public void setQamDlnum64(String qamDlnum64) {
		this.qamDlnum64 = qamDlnum64;
	}

	public String getQamUlnum256() {
		return qamUlnum256;
	}

	public void setQamUlnum256(String qamUlnum256) {
		this.qamUlnum256 = qamUlnum256;
	}

	public String getQamDlnum256() {
		return qamDlnum256;
	}

	public void setQamDlnum256(String qamDlnum256) {
		this.qamDlnum256 = qamDlnum256;
	}

	public String getQpskulnum() {
		return qpskulnum;
	}

	public void setQpskulnum(String qpskulnum) {
		this.qpskulnum = qpskulnum;
	}

	public ProNrDataInfoBeans(String band, String bandWidth, String frequencyPointA, String frequencyDL, String gscn,
			String subCarrierSpace, String pci, String ssbIndex, String ssArfcn, String ssRsrp, String ssSinr,
			String ssRsrq, String pushTxPower, String pucchTxPower, String srsTxPower, String cqi, String mcsUl,
			String mcsDl, String modUl, String modDl, String puschBler, String pdschBler, String grantUlNum,
			String grantDLNum, String riNumDl, String pdcpUlThr, String pdcpDlThr, String rlcUlThr, String rlcDlThr,
			String macUlThr, String macDlThr, String puschRb, String pdschRb, String slotConfig, String qamnum16,
			String qamnum64, String qamnum256, String qamUlnum16, String qamDlnum16, String qamUlnum64,
			String qamDlnum64, String qamUlnum256, String qamDlnum256, String qpskulnum,
			List<ProNrNeighborhoodInfos> proNrNeighborhoodInfos) {
		super();
		this.band = band;
		this.bandWidth = bandWidth;
		this.frequencyPointA = frequencyPointA;
		this.frequencyDL = frequencyDL;
		this.gscn = gscn;
		this.subCarrierSpace = subCarrierSpace;
		this.pci = pci;
		this.ssbIndex = ssbIndex;
		this.ssArfcn = ssArfcn;
		this.ssRsrp = ssRsrp;
		this.ssSinr = ssSinr;
		this.ssRsrq = ssRsrq;
		this.pushTxPower = pushTxPower;
		this.pucchTxPower = pucchTxPower;
		this.srsTxPower = srsTxPower;
		this.cqi = cqi;
		this.mcsUl = mcsUl;
		this.mcsDl = mcsDl;
		this.modUl = modUl;
		this.modDl = modDl;
		this.puschBler = puschBler;
		this.pdschBler = pdschBler;
		this.grantUlNum = grantUlNum;
		this.grantDLNum = grantDLNum;
		this.riNumDl = riNumDl;
		this.pdcpUlThr = pdcpUlThr;
		this.pdcpDlThr = pdcpDlThr;
		this.rlcUlThr = rlcUlThr;
		this.rlcDlThr = rlcDlThr;
		this.macUlThr = macUlThr;
		this.macDlThr = macDlThr;
		this.puschRb = puschRb;
		this.pdschRb = pdschRb;
		this.slotConfig = slotConfig;
		this.qamnum16 = qamnum16;
		this.qamnum64 = qamnum64;
		this.qamnum256 = qamnum256;
		this.qamUlnum16 = qamUlnum16;
		this.qamDlnum16 = qamDlnum16;
		this.qamUlnum64 = qamUlnum64;
		this.qamDlnum64 = qamDlnum64;
		this.qamUlnum256 = qamUlnum256;
		this.qamDlnum256 = qamDlnum256;
		this.qpskulnum = qpskulnum;
		this.proNrNeighborhoodInfos = proNrNeighborhoodInfos;
	}

	public ProNrDataInfoBeans() {
		super();
	}

}
