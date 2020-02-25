package com.boot.kaizen.business.buss.entity;

import java.io.Serializable;

/**
 * 目前用不到 只是csv的字段格式如下
 * 
 * @author weichengz
 * @date 2020年2月25日 上午9:36:23
 */
public class SignalCsvLogBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String Timestamp = "";// 时间 testTime
	private String PHONE = "";// 手机型号 【无】
	private String OPERATOR = "";// 运营商 【无】
	private String MCC = "";// MCC
	private String MNC = "";// MNC
	private String NET_TYPE = "";// 网络类型 netWorkType
	private String LON = "";// 经度 longitude
	private String LAT = "";// 纬度 latitude
	private String SPEED = "";// 速度sPEED
	private String HEIGHT = "";// 高度 【无】

	private String EVENT = "";// 应用层事件normalEventType/ abNormalEventType
	private String UL_Throughput = "";// 上行速率 upLoadSpeed
	private String DL_Throughput = "";// 下行速率 downLoadSpeed
	private String Ping_Delay = "";// Ping时延 pjsy
	private String Http_Delay = "";// Http时延 pjsy
	private String CGI = "";// CGI cI
	private String ENB = "";// 基站号 eNB
	private String CELL_NAME = "";// 小区中文名
	private String CID = "";// 小区号 cellName
	private String TAC = "";// TAC tAC
	private String EARFCN = "";// 频点 earfcn
	private String PCI = "";// PCI pci
	
	
	private String RSRQ = "";// RSRQ rsrq
	private String RSRP = "";// RSRP rsrp
	private String SINR = "";// SINR sinr

	private String N1EARFCN = "";// 邻区1频点 mLteNeighborhoodEarfcn
	private String N1PCI = "";// 邻区1PCI mLteNeighborhoodPCI
	private String N1RSRQ = "";// 邻区1RSRQ mLteNeighborhoodRsrq
	private String N1RSRP = "";// 邻区1RSRP mLteNeighborhoodRSRPOrSINR

	private String N2EARFCN = "";// 邻区2频点 同上
	private String N2PCI = "";// 邻区2PCI
	private String N2RSRQ = "";// 邻区2RSRQ
	private String N2RSRP = "";// 邻区2RSRP

	private String N3EARFCN = "";// 邻区3频点 同上
	private String N3PCI = "";// 邻区3PCI
	private String N3RSRQ = "";// 邻区3RSRQ
	private String N3RSRP = "";// 邻区3RSRP

	private String N4EARFCN = "";// 邻区4频点 同上
	private String N4PCI = "";// 邻区4PCI
	private String N4RSRQ = "";// 邻区4RSRQ
	private String N4RSRP = "";// 邻区4RSRP

	private String N5EARFCN = "";// 邻区5频点 同上
	private String N5PCI = "";// 邻区5PCI
	private String N5RSRQ = "";// 邻区5RSRQ
	private String N5RSRP = "";// 邻区5RSRP

	private String N6EARFCN = "";// 邻区6频点 同上
	private String N6PCI = "";// 邻区6PCI
	private String N6RSRQ = "";// 邻区6RSRQ
	private String N6RSRP = "";// 邻区6RSRP

	public String getTimestamp() {
		return Timestamp;
	}

	public void setTimestamp(String timestamp) {
		Timestamp = timestamp;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	public String getOPERATOR() {
		return OPERATOR;
	}

	public void setOPERATOR(String oPERATOR) {
		OPERATOR = oPERATOR;
	}

	public String getMCC() {
		return MCC;
	}

	public void setMCC(String mCC) {
		MCC = mCC;
	}

	public String getMNC() {
		return MNC;
	}

	public void setMNC(String mNC) {
		MNC = mNC;
	}

	public String getNET_TYPE() {
		return NET_TYPE;
	}

	public void setNET_TYPE(String nET_TYPE) {
		NET_TYPE = nET_TYPE;
	}

	public String getLON() {
		return LON;
	}

	public void setLON(String lON) {
		LON = lON;
	}

	public String getLAT() {
		return LAT;
	}

	public void setLAT(String lAT) {
		LAT = lAT;
	}

	public String getSPEED() {
		return SPEED;
	}

	public void setSPEED(String sPEED) {
		SPEED = sPEED;
	}

	public String getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(String hEIGHT) {
		HEIGHT = hEIGHT;
	}

	public String getEVENT() {
		return EVENT;
	}

	public void setEVENT(String eVENT) {
		EVENT = eVENT;
	}

	public String getUL_Throughput() {
		return UL_Throughput;
	}

	public void setUL_Throughput(String uL_Throughput) {
		UL_Throughput = uL_Throughput;
	}

	public String getDL_Throughput() {
		return DL_Throughput;
	}

	public void setDL_Throughput(String dL_Throughput) {
		DL_Throughput = dL_Throughput;
	}

	public String getPing_Delay() {
		return Ping_Delay;
	}

	public void setPing_Delay(String ping_Delay) {
		Ping_Delay = ping_Delay;
	}

	public String getHttp_Delay() {
		return Http_Delay;
	}

	public void setHttp_Delay(String http_Delay) {
		Http_Delay = http_Delay;
	}

	public String getCGI() {
		return CGI;
	}

	public void setCGI(String cGI) {
		CGI = cGI;
	}

	public String getENB() {
		return ENB;
	}

	public void setENB(String eNB) {
		ENB = eNB;
	}

	public String getCELL_NAME() {
		return CELL_NAME;
	}

	public void setCELL_NAME(String cELL_NAME) {
		CELL_NAME = cELL_NAME;
	}

	public String getCID() {
		return CID;
	}

	public void setCID(String cID) {
		CID = cID;
	}

	public String getTAC() {
		return TAC;
	}

	public void setTAC(String tAC) {
		TAC = tAC;
	}

	public String getEARFCN() {
		return EARFCN;
	}

	public void setEARFCN(String eARFCN) {
		EARFCN = eARFCN;
	}

	public String getPCI() {
		return PCI;
	}

	public void setPCI(String pCI) {
		PCI = pCI;
	}

	public String getRSRQ() {
		return RSRQ;
	}

	public void setRSRQ(String rSRQ) {
		RSRQ = rSRQ;
	}

	public String getRSRP() {
		return RSRP;
	}

	public void setRSRP(String rSRP) {
		RSRP = rSRP;
	}

	public String getSINR() {
		return SINR;
	}

	public void setSINR(String sINR) {
		SINR = sINR;
	}

	public String getN1EARFCN() {
		return N1EARFCN;
	}

	public void setN1EARFCN(String n1earfcn) {
		N1EARFCN = n1earfcn;
	}

	public String getN1PCI() {
		return N1PCI;
	}

	public void setN1PCI(String n1pci) {
		N1PCI = n1pci;
	}

	public String getN1RSRQ() {
		return N1RSRQ;
	}

	public void setN1RSRQ(String n1rsrq) {
		N1RSRQ = n1rsrq;
	}

	public String getN1RSRP() {
		return N1RSRP;
	}

	public void setN1RSRP(String n1rsrp) {
		N1RSRP = n1rsrp;
	}

	public String getN2EARFCN() {
		return N2EARFCN;
	}

	public void setN2EARFCN(String n2earfcn) {
		N2EARFCN = n2earfcn;
	}

	public String getN2PCI() {
		return N2PCI;
	}

	public void setN2PCI(String n2pci) {
		N2PCI = n2pci;
	}

	public String getN2RSRQ() {
		return N2RSRQ;
	}

	public void setN2RSRQ(String n2rsrq) {
		N2RSRQ = n2rsrq;
	}

	public String getN2RSRP() {
		return N2RSRP;
	}

	public void setN2RSRP(String n2rsrp) {
		N2RSRP = n2rsrp;
	}

	public String getN3EARFCN() {
		return N3EARFCN;
	}

	public void setN3EARFCN(String n3earfcn) {
		N3EARFCN = n3earfcn;
	}

	public String getN3PCI() {
		return N3PCI;
	}

	public void setN3PCI(String n3pci) {
		N3PCI = n3pci;
	}

	public String getN3RSRQ() {
		return N3RSRQ;
	}

	public void setN3RSRQ(String n3rsrq) {
		N3RSRQ = n3rsrq;
	}

	public String getN3RSRP() {
		return N3RSRP;
	}

	public void setN3RSRP(String n3rsrp) {
		N3RSRP = n3rsrp;
	}

	public String getN4EARFCN() {
		return N4EARFCN;
	}

	public void setN4EARFCN(String n4earfcn) {
		N4EARFCN = n4earfcn;
	}

	public String getN4PCI() {
		return N4PCI;
	}

	public void setN4PCI(String n4pci) {
		N4PCI = n4pci;
	}

	public String getN4RSRQ() {
		return N4RSRQ;
	}

	public void setN4RSRQ(String n4rsrq) {
		N4RSRQ = n4rsrq;
	}

	public String getN4RSRP() {
		return N4RSRP;
	}

	public void setN4RSRP(String n4rsrp) {
		N4RSRP = n4rsrp;
	}

	public String getN5EARFCN() {
		return N5EARFCN;
	}

	public void setN5EARFCN(String n5earfcn) {
		N5EARFCN = n5earfcn;
	}

	public String getN5PCI() {
		return N5PCI;
	}

	public void setN5PCI(String n5pci) {
		N5PCI = n5pci;
	}

	public String getN5RSRQ() {
		return N5RSRQ;
	}

	public void setN5RSRQ(String n5rsrq) {
		N5RSRQ = n5rsrq;
	}

	public String getN5RSRP() {
		return N5RSRP;
	}

	public void setN5RSRP(String n5rsrp) {
		N5RSRP = n5rsrp;
	}

	public String getN6EARFCN() {
		return N6EARFCN;
	}

	public void setN6EARFCN(String n6earfcn) {
		N6EARFCN = n6earfcn;
	}

	public String getN6PCI() {
		return N6PCI;
	}

	public void setN6PCI(String n6pci) {
		N6PCI = n6pci;
	}

	public String getN6RSRQ() {
		return N6RSRQ;
	}

	public void setN6RSRQ(String n6rsrq) {
		N6RSRQ = n6rsrq;
	}

	public String getN6RSRP() {
		return N6RSRP;
	}

	public void setN6RSRP(String n6rsrp) {
		N6RSRP = n6rsrp;
	}

	public SignalCsvLogBean(String timestamp, String pHONE, String oPERATOR, String mCC, String mNC, String nET_TYPE,
			String lON, String lAT, String sPEED, String hEIGHT, String eVENT, String uL_Throughput,
			String dL_Throughput, String ping_Delay, String http_Delay, String cGI, String eNB, String cELL_NAME,
			String cID, String tAC, String eARFCN, String pCI, String rSRQ, String rSRP, String sINR, String n1earfcn,
			String n1pci, String n1rsrq, String n1rsrp, String n2earfcn, String n2pci, String n2rsrq, String n2rsrp,
			String n3earfcn, String n3pci, String n3rsrq, String n3rsrp, String n4earfcn, String n4pci, String n4rsrq,
			String n4rsrp, String n5earfcn, String n5pci, String n5rsrq, String n5rsrp, String n6earfcn, String n6pci,
			String n6rsrq, String n6rsrp) {
		super();
		Timestamp = timestamp;
		PHONE = pHONE;
		OPERATOR = oPERATOR;
		MCC = mCC;
		MNC = mNC;
		NET_TYPE = nET_TYPE;
		LON = lON;
		LAT = lAT;
		SPEED = sPEED;
		HEIGHT = hEIGHT;
		EVENT = eVENT;
		UL_Throughput = uL_Throughput;
		DL_Throughput = dL_Throughput;
		Ping_Delay = ping_Delay;
		Http_Delay = http_Delay;
		CGI = cGI;
		ENB = eNB;
		CELL_NAME = cELL_NAME;
		CID = cID;
		TAC = tAC;
		EARFCN = eARFCN;
		PCI = pCI;
		RSRQ = rSRQ;
		RSRP = rSRP;
		SINR = sINR;
		N1EARFCN = n1earfcn;
		N1PCI = n1pci;
		N1RSRQ = n1rsrq;
		N1RSRP = n1rsrp;
		N2EARFCN = n2earfcn;
		N2PCI = n2pci;
		N2RSRQ = n2rsrq;
		N2RSRP = n2rsrp;
		N3EARFCN = n3earfcn;
		N3PCI = n3pci;
		N3RSRQ = n3rsrq;
		N3RSRP = n3rsrp;
		N4EARFCN = n4earfcn;
		N4PCI = n4pci;
		N4RSRQ = n4rsrq;
		N4RSRP = n4rsrp;
		N5EARFCN = n5earfcn;
		N5PCI = n5pci;
		N5RSRQ = n5rsrq;
		N5RSRP = n5rsrp;
		N6EARFCN = n6earfcn;
		N6PCI = n6pci;
		N6RSRQ = n6rsrq;
		N6RSRP = n6rsrp;
	}

	public SignalCsvLogBean() {
		super();
	}

}
