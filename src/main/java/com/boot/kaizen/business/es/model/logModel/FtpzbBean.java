package com.boot.kaizen.business.es.model.logModel;


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

	public FtpzbBean(String xzzl, String cszl, int pppRequestCount, int testTime, int pppRequestFaileCount,
			String downloadAvg, String downloadMax, String uploadAvg, String uploadMax) {
		super();
		this.xzzl = xzzl;
		this.cszl = cszl;
		this.pppRequestCount = pppRequestCount;
		this.testTime = testTime;
		this.pppRequestFaileCount = pppRequestFaileCount;
		this.downloadAvg = downloadAvg;
		this.downloadMax = downloadMax;
		this.uploadAvg = uploadAvg;
		this.uploadMax = uploadMax;
	}

	public FtpzbBean() {
		super();
	}

}
