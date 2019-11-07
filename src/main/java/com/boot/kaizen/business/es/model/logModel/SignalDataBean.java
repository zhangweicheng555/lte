package com.boot.kaizen.business.es.model.logModel;

import java.util.ArrayList;

/**
 * 主题序列化实体类
 * 
 * @author weichengz
 * @date 2019年11月4日 下午2:42:53
 */
public class SignalDataBean {

	private String id;// 自定义编号ID 注意这个非log上传信息   logmain

	private String latitude;// 纬度
	private String longitude;// 经度
	private String sinr;
	private String rsrp;
	private String CellName;
	private String CI;
	private String CELLID;
	private String NetWorkType;
	private String TAC;
	private String ENB;
	private String SPEED;
	private String downLoadSpeed;
	private String upLoadSpeed;
	private String normalEventType;// 业务事件正常类型
	private String abNormalEventType;// 业务事件异常类型
	private int eventType;// 事件，0没有，1http失败，2ping失败，3ftp连接失败，4ftp掉线，5语音未接通，6语音掉线
	private String siteLat;
	private String siteLng;
	private int angle;// 角度
	private String pci;
	private String earfcn;
	private String testTime;
	/* 0:上传和下载 1:上传 2:下载 */
	private int ftpType = -1;
	private OurDoorDataInfoBean doorDataInfoBeans;   
	
	private ArrayList<MSignaEventBean> mSignaEventBean;// 一秒钟事件   logevent
	private ArrayList<MSignaBean> mSignaBean;// 一秒钟信令     logmsg
	
	
	
	
	
	
	private PingzbBean pingzbBean;   //logother
	private HttpzbBean httpzbBean;
	private FtpzbBean ftpzbBean;
	private YyzbBean Yyzbbean;
	private WxzbBean wxzbBean;
	private PowerBean powerBean;
	private ZbBean zbBean;// 指标参数
	private ProIndicators proIndicators;// 专业指标

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNormalEventType() {
		return normalEventType;
	}

	public void setNormalEventType(String normalEventType) {
		this.normalEventType = normalEventType;
	}

	public String getAbNormalEventType() {
		return abNormalEventType;
	}

	public void setAbNormalEventType(String abNormalEventType) {
		this.abNormalEventType = abNormalEventType;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public int getFtpType() {
		return ftpType;
	}

	public void setFtpType(int ftpType) {
		this.ftpType = ftpType;
	}

	public ZbBean getZbBean() {
		return zbBean;
	}

	public void setZbBean(ZbBean zbBean) {
		this.zbBean = zbBean;
	}

	public PowerBean getPowerBean() {
		return powerBean;
	}

	public void setPowerBean(PowerBean powerBean) {
		this.powerBean = powerBean;
	}

	public ProIndicators getProIndicators() {
		return proIndicators;
	}

	public void setProIndicators(ProIndicators proIndicators) {
		this.proIndicators = proIndicators;
	}

	public ArrayList<MSignaEventBean> getmSignaEventBean() {
		return mSignaEventBean;
	}

	public void setmSignaEventBean(ArrayList<MSignaEventBean> mSignaEventBean) {
		this.mSignaEventBean = mSignaEventBean;
	}

	public ArrayList<MSignaBean> getmSignaBean() {
		return mSignaBean;
	}

	public void setmSignaBean(ArrayList<MSignaBean> mSignaBean) {
		this.mSignaBean = mSignaBean;
	}

	public OurDoorDataInfoBean getDoorDataInfoBeans() {
		return doorDataInfoBeans;
	}

	public void setDoorDataInfoBeans(OurDoorDataInfoBean doorDataInfoBeans) {
		this.doorDataInfoBeans = doorDataInfoBeans;
	}

	public PingzbBean getPingzbBean() {
		return pingzbBean;
	}

	public void setPingzbBean(PingzbBean pingzbBean) {
		this.pingzbBean = pingzbBean;
	}

	public HttpzbBean getHttpzbBean() {
		return httpzbBean;
	}

	public void setHttpzbBean(HttpzbBean httpzbBean) {
		this.httpzbBean = httpzbBean;
	}

	public FtpzbBean getFtpzbBean() {
		return ftpzbBean;
	}

	public void setFtpzbBean(FtpzbBean ftpzbBean) {
		this.ftpzbBean = ftpzbBean;
	}

	public YyzbBean getYyzbbean() {
		return Yyzbbean;
	}

	public void setYyzbbean(YyzbBean yyzbbean) {
		Yyzbbean = yyzbbean;
	}

	public WxzbBean getWxzbBean() {
		return wxzbBean;
	}

	public void setWxzbBean(WxzbBean wxzbBean) {
		this.wxzbBean = wxzbBean;
	}

	public String getPci() {
		return pci;
	}

	public void setPci(String pci) {
		this.pci = pci;
	}

	public String getEarfcn() {
		return earfcn;
	}

	public void setEarfcn(String earfcn) {
		this.earfcn = earfcn;
	}

	public String getDownLoadSpeed() {
		return downLoadSpeed;
	}

	public void setDownLoadSpeed(String downLoadSpeed) {
		this.downLoadSpeed = downLoadSpeed;
	}

	public String getUpLoadSpeed() {
		return upLoadSpeed;
	}

	public void setUpLoadSpeed(String upLoadSpeed) {
		this.upLoadSpeed = upLoadSpeed;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public String getCellName() {
		return CellName;
	}

	public void setCellName(String cellName) {
		CellName = cellName;
	}

	public String getCI() {
		return CI;
	}

	public void setCI(String CI) {
		this.CI = CI;
	}

	public String getCELLID() {
		return CELLID;
	}

	public void setCELLID(String CELLID) {
		this.CELLID = CELLID;
	}

	public String getNetWorkType() {
		return NetWorkType;
	}

	public void setNetWorkType(String netWorkType) {
		NetWorkType = netWorkType;
	}

	public String getSinr() {
		return sinr;
	}

	public void setSinr(String sinr) {
		this.sinr = sinr;
	}

	public String getRsrp() {
		return rsrp;
	}

	public void setRsrp(String rsrp) {
		this.rsrp = rsrp;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTAC() {
		return TAC;
	}

	public void setTAC(String TAC) {
		this.TAC = TAC;
	}

	public String getENB() {
		return ENB;
	}

	public void setENB(String ENB) {
		this.ENB = ENB;
	}

	public String getSPEED() {
		return SPEED;
	}

	public void setSPEED(String SPEED) {
		this.SPEED = SPEED;
	}

	public String getSiteLat() {
		return siteLat;
	}

	public void setSiteLat(String siteLat) {
		this.siteLat = siteLat;
	}

	public String getSiteLng() {
		return siteLng;
	}

	public void setSiteLng(String siteLng) {
		this.siteLng = siteLng;
	}
}
