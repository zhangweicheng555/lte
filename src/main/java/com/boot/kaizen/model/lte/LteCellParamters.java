package com.boot.kaizen.model.lte;

import java.io.Serializable;

/**
 * 上传小区验收接口
 * 
 * @author weichengz
 * @date 2019年1月6日 下午6:39:46
 */
public class LteCellParamters implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String testDate;
	private Long userId;
	private Long projId;
	private String eNodeBID;
	private String mAntennaHangUp;// 天线挂高
	private String mAzimuth;// 方位角
	private String mtotalLowerInclination;// 总下倾角
	private String mPresetElectricDip;// 预置电下倾
	private String mMechanicalLowerInclination;// 机械下倾角
	private String pci;
	private String cellId;
	private String frequency;// 频点

	
	
	public String geteNodeBID() {
		return eNodeBID;
	}

	public void seteNodeBID(String eNodeBID) {
		this.eNodeBID = eNodeBID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
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

	public String getPci() {
		return pci;
	}

	public void setPci(String pci) {
		this.pci = pci;
	}

	public String getCellId() {
		return cellId;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public LteCellParamters(String id, String testDate, Long userId, Long projId, String mAntennaHangUp,
			String mAzimuth, String mtotalLowerInclination, String mPresetElectricDip,
			String mMechanicalLowerInclination, String pci, String cellId, String frequency) {
		super();
		this.id = id;
		this.testDate = testDate;
		this.userId = userId;
		this.projId = projId;
		this.mAntennaHangUp = mAntennaHangUp;
		this.mAzimuth = mAzimuth;
		this.mtotalLowerInclination = mtotalLowerInclination;
		this.mPresetElectricDip = mPresetElectricDip;
		this.mMechanicalLowerInclination = mMechanicalLowerInclination;
		this.pci = pci;
		this.cellId = cellId;
		this.frequency = frequency;
	}

	public LteCellParamters() {
		super();
	}

}
