package com.boot.kaizen.business.charge;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
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

	/**
	 * 
	 * @Description: 微信下单代码
	 * @author weichengz
	 * @date 2019年10月21日 下午4:09:41
	 */
	@ResponseBody
	@RequestMapping(value = "/chargeAli", method = RequestMethod.POST)
	public JsonMsgUtil charge() {
		try {
			// 创建HttpClient对象
			String url="https://openapi.alipay.com/gateway.do";
			String appId="2019102468606534";
			String appPrivateKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQChZAk6++kSeWXeOPY9IKNTtd+dvobPyongPyhdrDGObBV3JRVvBG+jv20nufhgndVfFVY9c5YQYxaBixKkabUZHP3YFOsUYlZa8LGjkUjeKLdbzQyOMilHVLmzpg7Eo89SSkCacsBaCwx4dC2uUgHIByyWtKK/aCKo5yQnkETz0BGCEKCfO9tskac5mPHeuxQOWTvWQMtHuImVBNEO/Up8Zv1Y+Mjtrwg5uBm7pcAj3CZFri7FhnN/4uLrNkPjrXjGepyxOCiDdltRwu2+O+hj1TzqIrfXmYquEQQX4WxL7+bHRbSoN3BuhlkMCquYjz6hggT7kqolTiqsz9A8SeyPAgMBAAECggEBAJnZrj7vZAk7dxO3w20UTv5+83QB66vsTTFbo9RgUUGO4YuZ62vQk8hKtPjY/ODFtWEWCwc7x8NJYmwNsrDPIFeXXpl9n3foIqBAsWbXGdrGf7zgpOZtBRK9vuV2GL/11K6CoEFbwOhVfgbZ4a46+P3BcQ0dl8PVtqzC46Ffk4vDuvOvyPL7RRauqowTNMCHgwG1rlraPaAn6e8w5S1UXxX+UNTfNq6tvfIYoxD5FOfROtepirYzvKDxdBSJOycDHw2wE7nSEyV3r+CIbGDWObc08qRnnaX7BuCB4npTE2VEQrWIBVHRjPe/a5bvbMgu34Y2pWDqdn547O3lnssMfVkCgYEA1F5RJpbsQefnOnCdq8PphtQiwQNSbX7C+vBc7zdGRhDA+mQP9SNCFd1IO18RVd4FRYpXKnaQchg/OKXvvPD0h3fsGFnoPV9AWWIu4/SNpP+5wNoKG6CSyKlx+/5NAD/kpkdssHjpB4XcdbJuS5Vzuo7fizG4uhEFDbE4Bevdj4UCgYEAwoyCX8rJwADh70mqMMLgx8ggrABqpEVdi/V0J/wvjT06kW09la7pi5B0QapZJbpY0+LaFApS29KWXTTTiEMx4UtIO4jdoaPO2zVKSQJcYtSY3WN4QMP3TVsn+H/VhshhjF9EvFZi0SGNvGV8WVvzJrlgBqeh8R/Y8E6UhYptpgMCgYBx1vlTYVXWYnTOG+45VDTLxM24f4phDbGBiPWYeh3ekz6POrJfKv5S8CK3SNfnGrPiuizNo2786kiBmlnwA45hTASwbQmUVIoPIBWYttBzIW42lesTMjlc/ZAPkBtAxf9QrzrxQm9QTA6IvRRoyf2WhN4KJKYsf9EgrN4mNnC3bQKBgGwBE1PYwZ4tH0D3lVsSpTVj/fdfF9TwiVlsv7p+/2sapcwcaEcT3qXTYScfSfRu+mdPFiBwBsXwp/dAn3qTSzq2z3f1qIzW4rzBXecgP2fCfMbTNOxHnqA8TflgBouX7MfAD+m6z4soZIZFlP4K1PcXjJ7FK1swggAOr9dfHTY/AoGBAKaRcBRg7LEtVUolEiYLmS5PS8nOmilLn+URXk6yqZvz6SrtCXZ6Wh8OVvacJsO0IhxDCad9Xb139/Ihji7xwhIuvuDPbMjTDoELlgLcYcXluXTK0/FkORRLnCB0pjwnzGwqdXfb5MyMoqzQwokdEKKDcE7Dr0PGgswuVv+fZS8e";
			String format="json";
			String charset="UTF-8";
			String aliPayPublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0NnM5pnr2m0eyufnYrf3bmpar7PQqF5boeoFU9fmYZhMqfGWyKCiz5GSmYXgaZXwxd4pDfjwBlTNBFYpAfpCVHwZOG1u9Q2q5VqhEAbT1gJtaDZP4ZuICD9jThN/g5ipbrY+VVY22pX7lLtyrhXv5s5kYJl2REd8Wj9wZsIW20NebBgaBlANVKU1aXCI1Ss79utyxlvaV1bIaKerYw7bXtYpKlJMdajbRRdV6K9Ugo8b8r8kbvG8f05cAPM9m7f42bY1ds0j2+AAp96J/yjbYMotvWFCqzM6/fsacnFzOWkJpaTAiX/3LiJlQmxNSKFLiL7iHE/J3o/tX23iAUospwIDAQAB";
			String signType="RSA2";
			AlipayClient alipayClient = new DefaultAlipayClient(url, appId, appPrivateKey, format, charset, aliPayPublicKey, signType);

			//创建支付宝请求
			AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();//创建API对应的request类\
			Map<String, Object> paramMap=new HashMap<>();
			paramMap.put("out_trade_no", new IdWorkerUtil().nextId() + "");
			paramMap.put("total_amount", "1");//以元为单位
			paramMap.put("subject", "支付宝支付");
			paramMap.put("store_id", UUID.randomUUID().toString().replace("-", ""));
			paramMap.put("timeout_express", "90m");
			request.setBizContent(JSON.toJSONString(paramMap));
			AlipayTradePrecreateResponse response = alipayClient.execute(request);
			String body = response.getBody();
			return new JsonMsgUtil(true, "操作成功", response.getBody());
		} catch (Exception e) {
			return new JsonMsgUtil(false, "失败了", "" + e.getMessage());
		}
	}

	

}
