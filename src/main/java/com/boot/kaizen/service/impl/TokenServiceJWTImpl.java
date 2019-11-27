package com.boot.kaizen.service.impl;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.Token;
import com.boot.kaizen.service.TokenService;
import com.boot.kaizen.util.JsonMsgUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * redis方式存储token wjt格式的token
 * 
 * @author weichengz
 * @date 2019年5月21日 上午9:51:02
 */
// @Deprecated
@Primary
@Service
public class TokenServiceJWTImpl implements TokenService {

	/**
	 * token过期秒数
	 */
	@Value("${token.expire.seconds}")
	private Integer expireSeconds;
	@Autowired
	private RedisTemplate<String, LoginUser> redisTemplate;
	/**
	 * 私钥
	 */
	@Value("${token.jwtSecret}")
	private String jwtSecret;

	private static Key KEY = null;
	private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

	@Override
	public Token saveToken(LoginUser loginUser) {
		loginUser.setToken(UUID.randomUUID().toString());
		cacheLoginUser(loginUser);
		String jwtToken = createJWTToken(loginUser);
		return new Token(jwtToken, loginUser.getLoginTime());
	}

	/**
	 * 生成jwt
	 * 
	 * @param loginUser
	 * @return
	 */
	private String createJWTToken(LoginUser loginUser) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(LOGIN_USER_KEY, loginUser.getToken());// 放入一个随机字符串，通过该串可找到登陆用户
		String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
				.compact();
		return jwtToken;
	}

	private void cacheLoginUser(LoginUser loginUser) {
		loginUser.setLoginTime(System.currentTimeMillis());
		loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);
		redisTemplate.boundValueOps(getTokenKey(loginUser.getToken())).set(loginUser, expireSeconds, TimeUnit.SECONDS);
	}

	/**
	 * 更新缓存的用户信息
	 */
	@Override
	public void refresh(LoginUser loginUser) {
		cacheLoginUser(loginUser);
	}

	@Override
	public LoginUser getLoginUser(String jwtToken) {
		String uuid = getUUIDFromJWT(jwtToken);
		if (uuid != null) {
			return redisTemplate.boundValueOps(getTokenKey(uuid)).get();
		}

		return null;
	}

	@Override
	public boolean deleteToken(String jwtToken) {
		String uuid = getUUIDFromJWT(jwtToken);
		if (uuid != null) {
			String key = getTokenKey(uuid);
			LoginUser loginUser = redisTemplate.opsForValue().get(key);
			if (loginUser != null) {
				redisTemplate.delete(key);
				return true;
			}
		}

		return false;
	}

	private String getTokenKey(String uuid) {
		return "tokens:" + uuid;
	}

	private Key getKeyInstance() {
		if (KEY == null) {
			synchronized (TokenServiceJWTImpl.class) {
				if (KEY == null) {// 双重锁
					byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
					KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
				}
			}
		}

		return KEY;
	}

	private String getUUIDFromJWT(String jwtToken) {
		if ("null".equals(jwtToken) || StringUtils.isBlank(jwtToken)) {
			return null;
		}

		try {
			Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken)
					.getBody();
			return MapUtils.getString(jwtClaims, LOGIN_USER_KEY);
		} catch (ExpiredJwtException e) {
		} catch (Exception e) {
		}

		return null;
	}

	@Override
	public JsonMsgUtil checkAndRefreshToken(String token) {
		try {
			if (StringUtils.isBlank(token)) {
				return new JsonMsgUtil(400, "token凭证不存在");
			}
			LoginUser loginUser = getLoginUser(token);
			if (loginUser == null) {
				return new JsonMsgUtil(400, "用户过期或者不存在");
			}
			checkLoginTime(loginUser);
			return new JsonMsgUtil(true, "更新token成功", "");
		} catch (Exception e) {
			return new JsonMsgUtil(400, "异常了：" + e.getMessage());
		}
	}

	private static final Long MINUTES_10 = 10 * 60 * 1000L;

	/**
	 * 
	 * @Description: 检查校验时间 小于10分钟的时候 我们在去更新redis
	 * @author weichengz
	 * @date 2019年11月12日 下午7:55:27
	 */
	private LoginUser checkLoginTime(LoginUser loginUser) {
		long expireTime = loginUser.getExpireTime();
		long currentTime = System.currentTimeMillis();
		if (expireTime - currentTime <= MINUTES_10) {
			refresh(loginUser);
		}
		return loginUser;
	}
}
