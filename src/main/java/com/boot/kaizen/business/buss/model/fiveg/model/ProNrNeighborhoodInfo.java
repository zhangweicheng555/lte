package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;

/**
 * root5G邻区信息
 * @author weichengz
 * @date 2020年5月6日 上午10:14:27
 */
public class ProNrNeighborhoodInfo implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	private String pNrNeighborhoodNRARFCN;//邻区频点
    private String pNrNeighborhoodPCI;//邻区pci
    private String pNrNeighborhoodBeam;//邻区beam
    private String pNrNeighborhoodSSRSRP;//邻区ss rsrp
    private String pNrNeighborhoodSSSINR;//邻区ss sinr
    private String pNrNeighborhoodSSRSRQ;//邻区ss rsrq

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

    public String getpNrNeighborhoodBeam() {
        return pNrNeighborhoodBeam;
    }

    public void setpNrNeighborhoodBeam(String pNrNeighborhoodBeam) {
        this.pNrNeighborhoodBeam = pNrNeighborhoodBeam;
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

	public ProNrNeighborhoodInfo(String pNrNeighborhoodNRARFCN, String pNrNeighborhoodPCI, String pNrNeighborhoodBeam,
			String pNrNeighborhoodSSRSRP, String pNrNeighborhoodSSSINR, String pNrNeighborhoodSSRSRQ) {
		super();
		this.pNrNeighborhoodNRARFCN = pNrNeighborhoodNRARFCN;
		this.pNrNeighborhoodPCI = pNrNeighborhoodPCI;
		this.pNrNeighborhoodBeam = pNrNeighborhoodBeam;
		this.pNrNeighborhoodSSRSRP = pNrNeighborhoodSSRSRP;
		this.pNrNeighborhoodSSSINR = pNrNeighborhoodSSSINR;
		this.pNrNeighborhoodSSRSRQ = pNrNeighborhoodSSRSRQ;
	}

	public ProNrNeighborhoodInfo() {
		super();
	}
    
}
