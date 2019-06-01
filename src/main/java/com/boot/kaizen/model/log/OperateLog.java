package com.boot.kaizen.model.log;

import java.util.Date;

/**
 * 操作日志：监听核心模块增删改查
 * 
 * @author a-zhangweicheng
 *
 */
public class OperateLog {

	private Long id;
	private Long userId;// 用户
	private Long projId;// 项目
	private String packageName;// 包名
	private String methodName;// 方法名字
	private String methodArgs;// 参数
	private Date createTime;// 创建时间
	private String entityName;// crud
	private String msg;// 附加信息

	public Date getCreateTime() {
		return createTime;
	}


	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodArgs() {
		return methodArgs;
	}

	public void setMethodArgs(String methodArgs) {
		this.methodArgs = methodArgs;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	public OperateLog(Long userId, Long projId, String packageName, String methodName, String methodArgs,
			Date createTime, String entityName, String msg) {
		super();
		this.userId = userId;
		this.projId = projId;
		this.packageName = packageName;
		this.methodName = methodName;
		this.methodArgs = methodArgs;
		this.createTime = createTime;
		this.entityName = entityName;
		this.msg = msg;
	}

	public OperateLog(Long id, Long userId, Long projId, String packageName, String methodName, String methodArgs,
			String msg) {
		super();
		this.id = id;
		this.userId = userId;
		this.projId = projId;
		this.packageName = packageName;
		this.methodName = methodName;
		this.methodArgs = methodArgs;
		this.msg = msg;
	}

	public OperateLog() {
		super();
	}

	public interface operateV {
		int ADD = 0;
		int DELETE = 1;
		int UPDATE = 2;
		int QUERY = 3;
	}
}
