package com.boot.kaizen.business.buss.model.fiveg;

import java.io.Serializable;

/**
 * ping指标【对象】
 * 
 * @author weichengz
 * @date 2020年4月26日 上午10:16:16
 */
public class FootPingzbBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String qqcs;// 请求次数
	private String cgcs;// 成功次数
	private String cgl;// 成功率
	private String zdsy;// 最大时延（ms）
	private String zxsy;// 最小时延（ms）
	private String pjsy;// 平均时延（ms）

	public String getQqcs() {
		return qqcs;
	}

	public void setQqcs(String qqcs) {
		this.qqcs = qqcs;
	}

	public String getCgcs() {
		return cgcs;
	}

	public void setCgcs(String cgcs) {
		this.cgcs = cgcs;
	}

	public String getCgl() {
		return cgl;
	}

	public void setCgl(String cgl) {
		this.cgl = cgl;
	}

	public String getZdsy() {
		return zdsy;
	}

	public void setZdsy(String zdsy) {
		this.zdsy = zdsy;
	}

	public String getZxsy() {
		return zxsy;
	}

	public void setZxsy(String zxsy) {
		this.zxsy = zxsy;
	}

	public String getPjsy() {
		return pjsy;
	}

	public void setPjsy(String pjsy) {
		this.pjsy = pjsy;
	}

	public FootPingzbBean(String qqcs, String cgcs, String cgl, String zdsy, String zxsy, String pjsy) {
		super();
		this.qqcs = qqcs;
		this.cgcs = cgcs;
		this.cgl = cgl;
		this.zdsy = zdsy;
		this.zxsy = zxsy;
		this.pjsy = pjsy;
	}

	public FootPingzbBean() {
		super();
	}

}
