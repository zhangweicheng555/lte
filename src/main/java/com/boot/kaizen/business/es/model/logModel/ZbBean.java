package com.boot.kaizen.business.es.model.logModel;

public class ZbBean {
	/** rrc请求数 */
	private int rrcRequestNum;
	/** rrc成功数 */
	private int rrcSuccessNum;
	/** 接通率成功 */
	private int connectionSuccess;
	/** 接通率失败 */
	private int connectionFailure;
	/** 切换成功 */
	private int handoverSuccess;
	/** 切换失败 */
	private int handoverFailure;
	/** SRVCC成功 */
	private int sRVCCSuccess;
	/** SRVCC失败 */
	private int sRVCCFailure;
	/** CSFB开始 */
	private int cSFBStart;
	/** E-RAB连接请求次数 */
	private int eRABRequestSuccessNum;
	/** E-RAB连接成功次数 */
	private int eRABRequestFailureNum;
	/** TAU成功次数 */
	private int tAURequestSuccessNum;
	/** TAU失败次数 */
	private int tAURequestFailureNum;
	/** CSFB时延 */
	private String cSFBDelay;
	/** CSFB开始时间 */
	private long cSFBStartTime;
	/** CSFB最大时延 */
	private long maxCSFBTime = -1;
	/** CSFB最小时延 */
	private long minCSFBTime = -1;
	/** CSFB平均时延 */
	private long avgCSFBTime = -1;
	/** CSFB开始到连接所耗时间 */
	private long cSFBTime = 0;
	/** CSFB连接次数 */
	private int cSFBConnection;
	/** CSFB开始连接成功时间 */
	private long cSFBConnectionTime;
	public int getRrcRequestNum() {
		return rrcRequestNum;
	}
	public void setRrcRequestNum(int rrcRequestNum) {
		this.rrcRequestNum = rrcRequestNum;
	}
	public int getRrcSuccessNum() {
		return rrcSuccessNum;
	}
	public void setRrcSuccessNum(int rrcSuccessNum) {
		this.rrcSuccessNum = rrcSuccessNum;
	}
	public int getConnectionSuccess() {
		return connectionSuccess;
	}
	public void setConnectionSuccess(int connectionSuccess) {
		this.connectionSuccess = connectionSuccess;
	}
	public int getConnectionFailure() {
		return connectionFailure;
	}
	public void setConnectionFailure(int connectionFailure) {
		this.connectionFailure = connectionFailure;
	}
	public int getHandoverSuccess() {
		return handoverSuccess;
	}
	public void setHandoverSuccess(int handoverSuccess) {
		this.handoverSuccess = handoverSuccess;
	}
	public int getHandoverFailure() {
		return handoverFailure;
	}
	public void setHandoverFailure(int handoverFailure) {
		this.handoverFailure = handoverFailure;
	}
	public int getsRVCCSuccess() {
		return sRVCCSuccess;
	}
	public void setsRVCCSuccess(int sRVCCSuccess) {
		this.sRVCCSuccess = sRVCCSuccess;
	}
	public int getsRVCCFailure() {
		return sRVCCFailure;
	}
	public void setsRVCCFailure(int sRVCCFailure) {
		this.sRVCCFailure = sRVCCFailure;
	}
	public int getcSFBStart() {
		return cSFBStart;
	}
	public void setcSFBStart(int cSFBStart) {
		this.cSFBStart = cSFBStart;
	}
	public int geteRABRequestSuccessNum() {
		return eRABRequestSuccessNum;
	}
	public void seteRABRequestSuccessNum(int eRABRequestSuccessNum) {
		this.eRABRequestSuccessNum = eRABRequestSuccessNum;
	}
	public int geteRABRequestFailureNum() {
		return eRABRequestFailureNum;
	}
	public void seteRABRequestFailureNum(int eRABRequestFailureNum) {
		this.eRABRequestFailureNum = eRABRequestFailureNum;
	}
	public int gettAURequestSuccessNum() {
		return tAURequestSuccessNum;
	}
	public void settAURequestSuccessNum(int tAURequestSuccessNum) {
		this.tAURequestSuccessNum = tAURequestSuccessNum;
	}
	public int gettAURequestFailureNum() {
		return tAURequestFailureNum;
	}
	public void settAURequestFailureNum(int tAURequestFailureNum) {
		this.tAURequestFailureNum = tAURequestFailureNum;
	}
	public String getcSFBDelay() {
		return cSFBDelay;
	}
	public void setcSFBDelay(String cSFBDelay) {
		this.cSFBDelay = cSFBDelay;
	}
	public long getcSFBStartTime() {
		return cSFBStartTime;
	}
	public void setcSFBStartTime(long cSFBStartTime) {
		this.cSFBStartTime = cSFBStartTime;
	}
	public long getMaxCSFBTime() {
		return maxCSFBTime;
	}
	public void setMaxCSFBTime(long maxCSFBTime) {
		this.maxCSFBTime = maxCSFBTime;
	}
	public long getMinCSFBTime() {
		return minCSFBTime;
	}
	public void setMinCSFBTime(long minCSFBTime) {
		this.minCSFBTime = minCSFBTime;
	}
	public long getAvgCSFBTime() {
		return avgCSFBTime;
	}
	public void setAvgCSFBTime(long avgCSFBTime) {
		this.avgCSFBTime = avgCSFBTime;
	}
	public long getcSFBTime() {
		return cSFBTime;
	}
	public void setcSFBTime(long cSFBTime) {
		this.cSFBTime = cSFBTime;
	}
	public int getcSFBConnection() {
		return cSFBConnection;
	}
	public void setcSFBConnection(int cSFBConnection) {
		this.cSFBConnection = cSFBConnection;
	}
	public long getcSFBConnectionTime() {
		return cSFBConnectionTime;
	}
	public void setcSFBConnectionTime(long cSFBConnectionTime) {
		this.cSFBConnectionTime = cSFBConnectionTime;
	}
	public ZbBean(int rrcRequestNum, int rrcSuccessNum, int connectionSuccess, int connectionFailure,
			int handoverSuccess, int handoverFailure, int sRVCCSuccess, int sRVCCFailure, int cSFBStart,
			int eRABRequestSuccessNum, int eRABRequestFailureNum, int tAURequestSuccessNum, int tAURequestFailureNum,
			String cSFBDelay, long cSFBStartTime, long maxCSFBTime, long minCSFBTime, long avgCSFBTime, long cSFBTime,
			int cSFBConnection, long cSFBConnectionTime) {
		super();
		this.rrcRequestNum = rrcRequestNum;
		this.rrcSuccessNum = rrcSuccessNum;
		this.connectionSuccess = connectionSuccess;
		this.connectionFailure = connectionFailure;
		this.handoverSuccess = handoverSuccess;
		this.handoverFailure = handoverFailure;
		this.sRVCCSuccess = sRVCCSuccess;
		this.sRVCCFailure = sRVCCFailure;
		this.cSFBStart = cSFBStart;
		this.eRABRequestSuccessNum = eRABRequestSuccessNum;
		this.eRABRequestFailureNum = eRABRequestFailureNum;
		this.tAURequestSuccessNum = tAURequestSuccessNum;
		this.tAURequestFailureNum = tAURequestFailureNum;
		this.cSFBDelay = cSFBDelay;
		this.cSFBStartTime = cSFBStartTime;
		this.maxCSFBTime = maxCSFBTime;
		this.minCSFBTime = minCSFBTime;
		this.avgCSFBTime = avgCSFBTime;
		this.cSFBTime = cSFBTime;
		this.cSFBConnection = cSFBConnection;
		this.cSFBConnectionTime = cSFBConnectionTime;
	}
	public ZbBean() {
		super();
	}

	
	
	

}
