package com.boot.kaizen.business.es.model.logModel;

import java.util.ArrayList;

public class OurDoorDataInfoBean {
	
	private ArrayList<MLteNeighborhoodInfo> mLteNeighborhoodInfos;
	private int sinr;
	private String rsrp;
	private String rsrq;
	private int earfcn;
	private String ci;
	private String pci;
	private double dl;
	private double ul;
	private String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRsrp() {
		return rsrp;
	}

	public void setRsrp(String rsrp) {
		this.rsrp = rsrp;
	}

	public String getRsrq() {
		return rsrq;
	}

	public void setRsrq(String rsrq) {
		this.rsrq = rsrq;
	}

	public String getPci() {
		return pci;
	}

	public int getEarfcn() {
		return earfcn;
	}

	public void setEarfcn(int earfcn) {
		this.earfcn = earfcn;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public void setPci(String pci) {
		this.pci = pci;
	}

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

	public OurDoorDataInfoBean() {
	}

}
