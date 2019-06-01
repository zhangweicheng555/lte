package com.boot.kaizen.controller.lte.model;

/**
 * 小区工程参数
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:12:53
 */
public class MCommunityProjectBean {

	/** Cell ID */
	private String mCellID = "";

	/** 频点 */
	private String mFrequency = "";

	/** PCI */
	private String mPCI = "";

	public String getmCellID() {
		return mCellID;
	}

	public void setmCellID(String mCellID) {
		this.mCellID = mCellID;
	}

	public String getmFrequency() {
		return mFrequency;
	}

	public void setmFrequency(String mFrequency) {
		this.mFrequency = mFrequency;
	}

	public String getmPCI() {
		return mPCI;
	}

	public void setmPCI(String mPCI) {
		this.mPCI = mPCI;
	}

	public MCommunityProjectBean(String mCellID, String mFrequency, String mPCI) {
		super();
		this.mCellID = mCellID;
		this.mFrequency = mFrequency;
		this.mPCI = mPCI;
	}

	public MCommunityProjectBean() {
		super();
	}

}
