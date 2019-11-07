package com.boot.kaizen.business.es.model.logModel;

/**
 * Created by 今夜犬吠 on 2017/6/1.
 * <p>
 * Lte邻区信息
 */

public class MLteNeighborhoodInfo {

	/// *移动国家码*/ private String mLteNeighborhoodMCC = "-";
	/// *移动网络码*/ private String MLteNeighborhoodMNC = "-";
	/* 小区识别码 */ private String MLteNeighborhoodCI = "-";
	/* RSRQ */private String MLteNeighborhoodRsrq = "-";
	/* 物理小区标识 */ private String MLteNeighborhoodPCI = "-";
	/* 中心频点 */public String mLteNeighborhoodEarfcn = "-";
	/* 跟踪区域码 */ private String MLteNeighborhoodTAC = "-";
	/* Band */private String mBand = "-";
	/* 信号强度/信噪比 */private String mLteNeighborhoodRSRPOrSINR = "-";
	/* 信号强度级别 */private int mRsrpLevel = -1;

	private String siteName;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public int getmRsrpLevel() {
		return mRsrpLevel;
	}

	public void setmRsrpLevel(int mRsrpLevel) {
		this.mRsrpLevel = mRsrpLevel;
	}

	public String getMLteNeighborhoodRsrq() {
		return MLteNeighborhoodRsrq;
	}

	public void setMLteNeighborhoodRsrq(String MLteNeighborhoodRsrq) {
		this.MLteNeighborhoodRsrq = MLteNeighborhoodRsrq;
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

	public String getMLteNeighborhoodCI() {
		return MLteNeighborhoodCI;
	}

	public void setMLteNeighborhoodCI(String MLteNeighborhoodCI) {
		this.MLteNeighborhoodCI = MLteNeighborhoodCI;
	}

	public String getMLteNeighborhoodPCI() {
		return MLteNeighborhoodPCI;
	}

	public void setMLteNeighborhoodPCI(String MLteNeighborhoodPCI) {
		this.MLteNeighborhoodPCI = MLteNeighborhoodPCI;
	}

	public String getmLteNeighborhoodEarfcn() {
		return mLteNeighborhoodEarfcn;
	}

	public void setmLteNeighborhoodEarfcn(String mLteNeighborhoodEarfcn) {
		this.mLteNeighborhoodEarfcn = mLteNeighborhoodEarfcn;
	}

	public String getMLteNeighborhoodTAC() {
		return MLteNeighborhoodTAC;
	}

	public void setMLteNeighborhoodTAC(String MLteNeighborhoodTAC) {
		this.MLteNeighborhoodTAC = MLteNeighborhoodTAC;
	}

	public MLteNeighborhoodInfo() {
	}

}
