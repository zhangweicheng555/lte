package com.boot.kaizen.business.es.model.logModel;

public class PingzbBean {
	private String qqcs;
	private String cgcs;
	private String cgl;
	private String zdsy;
	private String zxsy;
	private String pjsy;

	public PingzbBean() {
	}

	public PingzbBean(String qqcs, String cgcs, String cgl, String zdsy, String zxsy, String pjsy) {
		this.qqcs = qqcs;
		this.cgcs = cgcs;
		this.cgl = cgl;
		this.zdsy = zdsy;
		this.zxsy = zxsy;
		this.pjsy = pjsy;
	}

	public String getQqcs() {
		return qqcs;
	}

	public void setQqcs(String qqcs) {
		this.qqcs = qqcs;
	}

	public String getCgcs() {
		return cgcs;
	}

	public void setCgcs(String cgcs) {
		this.cgcs = cgcs;
	}

	public String getCgl() {
		return cgl;
	}

	public void setCgl(String cgl) {
		this.cgl = cgl;
	}

	public String getZdsy() {
		return zdsy;
	}

	public void setZdsy(String zdsy) {
		this.zdsy = zdsy;
	}

	public String getZxsy() {
		return zxsy;
	}

	public void setZxsy(String zxsy) {
		this.zxsy = zxsy;
	}

	public String getPjsy() {
		return pjsy;
	}

	public void setPjsy(String pjsy) {
		this.pjsy = pjsy;
	}

}
