package com.boot.kaizen.business.es.model.fiveg.model;

import java.io.Serializable;
/**
 * 测试指标【对象】   root  4g
 * @author weichengz
 * @date 2020年4月26日 下午2:03:03
 */
public class ProLteDataInfoBeans implements Serializable{

	private static final long serialVersionUID = 1L;

	private String servingCellPccMcc 	;//	MCC
	private String servingCellPccMnc 	;//	MNC
	private String servingCellPccSiteEci 	;//	ECI
	private String servingCellPccBandIndex 	;//	Band
	private String servingCellPccBwDl 	;//	BandWidth
	private String servingCellPccFreqDl 	;//	Fequency DL
	private String servingCellPccTac 	;//	TAC
	private String servingCellPccSsp	;//	SSP
	private String servingCellPccSa	;//	SA
	private String servingCellPccCellId 	;//	CI
	private String servingCellPccEarfcnDl 	;//	EARFCN
	private String servingCellPccPci 	;//	PCI
	private String servingCellPccRsrp 	;//	RSRP
	private String servingCellPccSinr 	;//	SINR
	private String servingCellPccRsrq 	;//	RSRQ
	private String servingCellPccRssi 	;//	RSSI
	private String servingCellPccPuschTxpower 	;//	PUSCH TxPower
	private String servingCellPccPucchTxpower 	;//	PUCCH TxPower
	private String servingCellPccEnodebId 	;//	eNodeBID
	private String servingCellPccWidebandCqi 	;//	CQI
	private String servingCellPccMcsul	;//	MCS UL
	private String servingCellPccMcsdl	;//	MCS DL
	private String servingCellPccModul	;//	Mod UL
	private String servingCellPccModdl	;//	Mod DL
	private String servingCellPccULBLER 	;//	PUSCH BLER（%）
	private String servingCellPccDLBLER 	;//	PDSCH BLER Ser（%）
	private String servingCellPccGrantulnum	;//	Grant UL Num
	private String servingCellPccGrantdlnum	;//	Grant DL Num
	private String servingCellPccRankIndex 	;//	RI Num DL
	private String throughputPccPdcpUl 	;//	PDCP UL Thr
	private String throughputPccPdcpDl 	;//	PDCP DL Thr
	private String throughputPccRlcUl 	;//	RLC UL Thr
	private String throughputPccRlcDl 	;//	RLC DL Thr
	private String throughputPccMacUl 	;//	MAC UL Thr
	private String throughputPccMacDl 	;//	MAC DL Thr
	private String servingCellPccPuschRbs	;//	PUSCH RBs/Slots
	private String servingCellPccPdschRbs	;//	PDSCH RBs/Slots
	private String modulationPcc16qam 	;//	PCC16QAM Mod
	private String modulationPcc64qam 	;//	PCC64QAM Mod
	private String modulationPcc256qam 	;//	PCC256QAM Mod
	private String modulationPcc16qamUl 	;//	PCC16QAM UL Mod
	private String modulationPcc16qamDl 	;//	PCC16QAM DL Mod
	private String modulationPcc64qamUl 	;//	PCC64QAM UL Mod
	private String modulationPcc64qamDl 	;//	PCC64QAM DL Mod
	private String modulationPcc256qamUl 	;//	PCC256QAM UL Mod
	private String modulationPcc256qamDl 	;//	PCC256QAM DL Mod
	private String modulationPccQPSKUl 	;//	PCC QPSKUL Mod
	private String modulationPccQPSKDl 	;//	PCC QPSKDL Mod
	
	
	private ProLteNeighborhoodInfos proLteNeighborhoodInfos;


	public String getServingCellPccMcc() {
		return servingCellPccMcc;
	}


	public void setServingCellPccMcc(String servingCellPccMcc) {
		this.servingCellPccMcc = servingCellPccMcc;
	}


	public String getServingCellPccMnc() {
		return servingCellPccMnc;
	}


	public void setServingCellPccMnc(String servingCellPccMnc) {
		this.servingCellPccMnc = servingCellPccMnc;
	}


	public String getServingCellPccSiteEci() {
		return servingCellPccSiteEci;
	}


	public void setServingCellPccSiteEci(String servingCellPccSiteEci) {
		this.servingCellPccSiteEci = servingCellPccSiteEci;
	}


	public String getServingCellPccBandIndex() {
		return servingCellPccBandIndex;
	}


	public void setServingCellPccBandIndex(String servingCellPccBandIndex) {
		this.servingCellPccBandIndex = servingCellPccBandIndex;
	}


	public String getServingCellPccBwDl() {
		return servingCellPccBwDl;
	}


	public void setServingCellPccBwDl(String servingCellPccBwDl) {
		this.servingCellPccBwDl = servingCellPccBwDl;
	}


	public String getServingCellPccFreqDl() {
		return servingCellPccFreqDl;
	}


	public void setServingCellPccFreqDl(String servingCellPccFreqDl) {
		this.servingCellPccFreqDl = servingCellPccFreqDl;
	}


	public String getServingCellPccTac() {
		return servingCellPccTac;
	}


	public void setServingCellPccTac(String servingCellPccTac) {
		this.servingCellPccTac = servingCellPccTac;
	}


	public String getServingCellPccSsp() {
		return servingCellPccSsp;
	}


	public void setServingCellPccSsp(String servingCellPccSsp) {
		this.servingCellPccSsp = servingCellPccSsp;
	}


	public String getServingCellPccSa() {
		return servingCellPccSa;
	}


	public void setServingCellPccSa(String servingCellPccSa) {
		this.servingCellPccSa = servingCellPccSa;
	}


	public String getServingCellPccCellId() {
		return servingCellPccCellId;
	}


	public void setServingCellPccCellId(String servingCellPccCellId) {
		this.servingCellPccCellId = servingCellPccCellId;
	}


	public String getServingCellPccEarfcnDl() {
		return servingCellPccEarfcnDl;
	}


	public void setServingCellPccEarfcnDl(String servingCellPccEarfcnDl) {
		this.servingCellPccEarfcnDl = servingCellPccEarfcnDl;
	}


	public String getServingCellPccPci() {
		return servingCellPccPci;
	}


	public void setServingCellPccPci(String servingCellPccPci) {
		this.servingCellPccPci = servingCellPccPci;
	}


	public String getServingCellPccRsrp() {
		return servingCellPccRsrp;
	}


	public void setServingCellPccRsrp(String servingCellPccRsrp) {
		this.servingCellPccRsrp = servingCellPccRsrp;
	}


	public String getServingCellPccSinr() {
		return servingCellPccSinr;
	}


	public void setServingCellPccSinr(String servingCellPccSinr) {
		this.servingCellPccSinr = servingCellPccSinr;
	}


	public String getServingCellPccRsrq() {
		return servingCellPccRsrq;
	}


	public void setServingCellPccRsrq(String servingCellPccRsrq) {
		this.servingCellPccRsrq = servingCellPccRsrq;
	}


	public String getServingCellPccRssi() {
		return servingCellPccRssi;
	}


	public void setServingCellPccRssi(String servingCellPccRssi) {
		this.servingCellPccRssi = servingCellPccRssi;
	}


	public String getServingCellPccPuschTxpower() {
		return servingCellPccPuschTxpower;
	}


	public void setServingCellPccPuschTxpower(String servingCellPccPuschTxpower) {
		this.servingCellPccPuschTxpower = servingCellPccPuschTxpower;
	}


	public String getServingCellPccPucchTxpower() {
		return servingCellPccPucchTxpower;
	}


	public void setServingCellPccPucchTxpower(String servingCellPccPucchTxpower) {
		this.servingCellPccPucchTxpower = servingCellPccPucchTxpower;
	}


	public String getServingCellPccEnodebId() {
		return servingCellPccEnodebId;
	}


	public void setServingCellPccEnodebId(String servingCellPccEnodebId) {
		this.servingCellPccEnodebId = servingCellPccEnodebId;
	}


	public String getServingCellPccWidebandCqi() {
		return servingCellPccWidebandCqi;
	}


	public void setServingCellPccWidebandCqi(String servingCellPccWidebandCqi) {
		this.servingCellPccWidebandCqi = servingCellPccWidebandCqi;
	}


	public String getServingCellPccMcsul() {
		return servingCellPccMcsul;
	}


	public void setServingCellPccMcsul(String servingCellPccMcsul) {
		this.servingCellPccMcsul = servingCellPccMcsul;
	}


	public String getServingCellPccMcsdl() {
		return servingCellPccMcsdl;
	}


	public void setServingCellPccMcsdl(String servingCellPccMcsdl) {
		this.servingCellPccMcsdl = servingCellPccMcsdl;
	}


	public String getServingCellPccModul() {
		return servingCellPccModul;
	}


	public void setServingCellPccModul(String servingCellPccModul) {
		this.servingCellPccModul = servingCellPccModul;
	}


	public String getServingCellPccModdl() {
		return servingCellPccModdl;
	}


	public void setServingCellPccModdl(String servingCellPccModdl) {
		this.servingCellPccModdl = servingCellPccModdl;
	}


	public String getServingCellPccULBLER() {
		return servingCellPccULBLER;
	}


	public void setServingCellPccULBLER(String servingCellPccULBLER) {
		this.servingCellPccULBLER = servingCellPccULBLER;
	}


	public String getServingCellPccDLBLER() {
		return servingCellPccDLBLER;
	}


	public void setServingCellPccDLBLER(String servingCellPccDLBLER) {
		this.servingCellPccDLBLER = servingCellPccDLBLER;
	}


	public String getServingCellPccGrantulnum() {
		return servingCellPccGrantulnum;
	}


	public void setServingCellPccGrantulnum(String servingCellPccGrantulnum) {
		this.servingCellPccGrantulnum = servingCellPccGrantulnum;
	}


	public String getServingCellPccGrantdlnum() {
		return servingCellPccGrantdlnum;
	}


	public void setServingCellPccGrantdlnum(String servingCellPccGrantdlnum) {
		this.servingCellPccGrantdlnum = servingCellPccGrantdlnum;
	}


	public String getServingCellPccRankIndex() {
		return servingCellPccRankIndex;
	}


	public void setServingCellPccRankIndex(String servingCellPccRankIndex) {
		this.servingCellPccRankIndex = servingCellPccRankIndex;
	}


	public String getThroughputPccPdcpUl() {
		return throughputPccPdcpUl;
	}


	public void setThroughputPccPdcpUl(String throughputPccPdcpUl) {
		this.throughputPccPdcpUl = throughputPccPdcpUl;
	}


	public String getThroughputPccPdcpDl() {
		return throughputPccPdcpDl;
	}


	public void setThroughputPccPdcpDl(String throughputPccPdcpDl) {
		this.throughputPccPdcpDl = throughputPccPdcpDl;
	}


	public String getThroughputPccRlcUl() {
		return throughputPccRlcUl;
	}


	public void setThroughputPccRlcUl(String throughputPccRlcUl) {
		this.throughputPccRlcUl = throughputPccRlcUl;
	}


	public String getThroughputPccRlcDl() {
		return throughputPccRlcDl;
	}


	public void setThroughputPccRlcDl(String throughputPccRlcDl) {
		this.throughputPccRlcDl = throughputPccRlcDl;
	}


	public String getThroughputPccMacUl() {
		return throughputPccMacUl;
	}


	public void setThroughputPccMacUl(String throughputPccMacUl) {
		this.throughputPccMacUl = throughputPccMacUl;
	}


	public String getThroughputPccMacDl() {
		return throughputPccMacDl;
	}


	public void setThroughputPccMacDl(String throughputPccMacDl) {
		this.throughputPccMacDl = throughputPccMacDl;
	}


	public String getServingCellPccPuschRbs() {
		return servingCellPccPuschRbs;
	}


	public void setServingCellPccPuschRbs(String servingCellPccPuschRbs) {
		this.servingCellPccPuschRbs = servingCellPccPuschRbs;
	}


	public String getServingCellPccPdschRbs() {
		return servingCellPccPdschRbs;
	}


	public void setServingCellPccPdschRbs(String servingCellPccPdschRbs) {
		this.servingCellPccPdschRbs = servingCellPccPdschRbs;
	}


	public String getModulationPcc16qam() {
		return modulationPcc16qam;
	}


	public void setModulationPcc16qam(String modulationPcc16qam) {
		this.modulationPcc16qam = modulationPcc16qam;
	}


	public String getModulationPcc64qam() {
		return modulationPcc64qam;
	}


	public void setModulationPcc64qam(String modulationPcc64qam) {
		this.modulationPcc64qam = modulationPcc64qam;
	}


	public String getModulationPcc256qam() {
		return modulationPcc256qam;
	}


	public void setModulationPcc256qam(String modulationPcc256qam) {
		this.modulationPcc256qam = modulationPcc256qam;
	}


	public String getModulationPcc16qamUl() {
		return modulationPcc16qamUl;
	}


	public void setModulationPcc16qamUl(String modulationPcc16qamUl) {
		this.modulationPcc16qamUl = modulationPcc16qamUl;
	}


	public String getModulationPcc16qamDl() {
		return modulationPcc16qamDl;
	}


	public void setModulationPcc16qamDl(String modulationPcc16qamDl) {
		this.modulationPcc16qamDl = modulationPcc16qamDl;
	}


	public String getModulationPcc64qamUl() {
		return modulationPcc64qamUl;
	}


	public void setModulationPcc64qamUl(String modulationPcc64qamUl) {
		this.modulationPcc64qamUl = modulationPcc64qamUl;
	}


	public String getModulationPcc64qamDl() {
		return modulationPcc64qamDl;
	}


	public void setModulationPcc64qamDl(String modulationPcc64qamDl) {
		this.modulationPcc64qamDl = modulationPcc64qamDl;
	}


	public String getModulationPcc256qamUl() {
		return modulationPcc256qamUl;
	}


	public void setModulationPcc256qamUl(String modulationPcc256qamUl) {
		this.modulationPcc256qamUl = modulationPcc256qamUl;
	}


	public String getModulationPcc256qamDl() {
		return modulationPcc256qamDl;
	}


	public void setModulationPcc256qamDl(String modulationPcc256qamDl) {
		this.modulationPcc256qamDl = modulationPcc256qamDl;
	}


	public String getModulationPccQPSKUl() {
		return modulationPccQPSKUl;
	}


	public void setModulationPccQPSKUl(String modulationPccQPSKUl) {
		this.modulationPccQPSKUl = modulationPccQPSKUl;
	}


	public String getModulationPccQPSKDl() {
		return modulationPccQPSKDl;
	}


	public void setModulationPccQPSKDl(String modulationPccQPSKDl) {
		this.modulationPccQPSKDl = modulationPccQPSKDl;
	}


	public ProLteNeighborhoodInfos getProLteNeighborhoodInfos() {
		return proLteNeighborhoodInfos;
	}


	public void setProLteNeighborhoodInfos(ProLteNeighborhoodInfos proLteNeighborhoodInfos) {
		this.proLteNeighborhoodInfos = proLteNeighborhoodInfos;
	}


	public ProLteDataInfoBeans(String servingCellPccMcc, String servingCellPccMnc, String servingCellPccSiteEci,
			String servingCellPccBandIndex, String servingCellPccBwDl, String servingCellPccFreqDl,
			String servingCellPccTac, String servingCellPccSsp, String servingCellPccSa, String servingCellPccCellId,
			String servingCellPccEarfcnDl, String servingCellPccPci, String servingCellPccRsrp,
			String servingCellPccSinr, String servingCellPccRsrq, String servingCellPccRssi,
			String servingCellPccPuschTxpower, String servingCellPccPucchTxpower, String servingCellPccEnodebId,
			String servingCellPccWidebandCqi, String servingCellPccMcsul, String servingCellPccMcsdl,
			String servingCellPccModul, String servingCellPccModdl, String servingCellPccULBLER,
			String servingCellPccDLBLER, String servingCellPccGrantulnum, String servingCellPccGrantdlnum,
			String servingCellPccRankIndex, String throughputPccPdcpUl, String throughputPccPdcpDl,
			String throughputPccRlcUl, String throughputPccRlcDl, String throughputPccMacUl, String throughputPccMacDl,
			String servingCellPccPuschRbs, String servingCellPccPdschRbs, String modulationPcc16qam,
			String modulationPcc64qam, String modulationPcc256qam, String modulationPcc16qamUl,
			String modulationPcc16qamDl, String modulationPcc64qamUl, String modulationPcc64qamDl,
			String modulationPcc256qamUl, String modulationPcc256qamDl, String modulationPccQPSKUl,
			String modulationPccQPSKDl, ProLteNeighborhoodInfos proLteNeighborhoodInfos) {
		super();
		this.servingCellPccMcc = servingCellPccMcc;
		this.servingCellPccMnc = servingCellPccMnc;
		this.servingCellPccSiteEci = servingCellPccSiteEci;
		this.servingCellPccBandIndex = servingCellPccBandIndex;
		this.servingCellPccBwDl = servingCellPccBwDl;
		this.servingCellPccFreqDl = servingCellPccFreqDl;
		this.servingCellPccTac = servingCellPccTac;
		this.servingCellPccSsp = servingCellPccSsp;
		this.servingCellPccSa = servingCellPccSa;
		this.servingCellPccCellId = servingCellPccCellId;
		this.servingCellPccEarfcnDl = servingCellPccEarfcnDl;
		this.servingCellPccPci = servingCellPccPci;
		this.servingCellPccRsrp = servingCellPccRsrp;
		this.servingCellPccSinr = servingCellPccSinr;
		this.servingCellPccRsrq = servingCellPccRsrq;
		this.servingCellPccRssi = servingCellPccRssi;
		this.servingCellPccPuschTxpower = servingCellPccPuschTxpower;
		this.servingCellPccPucchTxpower = servingCellPccPucchTxpower;
		this.servingCellPccEnodebId = servingCellPccEnodebId;
		this.servingCellPccWidebandCqi = servingCellPccWidebandCqi;
		this.servingCellPccMcsul = servingCellPccMcsul;
		this.servingCellPccMcsdl = servingCellPccMcsdl;
		this.servingCellPccModul = servingCellPccModul;
		this.servingCellPccModdl = servingCellPccModdl;
		this.servingCellPccULBLER = servingCellPccULBLER;
		this.servingCellPccDLBLER = servingCellPccDLBLER;
		this.servingCellPccGrantulnum = servingCellPccGrantulnum;
		this.servingCellPccGrantdlnum = servingCellPccGrantdlnum;
		this.servingCellPccRankIndex = servingCellPccRankIndex;
		this.throughputPccPdcpUl = throughputPccPdcpUl;
		this.throughputPccPdcpDl = throughputPccPdcpDl;
		this.throughputPccRlcUl = throughputPccRlcUl;
		this.throughputPccRlcDl = throughputPccRlcDl;
		this.throughputPccMacUl = throughputPccMacUl;
		this.throughputPccMacDl = throughputPccMacDl;
		this.servingCellPccPuschRbs = servingCellPccPuschRbs;
		this.servingCellPccPdschRbs = servingCellPccPdschRbs;
		this.modulationPcc16qam = modulationPcc16qam;
		this.modulationPcc64qam = modulationPcc64qam;
		this.modulationPcc256qam = modulationPcc256qam;
		this.modulationPcc16qamUl = modulationPcc16qamUl;
		this.modulationPcc16qamDl = modulationPcc16qamDl;
		this.modulationPcc64qamUl = modulationPcc64qamUl;
		this.modulationPcc64qamDl = modulationPcc64qamDl;
		this.modulationPcc256qamUl = modulationPcc256qamUl;
		this.modulationPcc256qamDl = modulationPcc256qamDl;
		this.modulationPccQPSKUl = modulationPccQPSKUl;
		this.modulationPccQPSKDl = modulationPccQPSKDl;
		this.proLteNeighborhoodInfos = proLteNeighborhoodInfos;
	}


	public ProLteDataInfoBeans() {
		super();
	}

	
	
}
