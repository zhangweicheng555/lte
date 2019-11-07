package com.boot.kaizen.business.es.model.logModel;

import java.util.ArrayList;

public class WxzbBean {
	private String rsrqd;
	private String rsrqx;
	private String rsrqp;
	private String rsrpd;
	private String rsrpx;
	private String rsrpp;
	private String sinrd;
	private String sinrx;
	private String sinrp;
	private String cssc;
	private String fgl;
	private String fglc;
	private String zlc;
	private String lcfgl;

	private ArrayList<Integer> rsrpList;
	private ArrayList<Integer> sinrList;

	public WxzbBean() {

	}

	public WxzbBean(String rsrqd, String rsrqx, String rsrqp, String rsrpd, String rsrpx, String rsrpp, String sinrd,
			String sinrx, String sinrp, String cssc, String fgl, String fglc, String zlc, String lcfgl) {
		this.rsrqd = rsrqd;
		this.rsrqx = rsrqx;
		this.rsrqp = rsrqp;
		this.rsrpd = rsrpd;
		this.rsrpx = rsrpx;
		this.rsrpp = rsrpp;
		this.sinrp = sinrp;
		this.sinrd = sinrd;
		this.sinrx = sinrx;
		this.cssc = cssc;
		this.fgl = fgl;
		this.fglc = fglc;
		this.zlc = zlc;
		this.lcfgl = lcfgl;

	}

	public String getRsrqd() {
		return rsrqd;
	}

	public void setRsrqd(String rsrqd) {
		this.rsrqd = rsrqd;
	}

	public String getRsrqx() {
		return rsrqx;
	}

	public void setRsrqx(String rsrqx) {
		this.rsrqx = rsrqx;
	}

	public String getRsrqp() {
		return rsrqp;
	}

	public void setRsrqp(String rsrqp) {
		this.rsrqp = rsrqp;
	}

	public String getRsrpd() {
		return rsrpd;
	}

	public void setRsrpd(String rsrpd) {
		this.rsrpd = rsrpd;
	}

	public String getRsrpx() {
		return rsrpx;
	}

	public void setRsrpx(String rsrpx) {
		this.rsrpx = rsrpx;
	}

	public String getRsrpp() {
		return rsrpp;
	}

	public void setRsrpp(String rsrpp) {
		this.rsrpp = rsrpp;
	}

	public String getSinrd() {
		return sinrd;
	}

	public void setSinrd(String sinrd) {
		this.sinrd = sinrd;
	}

	public String getSinrx() {
		return sinrx;
	}

	public void setSinrx(String sinrx) {
		this.sinrx = sinrx;
	}

	public String getSinrp() {
		return sinrp;
	}

	public void setSinrp(String sinrp) {
		this.sinrp = sinrp;
	}

	public String getCssc() {
		return cssc;
	}

	public void setCssc(String cssc) {
		this.cssc = cssc;
	}

	public String getFgl() {
		return fgl;
	}

	public void setFgl(String fgl) {
		this.fgl = fgl;
	}

	public String getFglc() {
		return fglc;
	}

	public void setFglc(String fglc) {
		this.fglc = fglc;
	}

	public String getZlc() {
		return zlc;
	}

	public void setZlc(String zlc) {
		this.zlc = zlc;
	}

	public String getLcfgl() {
		return lcfgl;
	}

	public void setLcfgl(String lcfgl) {
		this.lcfgl = lcfgl;
	}

	public ArrayList<Integer> getRsrpList() {
		return rsrpList;
	}

	public void setRsrpList(ArrayList<Integer> rsrpList) {
		this.rsrpList = rsrpList;
	}

	public ArrayList<Integer> getSinrList() {
		return sinrList;
	}

	public void setSinrList(ArrayList<Integer> sinrList) {
		this.sinrList = sinrList;
	}

}
