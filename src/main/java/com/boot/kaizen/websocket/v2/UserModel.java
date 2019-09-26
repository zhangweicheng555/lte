package com.boot.kaizen.websocket.v2;

/**
 * 用户模型
 * 
 * @author weichengz
 * @date 2019年9月17日 下午3:34:57
 */
public class UserModel {

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

	public UserModel(String username, String pwd) {
		super();
		this.username = username;
		this.pwd = pwd;
	}

	public UserModel() {
		super();
	}

}
