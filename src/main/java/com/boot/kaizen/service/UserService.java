package com.boot.kaizen.service;

import java.util.List;
import java.util.Map;

import com.boot.kaizen.model.SysUser;
import com.boot.kaizen.util.JsonMsgUtil;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:26:11
 */
public interface UserService {

	/**
	 * 
	 * @Description: 保存用户
	 * @author weichengz
	 * @date 2018年10月6日 下午5:06:10
	 */
	SysUser saveUser(SysUser userDto);

	JsonMsgUtil updateUser(SysUser sysUser,Long projId);

	SysUser getUser(String username);

	void changePassword(Long id, String oldPassword, String newPassword);

	/**
	 * 
	 * @Description: 列表查询
	 * @author weichengz
	 * @date 2018年10月6日 下午5:39:16
	 */
	List<SysUser> find(Map<String, Object> map);

	JsonMsgUtil delete(String ids,Long projId,Long userId);

	JsonMsgUtil findById(Long id);
	
	SysUser selectById(Long id);

	/**
	 * 
	 * @Description: 通过角色Id查询用户的名字
	 * @author weichengz
	 * @date 2018年10月21日 上午9:29:26
	 */
	String findUserNames(Long roleId);
	/**
	 * 
	 * @Description: 通过角色Id查询用户的id
	 * @author weichengz
	 * @date 2018年10月21日 上午9:29:26
	 */
	List<Long> findUserIds(Long roleId);

	/**
	 * app使用
	 * @param roleId
	 * @return
	 */
    SysUser queryUser(Long projId,String username);

    /**
     * 通过项目的id查询用户  id   username
    * @Description: TODO
    * @author weichengz
    * @date 2018年11月18日 上午8:20:01
     */
    List<Map<String, Object>> queryUserByProjId(Long projId);
    
	/**
	 * 通过角色的id查询用户 id username
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年11月18日 上午8:20:01
	 */
	List<Map<String, Object>> queryUserByRoleId(Long roleId);
    /**
     * 添加用户
    * @Description: TODO
    * @author weichengz
    * @date 2019年3月8日 上午9:19:53
     */
    void insertSelective(SysUser sysUser);

	JsonMsgUtil saveUserRole(SysUser sysUser);
}
