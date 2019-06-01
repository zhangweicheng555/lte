package com.boot.kaizen.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 项目实体类设计
 * 
 * @author weichengz
 * @date 2018年9月8日 上午10:08:43
 */
public class SysProject extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	private String projName;
	private String projCode;
	/**简介*/
	private String projIntro;
	private String sort;
	@JsonIgnore
	private List<SysRole> roles;

	private String roleNames;
	private String roleIds;

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getProjName() {
		return projName;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getProjCode() {
		return projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public String getProjIntro() {
		return projIntro;
	}

	public void setProjIntro(String projIntro) {
		this.projIntro = projIntro;
	}

}
