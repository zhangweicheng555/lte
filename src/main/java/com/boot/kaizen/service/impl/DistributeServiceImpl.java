package com.boot.kaizen.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.kaizen.entity.DistributeTreeTable;
import com.boot.kaizen.model.SysProject;
import com.boot.kaizen.model.SysRole;
import com.boot.kaizen.model.SysRoleUser;
import com.boot.kaizen.service.DistributeService;
import com.boot.kaizen.service.SysProjectService;
import com.boot.kaizen.service.SysRoleUserService;
import com.boot.kaizen.service.UserService;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:27:33
 */
@Service
public class DistributeServiceImpl implements DistributeService {

	@Autowired
	private SysProjectService projectService;
	@Autowired
	private SysRoleUserService roleUserService;
	@Autowired
	private UserService userService;

	@Override
	public List<DistributeTreeTable> list(Long projId, String projName) {
		List<DistributeTreeTable> treeTables = new ArrayList<DistributeTreeTable>();
		List<SysProject> projects = projectService.findWithRoleRealtion(projId, projName);
		if (projects != null && projects.size() > 0) {
			for (SysProject project : projects) {
				DistributeTreeTable table = new DistributeTreeTable(project.getId(), project.getProjName(), "", -1L);
				treeTables.add(table);
				List<SysRole> roles = project.getRoles();
				if (roles != null && roles.size() > 0) {
					for (SysRole sysRole : roles) {
						// 查询对应的用户
						String userNames = userService.findUserNames(sysRole.getId());
						DistributeTreeTable roleTable = new DistributeTreeTable(sysRole.getId(), sysRole.getName(),
								userNames == null ? "" : userNames, project.getId());
						treeTables.add(roleTable);
					}
				}
			}
		}
		return treeTables;
	}

	@Override
	public List<Long> findUserIds(Long roleId) {
		return userService.findUserIds(roleId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonMsgUtil distribute(Long roleId, String userIds) {
		JsonMsgUtil j = new JsonMsgUtil(false);
		try {
			// 删除用户角色的关系
			roleUserService.delete(roleId);
			// 添加用户角色的关系
			if (StringUtils.isNoneBlank(userIds)) {
				List<SysRoleUser> list = new ArrayList<SysRoleUser>();
				String[] userIdsStr = userIds.trim().split(",");
				for (String userId : userIdsStr) {
					list.add(new SysRoleUser(Long.valueOf(userId), roleId));
				}
				roleUserService.batchInsert(list);
			}
			j = new JsonMsgUtil(true, "操作成功,用户重新登录即可生效", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}

}
