package com.boot.kaizen.business.es.model.logModel;

import java.io.Serializable;

/** 专业指标 */
public class ProIndicatorsSimple implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//一下显示 start
	private String servingCellPccTac;
	private String servingCellPccFreqDl;
	private String servingCellPccEnodebId;
	private String servingCellPccSiteEci;
	private String servingCellPccRsrp;
	private String servingCellPccRsrq;
	private String servingCellPccWidebandCqi;
	private String servingCellPccPucchTxpower;
	private String servingCellPccULBLER;
	private String servingCellPccBandIndex;
	private String servingCellPccEarfcnDl;
	private String servingCellPccCellId;
	private String servingCellPccPci;
	private String servingCellPccSinr;
	private String servingCellPccRssi;
	private String servingCellPccRankIndex;
	private String servingCellPccPuschTxpower;
	private String servingCellPccDLBLER;
	//stop
	
	
	//start  DL
		private String throughputPccPdcpDl;
		private String throughputPccRlcDl;
		private String throughputPccMacDl;
		private String throughputPccPhyDl;
		
		//start UL
		private String throughputPccPdcpUl;
		private String throughputPccRlcUl;
		private String throughputPccMacUl;
		private String throughputPccPhyUl;
	
	
	
	public ProIndicatorsSimple(String servingCellPccTac, String servingCellPccFreqDl, String servingCellPccEnodebId,
				String servingCellPccSiteEci, String servingCellPccRsrp, String servingCellPccRsrq,
				String servingCellPccWidebandCqi, String servingCellPccPucchTxpower, String servingCellPccULBLER,
				String servingCellPccBandIndex, String servingCellPccEarfcnDl, String servingCellPccCellId,
				String servingCellPccPci, String servingCellPccSinr, String servingCellPccRssi,
				String servingCellPccRankIndex, String servingCellPccPuschTxpower, String servingCellPccDLBLER,
				String throughputPccPdcpDl, String throughputPccRlcDl, String throughputPccMacDl,
				String throughputPccPhyDl, String throughputPccPdcpUl, String throughputPccRlcUl,
				String throughputPccMacUl, String throughputPccPhyUl) {
			super();
			this.servingCellPccTac = servingCellPccTac;
			this.servingCellPccFreqDl = servingCellPccFreqDl;
			this.servingCellPccEnodebId = servingCellPccEnodebId;
			this.servingCellPccSiteEci = servingCellPccSiteEci;
			this.servingCellPccRsrp = servingCellPccRsrp;
			this.servingCellPccRsrq = servingCellPccRsrq;
			this.servingCellPccWidebandCqi = servingCellPccWidebandCqi;
			this.servingCellPccPucchTxpower = servingCellPccPucchTxpower;
			this.servingCellPccULBLER = servingCellPccULBLER;
			this.servingCellPccBandIndex = servingCellPccBandIndex;
			this.servingCellPccEarfcnDl = servingCellPccEarfcnDl;
			this.servingCellPccCellId = servingCellPccCellId;
			this.servingCellPccPci = servingCellPccPci;
			this.servingCellPccSinr = servingCellPccSinr;
			this.servingCellPccRssi = servingCellPccRssi;
			this.servingCellPccRankIndex = servingCellPccRankIndex;
			this.servingCellPccPuschTxpower = servingCellPccPuschTxpower;
			this.servingCellPccDLBLER = servingCellPccDLBLER;
			this.throughputPccPdcpDl = throughputPccPdcpDl;
			this.throughputPccRlcDl = throughputPccRlcDl;
			this.throughputPccMacDl = throughputPccMacDl;
			this.throughputPccPhyDl = throughputPccPhyDl;
			this.throughputPccPdcpUl = throughputPccPdcpUl;
			this.throughputPccRlcUl = throughputPccRlcUl;
			this.throughputPccMacUl = throughputPccMacUl;
			this.throughputPccPhyUl = throughputPccPhyUl;
		}
	public String getThroughputPccPdcpDl() {
			return throughputPccPdcpDl;
		}
		public void setThroughputPccPdcpDl(String throughputPccPdcpDl) {
			this.throughputPccPdcpDl = throughputPccPdcpDl;
		}
		public String getThroughputPccRlcDl() {
			return throughputPccRlcDl;
		}
		public void setThroughputPccRlcDl(String throughputPccRlcDl) {
			this.throughputPccRlcDl = throughputPccRlcDl;
		}
		public String getThroughputPccMacDl() {
			return throughputPccMacDl;
		}
		public void setThroughputPccMacDl(String throughputPccMacDl) {
			this.throughputPccMacDl = throughputPccMacDl;
		}
		public String getThroughputPccPhyDl() {
			return throughputPccPhyDl;
		}
		public void setThroughputPccPhyDl(String throughputPccPhyDl) {
			this.throughputPccPhyDl = throughputPccPhyDl;
		}
		public String getThroughputPccPdcpUl() {
			return throughputPccPdcpUl;
		}
		public void setThroughputPccPdcpUl(String throughputPccPdcpUl) {
			this.throughputPccPdcpUl = throughputPccPdcpUl;
		}
		public String getThroughputPccRlcUl() {
			return throughputPccRlcUl;
		}
		public void setThroughputPccRlcUl(String throughputPccRlcUl) {
			this.throughputPccRlcUl = throughputPccRlcUl;
		}
		public String getThroughputPccMacUl() {
			return throughputPccMacUl;
		}
		public void setThroughputPccMacUl(String throughputPccMacUl) {
			this.throughputPccMacUl = throughputPccMacUl;
		}
		public String getThroughputPccPhyUl() {
			return throughputPccPhyUl;
		}
		public void setThroughputPccPhyUl(String throughputPccPhyUl) {
			this.throughputPccPhyUl = throughputPccPhyUl;
		}
	public String getServingCellPccTac() {
		return servingCellPccTac;
	}
	public void setServingCellPccTac(String servingCellPccTac) {
		this.servingCellPccTac = servingCellPccTac;
	}
	public String getServingCellPccFreqDl() {
		return servingCellPccFreqDl;
	}
	public void setServingCellPccFreqDl(String servingCellPccFreqDl) {
		this.servingCellPccFreqDl = servingCellPccFreqDl;
	}
	public String getServingCellPccEnodebId() {
		return servingCellPccEnodebId;
	}
	public void setServingCellPccEnodebId(String servingCellPccEnodebId) {
		this.servingCellPccEnodebId = servingCellPccEnodebId;
	}
	public String getServingCellPccSiteEci() {
		return servingCellPccSiteEci;
	}
	public void setServingCellPccSiteEci(String servingCellPccSiteEci) {
		this.servingCellPccSiteEci = servingCellPccSiteEci;
	}
	public String getServingCellPccRsrp() {
		return servingCellPccRsrp;
	}
	public void setServingCellPccRsrp(String servingCellPccRsrp) {
		this.servingCellPccRsrp = servingCellPccRsrp;
	}
	public String getServingCellPccRsrq() {
		return servingCellPccRsrq;
	}
	public void setServingCellPccRsrq(String servingCellPccRsrq) {
		this.servingCellPccRsrq = servingCellPccRsrq;
	}
	public String getServingCellPccWidebandCqi() {
		return servingCellPccWidebandCqi;
	}
	public void setServingCellPccWidebandCqi(String servingCellPccWidebandCqi) {
		this.servingCellPccWidebandCqi = servingCellPccWidebandCqi;
	}
	public String getServingCellPccPucchTxpower() {
		return servingCellPccPucchTxpower;
	}
	public void setServingCellPccPucchTxpower(String servingCellPccPucchTxpower) {
		this.servingCellPccPucchTxpower = servingCellPccPucchTxpower;
	}
	public String getServingCellPccULBLER() {
		return servingCellPccULBLER;
	}
	public void setServingCellPccULBLER(String servingCellPccULBLER) {
		this.servingCellPccULBLER = servingCellPccULBLER;
	}
	public String getServingCellPccBandIndex() {
		return servingCellPccBandIndex;
	}
	public void setServingCellPccBandIndex(String servingCellPccBandIndex) {
		this.servingCellPccBandIndex = servingCellPccBandIndex;
	}
	public String getServingCellPccEarfcnDl() {
		return servingCellPccEarfcnDl;
	}
	public void setServingCellPccEarfcnDl(String servingCellPccEarfcnDl) {
		this.servingCellPccEarfcnDl = servingCellPccEarfcnDl;
	}
	public String getServingCellPccCellId() {
		return servingCellPccCellId;
	}
	public void setServingCellPccCellId(String servingCellPccCellId) {
		this.servingCellPccCellId = servingCellPccCellId;
	}
	public String getServingCellPccPci() {
		return servingCellPccPci;
	}
	public void setServingCellPccPci(String servingCellPccPci) {
		this.servingCellPccPci = servingCellPccPci;
	}
	public String getServingCellPccSinr() {
		return servingCellPccSinr;
	}
	public void setServingCellPccSinr(String servingCellPccSinr) {
		this.servingCellPccSinr = servingCellPccSinr;
	}
	public String getServingCellPccRssi() {
		return servingCellPccRssi;
	}
	public void setServingCellPccRssi(String servingCellPccRssi) {
		this.servingCellPccRssi = servingCellPccRssi;
	}
	public String getServingCellPccRankIndex() {
		return servingCellPccRankIndex;
	}
	public void setServingCellPccRankIndex(String servingCellPccRankIndex) {
		this.servingCellPccRankIndex = servingCellPccRankIndex;
	}
	public String getServingCellPccPuschTxpower() {
		return servingCellPccPuschTxpower;
	}
	public void setServingCellPccPuschTxpower(String servingCellPccPuschTxpower) {
		this.servingCellPccPuschTxpower = servingCellPccPuschTxpower;
	}
	public String getServingCellPccDLBLER() {
		return servingCellPccDLBLER;
	}
	public void setServingCellPccDLBLER(String servingCellPccDLBLER) {
		this.servingCellPccDLBLER = servingCellPccDLBLER;
	}
	public ProIndicatorsSimple(String servingCellPccTac, String servingCellPccFreqDl, String servingCellPccEnodebId,
			String servingCellPccSiteEci, String servingCellPccRsrp, String servingCellPccRsrq,
			String servingCellPccWidebandCqi, String servingCellPccPucchTxpower, String servingCellPccULBLER,
			String servingCellPccBandIndex, String servingCellPccEarfcnDl, String servingCellPccCellId,
			String servingCellPccPci, String servingCellPccSinr, String servingCellPccRssi,
			String servingCellPccRankIndex, String servingCellPccPuschTxpower, String servingCellPccDLBLER) {
		super();
		this.servingCellPccTac = servingCellPccTac;
		this.servingCellPccFreqDl = servingCellPccFreqDl;
		this.servingCellPccEnodebId = servingCellPccEnodebId;
		this.servingCellPccSiteEci = servingCellPccSiteEci;
		this.servingCellPccRsrp = servingCellPccRsrp;
		this.servingCellPccRsrq = servingCellPccRsrq;
		this.servingCellPccWidebandCqi = servingCellPccWidebandCqi;
		this.servingCellPccPucchTxpower = servingCellPccPucchTxpower;
		this.servingCellPccULBLER = servingCellPccULBLER;
		this.servingCellPccBandIndex = servingCellPccBandIndex;
		this.servingCellPccEarfcnDl = servingCellPccEarfcnDl;
		this.servingCellPccCellId = servingCellPccCellId;
		this.servingCellPccPci = servingCellPccPci;
		this.servingCellPccSinr = servingCellPccSinr;
		this.servingCellPccRssi = servingCellPccRssi;
		this.servingCellPccRankIndex = servingCellPccRankIndex;
		this.servingCellPccPuschTxpower = servingCellPccPuschTxpower;
		this.servingCellPccDLBLER = servingCellPccDLBLER;
	}
	
	public ProIndicatorsSimple(ProIndicators proIndicators) {
		if (proIndicators !=null) {
			this.servingCellPccTac = proIndicators.getServingCellPccTac();
			this.servingCellPccFreqDl = proIndicators.getServingCellPccFreqDl();
			this.servingCellPccEnodebId = proIndicators.getServingCellPccEnodebId();
			this.servingCellPccSiteEci = proIndicators.getServingCellPccSiteEci();
			this.servingCellPccRsrp = proIndicators.getServingCellPccRsrp();
			this.servingCellPccRsrq = proIndicators.getServingCellPccRsrq();
			this.servingCellPccWidebandCqi = proIndicators.getServingCellPccWidebandCqi();
			this.servingCellPccPucchTxpower = proIndicators.getServingCellPccPucchTxpower();
			this.servingCellPccULBLER = proIndicators.getServingCellPccULBLER();
			this.servingCellPccBandIndex = proIndicators.getServingCellPccBandIndex();
			this.servingCellPccEarfcnDl = proIndicators.getServingCellPccEarfcnDl();
			this.servingCellPccCellId = proIndicators.getServingCellPccCellId();
			this.servingCellPccPci = proIndicators.getServingCellPccPci();
			this.servingCellPccSinr = proIndicators.getServingCellPccSinr();
			this.servingCellPccRssi = proIndicators.getServingCellPccRssi();
			this.servingCellPccRankIndex = proIndicators.getServingCellPccRankIndex();
			this.servingCellPccPuschTxpower = proIndicators.getServingCellPccPuschTxpower();
			this.servingCellPccDLBLER = proIndicators.getServingCellPccDLBLER();
			
			this.throughputPccPdcpDl = proIndicators.getThroughputPccPdcpDl();
			this.throughputPccRlcDl = proIndicators.getThroughputPccRlcDl();
			this.throughputPccMacDl = proIndicators.getThroughputPccMacDl();
			this.throughputPccPhyDl = proIndicators.getThroughputPccPhyDl();
			this.throughputPccPdcpUl = proIndicators.getThroughputPccPdcpUl();
			this.throughputPccRlcUl = proIndicators.getThroughputPccRlcUl();
			this.throughputPccMacUl = proIndicators.getThroughputPccMacUl();
			this.throughputPccPhyUl = proIndicators.getThroughputPccPhyUl();
		
		}
	}
	
	
	
}
