package com.boot.kaizen.model;

import java.io.Serializable;

/**
 * 用户项目角色实体类设计
 * 
 * @author weichengz
 * @date 2018年9月8日 上午10:26:05
 */
public class SysProjectRoleRelation implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long roleId;
	private Long projId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SysProjectRoleRelation(Long roleId, Long projId) {
		super();
		this.roleId = roleId;
		this.projId = projId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public SysProjectRoleRelation() {
	}

}
