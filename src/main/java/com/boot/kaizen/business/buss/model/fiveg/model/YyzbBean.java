package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;

/**
 * 
 * @author weichengz
 * @date 2020年5月6日 上午10:16:36
 */
public class YyzbBean implements Serializable{
    
	private static final long serialVersionUID = 1L;
	private String hjsyd;//呼叫时延最大（s）
    private String hjsyx;//呼叫时延最小（s）
    private String hjsyp;//呼叫时延平均（s）
    private String zjcs;//主叫次数
    private String bjcs;//被叫次数
    private String dhcs;//掉话次数
    private String jtl;//接通率
    private String dhl;//掉话率
    private String jtcs;//接通次数

    public String getHjsyd() {
        return hjsyd;
    }

    public void setHjsyd(String hjsyd) {
        this.hjsyd = hjsyd;
    }

    public String getHjsyx() {
        return hjsyx;
    }

    public void setHjsyx(String hjsyx) {
        this.hjsyx = hjsyx;
    }

    public String getHjsyp() {
        return hjsyp;
    }

    public void setHjsyp(String hjsyp) {
        this.hjsyp = hjsyp;
    }

    public String getZjcs() {
        return zjcs;
    }

    public void setZjcs(String zjcs) {
        this.zjcs = zjcs;
    }

    public String getBjcs() {
        return bjcs;
    }

    public void setBjcs(String bjcs) {
        this.bjcs = bjcs;
    }

    public String getDhcs() {
        return dhcs;
    }

    public void setDhcs(String dhcs) {
        this.dhcs = dhcs;
    }

    public String getJtl() {
        return jtl;
    }

    public void setJtl(String jtl) {
        this.jtl = jtl;
    }

    public String getDhl() {
        return dhl;
    }

    public void setDhl(String dhl) {
        this.dhl = dhl;
    }

    public String getJtcs() {
        return jtcs;
    }

    public void setJtcs(String jtcs) {
        this.jtcs = jtcs;
    }
}
