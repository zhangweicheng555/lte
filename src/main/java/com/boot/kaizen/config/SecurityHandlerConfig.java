package com.boot.kaizen.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.Token;
import com.boot.kaizen.filter.TokenFilter;
import com.boot.kaizen.model.log.LoginLog;
import com.boot.kaizen.service.TokenService;
import com.boot.kaizen.service.log.ISysLogService;
import com.boot.kaizen.util.HttpUtil;
import com.boot.kaizen.util.JsonMsgUtil;
import com.boot.kaizen.util.JsonTokenUtil;
import com.boot.kaizen.util.ResponseUtil;

/**
 * security 处理器
 * 
 * @author weichengz
 * @date 2018年9月2日 上午2:06:17
 */
@Configuration
public class SecurityHandlerConfig {

	@Autowired
	private TokenService tokenService;
	@Autowired
	private ISysLogService sysLogService;
	
	/**
	 * @Description: 登陆成功处理器
	 * @author weichengz
	 * @date 2018年9月2日 上午2:06:45
	 */
	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new AuthenticationSuccessHandler() {

			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				LoginUser loginUser = (LoginUser) authentication.getPrincipal();

				Token token = tokenService.saveToken(loginUser);
				JsonTokenUtil j = new JsonTokenUtil(token.getToken(), token.getLoginTime(), 1, "登陆成功", null);
				j.setDataSource(token);
				try {//切换项目 记录登陆成功
					LoginLog loginLog=new LoginLog(loginUser.getProjId(),loginUser.getUsername(), HttpUtil.getIp(request), HttpUtil.queryRegionByIp(HttpUtil.getIp(request)), LoginLog.StatusV.SUCCESS+"", new Date(), JSONObject.toJSONString(loginUser));
					sysLogService.save(loginLog);
				} catch (Exception e) {//记录登陆错误日志
					
				}
				//JSONObject.toJSONString(j);
				ResponseUtil.responseJson(response, HttpStatus.OK.value(), j);
			}
		};
	}
	
	
	/**
	 * @Description: 登陆失败处理器
	 * @author weichengz
	 * @date 2018年9月2日 上午2:07:55
	 */
	@Bean
	public AuthenticationFailureHandler loginFailureHandler() {
		return new AuthenticationFailureHandler() {

			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				String msg = null;
				String username=request.getParameter("username");
				if (exception instanceof BadCredentialsException) {
					msg = "密码错误";
				} else {
					msg = exception.getMessage();
				}
				
				try {//记录登陆失败
					String ip=HttpUtil.getIp(request);
					LoginLog loginLog=new LoginLog(-1L,username, ip, HttpUtil.queryRegionByIp(ip), LoginLog.StatusV.FAIL+"", new Date(), "用户【"+username+"】登陆失败，"+exception.getMessage());
					sysLogService.save(loginLog);
				} catch (Exception e) {
					
				}
				
				JsonMsgUtil j = new JsonMsgUtil(HttpStatus.UNAUTHORIZED.value(), msg, 0, msg);
				ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), j);
			}
		};

	}

	/**
	 * @Description: 未登录处理
	 * @author weichengz
	 * @date 2018年9月2日 上午2:08:09
	 */
	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {

			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException authException) throws IOException, ServletException {
				JsonMsgUtil j = new JsonMsgUtil(HttpStatus.UNAUTHORIZED.value(), "请先登录",
						HttpStatus.UNAUTHORIZED.value(), "请先登录");
				ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), j);
			}
		};
	}

	/**
	 * @Description: 退出登陆处理器
	 * @author weichengz
	 * @date 2018年9月2日 上午2:08:48
	 */
	@Bean
	public LogoutSuccessHandler logoutSussHandler() {
		return new LogoutSuccessHandler() {

			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				JsonMsgUtil j = new JsonMsgUtil(HttpStatus.OK.value(), "退出成功", HttpStatus.OK.value(), "退出成功");
				String token = TokenFilter.getToken(request);
				tokenService.deleteToken(token);
				ResponseUtil.responseJson(response, HttpStatus.OK.value(), j);
			}
		};

	}

}
