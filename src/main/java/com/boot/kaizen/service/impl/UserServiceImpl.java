package com.boot.kaizen.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boot.kaizen.dao.SysRoleDao;
import com.boot.kaizen.dao.SysUserDao;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.enump.Constant;
import com.boot.kaizen.model.SysRole;
import com.boot.kaizen.model.SysRoleUser;
import com.boot.kaizen.model.SysUser;
import com.boot.kaizen.model.SysUser.Status;
import com.boot.kaizen.service.SysLoginServiceService;
import com.boot.kaizen.service.SysRoleUserService;
import com.boot.kaizen.service.UserService;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * 
 * @author weichengz
 * @date 2019年5月29日 下午7:14:22
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	@Autowired
	private SysUserDao userDao;
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private SysRoleUserService roleUserService;
	@Autowired
	private SysLoginServiceService sysLoginServiceService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SysUser saveUser(SysUser sysUser) {
		sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
		sysUser.setStatus(Status.VALID);
		sysUser.setCreateTime(new Date());
		// userDao.save(sysUser);
		userDao.insertSelective(sysUser);
		return sysUser;
	}

	@Override
	public SysUser getUser(String username) {
		return userDao.getUser(username);
	}

	@Override
	public void changePassword(Long id, String oldPassword, String newPassword) {
		SysUser u = userDao.getById(id);
		if (u == null) {
			throw new IllegalArgumentException("用户不存在");
		}
		if (!passwordEncoder.matches(oldPassword, u.getPassword())) {
			throw new IllegalArgumentException("旧密码错误");
		}
		userDao.changePassword(u.getId(), passwordEncoder.encode(newPassword));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JsonMsgUtil updateUser(SysUser sysUser, Long projId) {
		JsonMsgUtil j = new JsonMsgUtil(false);
		try {
			// 修改信息
			sysUser.setUpdateTime(new Date());
			userDao.update(sysUser);

			// 删除该项目下该用户的所有角色
			List<SysRole> roles = sysRoleDao.queryByProIdAndUserId(projId, sysUser.getId());
			if (roles != null && roles.size() > 0) {
				Long[] userIds = new Long[] { sysUser.getId() };
				roleUserService.deleteBatch(userIds);
			}

			// 查询用户 角色信息 没有就添加
			String roleIdsStr = sysUser.getRoleIdsStr();
			if (StringUtils.isNotBlank(roleIdsStr)) {
				// 重新添加改用户的角色
				List<SysRoleUser> list = new ArrayList<SysRoleUser>();
				String[] split = roleIdsStr.split(",");
				for (String roleId : split) {
					list.add(new SysRoleUser(sysUser.getId(), Long.valueOf(roleId)));
				}
				if (list != null && list.size() > 0) {
					roleUserService.batchInsert(list);
				}
			}

			j = new JsonMsgUtil(true, "操作成功", "");
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return j;
	}

	@Override
	public List<SysUser> find(Map<String, Object> map) {
		return userDao.find(map);
	}

	@Transactional
	@Override
	public JsonMsgUtil delete(String ids, Long projId, Long userId) {
		JsonMsgUtil j = new JsonMsgUtil(false);

		if (StringUtils.isNoneBlank(ids)) {
			String[] idsArray = ids.trim().split(",");
			Long[] array = new Long[idsArray.length];
			for (int i = 0; i < idsArray.length; i++) {
				String id = idsArray[i];
				// 排除wuzhihua mocun用户
				if (!id.equals(Constant.USER_ID.toString()) && !id.equals(Constant.USER_ID_MASTER.toString())) {
					Long userid = Long.valueOf(id.trim());
					if (!userid.equals(userId)) {
						array[i] = userid;
						// 删出用户登陆日志
						SysUser sysUser = selectById(userid);
						if (sysUser != null && StringUtils.isNotBlank(sysUser.getUsername())) {
							sysLoginServiceService.deleteByUserName(sysUser.getUsername());
						}
					}
				}
			}
			// 删除用户角色关系
			roleUserService.deleteBatch(array);
			// userDao.deleteBatch(array, projId);
			userDao.deleteBatch(array, null);
			j = new JsonMsgUtil(true, "删除操作成功", "");
		}

		return j;
	}

	@Override
	public JsonMsgUtil findById(Long id) {
		JsonMsgUtil j = new JsonMsgUtil(false);
		try {
			j = new JsonMsgUtil(true, "操作成功", userDao.getById(id));
		} catch (Exception e) {
			j.setMessage("操作失败");
			// e.printStackTrace();
		}
		return j;
	}

	@Override
	public String findUserNames(Long roleId) {
		return userDao.findUserNames(roleId);
	}

	@Override
	public List<Long> findUserIds(Long roleId) {
		return userDao.findUserIds(roleId);
	}

	@Override
	public SysUser queryUser(Long projId, String username) {
		return userDao.queryUser(projId, username);
	}

	@Override
	public List<Map<String, Object>> queryUserByProjId(Long projId) {
		return userDao.queryUserByProjId(projId);
	}

	@Override
	public List<Map<String, Object>> queryUserByRoleId(Long roleId) {
		return userDao.queryUserByRoleId(roleId);
	}

	@Override
	public void insertSelective(SysUser sysUser) {
		userDao.insertSelective(sysUser);
	}

	@Override
	public SysUser selectById(Long id) {
		return userDao.getById(id);
	}

	@Transactional
	@Override
	public JsonMsgUtil saveUserRole(SysUser sysUser, LoginUser user) {
		SysUser model = saveUser(sysUser);

		String roleIdsStr = sysUser.getRoleIdsStr();
		// 系统项目角色 可不勾选 其余的项目必须要勾选
		if (!Constant.SYSTEM_ID_PROJECT.equals(user.getProjId())) {
			if (StringUtils.isBlank(roleIdsStr)) {
				throw new IllegalArgumentException("用户角色不能为空");
			}
		}
		if (StringUtils.isNotBlank(roleIdsStr)) {
			List<SysRoleUser> list = new ArrayList<SysRoleUser>();
			String[] roleIdsArray = roleIdsStr.trim().split(",");
			for (String idRole : roleIdsArray) {
				list.add(new SysRoleUser(model.getId(), Long.valueOf(idRole)));
			}
			if (list != null && list.size() > 0) {
				roleUserService.batchInsert(list);
			}
		}
		return new JsonMsgUtil(true, "添加成功", "");
	}

}
