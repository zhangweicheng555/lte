package com.boot.kaizen.client.conf;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 微服务客户端超时配置
 * 
 * @author weichengz
 * @date 2019年10月31日 上午11:30:47
 */
@Configuration
public class ClientConf implements RequestInterceptor {

	private static final String TOKEN_CHECK = "token";

	@Bean
	Request.Options feignOptions() {
		return new Request.Options(/** connectTimeoutMillis **/
				60 * 60 * 1000, /** readTimeoutMillis 毫秒 **/
				60 * 60 * 1000);
	}

	/**
	 * 所有请求打上token请求头
	 */
	@Override
	public void apply(RequestTemplate template) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 添加token
		template.header(TOKEN_CHECK, getToken(request, TOKEN_CHECK));
	}

	/**
	 * 从请求中获取凭证token
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月2日 下午9:03:23
	 */
	public static String getToken(HttpServletRequest request, String tokenName) {
		String token = request.getParameter(tokenName);
		if (StringUtils.isBlank(token)) {
			token = request.getHeader(tokenName);
		}
		return token;
	}
}
