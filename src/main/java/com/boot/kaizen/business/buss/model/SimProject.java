package com.boot.kaizen.business.buss.model;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotations.TableName;
import com.boot.kaizen.config.SuperEntity;

/**
 * SIM模块的项目管理
 * @author weichengz
 * @date 2020年6月12日 上午10:44:38
 */
@TableName("buss_sim_project")
public class SimProject extends SuperEntity<SimProject> {

	private static final long serialVersionUID = 1L;

	private String project_name;
	private String net_id;
	private String province_name;
	private String city_name;
	private String operators;
	private String project_level;
	private String ihandle_url;
	
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getNet_id() {
		return net_id;
	}
	public void setNet_id(String net_id) {
		this.net_id = net_id;
	}
	public String getProvince_name() {
		return province_name;
	}
	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getOperators() {
		return operators;
	}
	public void setOperators(String operators) {
		this.operators = operators;
	}
	public String getProject_level() {
		return project_level;
	}
	public void setProject_level(String project_level) {
		this.project_level = project_level;
	}
	public String getIhandle_url() {
		return ihandle_url;
	}
	public void setIhandle_url(String ihandle_url) {
		this.ihandle_url = ihandle_url;
	}
	public SimProject(String project_name, String net_id, String province_name, String city_name, String operators,
			String project_level, String ihandle_url) {
		super();
		this.project_name = project_name;
		this.net_id = net_id;
		this.province_name = province_name;
		this.city_name = city_name;
		this.operators = operators;
		this.project_level = project_level;
		this.ihandle_url = ihandle_url;
	}
	public SimProject() {
		super();
	}
	
	/**
	* @Description:  校验所有的字段不能为空
	* @author weichengz
	* @date 2020年6月12日 下午3:45:33
	 */
	public void verificationAll() {
		if (StringUtils.isBlank(this.project_name)) {
			throw new IllegalArgumentException("项目名不能为空");
		}
		if (StringUtils.isBlank(this.net_id)) {
			throw new IllegalArgumentException("网络不能为空");
		}
		if (StringUtils.isBlank(this.province_name)) {
			throw new IllegalArgumentException("省不能为空");
		}
		if (StringUtils.isBlank(this.city_name)) {
			throw new IllegalArgumentException("城市不能为空");
		}
		if (StringUtils.isBlank(this.operators)) {
			throw new IllegalArgumentException("运营商不能为空");
		}
		if (StringUtils.isBlank(this.ihandle_url)) {
			throw new IllegalArgumentException("项目URL不能为空");
		}
	}
	
	/**
	 * 校验网络ID  是不是符合要求
	* @Description: TODO
	* @author weichengz
	* @date 2020年6月15日 下午3:42:15
	 */
	public void validNetId(String net_id2) {
		
	}

}
