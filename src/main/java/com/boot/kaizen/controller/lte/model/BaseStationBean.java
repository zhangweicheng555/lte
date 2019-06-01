package com.boot.kaizen.controller.lte.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 基站实体类
 * @author weichengz
 * @date 2019年5月31日 上午11:13:17
 */
public class BaseStationBean {

	/* 基站海拔 */private String mAltitude = "";
	/* 基站名称 */private String mBaseStationName = "";
	/* 基站编号 */private String mENodeBID = "";
	/* 基站类型 */private String mBaseStationType = "";
	/* 纬度 */private String mLatitude = "";
	/* 经度 */private String mLongitude = "";
	/* tac */private String mTac = "";

	/* 小区数据集 */private List<CommunityBean> mCommunityBeanList = new ArrayList<CommunityBean>();

	public BaseStationBean() {
		super();
	}

	public String getmENodeBID() {
		return mENodeBID;
	}

	public void setmENodeBID(String mENodeBID) {
		this.mENodeBID = mENodeBID;
	}

	public String getmTac() {
		return mTac;
	}

	public void setmTac(String mTac) {
		this.mTac = mTac;
	}

	public String getmAltitude() {
		return mAltitude;
	}

	public void setmAltitude(String mAltitude) {
		this.mAltitude = mAltitude;
	}

	public String getmBaseStationName() {
		return mBaseStationName;
	}

	public void setmBaseStationName(String mBaseStationName) {
		this.mBaseStationName = mBaseStationName;
	}

	public String getmBaseStationType() {
		return mBaseStationType;
	}

	public void setmBaseStationType(String mBaseStationType) {
		this.mBaseStationType = mBaseStationType;
	}

	public String getmLatitude() {
		return mLatitude;
	}

	public void setmLatitude(String mLatitude) {
		this.mLatitude = mLatitude;
	}

	public String getmLongitude() {
		return mLongitude;
	}

	public void setmLongitude(String mLongitude) {
		this.mLongitude = mLongitude;
	}

	public List<CommunityBean> getmCommunityBeanList() {
		return mCommunityBeanList;
	}

	public void setmCommunityBeanList(List<CommunityBean> mCommunityBeanList) {
		this.mCommunityBeanList = mCommunityBeanList;
	}

}
