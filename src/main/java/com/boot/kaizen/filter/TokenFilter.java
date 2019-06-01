package com.boot.kaizen.filter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.model.Permission;
import com.boot.kaizen.model.log.LoginLog;
import com.boot.kaizen.model.log.LoginService;
import com.boot.kaizen.service.PermissionService;
import com.boot.kaizen.service.SysLoginServiceService;
import com.boot.kaizen.service.TokenService;
import com.boot.kaizen.service.log.ISysLogService;
import com.boot.kaizen.util.HttpUtil;

/**
 * token过滤器  所有的请求都会拦截
 * 
 * @author weichengz
 * @date 2018年9月2日 上午2:11:55
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private SysLoginServiceService sysLoginServiceService;
	@Autowired
	private ISysLogService sysLogService;

	private static final Long MINUTES_10 = 10 * 60 * 1000L;
	private static final String SPRING_SECURITY_PROJ = "proj";
	private static final String TOKEN_KEY = "token";
	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String projId = obtainProjId(request);
		String username = obtainUserName(request);
		if (StringUtils.isNoneBlank(username) && StringUtils.isNoneBlank(projId)) {
			LoginService loginService = new LoginService(username, Long.valueOf(projId), new Date());
			sysLoginServiceService.insert(loginService);
		}

		String token = getToken(request);
		if (StringUtils.isNotBlank(token)) {
			LoginUser loginUser = tokenService.getLoginUser(token);
			if (loginUser != null) {
				/** 更新权限 刷新token*/
				if (StringUtils.isNoneBlank(projId) && StringUtils.isNoneBlank(username)) {
					List<Permission> listPermissions = permissionService.queryByUserIdAndProjId(username,
							Long.valueOf(projId));
					if (listPermissions == null || listPermissions.size() == 0) {
						throw new AuthenticationCredentialsNotFoundException("用户在该项目下无授权");
					}
					loginUser.setPermissions(listPermissions);
					loginUser.setProjId(Long.valueOf(projId));
					tokenService.refresh(loginUser);
					/**切换项目 记录登陆成功*/
					try {
						LoginLog loginLog=new LoginLog(loginUser.getProjId(),loginUser.getUsername(), HttpUtil.getIp(request), HttpUtil.queryRegionByIp(HttpUtil.getIp(request)), LoginLog.StatusV.SUCCESS+"", new Date(), JSONObject.toJSONString(loginUser));
						sysLogService.save(loginLog);
					} catch (Exception e) {
						/**记录登陆错误日志*/
					}
				} else {
					loginUser = checkLoginTime(loginUser);
				}

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser,
						null, loginUser.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		filterChain.doFilter(request, response);
	}

	private String obtainUserName(HttpServletRequest request) {
		return request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
	}

	private String obtainProjId(HttpServletRequest request) {
		return request.getParameter(SPRING_SECURITY_PROJ);
	}

	/**
	 * @Description: 校验时间 小于10分钟刷新
	 * @author weichengz
	 * @date 2018年9月2日 上午2:12:36
	 */
	private LoginUser checkLoginTime(LoginUser loginUser) {
		long expireTime = loginUser.getExpireTime();
		long currentTime = System.currentTimeMillis();
		if (expireTime - currentTime <= MINUTES_10) {
			String token = loginUser.getToken();
			loginUser = (LoginUser) userDetailsService.loadUserByUsername(loginUser.getUsername());
			loginUser.setToken(token);
			tokenService.refresh(loginUser);
		}
		return loginUser;
	}

	/**
	 * @Description: 根据参数或者header获取token
	 * @author weichengz
	 * @date 2018年9月2日 上午2:13:02
	 */
	public static String getToken(HttpServletRequest request) {
		String token = request.getParameter(TOKEN_KEY);
		if (StringUtils.isBlank(token)) {
			token = request.getHeader(TOKEN_KEY);
		}

		return token;
	}

}
