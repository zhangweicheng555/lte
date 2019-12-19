package com.boot.kaizen.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.boot.kaizen.config.SuperEntity;

/**
 * 项目-》sim项目的映射关系表
 * 
 * @author weichengz
 * @date 2019年11月28日 下午3:03:46
 */
@TableName("sys_project_mapper")
public class SysProjectMapper extends SuperEntity<SysProjectMapper> {

	private static final long serialVersionUID = 1L;

	/** 主键ID 自增 */
	private String id;
	private String projName; // 项目名字
	private String hostAp; // SIM工参地址
	private String projSimName;// sim 项目名字 目前手动填写
	private String projOperator;// sim项目 运行商 手动填写
	private String sort; // 排序
	/** 创建时间 */
	private Date createTime;

	public SysProjectMapper(String id, String projName, String hostAp, String projSimName, String projOperator,
			String sort, Date createTime) {
		super();
		this.id = id;
		this.projName = projName;
		this.hostAp = hostAp;
		this.projSimName = projSimName;
		this.projOperator = projOperator;
		this.sort = sort;
		this.createTime = createTime;
	}

	public String getHostAp() {
		return hostAp;
	}

	public void setHostAp(String hostAp) {
		this.hostAp = hostAp;
	}

	public SysProjectMapper(String id, Date createTime, String projName, String projSimName, String projOperator,
			String sort) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.projName = projName;
		this.projSimName = projSimName;
		this.projOperator = projOperator;
		this.sort = sort;
	}

	public SysProjectMapper() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getProjSimName() {
		return projSimName;
	}

	public void setProjSimName(String projSimName) {
		this.projSimName = projSimName;
	}

	public String getProjOperator() {
		return projOperator;
	}

	public void setProjOperator(String projOperator) {
		this.projOperator = projOperator;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}
