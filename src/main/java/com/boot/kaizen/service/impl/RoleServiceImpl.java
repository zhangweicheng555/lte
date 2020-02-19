package com.boot.kaizen.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.kaizen.dao.SysRoleDao;
import com.boot.kaizen.dao.SysRolePermissionDao;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.TreeTable;
import com.boot.kaizen.entity.ZtreeModel;
import com.boot.kaizen.enump.Constant;
import com.boot.kaizen.model.SysProject;
import com.boot.kaizen.model.SysProjectRoleRelation;
import com.boot.kaizen.model.SysRole;
import com.boot.kaizen.model.SysRolePermission;
import com.boot.kaizen.service.ProjectRoleRelationService;
import com.boot.kaizen.service.RoleService;
import com.boot.kaizen.service.SysProjectService;
import com.boot.kaizen.service.SysRolePermissionService;
import com.boot.kaizen.service.SysRoleUserService;
import com.boot.kaizen.util.JsonMsgUtil;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:28:29
 */
@Service
public class RoleServiceImpl implements RoleService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private ProjectRoleRelationService projectRoleRelationService;
	@Autowired
	private SysProjectService projectService;
	@Autowired
	private SysRolePermissionService rolePermissionService;
	@Autowired
	private SysRolePermissionDao sysRolePermissionDao;
	@Autowired
	private SysRoleUserService roleUserService;

	@Override
	public List<TreeTable> list(LoginUser user, String projName) {

		Long projId = null;
		if (Constant.SYSTEM_ID_PROJECT != user.getProjId()) {
			projId = user.getProjId();
		}
		List<TreeTable> treeTables = new ArrayList<TreeTable>();
		List<SysProject> projects = projectService.findWithRoleRealtion(projId, projName);
		if (projects != null && projects.size() > 0) {
			for (SysProject project : projects) {
				TreeTable table = new TreeTable(project.getId(), project.getProjName(), "", project.getCreateTime(),
						project.getUpdateTime(), -1L);
				treeTables.add(table);
				List<SysRole> roles = project.getRoles();
				if (roles != null && roles.size() > 0) {
					for (SysRole sysRole : roles) {
						TreeTable roleTable = new TreeTable(sysRole.getId(), sysRole.getName(),
								sysRole.getDescription(), sysRole.getCreateTime(), sysRole.getUpdateTime(),
								project.getId());
						treeTables.add(roleTable);
					}
				}
			}
		}
		return treeTables;
	}

	@Override
	public JsonMsgUtil edit(SysRole sysRole, String permissionIds, String projId) {
		JsonMsgUtil j = new JsonMsgUtil(false);
		try {
			if (sysRole.getId() != null) {

				sysRole.setUpdateTime(new Date());
				sysRoleDao.update(sysRole);

				// 删除角色 项目 资源关系表
				projectRoleRelationService.deleteByRoleAndProId(sysRole.getId());
				rolePermissionService.deleteByRoleId(sysRole.getId());
			} else {// add
				sysRole.setCreateTime(new Date());
				sysRoleDao.save(sysRole);
			}

			List<SysProjectRoleRelation> relations = new ArrayList<>();
			if (StringUtils.isNoneBlank(projId)) {
				String[] ids = projId.trim().split(",");
				for (String id : ids) {
					relations.add(new SysProjectRoleRelation(sysRole.getId(), Long.valueOf(id)));
				}
			}
			projectRoleRelationService.batchInsert(relations);

			List<SysRolePermission> rolePermissions = new ArrayList<>();
			if (StringUtils.isNoneBlank(permissionIds)) {
				String[] ids = permissionIds.trim().split(",");
				for (String id : ids) {
					rolePermissions.add(new SysRolePermission(sysRole.getId(), Long.valueOf(id)));
				}
			}
			sysRolePermissionDao.batchInsert(rolePermissions);

			j = new JsonMsgUtil(true, "操作成功", null);
		} catch (Exception e) {
			//e.printStackTrace();
			j.setMessage("操作失败");
		}

		return j;
	}

	@Override
	public SysRole findById(Long id) {
		return sysRoleDao.findById(id);
	}

	@Override
	public List<Long> findPermissionIdsByRoleId(Long roleId) {
		return sysRoleDao.findPermissionIdsByRoleId(roleId);
	}

	@Transactional
	@Override
	public JsonMsgUtil delete(Long id) {
		JsonMsgUtil j = new JsonMsgUtil(false);
		try {
			projectRoleRelationService.deleteByRoleAndProId(id);
			rolePermissionService.deleteByRoleId(id);
			roleUserService.delete(id);
			sysRoleDao.delete(id);
			j = new JsonMsgUtil(true, "删除操作成功", "");
		} catch (Exception e) {
			j.setMessage("删除操作失败");
			//e.printStackTrace();
		}
		return j;
	}

	@Override
	public List<ZtreeModel> findRolePersion(Long projId) {
		List<ZtreeModel> ztreeModels = new ArrayList<ZtreeModel>();
		List<Map<String, Object>> roleUsers = sysRoleDao.findRolePersion(projId);
		if (roleUsers != null && roleUsers.size() > 0) {
			for (Map<String, Object> map : roleUsers) {
				Long roleId = Long.valueOf(map.get("roleId").toString());

				String roleName = map.get("roleName").toString();
				String userList = map.get("userList").toString();
				ZtreeModel ztreeModel = new ZtreeModel(roleId, 0L, roleName);
				ztreeModels.add(ztreeModel);

				if (userList != null) {
					String[] array = userList.split(",");
					for (String userIdName : array) {
						String[] idName = userIdName.split("_");
						Long userId = Long.valueOf(idName[0].toString());
						String userName = idName[1].toString();
						ZtreeModel ztreeModelChild = new ZtreeModel(userId, ztreeModel.getId(), userName);
						ztreeModels.add(ztreeModelChild);
					}
				}
			}
		}
		return ztreeModels;
	}

	@Override
	public JsonMsgUtil chechUnique(String name, Long projId) {
		JsonMsgUtil j = new JsonMsgUtil(false, "改角色名称已经存在", "");
		Long num = sysRoleDao.chechUnique(name, projId);
		if (num != null && num == 0L) {
			j = new JsonMsgUtil(true, "不存在的角色名称", "");
		}
		return j;
	}

	@Override
	public List<SysRole> queryByProId(Long projId) {
		return sysRoleDao.queryByProId(projId);
	}

	@Override
	public List<SysRole> queryByProIdAndUserId(Long projId, Long userId) {
		if (projId == null) {
			throw new IllegalArgumentException("项目ID不能为空");
		}
		if (userId == null) {
			throw new IllegalArgumentException("用户ID不能为空");
		}
		return sysRoleDao.queryByProIdAndUserId(projId, userId);
	}

	@Override
	public List<SysRole> find(Map<String, Object> map) {
		return sysRoleDao.find(map);
	}
}
