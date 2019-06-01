package com.boot.kaizen.model;

import java.io.Serializable;

/**
 * 用户角色实体类设计
 * 
 * @author weichengz
 * @date 2018年10月21日 上午11:15:55
 */
public class SysRoleUser implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public SysRoleUser(Long id, Long userId, Long roleId) {
		super();
		this.id = id;
		this.userId = userId;
		this.roleId = roleId;
	}
	public SysRoleUser(Long userId, Long roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	public SysRoleUser() {
		super();
	}

}
