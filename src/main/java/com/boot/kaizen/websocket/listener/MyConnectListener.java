package com.boot.kaizen.websocket.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * 
 * 其实这个可以使用  ChannelInterceptor  来取代
 * 链接拦截器  这些事在MyHandInterceptor、ChannelInterceptor之后
 * 
 * @author weichengz
 * @date 2019年9月17日 上午11:07:44
 */
@Component
public class MyConnectListener implements ApplicationListener<SessionConnectEvent> {

	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
		Message<byte[]> message = event.getMessage();
		// 将此信息包装一下
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
		//System.out.println("监听的类型：" + headerAccessor.getCommand().getMessageType());
		// 前面拦截器里面配置了参数
		
		//Object object = headerAccessor.getSessionAttributes().get("sessionId");
		//Object token = headerAccessor.getSessionAttributes().get("token");
	}

}
