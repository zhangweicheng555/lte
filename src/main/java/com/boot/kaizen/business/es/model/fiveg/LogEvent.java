package com.boot.kaizen.business.es.model.fiveg;

import java.io.Serializable;

/**
 * 事件
 * 
 * @author weichengz
 * @date 2020年4月26日 下午2:07:44
 */
public class LogEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long mTimestamp;// long 时间戳
	private String mEventType;// 事件类型（L2/EMM/RRC）
	private String mEvent;// 事件名称
	private Double mLongitude;// double 经度
	private Double mLatitude;// double 纬度

	public Long getmTimestamp() {
		return mTimestamp;
	}

	public void setmTimestamp(Long mTimestamp) {
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

	public LogEvent(Long mTimestamp, String mEventType, String mEvent, Double mLongitude, Double mLatitude) {
		super();
		this.mTimestamp = mTimestamp;
		this.mEventType = mEventType;
		this.mEvent = mEvent;
		this.mLongitude = mLongitude;
		this.mLatitude = mLatitude;
	}

	public LogEvent() {
		super();
	}

}
