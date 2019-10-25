package com.boot.kaizen.business.charge;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.boot.kaizen.business.charge.model.NotifyUrlModel;
import com.boot.kaizen.util.IdWorkerUtil;
import com.boot.kaizen.util.JsonMsgUtil;

/**
 * 支付宝支付测试代码
 * 
 * @author weichengz
 * @date 2019年10月24日 上午11:18:54
 */
@Controller
@RequestMapping("/charge")
public class ChargeAliController {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");
	private static Integer num = 1;

	@Autowired
	private AlipayClient alipayClient;

	/**
	 * 
	 * @Description: 微信下单代码
	 * @author weichengz
	 * @date 2019年10月21日 下午4:09:41
	 */

	/**
	 * 返回参数
	 * 
	 * {"alipay_trade_precreate_response":{"code":"10000","msg":"Success","out_trade_no":"1187547287622701056","qr_code":"https:\/\/qr.alipay.com\/bax04191ysywmggwq6gi00fe"},"sign":"AYDvgJkugn1dWc4p97oQlhyBdj6U4Gf2gceRBSV88IB4ird4BVvXjFXwg040i8ZHeYYvuYuh1JEb0dz2An1nd4TlcsaB+hOjOBksuVwcoYGca6zyQbQOy4JrlvX9IuGdoO3wUrIvpupv+CS5kZgZ2jPtqFpl2DdtpOaXNC3P8aB+2OA5IgPeB4w/mQwb3QZCtLISE4KZnkyIMtRmfNNWKcHgJpl0dzGXScCIbidPnt5MAFGMRArJNrc0Za3O/aDWqeYam3v8xz2lUqVo82hNAn8wWxAs3gP4r3hNq80gUx2qq8N77m8L6RM9P+tL7fjLRoaChz7In5biW+kxyOxTjQ=="}
	 * {"alipay_trade_precreate_response":{"code":"10000","msg":"Success","out_trade_no":"1187548317160755200","qr_code":"https:\/\/qr.alipay.com\/bax00861zzvflvwntndq80e1"},"sign":"mel8LGMbTzWrbSa7jxSrwlimtXK6BeZrEsRztZBXIhWz2A3N2hzyinQK9YFK8gO04Lf7mc+qGURja7qV7OAo5IKDrqGBfioMz/q1q8aHrBb90Ma8Bosy+9p5A+PiXfuEyMNo70lgWWvleSF71XsS6n7Bl3VHeHszJb/rPQ6ri29itrnDkEOgEKmZYyF/5dv1ttLfr5xvbF7uYuAo0DxuRlgVpLgy2VEZscxUPYwbuhfFIijkZha/IDF+obbErT0tbnmv33sv3HmeQo2x7vB6xhV7q4Icr1/RbnMz1QoNYpR2NPLnMCVGwZcFVzFtisdxDF0bbqFqLPGbdrmVnH/rYg=="}
	 * {"alipay_trade_precreate_response":{"code":"10000","msg":"Success","out_trade_no":"1187565534235025408","qr_code":"https:\/\/qr.alipay.com\/bax01621mughbjmbqnhi2031"},"sign":"THtfP3ugrs9koJBxEGw3q2ngm5c7JlKvAMcOq9i1/upKXQFG5erqS3gIyziYBnD32hERRYHvu7z4XeS//k4FqLiOU8R7ii0qRgu2vCtIV0XmKwTvwb8s9Lej8jn5bNj79Lo3ncNkIde/hxI2ebE1RUbH5I9n9QENHoUSHQJMj63gdP17skhBvLIn9HTA9u5/IwGgatLq7JkcIR6BBHxRnh2Y43ClojzrzTKiM5Ao9faYGSSP5V5fyZHVMJ/2Xmd6TMlTG6SYMu9f7sDjHWZ7JzCZmXx6GKmAuzY8nZTAqyC/OxRMN6EYxR9/LCOIg1UwKSo8LTAXgtw3ZG/wnG9waw=="}
	 * {"alipay_trade_precreate_response":{"code":"10000","msg":"Success","out_trade_no":"1187571974444666880","qr_code":"https:\/\/qr.alipay.com\/bax05888gsmt5qxoqy7760ea"},"sign":"O422dED7EV+eih9MQFT2FL6l/PQebQpgb6It2hAd5Mx0E+kqQlh1R7RmqLhOsbc/Tr3jRRvB9Qnp1opK0VpJVjx1pgSx9/LtsA7cdR7bdI07wjaQ0Mvrx73nNaBnceN9U1Wj0EVZEVkZemXFPpdnwN+gDTu86Aesr/y0jrsxKcLDf9EzJ2PmtF/hm032A9rSJ5pVRTN7omV2VaSsA5vuZZfpDKrC1Q+ftoCOtuk+sCn2Mzvp5fSKv2hcCiYmACA9yhrMPSpjl5hHW7dx6r3qePYG1B3TKgWyaSCZCREKz2eXdJcqnihHmYD5dYk9tUoNGo/6JI1QWmWu5i70TNtUdQ=="}
	 * 
	 * {"alipay_trade_precreate_response":{"code":"10000","msg":"Success","out_trade_no":"1187609691618471936","qr_code":"https:\/\/qr.alipay.com\/bax099688vzbes7mi6j40003"},"sign":"pK4drRDzdyYAljOCugcjf6VFY3a5Jk+ikH4dzib8mgnBXrOXfcJ6U+MUIsLoq/4MlPoFrgK99nqh95X+7ztttKWOECRbKoVWYdJeO8LnpyyOshvXLT4jbGmU5Wymkm0gmDwlk8Xo2Wdp2TundQ4AKGk1bcpLreEf7AanzYvqcbXt/bb94eQg+UrqjUKfwa+UJiVFemgC5d4ir1rqhaE0nEYsHhPYdYcCfcJDG0m8IsdlkEMLejKX/qT7DAOCDUEqmXC4A3xi+K1vkMZTcMuTuCq0oidcNgTDM4x+Hwjpcd/jcnfp9oBvJXlzutTZonU71NCbz4qoxeBEir8VgU5rGA=="}
	 */
	@ResponseBody
	@RequestMapping(value = "/chargeAli", method = RequestMethod.POST)
	public JsonMsgUtil charge() {
		try {
			// 创建支付宝请求
			AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();// 创建API对应的request类\
			Map<String, Object> paramMap = new HashMap<>();
			// 商户订单号 保证全局唯一
			paramMap.put("out_trade_no", new IdWorkerUtil().nextId() + "");
			// 商户订单总额 精确到小数点两位 单位为元
			paramMap.put("total_amount", "0.01");// 以元为单位
			// 订单标题
			paramMap.put("subject", "小程程支付");
			// 取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如
			// 1.5h，可转换为 90m。
			// 订单允许的最晚付款时间
			paramMap.put("timeout_express", "10m");
			// 二维码的有效时间
			paramMap.put("qr_code_timeout_express", "2m");
			request.setBizContent(JSON.toJSONString(paramMap));
			AlipayTradePrecreateResponse response = alipayClient.execute(request);
			String body = response.getBody();
			System.out.println(body);
			return new JsonMsgUtil(true, "操作成功", response.getBody());
		} catch (Exception e) {
			return new JsonMsgUtil(false, "失败了", "" + e.getMessage());
		}
	}

	/**
	 * 获取微信支付成功回调的参数（notify_url中返回的参数） 这里面有个技巧，有时候
	 * 程序会出现问题，那么这个时候我们需要手动去微信查询订单的状态，这样可以保证了万无一失
	 * 
	 * @Description: TODO 待验证
	 * @author weichengz
	 * @date 2019年10月21日 下午5:30:47
	 */
	@ResponseBody
	@RequestMapping(value = "/chargeNotifyUrlAl", method = RequestMethod.POST)
	public String chargeNotifyUrlAl(NotifyUrlModel notifyUrlModel) throws Exception {
		log.info("支付宝回调的值：{}", JSON.toJSONString(notifyUrlModel));
		return "success";
	}

	/**
	 * 查询接口
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月25日 上午11:09:26
	 * 
	 *       {"alipay_trade_query_response":{"code":"10000","msg":"Success","buyer_logon_id":"150******31","buyer_pay_amount":"0.01","buyer_user_id":"2088912489701010","fund_bill_list":[{"amount":"0.01","fund_channel":"ALIPAYACCOUNT"}],"invoice_amount":"0.01","out_trade_no":"1187547287622701056","point_amount":"0.00","receipt_amount":"0.01","send_pay_date":"2019-10-25
	 *       09:52:08","total_amount":"0.01","trade_no":"2019102522001401015712427691","trade_status":"TRADE_SUCCESS"},"sign":"iP8X2GkdpkbxrxyrSMZAo8CWGSlEu+ybP8YLL8xH/JpkpLW9D2CqZnCK/HS3Tj4/PRQdz6Boi79cLDEoTJR9DFsU8c3CHlEuOhZdFv+W+h7aE8f71ssF98VaHeRMOvbVKu/9oDoRuqiXN/5BZQlLknuVqiFjBsE0d+93PHbEMVKK+9e4EJ5pdzKZJBulgxNqNEQ5WcD9pqDX4JT+CamOMEXqWKIIsJuPiCJy0tS/IoZeXHQD23EBCQOMCq33VuJ8YV0Me2hUrw8XUMzPr8EDX+94SU45nqiCN4LhwSbAdyOirL0Do1Pdf0iomYqgI53x6AS0x0N3uQCbB1pnN4lc0A=="}
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/chargeQuery", method = RequestMethod.POST)
	public String chargeQuery(@RequestParam("outTradeNo") String outTradeNo) throws Exception {
		// 创建支付宝请求
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();// 创建API对应的query类\ 注意 各个请求的就这块存在区别
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("out_trade_no", outTradeNo);// 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。
		request.setBizContent(JSON.toJSONString(paramMap));
		AlipayTradeQueryResponse response = alipayClient.execute(request);
		String body = response.getBody();
		if (response.isSuccess()) {
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
		return body;
	}

	/**
	 * 订单撤销 只有发生支付系统超时或者支付结果未知时可调用撤销 1.自己手动撤销的时候，支付宝会立马退款（在用户支付成功的条件下）
	 * 2.这个应该是支付宝的回调接口收不到success的时候原因
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月25日 上午11:27:39
	 */
	@ResponseBody
	@RequestMapping(value = "/chargeCancelAl", method = RequestMethod.POST)
	public String chargeCancelAl(@RequestParam("outTradeNo") String outTradeNo) throws Exception {
		// 创建支付宝请求
		AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
		;// 创建API对应的query类\ 注意 各个请求的就这块存在区别
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("out_trade_no", outTradeNo);// 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。
		request.setBizContent(JSON.toJSONString(paramMap));
		AlipayTradeCancelResponse response = alipayClient.execute(request);
		String body = response.getBody();
		if (response.isSuccess()) {
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
		return body;
	}

	/**
	 * 手动退款
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月25日 上午11:37:26
	 */
	@ResponseBody
	@RequestMapping(value = "/chargerefundAl", method = RequestMethod.POST)
	public String chargerefundAl(@RequestParam("outTradeNo") String outTradeNo) throws Exception {
		// 创建支付宝请求
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		;// 创建API对应的query类\ 注意 各个请求的就这块存在区别
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("out_trade_no", outTradeNo);// 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。
		paramMap.put("refund_amount", "0.01");// 退款金额。
		request.setBizContent(JSON.toJSONString(paramMap));
		AlipayTradeRefundResponse response = alipayClient.execute(request);
		String body = response.getBody();
		if (response.isSuccess()) {
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
		return body;
	}

	/**
	 * 
	 * @Description: 手动收单交易关闭(用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭。) 这个接口貌似不起作用
	 * @author weichengz
	 * @date 2019年10月25日 下午1:53:01
	 */
	@ResponseBody
	@RequestMapping(value = "/chargeCloseAl", method = RequestMethod.POST)
	public String chargeCloseAl(@RequestParam("outTradeNo") String outTradeNo) throws Exception {
		// 创建支付宝请求
		AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
		;// 创建API对应的query类\ 注意 各个请求的就这块存在区别
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("out_trade_no", outTradeNo);// 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。
		request.setBizContent(JSON.toJSONString(paramMap));
		AlipayTradeCloseResponse response = alipayClient.execute(request);
		String body = response.getBody();
		if (response.isSuccess()) {
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
		return body;
	}

	/**
	 * 全部账单下载地址查询 这个也不通
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月25日 下午2:03:23
	 */
	@ResponseBody
	@RequestMapping(value = "/chargeBillDown", method = RequestMethod.POST)
	public String chargeBillDown(@RequestParam("billType") String billType, @RequestParam("billDate") String billDate)
			throws Exception {
		// 创建支付宝请求
		AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
		;// 创建API对应的query类\ 注意 各个请求的就这块存在区别
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("bill_type", billType);// 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。
		paramMap.put("bill_date", billDate);// 订单支付时传入的商户订单号,和支付宝交易号不能同时为空。
		request.setBizContent(JSON.toJSONString(paramMap));

		AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
		String body = response.getBody();
		if (response.isSuccess()) {
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
		return body;
	}

}
