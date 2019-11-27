package com.boot.kaizen.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.boot.kaizen.entity.LoginUser;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:37:51
 */
public class UserUtil {

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


}
