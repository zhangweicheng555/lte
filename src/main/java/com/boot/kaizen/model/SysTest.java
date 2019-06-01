package com.boot.kaizen.model;
/**
 * 
 * @author weichengz
 * @date 2019年5月30日 上午8:53:39
 */
public class SysTest extends Entity {

	private static final long serialVersionUID = -6525908145032868837L;

	private String username;
	private String password;
	private String nickname;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public SysTest(String username, String password, String nickname) {
		super();
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}

	public SysTest() {
		super();
	}

}
