package com.boot.kaizen.business.es.model.fiveg.model;

import java.io.Serializable;

/**
 * 测试指标【对象】（主卡）
 * 
 * @author weichengz
 * @date 2020年4月26日 上午10:35:56
 */
public class LteDataInfoBeans implements Serializable {

	private static final long serialVersionUID = 1L;

	private String lteTAC;// 锚点tac
	private String lteEARFCN;// 锚点频点
	private String ltePCI;// 锚点pci
	private String lteENB;// 锚点enb
	private String lteCellId;// 锚点cell id
	private String lteCellName;// 锚点小区名
	private String lteRSRP;// 锚点rsrp
	private String lteRSRQ;// 锚点rsrq
	private String lteSINR;// 锚点sinr
	private String lteRSSI;// 锚点rssi

	private LteNeighborhoodInfos lteNeighborhoodInfos;

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

	public String getLteCellId() {
		return lteCellId;
	}

	public void setLteCellId(String lteCellId) {
		this.lteCellId = lteCellId;
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

	

	public LteNeighborhoodInfos getLteNeighborhoodInfos() {
		return lteNeighborhoodInfos;
	}

	public void setLteNeighborhoodInfos(LteNeighborhoodInfos lteNeighborhoodInfos) {
		this.lteNeighborhoodInfos = lteNeighborhoodInfos;
	}

	public LteDataInfoBeans(String lteTAC, String lteEARFCN, String ltePCI, String lteENB, String lteCellId,
			String lteCellName, String lteRSRP, String lteRSRQ, String lteSINR, String lteRSSI,
			com.boot.kaizen.business.es.model.fiveg.model.LteNeighborhoodInfos lteNeighborhoodInfos) {
		super();
		this.lteTAC = lteTAC;
		this.lteEARFCN = lteEARFCN;
		this.ltePCI = ltePCI;
		this.lteENB = lteENB;
		this.lteCellId = lteCellId;
		this.lteCellName = lteCellName;
		this.lteRSRP = lteRSRP;
		this.lteRSRQ = lteRSRQ;
		this.lteSINR = lteSINR;
		this.lteRSSI = lteRSSI;
		this.lteNeighborhoodInfos = lteNeighborhoodInfos;
	}

	public LteDataInfoBeans() {
		super();
	}

}
