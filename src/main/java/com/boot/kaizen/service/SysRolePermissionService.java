package com.boot.kaizen.service;

import java.util.List;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:26:11
 */
public interface SysRolePermissionService {

	void deleteByRoleId(Long roleId);

	void deleteByPermissionId(Long id);

	void deleteByProjIds(Long[] projIds);

	
}
