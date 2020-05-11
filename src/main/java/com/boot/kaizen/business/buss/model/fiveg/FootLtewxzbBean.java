package com.boot.kaizen.business.buss.model.fiveg;

import java.io.Serializable;

/**
 * 4G无线指标【对象】
 * 
 * @author weichengz
 * @date 2020年4月26日 上午10:04:41
 */
public class FootLtewxzbBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String rsrqd; // rsrq最大值
	private String rsrqx; // rsrq最小值
	private String rsrqp; // rsrq平均值
	private String rsrpd; // rsrp最大值
	private String rsrpx;// rsrp最小值
	private String rsrpp;// rsrp平均值
	private String sinrd;// sinr最大值
	private String sinrx;// sinr最小值
	private String sinrp;// sinr平均值
	private String cssc;// 测试时长
	private String fgl;// 覆盖率
	private String fglc;// 覆盖里程
	private String zlc;// 总里程
	private String lcfgl;// 里程覆盖率
	private String rsrqd_y;// 副卡rsrq最大值
	private String rsrqx_y;// 副卡rsrq最小值
	private String rsrqp_y;// 副卡rsrq平均值
	private String rsrpd_y;// 副卡rsrp最大值
	private String rsrpx_y;// 副卡rsrp最小值
	private String rsrpp_y;// 副卡rsrp平均值
	private String sinrd_y;// 副卡sinr最大值
	private String sinrx_y;// 副卡sinr最小值
	private String sinrp_y;// 副卡sinr平均值
	private String cssc_y;// 副卡测试时长
	private String fgl_y;// 副卡覆盖率
	private String fglc_y;// 副卡覆盖里程
	private String zlc_y;// 副卡总里程
	private String lcfgl_y;// 副卡里程覆盖率
	private String comptestRsrp0;// 竞对测试RSRP平均值（服务商）如CMCC,-90
	private String comptestRsrp1;// 竞对测试RSRP平均值（竞对商）如CTC,-90
	private String comptestRsrp2;// 竞对测试RSRP平均值（竞对商）如CUC,-90

	public FootLtewxzbBean(String rsrqd, String rsrqx, String rsrqp, String rsrpd, String rsrpx, String rsrpp,
			String sinrd, String sinrx, String sinrp, String cssc, String fgl, String fglc, String zlc, String lcfgl,
			String rsrqd_y, String rsrqx_y, String rsrqp_y, String rsrpd_y, String rsrpx_y, String rsrpp_y,
			String sinrd_y, String sinrx_y, String sinrp_y, String cssc_y, String fgl_y, String fglc_y, String zlc_y,
			String lcfgl_y, String comptestRsrp0, String comptestRsrp1, String comptestRsrp2) {
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
		this.rsrqd_y = rsrqd_y;
		this.rsrqx_y = rsrqx_y;
		this.rsrqp_y = rsrqp_y;
		this.rsrpd_y = rsrpd_y;
		this.rsrpx_y = rsrpx_y;
		this.rsrpp_y = rsrpp_y;
		this.sinrd_y = sinrd_y;
		this.sinrx_y = sinrx_y;
		this.sinrp_y = sinrp_y;
		this.cssc_y = cssc_y;
		this.fgl_y = fgl_y;
		this.fglc_y = fglc_y;
		this.zlc_y = zlc_y;
		this.lcfgl_y = lcfgl_y;
		this.comptestRsrp0 = comptestRsrp0;
		this.comptestRsrp1 = comptestRsrp1;
		this.comptestRsrp2 = comptestRsrp2;
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

	public String getRsrqd_y() {
		return rsrqd_y;
	}

	public void setRsrqd_y(String rsrqd_y) {
		this.rsrqd_y = rsrqd_y;
	}

	public String getRsrqx_y() {
		return rsrqx_y;
	}

	public void setRsrqx_y(String rsrqx_y) {
		this.rsrqx_y = rsrqx_y;
	}

	public String getRsrqp_y() {
		return rsrqp_y;
	}

	public void setRsrqp_y(String rsrqp_y) {
		this.rsrqp_y = rsrqp_y;
	}

	public String getRsrpd_y() {
		return rsrpd_y;
	}

	public void setRsrpd_y(String rsrpd_y) {
		this.rsrpd_y = rsrpd_y;
	}

	public String getRsrpx_y() {
		return rsrpx_y;
	}

	public void setRsrpx_y(String rsrpx_y) {
		this.rsrpx_y = rsrpx_y;
	}

	public String getRsrpp_y() {
		return rsrpp_y;
	}

	public void setRsrpp_y(String rsrpp_y) {
		this.rsrpp_y = rsrpp_y;
	}

	public String getSinrd_y() {
		return sinrd_y;
	}

	public void setSinrd_y(String sinrd_y) {
		this.sinrd_y = sinrd_y;
	}

	public String getSinrx_y() {
		return sinrx_y;
	}

	public void setSinrx_y(String sinrx_y) {
		this.sinrx_y = sinrx_y;
	}

	public String getSinrp_y() {
		return sinrp_y;
	}

	public void setSinrp_y(String sinrp_y) {
		this.sinrp_y = sinrp_y;
	}

	public String getCssc_y() {
		return cssc_y;
	}

	public void setCssc_y(String cssc_y) {
		this.cssc_y = cssc_y;
	}

	public String getFgl_y() {
		return fgl_y;
	}

	public void setFgl_y(String fgl_y) {
		this.fgl_y = fgl_y;
	}

	public String getFglc_y() {
		return fglc_y;
	}

	public void setFglc_y(String fglc_y) {
		this.fglc_y = fglc_y;
	}

	public String getZlc_y() {
		return zlc_y;
	}

	public void setZlc_y(String zlc_y) {
		this.zlc_y = zlc_y;
	}

	public String getLcfgl_y() {
		return lcfgl_y;
	}

	public void setLcfgl_y(String lcfgl_y) {
		this.lcfgl_y = lcfgl_y;
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

	public FootLtewxzbBean() {
		super();
	}

}
