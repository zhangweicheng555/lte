package com.boot.kaizen.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author weichengz
 * @date 2019年5月30日 上午8:54:16
 */
public class Permission implements Serializable {

	private static final long serialVersionUID = 6180869216498363919L;

	/** 主键ID 自增 */
	private Long id;
	private Long parentId;
	private String name;
	private String css;
	private String href;
	private Integer type;
	private String permission;
	private Integer sort;

	private List<Permission> child;

	public Long getParentId() {
		return parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<Permission> getChild() {
		return child;
	}

	public void setChild(List<Permission> child) {
		this.child = child;
	}
}
