package com.boot.kaizen.business.es.model.fiveg.model;

import java.io.Serializable;
/**
 * 测试指标【对象】（主卡）  5G
 * @author weichengz
 * @date 2020年4月26日 上午10:30:07
 */
import java.util.List;

public class NrDataInfoBeans implements Serializable {

	private static final long serialVersionUID = 1L;

	private String NrARFCN;// 5G频点
	private String NrPCI;// 5GPCI
	private String NrCellName;// 5G小区名，根据频点/PCI，默认5公里内进行匹配
	private String SSRSRP;// ss rsrp
	private String SSSINR;// ss sinr
	private String SSRSRQ;// ss rsrq

	private List<NrNeighborhoodInfos> nrDataInfoBeans;// 邻区信息【集合】

	public String getNrARFCN() {
		return NrARFCN;
	}

	public void setNrARFCN(String nrARFCN) {
		NrARFCN = nrARFCN;
	}

	public String getNrPCI() {
		return NrPCI;
	}

	public void setNrPCI(String nrPCI) {
		NrPCI = nrPCI;
	}

	public String getNrCellName() {
		return NrCellName;
	}

	public void setNrCellName(String nrCellName) {
		NrCellName = nrCellName;
	}

	public String getSSRSRP() {
		return SSRSRP;
	}

	public void setSSRSRP(String sSRSRP) {
		SSRSRP = sSRSRP;
	}

	public String getSSSINR() {
		return SSSINR;
	}

	public void setSSSINR(String sSSINR) {
		SSSINR = sSSINR;
	}

	public String getSSRSRQ() {
		return SSRSRQ;
	}

	public void setSSRSRQ(String sSRSRQ) {
		SSRSRQ = sSRSRQ;
	}

	public List<NrNeighborhoodInfos> getNrDataInfoBeans() {
		return nrDataInfoBeans;
	}

	public void setNrDataInfoBeans(List<NrNeighborhoodInfos> nrDataInfoBeans) {
		this.nrDataInfoBeans = nrDataInfoBeans;
	}

	public NrDataInfoBeans(String nrARFCN, String nrPCI, String nrCellName, String sSRSRP, String sSSINR, String sSRSRQ,
			List<NrNeighborhoodInfos> nrDataInfoBeans) {
		super();
		NrARFCN = nrARFCN;
		NrPCI = nrPCI;
		NrCellName = nrCellName;
		SSRSRP = sSRSRP;
		SSSINR = sSSINR;
		SSRSRQ = sSRSRQ;
		this.nrDataInfoBeans = nrDataInfoBeans;
	}

	public NrDataInfoBeans() {
		super();
	}

}
