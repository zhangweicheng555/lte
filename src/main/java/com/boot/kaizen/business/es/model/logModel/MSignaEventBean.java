package com.boot.kaizen.business.es.model.logModel;

import java.io.Serializable;

public class MSignaEventBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pid;// 主日志信息的id   这个原日志里面是没有的
	
	private long mTimestamp;
	private String mSignaTimestamp;
	private int ci;
	private String mEventType;
	private String mEvent;
	private double mLongitude;
	private double mLatitude;

	
	
	public MSignaEventBean(String pid, long mTimestamp, String mSignaTimestamp, int ci, String mEventType,
			String mEvent, double mLongitude, double mLatitude) {
		super();
		this.pid = pid;
		this.mTimestamp = mTimestamp;
		this.mSignaTimestamp = mSignaTimestamp;
		this.ci = ci;
		this.mEventType = mEventType;
		this.mEvent = mEvent;
		this.mLongitude = mLongitude;
		this.mLatitude = mLatitude;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public MSignaEventBean() {
	}

	public String getmSignaTimestamp() {
		return this.mSignaTimestamp;
	}

	public void setmSignaTimestamp(String var1) {
		this.mSignaTimestamp = var1;
	}

	public double getmLongitude() {
		return this.mLongitude;
	}

	public void setmLongitude(double var1) {
		this.mLongitude = var1;
	}

	public double getmLatitude() {
		return this.mLatitude;
	}

	public void setmLatitude(double var1) {
		this.mLatitude = var1;
	}

	public long getmTimestamp() {
		return this.mTimestamp;
	}

	public void setmTimestamp(long var1) {
		this.mTimestamp = var1;
	}

	public String getmEventType() {
		return this.mEventType;
	}

	public void setmEventType(String var1) {
		this.mEventType = var1;
	}

	public String getmEvent() {
		return this.mEvent;
	}

	public void setmEvent(String var1) {
		this.mEvent = var1;
	}

	public int getCi() {
		return this.ci;
	}

	public void setCi(int var1) {
		this.ci = var1;
	}

	public String toString() {
		return "MSignaEventBean{mTimestamp=" + this.mTimestamp + ", ci=" + this.ci + ", mEventType='" + this.mEventType
				+ '\'' + ", mEvent='" + this.mEvent + '\'' + '}';
	}
}
