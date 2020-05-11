package com.boot.kaizen.business.buss.model.fiveg;

import java.io.Serializable;

/**
 * 信令
 * 
 * @author weichengz
 * @date 2020年4月26日 下午2:07:56
 */
public class LogMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long mTimestamp;// long 时间戳
	private String mSingaType;// 信令上下行指示
	private String mMessageType;// 信令名称
	private String mChannelType;// 信道类型
	private String mMeaasge;// 信令码流
	private Double mLongitude;// double 经度
	private Double mLatitude;// double 纬度

	public Long getmTimestamp() {
		return mTimestamp;
	}

	public void setmTimestamp(Long mTimestamp) {
		this.mTimestamp = mTimestamp;
	}

	public String getmSingaType() {
		return mSingaType;
	}

	public void setmSingaType(String mSingaType) {
		this.mSingaType = mSingaType;
	}

	public String getmMessageType() {
		return mMessageType;
	}

	public void setmMessageType(String mMessageType) {
		this.mMessageType = mMessageType;
	}

	public String getmChannelType() {
		return mChannelType;
	}

	public void setmChannelType(String mChannelType) {
		this.mChannelType = mChannelType;
	}

	public String getmMeaasge() {
		return mMeaasge;
	}

	public void setmMeaasge(String mMeaasge) {
		this.mMeaasge = mMeaasge;
	}

	public Double getmLongitude() {
		return mLongitude;
	}

	public void setmLongitude(Double mLongitude) {
		this.mLongitude = mLongitude;
	}

	public Double getmLatitude() {
		return mLatitude;
	}

	public void setmLatitude(Double mLatitude) {
		this.mLatitude = mLatitude;
	}

	public LogMessage(Long mTimestamp, String mSingaType, String mMessageType, String mChannelType, String mMeaasge,
			Double mLongitude, Double mLatitude) {
		super();
		this.mTimestamp = mTimestamp;
		this.mSingaType = mSingaType;
		this.mMessageType = mMessageType;
		this.mChannelType = mChannelType;
		this.mMeaasge = mMeaasge;
		this.mLongitude = mLongitude;
		this.mLatitude = mLatitude;
	}

	public LogMessage() {
		super();
	}

}
