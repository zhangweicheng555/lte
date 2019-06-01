package com.boot.kaizen.entity;

import java.io.Serializable;

/**
 * ztree实体类设计
 * 
 * @author weichengz
 * @date 2018年10月4日 上午10:11:41
 */
public class ZtreeModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long pId;
	private String name;
	private Boolean open = true;
	private Boolean checked = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public ZtreeModel(Long id, Long pId, String name) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
	}

	public ZtreeModel(Long id, Long pId, String name, Boolean checked) {
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.checked = checked;
	}

	public ZtreeModel() {
		super();
	}

}
