package com.boot.kaizen.websocket;

import java.util.Date;

/**
 * websocket输出信息
 * 
 * @author weichengz
 * @date 2019年9月16日 下午5:32:46
 */
public class OutMessage {

	private String from;
	private String content;
	private Date time=new Date();

	public OutMessage(String from,  String content, Date time) {
		this.from = from;
		this.content = content;
		this.time = time;
	}

	public OutMessage() {
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
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
