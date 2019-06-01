package com.boot.kaizen.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.kaizen.model.SysRolePermission;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */
@Mapper
public interface SysRolePermissionDao {

	void batchInsert(@Param("list") List<SysRolePermission> relations);

	void deleteByRoleId(@Param("roleId") Long roleId);

	void deleteByPermissionId(@Param("permissionId") Long permissionId);

	void deleteByProjIds(@Param("projIds") Long[] projIds);
}
