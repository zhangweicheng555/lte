package com.boot.kaizen.model.lte;

import com.boot.kaizen.model.BaseEntity;

/**
 * lte规划表设计 lte_plane
 * 
 * String businessKey = "LtePlan" + "_" + ltePlan.getmENodeBID() + "_" +
 * ltePlan.getId();
 * 
 * @author weichengz
 * @date 2018年10月28日 上午3:37:57
 */
public class LtePlan extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	private String mENodeBID;// 基站号
	private String mBaseStationName;// 基站名
	private String mBaseStationType;// 基站类型
	private String mAltitude;// 海拔
	private String mLongitude;// 经度
	private String mLatitude;// 纬度
	private String mTac;// tac

	private String testPerson;// 测试工程师
	private String testPersonPhone;// 测试工程师电话
	private String backPerson;// 后台工程师
	private String backPersonPhone;// 后台工程师电话
	private String testTime;// 测试时间 yyyy-mm-dd

	private String dealPersonId;// 接收改计划任务的人的id
	private Long status = 0L;// 最终是不是报告可以审核 之后最后一步的时候 才设置为1 0 未审核 1正在审核通过 2完成

	/**
	 * 上海流程需要
	 */
	private String processState;// 流程任务的状态 0未发布 1已运行 2完成
	private String deviceCompany;//设备厂家
	private String secondCompany;//分公司
	
	
	public String getDeviceCompany() {
		return deviceCompany;
	}

	public void setDeviceCompany(String deviceCompany) {
		this.deviceCompany = deviceCompany;
	}

	public String getSecondCompany() {
		return secondCompany;
	}

	public void setSecondCompany(String secondCompany) {
		this.secondCompany = secondCompany;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getDealPersonId() {
		return dealPersonId;
	}

	public void setDealPersonId(String dealPersonId) {
		this.dealPersonId = dealPersonId;
	}

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

	public String getmBaseStationType() {
		return mBaseStationType;
	}

	public void setmBaseStationType(String mBaseStationType) {
		this.mBaseStationType = mBaseStationType;
	}

	public String getmAltitude() {
		return mAltitude;
	}

	public void setmAltitude(String mAltitude) {
		this.mAltitude = mAltitude;
	}

	public String getmLongitude() {
		return mLongitude;
	}

	public void setmLongitude(String mLongitude) {
		this.mLongitude = mLongitude;
	}

	public String getmLatitude() {
		return mLatitude;
	}

	public void setmLatitude(String mLatitude) {
		this.mLatitude = mLatitude;
	}

	public String getmTac() {
		return mTac;
	}

	public void setmTac(String mTac) {
		this.mTac = mTac;
	}

	public String getTestPerson() {
		return testPerson;
	}

	public void setTestPerson(String testPerson) {
		this.testPerson = testPerson;
	}

	public String getTestPersonPhone() {
		return testPersonPhone;
	}

	public void setTestPersonPhone(String testPersonPhone) {
		this.testPersonPhone = testPersonPhone;
	}

	public String getBackPerson() {
		return backPerson;
	}

	public void setBackPerson(String backPerson) {
		this.backPerson = backPerson;
	}

	public String getBackPersonPhone() {
		return backPersonPhone;
	}

	public void setBackPersonPhone(String backPersonPhone) {
		this.backPersonPhone = backPersonPhone;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public LtePlan() {
		super();
	}

	public LtePlan(String mENodeBID, String mBaseStationName, String mBaseStationType, String mAltitude,
			String mLongitude, String mLatitude, String mTac, String testPerson, String testPersonPhone,
			String backPerson, String backPersonPhone, String testTime, String dealPersonId, Long status) {
		super();
		this.mENodeBID = mENodeBID;
		this.mBaseStationName = mBaseStationName;
		this.mBaseStationType = mBaseStationType;
		this.mAltitude = mAltitude;
		this.mLongitude = mLongitude;
		this.mLatitude = mLatitude;
		this.mTac = mTac;
		this.testPerson = testPerson;
		this.testPersonPhone = testPersonPhone;
		this.backPerson = backPerson;
		this.backPersonPhone = backPersonPhone;
		this.testTime = testTime;
		this.dealPersonId = dealPersonId;
		this.status = status;
	}

}
