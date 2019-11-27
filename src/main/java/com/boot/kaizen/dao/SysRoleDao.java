package com.boot.kaizen.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.boot.kaizen.model.SysRole;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:15:52
 */
@Mapper
public interface SysRoleDao {

	List<SysRole> queryByProId(@Param("projId") Long projId);

	void save(SysRole sysRole);

	SysRole findById(@Param("id") Long id);

	List<Long> findPermissionIdsByRoleId(@Param("roleId") Long roleId);

	void update(SysRole sysRole);

	void delete(@Param("id") Long id);

	/**
	 * 
	 * @Description: 查询项目下的各个角色的人员信息
	 * @author weichengz
	 * @date 2018年11月3日 下午11:40:40
	 */
	List<Map<String, Object>> findRolePersion(@Param("projId") Long projId);

	/**
	 * 
	 * @Description: 校验唯一性
	 * @author weichengz
	 * @date 2018年11月24日 下午8:03:02
	 */
	Long chechUnique(@Param("name") String name, @Param("projId") Long projId);

	List<SysRole> queryByProIdAndUserId(@Param("projId") Long projId, @Param("userId") Long userId);

	List<SysRole> find(@Param("map") Map<String, Object> map);

}
