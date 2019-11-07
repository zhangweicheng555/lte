package com.boot.kaizen.business.es.model;
import java.util.ArrayList;
import com.boot.kaizen.business.es.model.logModel.MSignaBean;
import com.boot.kaizen.business.es.model.logModel.SignalDataBean;

/**
 * log日志的【信令】信息 logmsg
 * 
 * @author weichengz
 * @date 2019年11月5日 下午1:47:43
 */
public class MsgLogModel {

	private String pid;// 主日志信息的id

	private ArrayList<MSignaBean> mSignaBean;// 一秒钟信令 

	public MsgLogModel(SignalDataBean signalDataBean) {
		this.pid = signalDataBean.getId();
		this.mSignaBean = signalDataBean.getmSignaBean();
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public MsgLogModel(String pid, ArrayList<MSignaBean> mSignaBean) {
		super();
		this.pid = pid;
		this.mSignaBean = mSignaBean;
	}

	public ArrayList<MSignaBean> getmSignaBean() {
		return mSignaBean;
	}

	public void setmSignaBean(ArrayList<MSignaBean> mSignaBean) {
		this.mSignaBean = mSignaBean;
	}

	public MsgLogModel() {
		super();
	}

}
