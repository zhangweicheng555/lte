package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;
import java.util.List;

/**
 * 非root4G信令
 * @author weichengz
 * @date 2020年5月6日 上午10:04:27
 */
public class LteDataInfoBean implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	private String lteTAC;
    private String lteEARFCN;
    private String ltePCI;
    private String lteENB;
    private String lteCellID;
    private String lteCellName;
    private String lteRSRP;
    private String lteRSRQ;
    private String lteSINR;
    private String lteRSSI;

    private List<LteNeighborhoodInfo> LteNeighborhoodInfos;

    public String getLteTAC() {
        return lteTAC;
    }

    public void setLteTAC(String lteTAC) {
        this.lteTAC = lteTAC;
    }

    public String getLteEARFCN() {
        return lteEARFCN;
    }

    public void setLteEARFCN(String lteEARFCN) {
        this.lteEARFCN = lteEARFCN;
    }

    public String getLtePCI() {
        return ltePCI;
    }

    public void setLtePCI(String ltePCI) {
        this.ltePCI = ltePCI;
    }

    public String getLteENB() {
        return lteENB;
    }

    public void setLteENB(String lteENB) {
        this.lteENB = lteENB;
    }

    public String getLteCellID() {
        return lteCellID;
    }

    public void setLteCellID(String lteCellID) {
        this.lteCellID = lteCellID;
    }

    public String getLteCellName() {
        return lteCellName;
    }

    public void setLteCellName(String lteCellName) {
        this.lteCellName = lteCellName;
    }

    public String getLteRSRP() {
        return lteRSRP;
    }

    public void setLteRSRP(String lteRSRP) {
        this.lteRSRP = lteRSRP;
    }

    public String getLteRSRQ() {
        return lteRSRQ;
    }

    public void setLteRSRQ(String lteRSRQ) {
        this.lteRSRQ = lteRSRQ;
    }

    public String getLteSINR() {
        return lteSINR;
    }

    public void setLteSINR(String lteSINR) {
        this.lteSINR = lteSINR;
    }

    public String getLteRSSI() {
        return lteRSSI;
    }

    public void setLteRSSI(String lteRSSI) {
        this.lteRSSI = lteRSSI;
    }

    public List<LteNeighborhoodInfo> getLteNeighborhoodInfos() {
        return LteNeighborhoodInfos;
    }

    public void setLteNeighborhoodInfos(List<LteNeighborhoodInfo> lteNeighborhoodInfos) {
        LteNeighborhoodInfos = lteNeighborhoodInfos;
    }

	public LteDataInfoBean(String lteTAC, String lteEARFCN, String ltePCI, String lteENB, String lteCellID,
			String lteCellName, String lteRSRP, String lteRSRQ, String lteSINR, String lteRSSI,
			List<LteNeighborhoodInfo> lteNeighborhoodInfos) {
		super();
		this.lteTAC = lteTAC;
		this.lteEARFCN = lteEARFCN;
		this.ltePCI = ltePCI;
		this.lteENB = lteENB;
		this.lteCellID = lteCellID;
		this.lteCellName = lteCellName;
		this.lteRSRP = lteRSRP;
		this.lteRSRQ = lteRSRQ;
		this.lteSINR = lteSINR;
		this.lteRSSI = lteRSSI;
		LteNeighborhoodInfos = lteNeighborhoodInfos;
	}

	public LteDataInfoBean() {
		super();
	}
    
    
}
