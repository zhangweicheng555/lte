package com.boot.kaizen.config;


/**
 * redis属性注入
 * 
 * @author weichengz
 * @date 2019年4月1日 下午7:17:06
 */
//@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

	private Integer maxIdle;
	private Integer minIdle;
	private Integer maxTotal;
	private Long maxWaitMillis;
	private Boolean testOnBorrow;
	private Integer timeout;
	private Integer db;
	private String sentinelPassword;
	private String sentinelMaster;
	private String sentinelNodes;

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}

	public Long getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(Long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public Boolean getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(Boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getDb() {
		return db;
	}

	public void setDb(Integer db) {
		this.db = db;
	}

	public String getSentinelPassword() {
		return sentinelPassword;
	}

	public void setSentinelPassword(String sentinelPassword) {
		this.sentinelPassword = sentinelPassword;
	}

	public String getSentinelMaster() {
		return sentinelMaster;
	}

	public void setSentinelMaster(String sentinelMaster) {
		this.sentinelMaster = sentinelMaster;
	}

	public String getSentinelNodes() {
		return sentinelNodes;
	}

	public void setSentinelNodes(String sentinelNodes) {
		this.sentinelNodes = sentinelNodes;
	}

	public RedisProperties(Integer maxIdle, Integer minIdle, Integer maxTotal, Long maxWaitMillis, Boolean testOnBorrow,
			Integer timeout, Integer db, String sentinelPassword, String sentinelMaster, String sentinelNodes) {
		super();
		this.maxIdle = maxIdle;
		this.minIdle = minIdle;
		this.maxTotal = maxTotal;
		this.maxWaitMillis = maxWaitMillis;
		this.testOnBorrow = testOnBorrow;
		this.timeout = timeout;
		this.db = db;
		this.sentinelPassword = sentinelPassword;
		this.sentinelMaster = sentinelMaster;
		this.sentinelNodes = sentinelNodes;
	}

	public RedisProperties() {
		super();
	}

}
