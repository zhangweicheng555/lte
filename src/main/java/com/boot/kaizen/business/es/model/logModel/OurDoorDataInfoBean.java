package com.boot.kaizen.business.es.model.logModel;

import java.util.ArrayList;

public class OurDoorDataInfoBean {
	
	private ArrayList<MLteNeighborhoodInfo> mLteNeighborhoodInfos;
	private int sinr;
	private String ssSinr;
	private String rsrp;
	private String ssRsrp;
	private String rsrq;
	private String ssRsrq;
	private int earfcn;
	private int sim;
	private String nrEarfcn;
	private String ci;
	private String pci;
	private String nrPci;
	private double dl;
	private double ul;
	private String time;
	
	public ArrayList<MLteNeighborhoodInfo> getmLteNeighborhoodInfos() {
		return mLteNeighborhoodInfos;
	}
	public void setmLteNeighborhoodInfos(ArrayList<MLteNeighborhoodInfo> mLteNeighborhoodInfos) {
		this.mLteNeighborhoodInfos = mLteNeighborhoodInfos;
	}
	public int getSinr() {
		return sinr;
	}
	public void setSinr(int sinr) {
		this.sinr = sinr;
	}
	public String getSsSinr() {
		return ssSinr;
	}
	public void setSsSinr(String ssSinr) {
		this.ssSinr = ssSinr;
	}
	public String getRsrp() {
		return rsrp;
	}
	public void setRsrp(String rsrp) {
		this.rsrp = rsrp;
	}
	public String getSsRsrp() {
		return ssRsrp;
	}
	public void setSsRsrp(String ssRsrp) {
		this.ssRsrp = ssRsrp;
	}
	public String getRsrq() {
		return rsrq;
	}
	public void setRsrq(String rsrq) {
		this.rsrq = rsrq;
	}
	public String getSsRsrq() {
		return ssRsrq;
	}
	public void setSsRsrq(String ssRsrq) {
		this.ssRsrq = ssRsrq;
	}
	public int getEarfcn() {
		return earfcn;
	}
	public void setEarfcn(int earfcn) {
		this.earfcn = earfcn;
	}
	public int getSim() {
		return sim;
	}
	public void setSim(int sim) {
		this.sim = sim;
	}
	public String getNrEarfcn() {
		return nrEarfcn;
	}
	public void setNrEarfcn(String nrEarfcn) {
		this.nrEarfcn = nrEarfcn;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getPci() {
		return pci;
	}
	public void setPci(String pci) {
		this.pci = pci;
	}
	public String getNrPci() {
		return nrPci;
	}
	public void setNrPci(String nrPci) {
		this.nrPci = nrPci;
	}
	public double getDl() {
		return dl;
	}
	public void setDl(double dl) {
		this.dl = dl;
	}
	public double getUl() {
		return ul;
	}
	public void setUl(double ul) {
		this.ul = ul;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public OurDoorDataInfoBean(ArrayList<MLteNeighborhoodInfo> mLteNeighborhoodInfos, int sinr, String ssSinr,
			String rsrp, String ssRsrp, String rsrq, String ssRsrq, int earfcn, int sim, String nrEarfcn, String ci,
			String pci, String nrPci, double dl, double ul, String time) {
		super();
		this.mLteNeighborhoodInfos = mLteNeighborhoodInfos;
		this.sinr = sinr;
		this.ssSinr = ssSinr;
		this.rsrp = rsrp;
		this.ssRsrp = ssRsrp;
		this.rsrq = rsrq;
		this.ssRsrq = ssRsrq;
		this.earfcn = earfcn;
		this.sim = sim;
		this.nrEarfcn = nrEarfcn;
		this.ci = ci;
		this.pci = pci;
		this.nrPci = nrPci;
		this.dl = dl;
		this.ul = ul;
		this.time = time;
	}
	public OurDoorDataInfoBean() {
		super();
	}

	

}
