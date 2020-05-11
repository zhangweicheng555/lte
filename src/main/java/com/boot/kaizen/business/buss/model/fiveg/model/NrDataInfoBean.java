package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;
import java.util.List;

/**
 * 非root5G信令
 * @author weichengz
 * @date 2020年5月6日 上午10:07:17
 */
public class NrDataInfoBean implements Serializable{
   
	private static final long serialVersionUID = 1L;
	private String nrARFCN;
    private String nrPCI;
    private String nrCellName;//5G小区名，根据频点/PCI，默认5公里内进行匹配
    private String ssRSRP;
    private String ssSINR;
    private String ssRSRQ;

    private List<NrNeighborhoodInfo> nrNeighborhoodInfos;

    public String getNrARFCN() {
        return nrARFCN;
    }

    public void setNrARFCN(String nrARFCN) {
        this.nrARFCN = nrARFCN;
    }

    public String getNrPCI() {
        return nrPCI;
    }

    public void setNrPCI(String nrPCI) {
        this.nrPCI = nrPCI;
    }

    public String getNrCellName() {
        return nrCellName;
    }

    public void setNrCellName(String nrCellName) {
        this.nrCellName = nrCellName;
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

    public List<NrNeighborhoodInfo> getNrNeighborhoodInfos() {
        return nrNeighborhoodInfos;
    }

    public void setNrNeighborhoodInfos(List<NrNeighborhoodInfo> nrNeighborhoodInfos) {
        this.nrNeighborhoodInfos = nrNeighborhoodInfos;
    }

	public NrDataInfoBean(String nrARFCN, String nrPCI, String nrCellName, String ssRSRP, String ssSINR, String ssRSRQ,
			List<NrNeighborhoodInfo> nrNeighborhoodInfos) {
		super();
		this.nrARFCN = nrARFCN;
		this.nrPCI = nrPCI;
		this.nrCellName = nrCellName;
		this.ssRSRP = ssRSRP;
		this.ssSINR = ssSINR;
		this.ssRSRQ = ssRSRQ;
		this.nrNeighborhoodInfos = nrNeighborhoodInfos;
	}

	public NrDataInfoBean() {
		super();
	}
    
}
