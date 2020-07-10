package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;
/**
 * 
 * @author weichengz
 * @date 2020年5月6日 上午10:16:07
 */
public class SignalEventBean implements Serializable{
    
	private static final long serialVersionUID = 1L;
	private long mTimestamp;
    private String mEventType;
    private String mEvent;
    private double mLongitude;
    private double mLatitude;
    
    private String mid;//此点的id

    
    public SignalEventBean(long mTimestamp, String mEventType, String mEvent, double mLongitude, double mLatitude,
			String mid) {
		super();
		this.mTimestamp = mTimestamp;
		this.mEventType = mEventType;
		this.mEvent = mEvent;
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

    public String getmEventType() {
        return mEventType;
    }

    public void setmEventType(String mEventType) {
        this.mEventType = mEventType;
    }

    public String getmEvent() {
        return mEvent;
    }

    public void setmEvent(String mEvent) {
        this.mEvent = mEvent;
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

	public SignalEventBean(long mTimestamp, String mEventType, String mEvent, double mLongitude, double mLatitude) {
		super();
		this.mTimestamp = mTimestamp;
		this.mEventType = mEventType;
		this.mEvent = mEvent;
		this.mLongitude = mLongitude;
		this.mLatitude = mLatitude;
	}

	public SignalEventBean() {
		super();
	}
    
}
