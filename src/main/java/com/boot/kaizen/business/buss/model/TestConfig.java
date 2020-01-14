package com.boot.kaizen.business.buss.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableName;
import com.boot.kaizen.config.SuperEntity;

/**
 * 配置类型的一些值
 * 
 * @author weichengz
 * @date 2020年1月14日 下午4:23:14
 */
@TableName("test_config")
public class TestConfig extends SuperEntity<TestConfig> {

	private static final long serialVersionUID = 1L;

	private Integer projId;
	private Date createTime;

	private String item; // RSRP 、SINR、DL、UL 这个是固定的
	private String jsonStr;// 该项对应得测试配置列表内容 这个就是 RequestParamConfig得json传

	public Integer getProjId() {
		return projId;
	}

	public void setProjId(Integer projId) {
		this.projId = projId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public TestConfig(Integer projId, Date createTime, String item, String jsonStr) {
		super();
		this.projId = projId;
		this.createTime = createTime;
		this.item = item;
		this.jsonStr = jsonStr;
	}

	public TestConfig() {
		super();
	}

}
