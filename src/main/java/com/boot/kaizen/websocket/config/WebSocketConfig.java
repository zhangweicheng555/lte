package com.boot.kaizen.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.boot.kaizen.websocket.Interceptor.MyHandInterceptor;
import com.boot.kaizen.websocket.v6.interceptor.SockerChannelInterceptor;

/**
 * @EnableWebSocketMessageBroker : 表明这个类是一个websocket代理类
 * @author weichengz
 * @date 2019年9月25日 下午4:54:15
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	/**
	 * 
	 * addInterceptors : 优先级最高的 注册端点， 发布订阅消息的时候需要先链接上这个端点 这个地方 如果又springsecurity的话
	 * 那么这个要开放 一旦前端链接之后
	 * 就会出现一个websocket协议：ws://localhost:8081/endpoint-websocket/840/t0o3acxr/websocket
	 * 
	 * setAllowedOrigins ： 允许其他域进行链接 withSockJS ：表示开始SockJS支持
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {// 这个MyHandInterceptor 拦截器 是优先级最高的
		registry.addEndpoint("/endpoint-websocket").addInterceptors(new MyHandInterceptor()).setAllowedOrigins("*")
				.withSockJS();
	}

	/**
	 * 配置一下消息代理（类似于中介，其实这个就好比类别），每个类别或者中介对应一批前端的链接
	 * 
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 服务端推送到客户端的请求前缀
		registry.enableSimpleBroker("/topic", "/chat");
		// 客户端发送数据到服务端的一个请求前缀
		registry.setApplicationDestinationPrefixes("/app1");
	}

	/**
	 * 拦截器的配置 这个拦截器 是次之
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new SockerChannelInterceptor());
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		registration.interceptors(new SockerChannelInterceptor());
	}

}
