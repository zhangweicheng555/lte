package com.boot.kaizen.model.lte;

import java.io.Serializable;

/**
 * 结构验收接口
 * 
 * @author weichengz
 * @date 2019年1月6日 下午6:41:31
 */
public class LteCellStructuralValidation implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private Long userId;
	private String testDate;
	private Long projId;
	private String eNodeBID;
	private String veryClose;// 超近（true代表通过）
	private String vastDistances;// 超远（false代表不通过）
	private String ultraHigh;// 超高
	private String azimuthSuperoverlap;// 方位超重叠
	private String declinationOverlap;// 下倾超重叠
	private String skyBlockCondition;// 天面阻挡情况
	private String canBeAdjusted;// 天线后期可调整情况

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public String geteNodeBID() {
		return eNodeBID;
	}

	public void seteNodeBID(String eNodeBID) {
		this.eNodeBID = eNodeBID;
	}

	public String getVeryClose() {
		return veryClose;
	}

	public void setVeryClose(String veryClose) {
		this.veryClose = veryClose;
	}

	public String getVastDistances() {
		return vastDistances;
	}

	public void setVastDistances(String vastDistances) {
		this.vastDistances = vastDistances;
	}

	public String getUltraHigh() {
		return ultraHigh;
	}

	public void setUltraHigh(String ultraHigh) {
		this.ultraHigh = ultraHigh;
	}

	public String getAzimuthSuperoverlap() {
		return azimuthSuperoverlap;
	}

	public void setAzimuthSuperoverlap(String azimuthSuperoverlap) {
		this.azimuthSuperoverlap = azimuthSuperoverlap;
	}

	public String getDeclinationOverlap() {
		return declinationOverlap;
	}

	public void setDeclinationOverlap(String declinationOverlap) {
		this.declinationOverlap = declinationOverlap;
	}

	public String getSkyBlockCondition() {
		return skyBlockCondition;
	}

	public void setSkyBlockCondition(String skyBlockCondition) {
		this.skyBlockCondition = skyBlockCondition;
	}

	public String getCanBeAdjusted() {
		return canBeAdjusted;
	}

	public void setCanBeAdjusted(String canBeAdjusted) {
		this.canBeAdjusted = canBeAdjusted;
	}

	public LteCellStructuralValidation(String id, Long userId, String testDate, Long projId, String eNodeBID,
			String veryClose, String vastDistances, String ultraHigh, String azimuthSuperoverlap,
			String declinationOverlap, String skyBlockCondition, String canBeAdjusted) {
		super();
		this.id = id;
		this.userId = userId;
		this.testDate = testDate;
		this.projId = projId;
		this.eNodeBID = eNodeBID;
		this.veryClose = veryClose;
		this.vastDistances = vastDistances;
		this.ultraHigh = ultraHigh;
		this.azimuthSuperoverlap = azimuthSuperoverlap;
		this.declinationOverlap = declinationOverlap;
		this.skyBlockCondition = skyBlockCondition;
		this.canBeAdjusted = canBeAdjusted;
	}

	public LteCellStructuralValidation() {
		super();
	}

}
