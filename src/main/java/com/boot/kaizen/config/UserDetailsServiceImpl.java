package com.boot.kaizen.config;

import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.model.Permission;
import com.boot.kaizen.model.SysUser;
import com.boot.kaizen.model.SysUser.Status;
import com.boot.kaizen.model.log.LoginService;
import com.boot.kaizen.service.PermissionService;
import com.boot.kaizen.service.SysLoginServiceService;
import com.boot.kaizen.service.SysProjectService;
import com.boot.kaizen.service.UserService;

/**
 * 资源权限获取
 * 
 * @author weichengz
 * @date 2018年9月2日 上午2:16:11
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;
	@Autowired
	private SysProjectService projectService;
	@Autowired
	private SysLoginServiceService sysLoginServiceService;
	@Autowired
	private PermissionService permissionService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = userService.getUser(username);
		if (sysUser == null) {
			throw new AuthenticationCredentialsNotFoundException("用户名不存在");
		} else if (sysUser.getStatus() == Status.LOCKED) {
			throw new LockedException("用户被锁定,请联系管理员");
		} else if (sysUser.getStatus() == Status.DISABLED) {
			throw new DisabledException("用户已作废");
		}

		LoginUser loginUser = new LoginUser();
		BeanUtils.copyProperties(sysUser, loginUser);
		
		Long projId=sysLoginServiceService.findProjByUserName(username);
		if (projId == null) {
			projId=projectService.findRandomProj(username);
			if (projId == null) {
				throw new AuthenticationCredentialsNotFoundException("用户不属于任何项目");
			}else {
				LoginService loginService=new LoginService(username, projId, new Date());
				sysLoginServiceService.insert(loginService);
			}
		}
		// 根据用户的名字、项目id 查询所有的资源
		List<Permission> permissions = permissionService.queryByUserIdAndProjId(username,projId);
		loginUser.setPermissions(permissions);
		loginUser.setProjId(projId);
		return loginUser;
	}

}
