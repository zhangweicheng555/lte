package com.boot.kaizen.websocket.v1;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import com.boot.kaizen.websocket.InMessage;
import com.boot.kaizen.websocket.service.MyWebSocketService;
import com.boot.kaizen.websocket.v6.controller.UserChatController;

@Controller
public class GameInfoTwoController {

	@Autowired
	private MyWebSocketService myWebSocketService;
	
	/**
	 * 将接收的信息 不修改的在返回出去
	 * 
	 * @Description: message 接收到的信息
	 * @author weichengz
	 * @date 2019年9月16日 下午5:39:24
	 */
	@MessageMapping("/v2/chat1") // 接收的链接 通过路由进来的链接  注意这个连接使用的时候 注意请求的前缀
	public void gameInfo1(InMessage message) {
		myWebSocketService.sendMessage(message, "/topic/game_chat");
	}
	
	/**
	 * 注意schedule的方法上是不能存在参数的 这个要注意以下
	* @Description: TODO
	* @author weichengz
	* @date 2019年9月17日 上午11:58:44
	 */
	@MessageMapping("/v2/chat") // 接收的链接 通过路由进来的链接  注意这个连接使用的时候 注意请求的前缀
	//@Scheduled(fixedRate=3000)//3秒执行一次
	public void gameInfo() {
		InMessage message=new InMessage("zhangsan", "lisi", "我是参数内容区域", new Date());
		myWebSocketService.sendMessage(message, "/topic/game_chat");
	}

	
	
	/**
	 * 在线人员
	 */
	@Scheduled(fixedRate=3000)//3秒执行一次
	public void userOnline() {
		myWebSocketService.userOnline(UserChatController.onlineUser, "/topic/userOnline");
	}
	
	
}
