package com.boot.kaizen.business.es.model;

import com.boot.kaizen.business.es.model.logModel.FtpzbBean;
import com.boot.kaizen.business.es.model.logModel.HttpzbBean;
import com.boot.kaizen.business.es.model.logModel.PingzbBean;
import com.boot.kaizen.business.es.model.logModel.PowerBean;
import com.boot.kaizen.business.es.model.logModel.ProIndicators;
import com.boot.kaizen.business.es.model.logModel.SignalDataBean;
import com.boot.kaizen.business.es.model.logModel.WxzbBean;
import com.boot.kaizen.business.es.model.logModel.YyzbBean;
import com.boot.kaizen.business.es.model.logModel.ZbBean;

/**
 * 日志模块的其余字段信息 这些信息是暂时存储下来 但是目前没使用到这些数据  logother
 * 
 * @author weichengz
 * @date 2019年11月5日 下午1:45:49
 */
public class OtherLogModel {

	private String pid;// 主日志信息的id

	private PingzbBean pingzbBean;
	private HttpzbBean httpzbBean;
	private FtpzbBean ftpzbBean;
	private YyzbBean Yyzbbean;
	private WxzbBean wxzbBean;
	private PowerBean powerBean;
	private ZbBean zbBean;// 指标参数
	private ProIndicators proIndicators;// 专业指标

	/** 自定义构造函数 */
	public OtherLogModel(SignalDataBean signalDataBean) {
		this.pid = signalDataBean.getId();
		this.pingzbBean = signalDataBean.getPingzbBean();
		this.httpzbBean = signalDataBean.getHttpzbBean();
		this.ftpzbBean = signalDataBean.getFtpzbBean();
		this.Yyzbbean = signalDataBean.getYyzbbean();
		this.wxzbBean = signalDataBean.getWxzbBean();
		this.powerBean = signalDataBean.getPowerBean();
		this.zbBean = signalDataBean.getZbBean();
		this.proIndicators = signalDataBean.getProIndicators();
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public PingzbBean getPingzbBean() {
		return pingzbBean;
	}

	public void setPingzbBean(PingzbBean pingzbBean) {
		this.pingzbBean = pingzbBean;
	}

	public HttpzbBean getHttpzbBean() {
		return httpzbBean;
	}

	public void setHttpzbBean(HttpzbBean httpzbBean) {
		this.httpzbBean = httpzbBean;
	}

	public FtpzbBean getFtpzbBean() {
		return ftpzbBean;
	}

	public void setFtpzbBean(FtpzbBean ftpzbBean) {
		this.ftpzbBean = ftpzbBean;
	}

	public YyzbBean getYyzbbean() {
		return Yyzbbean;
	}

	public void setYyzbbean(YyzbBean yyzbbean) {
		Yyzbbean = yyzbbean;
	}

	public WxzbBean getWxzbBean() {
		return wxzbBean;
	}

	public void setWxzbBean(WxzbBean wxzbBean) {
		this.wxzbBean = wxzbBean;
	}

	public PowerBean getPowerBean() {
		return powerBean;
	}

	public void setPowerBean(PowerBean powerBean) {
		this.powerBean = powerBean;
	}

	public ZbBean getZbBean() {
		return zbBean;
	}

	public void setZbBean(ZbBean zbBean) {
		this.zbBean = zbBean;
	}

	public ProIndicators getProIndicators() {
		return proIndicators;
	}

	public void setProIndicators(ProIndicators proIndicators) {
		this.proIndicators = proIndicators;
	}

	public OtherLogModel(String pid, PingzbBean pingzbBean, HttpzbBean httpzbBean, FtpzbBean ftpzbBean,
			YyzbBean yyzbbean, WxzbBean wxzbBean, PowerBean powerBean, ZbBean zbBean, ProIndicators proIndicators) {
		super();
		this.pid = pid;
		this.pingzbBean = pingzbBean;
		this.httpzbBean = httpzbBean;
		this.ftpzbBean = ftpzbBean;
		Yyzbbean = yyzbbean;
		this.wxzbBean = wxzbBean;
		this.powerBean = powerBean;
		this.zbBean = zbBean;
		this.proIndicators = proIndicators;
	}

	public OtherLogModel() {
		super();
	}

}
