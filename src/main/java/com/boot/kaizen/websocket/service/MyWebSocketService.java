package com.boot.kaizen.websocket.service;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.boot.kaizen.websocket.InMessage;
import com.boot.kaizen.websocket.OutMessage;
import com.boot.kaizen.websocket.v6.model.User;

/**
 * webSocket自定义服务类
 * 
 * @author weichengz
 * @date 2019年9月17日 上午10:44:41
 */
@Service
public class MyWebSocketService {

	/**
	 * 用 template 代替 sendTo的用法 因为sendTo 只能单发 不能多发，topic链接不能多方指定
	 */
	@Autowired
	private SimpMessagingTemplate template;

	public void sendMessage(InMessage message, String backFrontUrl) {
		template.convertAndSend(backFrontUrl, message);
	}

	/**
	 * 实现单聊的一种方式：
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年9月17日 上午11:29:04
	 */
	public void sendMessage(InMessage message) {
		// 这个地方通过to动态的设置了这个消息给谁 然后前端只需要订阅to（自己）就可以了
		template.convertAndSend("/topic/ass/" + message.getTo(), message);
	}

	public void userOnline(Map<String, User> onlineUser, String toTopic) {
		String userStr = "";
		if (onlineUser != null && !onlineUser.isEmpty()) {
			Set<String> keySet = onlineUser.keySet();
			for (String key : keySet) {
				if (StringUtils.isBlank(userStr)) {
					userStr = onlineUser.get(key).getUsername();
				} else {
					userStr = userStr + "||" + onlineUser.get(key).getUsername();
				}
			}
		}
		// 这里是将在线用户拼接成 一个字符串 然后输出
		template.convertAndSend(toTopic, userStr);
	}

	/**
	 * 群聊
	* @Description: TODO
	* @author weichengz
	* @date 2019年9月23日 下午4:02:07
	 */
	public void allChat(InMessage message, String toTopic) {
		template.convertAndSend(toTopic, new OutMessage(message.getFrom(), message.getContent(), new Date()));
	}

}
