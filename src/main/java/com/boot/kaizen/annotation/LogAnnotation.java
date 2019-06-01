package com.boot.kaizen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * log注解 定义在方法上 用@LogAnnotation(flag="") 配合日志拦截器使用
 * flag：crud
 * @author a-zhangweicheng
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
	String flag() default "";
}
