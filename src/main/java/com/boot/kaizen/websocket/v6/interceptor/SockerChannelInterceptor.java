package com.boot.kaizen.websocket.v6.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import com.boot.kaizen.websocket.v6.controller.UserChatController;

/**
 * 自定义拦截器 频道拦截器 类似管道，可以获取消息的一些meta元数据
 * 
 * @author weichengz
 * @date 2019年9月23日 上午11:11:49
 */
public class SockerChannelInterceptor implements ChannelInterceptor {

	/**
	 * 在消息被实际发送到频道前调用 先执行1
	 */
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		System.out.println("preSend");
		return ChannelInterceptor.super.preSend(message, channel);
	}

	/**
	 * 再发送结束之后调用，不管是否有异常发生，一般用于资源清理 执行3
	 */
	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
		System.out.println("afterSendCompletion");
		ChannelInterceptor.super.afterSendCompletion(message, channel, sent, ex);
	}

	/**
	 * 发送消息调用之后，立马会调用这个方法 执行2
	 */
	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);// 消息头访问器

		Object object = headerAccessor.getSessionAttributes().get("token");
		System.out.println(object);
		
		// 注意这里面有时候有些心跳检测之类的 会为空  避免非stom消息类型
		StompCommand command = headerAccessor.getCommand();
		if (command != null) {
			// 获取消息类型
			SimpMessageType messageType = command.getMessageType();
			// 获取sessionID
			String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
			System.out.println("SockerChannelInterceptor->获得的sessionId:" + sessionId);
			System.out.println("SockerChannelInterceptor->messageType:"+messageType);
			
			// 获取消息体 通过这个指令我们就知道是链接断开连接还是发送数据 其他的case 点开选择即可
			switch (command) {
			case CONNECT:
				connect(sessionId);
				break;
			case DISCONNECT:// 页面刷新或者断开链接之后都会触发
				disConnect(sessionId);
				break;
			case SUBSCRIBE:
				System.out.println("订阅r->messageType:——————————————————————————————————————————————————————————————");
				break;
			case UNSUBSCRIBE:
				System.out.println("取消订阅r->messageType:——————————————————————————————————————————————————————————————");
				break;
			default:
				break;
			}
		}
	}

	private void connect(String sessionId) {
		System.out.println("链接之后的sessionId:" + sessionId);
	}

	private void disConnect(String sessionId) {
		System.out.println("断开链接之后的sessionId:" + sessionId);
		// 用户下线功能
		UserChatController.onlineUser.remove(sessionId);
	}

}
