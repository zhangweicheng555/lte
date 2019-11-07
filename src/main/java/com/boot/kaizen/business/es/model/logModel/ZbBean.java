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
	private int SRVCCSuccess;
	/** SRVCC失败 */
	private int SRVCCFailure;
	/** CSFB开始 */
	private int CSFBStart;
	/** E-RAB连接请求次数 */
	private int ERABRequestSuccessNum;
	/** E-RAB连接成功次数 */
	private int ERABRequestFailureNum;
	/** TAU成功次数 */
	private int TAURequestSuccessNum;
	/** TAU失败次数 */
	private int TAURequestFailureNum;
	/** CSFB时延 */
	private String CSFBDelay;
	/** CSFB开始时间 */
	private long CSFBStartTime;
	/** CSFB最大时延 */
	private long maxCSFBTime = -1;
	/** CSFB最小时延 */
	private long minCSFBTime = -1;
	/** CSFB平均时延 */
	private long avgCSFBTime = -1;
	/** CSFB开始到连接所耗时间 */
	private long CSFBTime = 0;
	/** CSFB连接次数 */
	private int CSFBConnection;
	/** CSFB开始连接成功时间 */
	private long CSFBConnectionTime;

	public String getCSFBDelay() {
		return CSFBDelay;
	}

	public void setCSFBDelay(String CSFBDelay) {
		this.CSFBDelay = CSFBDelay;
	}

	public int getTAURequestSuccessNum() {
		return TAURequestSuccessNum;
	}

	public void setTAURequestSuccessNum(int TAURequestSuccessNum) {
		this.TAURequestSuccessNum = TAURequestSuccessNum;
	}

	public int getTAURequestFailureNum() {
		return TAURequestFailureNum;
	}

	public void setTAURequestFailureNum(int TAURequestFailureNum) {
		this.TAURequestFailureNum = TAURequestFailureNum;
	}

	public int getERABRequestSuccessNum() {
		return ERABRequestSuccessNum;
	}

	public void setERABRequestSuccessNum(int ERABRequestSuccessNum) {
		this.ERABRequestSuccessNum = ERABRequestSuccessNum;
	}

	public int getERABRequestFailureNum() {
		return ERABRequestFailureNum;
	}

	public void setERABRequestFailureNum(int ERABRequestFailureNum) {
		this.ERABRequestFailureNum = ERABRequestFailureNum;
	}

	public long getCSFBTime() {
		return CSFBTime;
	}

	public void setCSFBTime(long CSFBTime) {
		this.CSFBTime = CSFBTime;
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

	public int getSRVCCSuccess() {
		return SRVCCSuccess;
	}

	public void setSRVCCSuccess(int SRVCCSuccess) {
		this.SRVCCSuccess = SRVCCSuccess;
	}

	public int getSRVCCFailure() {
		return SRVCCFailure;
	}

	public void setSRVCCFailure(int SRVCCFailure) {
		this.SRVCCFailure = SRVCCFailure;
	}

	public int getCSFBStart() {
		return CSFBStart;
	}

	public void setCSFBStart(int CSFBStart) {
		this.CSFBStart = CSFBStart;
	}

	public long getCSFBStartTime() {
		return CSFBStartTime;
	}

	public void setCSFBStartTime(long CSFBStartTime) {
		this.CSFBStartTime = CSFBStartTime;
	}

	public int getCSFBConnection() {
		return CSFBConnection;
	}

	public void setCSFBConnection(int CSFBConnection) {
		this.CSFBConnection = CSFBConnection;
	}

	public long getCSFBConnectionTime() {
		return CSFBConnectionTime;
	}

	public void setCSFBConnectionTime(long CSFBConnectionTime) {
		this.CSFBConnectionTime = CSFBConnectionTime;
	}

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

	public ZbBean() {
	}

}
