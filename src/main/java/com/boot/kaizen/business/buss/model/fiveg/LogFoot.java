package com.boot.kaizen.business.buss.model.fiveg;

import java.io.Serializable;

/**
 * log底部信息
 * 
 * @author weichengz
 * @date 2020年4月26日 上午9:57:36
 */

public class LogFoot implements Serializable {

	private static final long serialVersionUID = 1L;

	private FootNrwxzbBean nrwxzbBean;// 5G无线指标【对象】
	private FootLtewxzbBean ltewxzbBean;// 4G无线指标【对象】
	private FootFtpzbBean ftpzbBean;// ftp指标【对象】
	private FootYyzbBean yyzbBean;// 语音指标【对象】
	private FootPingzbBean pingzbBean;// ping指标【对象】
	private FootHttpzbBean httpzbBean;// http指标【对象】

	public FootNrwxzbBean getNrwxzbBean() {
		return nrwxzbBean;
	}

	public void setNrwxzbBean(FootNrwxzbBean nrwxzbBean) {
		this.nrwxzbBean = nrwxzbBean;
	}

	public FootLtewxzbBean getLtewxzbBean() {
		return ltewxzbBean;
	}

	public void setLtewxzbBean(FootLtewxzbBean ltewxzbBean) {
		this.ltewxzbBean = ltewxzbBean;
	}

	public FootFtpzbBean getFtpzbBean() {
		return ftpzbBean;
	}

	public void setFtpzbBean(FootFtpzbBean ftpzbBean) {
		this.ftpzbBean = ftpzbBean;
	}

	public FootYyzbBean getYyzbBean() {
		return yyzbBean;
	}

	public void setYyzbBean(FootYyzbBean yyzbBean) {
		this.yyzbBean = yyzbBean;
	}

	public FootPingzbBean getPingzbBean() {
		return pingzbBean;
	}

	public void setPingzbBean(FootPingzbBean pingzbBean) {
		this.pingzbBean = pingzbBean;
	}

	public FootHttpzbBean getHttpzbBean() {
		return httpzbBean;
	}

	public void setHttpzbBean(FootHttpzbBean httpzbBean) {
		this.httpzbBean = httpzbBean;
	}

	public LogFoot(FootNrwxzbBean nrwxzbBean, FootLtewxzbBean ltewxzbBean, FootFtpzbBean ftpzbBean,
			FootYyzbBean yyzbBean, FootPingzbBean pingzbBean, FootHttpzbBean httpzbBean) {
		super();
		this.nrwxzbBean = nrwxzbBean;
		this.ltewxzbBean = ltewxzbBean;
		this.ftpzbBean = ftpzbBean;
		this.yyzbBean = yyzbBean;
		this.pingzbBean = pingzbBean;
		this.httpzbBean = httpzbBean;
	}

	public LogFoot() {
		super();
	}

}
