package com.boot.kaizen.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 地市实体类设计
 * 
 * @author weichengz
 * @date 2018年9月8日 上午10:08:43
 */
public class SysProject extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	private String projectMapperName; // 所属项目名称 这个一般是自己查询显示用的
	
	private String projIntro; // 所属项目编号 新增  这个废弃20200224

	private String projName;// 地市名字
	private String projCode;// SIM地市的名字
	private String proProvice;// SIM地市省份

	// 20200224新增
	private String hostAp; // SIM工参地址
	private String projSimName;// SIM ProjectName名称
	private String projOperator;// sim项目 运行商 手动填写

	private String sort;
	@JsonIgnore
	private List<SysRole> roles;
	private String roleNames;
	private String roleIds;

	
	public SysProject(String projectMapperName, String projIntro, String projName, String projCode, String proProvice,
			String hostAp, String projSimName, String projOperator, String sort, List<SysRole> roles, String roleNames,
			String roleIds) {
		super();
		this.projectMapperName = projectMapperName;
		this.projIntro = projIntro;
		this.projName = projName;
		this.projCode = projCode;
		this.proProvice = proProvice;
		this.hostAp = hostAp;
		this.projSimName = projSimName;
		this.projOperator = projOperator;
		this.sort = sort;
		this.roles = roles;
		this.roleNames = roleNames;
		this.roleIds = roleIds;
	}

	public String getHostAp() {
		return hostAp;
	}

	public void setHostAp(String hostAp) {
		this.hostAp = hostAp;
	}

	public String getProjSimName() {
		return projSimName;
	}

	public void setProjSimName(String projSimName) {
		this.projSimName = projSimName;
	}

	public String getProjOperator() {
		return projOperator;
	}

	public void setProjOperator(String projOperator) {
		this.projOperator = projOperator;
	}

	public String getProProvice() {
		return proProvice;
	}

	public void setProProvice(String proProvice) {
		this.proProvice = proProvice;
	}

	public String getProjectMapperName() {
		return projectMapperName;
	}

	public void setProjectMapperName(String projectMapperName) {
		this.projectMapperName = projectMapperName;
	}

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

	public SysProject(String projectMapperName, String projIntro, String projName, String projCode, String proProvice,
			String sort, List<SysRole> roles, String roleNames, String roleIds) {
		super();
		this.projectMapperName = projectMapperName;
		this.projIntro = projIntro;
		this.projName = projName;
		this.projCode = projCode;
		this.proProvice = proProvice;
		this.sort = sort;
		this.roles = roles;
		this.roleNames = roleNames;
		this.roleIds = roleIds;
	}

	public SysProject() {
		super();
	}

}
