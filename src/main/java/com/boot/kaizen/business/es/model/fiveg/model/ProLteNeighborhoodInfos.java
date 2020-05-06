package com.boot.kaizen.business.es.model.fiveg.model;

import java.io.Serializable;
/**
 * 邻区信息【集合】 邻区信息集合
 * @author weichengz
 * @date 2020年4月26日 下午2:05:02
 */
public class ProLteNeighborhoodInfos implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String pLteNeighbirhoodEARFCN	;//	锚点邻区频点
	private String pLteNeighbirhoodPCI	;//	锚点邻区pci
	private String pLteNeighbirhoodRSRP	;//	锚点邻区rsrp
	private String pLteNeighbirhoodRSRQ	;//	锚点邻区rsrq
	private String pLteNeighbirhoodRSSI	;//	锚点邻区rssi
	public String getpLteNeighbirhoodEARFCN() {
		return pLteNeighbirhoodEARFCN;
	}
	public void setpLteNeighbirhoodEARFCN(String pLteNeighbirhoodEARFCN) {
		this.pLteNeighbirhoodEARFCN = pLteNeighbirhoodEARFCN;
	}
	public String getpLteNeighbirhoodPCI() {
		return pLteNeighbirhoodPCI;
	}
	public void setpLteNeighbirhoodPCI(String pLteNeighbirhoodPCI) {
		this.pLteNeighbirhoodPCI = pLteNeighbirhoodPCI;
	}
	public String getpLteNeighbirhoodRSRP() {
		return pLteNeighbirhoodRSRP;
	}
	public void setpLteNeighbirhoodRSRP(String pLteNeighbirhoodRSRP) {
		this.pLteNeighbirhoodRSRP = pLteNeighbirhoodRSRP;
	}
	public String getpLteNeighbirhoodRSRQ() {
		return pLteNeighbirhoodRSRQ;
	}
	public void setpLteNeighbirhoodRSRQ(String pLteNeighbirhoodRSRQ) {
		this.pLteNeighbirhoodRSRQ = pLteNeighbirhoodRSRQ;
	}
	public String getpLteNeighbirhoodRSSI() {
		return pLteNeighbirhoodRSSI;
	}
	public void setpLteNeighbirhoodRSSI(String pLteNeighbirhoodRSSI) {
		this.pLteNeighbirhoodRSSI = pLteNeighbirhoodRSSI;
	}
	public ProLteNeighborhoodInfos(String pLteNeighbirhoodEARFCN, String pLteNeighbirhoodPCI,
			String pLteNeighbirhoodRSRP, String pLteNeighbirhoodRSRQ, String pLteNeighbirhoodRSSI) {
		super();
		this.pLteNeighbirhoodEARFCN = pLteNeighbirhoodEARFCN;
		this.pLteNeighbirhoodPCI = pLteNeighbirhoodPCI;
		this.pLteNeighbirhoodRSRP = pLteNeighbirhoodRSRP;
		this.pLteNeighbirhoodRSRQ = pLteNeighbirhoodRSRQ;
		this.pLteNeighbirhoodRSSI = pLteNeighbirhoodRSSI;
	}
	public ProLteNeighborhoodInfos() {
		super();
	}

	
}
