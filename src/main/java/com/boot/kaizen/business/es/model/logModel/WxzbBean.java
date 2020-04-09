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

	// 20200408根据新的数据结构去掉这些不用的
	// private ArrayList<Integer> rsrpList;
	// private ArrayList<Integer> sinrList;

	// 新增
	private String noneLteMileage;
	private String noneLteTime;
	private String nr5GTimeRate;
	private String nr5gMileageRate;
	private String csiRsrq;
	private String ssRsrp;
	private String ssSinr;
	private String nrRsrp100Rate;
	private String nrRsrp99Rate;
	private String nrRsrp96Rate;
	private String nrSinr3Rate;
	private String nrSinr0Rate;
	private String nrRsrp105Sinr3Rate;
	private String nrRsrp100Sinr0Rate;
	private String linkSsSinrTimeDistanceTotalRate;
	private String linkSsRsrpTimeDistanceTotalRate;
	private String linkSinrTimeDistanceTotalRate;
	private String linkRsrpTimeDistanceTotalRate;
	private String comptestRsrp0;
	private String comptestRsrp1;
	private String comptestRsrp2;

	public WxzbBean() {

	}

	public String getNoneLteMileage() {
		return noneLteMileage;
	}

	public void setNoneLteMileage(String noneLteMileage) {
		this.noneLteMileage = noneLteMileage;
	}

	public String getNoneLteTime() {
		return noneLteTime;
	}

	public void setNoneLteTime(String noneLteTime) {
		this.noneLteTime = noneLteTime;
	}

	public String getNr5GTimeRate() {
		return nr5GTimeRate;
	}

	public void setNr5GTimeRate(String nr5gTimeRate) {
		nr5GTimeRate = nr5gTimeRate;
	}

	public String getNr5gMileageRate() {
		return nr5gMileageRate;
	}

	public void setNr5gMileageRate(String nr5gMileageRate) {
		this.nr5gMileageRate = nr5gMileageRate;
	}

	public String getCsiRsrq() {
		return csiRsrq;
	}

	public void setCsiRsrq(String csiRsrq) {
		this.csiRsrq = csiRsrq;
	}

	public String getSsRsrp() {
		return ssRsrp;
	}

	public void setSsRsrp(String ssRsrp) {
		this.ssRsrp = ssRsrp;
	}

	public String getSsSinr() {
		return ssSinr;
	}

	public void setSsSinr(String ssSinr) {
		this.ssSinr = ssSinr;
	}

	public String getNrRsrp100Rate() {
		return nrRsrp100Rate;
	}

	public void setNrRsrp100Rate(String nrRsrp100Rate) {
		this.nrRsrp100Rate = nrRsrp100Rate;
	}

	public String getNrRsrp99Rate() {
		return nrRsrp99Rate;
	}

	public void setNrRsrp99Rate(String nrRsrp99Rate) {
		this.nrRsrp99Rate = nrRsrp99Rate;
	}

	public String getNrRsrp96Rate() {
		return nrRsrp96Rate;
	}

	public void setNrRsrp96Rate(String nrRsrp96Rate) {
		this.nrRsrp96Rate = nrRsrp96Rate;
	}

	public String getNrSinr3Rate() {
		return nrSinr3Rate;
	}

	public void setNrSinr3Rate(String nrSinr3Rate) {
		this.nrSinr3Rate = nrSinr3Rate;
	}

	public String getNrSinr0Rate() {
		return nrSinr0Rate;
	}

	public void setNrSinr0Rate(String nrSinr0Rate) {
		this.nrSinr0Rate = nrSinr0Rate;
	}

	public String getNrRsrp105Sinr3Rate() {
		return nrRsrp105Sinr3Rate;
	}

	public void setNrRsrp105Sinr3Rate(String nrRsrp105Sinr3Rate) {
		this.nrRsrp105Sinr3Rate = nrRsrp105Sinr3Rate;
	}

	public String getNrRsrp100Sinr0Rate() {
		return nrRsrp100Sinr0Rate;
	}

	public void setNrRsrp100Sinr0Rate(String nrRsrp100Sinr0Rate) {
		this.nrRsrp100Sinr0Rate = nrRsrp100Sinr0Rate;
	}

	public String getLinkSsSinrTimeDistanceTotalRate() {
		return linkSsSinrTimeDistanceTotalRate;
	}

	public void setLinkSsSinrTimeDistanceTotalRate(String linkSsSinrTimeDistanceTotalRate) {
		this.linkSsSinrTimeDistanceTotalRate = linkSsSinrTimeDistanceTotalRate;
	}

	public String getLinkSsRsrpTimeDistanceTotalRate() {
		return linkSsRsrpTimeDistanceTotalRate;
	}

	public void setLinkSsRsrpTimeDistanceTotalRate(String linkSsRsrpTimeDistanceTotalRate) {
		this.linkSsRsrpTimeDistanceTotalRate = linkSsRsrpTimeDistanceTotalRate;
	}

	public String getLinkSinrTimeDistanceTotalRate() {
		return linkSinrTimeDistanceTotalRate;
	}

	public void setLinkSinrTimeDistanceTotalRate(String linkSinrTimeDistanceTotalRate) {
		this.linkSinrTimeDistanceTotalRate = linkSinrTimeDistanceTotalRate;
	}

	public String getLinkRsrpTimeDistanceTotalRate() {
		return linkRsrpTimeDistanceTotalRate;
	}

	public void setLinkRsrpTimeDistanceTotalRate(String linkRsrpTimeDistanceTotalRate) {
		this.linkRsrpTimeDistanceTotalRate = linkRsrpTimeDistanceTotalRate;
	}

	public String getComptestRsrp0() {
		return comptestRsrp0;
	}

	public void setComptestRsrp0(String comptestRsrp0) {
		this.comptestRsrp0 = comptestRsrp0;
	}

	public String getComptestRsrp1() {
		return comptestRsrp1;
	}

	public void setComptestRsrp1(String comptestRsrp1) {
		this.comptestRsrp1 = comptestRsrp1;
	}

	public String getComptestRsrp2() {
		return comptestRsrp2;
	}

	public void setComptestRsrp2(String comptestRsrp2) {
		this.comptestRsrp2 = comptestRsrp2;
	}

	public WxzbBean(String rsrqd, String rsrqx, String rsrqp, String rsrpd, String rsrpx, String rsrpp, String sinrd,
			String sinrx, String sinrp, String cssc, String fgl, String fglc, String zlc, String lcfgl,
			String noneLteMileage, String noneLteTime, String nr5gTimeRate, String nr5gMileageRate, String csiRsrq,
			String ssRsrp, String ssSinr, String nrRsrp100Rate, String nrRsrp99Rate, String nrRsrp96Rate,
			String nrSinr3Rate, String nrSinr0Rate, String nrRsrp105Sinr3Rate, String nrRsrp100Sinr0Rate,
			String linkSsSinrTimeDistanceTotalRate, String linkSsRsrpTimeDistanceTotalRate,
			String linkSinrTimeDistanceTotalRate, String linkRsrpTimeDistanceTotalRate, String comptestRsrp0,
			String comptestRsrp1, String comptestRsrp2) {
		super();
		this.rsrqd = rsrqd;
		this.rsrqx = rsrqx;
		this.rsrqp = rsrqp;
		this.rsrpd = rsrpd;
		this.rsrpx = rsrpx;
		this.rsrpp = rsrpp;
		this.sinrd = sinrd;
		this.sinrx = sinrx;
		this.sinrp = sinrp;
		this.cssc = cssc;
		this.fgl = fgl;
		this.fglc = fglc;
		this.zlc = zlc;
		this.lcfgl = lcfgl;
		this.noneLteMileage = noneLteMileage;
		this.noneLteTime = noneLteTime;
		nr5GTimeRate = nr5gTimeRate;
		this.nr5gMileageRate = nr5gMileageRate;
		this.csiRsrq = csiRsrq;
		this.ssRsrp = ssRsrp;
		this.ssSinr = ssSinr;
		this.nrRsrp100Rate = nrRsrp100Rate;
		this.nrRsrp99Rate = nrRsrp99Rate;
		this.nrRsrp96Rate = nrRsrp96Rate;
		this.nrSinr3Rate = nrSinr3Rate;
		this.nrSinr0Rate = nrSinr0Rate;
		this.nrRsrp105Sinr3Rate = nrRsrp105Sinr3Rate;
		this.nrRsrp100Sinr0Rate = nrRsrp100Sinr0Rate;
		this.linkSsSinrTimeDistanceTotalRate = linkSsSinrTimeDistanceTotalRate;
		this.linkSsRsrpTimeDistanceTotalRate = linkSsRsrpTimeDistanceTotalRate;
		this.linkSinrTimeDistanceTotalRate = linkSinrTimeDistanceTotalRate;
		this.linkRsrpTimeDistanceTotalRate = linkRsrpTimeDistanceTotalRate;
		this.comptestRsrp0 = comptestRsrp0;
		this.comptestRsrp1 = comptestRsrp1;
		this.comptestRsrp2 = comptestRsrp2;
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

}
