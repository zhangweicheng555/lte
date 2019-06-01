package com.boot.kaizen.model.lte;

import com.boot.kaizen.model.BaseEntity;

/**
 * lte测试配置项 lte_config
 * 
 * @author weichengz
 * @date 2018年10月28日 上午3:44:34
 */
public class LteConfig extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	/** 上行配置参数 **/
	private String mVoiceCount;// 语音测试次数
	private String mVoiceTarget;// 成功率目标值

	/*** 下行配置参数 */
	private String mFtpService;// FTP服务器地址
	private String mFtpPort;// FTP服务器端口
	private String mFtpUserName;// FTP服务器用户名
	private String mFtpPaw;// FTP服务器密码
	private String mFtpFileDownPath;// FTP下载文件路径
	private String mFtpFileUpPath;// FTP上传文件路径
	private String mFtpUpRateTarget;// FTP上传速率目标值
	private String mFtpDownRateTarget;// FTP下载速率目标值

	private Long status;// 流程审核的状态 默认是0 待审核 1正在审核 2 审核通过 3 审核不通过 这个去掉


	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getmVoiceCount() {
		return mVoiceCount;
	}

	public void setmVoiceCount(String mVoiceCount) {
		this.mVoiceCount = mVoiceCount;
	}

	public String getmVoiceTarget() {
		return mVoiceTarget;
	}

	public void setmVoiceTarget(String mVoiceTarget) {
		this.mVoiceTarget = mVoiceTarget;
	}

	public String getmFtpService() {
		return mFtpService;
	}

	public void setmFtpService(String mFtpService) {
		this.mFtpService = mFtpService;
	}

	public String getmFtpPort() {
		return mFtpPort;
	}

	public void setmFtpPort(String mFtpPort) {
		this.mFtpPort = mFtpPort;
	}

	public String getmFtpUserName() {
		return mFtpUserName;
	}

	public void setmFtpUserName(String mFtpUserName) {
		this.mFtpUserName = mFtpUserName;
	}

	public String getmFtpPaw() {
		return mFtpPaw;
	}

	public void setmFtpPaw(String mFtpPaw) {
		this.mFtpPaw = mFtpPaw;
	}

	public String getmFtpFileDownPath() {
		return mFtpFileDownPath;
	}

	public void setmFtpFileDownPath(String mFtpFileDownPath) {
		this.mFtpFileDownPath = mFtpFileDownPath;
	}

	public String getmFtpFileUpPath() {
		return mFtpFileUpPath;
	}

	public void setmFtpFileUpPath(String mFtpFileUpPath) {
		this.mFtpFileUpPath = mFtpFileUpPath;
	}

	public String getmFtpUpRateTarget() {
		return mFtpUpRateTarget;
	}

	public void setmFtpUpRateTarget(String mFtpUpRateTarget) {
		this.mFtpUpRateTarget = mFtpUpRateTarget;
	}

	public String getmFtpDownRateTarget() {
		return mFtpDownRateTarget;
	}

	public void setmFtpDownRateTarget(String mFtpDownRateTarget) {
		this.mFtpDownRateTarget = mFtpDownRateTarget;
	}

	public LteConfig() {
		super();
	}

	public LteConfig(String mVoiceCount, String mVoiceTarget, String mFtpService, String mFtpPort, String mFtpUserName,
			String mFtpPaw, String mFtpFileDownPath, String mFtpFileUpPath, String mFtpUpRateTarget,
			String mFtpDownRateTarget) {
		super();
		this.mVoiceCount = mVoiceCount;
		this.mVoiceTarget = mVoiceTarget;
		this.mFtpService = mFtpService;
		this.mFtpPort = mFtpPort;
		this.mFtpUserName = mFtpUserName;
		this.mFtpPaw = mFtpPaw;
		this.mFtpFileDownPath = mFtpFileDownPath;
		this.mFtpFileUpPath = mFtpFileUpPath;
		this.mFtpUpRateTarget = mFtpUpRateTarget;
		this.mFtpDownRateTarget = mFtpDownRateTarget;
	}

}
