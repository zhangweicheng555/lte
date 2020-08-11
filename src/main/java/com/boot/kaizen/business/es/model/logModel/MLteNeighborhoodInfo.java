package com.boot.kaizen.business.es.model.logModel;

/**
 * Created by 今夜犬吠 on 2017/6/1.
 * <p>
 * Lte邻区信息
 */

public class MLteNeighborhoodInfo {

	/// *移动国家码*/ private String mLteNeighborhoodMCC = "-";
	/// *移动网络码*/ private String MLteNeighborhoodMNC = "-";
	/* 小区识别码 */ private String mLteNeighborhoodCI = "-";
	/* RSRQ */private String mLteNeighborhoodRsrq = "-";
	/* 物理小区标识 */ private String mLteNeighborhoodPCI = "-";
	/* 中心频点 */public String mLteNeighborhoodEarfcn = "-";
	/* 跟踪区域码 */ private String mLteNeighborhoodTAC = "-";
	/* Band */private String mBand = "-";
	/* 信号强度/信噪比 */private String mLteNeighborhoodRSRPOrSINR = "-";
	/* 信号强度级别 */private int mRsrpLevel = -1;

	private String siteName;

	public String getmLteNeighborhoodCI() {
		return mLteNeighborhoodCI;
	}

	public void setmLteNeighborhoodCI(String mLteNeighborhoodCI) {
		this.mLteNeighborhoodCI = mLteNeighborhoodCI;
	}

	public String getmLteNeighborhoodRsrq() {
		return mLteNeighborhoodRsrq;
	}

	public void setmLteNeighborhoodRsrq(String mLteNeighborhoodRsrq) {
		this.mLteNeighborhoodRsrq = mLteNeighborhoodRsrq;
	}

	public String getmLteNeighborhoodPCI() {
		return mLteNeighborhoodPCI;
	}

	public void setmLteNeighborhoodPCI(String mLteNeighborhoodPCI) {
		this.mLteNeighborhoodPCI = mLteNeighborhoodPCI;
	}

	public String getmLteNeighborhoodEarfcn() {
		return mLteNeighborhoodEarfcn;
	}

	public void setmLteNeighborhoodEarfcn(String mLteNeighborhoodEarfcn) {
		this.mLteNeighborhoodEarfcn = mLteNeighborhoodEarfcn;
	}

	public String getmLteNeighborhoodTAC() {
		return mLteNeighborhoodTAC;
	}

	public void setmLteNeighborhoodTAC(String mLteNeighborhoodTAC) {
		this.mLteNeighborhoodTAC = mLteNeighborhoodTAC;
	}

	public String getmBand() {
		return mBand;
	}

	public void setmBand(String mBand) {
		this.mBand = mBand;
	}

	public String getmLteNeighborhoodRSRPOrSINR() {
		return mLteNeighborhoodRSRPOrSINR;
	}

	public void setmLteNeighborhoodRSRPOrSINR(String mLteNeighborhoodRSRPOrSINR) {
		this.mLteNeighborhoodRSRPOrSINR = mLteNeighborhoodRSRPOrSINR;
	}

	public int getmRsrpLevel() {
		return mRsrpLevel;
	}

	public void setmRsrpLevel(int mRsrpLevel) {
		this.mRsrpLevel = mRsrpLevel;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public MLteNeighborhoodInfo(String mLteNeighborhoodCI, String mLteNeighborhoodRsrq, String mLteNeighborhoodPCI,
			String mLteNeighborhoodEarfcn, String mLteNeighborhoodTAC, String mBand, String mLteNeighborhoodRSRPOrSINR,
			int mRsrpLevel, String siteName) {
		super();
		this.mLteNeighborhoodCI = mLteNeighborhoodCI;
		this.mLteNeighborhoodRsrq = mLteNeighborhoodRsrq;
		this.mLteNeighborhoodPCI = mLteNeighborhoodPCI;
		this.mLteNeighborhoodEarfcn = mLteNeighborhoodEarfcn;
		this.mLteNeighborhoodTAC = mLteNeighborhoodTAC;
		this.mBand = mBand;
		this.mLteNeighborhoodRSRPOrSINR = mLteNeighborhoodRSRPOrSINR;
		this.mRsrpLevel = mRsrpLevel;
		this.siteName = siteName;
	}

	public MLteNeighborhoodInfo() {
		super();
	}

	

}
