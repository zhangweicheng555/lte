package com.boot.kaizen.controller.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.TreeTable;
import com.boot.kaizen.entity.ZtreeModel;
import com.boot.kaizen.enump.Constant;
import com.boot.kaizen.model.SysProject;
import com.boot.kaizen.model.SysRole;
import com.boot.kaizen.service.PermissionService;
import com.boot.kaizen.service.RoleService;
import com.boot.kaizen.service.SysProjectService;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.UserUtil;

/**
 * 角色管理
 * 
 * @author weichengz
 * @date 2018年10月4日 上午1:13:13
 */
@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private SysProjectService projectService;
	@Autowired
	private PermissionService permissionService;

	/**
	 * 
	 * @Description: 角色treeTable列表
	 * @author weichengz
	 * @date 2018年10月5日 下午2:42:05
	 */
	@RequestMapping(value = "/list")
	public List<TreeTable> list(@RequestParam(value = "projName", required = false) String projName) {
		LoginUser user = UserUtil.getLoginUser();
		return roleService.list(user, projName);
	}

	/**
	 * 
	 * @Description: 项目下拉框
	 * @author weichengz
	 * @date 2018年10月5日 下午2:41:44
	 */
	@RequestMapping(value = "/projSelect")
	public List<SysProject> projSelect() {
		LoginUser user = UserUtil.getLoginUser();
		Long projId = null;
		if (Constant.SYSTEM_ID_PROJECT != user.getProjId()) {
			projId = user.getProjId();
		}
		return projectService.findList(projId);
	}

	/**
	 * 
	 * @Description: 查询本项目下的所有的角色
	 * @author weichengz
	 * @date 2019年7月18日 下午2:29:03
	 */
	@RequestMapping(value = "/queryByProId")
	public List<SysRole> queryByProId() {
		LoginUser user = UserUtil.getLoginUser();
		return roleService.queryByProId(user.getProjId());
	}

	/**
	 * 查询用户在该项目下的角色
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年7月18日 下午3:33:11
	 */
	@RequestMapping(value = "/queryMyRoleByProId")
	public List<SysRole> queryMyRoleByProId(@RequestParam("userId") Long userId) {
		LoginUser user = UserUtil.getLoginUser();
		return roleService.queryByProIdAndUserId(user.getProjId(), userId);
	}

	/**
	 * 
	 * @Description: 根据ID查询
	 * @author weichengz
	 * @date 2018年10月5日 下午2:41:32
	 */
	@RequestMapping(value = "/findById")
	public SysRole findById(@RequestParam(value = "id", required = true) Long id) {
		return roleService.findById(id);
	}

	/**
	 * 
	 * @Description: 根据ID删除
	 * @author weichengz
	 * @date 2018年10月5日 下午2:41:32
	 */
	@RequestMapping(value = "/delete")
	public JsonMsgUtil delete(@RequestParam(value = "id", required = true) Long id) {
		return roleService.delete(id);
	}

	/**
	 * 
	 * @Description: 添加/修改
	 * @author weichengz
	 * @date 2018年10月5日 下午2:41:19
	 */
	@RequestMapping(value = "/edit")
	public JsonMsgUtil edit(SysRole sysRole, @RequestParam("permissionIds") String permissionIds,
			@RequestParam("projId") String projId) {
		return roleService.edit(sysRole, permissionIds, projId);
	}

	/**
	 * 
	 * @Description: 查询角色对应的资源树
	 * @author weichengz
	 * @date 2018年10月5日 上午11:56:40 flag:角色的id
	 */
	@RequestMapping(value = "/find")
	public List<ZtreeModel> find(@RequestParam(value = "flag", required = false) Long flag) {
		List<Long> ids = roleService.findPermissionIdsByRoleId(flag);
		if (ids == null) {
			ids = new ArrayList<Long>();
		}
		LoginUser user = UserUtil.getLoginUser();
		return permissionService.find(ids, user);
	}

	/**
	 * 
	 * @Description: 查询角色对应的人员树
	 * @author weichengz
	 * @date 2018年10月5日 上午11:56:40 flag:角色的id
	 */
	@RequestMapping(value = "/findRolePersion")
	public List<ZtreeModel> findRolePersion(@RequestParam(value = "flag", required = true) Long flag) {
		return roleService.findRolePersion(flag);
	}

	/**
	 * 
	 * @Description: 验证角色的唯一性
	 * @author weichengz
	 * @date 2018年11月24日 下午7:56:11
	 */
	@RequestMapping(value = "/chechUnique")
	public JsonMsgUtil chechUnique(@RequestParam(value = "name", required = true) String name,@RequestParam(value = "projId", required = true) Long projId) {
		//LoginUser loginUser = UserUtil.getLoginUser();
		return roleService.chechUnique(name, projId);
	}

	/**
	 * 角色项目过滤
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年1月5日 下午1:09:35
	 */
	@RequestMapping(value = "/getProjectFilter")
	public Map<String, Object> getProjectFilter() {
		/** 角色项目过滤条件使用 */
		Map<String, Object> map = new HashMap<>(1);
		map.put("name", Constant.SYSTEM_ROLE_PROJECT_FLITER);
		return map;
	}

	/**
	 * 角色项目过滤
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年1月5日 下午1:09:35
	 */
	@RequestMapping(value = "/setProjectFilter")
	public Map<String, Object> setProjectFilter(@RequestParam(value = "projName", required = false) String projName) {
		Map<String, Object> map = new HashMap<>(3);
		Constant.SYSTEM_ROLE_PROJECT_FLITER = projName;
		map.put("name", Constant.SYSTEM_ROLE_PROJECT_FLITER);
		return map;
	}
}
