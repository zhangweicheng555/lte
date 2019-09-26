package com.boot.kaizen.websocket.v1;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.boot.kaizen.websocket.InMessage;
import com.boot.kaizen.websocket.OutMessage;

@Controller
public class GameInfoController {

	/**
	 * 将接收的信息 不修改的在返回出去
	 * 这个地方注意了：如果我们只是简单的进行订阅，后端不需要接收数据，那么我们只需要订阅就可以，也就是说
	 * @MessageMapping 这个注解是不需要的
	 * 
	 * @Description: message 接收到的信息
	 * @author weichengz
	 * @date 2019年9月16日 下午5:39:24
	 */
	@MessageMapping("/v1/chat") // 接收的链接 通过路由进来的链接  注意这个连接使用的时候 注意请求的前缀
	@SendTo("/topic/game_chat") // 发送的链接 执行玩之后在发送出去的链接 网页客户端就需要订阅这个客户端
	public OutMessage gameInfo(InMessage message) {
		return new OutMessage(message.getFrom(), message.getContent(), message.getTime());
	}

}
