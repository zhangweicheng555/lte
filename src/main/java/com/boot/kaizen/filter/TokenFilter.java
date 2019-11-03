package com.boot.kaizen.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.boot.kaizen.client.KaizenClient;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * token过滤器 所有的请求都会拦截
 * 
 * @author weichengz
 * @date 2018年9月2日 上午2:11:55
 */
@Component
public class TokenFilter extends OncePerRequestFilter {
	private static final String TOKEN_KEY = "token";

	//屏蔽的资源
	private static final List<String> EXCLUDE_URI=Arrays.asList("png","jpg","html","css","js","txt");
	@Autowired
	private KaizenClient kaizenClient;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String UtiType = requestURI.substring(requestURI.lastIndexOf(".")+1);
		if (!EXCLUDE_URI.contains(UtiType)) {
			String token = getToken(request);
			if (StringUtils.isNotBlank(token)) {
				//查询token对应的用户是不是存在  存在用户 在校验更新时间
				try {
					JsonMsgUtil jsonMsgUtil = kaizenClient.checkAndRefreshToken(token);
					 if (jsonMsgUtil !=null && jsonMsgUtil.isSuccess()) {
						System.out.println("更新成功");
					}else {
						response.sendRedirect("/login.html");
					}
				} catch (Exception e) {
					System.out.println("-----------异常了--------------------");
					throw new IllegalArgumentException("访问异常："+e.getMessage());
				}
			} else {
				response.sendRedirect("/login.html");
			}
		}
		filterChain.doFilter(request, response);
	}

	
	
	/**
	 * 
	 * @Description: 根据参数或者header获取token
	 * @author weichengz
	 * @date 2019年11月3日 下午4:32:53
	 */
	public static String getToken(HttpServletRequest request) {
		String token = request.getParameter(TOKEN_KEY);
		if (StringUtils.isBlank(token)) {
			token = request.getHeader(TOKEN_KEY);
		}

		return token;
	}

}
