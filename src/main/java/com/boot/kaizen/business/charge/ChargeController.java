package com.boot.kaizen.business.charge;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.boot.kaizen.util.HttpClient;
import com.boot.kaizen.util.IdWorkerUtil;
import com.boot.kaizen.util.JsonMsgUtil;
import com.github.wxpay.sdk.WXPayUtil;

/**
 * 微信支付测试代码
 * 
 * @author weichengz
 * @date 2019年10月21日 下午3:32:59
 */
@Controller
@RequestMapping("/charge")
public class ChargeController {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");
	private static Integer num = 1;

	/**
	 * 
	 * @Description: 微信下单代码
	 * @author weichengz
	 * @date 2019年10月21日 下午4:09:41
	 */
	@ResponseBody
	@RequestMapping(value = "/charge", method = RequestMethod.POST)
	public JsonMsgUtil charge() {
		log.debug("您点击了生成二维码的链接{}", "张维程");
		try {
			// 创建HttpClient对象
			HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
			// 设置https
			client.setHttps(true);
			// 创建map集合
			Map<String, String> param = new HashMap<>();
			// 公众号
			param.put("appid", "wx0609f8351dca9750");
			// 商户号
			param.put("mch_id", "1536725911");
			// 随机字符串
			param.put("nonce_str", WXPayUtil.generateNonceStr());
			// 商品描述
			param.put("body", "音达测试");// 在钱的上方提示信息
			// 订单的编号 这个必须保证唯一性
			String orderNo = new IdWorkerUtil().nextId() + "";
			// 商户订单号
			param.put("out_trade_no", orderNo);
			// 支付的金额
			String totalFee = "1";
			// 总金额（分）
			param.put("total_fee", totalFee);
			// IP
			param.put("spbill_create_ip", "127.0.0.1");
			// 回调地址
			param.put("notify_url", "http://ihandle.huanuo-nsb.com/charge/chargeNotifyUrl");
			// 交易类型
			param.put("trade_type", "NATIVE");
			// 生成签名，把map集合转换成xml格式的字符串
			// 1. 存放数据map 2. 申请的时候预留的密钥
			String xmlParam = WXPayUtil.generateSignedXml(param, "txjavayingmulaoshi01234567891234");
			// 请求https://api.mch.weixin.qq.com/pay/unifiedorder，传很多请求参数
			client.setXmlParam(xmlParam);
			// 发送请求
			client.post();
			// 获取到返回结果
			String content = client.getContent();
			// 使用工具类，解析content数据
			Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
			// 把订单编号 和 支付金额存入到resultMap
			resultMap.put("out_trade_no", orderNo);
			resultMap.put("total_fee", totalFee);

			log.info("下单的信息值{}", JSON.toJSONString(resultMap));

			return new JsonMsgUtil(true, "操作成功", resultMap);
		} catch (Exception e) {
			return new JsonMsgUtil(false, "失败了", "" + e.getMessage());
		}
	}

	/**
	 * 获取微信支付成功回调的参数（notify_url中返回的参数） 这里面有个技巧，有时候
	 * 程序会出现问题，那么这个时候我们需要手动去微信查询订单的状态，这样可以保证了万无一失
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月21日 下午5:30:47
	 */
	@ResponseBody
	@RequestMapping(value = "/chargeNotifyUrl", method = RequestMethod.POST)
	public String getNotifyParameter(HttpServletRequest request) throws Exception {

		log.info("支付调用了回调的结果，次数：{}", num);
		InputStream inputStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, length);
		}
		outSteam.close();
		inputStream.close();
		// 获取微信调用我们notify_url的返回信息
		String resultXml = new String(outSteam.toByteArray(), "UTF-8");
		Map<String, String> notifyMap = WXPayUtil.xmlToMap(resultXml);// 接收返回的值
		log.info("支付回调的值：{}", JSON.toJSONString(notifyMap));
		// 通知微信我接收成功了
		Map<String, String> responseMap = new HashMap<>(2);
		responseMap.put("return_code", "SUCCESS1");
		responseMap.put("return_msg", "OK");
		String responseXml = WXPayUtil.mapToXml(responseMap);
		num++;
		return responseXml;
	}

	/**
	 * 查询订单
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月22日 上午9:32:54
	 */
	@ResponseBody
	@RequestMapping(value = "/orderquery", method = RequestMethod.POST)
	public JsonMsgUtil orderquery(@RequestParam("transactionId") String transactionId) throws Exception {

		log.debug("订单查询接口开始", "张维程");
		try {
			// 创建HttpClient对象
			HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
			// 设置https
			client.setHttps(true);
			// 创建map集合
			Map<String, String> param = new HashMap<>();
			// 公众号
			param.put("appid", "wx0609f8351dca9750");
			// 商户号
			param.put("mch_id", "1536725911");
			// 微信订单号
			param.put("transaction_id", transactionId);
			// 随机字符串
			param.put("nonce_str", WXPayUtil.generateNonceStr());
			// 生成签名，把map集合转换成xml格式的字符串
			// 1. 存放数据map 2. 申请的时候预留的密钥
			String xmlParam = WXPayUtil.generateSignedXml(param, "txjavayingmulaoshi01234567891234");
			// 请求https://api.mch.weixin.qq.com/pay/unifiedorder，传很多请求参数
			client.setXmlParam(xmlParam);
			// 发送请求
			client.post();
			// 获取到返回结果
			String content = client.getContent();
			// 使用工具类，解析content数据
			Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
			// 把订单编号 和 支付金额存入到resultMap
			log.info("订单查询的结果：{}", JSON.toJSONString(resultMap));
			return new JsonMsgUtil(true, "操作成功", resultMap);
		} catch (Exception e) {
			return new JsonMsgUtil(false, "失败了", "" + e.getMessage());
		}
	}

	/**
	 * 关闭订单：订单生成之后可以立马关闭，注意关闭之后如果在支付会提示工单已被关闭
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月22日 上午9:32:54
	 */
	@ResponseBody
	@RequestMapping(value = "/closeorder", method = RequestMethod.POST)
	public JsonMsgUtil closeorder(@RequestParam("outTradeNo") String outTradeNo) throws Exception {

		log.debug("商品订单关闭------------{}", "张维程");
		try {
			// 创建HttpClient对象
			HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/closeorder");
			// 设置https
			client.setHttps(true);
			// 创建map集合
			Map<String, String> param = new HashMap<>();
			// 公众号
			param.put("appid", "wx0609f8351dca9750");
			// 商户号
			param.put("mch_id", "1536725911");
			// 商户订单号
			param.put("out_trade_no", outTradeNo);
			// 随机字符串
			param.put("nonce_str", WXPayUtil.generateNonceStr());
			// 生成签名，把map集合转换成xml格式的字符串
			// 1. 存放数据map 2. 申请的时候预留的密钥
			String xmlParam = WXPayUtil.generateSignedXml(param, "txjavayingmulaoshi01234567891234");
			// 请求https://api.mch.weixin.qq.com/pay/unifiedorder，传很多请求参数
			client.setXmlParam(xmlParam);
			// 发送请求
			client.post();
			// 获取到返回结果
			String content = client.getContent();
			// 使用工具类，解析content数据
			Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
			// 把订单编号 和 支付金额存入到resultMap
			log.info("关闭订单时候的返回结果：{}", JSON.toJSONString(resultMap));
			return new JsonMsgUtil(true, "操作成功", resultMap);
		} catch (Exception e) {
			return new JsonMsgUtil(false, "失败了", "" + e.getMessage());
		}
	}

	/**
	 * 历史交易清单查询，注意历史订单是后一天10点后生成前一天的订单
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月22日 上午10:03:32
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadbill", method = RequestMethod.POST)
	public JsonMsgUtil downloadbill(@RequestParam("billDate") String billDate) throws Exception {

		log.debug("查询指定时间的历史清单------------{}", "张维程");
		try {
			// 创建HttpClient对象
			HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/downloadbill");
			// 设置https
			client.setHttps(true);
			// 创建map集合
			Map<String, String> param = new HashMap<>();
			// 公众号
			param.put("appid", "wx0609f8351dca9750");
			// 商户号
			param.put("mch_id", "1536725911");
			// 随机字符串
			param.put("nonce_str", WXPayUtil.generateNonceStr());
			// 队账日期
			param.put("bill_date", billDate);
			param.put("bill_type", "ALL");
			// 生成签名，把map集合转换成xml格式的字符串
			// 1. 存放数据map 2. 申请的时候预留的密钥
			String xmlParam = WXPayUtil.generateSignedXml(param, "txjavayingmulaoshi01234567891234");
			// 请求https://api.mch.weixin.qq.com/pay/unifiedorder，传很多请求参数
			client.setXmlParam(xmlParam);
			// 发送请求
			client.post();
			// 获取到返回结果
			String content = client.getContent();// 在支付成功的时候，会返回文本，在支付失败的时候 会返回 xml的串
			// 把订单编号 和 支付金额存入到resultMap
			log.info("查询历史订单数据：{}", content);

			// 这种是可以将csv字符串转为流，然后一行行读取
			InputStream in = new ByteArrayInputStream(content.getBytes("UTF-8"));
			InputStreamReader reader = new InputStreamReader(in, "UTF-8");

			BufferedReader readerBuffer = new BufferedReader(reader);
			while (true) {
				String nextline = readerBuffer.readLine();
				if (nextline == null) {
					break;
				}
				String[] split = nextline.trim().split(",");
				for (int i = 0; i < split.length; i++) {
					System.out.print(split[i] + "------");
				}
				System.out.println("");
			}
			readerBuffer.close();
			reader.close();

			return new JsonMsgUtil(true, "操作成功", content);
		} catch (Exception e) {
			return new JsonMsgUtil(false, "失败了", "" + e.getMessage());
		}
	}

	/**
	 * 历史资金流水账单
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月22日 上午10:41:47
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadfundflow", method = RequestMethod.POST)
	public JsonMsgUtil downloadfundflow(@RequestParam("billDate") String billDate,
			@RequestParam("accountType") String accountType) throws Exception {

		log.debug("查询指定时间的历史资金流水账单------------{}", "张维程");
		try {
			// 创建HttpClient对象
			HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/downloadfundflow");
			// 设置https
			client.setHttps(true);
			// 创建map集合
			Map<String, String> param = new HashMap<>();
			// 公众号
			param.put("appid", "wx0609f8351dca9750");
			// 商户号
			param.put("mch_id", "1536725911");
			// 随机字符串
			param.put("nonce_str", WXPayUtil.generateNonceStr());
			// 队账日期
			param.put("bill_date", billDate);
			// 资金账户类型
			param.put("account_type", accountType);
			// 生成签名，把map集合转换成xml格式的字符串
			// 1. 存放数据map 2. 申请的时候预留的密钥
			String xmlParam = WXPayUtil.generateSignedXml(param, "txjavayingmulaoshi01234567891234");
			// 请求https://api.mch.weixin.qq.com/pay/unifiedorder，传很多请求参数
			client.setXmlParam(xmlParam);
			// 发送请求
			client.post();
			// 获取到返回结果
			String content = client.getContent();// 在支付成功的时候，会返回文本，在支付失败的时候 会返回 xml的串
			// 把订单编号 和 支付金额存入到resultMap
			log.info("查询历史资金流水账单数据：{}", content);

			// 这种是可以将csv字符串转为流，然后一行行读取
			InputStream in = new ByteArrayInputStream(content.getBytes("UTF-8"));
			InputStreamReader reader = new InputStreamReader(in, "UTF-8");

			BufferedReader readerBuffer = new BufferedReader(reader);
			while (true) {
				String nextline = readerBuffer.readLine();
				if (nextline == null) {
					break;
				}
				String[] split = nextline.trim().split(",");
				for (int i = 0; i < split.length; i++) {
					System.out.print(split[i] + "------");
				}
				System.out.println("");
			}
			readerBuffer.close();
			reader.close();

			return new JsonMsgUtil(true, "操作成功", content);
		} catch (Exception e) {
			return new JsonMsgUtil(false, "失败了", "" + e.getMessage());
		}

	}

	/**
	 * 申请退款
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月22日 上午10:41:47
	 */
	@ResponseBody
	@RequestMapping(value = "/refund", method = RequestMethod.POST)
	public JsonMsgUtil refund(@RequestParam("billDate") String billDate,
			@RequestParam("accountType") String accountType,
			@RequestParam("transactionId") String transactionId) throws Exception {
		log.debug("申请退款接口------------{}", "张维程");
		try {
			// 创建HttpClient对象
			HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/secapi/pay/refund");
			// 设置https
			client.setHttps(true);
			// 创建map集合
			Map<String, String> param = new HashMap<>();
			// 公众号
			param.put("appid", "wx0609f8351dca9750");
			// 商户号
			param.put("mch_id", "1536725911");
			// 随机字符串
			param.put("nonce_str", WXPayUtil.generateNonceStr());
			// 微信订单号
			param.put("transaction_id", transactionId);
			
			
			// 队账日期
			param.put("bill_date", billDate);
			// 资金账户类型
			param.put("account_type", accountType);
			// 生成签名，把map集合转换成xml格式的字符串
			// 1. 存放数据map 2. 申请的时候预留的密钥
			String xmlParam = WXPayUtil.generateSignedXml(param, "txjavayingmulaoshi01234567891234");
			// 请求https://api.mch.weixin.qq.com/pay/unifiedorder，传很多请求参数
			client.setXmlParam(xmlParam);
			// 发送请求
			client.post();
			// 获取到返回结果
			String content = client.getContent();// 在支付成功的时候，会返回文本，在支付失败的时候 会返回 xml的串
			// 把订单编号 和 支付金额存入到resultMap
			log.info("查询历史资金流水账单数据：{}", content);

			// 这种是可以将csv字符串转为流，然后一行行读取
			InputStream in = new ByteArrayInputStream(content.getBytes("UTF-8"));
			InputStreamReader reader = new InputStreamReader(in, "UTF-8");

			BufferedReader readerBuffer = new BufferedReader(reader);
			while (true) {
				String nextline = readerBuffer.readLine();
				if (nextline == null) {
					break;
				}
				String[] split = nextline.trim().split(",");
				for (int i = 0; i < split.length; i++) {
					System.out.print(split[i] + "------");
				}
				System.out.println("");
			}
			readerBuffer.close();
			reader.close();

			return new JsonMsgUtil(true, "操作成功", content);
		} catch (Exception e) {
			return new JsonMsgUtil(false, "失败了", "" + e.getMessage());
		}

	}
	
	
	
	/**
	 * 退款回调接口
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月22日 上午10:41:47
	 */
	@ResponseBody
	@RequestMapping(value = "/notifyRefundUrl", method = RequestMethod.POST)
	public JsonMsgUtil notifyRefundUrl(@RequestParam("billDate") String billDate,
			@RequestParam("accountType") String accountType) throws Exception {
		
		log.debug("查询指定时间的历史资金流水账单------------{}", "张维程");
		try {
			// 创建HttpClient对象
			HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/downloadfundflow");
			// 设置https
			client.setHttps(true);
			// 创建map集合
			Map<String, String> param = new HashMap<>();
			// 公众号
			param.put("appid", "wx0609f8351dca9750");
			// 商户号
			param.put("mch_id", "1536725911");
			// 随机字符串
			param.put("nonce_str", WXPayUtil.generateNonceStr());
			// 队账日期
			param.put("bill_date", billDate);
			// 资金账户类型
			param.put("account_type", accountType);
			// 生成签名，把map集合转换成xml格式的字符串
			// 1. 存放数据map 2. 申请的时候预留的密钥
			String xmlParam = WXPayUtil.generateSignedXml(param, "txjavayingmulaoshi01234567891234");
			// 请求https://api.mch.weixin.qq.com/pay/unifiedorder，传很多请求参数
			client.setXmlParam(xmlParam);
			// 发送请求
			client.post();
			// 获取到返回结果
			String content = client.getContent();// 在支付成功的时候，会返回文本，在支付失败的时候 会返回 xml的串
			// 把订单编号 和 支付金额存入到resultMap
			log.info("查询历史资金流水账单数据：{}", content);
			
			// 这种是可以将csv字符串转为流，然后一行行读取
			InputStream in = new ByteArrayInputStream(content.getBytes("UTF-8"));
			InputStreamReader reader = new InputStreamReader(in, "UTF-8");
			
			BufferedReader readerBuffer = new BufferedReader(reader);
			while (true) {
				String nextline = readerBuffer.readLine();
				if (nextline == null) {
					break;
				}
				String[] split = nextline.trim().split(",");
				for (int i = 0; i < split.length; i++) {
					System.out.print(split[i] + "------");
				}
				System.out.println("");
			}
			readerBuffer.close();
			reader.close();
			
			return new JsonMsgUtil(true, "操作成功", content);
		} catch (Exception e) {
			return new JsonMsgUtil(false, "失败了", "" + e.getMessage());
		}
		
	}

}
