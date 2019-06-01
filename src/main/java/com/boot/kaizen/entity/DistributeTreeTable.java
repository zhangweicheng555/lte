package com.boot.kaizen.entity;

import java.io.Serializable;

/**
 * 权限分配实体类设计
 * 
 * @author weichengz
 * @date 2018年10月21日 上午9:19:14
 */
public class DistributeTreeTable implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String persons;
	private Long parentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersons() {
		return persons;
	}

	public void setPersons(String persons) {
		this.persons = persons;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public DistributeTreeTable(Long id, String name, String persons, Long parentId) {
		super();
		this.id = id;
		this.name = name;
		this.persons = persons;
		this.parentId = parentId;
	}

	public DistributeTreeTable() {
		super();
	}

}
