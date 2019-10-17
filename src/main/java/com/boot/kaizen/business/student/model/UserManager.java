package com.boot.kaizen.business.student.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.boot.kaizen.config.SerializeKeyConfig;

/**
 * 模板实例步骤3
 * 
 * @author weichengz
 * @date 2019年9月26日 下午12:12:15
 */
@TableName("us_users")
public class UserManager extends SerializeKeyConfig<UserManager> {

	/**
	 * @author weichengz
	 * @date 2019年8月30日 下午4:54:53
	 */
	private static final long serialVersionUID = 1L;

	private String userLogin;

	private String userName;

	private String userPassword;

	private Integer userStatus;

	private String userLogin2;

	private String userMail;

	private String userMail1;

	private String userPhone;

	private String userCompany;

	private String userSuper;

	private Date userExpire;

	private String sessionId;

	private String province;

	private String city;

	private String selectc;

	private String selectp;

	private String userType;

	private String projectId;

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserLogin2() {
		return userLogin2;
	}

	public void setUserLogin2(String userLogin2) {
		this.userLogin2 = userLogin2;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserMail1() {
		return userMail1;
	}

	public void setUserMail1(String userMail1) {
		this.userMail1 = userMail1;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}

	public String getUserSuper() {
		return userSuper;
	}

	public void setUserSuper(String userSuper) {
		this.userSuper = userSuper;
	}

	public Date getUserExpire() {
		return userExpire;
	}

	public void setUserExpire(Date userExpire) {
		this.userExpire = userExpire;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSelectc() {
		return selectc;
	}

	public void setSelectc(String selectc) {
		this.selectc = selectc;
	}

	public String getSelectp() {
		return selectp;
	}

	public void setSelectp(String selectp) {
		this.selectp = selectp;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public UserManager(String id, String userLogin, String userName, String userPassword, Integer userStatus,
			String userLogin2, String userMail, String userMail1, String userPhone, String userCompany,
			String userSuper, Date userExpire, String sessionId, String province, String city, String selectc,
			String selectp, String userType, String projectId) {
		super();
		this.id = id;
		this.userLogin = userLogin;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userStatus = userStatus;
		this.userLogin2 = userLogin2;
		this.userMail = userMail;
		this.userMail1 = userMail1;
		this.userPhone = userPhone;
		this.userCompany = userCompany;
		this.userSuper = userSuper;
		this.userExpire = userExpire;
		this.sessionId = sessionId;
		this.province = province;
		this.city = city;
		this.selectc = selectc;
		this.selectp = selectp;
		this.userType = userType;
		this.projectId = projectId;
	}

	public UserManager() {
		super();
	}

}