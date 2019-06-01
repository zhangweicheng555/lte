package com.boot.kaizen.model.lte;

import com.boot.kaizen.model.BaseEntity;

/**
 * lte工参表设计  lte_gc
 * 
 * @author weichengz
 * @date 2018年10月28日 上午3:44:34
 */
public class LteGcParameter extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	private String mENodeBID;// 基站号
	private String mBaseStationName;// 基站名

	/**小区工程参数**/
	private String mCellID;// 小区号    
	private String mCellName;// 小区名称
	private String mFrequency;// 频点
	private String mPCI;// PCI
	
	/***小区网优参数*/
	private String mRsPower;// RsPower
	private String mAntennaHangUp;// 天线挂高
	private String mAzimuth;// 方位角
	private String mMechanicalLowerInclination;// 机械下倾角
	private String mPresetElectricDip;// 预置电下倾
	private String mtotalLowerInclination;// 总下倾角

	private String configName;// 这个字段用于测试时间

	public String getmENodeBID() {
		return mENodeBID;
	}

	public void setmENodeBID(String mENodeBID) {
		this.mENodeBID = mENodeBID;
	}

	public String getmBaseStationName() {
		return mBaseStationName;
	}

	public void setmBaseStationName(String mBaseStationName) {
		this.mBaseStationName = mBaseStationName;
	}

	public String getmCellID() {
		return mCellID;
	}

	public void setmCellID(String mCellID) {
		this.mCellID = mCellID;
	}

	public String getmCellName() {
		return mCellName;
	}

	public void setmCellName(String mCellName) {
		this.mCellName = mCellName;
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

	public String getmMechanicalLowerInclination() {
		return mMechanicalLowerInclination;
	}

	public void setmMechanicalLowerInclination(String mMechanicalLowerInclination) {
		this.mMechanicalLowerInclination = mMechanicalLowerInclination;
	}

	public String getmPresetElectricDip() {
		return mPresetElectricDip;
	}

	public void setmPresetElectricDip(String mPresetElectricDip) {
		this.mPresetElectricDip = mPresetElectricDip;
	}

	public String getMtotalLowerInclination() {
		return mtotalLowerInclination;
	}

	public void setMtotalLowerInclination(String mtotalLowerInclination) {
		this.mtotalLowerInclination = mtotalLowerInclination;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public LteGcParameter(String mENodeBID, String mBaseStationName, String mCellID, String mCellName,
			String mFrequency, String mPCI, String mRsPower, String mAntennaHangUp, String mAzimuth,
			String mMechanicalLowerInclination, String mPresetElectricDip, String mtotalLowerInclination,
			String configName) {
		super();
		this.mENodeBID = mENodeBID;
		this.mBaseStationName = mBaseStationName;
		this.mCellID = mCellID;
		this.mCellName = mCellName;
		this.mFrequency = mFrequency;
		this.mPCI = mPCI;
		this.mRsPower = mRsPower;
		this.mAntennaHangUp = mAntennaHangUp;
		this.mAzimuth = mAzimuth;
		this.mMechanicalLowerInclination = mMechanicalLowerInclination;
		this.mPresetElectricDip = mPresetElectricDip;
		this.mtotalLowerInclination = mtotalLowerInclination;
		this.configName = configName;
	}

	public LteGcParameter() {
		super();
	}

}
