package com.boot.kaizen.business.es.model;

import java.io.Serializable;
import java.util.List;

public class TaskModel2 implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer type;
	private String name;
	private Integer state;
	private String user;
	private Long createdate;
	private String updateuser;
	private Long updatedate;
	private Integer deleted;
	private Integer priority;
	private Integer range;
	private Integer sourcerange;
	private Integer projid;

	private List<String> tags; // ["测试1","测试2"] 注意这种格式的是也可以进行聚合分析的

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public Long getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}

	public Long getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Long updatedate) {
		this.updatedate = updatedate;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public Integer getSourcerange() {
		return sourcerange;
	}

	public void setSourcerange(Integer sourcerange) {
		this.sourcerange = sourcerange;
	}

	public Integer getProjid() {
		return projid;
	}

	public void setProjid(Integer projid) {
		this.projid = projid;
	}

	public TaskModel2(Integer id, Integer type, String name, Integer state, String user, Long createdate,
			String updateuser, Long updatedate, Integer deleted, Integer priority, Integer range, Integer sourcerange,
			Integer projid) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.state = state;
		this.user = user;
		this.createdate = createdate;
		this.updateuser = updateuser;
		this.updatedate = updatedate;
		this.deleted = deleted;
		this.priority = priority;
		this.range = range;
		this.sourcerange = sourcerange;
		this.projid = projid;
	}

	public TaskModel2() {
		super();
	}

}
