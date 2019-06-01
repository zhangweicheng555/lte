package com.boot.kaizen.model.log;
import java.io.Serializable;
import java.util.Date;

public class LoginService implements Serializable {

	private static final long serialVersionUID = 6180869216498363919L;

	private Long id;// 主键ID 自增
	private String username;
	private Long proj;
	private Date createTime;

	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getProj() {
		return proj;
	}

	public void setProj(Long proj) {
		this.proj = proj;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	

	public LoginService(String username, Long proj) {
		super();
		this.username = username;
		this.proj = proj;
	}

	public LoginService(String username, Long proj, Date createTime) {
		super();
		this.username = username;
		this.proj = proj;
		this.createTime = createTime;
	}

	public LoginService() {
	}

	
}
