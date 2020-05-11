package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;

/**
 * root4G邻区信息
 * @author weichengz
 * @date 2020年5月6日 上午10:11:21
 */
public class ProLteNeighborhoodInfo implements Serializable{
   
	private static final long serialVersionUID = 1L;
	private String pLteNeighbirhoodEARFCN;
    private String pLteNeighbirhoodPCI;
    private String pLteNeighbirhoodRSRP;
    private String pLteNeighbirhoodRSRQ;
    private String pLteNeighbirhoodRSSI;

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

	public ProLteNeighborhoodInfo(String pLteNeighbirhoodEARFCN, String pLteNeighbirhoodPCI,
			String pLteNeighbirhoodRSRP, String pLteNeighbirhoodRSRQ, String pLteNeighbirhoodRSSI) {
		super();
		this.pLteNeighbirhoodEARFCN = pLteNeighbirhoodEARFCN;
		this.pLteNeighbirhoodPCI = pLteNeighbirhoodPCI;
		this.pLteNeighbirhoodRSRP = pLteNeighbirhoodRSRP;
		this.pLteNeighbirhoodRSRQ = pLteNeighbirhoodRSRQ;
		this.pLteNeighbirhoodRSSI = pLteNeighbirhoodRSSI;
	}

	public ProLteNeighborhoodInfo() {
		super();
	}
    
}
