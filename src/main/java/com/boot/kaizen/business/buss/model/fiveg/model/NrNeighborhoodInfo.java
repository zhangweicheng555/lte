package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;

/**
 * 非root5G邻区信息
 * @author weichengz
 * @date 2020年5月6日 上午10:09:23
 */
public class NrNeighborhoodInfo implements Serializable{
   
	private static final long serialVersionUID = 1L;
	private String nrNeighborhoodNRARFCN;
    private String nrNeighborhoodPCI;
    private String nrNeighborhoodSSRSRP;
    private String nrNeighborhoodSSRSRQ;
    private String nrNeighborhoodSSSINR;

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

	public NrNeighborhoodInfo(String nrNeighborhoodNRARFCN, String nrNeighborhoodPCI, String nrNeighborhoodSSRSRP,
			String nrNeighborhoodSSRSRQ, String nrNeighborhoodSSSINR) {
		super();
		this.nrNeighborhoodNRARFCN = nrNeighborhoodNRARFCN;
		this.nrNeighborhoodPCI = nrNeighborhoodPCI;
		this.nrNeighborhoodSSRSRP = nrNeighborhoodSSRSRP;
		this.nrNeighborhoodSSRSRQ = nrNeighborhoodSSRSRQ;
		this.nrNeighborhoodSSSINR = nrNeighborhoodSSSINR;
	}

	public NrNeighborhoodInfo() {
		super();
	}
    
}
