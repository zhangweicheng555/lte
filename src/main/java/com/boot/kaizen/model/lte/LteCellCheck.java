package com.boot.kaizen.model.lte;

import com.boot.kaizen.model.BaseEntity;

/**
 * lte小区核查结果 lte_cell_check
 * 
 * @author weichengz
 * @date 2018年10月28日 上午4:04:02
 */
public class LteCellCheck extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	private Long userId;// 用户id
	private String eNodeBID;// 站号
	private String communityName;// 小区名或者小区号
	private String testDate;// 测试时间

	private String csfbTestCount;// 语音测试次数
	private String csfbFallSuccessCount;// 语音回落成功次数
	private String csfbFallRate;// 语音回落率

	private String goodFtpUpAverageRsrp;// 好点FTP上传-平均RSRP dBm
	private String goodFtpUpAverageSinr;// 好点FTP上传-平均SINR dB
	private String goodFtpUpRate;// 好点FTP上传-上传速率 MB/s

	private String generalFtpUpAverageRsrp;// 中点FTP上传-平均RSRP
	private String generalFtpUpAverageSinr;// 中点FTP上传-平均SINR
	private String generalFtpUpRate;// 中点FTP上传-上传速率

	private String poorFtpUpAverageRsrp;// 差点FTP上传-平均RSRP
	private String poorFtpUpAverageSinr;// 差点FTP上传-平均SINR
	private String poorFtpUpRate;// 差点FTP上传-上传速率

	private String goodFtpDownAverageRsrp;// 好点FTP下载-平均RSRP
	private String goodFtpDownAverageSinr;// 好点FTP下载-平均SINR
	private String goodFtpDownRate;// 好点FTP下载-下载速率

	private String generalFtpDownAverageRsrp;// 中点FTP下载-平均RSRP
	private String generalFtpDownAverageSinr;// 中点FTP下载-平均SINR
	private String generalFtpDownRate;// 中点FTP下载-下载速率

	private String poorFtpDownAverageRsrp;// 差点FTP下载-平均RSRP
	private String poorFtpDownAverageSinr;// 差点FTP下载-平均SINR
	private String poorFtpDownRate;// 差点FTP下载-下载速率 以上单位同上

	private String pci;// PCI
	private String tac;// tac
	private String cellId;// CELL ID
	private String frequency;// 频点

	public LteCellCheck(Long userId, String eNodeBID, String communityName, String testDate, String csfbTestCount,
			String csfbFallSuccessCount, String csfbFallRate, String goodFtpUpAverageRsrp, String goodFtpUpAverageSinr,
			String goodFtpUpRate, String generalFtpUpAverageRsrp, String generalFtpUpAverageSinr,
			String generalFtpUpRate, String poorFtpUpAverageRsrp, String poorFtpUpAverageSinr, String poorFtpUpRate,
			String goodFtpDownAverageRsrp, String goodFtpDownAverageSinr, String goodFtpDownRate,
			String generalFtpDownAverageRsrp, String generalFtpDownAverageSinr, String generalFtpDownRate,
			String poorFtpDownAverageRsrp, String poorFtpDownAverageSinr, String poorFtpDownRate, String pci,
			String tac, String cellId, String frequency) {
		super();
		this.userId = userId;
		this.eNodeBID = eNodeBID;
		this.communityName = communityName;
		this.testDate = testDate;
		this.csfbTestCount = csfbTestCount;
		this.csfbFallSuccessCount = csfbFallSuccessCount;
		this.csfbFallRate = csfbFallRate;
		this.goodFtpUpAverageRsrp = goodFtpUpAverageRsrp;
		this.goodFtpUpAverageSinr = goodFtpUpAverageSinr;
		this.goodFtpUpRate = goodFtpUpRate;
		this.generalFtpUpAverageRsrp = generalFtpUpAverageRsrp;
		this.generalFtpUpAverageSinr = generalFtpUpAverageSinr;
		this.generalFtpUpRate = generalFtpUpRate;
		this.poorFtpUpAverageRsrp = poorFtpUpAverageRsrp;
		this.poorFtpUpAverageSinr = poorFtpUpAverageSinr;
		this.poorFtpUpRate = poorFtpUpRate;
		this.goodFtpDownAverageRsrp = goodFtpDownAverageRsrp;
		this.goodFtpDownAverageSinr = goodFtpDownAverageSinr;
		this.goodFtpDownRate = goodFtpDownRate;
		this.generalFtpDownAverageRsrp = generalFtpDownAverageRsrp;
		this.generalFtpDownAverageSinr = generalFtpDownAverageSinr;
		this.generalFtpDownRate = generalFtpDownRate;
		this.poorFtpDownAverageRsrp = poorFtpDownAverageRsrp;
		this.poorFtpDownAverageSinr = poorFtpDownAverageSinr;
		this.poorFtpDownRate = poorFtpDownRate;
		this.pci = pci;
		this.tac = tac;
		this.cellId = cellId;
		this.frequency = frequency;
	}

	public LteCellCheck() {
		super();
	}

	public Long getUserId() {
		return userId;
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

	public String getCsfbTestCount() {
		return csfbTestCount;
	}

	public void setCsfbTestCount(String csfbTestCount) {
		this.csfbTestCount = csfbTestCount;
	}

	public String getCsfbFallSuccessCount() {
		return csfbFallSuccessCount;
	}

	public void setCsfbFallSuccessCount(String csfbFallSuccessCount) {
		this.csfbFallSuccessCount = csfbFallSuccessCount;
	}

	public String getCsfbFallRate() {
		return csfbFallRate;
	}

	public void setCsfbFallRate(String csfbFallRate) {
		this.csfbFallRate = csfbFallRate;
	}

	public String getGoodFtpUpAverageRsrp() {
		return goodFtpUpAverageRsrp;
	}

	public void setGoodFtpUpAverageRsrp(String goodFtpUpAverageRsrp) {
		this.goodFtpUpAverageRsrp = goodFtpUpAverageRsrp;
	}

	public String getGoodFtpUpAverageSinr() {
		return goodFtpUpAverageSinr;
	}

	public void setGoodFtpUpAverageSinr(String goodFtpUpAverageSinr) {
		this.goodFtpUpAverageSinr = goodFtpUpAverageSinr;
	}

	public String getGoodFtpUpRate() {
		return goodFtpUpRate;
	}

	public void setGoodFtpUpRate(String goodFtpUpRate) {
		this.goodFtpUpRate = goodFtpUpRate;
	}

	public String getGeneralFtpUpAverageRsrp() {
		return generalFtpUpAverageRsrp;
	}

	public void setGeneralFtpUpAverageRsrp(String generalFtpUpAverageRsrp) {
		this.generalFtpUpAverageRsrp = generalFtpUpAverageRsrp;
	}

	public String getGeneralFtpUpAverageSinr() {
		return generalFtpUpAverageSinr;
	}

	public void setGeneralFtpUpAverageSinr(String generalFtpUpAverageSinr) {
		this.generalFtpUpAverageSinr = generalFtpUpAverageSinr;
	}

	public String getGeneralFtpUpRate() {
		return generalFtpUpRate;
	}

	public void setGeneralFtpUpRate(String generalFtpUpRate) {
		this.generalFtpUpRate = generalFtpUpRate;
	}

	public String getPoorFtpUpAverageRsrp() {
		return poorFtpUpAverageRsrp;
	}

	public void setPoorFtpUpAverageRsrp(String poorFtpUpAverageRsrp) {
		this.poorFtpUpAverageRsrp = poorFtpUpAverageRsrp;
	}

	public String getPoorFtpUpAverageSinr() {
		return poorFtpUpAverageSinr;
	}

	public void setPoorFtpUpAverageSinr(String poorFtpUpAverageSinr) {
		this.poorFtpUpAverageSinr = poorFtpUpAverageSinr;
	}

	public String getPoorFtpUpRate() {
		return poorFtpUpRate;
	}

	public void setPoorFtpUpRate(String poorFtpUpRate) {
		this.poorFtpUpRate = poorFtpUpRate;
	}

	public String getGoodFtpDownAverageRsrp() {
		return goodFtpDownAverageRsrp;
	}

	public void setGoodFtpDownAverageRsrp(String goodFtpDownAverageRsrp) {
		this.goodFtpDownAverageRsrp = goodFtpDownAverageRsrp;
	}

	public String getGoodFtpDownAverageSinr() {
		return goodFtpDownAverageSinr;
	}

	public void setGoodFtpDownAverageSinr(String goodFtpDownAverageSinr) {
		this.goodFtpDownAverageSinr = goodFtpDownAverageSinr;
	}

	public String getGoodFtpDownRate() {
		return goodFtpDownRate;
	}

	public void setGoodFtpDownRate(String goodFtpDownRate) {
		this.goodFtpDownRate = goodFtpDownRate;
	}

	public String getGeneralFtpDownAverageRsrp() {
		return generalFtpDownAverageRsrp;
	}

	public void setGeneralFtpDownAverageRsrp(String generalFtpDownAverageRsrp) {
		this.generalFtpDownAverageRsrp = generalFtpDownAverageRsrp;
	}

	public String getGeneralFtpDownAverageSinr() {
		return generalFtpDownAverageSinr;
	}

	public void setGeneralFtpDownAverageSinr(String generalFtpDownAverageSinr) {
		this.generalFtpDownAverageSinr = generalFtpDownAverageSinr;
	}

	public String getGeneralFtpDownRate() {
		return generalFtpDownRate;
	}

	public void setGeneralFtpDownRate(String generalFtpDownRate) {
		this.generalFtpDownRate = generalFtpDownRate;
	}

	public String getPoorFtpDownAverageRsrp() {
		return poorFtpDownAverageRsrp;
	}

	public void setPoorFtpDownAverageRsrp(String poorFtpDownAverageRsrp) {
		this.poorFtpDownAverageRsrp = poorFtpDownAverageRsrp;
	}

	public String getPoorFtpDownAverageSinr() {
		return poorFtpDownAverageSinr;
	}

	public void setPoorFtpDownAverageSinr(String poorFtpDownAverageSinr) {
		this.poorFtpDownAverageSinr = poorFtpDownAverageSinr;
	}

	public String getPoorFtpDownRate() {
		return poorFtpDownRate;
	}

	public void setPoorFtpDownRate(String poorFtpDownRate) {
		this.poorFtpDownRate = poorFtpDownRate;
	}

	public String getPci() {
		return pci;
	}

	public void setPci(String pci) {
		this.pci = pci;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
