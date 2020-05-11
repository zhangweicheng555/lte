package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;
import java.util.List;

/**
 * root5G信令
 * @author weichengz
 * @date 2020年5月6日 上午10:11:48
 */
public class ProNrDataInfoBean implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	private String band;//频段
    private String BandWidth;//带宽
    private String FrequencyPointA;//PointA频率
    private String FrequencyDL;//下行频率
    private String GSCN;//全局同步信道
    private String SubCarrierSpace;//子载波间隔
    private String PCI;
    private String SSBIndex;//SSB号
    private String ssRSRP;
    private String ssSINR;
    private String ssRSRQ;
    private String puschTxPower;//上行共享信道发射功率
    private String pucchTxPower;//上行控制信道发射功率
    private String srsTxPower;//探测信号发射功率
    
    private String cqi;
    private String mcsUL;//上行MCS
    private String mcsDL;//下行MCS
    private String modUL;
    private String modDL;
    
    
    private String puschBler;//上行误块率
    private String pdschBler;//下行误块率
    private String grantULNum;//上行RB授权次数
    private String grantDLNum;//下行RB授权次数
    private String riNumDL;//下行秩指示
    
    
    private String pdcpULThr;//PDCP层上行吞吐率
    private String pdcpDLThr;//PDCP层下行吞吐率
    private String rlcULThr;//RLC层上行吞吐率
    private String rlcDLThr;//RLC层下行吞吐率
    private String macULThr;//MAC层上行吞吐率
    
    
    private String macDLThr;//MAC层下行吞吐率
    private String puschRB;//上行RB调度次数
    private String pdschRB;//下行RB调度次数
    private String slotConfigDLUL;//Slot Config(DL/UL)时隙配置
    
    
    private String _16qamNum;//16QAM 调制次数
    private String _64qamNum;//64QAM 调制次数
    private String _256qamNum;//256QAM 调制次数
    private String _16qamUlNum;//16QAM UL调制次数
    private String _16qamDlNum;//16QAM DL 调制次数
    
    
    private String _64qamUlNum;//64QAM UL 调制次数
    private String _64qamDlNum;//64QAM DL 调制次数
    private String _256qamUlNum;//256QAM UL 调制次数
    private String _256qamDlNum;//256QAM DL 调制次数
    
    
    private String qpskUlNum;//QPSKUL 调制次数
    private String qpskDlNum;//QPSKDL 调制次数
    private String ssARFCN;//频点
    private List<ProNrNeighborhoodInfo> proNrNeighborhoodInfos;

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getBandWidth() {
        return BandWidth;
    }

    public void setBandWidth(String bandWidth) {
        BandWidth = bandWidth;
    }

    public String getFrequencyPointA() {
        return FrequencyPointA;
    }

    public void setFrequencyPointA(String frequencyPointA) {
        FrequencyPointA = frequencyPointA;
    }

    public String getFrequencyDL() {
        return FrequencyDL;
    }

    public void setFrequencyDL(String frequencyDL) {
        FrequencyDL = frequencyDL;
    }

    public String getGSCN() {
        return GSCN;
    }

    public void setGSCN(String GSCN) {
        this.GSCN = GSCN;
    }

    public String getSubCarrierSpace() {
        return SubCarrierSpace;
    }

    public void setSubCarrierSpace(String subCarrierSpace) {
        SubCarrierSpace = subCarrierSpace;
    }

    public String getPCI() {
        return PCI;
    }

    public void setPCI(String PCI) {
        this.PCI = PCI;
    }

    public String getSSBIndex() {
        return SSBIndex;
    }

    public void setSSBIndex(String SSBIndex) {
        this.SSBIndex = SSBIndex;
    }

    public String getSsRSRP() {
        return ssRSRP;
    }

    public void setSsRSRP(String ssRSRP) {
        this.ssRSRP = ssRSRP;
    }

    public String getSsSINR() {
        return ssSINR;
    }

    public void setSsSINR(String ssSINR) {
        this.ssSINR = ssSINR;
    }

    public String getSsRSRQ() {
        return ssRSRQ;
    }

    public void setSsRSRQ(String ssRSRQ) {
        this.ssRSRQ = ssRSRQ;
    }

    public String getPuschTxPower() {
        return puschTxPower;
    }

    public void setPuschTxPower(String puschTxPower) {
        this.puschTxPower = puschTxPower;
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

    public String getMcsUL() {
        return mcsUL;
    }

    public void setMcsUL(String mcsUL) {
        this.mcsUL = mcsUL;
    }

    public String getMcsDL() {
        return mcsDL;
    }

    public void setMcsDL(String mcsDL) {
        this.mcsDL = mcsDL;
    }

    public String getModUL() {
        return modUL;
    }

    public void setModUL(String modUL) {
        this.modUL = modUL;
    }

    public String getModDL() {
        return modDL;
    }

    public void setModDL(String modDL) {
        this.modDL = modDL;
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

    public String getGrantULNum() {
        return grantULNum;
    }

    public void setGrantULNum(String grantULNum) {
        this.grantULNum = grantULNum;
    }

    public String getGrantDLNum() {
        return grantDLNum;
    }

    public void setGrantDLNum(String grantDLNum) {
        this.grantDLNum = grantDLNum;
    }

    public String getRiNumDL() {
        return riNumDL;
    }

    public void setRiNumDL(String riNumDL) {
        this.riNumDL = riNumDL;
    }

    public String getPdcpULThr() {
        return pdcpULThr;
    }

    public void setPdcpULThr(String pdcpULThr) {
        this.pdcpULThr = pdcpULThr;
    }

    public String getPdcpDLThr() {
        return pdcpDLThr;
    }

    public void setPdcpDLThr(String pdcpDLThr) {
        this.pdcpDLThr = pdcpDLThr;
    }

    public String getRlcULThr() {
        return rlcULThr;
    }

    public void setRlcULThr(String rlcULThr) {
        this.rlcULThr = rlcULThr;
    }

    public String getRlcDLThr() {
        return rlcDLThr;
    }

    public void setRlcDLThr(String rlcDLThr) {
        this.rlcDLThr = rlcDLThr;
    }

    public String getMacULThr() {
        return macULThr;
    }

    public void setMacULThr(String macULThr) {
        this.macULThr = macULThr;
    }

    public String getMacDLThr() {
        return macDLThr;
    }

    public void setMacDLThr(String macDLThr) {
        this.macDLThr = macDLThr;
    }

    public String getPuschRB() {
        return puschRB;
    }

    public void setPuschRB(String puschRB) {
        this.puschRB = puschRB;
    }

    public String getPdschRB() {
        return pdschRB;
    }

    public void setPdschRB(String pdschRB) {
        this.pdschRB = pdschRB;
    }

    public String getSlotConfigDLUL() {
        return slotConfigDLUL;
    }

    public void setSlotConfigDLUL(String slotConfigDLUL) {
        this.slotConfigDLUL = slotConfigDLUL;
    }

    public String get_16qamNum() {
        return _16qamNum;
    }

    public void set_16qamNum(String _16qamNum) {
        this._16qamNum = _16qamNum;
    }

    public String get_64qamNum() {
        return _64qamNum;
    }

    public void set_64qamNum(String _64qamNum) {
        this._64qamNum = _64qamNum;
    }

    public String get_256qamNum() {
        return _256qamNum;
    }

    public void set_256qamNum(String _256qamNum) {
        this._256qamNum = _256qamNum;
    }

    public String get_16qamUlNum() {
        return _16qamUlNum;
    }

    public void set_16qamUlNum(String _16qamUlNum) {
        this._16qamUlNum = _16qamUlNum;
    }

    public String get_16qamDlNum() {
        return _16qamDlNum;
    }

    public void set_16qamDlNum(String _16qamDlNum) {
        this._16qamDlNum = _16qamDlNum;
    }

    public String get_64qamUlNum() {
        return _64qamUlNum;
    }

    public void set_64qamUlNum(String _64qamUlNum) {
        this._64qamUlNum = _64qamUlNum;
    }

    public String get_64qamDlNum() {
        return _64qamDlNum;
    }

    public void set_64qamDlNum(String _64qamDlNum) {
        this._64qamDlNum = _64qamDlNum;
    }

    public String get_256qamUlNum() {
        return _256qamUlNum;
    }

    public void set_256qamUlNum(String _256qamUlNum) {
        this._256qamUlNum = _256qamUlNum;
    }

    public String get_256qamDlNum() {
        return _256qamDlNum;
    }

    public void set_256qamDlNum(String _256qamDlNum) {
        this._256qamDlNum = _256qamDlNum;
    }

    public String getQpskUlNum() {
        return qpskUlNum;
    }

    public void setQpskUlNum(String qpskUlNum) {
        this.qpskUlNum = qpskUlNum;
    }

    public String getQpskDlNum() {
        return qpskDlNum;
    }

    public void setQpskDlNum(String qpskDlNum) {
        this.qpskDlNum = qpskDlNum;
    }

    public List<ProNrNeighborhoodInfo> getProNrNeighborhoodInfos() {
        return proNrNeighborhoodInfos;
    }

    public void setProNrNeighborhoodInfos(List<ProNrNeighborhoodInfo> proNrNeighborhoodInfos) {
        this.proNrNeighborhoodInfos = proNrNeighborhoodInfos;
    }

    public String getSsARFCN() {
        return ssARFCN;
    }

    public void setSsARFCN(String ssARFCN) {
        this.ssARFCN = ssARFCN;
    }

	public ProNrDataInfoBean(String band, String bandWidth, String frequencyPointA, String frequencyDL, String gSCN,
			String subCarrierSpace, String pCI, String sSBIndex, String ssRSRP, String ssSINR, String ssRSRQ,
			String puschTxPower, String pucchTxPower, String srsTxPower, String cqi, String mcsUL, String mcsDL,
			String modUL, String modDL, String puschBler, String pdschBler, String grantULNum, String grantDLNum,
			String riNumDL, String pdcpULThr, String pdcpDLThr, String rlcULThr, String rlcDLThr, String macULThr,
			String macDLThr, String puschRB, String pdschRB, String slotConfigDLUL, String _16qamNum, String _64qamNum,
			String _256qamNum, String _16qamUlNum, String _16qamDlNum, String _64qamUlNum, String _64qamDlNum,
			String _256qamUlNum, String _256qamDlNum, String qpskUlNum, String qpskDlNum, String ssARFCN,
			List<ProNrNeighborhoodInfo> proNrNeighborhoodInfos) {
		super();
		this.band = band;
		BandWidth = bandWidth;
		FrequencyPointA = frequencyPointA;
		FrequencyDL = frequencyDL;
		GSCN = gSCN;
		SubCarrierSpace = subCarrierSpace;
		PCI = pCI;
		SSBIndex = sSBIndex;
		this.ssRSRP = ssRSRP;
		this.ssSINR = ssSINR;
		this.ssRSRQ = ssRSRQ;
		this.puschTxPower = puschTxPower;
		this.pucchTxPower = pucchTxPower;
		this.srsTxPower = srsTxPower;
		this.cqi = cqi;
		this.mcsUL = mcsUL;
		this.mcsDL = mcsDL;
		this.modUL = modUL;
		this.modDL = modDL;
		this.puschBler = puschBler;
		this.pdschBler = pdschBler;
		this.grantULNum = grantULNum;
		this.grantDLNum = grantDLNum;
		this.riNumDL = riNumDL;
		this.pdcpULThr = pdcpULThr;
		this.pdcpDLThr = pdcpDLThr;
		this.rlcULThr = rlcULThr;
		this.rlcDLThr = rlcDLThr;
		this.macULThr = macULThr;
		this.macDLThr = macDLThr;
		this.puschRB = puschRB;
		this.pdschRB = pdschRB;
		this.slotConfigDLUL = slotConfigDLUL;
		this._16qamNum = _16qamNum;
		this._64qamNum = _64qamNum;
		this._256qamNum = _256qamNum;
		this._16qamUlNum = _16qamUlNum;
		this._16qamDlNum = _16qamDlNum;
		this._64qamUlNum = _64qamUlNum;
		this._64qamDlNum = _64qamDlNum;
		this._256qamUlNum = _256qamUlNum;
		this._256qamDlNum = _256qamDlNum;
		this.qpskUlNum = qpskUlNum;
		this.qpskDlNum = qpskDlNum;
		this.ssARFCN = ssARFCN;
		this.proNrNeighborhoodInfos = proNrNeighborhoodInfos;
	}

	public ProNrDataInfoBean() {
		super();
	}
    
}
