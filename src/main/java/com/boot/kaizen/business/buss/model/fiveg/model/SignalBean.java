package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;

public class SignalBean implements Serializable{
   
	private static final long serialVersionUID = 1L;
	
	private long mTimestamp;
    private String mSingaType;
    private String mMessageType;
    private String mChannelType;
    private String mMeaasge;
    private double mLongitude;
    private double mLatitude;
    
    
    private String mid;//此点的id
    
    
    
    public SignalBean(long mTimestamp, String mSingaType, String mMessageType, String mChannelType, String mMeaasge,
			double mLongitude, double mLatitude, String mid) {
		super();
		this.mTimestamp = mTimestamp;
		this.mSingaType = mSingaType;
		this.mMessageType = mMessageType;
		this.mChannelType = mChannelType;
		this.mMeaasge = mMeaasge;
		this.mLongitude = mLongitude;
		this.mLatitude = mLatitude;
		this.mid = mid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public long getmTimestamp() {
        return mTimestamp;
    }

    public void setmTimestamp(long mTimestamp) {
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

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

	public SignalBean(long mTimestamp, String mSingaType, String mMessageType, String mChannelType, String mMeaasge,
			double mLongitude, double mLatitude) {
		super();
		this.mTimestamp = mTimestamp;
		this.mSingaType = mSingaType;
		this.mMessageType = mMessageType;
		this.mChannelType = mChannelType;
		this.mMeaasge = mMeaasge;
		this.mLongitude = mLongitude;
		this.mLatitude = mLatitude;
	}

	public SignalBean() {
		super();
	}
    
}
