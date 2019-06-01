package com.boot.kaizen.model.lte;

import com.boot.kaizen.model.BaseEntity;

/**
 * lte基站核查结果 lte_station_check
 * 
 * @author weichengz
 * @date 2018年10月28日 上午4:00:48
 */
public class LteStationCheck extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;

	private String eNodeBID;// 站号
	private Long userId;// 用户id
	private String testDate;// 测试时间
	private String latitude;// 经度
	private String longitude;// 纬度
	private String tac;// 实测Tac

	public String geteNodeBID() {
		return eNodeBID;
	}

	public void seteNodeBID(String eNodeBID) {
		this.eNodeBID = eNodeBID;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public LteStationCheck(String eNodeBID, Long userId, String testDate, String latitude, String longitude,
			String tac) {
		super();
		this.eNodeBID = eNodeBID;
		this.userId = userId;
		this.testDate = testDate;
		this.latitude = latitude;
		this.longitude = longitude;
		this.tac = tac;
	}

	public LteStationCheck() {
		super();
	}

}
