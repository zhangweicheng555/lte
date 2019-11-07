package com.boot.kaizen.business.es.model.logModel;

import java.util.ArrayList;

public class FtpzbBean {
	private String xzzl;
	private String cszl;
	private int pppRequestCount;
	private int testTime;
	private int pppRequestFaileCount;
	private String downloadAvg;
	private String downloadMax;
	private String uploadAvg;
	private String uploadMax;

	private ArrayList<Double> UList;
	private ArrayList<Double> DList;

	public String getDownloadAvg() {
		return downloadAvg;
	}

	public void setDownloadAvg(String downloadAvg) {
		this.downloadAvg = downloadAvg;
	}

	public String getDownloadMax() {
		return downloadMax;
	}

	public void setDownloadMax(String downloadMax) {
		this.downloadMax = downloadMax;
	}

	public String getUploadAvg() {
		return uploadAvg;
	}

	public void setUploadAvg(String uploadAvg) {
		this.uploadAvg = uploadAvg;
	}

	public String getUploadMax() {
		return uploadMax;
	}

	public void setUploadMax(String uploadMax) {
		this.uploadMax = uploadMax;
	}

	public FtpzbBean() {
	}

	public FtpzbBean(String xzzl, String cszl, int pppRequestCount, int pppRequestFaileCount, int testTime,
			ArrayList<Double> UList, ArrayList<Double> DList) {
		this.xzzl = xzzl;
		this.cszl = cszl;

		this.testTime = testTime;
		this.pppRequestCount = pppRequestCount;
		this.pppRequestFaileCount = pppRequestFaileCount;
		this.UList = UList;
		this.DList = DList;
	}

	public FtpzbBean(String xzzl, String cszl, int pppRequestCount, int pppRequestFaileCount, int testTime,
			String downloadAvg, String downloadMax, String uploadAvg, String uploadMax, ArrayList<Double> UList,
			ArrayList<Double> DList) {
		this.xzzl = xzzl;
		this.cszl = cszl;

		this.downloadAvg = downloadAvg;
		this.downloadMax = downloadMax;
		this.uploadAvg = uploadAvg;
		this.uploadMax = uploadMax;

		this.testTime = testTime;
		this.pppRequestCount = pppRequestCount;
		this.pppRequestFaileCount = pppRequestFaileCount;
		this.UList = UList;
		this.DList = DList;
	}

	public FtpzbBean(String xzzl, String cszl) {
		this.xzzl = xzzl;
		this.cszl = cszl;

	}

	public int getPppRequestCount() {
		return pppRequestCount;
	}

	public void setPppRequestCount(int pppRequestCount) {
		this.pppRequestCount = pppRequestCount;
	}

	public int getTestTime() {
		return testTime;
	}

	public void setTestTime(int testTime) {
		this.testTime = testTime;
	}

	public int getPppRequestFaileCount() {
		return pppRequestFaileCount;
	}

	public void setPppRequestFaileCount(int pppRequestFaileCount) {
		this.pppRequestFaileCount = pppRequestFaileCount;
	}

	public String getXzzl() {
		return xzzl;
	}

	public void setXzzl(String xzzl) {
		this.xzzl = xzzl;
	}

	public String getCszl() {
		return cszl;
	}

	public void setCszl(String cszl) {
		this.cszl = cszl;
	}

	public ArrayList<Double> getUList() {
		return UList;
	}

	public void setUList(ArrayList<Double> UList) {
		this.UList = UList;
	}

	public ArrayList<Double> getDList() {
		return DList;
	}

	public void setDList(ArrayList<Double> DList) {
		this.DList = DList;
	}

}
