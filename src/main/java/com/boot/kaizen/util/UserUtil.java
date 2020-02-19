package com.boot.kaizen.util;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.model.Permission;

/**
 * 用户权限相关的操作
 * 
 * @author weichengz
 * @date 2020年2月15日 上午11:57:46
 */
public class UserUtil {

	/**
	 * 
	 * @Description: 获取登陆用户的权限
	 * @author weichengz
	 * @date 2020年2月15日 上午11:58:49
	 */
	public static LoginUser getLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			if (authentication instanceof AnonymousAuthenticationToken) {
				return null;
			}

			if (authentication instanceof UsernamePasswordAuthenticationToken) {
				return (LoginUser) authentication.getPrincipal();
			}
		}

		return null;
	}

	/**
	 * 
	 * @Description: 将权限信息放置在集合里面
	 * @author weichengz
	 * @date 2020年2月15日 上午11:58:27
	 */
	public static Set<String> ownsPermission() {
		List<Permission> permissions = UserUtil.getLoginUser().getPermissions();
		if (CollectionUtils.isEmpty(permissions)) {
			return Collections.emptySet();
		}

		return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
				.map(Permission::getPermission).collect(Collectors.toSet());
	}

	/**
	 * 判断指定的权限是不是存在
	* @Description: TODO
	* @author weichengz
	* @date 2020年2月15日 下午12:21:02
	 */
	public static boolean hasPermission(String permissionStr) {
		if (org.apache.commons.lang3.StringUtils.isNotBlank(permissionStr)) {
			Set<String> ownsPermissions = ownsPermission();
			return ownsPermissions.contains(permissionStr);
		}
		return false;
	}
}
