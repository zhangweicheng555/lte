package com.boot.kaizen.model.log;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志 监听登录的所有信息 login_log
 * 
 * @author a-zhangweicheng
 *
 */
public class LoginLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long projId;
	private String username;
	private String ipNo;
	private String loginAddress;
	private String statusFlag;// 成功1 失败0 就这两个
	private Date loginTime;
	private String msg;// 附加信息

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIpNo() {
		return ipNo;
	}

	public void setIpNo(String ipNo) {
		this.ipNo = ipNo;
	}

	public String getLoginAddress() {
		return loginAddress;
	}

	public void setLoginAddress(String loginAddress) {
		this.loginAddress = loginAddress;
	}

	public String getStatusFlag() {
		if (("1").equals(statusFlag)) {
			return "成功";
		} else if (("0").equals(statusFlag)) {
			return "失败";
		} else {
			return "";
		}
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public LoginLog(Long id, Long projId, String username, String ipNo, String loginAddress, String statusFlag,
			Date loginTime, String msg) {
		super();
		this.id = id;
		this.projId = projId;
		this.username = username;
		this.ipNo = ipNo;
		this.loginAddress = loginAddress;
		this.statusFlag = statusFlag;
		this.loginTime = loginTime;
		this.msg = msg;
	}

	public LoginLog(Long projId, String username, String ipNo, String loginAddress, String statusFlag, Date loginTime,
			String msg) {
		super();
		this.projId = projId;
		this.username = username;
		this.ipNo = ipNo;
		this.loginAddress = loginAddress;
		this.statusFlag = statusFlag;
		this.loginTime = loginTime;
		this.msg = msg;
	}

	public LoginLog() {
		super();
	}

	public interface StatusV {
		int SUCCESS = 1;// 成功
		int FAIL = 0;// 失败
	}

}
