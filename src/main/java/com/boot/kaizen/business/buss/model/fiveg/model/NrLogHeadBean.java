package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;

/**
 * log文件头部信息
 * @author weichengz
 * @date 2020年5月6日 上午10:19:25
 */
public class NrLogHeadBean implements Serializable{
    
	private static final long serialVersionUID = 1L;
	private int logversion;//0: LTE 1: NR5G NSA
    private int dualSimSupport;//0: Single SIM 1: Dual SIM 针对双卡测试的判断 如果勾选双卡测试就是1 如果没勾选双卡测试就0
    private int operatorCompareSupport;//0: 非运营商竟对测试 1: 运营商竟对测试 针对竞对测试的 勾选了就是1 没勾选就是0
    private int rootSupport;//0: None Root1: Root
    private String phone;//手机型号
    private String operator;//运营商（主卡）
    private String operator_y;//运营商（副卡）

    public int getLogversion() {
        return logversion;
    }

    public void setLogversion(int logversion) {
        this.logversion = logversion;
    }

    public int getDualSimSupport() {
        return dualSimSupport;
    }

    public void setDualSimSupport(int dualSimSupport) {
        this.dualSimSupport = dualSimSupport;
    }

    public int getOperatorCompareSupport() {
        return operatorCompareSupport;
    }

    public void setOperatorCompareSupport(int operatorCompareSupport) {
        this.operatorCompareSupport = operatorCompareSupport;
    }

    public int getRootSupport() {
        return rootSupport;
    }

    public void setRootSupport(int rootSupport) {
        this.rootSupport = rootSupport;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator_y() {
        return operator_y;
    }

    public void setOperator_y(String operator_y) {
        this.operator_y = operator_y;
    }

	public NrLogHeadBean(int logversion, int dualSimSupport, int operatorCompareSupport, int rootSupport, String phone,
			String operator, String operator_y) {
		super();
		this.logversion = logversion;
		this.dualSimSupport = dualSimSupport;
		this.operatorCompareSupport = operatorCompareSupport;
		this.rootSupport = rootSupport;
		this.phone = phone;
		this.operator = operator;
		this.operator_y = operator_y;
	}

	public NrLogHeadBean() {
		super();
	}
    
}
