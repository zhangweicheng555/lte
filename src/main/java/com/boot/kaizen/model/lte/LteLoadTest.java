package com.boot.kaizen.model.lte;

import com.boot.kaizen.model.BaseEntity;

/**
 * lte路测信息 lte_load_test
 * 
 * @author weichengz
 * @date 2018年10月28日 上午4:21:39
 */
public class LteLoadTest extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	private Long userId;// 用户ID
	private String eNodeBID;// 基站编号
	private String testDate;// 测试时间

	private String sinrThresholdImage;// SINR阈值图
	private String rsrpThresholdImage;// RSRP阈值图
	private String upFtpRateThresholdImage;// FTP上行速率阈值图
	private String downFtpRateThresholdImage;// FTP上行速率阈值图
	
	private String roadLogFile;// 路测Log文件

	private String rsrpFtpUpImage;// 这个是小区的路测信息图的 图片的名字的字符串 逗号分隔

	private String communityName;// 小区名 废弃
	private String sinrFtpUpImage;// FTP上传测试-Sinr打点截图 废弃
	private String upFtpRateImage;// FTP上传测试-速率打点截图 废弃
	private String rsrpFtpDownImage;// FTP下载测试-Rsrp打点截图 废弃
	private String sinrFtpDownImage;// FTP下载测试-Sinr打点截图 废弃
	private String downFtpRateImage;// FTP下载测试-速率打点截图 废弃

	public Long getUserId() {
		return userId;
	}

	public String getUpFtpRateThresholdImage() {
		return upFtpRateThresholdImage;
	}

	public void setUpFtpRateThresholdImage(String upFtpRateThresholdImage) {
		this.upFtpRateThresholdImage = upFtpRateThresholdImage;
	}

	public String getDownFtpRateThresholdImage() {
		return downFtpRateThresholdImage;
	}

	public void setDownFtpRateThresholdImage(String downFtpRateThresholdImage) {
		this.downFtpRateThresholdImage = downFtpRateThresholdImage;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String geteNodeBID() {
		return eNodeBID;
	}

	public void seteNodeBID(String eNodeBID) {
		this.eNodeBID = eNodeBID;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getRsrpFtpUpImage() {
		return rsrpFtpUpImage;
	}

	public void setRsrpFtpUpImage(String rsrpFtpUpImage) {
		this.rsrpFtpUpImage = rsrpFtpUpImage;
	}

	public String getSinrFtpUpImage() {
		return sinrFtpUpImage;
	}

	public void setSinrFtpUpImage(String sinrFtpUpImage) {
		this.sinrFtpUpImage = sinrFtpUpImage;
	}

	public String getUpFtpRateImage() {
		return upFtpRateImage;
	}

	public void setUpFtpRateImage(String upFtpRateImage) {
		this.upFtpRateImage = upFtpRateImage;
	}

	public String getRsrpFtpDownImage() {
		return rsrpFtpDownImage;
	}

	public void setRsrpFtpDownImage(String rsrpFtpDownImage) {
		this.rsrpFtpDownImage = rsrpFtpDownImage;
	}

	public String getSinrFtpDownImage() {
		return sinrFtpDownImage;
	}

	public void setSinrFtpDownImage(String sinrFtpDownImage) {
		this.sinrFtpDownImage = sinrFtpDownImage;
	}

	public String getDownFtpRateImage() {
		return downFtpRateImage;
	}

	public void setDownFtpRateImage(String downFtpRateImage) {
		this.downFtpRateImage = downFtpRateImage;
	}

	public String getSinrThresholdImage() {
		return sinrThresholdImage;
	}

	public void setSinrThresholdImage(String sinrThresholdImage) {
		this.sinrThresholdImage = sinrThresholdImage;
	}

	public String getRsrpThresholdImage() {
		return rsrpThresholdImage;
	}

	public void setRsrpThresholdImage(String rsrpThresholdImage) {
		this.rsrpThresholdImage = rsrpThresholdImage;
	}

	public String getRoadLogFile() {
		return roadLogFile;
	}

	public void setRoadLogFile(String roadLogFile) {
		this.roadLogFile = roadLogFile;
	}

	public LteLoadTest(Long userId, String eNodeBID, String testDate, String rsrpFtpUpImage, 
			String sinrThresholdImage,
			String rsrpThresholdImage, String upFtpRateThresholdImage, String downFtpRateThresholdImage,
			String roadLogFile) {
		super();
		this.userId = userId;
		this.eNodeBID = eNodeBID;
		this.testDate = testDate;
		this.rsrpFtpUpImage=rsrpFtpUpImage;
		this.sinrThresholdImage = sinrThresholdImage;
		this.rsrpThresholdImage = rsrpThresholdImage;
		this.upFtpRateThresholdImage = upFtpRateThresholdImage;
		this.downFtpRateThresholdImage = downFtpRateThresholdImage;
		this.roadLogFile = roadLogFile;
	}

	public LteLoadTest(Long userId, String eNodeBID, String communityName, String testDate, String rsrpFtpUpImage,
			String sinrFtpUpImage, String upFtpRateImage, String rsrpFtpDownImage, String sinrFtpDownImage,
			String downFtpRateImage, String sinrThresholdImage, String rsrpThresholdImage,
			String upFtpRateThresholdImage, String downFtpRateThresholdImage, String roadLogFile) {
		super();
		this.userId = userId;
		this.eNodeBID = eNodeBID;
		this.communityName = communityName;
		this.testDate = testDate;
		this.rsrpFtpUpImage = rsrpFtpUpImage;
		this.sinrFtpUpImage = sinrFtpUpImage;
		this.upFtpRateImage = upFtpRateImage;
		this.rsrpFtpDownImage = rsrpFtpDownImage;
		this.sinrFtpDownImage = sinrFtpDownImage;
		this.downFtpRateImage = downFtpRateImage;
		this.sinrThresholdImage = sinrThresholdImage;
		this.rsrpThresholdImage = rsrpThresholdImage;
		this.upFtpRateThresholdImage = upFtpRateThresholdImage;
		this.downFtpRateThresholdImage = downFtpRateThresholdImage;
		this.roadLogFile = roadLogFile;
	}

	public LteLoadTest() {
		super();
	}

}
