package com.boot.kaizen.controller.lte.model;

/**
 * 小区网优参数
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:13:02
 */
public class MCommunityNetworkOptimizationBean {

	/** 天线挂高 */
	private String mAntennaHangUp = "";
	/** 方位角 */
	private String mAzimuth = "";
	/** 机械下倾角 */
	private String mMechanicalLowerInclination = "";
	/** 预置电下倾 */
	private String mPresetElectricDip = "";
	/** RsPower */
	private String mRsPower = "";
	/** 总下倾角 */
	private String mtotalLowerInclination = "";

	public String getmRsPower() {
		return mRsPower;
	}

	public void setmRsPower(String mRsPower) {
		this.mRsPower = mRsPower;
	}

	public String getmAntennaHangUp() {
		return mAntennaHangUp;
	}

	public void setmAntennaHangUp(String mAntennaHangUp) {
		this.mAntennaHangUp = mAntennaHangUp;
	}

	public String getmAzimuth() {
		return mAzimuth;
	}

	public void setmAzimuth(String mAzimuth) {
		this.mAzimuth = mAzimuth;
	}

	public String getMtotalLowerInclination() {
		return mtotalLowerInclination;
	}

	public void setMtotalLowerInclination(String mtotalLowerInclination) {
		this.mtotalLowerInclination = mtotalLowerInclination;
	}

	public String getmPresetElectricDip() {
		return mPresetElectricDip;
	}

	public void setmPresetElectricDip(String mPresetElectricDip) {
		this.mPresetElectricDip = mPresetElectricDip;
	}

	public String getmMechanicalLowerInclination() {
		return mMechanicalLowerInclination;
	}

	public void setmMechanicalLowerInclination(String mMechanicalLowerInclination) {
		this.mMechanicalLowerInclination = mMechanicalLowerInclination;
	}

	public MCommunityNetworkOptimizationBean(String mRsPower, String mAntennaHangUp, String mAzimuth,
			String mtotalLowerInclination, String mPresetElectricDip, String mMechanicalLowerInclination) {
		super();
		this.mRsPower = mRsPower;
		this.mAntennaHangUp = mAntennaHangUp;
		this.mAzimuth = mAzimuth;
		this.mtotalLowerInclination = mtotalLowerInclination;
		this.mPresetElectricDip = mPresetElectricDip;
		this.mMechanicalLowerInclination = mMechanicalLowerInclination;
	}

	public MCommunityNetworkOptimizationBean() {
		super();
	}

}
