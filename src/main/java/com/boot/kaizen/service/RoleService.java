package com.boot.kaizen.service;

import java.util.List;
import java.util.Map;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.TreeTable;
import com.boot.kaizen.entity.ZtreeModel;
import com.boot.kaizen.model.SysRole;
import com.boot.kaizen.util.JsonMsgUtil;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:26:11
 */
public interface RoleService {

	public List<TreeTable> list(LoginUser user, String projName);

	public JsonMsgUtil edit(SysRole sysRole, String permissionIds, String projId);

	public SysRole findById(Long id);

	public List<Long> findPermissionIdsByRoleId(Long roleId);

	public JsonMsgUtil delete(Long id);

	/**
	 * 
	 * @Description: 根据项目的id查询角色人员树
	 * @author weichengz
	 * @date 2018年11月3日 下午11:09:06
	 */
	List<ZtreeModel> findRolePersion(Long projId);

	/**
	 * 
	 * @Description: 同一项目下的统一名字验证
	 * @author weichengz
	 * @date 2018年11月24日 下午7:56:44
	 */
	public JsonMsgUtil chechUnique(String name, Long projId);

	/**
	 * 
	 * @Description: 查询本项目下的所有角色
	 * @author weichengz
	 * @date 2019年2月17日 下午8:31:15
	 */
	List<SysRole> queryByProId(Long projId);

	/**
	 * 
	 * @Description: 查询用户本项目下的所有角色
	 * @author weichengz
	 * @date 2019年2月17日 下午8:31:15
	 */
	List<SysRole> queryByProIdAndUserId(Long projId, Long userId);

	/**
	 * 条件查询
	* @Description: TODO
	* @author weichengz
	* @date 2019年3月13日 上午8:50:29
	 */
	List<SysRole> find(Map<String, Object> map);
}
