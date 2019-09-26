package com.boot.kaizen.websocket;

import java.util.Date;

/**
 * websocket输入信息
 * 
 * @author weichengz
 * @date 2019年9月16日 下午5:32:46
 */
public class InMessage {

	//消息来自谁
	private String from;
	//发送给谁
	private String to;
	//消息的内容
	private String content;
	//日期
	private Date time=new Date();

	public InMessage(String from, String to, String content, Date time) {
		this.from = from;
		this.to = to;
		this.content = content;
		this.time = time;
	}

	public InMessage() {
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
