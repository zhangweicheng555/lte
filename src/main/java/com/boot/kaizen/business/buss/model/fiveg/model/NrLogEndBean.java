package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;

/**
 * log文件尾
 * @author weichengz
 * @date 2020年5月6日 上午10:08:23
 */
public class NrLogEndBean implements Serializable{
   
	private static final long serialVersionUID = 1L;
	
	private NrwxzbBean nrwxzbBean;
    private LtewxzbBean ltewxzbBean;
    private FtpzbBean ftpzbBean;
    private YyzbBean yyzbBean;
    private PingzbBean pingzbBean;
    private HttpzbBean httpzbBean;

    public HttpzbBean getHttpzbBean() {
        return httpzbBean;
    }

    public void setHttpzbBean(HttpzbBean httpzbBean) {
        this.httpzbBean = httpzbBean;
    }

    public PingzbBean getPingzbBean() {
        return pingzbBean;
    }

    public void setPingzbBean(PingzbBean pingzbBean) {
        this.pingzbBean = pingzbBean;
    }

    public YyzbBean getYyzbBean() {
        return yyzbBean;
    }

    public void setYyzbBean(YyzbBean yyzbBean) {
        this.yyzbBean = yyzbBean;
    }

    public FtpzbBean getFtpzbBean() {
        return ftpzbBean;
    }

    public void setFtpzbBean(FtpzbBean ftpzbBean) {
        this.ftpzbBean = ftpzbBean;
    }

    public NrwxzbBean getNrwxzbBean() {
        return nrwxzbBean;
    }

    public void setNrwxzbBean(NrwxzbBean nrwxzbBean) {
        this.nrwxzbBean = nrwxzbBean;
    }

    public LtewxzbBean getLtewxzbBean() {
        return ltewxzbBean;
    }

    public void setLtewxzbBean(LtewxzbBean ltewxzbBean) {
        this.ltewxzbBean = ltewxzbBean;
    }

	public NrLogEndBean(NrwxzbBean nrwxzbBean, LtewxzbBean ltewxzbBean, FtpzbBean ftpzbBean, YyzbBean yyzbBean,
			PingzbBean pingzbBean, HttpzbBean httpzbBean) {
		super();
		this.nrwxzbBean = nrwxzbBean;
		this.ltewxzbBean = ltewxzbBean;
		this.ftpzbBean = ftpzbBean;
		this.yyzbBean = yyzbBean;
		this.pingzbBean = pingzbBean;
		this.httpzbBean = httpzbBean;
	}

	public NrLogEndBean() {
		super();
	}
    
}
