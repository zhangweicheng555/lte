package com.boot.kaizen.business.es.model.logModel;

/** 专业指标 */
public class ProIndicators {
	private String servingCellPcc;
	private String servingCellScc;
	private String servingCellPccDuplexMode;
	private String servingCellPccPaDb;
	private String servingCellPccMcc;
	private String servingCellPccBandIndex;
	private String servingCellPccMnc;
	private String servingCellPccRsrpAnt0;
	private String servingCellPccTac;
	private String servingCellPccRsrpAnt1;
	private String servingCellPccSiteEci;
	private String servingCellPccRsrqAnt0;
	private String servingCellPccEnodebId;
	private String servingCellPccRsrqAnt1;
	private String servingCellPccCellId;
	private String servingCellPccSinrAnt0;
	private String servingCellPccRsrp;
	private String servingCellPccSinrAnt1;
	private String servingCellPccRsrq;
	private String servingCellPccRssiAnt0;
	private String servingCellPccSinr;
	private String servingCellPccRssiAnt1;
	private String servingCellPccRssi;
	private String servingCellPccPuschTxpower;
	private String servingCellPccPci;
	private String servingCellPccPucchTxpower;
	private String servingCellPccCpType;
	private String servingCellPccRankIndex;
	private String servingCellPccBwUl;
	private String servingCellPccTransMode;
	private String servingCellPccBwDl;
	private String servingCellPccWidebandCqi;
	private String servingCellPccEarfcnUl;
	private String servingCellPccEarfcnDl;
	private String servingCellPccFreqUl;
	private String servingCellPccFreqDl;
	private String servingCellPccPathloss;
	private String servingCellSccPci;
	private String servingCellSccActiveStatus;
	private String servingCellSccRsrp;
	private String servingCellSccRsrpAnt0;
	private String servingCellSccRsrq;
	private String servingCellSccRsrpAnt1;
	private String servingCellSccSinr;
	private String servingCellSccRsrqAnt0;
	private String servingCellSccRssi;
	private String servingCellSccRsrqAnt1;
	private String servingCellSccBwUl;
	private String servingCellSccSinrAnt0;
	private String servingCellSccBwDl;
	private String servingCellSccSinrAnt1;
	private String servingCellSccEarfcnUl;
	private String servingCellSccRssiAnt0;
	private String servingCellSccEarfcnDl;
	private String servingCellSccRssiAnt1;

	private String throughputPccAppUl;
	private String throughputPccAppDl;
	private String throughputPccPdcpUl;
	private String throughputPccPdcpDl;
	private String throughputPccRlcUl;
	private String throughputPccRlcDl;
	private String throughputPccMacUl;
	private String throughputPccMacDl;
	private String throughputPccPhyUl;
	private String throughputPccPhyDl;
	private String throughputSccPhyDl;

	private String modulationPcc16qam;
	private String modulationPcc64qam;
	private String modulationPcc256qam;
	private String modulationScc16qam;
	private String modulationScc64qam;
	private String modulationScc256qam;

	private String modulationPcc16qamUl;
	private String modulationPcc16qamDl;
	private String modulationPcc64qamUl;
	private String modulationPcc64qamDl;
	private String modulationPcc256qamUl;
	private String modulationPcc256qamDl;
	private String modulationScc16qamUl;
	private String modulationScc16qamDl;
	private String modulationScc64qamUl;
	private String modulationScc64qamDl;
	private String modulationScc256qamUl;
	private String modulationScc256qamDl;

	private String modulationSccQPSKUl;
	private String modulationSccQPSKDl;

	private String modulationPccQPSKUl;
	private String modulationPccQPSKDl;

	private String servingCellPccULBLER;
	private String servingCellPccDLBLER;

	private String band;

	public ProIndicators() {
		this.servingCellPcc = "-";
		this.servingCellScc = "-";
		this.servingCellPccDuplexMode = "-";
		this.servingCellPccPaDb = "-";
		this.servingCellPccMcc = "-";
		this.servingCellPccBandIndex = "-";
		this.servingCellPccMnc = "-";
		this.servingCellPccRsrpAnt0 = "-";
		this.servingCellPccTac = "-";
		this.servingCellPccRsrpAnt1 = "-";
		this.servingCellPccSiteEci = "-";
		this.servingCellPccRsrqAnt0 = "-";
		this.servingCellPccEnodebId = "-";
		this.servingCellPccRsrqAnt1 = "-";
		this.servingCellPccCellId = "-";
		this.servingCellPccSinrAnt0 = "-";
		this.servingCellPccRsrp = "-";
		this.servingCellPccSinrAnt1 = "-";
		this.servingCellPccRsrq = "-";
		this.servingCellPccRssiAnt0 = "-";
		this.servingCellPccSinr = "-";
		this.servingCellPccRssiAnt1 = "-";
		this.servingCellPccRssi = "-";
		this.servingCellPccPuschTxpower = "-";
		this.servingCellPccPci = "-";
		this.servingCellPccPucchTxpower = "-";
		this.servingCellPccCpType = "-";
		this.servingCellPccRankIndex = "-";
		this.servingCellPccBwUl = "-";
		this.servingCellPccTransMode = "-";
		this.servingCellPccBwDl = "-";
		this.servingCellPccWidebandCqi = "-";
		this.servingCellPccEarfcnUl = "-";
		this.servingCellPccEarfcnDl = "-";
		this.servingCellPccFreqUl = "-";
		this.servingCellPccFreqDl = "-";
		this.servingCellPccPathloss = "-";
		this.servingCellSccPci = "-";
		this.servingCellSccActiveStatus = "deactive";
		this.servingCellSccRsrp = "-";
		this.servingCellSccRsrpAnt0 = "-";
		this.servingCellSccRsrq = "-";
		this.servingCellSccRsrpAnt1 = "-";
		this.servingCellSccSinr = "-";
		this.servingCellSccRsrqAnt0 = "-";
		this.servingCellSccRssi = "-";
		this.servingCellSccRsrqAnt1 = "-";
		this.servingCellSccBwUl = "-";
		this.servingCellSccSinrAnt0 = "-";
		this.servingCellSccBwDl = "-";
		this.servingCellSccSinrAnt1 = "-";
		this.servingCellSccEarfcnUl = "-";
		this.servingCellSccRssiAnt0 = "-";
		this.servingCellSccEarfcnDl = "-";
		this.servingCellSccRssiAnt1 = "-";
		this.throughputPccAppUl = "-";
		this.throughputPccAppDl = "-";
		this.throughputPccPdcpUl = "-";
		this.throughputPccPdcpDl = "-";
		this.throughputPccRlcUl = "-";
		this.throughputPccRlcDl = "-";
		this.throughputPccMacUl = "-";
		this.throughputPccMacDl = "-";
		this.throughputPccPhyUl = "-";
		this.throughputPccPhyDl = "-";
		this.throughputSccPhyDl = "-";
		this.modulationPcc16qam = "-";
		this.modulationPcc64qam = "-";
		this.modulationPcc256qam = "-";
		this.modulationScc16qam = "-";
		this.modulationScc64qam = "-";
		this.modulationScc256qam = "-";
		this.modulationPcc16qamUl = "-";
		this.modulationPcc16qamDl = "-";
		this.modulationPcc64qamUl = "-";
		this.modulationPcc64qamDl = "-";
		this.modulationPcc256qamUl = "-";
		this.modulationPcc256qamDl = "-";
		this.modulationScc16qamUl = "-";
		this.modulationScc16qamDl = "-";
		this.modulationScc64qamUl = "-";
		this.modulationScc64qamDl = "-";
		this.modulationScc256qamUl = "-";
		this.modulationScc256qamDl = "-";
		this.modulationSccQPSKUl = "-";
		this.modulationSccQPSKDl = "-";
		this.modulationPccQPSKUl = "-";
		this.modulationPccQPSKDl = "-";
		this.servingCellPccULBLER = "-";
		this.servingCellPccDLBLER = "-";
		this.band = "-";
	}

	public String getModulationPcc16qam() {
		return modulationPcc16qam;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getServingCellPcc() {
		return servingCellPcc;
	}

	public void setServingCellPcc(String servingCellPcc) {
		this.servingCellPcc = servingCellPcc;
	}

	public String getServingCellScc() {
		return servingCellScc;
	}

	public void setServingCellScc(String servingCellScc) {
		this.servingCellScc = servingCellScc;
	}

	public String getServingCellPccDuplexMode() {
		return servingCellPccDuplexMode;
	}

	public void setServingCellPccDuplexMode(String servingCellPccDuplexMode) {
		this.servingCellPccDuplexMode = servingCellPccDuplexMode;
	}

	public String getServingCellPccPaDb() {
		return servingCellPccPaDb;
	}

	public void setServingCellPccPaDb(String servingCellPccPaDb) {
		this.servingCellPccPaDb = servingCellPccPaDb;
	}

	public String getServingCellPccMcc() {
		return servingCellPccMcc;
	}

	public void setServingCellPccMcc(String servingCellPccMcc) {
		this.servingCellPccMcc = servingCellPccMcc;
	}

	public String getServingCellPccBandIndex() {
		return servingCellPccBandIndex;
	}

	public void setServingCellPccBandIndex(String servingCellPccBandIndex) {
		this.servingCellPccBandIndex = servingCellPccBandIndex;
	}

	public String getServingCellPccMnc() {
		return servingCellPccMnc;
	}

	public void setServingCellPccMnc(String servingCellPccMnc) {
		this.servingCellPccMnc = servingCellPccMnc;
	}

	public String getServingCellPccRsrpAnt0() {
		return servingCellPccRsrpAnt0;
	}

	public void setServingCellPccRsrpAnt0(String servingCellPccRsrpAnt0) {
		this.servingCellPccRsrpAnt0 = servingCellPccRsrpAnt0;
	}

	public String getServingCellPccTac() {
		return servingCellPccTac;
	}

	public void setServingCellPccTac(String servingCellPccTac) {
		this.servingCellPccTac = servingCellPccTac;
	}

	public String getServingCellPccRsrpAnt1() {
		return servingCellPccRsrpAnt1;
	}

	public void setServingCellPccRsrpAnt1(String servingCellPccRsrpAnt1) {
		this.servingCellPccRsrpAnt1 = servingCellPccRsrpAnt1;
	}

	public String getServingCellPccSiteEci() {
		return servingCellPccSiteEci;
	}

	public void setServingCellPccSiteEci(String servingCellPccSiteEci) {
		this.servingCellPccSiteEci = servingCellPccSiteEci;
	}

	public String getServingCellPccRsrqAnt0() {
		return servingCellPccRsrqAnt0;
	}

	public void setServingCellPccRsrqAnt0(String servingCellPccRsrqAnt0) {
		this.servingCellPccRsrqAnt0 = servingCellPccRsrqAnt0;
	}

	public String getServingCellPccEnodebId() {
		return servingCellPccEnodebId;
	}

	public void setServingCellPccEnodebId(String servingCellPccEnodebId) {
		this.servingCellPccEnodebId = servingCellPccEnodebId;
	}

	public String getServingCellPccRsrqAnt1() {
		return servingCellPccRsrqAnt1;
	}

	public void setServingCellPccRsrqAnt1(String servingCellPccRsrqAnt1) {
		this.servingCellPccRsrqAnt1 = servingCellPccRsrqAnt1;
	}

	public String getServingCellPccCellId() {
		return servingCellPccCellId;
	}

	public void setServingCellPccCellId(String servingCellPccCellId) {
		this.servingCellPccCellId = servingCellPccCellId;
	}

	public String getServingCellPccSinrAnt0() {
		return servingCellPccSinrAnt0;
	}

	public void setServingCellPccSinrAnt0(String servingCellPccSinrAnt0) {
		this.servingCellPccSinrAnt0 = servingCellPccSinrAnt0;
	}

	public String getServingCellPccRsrp() {
		return servingCellPccRsrp;
	}

	public void setServingCellPccRsrp(String servingCellPccRsrp) {
		this.servingCellPccRsrp = servingCellPccRsrp;
	}

	public String getServingCellPccSinrAnt1() {
		return servingCellPccSinrAnt1;
	}

	public void setServingCellPccSinrAnt1(String servingCellPccSinrAnt1) {
		this.servingCellPccSinrAnt1 = servingCellPccSinrAnt1;
	}

	public String getServingCellPccRsrq() {
		return servingCellPccRsrq;
	}

	public void setServingCellPccRsrq(String servingCellPccRsrq) {
		this.servingCellPccRsrq = servingCellPccRsrq;
	}

	public String getServingCellPccRssiAnt0() {
		return servingCellPccRssiAnt0;
	}

	public void setServingCellPccRssiAnt0(String servingCellPccRssiAnt0) {
		this.servingCellPccRssiAnt0 = servingCellPccRssiAnt0;
	}

	public String getServingCellPccSinr() {
		return servingCellPccSinr;
	}

	public void setServingCellPccSinr(String servingCellPccSinr) {
		this.servingCellPccSinr = servingCellPccSinr;
	}

	public String getServingCellPccRssiAnt1() {
		return servingCellPccRssiAnt1;
	}

	public void setServingCellPccRssiAnt1(String servingCellPccRssiAnt1) {
		this.servingCellPccRssiAnt1 = servingCellPccRssiAnt1;
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

	public String getServingCellPccPci() {
		return servingCellPccPci;
	}

	public void setServingCellPccPci(String servingCellPccPci) {
		this.servingCellPccPci = servingCellPccPci;
	}

	public String getServingCellPccPucchTxpower() {
		return servingCellPccPucchTxpower;
	}

	public void setServingCellPccPucchTxpower(String servingCellPccPucchTxpower) {
		this.servingCellPccPucchTxpower = servingCellPccPucchTxpower;
	}

	public String getServingCellPccCpType() {
		return servingCellPccCpType;
	}

	public void setServingCellPccCpType(String servingCellPccCpType) {
		this.servingCellPccCpType = servingCellPccCpType;
	}

	public String getServingCellPccRankIndex() {
		return servingCellPccRankIndex;
	}

	public void setServingCellPccRankIndex(String servingCellPccRankIndex) {
		this.servingCellPccRankIndex = servingCellPccRankIndex;
	}

	public String getServingCellPccBwUl() {
		return servingCellPccBwUl;
	}

	public void setServingCellPccBwUl(String servingCellPccBwUl) {
		this.servingCellPccBwUl = servingCellPccBwUl;
	}

	public String getServingCellPccTransMode() {
		return servingCellPccTransMode;
	}

	public void setServingCellPccTransMode(String servingCellPccTransMode) {
		this.servingCellPccTransMode = servingCellPccTransMode;
	}

	public String getServingCellPccBwDl() {
		return servingCellPccBwDl;
	}

	public void setServingCellPccBwDl(String servingCellPccBwDl) {
		this.servingCellPccBwDl = servingCellPccBwDl;
	}

	public String getServingCellPccWidebandCqi() {
		return servingCellPccWidebandCqi;
	}

	public void setServingCellPccWidebandCqi(String servingCellPccWidebandCqi) {
		this.servingCellPccWidebandCqi = servingCellPccWidebandCqi;
	}

	public String getServingCellPccEarfcnUl() {
		return servingCellPccEarfcnUl;
	}

	public void setServingCellPccEarfcnUl(String servingCellPccEarfcnUl) {
		this.servingCellPccEarfcnUl = servingCellPccEarfcnUl;
	}

	public String getServingCellPccEarfcnDl() {
		return servingCellPccEarfcnDl;
	}

	public void setServingCellPccEarfcnDl(String servingCellPccEarfcnDl) {
		this.servingCellPccEarfcnDl = servingCellPccEarfcnDl;
	}

	public String getServingCellPccFreqUl() {
		return servingCellPccFreqUl;
	}

	public void setServingCellPccFreqUl(String servingCellPccFreqUl) {
		this.servingCellPccFreqUl = servingCellPccFreqUl;
	}

	public String getServingCellPccFreqDl() {
		return servingCellPccFreqDl;
	}

	public void setServingCellPccFreqDl(String servingCellPccFreqDl) {
		this.servingCellPccFreqDl = servingCellPccFreqDl;
	}

	public String getServingCellPccPathloss() {
		return servingCellPccPathloss;
	}

	public void setServingCellPccPathloss(String servingCellPccPathloss) {
		this.servingCellPccPathloss = servingCellPccPathloss;
	}

	public String getServingCellSccPci() {
		return servingCellSccPci;
	}

	public void setServingCellSccPci(String servingCellSccPci) {
		this.servingCellSccPci = servingCellSccPci;
	}

	public String getServingCellSccActiveStatus() {
		return servingCellSccActiveStatus;
	}

	public void setServingCellSccActiveStatus(String servingCellSccActiveStatus) {
		this.servingCellSccActiveStatus = servingCellSccActiveStatus;
	}

	public String getServingCellSccRsrp() {
		return servingCellSccRsrp;
	}

	public void setServingCellSccRsrp(String servingCellSccRsrp) {
		this.servingCellSccRsrp = servingCellSccRsrp;
	}

	public String getServingCellSccRsrpAnt0() {
		return servingCellSccRsrpAnt0;
	}

	public void setServingCellSccRsrpAnt0(String servingCellSccRsrpAnt0) {
		this.servingCellSccRsrpAnt0 = servingCellSccRsrpAnt0;
	}

	public String getServingCellSccRsrq() {
		return servingCellSccRsrq;
	}

	public void setServingCellSccRsrq(String servingCellSccRsrq) {
		this.servingCellSccRsrq = servingCellSccRsrq;
	}

	public String getServingCellSccRsrpAnt1() {
		return servingCellSccRsrpAnt1;
	}

	public void setServingCellSccRsrpAnt1(String servingCellSccRsrpAnt1) {
		this.servingCellSccRsrpAnt1 = servingCellSccRsrpAnt1;
	}

	public String getServingCellSccSinr() {
		return servingCellSccSinr;
	}

	public void setServingCellSccSinr(String servingCellSccSinr) {
		this.servingCellSccSinr = servingCellSccSinr;
	}

	public String getServingCellSccRsrqAnt0() {
		return servingCellSccRsrqAnt0;
	}

	public void setServingCellSccRsrqAnt0(String servingCellSccRsrqAnt0) {
		this.servingCellSccRsrqAnt0 = servingCellSccRsrqAnt0;
	}

	public String getServingCellSccRssi() {
		return servingCellSccRssi;
	}

	public void setServingCellSccRssi(String servingCellSccRssi) {
		this.servingCellSccRssi = servingCellSccRssi;
	}

	public String getServingCellSccRsrqAnt1() {
		return servingCellSccRsrqAnt1;
	}

	public void setServingCellSccRsrqAnt1(String servingCellSccRsrqAnt1) {
		this.servingCellSccRsrqAnt1 = servingCellSccRsrqAnt1;
	}

	public String getServingCellSccBwUl() {
		return servingCellSccBwUl;
	}

	public void setServingCellSccBwUl(String servingCellSccBwUl) {
		this.servingCellSccBwUl = servingCellSccBwUl;
	}

	public String getServingCellSccSinrAnt0() {
		return servingCellSccSinrAnt0;
	}

	public void setServingCellSccSinrAnt0(String servingCellSccSinrAnt0) {
		this.servingCellSccSinrAnt0 = servingCellSccSinrAnt0;
	}

	public String getServingCellSccBwDl() {
		return servingCellSccBwDl;
	}

	public void setServingCellSccBwDl(String servingCellSccBwDl) {
		this.servingCellSccBwDl = servingCellSccBwDl;
	}

	public String getServingCellSccSinrAnt1() {
		return servingCellSccSinrAnt1;
	}

	public void setServingCellSccSinrAnt1(String servingCellSccSinrAnt1) {
		this.servingCellSccSinrAnt1 = servingCellSccSinrAnt1;
	}

	public String getServingCellSccEarfcnUl() {
		return servingCellSccEarfcnUl;
	}

	public void setServingCellSccEarfcnUl(String servingCellSccEarfcnUl) {
		this.servingCellSccEarfcnUl = servingCellSccEarfcnUl;
	}

	public String getServingCellSccRssiAnt0() {
		return servingCellSccRssiAnt0;
	}

	public void setServingCellSccRssiAnt0(String servingCellSccRssiAnt0) {
		this.servingCellSccRssiAnt0 = servingCellSccRssiAnt0;
	}

	public String getServingCellSccEarfcnDl() {
		return servingCellSccEarfcnDl;
	}

	public void setServingCellSccEarfcnDl(String servingCellSccEarfcnDl) {
		this.servingCellSccEarfcnDl = servingCellSccEarfcnDl;
	}

	public String getServingCellSccRssiAnt1() {
		return servingCellSccRssiAnt1;
	}

	public void setServingCellSccRssiAnt1(String servingCellSccRssiAnt1) {
		this.servingCellSccRssiAnt1 = servingCellSccRssiAnt1;
	}

	public String getThroughputPccAppUl() {
		return throughputPccAppUl;
	}

	public void setThroughputPccAppUl(String throughputPccAppUl) {
		this.throughputPccAppUl = throughputPccAppUl;
	}

	public String getThroughputPccAppDl() {
		return throughputPccAppDl;
	}

	public void setThroughputPccAppDl(String throughputPccAppDl) {
		this.throughputPccAppDl = throughputPccAppDl;
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

	public String getThroughputPccPhyUl() {
		return throughputPccPhyUl;
	}

	public void setThroughputPccPhyUl(String throughputPccPhyUl) {
		this.throughputPccPhyUl = throughputPccPhyUl;
	}

	public String getThroughputPccPhyDl() {
		return throughputPccPhyDl;
	}

	public void setThroughputPccPhyDl(String throughputPccPhyDl) {
		this.throughputPccPhyDl = throughputPccPhyDl;
	}

	public String getThroughputSccPhyDl() {
		return throughputSccPhyDl;
	}

	public void setThroughputSccPhyDl(String throughputSccPhyDl) {
		this.throughputSccPhyDl = throughputSccPhyDl;
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

	public String getModulationScc16qam() {
		return modulationScc16qam;
	}

	public void setModulationScc16qam(String modulationScc16qam) {
		this.modulationScc16qam = modulationScc16qam;
	}

	public String getModulationScc64qam() {
		return modulationScc64qam;
	}

	public void setModulationScc64qam(String modulationScc64qam) {
		this.modulationScc64qam = modulationScc64qam;
	}

	public String getModulationScc256qam() {
		return modulationScc256qam;
	}

	public void setModulationScc256qam(String modulationScc256qam) {
		this.modulationScc256qam = modulationScc256qam;
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

	public String getModulationScc16qamUl() {
		return modulationScc16qamUl;
	}

	public void setModulationScc16qamUl(String modulationScc16qamUl) {
		this.modulationScc16qamUl = modulationScc16qamUl;
	}

	public String getModulationScc16qamDl() {
		return modulationScc16qamDl;
	}

	public void setModulationScc16qamDl(String modulationScc16qamDl) {
		this.modulationScc16qamDl = modulationScc16qamDl;
	}

	public String getModulationScc64qamUl() {
		return modulationScc64qamUl;
	}

	public void setModulationScc64qamUl(String modulationScc64qamUl) {
		this.modulationScc64qamUl = modulationScc64qamUl;
	}

	public String getModulationScc64qamDl() {
		return modulationScc64qamDl;
	}

	public void setModulationScc64qamDl(String modulationScc64qamDl) {
		this.modulationScc64qamDl = modulationScc64qamDl;
	}

	public String getModulationScc256qamUl() {
		return modulationScc256qamUl;
	}

	public void setModulationScc256qamUl(String modulationScc256qamUl) {
		this.modulationScc256qamUl = modulationScc256qamUl;
	}

	public String getModulationScc256qamDl() {
		return modulationScc256qamDl;
	}

	public void setModulationScc256qamDl(String modulationScc256qamDl) {
		this.modulationScc256qamDl = modulationScc256qamDl;
	}

	public String getModulationSccQPSKUl() {
		return modulationSccQPSKUl;
	}

	public void setModulationSccQPSKUl(String modulationSccQPSKUl) {
		this.modulationSccQPSKUl = modulationSccQPSKUl;
	}

	public String getModulationSccQPSKDl() {
		return modulationSccQPSKDl;
	}

	public void setModulationSccQPSKDl(String modulationSccQPSKDl) {
		this.modulationSccQPSKDl = modulationSccQPSKDl;
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
}
