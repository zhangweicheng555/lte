package com.boot.kaizen.business.es.model.logModel;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.boot.kaizen.util.LngLatUtil;

/**
 * 室外测试日志的格式 实体类 /这个只接受不存储
 * 
 * @author weichengz
 * @date 2019年11月4日 下午2:42:53
 */
public class SignalDataBean {

	private String pid;// 室外测试列表的id 根据这个找主log信息
	private String id;// 自定义编号ID 注意这个非log上传信息 logmain
	private String latitude;// 纬度
	private String longitude;// 经度
	private String sinr;
	private String rsrp;
	private String cellName;
	private String cI;
	
	private String eCI; // 新增

	private String cELLID;
	private String netWorkType;
	private String tAC;
	private String eNB;
	private String sPEED;
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

	private ArrayList<MSignaEventBean> mSignaEventBean;// 一秒钟事件 logevent
	private ArrayList<MSignaBean> mSignaBean;// 一秒钟信令 logmsg

	private PingzbBean pingzbBean; // ping指标4
	private HttpzbBean httpzbBean;   //http指标4
	
	private FtpzbBean ftpzbBean;   //ftp指标2
	private YyzbBean yyzbbean;  //语音指标  3
	private WxzbBean wxzbBean;//无限指标  1
	private PowerBean powerBean;//专业指标  1
	private ZbBean zbBean;// 指标参数  1
	private ProIndicators proIndicators;// 
	
	private int rootSupport=0;// 是否root  默认是0
	
	
	
	public int getRootSupport() {
		return rootSupport;
	}

	public void setRootSupport(int rootSupport) {
		this.rootSupport = rootSupport;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getId() {
		return id;
	}

	public String geteCI() {
		return eCI;
	}

	public void seteCI(String eCI) {
		this.eCI = eCI;
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
		return yyzbbean;
	}

	public void setYyzbbean(YyzbBean yyzbbean) {
		this.yyzbbean = yyzbbean;
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
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getcI() {
		return cI;
	}

	public void setcI(String cI) {
		this.cI = cI;
	}

	public String getcELLID() {
		return cELLID;
	}

	public void setcELLID(String cELLID) {
		this.cELLID = cELLID;
	}

	public String getNetWorkType() {
		return netWorkType;
	}

	public void setNetWorkType(String netWorkType) {
		this.netWorkType = netWorkType;
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

	public String gettAC() {
		return tAC;
	}

	public void settAC(String tAC) {
		this.tAC = tAC;
	}

	public String geteNB() {
		return eNB;
	}

	public void seteNB(String eNB) {
		this.eNB = eNB;
	}

	public String getsPEED() {
		return sPEED;
	}

	public void setsPEED(String sPEED) {
		this.sPEED = sPEED;
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

	public SignalDataBean(String pid, String id, String latitude, String longitude, String sinr, String rsrp,
			String cellName, String cI, String cELLID, String netWorkType, String tAC, String eNB, String sPEED,
			String downLoadSpeed, String upLoadSpeed, String normalEventType, String abNormalEventType, int eventType,
			String siteLat, String siteLng, int angle, String pci, String earfcn, String testTime, int ftpType,
			OurDoorDataInfoBean doorDataInfoBeans, ArrayList<MSignaEventBean> mSignaEventBean,
			ArrayList<MSignaBean> mSignaBean, PingzbBean pingzbBean, HttpzbBean httpzbBean, FtpzbBean ftpzbBean,
			YyzbBean yyzbbean, WxzbBean wxzbBean, PowerBean powerBean, ZbBean zbBean, ProIndicators proIndicators) {
		super();
		this.pid = pid;
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sinr = sinr;
		this.rsrp = rsrp;
		this.cellName = cellName;
		this.cI = cI;
		this.cELLID = cELLID;
		this.netWorkType = netWorkType;
		this.tAC = tAC;
		this.eNB = eNB;
		this.sPEED = sPEED;
		this.downLoadSpeed = downLoadSpeed;
		this.upLoadSpeed = upLoadSpeed;
		this.normalEventType = normalEventType;
		this.abNormalEventType = abNormalEventType;
		this.eventType = eventType;
		this.siteLat = siteLat;
		this.siteLng = siteLng;
		this.angle = angle;
		this.pci = pci;
		this.earfcn = earfcn;
		this.testTime = testTime;
		this.ftpType = ftpType;
		this.doorDataInfoBeans = doorDataInfoBeans;
		this.mSignaEventBean = mSignaEventBean;
		this.mSignaBean = mSignaBean;
		this.pingzbBean = pingzbBean;
		this.httpzbBean = httpzbBean;
		this.ftpzbBean = ftpzbBean;
		this.yyzbbean = yyzbbean;
		this.wxzbBean = wxzbBean;
		this.powerBean = powerBean;
		this.zbBean = zbBean;
		this.proIndicators = proIndicators;
	}

	public SignalDataBean(String pid, String id, String latitude, String longitude, String sinr, String rsrp,
			String cellName, String cI, String eCI, String cELLID, String netWorkType, String tAC, String eNB,
			String sPEED, String downLoadSpeed, String upLoadSpeed, String normalEventType, String abNormalEventType,
			int eventType, String siteLat, String siteLng, int angle, String pci, String earfcn, String testTime,
			int ftpType, OurDoorDataInfoBean doorDataInfoBeans, ArrayList<MSignaEventBean> mSignaEventBean,
			ArrayList<MSignaBean> mSignaBean, PingzbBean pingzbBean, HttpzbBean httpzbBean, FtpzbBean ftpzbBean,
			YyzbBean yyzbbean, WxzbBean wxzbBean, PowerBean powerBean, ZbBean zbBean, ProIndicators proIndicators,
			int rootSupport) {
		super();
		this.pid = pid;
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sinr = sinr;
		this.rsrp = rsrp;
		this.cellName = cellName;
		this.cI = cI;
		this.eCI = eCI;
		this.cELLID = cELLID;
		this.netWorkType = netWorkType;
		this.tAC = tAC;
		this.eNB = eNB;
		this.sPEED = sPEED;
		this.downLoadSpeed = downLoadSpeed;
		this.upLoadSpeed = upLoadSpeed;
		this.normalEventType = normalEventType;
		this.abNormalEventType = abNormalEventType;
		this.eventType = eventType;
		this.siteLat = siteLat;
		this.siteLng = siteLng;
		this.angle = angle;
		this.pci = pci;
		this.earfcn = earfcn;
		this.testTime = testTime;
		this.ftpType = ftpType;
		this.doorDataInfoBeans = doorDataInfoBeans;
		this.mSignaEventBean = mSignaEventBean;
		this.mSignaBean = mSignaBean;
		this.pingzbBean = pingzbBean;
		this.httpzbBean = httpzbBean;
		this.ftpzbBean = ftpzbBean;
		this.yyzbbean = yyzbbean;
		this.wxzbBean = wxzbBean;
		this.powerBean = powerBean;
		this.zbBean = zbBean;
		this.proIndicators = proIndicators;
		this.rootSupport = rootSupport;
	}

	public void dealLngLatBdToWgs84() {
		if (StringUtils.isNotBlank(latitude) && StringUtils.isNotBlank(longitude)) {
			String bd2wgs84 = LngLatUtil.bd2wgs84(Double.valueOf(longitude), Double.valueOf(latitude));
			if (bd2wgs84 != null) {
				String[] split = bd2wgs84.split("_");
				if (split != null && split.length == 2) {
					latitude = split[1];
					longitude = split[0];
				}
			}
		}
	}

}
