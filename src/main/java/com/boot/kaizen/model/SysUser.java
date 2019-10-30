package com.boot.kaizen.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @author weichengz
 * @date 2019年5月30日 上午8:53:35
 */
public class SysUser extends BaseEntity<Long> {

	private static final long serialVersionUID = -6525908145032868837L;

	private String username;
	private String password;
	private String nickname;
	private String phone;
	private String telephone;
	private String email;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	private Integer sex;
	private String headImgUrl;
	private Integer status;

	/** 流程角色相关 */
	/** 厂商 */
	private String comapny;
	/** 分区归属 */
	private String comapnyBelong;
	/** 分公司名称 */
	private String secondConpany;

	private String roleIdsStr;

	/** 添加用户的角色使用 */

	public String getComapny() {
		return comapny;
	}

	public String getRoleIdsStr() {
		return roleIdsStr;
	}

	public void setRoleIdsStr(String roleIdsStr) {
		this.roleIdsStr = roleIdsStr;
	}

	public void setComapny(String comapny) {
		this.comapny = comapny;
	}

	public String getComapnyBelong() {
		return comapnyBelong;
	}

	public void setComapnyBelong(String comapnyBelong) {
		this.comapnyBelong = comapnyBelong;
	}

	public String getSecondConpany() {
		return secondConpany;
	}

	public void setSecondConpany(String secondConpany) {
		this.secondConpany = secondConpany;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public interface Status {
		/** 禁用 */
		int DISABLED = 0;
		/** 有效的 */
		int VALID = 1;
		/** 锁定 */
		int LOCKED = 2;
	}

}
