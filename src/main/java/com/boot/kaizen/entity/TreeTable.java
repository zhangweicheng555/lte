package com.boot.kaizen.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * treeTable实体类
 * 
 * @author weichengz
 * @date 2018年10月4日 上午1:45:04
 */
public class TreeTable implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	/** 创建时间*/
	private Date createTime;
	/** 修改时间*/
	private Date updateTime;
	private Long parentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public TreeTable(Long id, String name, String description, Date createTime, Date updateTime, Long parentId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.parentId = parentId;
	}

	public TreeTable() {
	}

}
