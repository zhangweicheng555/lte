package com.boot.kaizen.business.es.controller;

import java.io.Serializable;

/**
 * 测试查询 带不带keyword的测试实体类
 * 
 * @author weichengz
 * @date 2020年6月30日 上午9:25:39
 */
public class TestModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private Long nowdate;
	private Integer age;
	private Double money;
	private Boolean useable;//是否可用
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getNowdate() {
		return nowdate;
	}
	public void setNowdate(Long nowdate) {
		this.nowdate = nowdate;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Boolean getUseable() {
		return useable;
	}
	public void setUseable(Boolean useable) {
		this.useable = useable;
	}
	public TestModel(String id, String name, Long nowdate, Integer age, Double money, Boolean useable) {
		super();
		this.id = id;
		this.name = name;
		this.nowdate = nowdate;
		this.age = age;
		this.money = money;
		this.useable = useable;
	}
	public TestModel() {
		super();
	}
	
	
	
}
