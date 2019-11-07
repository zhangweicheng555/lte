package com.boot.kaizen.business.es.model;

import java.util.ArrayList;
import java.util.Date;

import com.boot.kaizen.business.es.model.logModel.MSignaBean;
import com.boot.kaizen.business.es.model.logModel.MSignaEventBean;
import com.boot.kaizen.business.es.model.logModel.OurDoorDataInfoBean;
import com.boot.kaizen.business.es.model.logModel.SignalDataBean;
import com.boot.kaizen.util.MyDateUtil;

/**
 * log日志的主表信息 cloudlog
 * 
 * @author weichengz
 * @date 2019年11月5日 下午1:47:43
 */
public class MainLogModel {

	private String id;// 自定义编号ID 注意这个非log上传信息 注意这个id一定要在log值转换之前的时候 付给原来的log日志的信息

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
	private Long testTime;
	/* 0:上传和下载 1:上传 2:下载 */
	private int ftpType = -1;

	private OurDoorDataInfoBean doorDataInfoBeans;

	private ArrayList<MSignaEventBean> mSignaEventBean;// 一秒钟事件
	private ArrayList<MSignaBean> mSignaBean;// 一秒钟信令

	public ArrayList<MSignaBean> getmSignaBean() {
		return mSignaBean;
	}

	public void setmSignaBean(ArrayList<MSignaBean> mSignaBean) {
		this.mSignaBean = mSignaBean;
	}

	public ArrayList<MSignaEventBean> getmSignaEventBean() {
		return mSignaEventBean;
	}

	public void setmSignaEventBean(ArrayList<MSignaEventBean> mSignaEventBean) {
		this.mSignaEventBean = mSignaEventBean;
	}

	public MainLogModel(SignalDataBean signalDataBean) {
		this.mSignaBean = signalDataBean.getmSignaBean();
		
		for (MSignaBean model : mSignaBean) {//设置为空观察速度
			model.setmMeaasge("");
		}
		
		Long timeFormat = null;
		Date date = MyDateUtil.stringToDate(signalDataBean.getTestTime(), "yyyy-MM-dd HH:mm:ss");
		if (date != null) {
			timeFormat = date.getTime();
		}
		this.id = signalDataBean.getId();
		this.latitude = signalDataBean.getLatitude();
		this.longitude = signalDataBean.getLongitude();
		this.mSignaEventBean = signalDataBean.getmSignaEventBean();
		this.sinr = signalDataBean.getSinr();
		this.rsrp = signalDataBean.getRsrp();
		CellName = signalDataBean.getCellName();
		CI = signalDataBean.getCI();
		CELLID = signalDataBean.getCELLID();
		NetWorkType = signalDataBean.getNetWorkType();
		TAC = signalDataBean.getTAC();
		ENB = signalDataBean.getENB();
		SPEED = signalDataBean.getSPEED();
		this.downLoadSpeed = signalDataBean.getDownLoadSpeed();
		this.upLoadSpeed = signalDataBean.getUpLoadSpeed();
		this.normalEventType = signalDataBean.getNormalEventType();
		this.abNormalEventType = signalDataBean.getAbNormalEventType();
		this.eventType = signalDataBean.getEventType();
		this.siteLat = signalDataBean.getSiteLat();
		this.siteLng = signalDataBean.getSiteLng();
		this.angle = signalDataBean.getAngle();
		this.pci = signalDataBean.getPci();
		this.earfcn = signalDataBean.getEarfcn();
		this.testTime = timeFormat;
		this.ftpType = signalDataBean.getFtpType();
		this.doorDataInfoBeans = signalDataBean.getDoorDataInfoBeans();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getRsrp() {
		return rsrp;
	}

	public void setRsrp(String rsrp) {
		this.rsrp = rsrp;
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

	public void setCI(String cI) {
		CI = cI;
	}

	public String getCELLID() {
		return CELLID;
	}

	public void setCELLID(String cELLID) {
		CELLID = cELLID;
	}

	public String getNetWorkType() {
		return NetWorkType;
	}

	public void setNetWorkType(String netWorkType) {
		NetWorkType = netWorkType;
	}

	public String getTAC() {
		return TAC;
	}

	public void setTAC(String tAC) {
		TAC = tAC;
	}

	public String getENB() {
		return ENB;
	}

	public void setENB(String eNB) {
		ENB = eNB;
	}

	public String getSPEED() {
		return SPEED;
	}

	public void setSPEED(String sPEED) {
		SPEED = sPEED;
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

	public String getEarfcn() {
		return earfcn;
	}

	public void setEarfcn(String earfcn) {
		this.earfcn = earfcn;
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

	public MainLogModel(String id, String latitude, String longitude, String sinr, String rsrp, String cellName,
			String cI, String cELLID, String netWorkType, String tAC, String eNB, String sPEED, String downLoadSpeed,
			String upLoadSpeed, String normalEventType, String abNormalEventType, int eventType, String siteLat,
			String siteLng, int angle, String pci, String earfcn, Long testTime, int ftpType,
			OurDoorDataInfoBean doorDataInfoBeans) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sinr = sinr;
		this.rsrp = rsrp;
		CellName = cellName;
		CI = cI;
		CELLID = cELLID;
		NetWorkType = netWorkType;
		TAC = tAC;
		ENB = eNB;
		SPEED = sPEED;
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
	}

	public Long getTestTime() {
		return testTime;
	}

	public void setTestTime(Long testTime) {
		this.testTime = testTime;
	}

	public MainLogModel() {
		super();
	}

}
