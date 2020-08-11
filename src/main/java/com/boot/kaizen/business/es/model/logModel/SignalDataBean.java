package com.boot.kaizen.business.es.model.logModel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.boot.kaizen.business.buss.model.fiveg.FootFtpzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootHttpzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootLtewxzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootPingzbBean;
import com.boot.kaizen.business.buss.model.fiveg.FootYyzbBean;
import com.boot.kaizen.business.buss.model.fiveg.LogFoot;
import com.boot.kaizen.business.buss.model.fiveg.model.ComptestBean;
import com.boot.kaizen.business.buss.model.fiveg.model.LteDataInfoBean;
import com.boot.kaizen.business.buss.model.fiveg.model.LteNeighborhoodInfo;
import com.boot.kaizen.business.buss.model.fiveg.model.NrLogBodyBean;
import com.boot.kaizen.business.buss.model.fiveg.model.NrLogHeadBean;
import com.boot.kaizen.business.buss.model.fiveg.model.ProLteDataInfoBean;
import com.boot.kaizen.business.buss.model.fiveg.model.ProLteNeighborhoodInfo;
import com.boot.kaizen.business.buss.model.fiveg.model.SignalBean;
import com.boot.kaizen.business.buss.model.fiveg.model.SignalEventBean;
import com.boot.kaizen.util.LngLatUtil;

/**
 * 室外测试日志的格式 实体类 /这个只接受不存储 
 * 4g log格式
 * 
 * @author weichengz
 * @date 2019年11月4日 下午2:42:53
 */
public class SignalDataBean {

	private String pid;// 室外测试列表的id 根据这个找主log信息
	private String id;// 自定义编号ID 注意这个非log上传信息 logmain

	private int simTest;// 0.单卡测试 1.双卡测试
	private int competitiveTest;// 0.非竟对测试 1.竟对测试
	private int rootSupport;// 是否root 默认是0

	
	private int logversion;// 适配 权限相关的三个信息
	private String user;//
	private String city;//
	
	
	
	private String phone;//
	private String operator;//
	private String operator_v;//
	private String height;//
	private String cgi;//

	private String latitude;// 纬度
	private String longitude;// 经度
	private String sinr;
	private String sinr_v;
	private String ss_sinr;

	private String rsrp;
	private String ss_rsrp;
	private String rsrp_v;
	private String eCI; // 新增
	private String eCI_v; // 新增

	private String cellName;
	private String cellName_v;
	private String cI;

	private String cELLID;
	private String cELLID_v;

	private String netWorkType;
	private String netWorkType_v;
	private String tAC;
	private String tAC_v;
	private String eNB;
	private String eNB_v;
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
	private String pci_v;
	private String earfcn;
	private String earfcn_v;
	private String testTime;
	/* 0:上传和下载 1:上传 2:下载 */
	private int ftpType = -1;
	private OurDoorDataInfoBean doorDataInfoBeans;
	private DoorDataInfoViceBeans doorDataInfoViceBeans;

	private PingzbBean pingzbBean; // ping指标4
	private HttpzbBean httpzbBean; // http指标4

	private FtpzbBean ftpzbBean; // ftp指标2
	private YyzbBean yyzbbean; // 语音指标 3
	private WxzbBean wxzbBean;// 无限指标 1
	private WxzbViceBean wxzbViceBean;// 无限指标 1
	private PowerBean powerBean;// 专业指标 1
	private ZbBean zbBean;// 指标参数 1
	private ProIndicators proIndicators;//
	private ProIndicators5G proIndicators5G;//
	private ComptestBeans comptestBeans;//

	private ArrayList<MSignaEventBean> mSignaEventBean;// 一秒钟事件 logevent
	private ArrayList<MSignaBean> mSignaBean;// 一秒钟信令 logmsg

	public SignalDataBean() {
		super();
	}

	


	public SignalDataBean(String pid, String id, int simTest, int competitiveTest, int rootSupport, int logversion,
			String user, String city, String phone, String operator, String operator_v, String height, String cgi,
			String latitude, String longitude, String sinr, String sinr_v, String ss_sinr, String rsrp, String ss_rsrp,
			String rsrp_v, String eCI, String eCI_v, String cellName, String cellName_v, String cI, String cELLID,
			String cELLID_v, String netWorkType, String netWorkType_v, String tAC, String tAC_v, String eNB,
			String eNB_v, String sPEED, String downLoadSpeed, String upLoadSpeed, String normalEventType,
			String abNormalEventType, int eventType, String siteLat, String siteLng, int angle, String pci,
			String pci_v, String earfcn, String earfcn_v, String testTime, int ftpType,
			OurDoorDataInfoBean doorDataInfoBeans, DoorDataInfoViceBeans doorDataInfoViceBeans, PingzbBean pingzbBean,
			HttpzbBean httpzbBean, FtpzbBean ftpzbBean, YyzbBean yyzbbean, WxzbBean wxzbBean, WxzbViceBean wxzbViceBean,
			PowerBean powerBean, ZbBean zbBean, ProIndicators proIndicators, ProIndicators5G proIndicators5G,
			ComptestBeans comptestBeans, ArrayList<MSignaEventBean> mSignaEventBean, ArrayList<MSignaBean> mSignaBean) {
		super();
		this.pid = pid;
		this.id = id;
		this.simTest = simTest;
		this.competitiveTest = competitiveTest;
		this.rootSupport = rootSupport;
		this.logversion = logversion;
		this.user = user;
		this.city = city;
		this.phone = phone;
		this.operator = operator;
		this.operator_v = operator_v;
		this.height = height;
		this.cgi = cgi;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sinr = sinr;
		this.sinr_v = sinr_v;
		this.ss_sinr = ss_sinr;
		this.rsrp = rsrp;
		this.ss_rsrp = ss_rsrp;
		this.rsrp_v = rsrp_v;
		this.eCI = eCI;
		this.eCI_v = eCI_v;
		this.cellName = cellName;
		this.cellName_v = cellName_v;
		this.cI = cI;
		this.cELLID = cELLID;
		this.cELLID_v = cELLID_v;
		this.netWorkType = netWorkType;
		this.netWorkType_v = netWorkType_v;
		this.tAC = tAC;
		this.tAC_v = tAC_v;
		this.eNB = eNB;
		this.eNB_v = eNB_v;
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
		this.pci_v = pci_v;
		this.earfcn = earfcn;
		this.earfcn_v = earfcn_v;
		this.testTime = testTime;
		this.ftpType = ftpType;
		this.doorDataInfoBeans = doorDataInfoBeans;
		this.doorDataInfoViceBeans = doorDataInfoViceBeans;
		this.pingzbBean = pingzbBean;
		this.httpzbBean = httpzbBean;
		this.ftpzbBean = ftpzbBean;
		this.yyzbbean = yyzbbean;
		this.wxzbBean = wxzbBean;
		this.wxzbViceBean = wxzbViceBean;
		this.powerBean = powerBean;
		this.zbBean = zbBean;
		this.proIndicators = proIndicators;
		this.proIndicators5G = proIndicators5G;
		this.comptestBeans = comptestBeans;
		this.mSignaEventBean = mSignaEventBean;
		this.mSignaBean = mSignaBean;
	}




	public String getCellName_v() {
		return cellName_v;
	}




	public void setCellName_v(String cellName_v) {
		this.cellName_v = cellName_v;
	}




	public int getLogversion() {
		return logversion;
	}


	public void setLogversion(int logversion) {
		this.logversion = logversion;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public SignalDataBean(String pid, String id, int simTest, int competitiveTest, int rootSupport, String phone,
			String operator, String operator_v, String height, String cgi, String latitude, String longitude,
			String sinr, String sinr_v, String ss_sinr, String rsrp, String ss_rsrp, String rsrp_v, String eCI,
			String eCI_v, String cellName, String cI, String cELLID, String cELLID_v, String netWorkType,
			String netWorkType_v, String tAC, String tAC_v, String eNB, String eNB_v, String sPEED,
			String downLoadSpeed, String upLoadSpeed, String normalEventType, String abNormalEventType, int eventType,
			String siteLat, String siteLng, int angle, String pci, String pci_v, String earfcn, String earfcn_v,
			String testTime, int ftpType, OurDoorDataInfoBean doorDataInfoBeans,
			DoorDataInfoViceBeans doorDataInfoViceBeans, PingzbBean pingzbBean, HttpzbBean httpzbBean,
			FtpzbBean ftpzbBean, YyzbBean yyzbbean, WxzbBean wxzbBean, WxzbViceBean wxzbViceBean, PowerBean powerBean,
			ZbBean zbBean, ProIndicators proIndicators, ProIndicators5G proIndicators5G, ComptestBeans comptestBeans,
			ArrayList<MSignaEventBean> mSignaEventBean, ArrayList<MSignaBean> mSignaBean) {
		super();
		this.pid = pid;
		this.id = id;
		this.simTest = simTest;
		this.competitiveTest = competitiveTest;
		this.rootSupport = rootSupport;
		this.phone = phone;
		this.operator = operator;
		this.operator_v = operator_v;
		this.height = height;
		this.cgi = cgi;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sinr = sinr;
		this.sinr_v = sinr_v;
		this.ss_sinr = ss_sinr;
		this.rsrp = rsrp;
		this.ss_rsrp = ss_rsrp;
		this.rsrp_v = rsrp_v;
		this.eCI = eCI;
		this.eCI_v = eCI_v;
		this.cellName = cellName;
		this.cI = cI;
		this.cELLID = cELLID;
		this.cELLID_v = cELLID_v;
		this.netWorkType = netWorkType;
		this.netWorkType_v = netWorkType_v;
		this.tAC = tAC;
		this.tAC_v = tAC_v;
		this.eNB = eNB;
		this.eNB_v = eNB_v;
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
		this.pci_v = pci_v;
		this.earfcn = earfcn;
		this.earfcn_v = earfcn_v;
		this.testTime = testTime;
		this.ftpType = ftpType;
		this.doorDataInfoBeans = doorDataInfoBeans;
		this.doorDataInfoViceBeans = doorDataInfoViceBeans;
		this.pingzbBean = pingzbBean;
		this.httpzbBean = httpzbBean;
		this.ftpzbBean = ftpzbBean;
		this.yyzbbean = yyzbbean;
		this.wxzbBean = wxzbBean;
		this.wxzbViceBean = wxzbViceBean;
		this.powerBean = powerBean;
		this.zbBean = zbBean;
		this.proIndicators = proIndicators;
		this.proIndicators5G = proIndicators5G;
		this.comptestBeans = comptestBeans;
		this.mSignaEventBean = mSignaEventBean;
		this.mSignaBean = mSignaBean;
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

	public void setId(String id) {
		this.id = id;
	}

	public int getSimTest() {
		return simTest;
	}

	public void setSimTest(int simTest) {
		this.simTest = simTest;
	}

	public int getCompetitiveTest() {
		return competitiveTest;
	}

	public void setCompetitiveTest(int competitiveTest) {
		this.competitiveTest = competitiveTest;
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

	public String getOperator_v() {
		return operator_v;
	}

	public void setOperator_v(String operator_v) {
		this.operator_v = operator_v;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getCgi() {
		return cgi;
	}

	public void setCgi(String cgi) {
		this.cgi = cgi;
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

	public String getSinr() {
		return sinr;
	}

	public void setSinr(String sinr) {
		this.sinr = sinr;
	}

	public String getSinr_v() {
		return sinr_v;
	}

	public void setSinr_v(String sinr_v) {
		this.sinr_v = sinr_v;
	}

	public String getSs_sinr() {
		return ss_sinr;
	}

	public void setSs_sinr(String ss_sinr) {
		this.ss_sinr = ss_sinr;
	}

	public String getRsrp() {
		return rsrp;
	}

	public void setRsrp(String rsrp) {
		this.rsrp = rsrp;
	}

	public String getSs_rsrp() {
		return ss_rsrp;
	}

	public void setSs_rsrp(String ss_rsrp) {
		this.ss_rsrp = ss_rsrp;
	}

	public String getRsrp_v() {
		return rsrp_v;
	}

	public void setRsrp_v(String rsrp_v) {
		this.rsrp_v = rsrp_v;
	}

	public String geteCI() {
		return eCI;
	}

	public void seteCI(String eCI) {
		this.eCI = eCI;
	}

	public String geteCI_v() {
		return eCI_v;
	}

	public void seteCI_v(String eCI_v) {
		this.eCI_v = eCI_v;
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

	public String getcELLID_v() {
		return cELLID_v;
	}

	public void setcELLID_v(String cELLID_v) {
		this.cELLID_v = cELLID_v;
	}

	public String getNetWorkType() {
		return netWorkType;
	}

	public void setNetWorkType(String netWorkType) {
		this.netWorkType = netWorkType;
	}

	public String getNetWorkType_v() {
		return netWorkType_v;
	}

	public void setNetWorkType_v(String netWorkType_v) {
		this.netWorkType_v = netWorkType_v;
	}

	public String gettAC() {
		return tAC;
	}

	public void settAC(String tAC) {
		this.tAC = tAC;
	}

	public String gettAC_v() {
		return tAC_v;
	}

	public void settAC_v(String tAC_v) {
		this.tAC_v = tAC_v;
	}

	public String geteNB() {
		return eNB;
	}

	public void seteNB(String eNB) {
		this.eNB = eNB;
	}

	public String geteNB_v() {
		return eNB_v;
	}

	public void seteNB_v(String eNB_v) {
		this.eNB_v = eNB_v;
	}

	public String getsPEED() {
		return sPEED;
	}

	public void setsPEED(String sPEED) {
		this.sPEED = sPEED;
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

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
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

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public String getPci() {
		return pci;
	}

	public void setPci(String pci) {
		this.pci = pci;
	}

	public String getPci_v() {
		return pci_v;
	}

	public void setPci_v(String pci_v) {
		this.pci_v = pci_v;
	}

	public String getEarfcn() {
		return earfcn;
	}

	public void setEarfcn(String earfcn) {
		this.earfcn = earfcn;
	}

	public String getEarfcn_v() {
		return earfcn_v;
	}

	public void setEarfcn_v(String earfcn_v) {
		this.earfcn_v = earfcn_v;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public int getFtpType() {
		return ftpType;
	}

	public void setFtpType(int ftpType) {
		this.ftpType = ftpType;
	}

	public OurDoorDataInfoBean getDoorDataInfoBeans() {
		return doorDataInfoBeans;
	}

	public void setDoorDataInfoBeans(OurDoorDataInfoBean doorDataInfoBeans) {
		this.doorDataInfoBeans = doorDataInfoBeans;
	}

	public DoorDataInfoViceBeans getDoorDataInfoViceBeans() {
		return doorDataInfoViceBeans;
	}

	public void setDoorDataInfoViceBeans(DoorDataInfoViceBeans doorDataInfoViceBeans) {
		this.doorDataInfoViceBeans = doorDataInfoViceBeans;
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

	public WxzbViceBean getWxzbViceBean() {
		return wxzbViceBean;
	}

	public void setWxzbViceBean(WxzbViceBean wxzbViceBean) {
		this.wxzbViceBean = wxzbViceBean;
	}

	public PowerBean getPowerBean() {
		return powerBean;
	}

	public void setPowerBean(PowerBean powerBean) {
		this.powerBean = powerBean;
	}

	public ZbBean getZbBean() {
		return zbBean;
	}

	public void setZbBean(ZbBean zbBean) {
		this.zbBean = zbBean;
	}

	public ProIndicators getProIndicators() {
		return proIndicators;
	}

	public void setProIndicators(ProIndicators proIndicators) {
		this.proIndicators = proIndicators;
	}

	public ProIndicators5G getProIndicators5G() {
		return proIndicators5G;
	}

	public void setProIndicators5G(ProIndicators5G proIndicators5G) {
		this.proIndicators5G = proIndicators5G;
	}

	public ComptestBeans getComptestBeans() {
		return comptestBeans;
	}

	public void setComptestBeans(ComptestBeans comptestBeans) {
		this.comptestBeans = comptestBeans;
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

	/**
	 * 将4glog信息转为5g的头部信息
	* @Description: TODO
	* @author weichengz
	* @date 2020年7月20日 下午4:04:30
	 */
	public NrLogHeadBean toNrLogHeadBean() {
		NrLogHeadBean model=new NrLogHeadBean();
		model.setLogversion(this.logversion);
		model.setDualSimSupport(this.simTest);
		model.setOperatorCompareSupport(this.competitiveTest);
		model.setRootSupport(this.rootSupport);
		model.setPhone(this.phone);
		model.setUser(this.user);
		model.setCity(this.city);
		model.setOperator(this.operator);
		model.setOperator_y(this.operator_v);
		return model;
	}


	/**
	 * 将4g的信息 转为5g的信息
	* @Description: TODO
	* @author weichengz
	* @date 2020年7月20日 下午4:15:39
	 */
	public NrLogBodyBean toNrLogBodyBean() {
		NrLogBodyBean nrLogBodyBean=new NrLogBodyBean();
		
		nrLogBodyBean.setLatitude(this.latitude);
		nrLogBodyBean.setLongitude(this.longitude);
		nrLogBodyBean.setSpeed(this.sPEED);
		nrLogBodyBean.setHeight(this.height);
		nrLogBodyBean.setTestTime(this.testTime);
		nrLogBodyBean.setDownLoadSpeed(this.downLoadSpeed);
		nrLogBodyBean.setUpLoadSpeed(this.upLoadSpeed);
		nrLogBodyBean.setNormalEventType(this.normalEventType);
		nrLogBodyBean.setAbNormalEventType(this.normalEventType);
		
		LteDataInfoBean lteDataInfoBean=new LteDataInfoBean();
		lteDataInfoBean.setLteTAC(this.tAC);
		lteDataInfoBean.setLteEARFCN(this.earfcn);
		lteDataInfoBean.setLtePCI(this.pci);
		lteDataInfoBean.setLteENB(this.eNB);
		lteDataInfoBean.setLteCellID(this.cELLID);
		lteDataInfoBean.setLteCellName(this.cellName);
		if (this.doorDataInfoBeans !=null) {
			lteDataInfoBean.setLteRSRP(doorDataInfoBeans.getRsrp());
			lteDataInfoBean.setLteRSRQ(doorDataInfoBeans.getRsrq());
			lteDataInfoBean.setLteRSSI(doorDataInfoBeans.getSinr()+"");
			
			List<LteNeighborhoodInfo> LteNeighborhoodInfos=new ArrayList<>();
			
			ArrayList<MLteNeighborhoodInfo> getmLteNeighborhoodInfos = doorDataInfoBeans.getmLteNeighborhoodInfos();
			if (getmLteNeighborhoodInfos !=null && getmLteNeighborhoodInfos.size()>0) {
				for (MLteNeighborhoodInfo mLteNeighborhoodInfo : getmLteNeighborhoodInfos) {
					LteNeighborhoodInfo model=new LteNeighborhoodInfo();
					model.setLteNeighbirhoodEARFCN(mLteNeighborhoodInfo.getmLteNeighborhoodEarfcn());
					model.setLteNeighbirhoodPCI(mLteNeighborhoodInfo.getmLteNeighborhoodPCI());
					model.setLteNeighbirhoodRSRP(mLteNeighborhoodInfo.getmLteNeighborhoodRSRPOrSINR());
					model.setLteNeighbirhoodRSRQ(mLteNeighborhoodInfo.getmLteNeighborhoodRsrq());
					LteNeighborhoodInfos.add(model);
				}
			}
			
			lteDataInfoBean.setLteNeighborhoodInfos(LteNeighborhoodInfos);
		}
		
		nrLogBodyBean.setLteDataInfoBean(lteDataInfoBean);
		
		
		LteDataInfoBean infoBean=new LteDataInfoBean();
		infoBean.setLteTAC(this.tAC_v);
		infoBean.setLteEARFCN(this.earfcn_v);
		infoBean.setLtePCI(this.pci_v);
		infoBean.setLteENB(this.eNB_v);
		infoBean.setLteCellID(this.cELLID_v);
		infoBean.setLteCellName(this.cellName_v);
		if (this.doorDataInfoViceBeans !=null) {
			infoBean.setLteRSRP(this.doorDataInfoViceBeans.getRsrp());
			infoBean.setLteRSRQ(this.doorDataInfoViceBeans.getRsrq());
			infoBean.setLteSINR(this.doorDataInfoViceBeans.getSinr()+"");
			List<LteNeighborhoodInfo> lteNeighborhoodInfos=new ArrayList<>();
			ArrayList<MLteNeighborhoodInfo> getmLteNeighborhoodInfos = this.doorDataInfoViceBeans.getmLteNeighborhoodInfos();
			if (getmLteNeighborhoodInfos !=null && getmLteNeighborhoodInfos.size()>0) {
				for (MLteNeighborhoodInfo mLteNeighborhoodInfo : getmLteNeighborhoodInfos) {
					LteNeighborhoodInfo modelLte=new LteNeighborhoodInfo();
					modelLte.setLteNeighbirhoodEARFCN(mLteNeighborhoodInfo.getmLteNeighborhoodEarfcn());
					modelLte.setLteNeighbirhoodPCI(mLteNeighborhoodInfo.getmLteNeighborhoodPCI());
					modelLte.setLteNeighbirhoodRSRP(mLteNeighborhoodInfo.getmLteNeighborhoodRSRPOrSINR());
					modelLte.setLteNeighbirhoodRSRQ(mLteNeighborhoodInfo.getmLteNeighborhoodRsrq());
					lteNeighborhoodInfos.add(modelLte);
				}
			}
			infoBean.setLteNeighborhoodInfos(lteNeighborhoodInfos);
		}
		
		nrLogBodyBean.setInfoBean(infoBean);
		
		
		List<ComptestBean> comptestBeans=new ArrayList<>();
		
		if (this.comptestBeans !=null) {
			ArrayList<MLteNeighborhoodInfo> getmLteNeighborhoodInfos = this.comptestBeans.getmLteNeighborhoodInfos();
			if (getmLteNeighborhoodInfos !=null && getmLteNeighborhoodInfos.size()>0) {
				for (MLteNeighborhoodInfo mLteNeighborhoodInfo : getmLteNeighborhoodInfos) {
					ComptestBean modelCom=new ComptestBean();
					modelCom.setmLteNeighborhoodEarfcn(mLteNeighborhoodInfo.getmLteNeighborhoodEarfcn());
					modelCom.setmLteNeighborhoodPCI(mLteNeighborhoodInfo.getmLteNeighborhoodPCI());
					modelCom.setmLteNeighborhoodRSRP(mLteNeighborhoodInfo.getmLteNeighborhoodRSRPOrSINR());
					modelCom.setmLteNeighborhoodRSRQ(mLteNeighborhoodInfo.getmLteNeighborhoodRsrq());
					modelCom.setSiteName(mLteNeighborhoodInfo.getSiteName());
					comptestBeans.add(modelCom);
				}
			}
		}
		
		nrLogBodyBean.setComptestBeans(comptestBeans);
		
		ProLteDataInfoBean proLteDataInfoBean=new ProLteDataInfoBean();
		if (this.proIndicators !=null) {
			proLteDataInfoBean.setServingCellPccMcc(this.proIndicators.getServingCellPccMcc());
			proLteDataInfoBean.setServingCellPccMnc(this.proIndicators.getServingCellPccMnc());
			proLteDataInfoBean.setServingCellPccSiteEci(this.proIndicators.getServingCellPccSiteEci());
			proLteDataInfoBean.setServingCellPccBandIndex(this.proIndicators.getServingCellPccBandIndex());
			proLteDataInfoBean.setServingCellPccBwDl(this.proIndicators.getServingCellPccBwDl());
			proLteDataInfoBean.setServingCellPccFreqDl(this.proIndicators.getServingCellPccFreqDl());
			proLteDataInfoBean.setServingCellPccTac(this.proIndicators.getServingCellPccTac());
			proLteDataInfoBean.setServingCellPccSsp("");
			proLteDataInfoBean.setServingCellPccSa("");
			proLteDataInfoBean.setServingCellPccCellId(this.proIndicators.getServingCellPccCellId());
			proLteDataInfoBean.setServingCellPccEarfcnDl(this.proIndicators.getServingCellPccEarfcnDl());
			proLteDataInfoBean.setServingCellPccPci(this.proIndicators.getServingCellPccPci());
			proLteDataInfoBean.setServingCellPccRsrp(this.proIndicators.getServingCellPccRsrp());
			proLteDataInfoBean.setServingCellPccSinr(this.proIndicators.getServingCellPccSinr());
			proLteDataInfoBean.setServingCellPccRsrq(this.proIndicators.getServingCellPccSinr());
			proLteDataInfoBean.setServingCellPccRssi(this.proIndicators.getServingCellPccRssi());
			proLteDataInfoBean.setServingCellPccPuschTxpower(this.proIndicators.getServingCellPccPuschTxpower());
			proLteDataInfoBean.setServingCellPccPucchTxpower(this.proIndicators.getServingCellPccPucchTxpower());
			proLteDataInfoBean.setServingCellPccEnodebId(this.proIndicators.getServingCellPccEnodebId());
			proLteDataInfoBean.setServingCellPccWidebandCqi(this.proIndicators.getServingCellPccWidebandCqi());
			proLteDataInfoBean.setServingCellPccMcsul("");
			proLteDataInfoBean.setServingCellPccMcsdl("");
			proLteDataInfoBean.setServingCellPccModul("");
			proLteDataInfoBean.setServingCellPccModdl("");
			proLteDataInfoBean.setServingCellPccULBLER(this.proIndicators.getServingCellPccULBLER());
			proLteDataInfoBean.setServingCellPccDLBLER(this.proIndicators.getServingCellPccDLBLER());
			proLteDataInfoBean.setServingCellPccGrantulnum("");
			proLteDataInfoBean.setServingCellPccGrantdlnum("");
			proLteDataInfoBean.setServingCellPccRankIndex(this.proIndicators.getServingCellPccRankIndex());
			proLteDataInfoBean.setThroughputPccPdcpUl(this.proIndicators.getThroughputPccPdcpUl());
			proLteDataInfoBean.setThroughputPccPdcpDl(this.proIndicators.getThroughputPccPdcpDl());
			proLteDataInfoBean.setThroughputPccRlcUl(this.proIndicators.getThroughputPccRlcUl());
			proLteDataInfoBean.setThroughputPccRlcDl(this.proIndicators.getThroughputPccRlcDl());
			proLteDataInfoBean.setThroughputPccMacDl(this.proIndicators.getThroughputPccMacDl());
			proLteDataInfoBean.setThroughputPccMacUl(this.proIndicators.getThroughputPccMacUl());
			proLteDataInfoBean.setServingCellPccPuschRbs("");
			proLteDataInfoBean.setServingCellPccPdschRbs("");
			proLteDataInfoBean.setModulationPcc16qam(this.proIndicators.getModulationPcc16qam());
			proLteDataInfoBean.setModulationPcc64qam(this.proIndicators.getModulationPcc64qam());
			proLteDataInfoBean.setModulationPcc256qam(this.proIndicators.getModulationPcc256qam());
			proLteDataInfoBean.setModulationPcc16qamUl(this.proIndicators.getModulationPcc16qamUl());
			proLteDataInfoBean.setModulationPcc16qamDl(this.proIndicators.getModulationPcc16qamDl());
			proLteDataInfoBean.setModulationPcc64qamDl(this.proIndicators.getModulationPcc64qamDl());
			proLteDataInfoBean.setModulationPcc64qamUl(this.proIndicators.getModulationPcc64qamUl());
			proLteDataInfoBean.setModulationPcc256qamUl(this.proIndicators.getModulationPcc256qamUl());
			proLteDataInfoBean.setModulationPcc256qamDl(this.proIndicators.getModulationPcc256qamDl());
			proLteDataInfoBean.setModulationPccQPSKUl(this.proIndicators.getModulationPccQPSKUl());
			proLteDataInfoBean.setModulationPccQPSKDl(this.proIndicators.getModulationPccQPSKDl());
			
			if (this.doorDataInfoBeans !=null) {
				List<ProLteNeighborhoodInfo> proLteNeighborhoodInfos=new ArrayList<>();
				ArrayList<MLteNeighborhoodInfo> getmLteNeighborhoodInfos = this.doorDataInfoBeans.getmLteNeighborhoodInfos();
				if (getmLteNeighborhoodInfos !=null && getmLteNeighborhoodInfos.size()>0) {
					for (MLteNeighborhoodInfo mLteNeighborhoodInfo : getmLteNeighborhoodInfos) {
						ProLteNeighborhoodInfo modelKc=new ProLteNeighborhoodInfo();
						modelKc.setpLteNeighbirhoodEARFCN(mLteNeighborhoodInfo.getmLteNeighborhoodEarfcn());
						modelKc.setpLteNeighbirhoodPCI(mLteNeighborhoodInfo.getmLteNeighborhoodPCI());
						modelKc.setpLteNeighbirhoodRSRP(mLteNeighborhoodInfo.getmLteNeighborhoodRSRPOrSINR());
						modelKc.setpLteNeighbirhoodRSRQ(mLteNeighborhoodInfo.getmLteNeighborhoodRsrq());
						modelKc.setpLteNeighbirhoodRSSI("");
						proLteNeighborhoodInfos.add(modelKc);
					}
				}
				
				proLteDataInfoBean.setProLteNeighborhoodInfos(proLteNeighborhoodInfos);
			}
			
			
		}
		nrLogBodyBean.setProLteDataInfoBean(proLteDataInfoBean);
		
		List<SignalEventBean> signalEventBeans=new ArrayList<>();
		if (this.mSignaEventBean !=null && this.mSignaEventBean.size()>0) {
			for (MSignaEventBean mSignaEventBean : this.mSignaEventBean) {
				SignalEventBean modelKil=new SignalEventBean();
				modelKil.setmTimestamp(mSignaEventBean.getmTimestamp());
				modelKil.setmEventType(mSignaEventBean.getmEventType());
				modelKil.setmEvent(mSignaEventBean.getmEvent());
				modelKil.setmLatitude(mSignaEventBean.getmLatitude());
				modelKil.setmLongitude(mSignaEventBean.getmLongitude());
				signalEventBeans.add(modelKil);
			}
		}
		nrLogBodyBean.setSignalEventBeans(signalEventBeans);
		
		List<SignalBean> signalBeans=new ArrayList<>();
		if (this.mSignaBean !=null && this.mSignaBean.size()>0) {
			for (MSignaBean mSignaBean : this.mSignaBean) {
				SignalBean modelSig=new SignalBean();
				modelSig.setmTimestamp(mSignaBean.getmTimestamp());
				modelSig.setmSingaType(mSignaBean.getmSingaType());
				modelSig.setmMessageType(mSignaBean.getmMessageType());
				modelSig.setmChannelType(mSignaBean.getmChannelType());
				modelSig.setmMeaasge(mSignaBean.getmMeaasge());
				modelSig.setmLongitude(mSignaBean.getmLongitude());
				modelSig.setmLatitude(mSignaBean.getmLatitude());
				signalBeans.add(modelSig);
			}
		}
		nrLogBodyBean.setSignalBeans(signalBeans);
		return nrLogBodyBean;
	}


	/*private int handleAbNormalEventTypeToInt(String normalEventType2) {
		if(("FTPConnectionFailure").equals(normalEventType2)) {
			return 0;
		}
		else if (("DownloadFailure").equals(normalEventType2)) {
			return 1;
		}
		else if (("UploadFailure").equals(normalEventType2)) {
			return 2;
		}
		else if (("PingFailure").equals(normalEventType2)) {
			return 3;
		}
		else if (("HttpFailure").equals(normalEventType2)) {
			return 4;
		}
		else if (("BlockedCall").equals(normalEventType2)) {
			return 5;
		}
		else if (("DropedCall").equals(normalEventType2)) {
			return 6;
		}
		return 0;
	}


	private int handleNormalEventTypeToInt(String normalEventType2) {
		if(("FTPConnectionSuccess").equals(normalEventType2)) {
			return 0;
		}
		else if (("DownloadStart").equals(normalEventType2)) {
			return 1;
		}
		else if (("DownloadComplete").equals(normalEventType2)) {
			return 2;
		}
		else if (("UploadStart").equals(normalEventType2)) {
			return 3;
		}
		else if (("UploadComplete").equals(normalEventType2)) {
			return 4;
		}
		else if (("PingSuccess").equals(normalEventType2)) {
			return 5;
		}
		else if (("HttpSuccess").equals(normalEventType2)) {
			return 6;
		}
		else if (("CallInitiate").equals(normalEventType2)) {
			return 7;
		}
		else if (("CallStart").equals(normalEventType2)) {
			return 8;
		}
		else if (("CallEnd").equals(normalEventType2)) {
			return 9;
		}
		return 0;
	}*/




	/**
	 * 针对4G的最后一条记录   转换foot值
	* @Description: TODO
	* @author weichengz
	* @date 2020年7月21日 上午10:39:25
	 */
	public LogFoot ToLogFoot() {
		LogFoot logFoot=new LogFoot();
		
		 FootLtewxzbBean ltewxzbBean=new  FootLtewxzbBean();
		 if (this.wxzbBean !=null) {
			 ltewxzbBean.setRsrpd(this.wxzbBean.getRsrpd());
			 ltewxzbBean.setRsrqx(this.wxzbBean.getRsrqx());
			 ltewxzbBean.setRsrqp(this.wxzbBean.getRsrqp());
			 ltewxzbBean.setRsrpd(this.wxzbBean.getRsrpd());
			 ltewxzbBean.setRsrpx(this.wxzbBean.getRsrpx());
			 ltewxzbBean.setRsrpp(this.wxzbBean.getRsrpp());
			 ltewxzbBean.setSinrd(this.wxzbBean.getSinrd());
			 ltewxzbBean.setSinrx(this.wxzbBean.getSinrx());
			 ltewxzbBean.setSinrp(this.wxzbBean.getSinrp());
			 ltewxzbBean.setCssc(this.wxzbBean.getCssc());
			 ltewxzbBean.setFgl(this.wxzbBean.getFgl());
			 ltewxzbBean.setFglc(this.wxzbBean.getFglc());
			 ltewxzbBean.setZlc(this.wxzbBean.getZlc());
			 ltewxzbBean.setLcfgl(this.wxzbBean.getZlc());
			 
			 ltewxzbBean.setComptestRsrp0(this.wxzbBean.getComptestRsrp0());
			 ltewxzbBean.setComptestRsrp1(this.wxzbBean.getComptestRsrp1());
			 ltewxzbBean.setComptestRsrp2(this.wxzbBean.getComptestRsrp2());
		}
		 if (this.wxzbViceBean !=null) {
			 ltewxzbBean.setRsrqd_y(this.wxzbViceBean.getRsrqd());
			 ltewxzbBean.setRsrqx_y(this.wxzbViceBean.getRsrqx());
			 ltewxzbBean.setRsrqp_y(this.wxzbViceBean.getRsrqp());
			 ltewxzbBean.setRsrpd_y(this.wxzbViceBean.getRsrpd());
			 ltewxzbBean.setRsrpx_y(this.wxzbViceBean.getRsrpx());
			 ltewxzbBean.setRsrpp_y(this.wxzbViceBean.getRsrpp());
			 ltewxzbBean.setSinrd_y(this.wxzbViceBean.getSinrd());
			 ltewxzbBean.setSinrx_y(this.wxzbViceBean.getSinrx());
			 ltewxzbBean.setSinrp_y(this.wxzbViceBean.getSinrp());
			 ltewxzbBean.setCssc_y(this.wxzbViceBean.getCssc());
			 ltewxzbBean.setFgl_y(this.wxzbViceBean.getFgl());
			 ltewxzbBean.setFglc_y(this.wxzbViceBean.getFglc());
			 ltewxzbBean.setZlc_y(this.wxzbViceBean.getZlc());
			 ltewxzbBean.setLcfgl_y(this.wxzbViceBean.getLcfgl());
		}
		 logFoot.setLtewxzbBean(ltewxzbBean);
		
		 
		 FootFtpzbBean ftpzbBean=new FootFtpzbBean();
		 if (this.ftpzbBean !=null) {
			 ftpzbBean.setQqcs(this.ftpzbBean.getPppRequestCount()+"");
			 ftpzbBean.setCgcs("");
			 ftpzbBean.setDxcs("");
			 ftpzbBean.setDxl("");
			 ftpzbBean.setXzcl(this.ftpzbBean.getXzzl());
			 ftpzbBean.setSczl(this.ftpzbBean.getCszl());
			 ftpzbBean.setDlAvg(this.ftpzbBean.getDownloadAvg());
			 ftpzbBean.setUlAvg(this.ftpzbBean.getUploadAvg());
			 ftpzbBean.setDlMax(this.ftpzbBean.getDownloadMax());
			 ftpzbBean.setUlMax(this.ftpzbBean.getUploadMax());
			 ftpzbBean.setDlLowRatio("");
			 ftpzbBean.setUlLowRatio("");
			 ftpzbBean.setMacDL("");
			 ftpzbBean.setMacUL("");
			 ftpzbBean.setMcsDL("");
			 ftpzbBean.setMcsUL("");
			 ftpzbBean.setDl64QAM("");
			 ftpzbBean.setDl256QAM("");
			 ftpzbBean.setUl64QAM("");
			 ftpzbBean.setUl256QAM("");
			 ftpzbBean.setGrantDLnum("");
			 ftpzbBean.setGrantULnum("");
			 ftpzbBean.setPdschRBnum("");
			 ftpzbBean.setPuschRBnum("");
			 ftpzbBean.setCqi("");
			 ftpzbBean.setRankDL("");
		}
		 logFoot.setFtpzbBean(ftpzbBean);
		 
		 
		 FootPingzbBean pingzbBeanMo=new FootPingzbBean();
		 if (this.pingzbBean !=null) {
			 pingzbBeanMo.setQqcs(this.pingzbBean.getQqcs());
			 pingzbBeanMo.setCgcs(this.pingzbBean.getCgcs());
			 pingzbBeanMo.setCgl(this.pingzbBean.getCgl());
			 pingzbBeanMo.setZdsy(this.pingzbBean.getZdsy());
			 pingzbBeanMo.setZxsy(this.pingzbBean.getZxsy());
			 pingzbBeanMo.setPjsy(this.pingzbBean.getPjsy());
		}
		 logFoot.setPingzbBean(pingzbBeanMo);
		 
		 
		 FootHttpzbBean httpzbBeanModel=new FootHttpzbBean();
		 if (this.httpzbBean !=null) {
			 httpzbBeanModel.setQqcs(this.httpzbBean.getQqcs());
			 httpzbBeanModel.setCgcs(this.httpzbBean.getCgcs());
			 httpzbBeanModel.setCgl(this.httpzbBean.getCgl());
			 httpzbBeanModel.setZdsy(this.httpzbBean.getZdsy());
			 httpzbBeanModel.setZxsy(this.httpzbBean.getZxsy());
			 httpzbBeanModel.setPjsy(this.httpzbBean.getPjsy());
		}
		 logFoot.setHttpzbBean(httpzbBeanModel);
		 
		 FootYyzbBean yyzbBeanModel=new FootYyzbBean();
		 if (this.yyzbbean !=null) {
			 yyzbBeanModel.setZjcs(this.yyzbbean.getHjsyd());
			 yyzbBeanModel.setBjcs(this.yyzbbean.getHjsyx());
			 yyzbBeanModel.setJtcs(this.yyzbbean.getHjsyp());
			 yyzbBeanModel.setJtl(this.yyzbbean.getZjcs());
			 yyzbBeanModel.setDhcs(this.yyzbbean.getBjcs());
			 yyzbBeanModel.setDhl(this.yyzbbean.getWjtcs());
			 yyzbBeanModel.setHjsyd(this.yyzbbean.getDhcs());
			 yyzbBeanModel.setHjsyx(this.yyzbbean.getJtl());
			 yyzbBeanModel.setHjsyp(this.yyzbbean.getDhl());
		}
		 logFoot.setYyzbBean(yyzbBeanModel);
		 
		return logFoot;
	}

}
