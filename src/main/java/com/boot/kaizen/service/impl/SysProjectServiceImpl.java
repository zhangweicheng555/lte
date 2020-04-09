package com.boot.kaizen.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.boot.kaizen.dao.SysProjectDao;
import com.boot.kaizen.model.SysProject;
import com.boot.kaizen.service.ProjectRoleRelationService;
import com.boot.kaizen.service.SysLoginServiceService;
import com.boot.kaizen.service.SysProjectService;
import com.boot.kaizen.service.SysRolePermissionService;
import com.boot.kaizen.service.SysRoleUserService;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * 
 * @author weichengz
 * @date 2019年5月29日 下午7:10:27
 */
@Service
public class SysProjectServiceImpl implements SysProjectService {

	@Autowired
	private SysProjectDao projectDao;

	@Autowired
	private ProjectRoleRelationService prijectRoleRelationService;
	@Autowired
	private SysRoleUserService roleUserService;
	@Autowired
	private SysRolePermissionService rolePermissionService;
	@Autowired
	private SysLoginServiceService sysLoginServiceService;

	@Override
	public List<SysProject> query() {
		return projectDao.query();
	}

	@Override
	public List<SysProject> find(Map<String, Object> map) {
		return projectDao.find(map);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public JsonMsgUtil delete(String ids) {
		JsonMsgUtil j = new JsonMsgUtil(false);
		try {
			if (StringUtils.isNoneBlank(ids)) {
				String[] idsArray = ids.trim().split(",");
				Long[] array = new Long[idsArray.length];
				for (int i = 0; i < idsArray.length; i++) {
					String id = idsArray[i];
					array[i] = Long.valueOf(id.trim());
				}
				// 根据项目查询角色
				Integer deleteNum = 0;
				// 删除角色跟用户的关系表
				roleUserService.deleteByProjIds(array);
				// 删除角色跟资源的关系表
				rolePermissionService.deleteByProjIds(array);
				// 删除项目下的角色
				prijectRoleRelationService.deleteByProIds(array);
				// 删除项目
				deleteNum = projectDao.delete(array);
				j = new JsonMsgUtil(true, "删除操作成功", deleteNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return j;
	}

	@Override
	public JsonMsgUtil edit(SysProject sysProject) {
		JsonMsgUtil j = new JsonMsgUtil(false);
		try {
			//查询参数
			Map<String, Object> paramMap=new HashMap<>();
			paramMap.put("projNameModel", sysProject.getProjName());
			paramMap.put("projCodeModel", sysProject.getProjCode());
//			paramMap.put("projIntroModel", sysProject.getProjIntro());
			paramMap.put("projSimNameModel", sysProject.getProjSimName());
			List<SysProject> sysProjects = find(paramMap);
			
			// add
			if (sysProject.getId() == null) {
				//判断该项目下该项目是 是不是存在了   项目
				if (sysProjects !=null && sysProjects.size()>0) {
					throw new IllegalArgumentException("该项目下已存在该地市或者SIM地市");
				}
				sysProject.setCreateTime(new Date());
				projectDao.insert(sysProject);
			} else {// edit
				if (sysProjects !=null && sysProjects.size()>1) {
					throw new IllegalArgumentException("该项目下已存在该地市或者SIM地市");
				}
				
				if (sysProjects !=null && sysProjects.size()==1) {
					SysProject model=sysProjects.get(0);
					Long recordId = model.getId();
					if (!recordId.equals(sysProject.getId())) {
						throw new IllegalArgumentException("该项目下已存在该地市或者SIM地市");
					}
				}
				sysProject.setUpdateTime(new Date());
				projectDao.update(sysProject);
			}
			j = new JsonMsgUtil(true, "操作成功", null);
		} catch (Exception e) {
			throw new IllegalArgumentException("异常："+e.getMessage());
		}
		return j;
	}

	@Override
	public JsonMsgUtil findById(Long id) {
		JsonMsgUtil j = new JsonMsgUtil(false);
		if (id != null) {
			//SysProject sysProject = projectDao.selectById(id);
			SysProject sysProject = projectDao.selectById(id);
			if (sysProject != null) {
				j = new JsonMsgUtil(true, "操作成功", sysProject);
			}
		}
		return j;
	}

	@Override
	public List<SysProject> findWithRoleRealtion(Long projId, String projName) {
		return projectDao.findWithRoleRealtion(projId, projName);
	}

	@Override
	public List<SysProject> findList(Long projId) {
		return projectDao.findList(projId);
	}

	@Override
	public Long findRandomProj(String username) {
		return projectDao.findRandomProj(username);
	}

	@Override
	public JsonMsgUtil queryProjectsForUsername(String username) {
		Map<String, Object> objMap = new HashMap<>(5);

		JsonMsgUtil j = new JsonMsgUtil(false);
		List<Map<String, Object>> projects = projectDao.queryProjects(username);
		if (projects == null || projects.size() == 0) {
			throw new IllegalArgumentException("用户不属于任何的项目");
		}
		Long prjId = sysLoginServiceService.findProjByUserName(username);
		if (prjId == null) {
			throw new IllegalArgumentException("用户不属于任何的项目");
		}
		Map<String, Object> nowMap = new HashMap<>(projects.size());
		for (Map<String, Object> map : projects) {
			Long id = Long.valueOf(map.get("id").toString());
			if (prjId.equals(id)) {
				nowMap.put("id", prjId);
				nowMap.put("projName", String.valueOf(map.get("projName")));
				projects.remove(map);
				break;
			}
		}
		objMap.put("nowProject", nowMap);
		objMap.put("listProject", projects);
		objMap.put("username", username);
		j = new JsonMsgUtil(true, "操作成功", objMap);
		return j;
	}

	@Override
	public List<Map<String, Object>> queryProjects(String username) {
		return projectDao.queryProjects(username);
	}

	@Override
	public SysProject selectById(Long id) {
		return projectDao.selectById(id);
	}

}
