package com.boot.kaizen.business.es.model.logModel;

import java.io.Serializable;
import java.util.Date;

import com.boot.kaizen.util.MyDateUtil;

public class MSignaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pid;// 主日志信息的id

	private long mTimestamp;
	private String mSignaTimestamp;
	private String mSingaType;
	private String mMessageType;
	private int ci;
	private String mChannelType;
	private String mMeaasge;
	private double mLongitude;
	private double mLatitude;

	//private String formatTimestamp;// 格式化得日期 自定义

	

	

	

	public MSignaBean(String pid, long mTimestamp, String mSignaTimestamp, String mSingaType, String mMessageType,
			int ci, String mChannelType, String mMeaasge, double mLongitude, double mLatitude) {
		super();
		this.pid = pid;
		this.mTimestamp = mTimestamp;
		this.mSignaTimestamp = mSignaTimestamp;
		this.mSingaType = mSingaType;
		this.mMessageType = mMessageType;
		this.ci = ci;
		this.mChannelType = mChannelType;
		this.mMeaasge = mMeaasge;
		this.mLongitude = mLongitude;
		this.mLatitude = mLatitude;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public MSignaBean() {
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

	public String getmMeaasge() {
		return this.mMeaasge;
	}

	public void setmMeaasge(String var1) {
		this.mMeaasge = var1;
	}

	public long getmTimestamp() {
		return this.mTimestamp;
	}

	public void setmTimestamp(long var1) {
		this.mTimestamp = var1;
	}

	public String getmSingaType() {
		return this.mSingaType;
	}

	public void setmSingaType(String var1) {
		this.mSingaType = var1;
	}

	public String getmMessageType() {
		return this.mMessageType;
	}

	public void setmMessageType(String var1) {
		this.mMessageType = var1;
	}

	public String getmChannelType() {
		return this.mChannelType;
	}

	public void setmChannelType(String var1) {
		this.mChannelType = var1;
	}

	public int getCi() {
		return this.ci;
	}

	public void setCi(int var1) {
		this.ci = var1;
	}

	public String toString() {
		return "MSignaBean{mTimestamp=" + this.mTimestamp + ", mSingaType='" + this.mSingaType + '\''
				+ ", mMessageType='" + this.mMessageType + '\'' + ", ci=" + this.ci + ", mChannelType='"
				+ this.mChannelType + '\'' + ", mMeaasge='" + this.mMeaasge + '\'' + '}';
	}
}
