package com.boot.kaizen.business.es.model.fiveg.model;

import java.io.Serializable;
/**
 *邻区信息【集合】
 * @author weichengz
 * @date 2020年4月26日 下午12:05:54
 */
public class LteNeighborhoodInfos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String LteNeighbirhoodEARFCN	;//	锚点邻区频点
	private String LteNeighbirhoodPCI		;//	锚点邻区pci
	private String LteNeighbirhoodRSRP		;//	锚点邻区rsrp
	private String LteNeighbirhoodRSRQ		;//	锚点邻区rsrq
	private String LteNeighbirhoodRSSI		;//	锚点邻区rssi

	public String getLteNeighbirhoodEARFCN() {
		return LteNeighbirhoodEARFCN;
	}
	public void setLteNeighbirhoodEARFCN(String lteNeighbirhoodEARFCN) {
		LteNeighbirhoodEARFCN = lteNeighbirhoodEARFCN;
	}
	public String getLteNeighbirhoodPCI() {
		return LteNeighbirhoodPCI;
	}
	public void setLteNeighbirhoodPCI(String lteNeighbirhoodPCI) {
		LteNeighbirhoodPCI = lteNeighbirhoodPCI;
	}
	public String getLteNeighbirhoodRSRP() {
		return LteNeighbirhoodRSRP;
	}
	public void setLteNeighbirhoodRSRP(String lteNeighbirhoodRSRP) {
		LteNeighbirhoodRSRP = lteNeighbirhoodRSRP;
	}
	public String getLteNeighbirhoodRSRQ() {
		return LteNeighbirhoodRSRQ;
	}
	public void setLteNeighbirhoodRSRQ(String lteNeighbirhoodRSRQ) {
		LteNeighbirhoodRSRQ = lteNeighbirhoodRSRQ;
	}
	public String getLteNeighbirhoodRSSI() {
		return LteNeighbirhoodRSSI;
	}
	public void setLteNeighbirhoodRSSI(String lteNeighbirhoodRSSI) {
		LteNeighbirhoodRSSI = lteNeighbirhoodRSSI;
	}
	public LteNeighborhoodInfos(String lteNeighbirhoodEARFCN, String lteNeighbirhoodPCI, String lteNeighbirhoodRSRP,
			String lteNeighbirhoodRSRQ, String lteNeighbirhoodRSSI) {
		super();
		LteNeighbirhoodEARFCN = lteNeighbirhoodEARFCN;
		LteNeighbirhoodPCI = lteNeighbirhoodPCI;
		LteNeighbirhoodRSRP = lteNeighbirhoodRSRP;
		LteNeighbirhoodRSRQ = lteNeighbirhoodRSRQ;
		LteNeighbirhoodRSSI = lteNeighbirhoodRSSI;
	}
	public LteNeighborhoodInfos() {
		super();
	}

	
}
