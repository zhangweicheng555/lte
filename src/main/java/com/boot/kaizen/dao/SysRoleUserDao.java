package com.boot.kaizen.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.kaizen.model.SysRoleUser;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */
@Mapper
public interface SysRoleUserDao {

	void delete(@Param("roleId") Long roleId);

	void batchInsert(@Param("list") List<SysRoleUser> roleUsers);

	void deleteBatch(@Param("userIds") Long[] userIds);

	void deleteByProjIds(@Param("projIds") Long[] projIds);

	List<SysRoleUser> queryRoleUserByRoleUserId(@Param("userId") Long userId, @Param("roleId") Long roleId);


}
