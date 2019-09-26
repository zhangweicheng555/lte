package com.boot.kaizen.websocket.v6.model;

/**
 * 用户列表： 这里面可以随意添加字段，例如添加头像之类的
 * 
 * @author weichengz
 * @date 2019年9月23日 上午10:13:47
 */
public class User {

	private String username;
	private String pwd;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public User(String username, String pwd) {
		super();
		this.username = username;
		this.pwd = pwd;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", pwd=" + pwd + "]";
	}

}
