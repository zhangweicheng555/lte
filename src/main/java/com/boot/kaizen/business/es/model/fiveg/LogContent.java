package com.boot.kaizen.business.es.model.fiveg;

import java.io.Serializable;
import java.util.List;

import com.boot.kaizen.business.es.model.fiveg.model.LteDataInfoBeans;
import com.boot.kaizen.business.es.model.fiveg.model.NrDataInfoBeans;
import com.boot.kaizen.business.es.model.fiveg.model.ProNrDataInfoBeans;

/**
 * log底部信息读取
 * 
 * @author weichengz
 * @date 2020年4月26日 上午9:53:51
 */
public class LogContent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String latitude; // 纬度
	private String longitude; // 经度
	private String speed; // 时速
	private String height; // 高度
	private String testTime; // 当前时间
	private String downLoadSpeed; // APP下载速率
	private String upLoadSpeed; // APP上传速率
	/**
	 * 0：FTPConnectionSuccess 1：DownloadStart 2：DownloadComplete 3：UploadStart
	 * 4：UploadComplete 5：PingSuccess 6：HttpSuccess 7：CallInitiate 8：CallStart
	 * 9：CallEnd
	 * 
	 */
	private Integer normalEventType; //内容如上
	/**
	 *  0：FTPConnectionFailure
		1：DownloadFailure
		2：UploadFailure
		3：PingFailure
		4：HttpFailure
		5：BlockedCall
		6：DropedCall

	 */
	private Integer abNormalEventType; //内容如上
	
	private NrDataInfoBeans nrDataInfoBeans;//测试指标【对象】（主卡）  非root  5g
	
	private LteDataInfoBeans lteDataInfoBeans;//测试指标【对象】（主卡）  非root  4g
	
	
    private ProNrDataInfoBeans proNrDataInfoBeans;  //测试指标【对象】  root  5g
    
    private List<LogEvent> mSignaEventBean;//事件
    private List<LogMessage> mSignaBean;//信令
    
    
    
    
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
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getTestTime() {
		return testTime;
	}
	public void setTestTime(String testTime) {
		this.testTime = testTime;
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
	public Integer getNormalEventType() {
		return normalEventType;
	}
	public void setNormalEventType(Integer normalEventType) {
		this.normalEventType = normalEventType;
	}
	public Integer getAbNormalEventType() {
		return abNormalEventType;
	}
	public void setAbNormalEventType(Integer abNormalEventType) {
		this.abNormalEventType = abNormalEventType;
	}
	public NrDataInfoBeans getNrDataInfoBeans() {
		return nrDataInfoBeans;
	}
	public void setNrDataInfoBeans(NrDataInfoBeans nrDataInfoBeans) {
		this.nrDataInfoBeans = nrDataInfoBeans;
	}
	public LteDataInfoBeans getLteDataInfoBeans() {
		return lteDataInfoBeans;
	}
	public void setLteDataInfoBeans(LteDataInfoBeans lteDataInfoBeans) {
		this.lteDataInfoBeans = lteDataInfoBeans;
	}
	public ProNrDataInfoBeans getProNrDataInfoBeans() {
		return proNrDataInfoBeans;
	}
	public void setProNrDataInfoBeans(ProNrDataInfoBeans proNrDataInfoBeans) {
		this.proNrDataInfoBeans = proNrDataInfoBeans;
	}
	public List<LogEvent> getmSignaEventBean() {
		return mSignaEventBean;
	}
	public void setmSignaEventBean(List<LogEvent> mSignaEventBean) {
		this.mSignaEventBean = mSignaEventBean;
	}
	public List<LogMessage> getmSignaBean() {
		return mSignaBean;
	}
	public void setmSignaBean(List<LogMessage> mSignaBean) {
		this.mSignaBean = mSignaBean;
	}
	public LogContent(String latitude, String longitude, String speed, String height, String testTime,
			String downLoadSpeed, String upLoadSpeed, Integer normalEventType, Integer abNormalEventType,
			NrDataInfoBeans nrDataInfoBeans, LteDataInfoBeans lteDataInfoBeans, ProNrDataInfoBeans proNrDataInfoBeans,
			List<LogEvent> mSignaEventBean, List<LogMessage> mSignaBean) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
		this.height = height;
		this.testTime = testTime;
		this.downLoadSpeed = downLoadSpeed;
		this.upLoadSpeed = upLoadSpeed;
		this.normalEventType = normalEventType;
		this.abNormalEventType = abNormalEventType;
		this.nrDataInfoBeans = nrDataInfoBeans;
		this.lteDataInfoBeans = lteDataInfoBeans;
		this.proNrDataInfoBeans = proNrDataInfoBeans;
		this.mSignaEventBean = mSignaEventBean;
		this.mSignaBean = mSignaBean;
	}
	public LogContent() {
		super();
	}
    
    
    
    
	
	
}
