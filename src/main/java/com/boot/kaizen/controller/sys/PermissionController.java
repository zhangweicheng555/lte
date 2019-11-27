package com.boot.kaizen.controller.sys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.dao.PermissionDao;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.enump.Constant;
import com.boot.kaizen.model.Permission;
import com.boot.kaizen.service.PermissionService;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.UserUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;

/**
 * 权限控制层
 * 
 * @author weichengz
 * @date 2018年9月2日 上午10:05:44
 */
@Api(tags = "权限")
@RestController
@RequestMapping("/permissions")
public class PermissionController {

	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private PermissionService permissionService;

	@GetMapping("/current")
	public List<Permission> permissionsCurrent() {
		LoginUser loginUser = UserUtil.getLoginUser();
		List<Permission> list = loginUser.getPermissions();
		final List<Permission> permissions = list.stream().filter(l -> l.getType().equals(1))
				.collect(Collectors.toList());
		List<Permission> firstLevel = permissions.stream().filter(p -> p.getParentId().equals(0L))
				.collect(Collectors.toList());
		firstLevel.parallelStream().forEach(p -> {
			setChild(p, permissions);
		});

		return firstLevel;
	}

	/**
	 * 根据项目号 用户名加载权限资源 提供微服务项目使用
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月31日 下午3:13:14
	 */
	@PostMapping(value = "/getPermissionsFouUserProj")
	public List<Permission> getPermissionsFouUserProj(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "projId", required = true) Long projId) {
		List<Permission> list = permissionService.queryByUserIdAndProjId(username, projId);
		final List<Permission> permissions = list.stream().filter(l -> l.getType().equals(1))
				.collect(Collectors.toList());
		List<Permission> firstLevel = permissions.stream().filter(p -> p.getParentId().equals(0L))
				.collect(Collectors.toList());
		firstLevel.parallelStream().forEach(p -> {
			setChild(p, permissions);
		});

		return firstLevel;
	}

	/**
	 * 
	 * @Description: 设置子元素
	 * @author weichengz
	 * @date 2018年10月21日 下午7:23:22
	 */
	private void setChild(Permission p, List<Permission> permissions) {
		List<Permission> child = permissions.parallelStream().filter(a -> a.getParentId().equals(p.getId()))
				.collect(Collectors.toList());
		p.setChild(child);
		if (!CollectionUtils.isEmpty(child)) {
			child.parallelStream().forEach(c -> {
				// 递归设置子元素，多级菜单支持
				setChild(c, permissions);
			});
		}
	}

	/**
	 * @Description: 查询资源列表 treeTable
	 * @author weichengz
	 * @date 2018年10月2日 上午11:48:00
	 */
	@RequestMapping(value = "/list")
	// @PreAuthorize("hasAuthority('sys:menu:list')")
	public List<Permission> permissionsList() {
		List<Permission> list = Lists.newArrayList();
		List<Permission> permissionsAll = null;

		LoginUser user = UserUtil.getLoginUser();

		if (Constant.SYSTEM_ID_PROJECT == user.getProjId()) {
			permissionsAll = permissionDao.listAll();
			setPermissionsList(0L, permissionsAll, list);
		}
		return list;
	}

	/**
	 * @Description: 迭代资源 f1-c1,c2 f2-c :依赖treeTable格式
	 * @author weichengz
	 * @date 2018年10月2日 上午11:49:23
	 */
	private void setPermissionsList(Long pId, List<Permission> permissionsAll, List<Permission> list) {
		for (Permission per : permissionsAll) {
			if (per.getParentId().equals(pId)) {
				list.add(per);
				setPermissionsList(per.getId(), permissionsAll, list);
			}
		}
	}

	@GetMapping("/all")
	public JSONArray permissionsAll() {
		List<Permission> permissionsAll = permissionDao.listAll();
		JSONArray array = new JSONArray();
		setPermissionsTree(0L, permissionsAll, array);
		return array;
	}

	/**
	 * @Description: 查询下拉框 父菜单
	 * @author weichengz
	 * @date 2018年10月3日 上午8:33:21
	 */
	@RequestMapping(value = "/parents")
	public List<Permission> parentMenu() {
		List<Permission> parents = permissionDao.listParents();
		return parents;
	}

	@GetMapping("/queryChlidTree")
	public List<Permission> queryChlidTree() {
		List<Permission> permissionsAll = permissionDao.listAll();
		List<Permission> datas = new ArrayList<>();
		queryChlidTree(0L, permissionsAll, datas);
		return datas;
	}

	/**
	 * 
	 * @Description:查询菜单书
	 * @author weichengz
	 * @date 2018年10月21日 下午7:25:10
	 */
	private void queryChlidTree(Long pId, List<Permission> permissionsAll, List<Permission> datas) {
		for (Permission per : permissionsAll) {
			if (1 == per.getType()) {
				if (per.getParentId().equals(pId)) {
					datas.add(per);
					if (permissionsAll.stream().filter(p -> p.getParentId().equals(per.getId())).findAny() != null) {
						queryChlidTree(per.getId(), permissionsAll, datas);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 菜单树
	 * @author weichengz
	 * @date 2018年10月21日 下午7:25:10
	 */
	private void setPermissionsTree(Long pId, List<Permission> permissionsAll, JSONArray array) {
		for (Permission per : permissionsAll) {
			if (per.getParentId().equals(pId)) {
				String string = JSONObject.toJSONString(per);
				JSONObject parent = (JSONObject) JSONObject.parse(string);
				array.add(parent);

				if (permissionsAll.stream().filter(p -> p.getParentId().equals(per.getId())).findAny() != null) {
					JSONArray child = new JSONArray();
					parent.put("child", child);
					setPermissionsTree(per.getId(), permissionsAll, child);
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 拥有多个权限的例子
	 * @author weichengz
	 * @date 2018年10月21日 下午7:23:54
	 */
	@GetMapping(params = "roleId")
	// @PreAuthorize("hasAnyAuthority('sys:menu:list','sys:role:list')")
	public List<Permission> listByRoleId(Long roleId) {
		return permissionDao.listByRoleId(roleId);
	}

	/**
	 * 
	 * @Description: 编辑添加
	 * @author weichengz
	 * @date 2018年10月3日 上午10:06:40
	 */
	@RequestMapping(value = "/edit")
	// @PreAuthorize("hasAuthority('sys:menu:edit')")
	public JsonMsgUtil edit(Permission permission) {
		return permissionService.edit(permission);
	}

	/**
	 * @Description: 根据ID查询
	 * @author weichengz
	 * @date 2018年10月3日 上午9:49:09
	 */
	@RequestMapping(value = "/get")
	// @PreAuthorize("hasAuthority('sys:menu:list')")
	public Permission get(@RequestParam(value = "id", required = true) Long id) {
		return permissionDao.getById(id);
	}

	@PutMapping
	// @PreAuthorize("hasAuthority('sys:menu:edit')")
	public void update(@RequestBody Permission permission) {
		permissionService.update(permission);
	}

	/**
	 * 
	 * @Description: 校验权限
	 * @author weichengz
	 * @date 2018年10月21日 下午7:24:41
	 */
	@GetMapping("/owns")
	public Set<String> ownsPermission() {
		List<Permission> permissions = UserUtil.getLoginUser().getPermissions();
		if (CollectionUtils.isEmpty(permissions)) {
			return Collections.emptySet();
		}

		return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
				.map(Permission::getPermission).collect(Collectors.toSet());
	}

	/**
	 * 
	 * @Description: 删除
	 * @author weichengz
	 * @date 2018年10月3日 下午4:42:42
	 */
	@RequestMapping(value = "/delete")
	// @PreAuthorize("hasAuthority('sys:menu:del')")
	public JsonMsgUtil delete(@RequestParam(value = "id", required = true) Long id) {
		LoginUser user = UserUtil.getLoginUser();
		Long userId = user.getId();
		if (Constant.USER_ID == userId || Constant.USER_ID_MASTER == userId) {
			if (Constant.SYSTEM_ID_PROJECT == user.getProjId()) {
				return permissionService.delete(id);
			} else {
				return new JsonMsgUtil(true, "非系统管理员不可删除资源", "");
			}
		} else {
			return new JsonMsgUtil(true, "非系统管理员不可删除资源", "");
		}
	}

}
