package com.boot.kaizen.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 手动控制输出json数据
 * 
 * @author a-zhangweicheng
 *
 */
public class ResponseUtil {

	public static void responseJson(HttpServletResponse response, int status, Object data) {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "*");
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(status);

			response.getWriter().write(JSONObject.toJSONString(data,SerializerFeature.WriteMapNullValue));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
