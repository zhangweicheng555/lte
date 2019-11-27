package com.boot.kaizen.service;

import java.util.List;

import com.boot.kaizen.model.SysRoleUser;

/**
 * 
 * @author weichengz
 * @date 2019年5月30日 下午3:39:27
 */
public interface SysRoleUserService {

	void delete(Long roleId);

	void batchInsert(List<SysRoleUser> roleUsers);

	/**
	 * 
	 * @Description: 根据用户id批量删除
	 * @author weichengz
	 * @date 2018年10月21日 下午7:35:18
	 */
	void deleteBatch(Long[] userIds);

	/**
	 * 这个方法 废弃
	 * @Description: 通过项目的id删除用户角色的对应关系表
	 * @author weichengz
	 * @date 2018年10月21日 下午8:09:10
	 */
	void deleteByProjIds(Long[] projIds);

	/**
	 * 通过角色Id 用户id查询
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年7月18日 下午3:01:28
	 */
	List<SysRoleUser> queryRoleUserByRoleUserId(Long userId, Long roleId);

}
