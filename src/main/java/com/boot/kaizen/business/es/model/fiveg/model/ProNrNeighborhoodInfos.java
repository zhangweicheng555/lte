package com.boot.kaizen.business.es.model.fiveg.model;

import java.io.Serializable;

/**
 * 邻区信息【集合】 5g root测试指标
 * 
 * @author weichengz
 * @date 2020年4月26日 下午1:59:55
 */
public class ProNrNeighborhoodInfos implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pNrNeighborhoodNRARFCN;// 5G邻区频点
	private String pNrNeighborhoodPCI;// 5G邻区pci
	private String pNrNeighborhoodReam;// 5G邻区beam
	private String pNrNeighborhoodSSRSRP;// 5G邻区ss rsrp
	private String pNrNeighborhoodSSSINR;// 5G邻区ss sinr
	private String pNrNeighborhoodSSRSRQ;// 5G邻区ss rsrq

	public String getpNrNeighborhoodNRARFCN() {
		return pNrNeighborhoodNRARFCN;
	}

	public void setpNrNeighborhoodNRARFCN(String pNrNeighborhoodNRARFCN) {
		this.pNrNeighborhoodNRARFCN = pNrNeighborhoodNRARFCN;
	}

	public String getpNrNeighborhoodPCI() {
		return pNrNeighborhoodPCI;
	}

	public void setpNrNeighborhoodPCI(String pNrNeighborhoodPCI) {
		this.pNrNeighborhoodPCI = pNrNeighborhoodPCI;
	}

	public String getpNrNeighborhoodReam() {
		return pNrNeighborhoodReam;
	}

	public void setpNrNeighborhoodReam(String pNrNeighborhoodReam) {
		this.pNrNeighborhoodReam = pNrNeighborhoodReam;
	}

	public String getpNrNeighborhoodSSRSRP() {
		return pNrNeighborhoodSSRSRP;
	}

	public void setpNrNeighborhoodSSRSRP(String pNrNeighborhoodSSRSRP) {
		this.pNrNeighborhoodSSRSRP = pNrNeighborhoodSSRSRP;
	}

	public String getpNrNeighborhoodSSSINR() {
		return pNrNeighborhoodSSSINR;
	}

	public void setpNrNeighborhoodSSSINR(String pNrNeighborhoodSSSINR) {
		this.pNrNeighborhoodSSSINR = pNrNeighborhoodSSSINR;
	}

	public String getpNrNeighborhoodSSRSRQ() {
		return pNrNeighborhoodSSRSRQ;
	}

	public void setpNrNeighborhoodSSRSRQ(String pNrNeighborhoodSSRSRQ) {
		this.pNrNeighborhoodSSRSRQ = pNrNeighborhoodSSRSRQ;
	}

	public ProNrNeighborhoodInfos(String pNrNeighborhoodNRARFCN, String pNrNeighborhoodPCI, String pNrNeighborhoodReam,
			String pNrNeighborhoodSSRSRP, String pNrNeighborhoodSSSINR, String pNrNeighborhoodSSRSRQ) {
		super();
		this.pNrNeighborhoodNRARFCN = pNrNeighborhoodNRARFCN;
		this.pNrNeighborhoodPCI = pNrNeighborhoodPCI;
		this.pNrNeighborhoodReam = pNrNeighborhoodReam;
		this.pNrNeighborhoodSSRSRP = pNrNeighborhoodSSRSRP;
		this.pNrNeighborhoodSSSINR = pNrNeighborhoodSSSINR;
		this.pNrNeighborhoodSSRSRQ = pNrNeighborhoodSSRSRQ;
	}

	public ProNrNeighborhoodInfos() {
		super();
	}

}
