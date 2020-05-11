package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;

/**
 * 非root4G邻区信息
 * 
 * @author weichengz
 * @date 2020年5月6日 上午10:06:10
 */
public class LteNeighborhoodInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String lteNeighbirhoodEARFCN;
	private String lteNeighbirhoodPCI;
	private String lteNeighbirhoodRSRP;
	private String lteNeighbirhoodRSRQ;
	private String lteNeighbirhoodRSSI;

	public String getLteNeighbirhoodEARFCN() {
		return lteNeighbirhoodEARFCN;
	}

	public void setLteNeighbirhoodEARFCN(String lteNeighbirhoodEARFCN) {
		this.lteNeighbirhoodEARFCN = lteNeighbirhoodEARFCN;
	}

	public String getLteNeighbirhoodPCI() {
		return lteNeighbirhoodPCI;
	}

	public void setLteNeighbirhoodPCI(String lteNeighbirhoodPCI) {
		this.lteNeighbirhoodPCI = lteNeighbirhoodPCI;
	}

	public String getLteNeighbirhoodRSRP() {
		return lteNeighbirhoodRSRP;
	}

	public void setLteNeighbirhoodRSRP(String lteNeighbirhoodRSRP) {
		this.lteNeighbirhoodRSRP = lteNeighbirhoodRSRP;
	}

	public String getLteNeighbirhoodRSRQ() {
		return lteNeighbirhoodRSRQ;
	}

	public void setLteNeighbirhoodRSRQ(String lteNeighbirhoodRSRQ) {
		this.lteNeighbirhoodRSRQ = lteNeighbirhoodRSRQ;
	}

	public String getLteNeighbirhoodRSSI() {
		return lteNeighbirhoodRSSI;
	}

	public void setLteNeighbirhoodRSSI(String lteNeighbirhoodRSSI) {
		this.lteNeighbirhoodRSSI = lteNeighbirhoodRSSI;
	}

	public LteNeighborhoodInfo(String lteNeighbirhoodEARFCN, String lteNeighbirhoodPCI, String lteNeighbirhoodRSRP,
			String lteNeighbirhoodRSRQ, String lteNeighbirhoodRSSI) {
		super();
		this.lteNeighbirhoodEARFCN = lteNeighbirhoodEARFCN;
		this.lteNeighbirhoodPCI = lteNeighbirhoodPCI;
		this.lteNeighbirhoodRSRP = lteNeighbirhoodRSRP;
		this.lteNeighbirhoodRSRQ = lteNeighbirhoodRSRQ;
		this.lteNeighbirhoodRSSI = lteNeighbirhoodRSSI;
	}

	public LteNeighborhoodInfo() {
		super();
	}

}
