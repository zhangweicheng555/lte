package com.boot.kaizen.business.es.model;

import java.util.ArrayList;

import com.boot.kaizen.business.es.model.logModel.MSignaEventBean;
import com.boot.kaizen.business.es.model.logModel.SignalDataBean;

/**
 * log日志的 【事件】信息  logevent
 * 
 * @author weichengz
 * @date 2019年11月5日 下午1:47:43
 */
public class EventLogModel {

	private String pid;// 主日志信息的id
	private ArrayList<MSignaEventBean> mSignaEventBean;// 一秒钟事件 

	public EventLogModel() {
		super();
	}

	public EventLogModel(SignalDataBean signalDataBean) {
		super();
		this.pid = signalDataBean.getId();
		this.mSignaEventBean = signalDataBean.getmSignaEventBean();
	}

	public EventLogModel(String pid, ArrayList<MSignaEventBean> mSignaEventBean) {
		super();
		this.pid = pid;
		this.mSignaEventBean = mSignaEventBean;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public ArrayList<MSignaEventBean> getmSignaEventBean() {
		return mSignaEventBean;
	}

	public void setmSignaEventBean(ArrayList<MSignaEventBean> mSignaEventBean) {
		this.mSignaEventBean = mSignaEventBean;
	}

}
