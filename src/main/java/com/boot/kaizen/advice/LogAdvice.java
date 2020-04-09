package com.boot.kaizen.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.annotation.LogAnnotation;
import com.boot.kaizen.service.log.ISysOperateLogService;

/**
 * 日志拦截器 使用方式 在方法上 用@LogAnnotation(flag="实体类的名字")
 * 
 * @author a-zhangweicheng
 */
@Aspect
@Component
public class LogAdvice {

	@Autowired
	private ISysOperateLogService operateLogService;

	@Around(value = "@annotation(com.boot.kaizen.annotation.LogAnnotation)")
	public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
		// LoginUser loginUser = UserUtil.getLoginUser();

		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		String packageName = methodSignature.getDeclaringTypeName();
		String methodName = methodSignature.getName();
		String args = JSONObject.toJSON(joinPoint.getArgs()).toString();
		// 获取方法上面的注解
		LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
		// 获取注解的参数 这个代表【实体类】的名字
		String entityName = logAnnotation.flag();
		try {// try的原因就是这个方法可能会出现异常
				// 记录操作日志
				// OperateLog operateLog = new OperateLog(uuid, loginUser.getProjId(),
				// packageName, methodName,args, new Date(), entityName,
				// JSONObject.toJSONString(loginUser));
				// operateLogService.save(operateLog);
			Object object = joinPoint.proceed();// 执行拦截的方法
			return object;
		} catch (Exception e) {
			throw e; // 1.异常一定要抛出来 方便json捕获 2.可以业务处理
		} finally { // 可以业务处理
		}

	}
}
