package com.boot.kaizen.model;

import java.util.List;

/**
 * 
 * @author weichengz
 * @date 2019年5月30日 上午8:53:31
 */
public class UserDto extends SysUser {

	private static final long serialVersionUID = -184009306207076712L;

	private List<Long> roleIds;

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

}
