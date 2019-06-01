package com.boot.kaizen.model;

/**
 * 角色实体类设计
 * 
 * @author weichengz
 * @date 2018年9月8日 上午10:43:02
 */
public class SysRole extends BaseEntity<Long> {

	private static final long serialVersionUID = -3802292814767103648L;

	private String name;

	private String description;

	private String sort;

	private Long projId;

	@Override
	public Long getProjId() {
		return projId;
	}
	@Override
	public void setProjId(Long projId) {
		this.projId = projId;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}
