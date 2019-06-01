package com.boot.kaizen.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * 
 * @author a-zhangweicheng
 * 
 * @ExceptionHandler：统一处理某一类异常，从而能够减少代码重复率和复杂度
 * @ControllerAdvice：异常集中处理，更好的使业务逻辑与异常处理剥离开
 * @ResponseStatus：可以将某种异常映射为HTTP状态码
 *
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

	// private static final Logger log = LoggerFactory.getLogger("adminLogger");

	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public JsonMsgUtil badRequestException(IllegalArgumentException exception) {
		return new JsonMsgUtil(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), HttpStatus.BAD_REQUEST.value(),
				exception.getMessage());
	}

	@ExceptionHandler({ AccessDeniedException.class })
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public JsonMsgUtil badRequestException(AccessDeniedException exception) {
		return new JsonMsgUtil(HttpStatus.FORBIDDEN.value(), exception.getMessage(), HttpStatus.FORBIDDEN.value(),
				exception.getMessage());
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class, HttpMessageNotReadableException.class,
			UnsatisfiedServletRequestParameterException.class, MethodArgumentTypeMismatchException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public JsonMsgUtil badRequestException(Exception exception) {
		return new JsonMsgUtil(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), HttpStatus.BAD_REQUEST.value(),
				exception.getMessage());
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public JsonMsgUtil exception(Throwable throwable) {
		return new JsonMsgUtil(HttpStatus.INTERNAL_SERVER_ERROR.value(), throwable.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), throwable.getMessage());
	}

}
