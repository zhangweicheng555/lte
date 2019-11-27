package com.boot.kaizen.util;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.boot.kaizen.util.httpip.HttpIp;
import com.boot.kaizen.util.httpip.HttpIpDataInfo;

/**
 * http工具类
 * 
 * @author a-zhangweicheng
 *
 */
public class HttpUtil {

	
	/**
	 * 返送post请求 返回效应的json传
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年3月19日 上午10:05:25 "http://localhost:8082/test/test"
	 */
	public static String sendPostRequest(String httpUrl, Map<String, Object> paramMap) {
		// 获取连接客户端工具
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String entityStr = null;
		CloseableHttpResponse response = null;
		try {
			// 创建POST请求对象
			HttpPost httpPost = new HttpPost(httpUrl);
			// 设置超时
			RequestConfig config = RequestConfig.custom()//
					.setConnectTimeout(60000)// 链接超时 1分钟     10秒=10000
					.setConnectionRequestTimeout(300000)// 请求超时
					.setSocketTimeout(300000)// 读取超时
					.build();//
			
			httpPost.setConfig(config);
			// 创建请求参数
			List<NameValuePair> list = new LinkedList<>();
			if (paramMap == null || paramMap.isEmpty()) {
				throw new IllegalArgumentException("传入参数为空");
			}
			Set<Entry<String, Object>> entrySet = paramMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				BasicNameValuePair basicNameValuePair = new BasicNameValuePair(entry.getKey(),
						entry.getValue().toString());
				list.add(basicNameValuePair);
			}
			// 使用URL实体转换工具
			UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
			httpPost.setEntity(entityParam);
			// 浏览器表示
			httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
			// 传输的类型
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			// 执行请求
			response = httpClient.execute(httpPost);
			// 获得响应的实体对象
			HttpEntity entity = response.getEntity();
			// 使用Apache提供的工具类进行转换成字符串
			entityStr = EntityUtils.toString(entity, "UTF-8");
			// 打印请求头的所有的信息
			// System.out.println(Arrays.toString(response.getAllHeaders()));
		} catch (ClientProtocolException e) {
			throw new IllegalArgumentException("Http协议出现问题:"+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("IO异常:"+e.getMessage());
		} finally {
			// 释放连接
			if (null != response) {
				try {
					response.close();
					httpClient.close();
				} catch (IOException e) {
					System.out.println("释放连接出错:"+e.getMessage());// 这个地方不抛出异常
				}
			}
		}
		return entityStr;
	}

	
	/**
	 * 获取ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String xip = request.getHeader("X-Real-IP");
		String xfor = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(xfor) && !"unKnown".equalsIgnoreCase(xfor)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = xfor.indexOf(",");
			if (index != -1) {
				return xfor.substring(0, index);
			} else {
				return xfor;
			}
		}
		xfor = xip;
		if (StringUtils.isNotEmpty(xfor) && !"unKnown".equalsIgnoreCase(xfor)) {
			return xfor;
		}
		if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
			xfor = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
			xfor = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
			xfor = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
			xfor = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
			xfor = request.getRemoteAddr();
		}
		return xfor;
	}

	/**
	 * 根据ip查询所属地区
	 * 
	 * @param ip
	 * @return
	 */
	public static String queryRegionByIp(String ip) {
		// ip = "219.136.134.157";
		if (StringUtils.isBlank(ip)) {
			return "";
		} else {
			// jsonResult用于接收返回的json数据
			String jsonResult = null;
			try {
				jsonResult = getAddresses("ip=" + ip, "utf-8");
				if (StringUtils.isBlank(jsonResult)) {
					return "";
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			HttpIp httpIp = JSONObject.parseObject(jsonResult, HttpIp.class);
			if (httpIp != null) {
				HttpIpDataInfo data = httpIp.getData();
				return data.getCountry() + data.getRegion() + data.getCity() + data.getCountry();
			}
			return "";
		}
	}

	public static String getAddresses(String content, String encodingString) throws UnsupportedEncodingException {
		// 这里调用淘宝API
		String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
		String returnStr = getResult(urlStr, content, encodingString);
		if (returnStr != null) {
			// 处理返回的省市区信息
			returnStr = decodeUnicode(returnStr);
			String[] temp = returnStr.split(",");
			if (temp.length < 3) {
				return "";// 无效IP，局域网测试
			}
			return returnStr;
		}
		return null;
	}

	private static String getResult(String urlStr, String content, String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true);// 是否打开输出流 true|false
			connection.setDoInput(true);// 是否打开输入流true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据
			out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
			out.flush();// 刷新
			out.close();// 关闭输出流
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
			// ,以BufferedReader流来读取
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}
		return null;
	}

	/**
	 * unicode 转换成 中文
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

}
