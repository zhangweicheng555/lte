package com.boot.kaizen.model;

import java.io.Serializable;

/**
 * 用户项目角色实体类设计
 * 
 * @author weichengz
 * @date 2018年9月8日 上午10:26:05
 */
public class SysRolePermission implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long roleId;
	private Long permissionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public SysRolePermission(Long id, Long roleId, Long permissionId) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.permissionId = permissionId;
	}

	
	public SysRolePermission(Long roleId, Long permissionId) {
		super();
		this.roleId = roleId;
		this.permissionId = permissionId;
	}

	public SysRolePermission() {
		super();
	}

}
