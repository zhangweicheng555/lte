package com.boot.kaizen.websocket.v6.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.boot.kaizen.websocket.InMessage;
import com.boot.kaizen.websocket.service.MyWebSocketService;
import com.boot.kaizen.websocket.v6.model.User;

@Controller
public class UserChatController {

	@Autowired
	private MyWebSocketService webSocketService;

	/**
	 * 模拟数据库用户
	 */
	public static Map<String, String> userMap = new HashMap<String, String>();
	static {
		userMap.put("jack", "123");
		userMap.put("marry", "456");
		userMap.put("tom", "789");
		userMap.put("tim", "000");
		userMap.put("xiaodi", "666");
	}

	/**
	 * 用户登陆之后 存储用户: 注意这里存储的信息为：sessionId：用户信息 ； 这样我们就可以通过浏览器的sessionId获取到唯一的登陆用户
	 */
	public static Map<String, User> onlineUser = new HashMap<>();
	static {
		// 设置默认的用户在线
		onlineUser.put("123", new User("admin", "888"));
	}

	/**
	 * 在这里模拟用登陆
	 */
	@RequestMapping(value = "/zwc/userLogin")
	public String userLogin(@RequestParam(value = "username") String username, @RequestParam(value = "pwd") String pwd,
			HttpSession session) {
		// 对用户的信息进行校验并跳转到成功或者失败的页面
		String pwdDb = userMap.get(username);
		if (StringUtils.isNoneBlank(pwdDb)) {
			if (pwdDb.equals(pwd)) {
				// 将sessionId 跟用户的信息存入
				onlineUser.put(session.getId(), new User(username, pwd));
				return "redirect:/pages/v6/chat.html";
			}
		}
		return "redirect:/pages/v6/error.html";
	}

	/**
	 * 群聊的功能 headers的信息模块 {"token":"zhangweicheng","name":"zhangsan","pwd":"123456"}
	 * 注意：如果我们订阅信息的时候，在send里面是可以发送headers的信息的，header信息的接收就是采用这个方式
	 */
	@MessageMapping("/topic/chatModel") // 接收的链接 通过路由进来的链接 注意这个连接使用的时候 注意请求的前缀
	public void allChat(InMessage message, @Header(name = "token") String token, @Header(name = "name") String name,
			@Headers Map<String, Object> map) {
		System.out.println(name);
		System.out.println(token);
		webSocketService.allChat(message, "/topic/allChatModel");
	}
}
