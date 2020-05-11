package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;

/**
 * LTE竟对测试【集合】
 * @author weichengz
 * @date 2020年5月6日 上午10:03:14
 */
public class ComptestBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String mLteNeighborhoodEarfcn;//频点
    private String mLteNeighborhoodPCI;//pci
    private String mLteNeighborhoodRSRQ;//rsrq
    private String mLteNeighborhoodRSRP;//rsrp
    private String siteName;//运营商名称

    public String getmLteNeighborhoodEarfcn() {
        return mLteNeighborhoodEarfcn;
    }

    public void setmLteNeighborhoodEarfcn(String mLteNeighborhoodEarfcn) {
        this.mLteNeighborhoodEarfcn = mLteNeighborhoodEarfcn;
    }

    public String getmLteNeighborhoodPCI() {
        return mLteNeighborhoodPCI;
    }

    public void setmLteNeighborhoodPCI(String mLteNeighborhoodPCI) {
        this.mLteNeighborhoodPCI = mLteNeighborhoodPCI;
    }

    public String getmLteNeighborhoodRSRQ() {
        return mLteNeighborhoodRSRQ;
    }

    public void setmLteNeighborhoodRSRQ(String mLteNeighborhoodRSRQ) {
        this.mLteNeighborhoodRSRQ = mLteNeighborhoodRSRQ;
    }

    public String getmLteNeighborhoodRSRP() {
        return mLteNeighborhoodRSRP;
    }

    public void setmLteNeighborhoodRSRP(String mLteNeighborhoodRSRP) {
        this.mLteNeighborhoodRSRP = mLteNeighborhoodRSRP;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

	public ComptestBean(String mLteNeighborhoodEarfcn, String mLteNeighborhoodPCI, String mLteNeighborhoodRSRQ,
			String mLteNeighborhoodRSRP, String siteName) {
		super();
		this.mLteNeighborhoodEarfcn = mLteNeighborhoodEarfcn;
		this.mLteNeighborhoodPCI = mLteNeighborhoodPCI;
		this.mLteNeighborhoodRSRQ = mLteNeighborhoodRSRQ;
		this.mLteNeighborhoodRSRP = mLteNeighborhoodRSRP;
		this.siteName = siteName;
	}

	public ComptestBean() {
		super();
	}
    
}
