package com.boot.kaizen.websocket.listener;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 * 其实这个可以使用  ChannelInterceptor  来取代
 * 
 * 订阅拦截器   其他的拦截器 可以打开SessionSubscribeEvent  查看他的父类的实现类：
 *   例如还有：  SessionConnectedEvent、SessionUnsubscribeEvent
 * @author weichengz
 * @date 2019年9月17日 上午11:07:44
 */
@Component
public class MySubriteListener implements ApplicationListener<SessionSubscribeEvent>{

	/**
	 * 简单消息传递协议中处理消息头的基类，通过这个基类可以获得很多的信息
	 */
	@Override
	public void onApplicationEvent(SessionSubscribeEvent event) {
		Message<byte[]> message = event.getMessage();
		//将此信息包装一下
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
		//System.out.println("订阅监听器的类型："+headerAccessor.getCommand().getMessageType());
	}

}
