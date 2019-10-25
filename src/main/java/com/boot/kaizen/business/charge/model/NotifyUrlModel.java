package com.boot.kaizen.business.charge.model;

import java.util.HashMap;
import java.util.Map;

import com.boot.kaizen.enump.DicType;

/**
 * 支付宝支付成功后的回调实体类接收参数
 * 
 * @author weichengz
 * @date 2019年10月25日 上午10:03:49
 */
public class NotifyUrlModel {

	private String notify_time;// 通知时间
	private String notify_type;// 通知类型
	private String notify_id;// 通知校验ID
	private String sign_type;// 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA
	private String sign;// 签名
	private String trade_no;// 支付宝交易号
	private String app_id;// 开发者的app_id
	private String out_trade_no;// 商户订单号
	private String out_biz_no;// 商户业务号 这个不一定有 主要是退款通知中返回退款申请的流水号
	private String buyer_id;// 买家支付宝用户号 否
	private String buyer_logon_id;// 买家支付宝账号 否
	private String seller_id;// 卖家支付宝用户号 否
	private String seller_email;// 卖家支付宝账号 否
	private String trade_status;// 交易状态 否 WAIT_BUYER_PAY/TRADE_CLOSED/TRADE_SUCCESS/TRADE_FINISHED
	private String total_amount;// 订单金额 元 否
	private String receipt_amount;// 实收金额 元 否
	private String invoice_amount;// 开票金额 元 否
	private String buyer_pay_amount;// 付款金额 元 否
	private String send_back_fee;// 实际退款金额 元 否
	private String subject;// 订单标题 否
	private String body;// 商品描述 否
	private String gmt_create;// 交易创建时间 否
	private String gmt_payment;// 交易付款时间 否
	private String gmt_refund;// 交易退款时间 否
	private String gmt_close;// 交易结束时间 否
	private String fund_bill_list;// 支付金额信息 否

	public NotifyUrlModel() {
		super();
	}

	public NotifyUrlModel(String notify_time, String notify_type, String notify_id, String sign_type, String sign,
			String trade_no, String app_id, String out_trade_no, String out_biz_no, String buyer_id,
			String buyer_logon_id, String seller_id, String seller_email, String trade_status, String total_amount,
			String receipt_amount, String invoice_amount, String buyer_pay_amount, String send_back_fee, String subject,
			String body, String gmt_create, String gmt_payment, String gmt_refund, String gmt_close,
			String fund_bill_list) {
		super();
		this.notify_time = notify_time;
		this.notify_type = notify_type;
		this.notify_id = notify_id;
		this.sign_type = sign_type;
		this.sign = sign;
		this.trade_no = trade_no;
		this.app_id = app_id;
		this.out_trade_no = out_trade_no;
		this.out_biz_no = out_biz_no;
		this.buyer_id = buyer_id;
		this.buyer_logon_id = buyer_logon_id;
		this.seller_id = seller_id;
		this.seller_email = seller_email;
		this.trade_status = trade_status;
		this.total_amount = total_amount;
		this.receipt_amount = receipt_amount;
		this.invoice_amount = invoice_amount;
		this.buyer_pay_amount = buyer_pay_amount;
		this.send_back_fee = send_back_fee;
		this.subject = subject;
		this.body = body;
		this.gmt_create = gmt_create;
		this.gmt_payment = gmt_payment;
		this.gmt_refund = gmt_refund;
		this.gmt_close = gmt_close;
		this.fund_bill_list = fund_bill_list;
	}

	public String getNotify_time() {
		return notify_time;
	}

	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}

	public String getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}

	public String getNotify_id() {
		return notify_id;
	}

	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOut_biz_no() {
		return out_biz_no;
	}

	public void setOut_biz_no(String out_biz_no) {
		this.out_biz_no = out_biz_no;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getBuyer_logon_id() {
		return buyer_logon_id;
	}

	public void setBuyer_logon_id(String buyer_logon_id) {
		this.buyer_logon_id = buyer_logon_id;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getReceipt_amount() {
		return receipt_amount;
	}

	public void setReceipt_amount(String receipt_amount) {
		this.receipt_amount = receipt_amount;
	}

	public String getInvoice_amount() {
		return invoice_amount;
	}

	public void setInvoice_amount(String invoice_amount) {
		this.invoice_amount = invoice_amount;
	}

	public String getBuyer_pay_amount() {
		return buyer_pay_amount;
	}

	public void setBuyer_pay_amount(String buyer_pay_amount) {
		this.buyer_pay_amount = buyer_pay_amount;
	}

	public String getSend_back_fee() {
		return send_back_fee;
	}

	public void setSend_back_fee(String send_back_fee) {
		this.send_back_fee = send_back_fee;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}

	public String getGmt_payment() {
		return gmt_payment;
	}

	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}

	public String getGmt_refund() {
		return gmt_refund;
	}

	public void setGmt_refund(String gmt_refund) {
		this.gmt_refund = gmt_refund;
	}

	public String getGmt_close() {
		return gmt_close;
	}

	public void setGmt_close(String gmt_close) {
		this.gmt_close = gmt_close;
	}

	public String getFund_bill_list() {
		return fund_bill_list;
	}

	public void setFund_bill_list(String fund_bill_list) {
		this.fund_bill_list = fund_bill_list;
	}

	/**
	 * 交易状态
	 * 
	 * @author weichengz
	 * @date 2019年10月25日 上午10:20:07
	 */
	public enum TradeStatusEnum {
		BTN("WAIT_BUYER_PAY", "交易创建，等待买家付款"), MENU("TRADE_CLOSED", "未付款交易超时关闭，或支付完成后全额退款"), MEAL("TRADE_SUCCESS",
				"交易支付成功"), FEMEAL("TRADE_FINISHED", "交易结束，不可退款");

		private String k;
		private String val;

		private static Map<String, String> map = new HashMap<String, String>();
		static {
			for (TradeStatusEnum s : TradeStatusEnum.values()) {
				map.put(s.getK(), s.getVal());
			}
		}

		private TradeStatusEnum(String k, String val) {
			this.k = k;
			this.val = val;
		}

		public String getK() {
			return k;
		}

		public void setK(String k) {
			this.k = k;
		}

		public String getVal() {
			return val;
		}

		public void setVal(String val) {
			this.val = val;
		}

		public static String valOf(String k) {
			for (DicType s : DicType.values()) {
				if (s.getk().equals(k)) {
					return s.getval();
				}
			}
			return null;
		}

		public static Map<String, String> map() {
			return map;
		}

		public static DicType enumOf(String k) {
			for (DicType s : DicType.values()) {
				if (s.getk().equals(k)) {
					return s;
				}
			}
			return null;
		}

	}

}
