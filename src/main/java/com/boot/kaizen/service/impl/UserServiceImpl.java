package com.boot.kaizen.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.boot.kaizen.dao.SysUserDao;
import com.boot.kaizen.enump.Constant;
import com.boot.kaizen.model.SysUser;
import com.boot.kaizen.model.SysUser.Status;
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

	@Autowired
	private SysUserDao userDao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private SysRoleUserService roleUserService;

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
	public JsonMsgUtil updateUser(SysUser sysUser) {
		JsonMsgUtil j = new JsonMsgUtil(false);
		try {
			sysUser.setUpdateTime(new Date());
			userDao.update(sysUser);
			j = new JsonMsgUtil(true, "操作成功", "");
		} catch (Exception e) {
			j.setMsg("修改失败");
			e.printStackTrace();
		}
		return j;
	}

	@Override
	public List<SysUser> find(Map<String, Object> map) {
		return userDao.find(map);
	}

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
					if (userid.equals(userId)) {
						array[i] = userid;
					}
				}
			}
			// 删除用户角色关系
			roleUserService.deleteBatch(array);
			userDao.deleteBatch(array, projId);
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
			j.setMsg("操作失败");
			e.printStackTrace();
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

}
