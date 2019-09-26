package com.boot.kaizen.websocket.Interceptor;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * 握手拦截器 使用的时候,需要将次拦截器加入到配置config里面去 执行顺序：拦截器-监听器-api
 * 
 * @author weichengz
 * @date 2019年9月17日 下午2:53:34
 */
public class MyHandInterceptor implements HandshakeInterceptor {

	/**
	 * 这个拦截器 要注意了，如果你这边最后return false，那这样的话前端会不断的请求，所以这个地方注意以下
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {

		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest httpRequest = (ServletServerHttpRequest) request;
			// HttpHeaders headers = httpRequest.getHeaders();
			System.out.println("--------------------------MyHandInterceptor拦截器--------------------------------------------");
			HttpSession session = httpRequest.getServletRequest().getSession();
			attributes.put("sessionId", session.getId()); // 注意 存储的这个值 在监听器里面是可以获取到的
			attributes.put("token", "测试------------------"); // 注意 存储的这个值 在监听器里面是可以获取到的
		}

		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {

	}

}
