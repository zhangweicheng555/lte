package com.boot.kaizen.business.buss.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableName;
import com.boot.kaizen.config.SuperEntity;

/**
 * Log的统计分析模块
 * 
 * @author weichengz
 * @date 2020年1月16日 下午4:27:31
 */
@TableName("buss_es_log_analyze")
public class LogAnaLyze extends SuperEntity<LogAnaLyze> {

	private static final long serialVersionUID = 1L;

	private String pid;// 室外测试的id

	private Double sinr;

	private Double rsrp;

	private Double downLoadSpeed;// 下载速率
	private Double upLoadSpeed;// 上传速率

	// 一下就一个  语音指标【对象】
	private String uniqueRecord;// 不为空就是唯一的记录
	
	// 统计：未接通、掉话次数、主-两个=正常次数
	private Double zjcs;// 主叫次数
	private Double wjtcs;// 未接通次数
	private Double dhcs;// 掉话次数
	private Double zhcs;// 正常次数   计算的

	// ping
	private Double pingQqcs;// Ping请求次数
	private Double pingCgch;// Ping成功次数
	private Double pingSbch;// Ping失败次数  计算的

	// http
	private Double httpQqcs;// Http请求次数
	private Double httpCgch;// Http成功次数
	private Double httpSbch;// Http失败次数   计算的

	private Date createTime;

	public Double getZhcs() {
		return zhcs;
	}

	
	public LogAnaLyze(String pid, Double sinr, Double rsrp, Double downLoadSpeed, Double upLoadSpeed,
			String uniqueRecord, Double zjcs, Double wjtcs, Double dhcs, Double zhcs, Double pingQqcs, Double pingCgch,
			Double pingSbch, Double httpQqcs, Double httpCgch, Double httpSbch, Date createTime) {
		super();
		this.pid = pid;
		this.sinr = sinr;
		this.rsrp = rsrp;
		this.downLoadSpeed = downLoadSpeed;
		this.upLoadSpeed = upLoadSpeed;
		this.uniqueRecord = uniqueRecord;
		this.zjcs = zjcs;
		this.wjtcs = wjtcs;
		this.dhcs = dhcs;
		this.zhcs = zhcs;
		this.pingQqcs = pingQqcs;
		this.pingCgch = pingCgch;
		this.pingSbch = pingSbch;
		this.httpQqcs = httpQqcs;
		this.httpCgch = httpCgch;
		this.httpSbch = httpSbch;
		this.createTime = createTime;
	}


	public String getUniqueRecord() {
		return uniqueRecord;
	}


	public void setUniqueRecord(String uniqueRecord) {
		this.uniqueRecord = uniqueRecord;
	}


	public LogAnaLyze(String pid, Double sinr, Double rsrp, Double downLoadSpeed, Double upLoadSpeed, Double zjcs,
			Double wjtcs, Double dhcs, Double zhcs, Double pingQqcs, Double pingCgch, Double pingSbch, Double httpQqcs,
			Double httpCgch, Double httpSbch, Date createTime) {
		super();
		this.pid = pid;
		this.sinr = sinr;
		this.rsrp = rsrp;
		this.downLoadSpeed = downLoadSpeed;
		this.upLoadSpeed = upLoadSpeed;
		this.zjcs = zjcs;
		this.wjtcs = wjtcs;
		this.dhcs = dhcs;
		this.zhcs = zhcs;
		this.pingQqcs = pingQqcs;
		this.pingCgch = pingCgch;
		this.pingSbch = pingSbch;
		this.httpQqcs = httpQqcs;
		this.httpCgch = httpCgch;
		this.httpSbch = httpSbch;
		this.createTime = createTime;
	}

	public void setZhcs(Double zhcs) {
		this.zhcs = zhcs;
	}

	public Double getPingSbch() {
		return pingSbch;
	}

	public void setPingSbch(Double pingSbch) {
		this.pingSbch = pingSbch;
	}

	public Double getHttpSbch() {
		return httpSbch;
	}

	public void setHttpSbch(Double httpSbch) {
		this.httpSbch = httpSbch;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Double getSinr() {
		return sinr;
	}

	public void setSinr(Double sinr) {
		this.sinr = sinr;
	}

	public Double getRsrp() {
		return rsrp;
	}

	public void setRsrp(Double rsrp) {
		this.rsrp = rsrp;
	}

	public Double getDownLoadSpeed() {
		return downLoadSpeed;
	}

	public void setDownLoadSpeed(Double downLoadSpeed) {
		this.downLoadSpeed = downLoadSpeed;
	}

	public Double getUpLoadSpeed() {
		return upLoadSpeed;
	}

	public void setUpLoadSpeed(Double upLoadSpeed) {
		this.upLoadSpeed = upLoadSpeed;
	}

	public Double getZjcs() {
		return zjcs;
	}

	public void setZjcs(Double zjcs) {
		this.zjcs = zjcs;
	}

	public Double getWjtcs() {
		return wjtcs;
	}

	public void setWjtcs(Double wjtcs) {
		this.wjtcs = wjtcs;
	}

	public Double getDhcs() {
		return dhcs;
	}

	public void setDhcs(Double dhcs) {
		this.dhcs = dhcs;
	}

	public Double getPingQqcs() {
		return pingQqcs;
	}

	public void setPingQqcs(Double pingQqcs) {
		this.pingQqcs = pingQqcs;
	}

	public Double getPingCgch() {
		return pingCgch;
	}

	public void setPingCgch(Double pingCgch) {
		this.pingCgch = pingCgch;
	}

	public Double getHttpQqcs() {
		return httpQqcs;
	}

	public void setHttpQqcs(Double httpQqcs) {
		this.httpQqcs = httpQqcs;
	}

	public Double getHttpCgch() {
		return httpCgch;
	}

	public void setHttpCgch(Double httpCgch) {
		this.httpCgch = httpCgch;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public LogAnaLyze(String pid, Double sinr, Double rsrp, Double downLoadSpeed, Double upLoadSpeed, Double zjcs,
			Double wjtcs, Double dhcs, Double pingQqcs, Double pingCgch, Double httpQqcs, Double httpCgch,
			Date createTime) {
		super();
		this.pid = pid;
		this.sinr = sinr;
		this.rsrp = rsrp;
		this.downLoadSpeed = downLoadSpeed;
		this.upLoadSpeed = upLoadSpeed;
		this.zjcs = zjcs;
		this.wjtcs = wjtcs;
		this.dhcs = dhcs;
		this.pingQqcs = pingQqcs;
		this.pingCgch = pingCgch;
		this.httpQqcs = httpQqcs;
		this.httpCgch = httpCgch;
		this.createTime = createTime;
	}

	public LogAnaLyze() {
		super();
	}

}
