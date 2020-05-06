package com.boot.kaizen.business.es.model.fiveg.model;

import java.io.Serializable;
/**
 * 测试指标【对象】（主卡）  5G  【的】 邻区信息集合
 * @author weichengz
 * @date 2020年4月26日 上午10:31:34
 */
public class NrNeighborhoodInfos implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nrNeighborhoodNRARFCN	;//	5G邻区频点
	private String nrNeighborhoodPCI	;//	5G邻区pci
	private String nrNeighborhoodSSRSRP	;//	5G邻区ss rsrp
	private String nrNeighborhoodSSRSRQ	;//	5G邻区ss rsrq
	private String nrNeighborhoodSSSINR	;//	5G邻区ss sinr
	
	
	public String getNrNeighborhoodNRARFCN() {
		return nrNeighborhoodNRARFCN;
	}
	public void setNrNeighborhoodNRARFCN(String nrNeighborhoodNRARFCN) {
		this.nrNeighborhoodNRARFCN = nrNeighborhoodNRARFCN;
	}
	public String getNrNeighborhoodPCI() {
		return nrNeighborhoodPCI;
	}
	public void setNrNeighborhoodPCI(String nrNeighborhoodPCI) {
		this.nrNeighborhoodPCI = nrNeighborhoodPCI;
	}
	public String getNrNeighborhoodSSRSRP() {
		return nrNeighborhoodSSRSRP;
	}
	public void setNrNeighborhoodSSRSRP(String nrNeighborhoodSSRSRP) {
		this.nrNeighborhoodSSRSRP = nrNeighborhoodSSRSRP;
	}
	public String getNrNeighborhoodSSRSRQ() {
		return nrNeighborhoodSSRSRQ;
	}
	public void setNrNeighborhoodSSRSRQ(String nrNeighborhoodSSRSRQ) {
		this.nrNeighborhoodSSRSRQ = nrNeighborhoodSSRSRQ;
	}
	public String getNrNeighborhoodSSSINR() {
		return nrNeighborhoodSSSINR;
	}
	public void setNrNeighborhoodSSSINR(String nrNeighborhoodSSSINR) {
		this.nrNeighborhoodSSSINR = nrNeighborhoodSSSINR;
	}
	public NrNeighborhoodInfos(String nrNeighborhoodNRARFCN, String nrNeighborhoodPCI, String nrNeighborhoodSSRSRP,
			String nrNeighborhoodSSRSRQ, String nrNeighborhoodSSSINR) {
		super();
		this.nrNeighborhoodNRARFCN = nrNeighborhoodNRARFCN;
		this.nrNeighborhoodPCI = nrNeighborhoodPCI;
		this.nrNeighborhoodSSRSRP = nrNeighborhoodSSRSRP;
		this.nrNeighborhoodSSRSRQ = nrNeighborhoodSSRSRQ;
		this.nrNeighborhoodSSSINR = nrNeighborhoodSSSINR;
	}
	public NrNeighborhoodInfos() {
		super();
	}

	
	
}
