package com.boot.kaizen.model;

/**
 * 字典管理
 * 
 * @author weichengz
 * @date 2018年10月3日 上午8:50:53
 */
public class SysDic extends BaseEntity<Long> {

	private static final long serialVersionUID = -6525908145032868837L;

	private String type;
	private String k;
	private String val;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public SysDic(String type, String k, String val) {
		super();
		this.type = type;
		this.k = k;
		this.val = val;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public SysDic() {
	}

}
