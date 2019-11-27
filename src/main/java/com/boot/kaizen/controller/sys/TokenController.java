package com.boot.kaizen.controller.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.boot.kaizen.service.TokenService;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * 用户控制层
 * 
 * @author weichengz
 * @date 2018年9月2日 上午10:16:10
 */
@RestController
@RequestMapping("/token")
public class TokenController {

	@Autowired
	private TokenService tokenService;

	/**
	 * 校验和更新token
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月3日 下午5:44:58
	 */
	@RequestMapping(value = "/checkAndRefreshToken", method = RequestMethod.POST)
	public JsonMsgUtil checkAndRefreshToken(@RequestParam("token") String token) {
		return tokenService.checkAndRefreshToken(token);
	}

}
