package com.boot.kaizen.business.buss.model.fiveg.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.boot.kaizen.util.LngLatUtil;
import com.boot.kaizen.util.MyDateUtil;

/**
 * log文件体
 * 
 * @author weichengz
 * @date 2020年5月6日 上午10:07:41
 */
public class NrLogBodyBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// 新增内容20200506
	private String pid;// 室外测试的id
	private String id;// 每个log实体类的字段

	private String latitude;// 纬度
	private String longitude;// 经度
	private String speed;// 时速
	private String height;// 海拔高度（m）
	private String testTime;// 当前时间(yyyy-MM-dd HH:mm:ss)
	private String downLoadSpeed;// APP下载速率
	private String upLoadSpeed;// APP上传速率
	// 0：FTPConnectionSuccess
	// 1：DownloadStart
	// 2：DownloadComplete
	// 3：UploadStart
	// 4：UploadComplete
	// 5：PingSuccess
	// 6：HttpSuccess
	// 7：CallInitiate
	// 8：CallStart
	// 9：CallEnd
	private String normalEventType;  //现在改成了 字符串了
	// 0：FTPConnectionFailure
	// 1：DownloadFailure
	// 2：UploadFailure
	// 3：PingFailure
	// 4：HttpFailure
	// 5：BlockedCall
	// 6：DropedCall
	private String abNormalEventType;

	private NrDataInfoBean nrDataInfoBean;
	private LteDataInfoBean lteDataInfoBean;
	private LteDataInfoBean infoBean;
	private List<ComptestBean> comptestBeans;
	private ProNrDataInfoBean proNrDataInfoBean;
	// private ProLteDataInfoBean proLteDataInfoBeans;
	private ProLteDataInfoBean proLteDataInfoBean;
	private List<SignalEventBean> signalEventBeans;
	private List<SignalBean> signalBeans;

	public NrLogBodyBean(String latitude, String longitude, String speed, String height, String testTime,
			String downLoadSpeed, String upLoadSpeed, String normalEventType, String abNormalEventType,
			NrDataInfoBean nrDataInfoBean, LteDataInfoBean lteDataInfoBean, LteDataInfoBean infoBean,
			List<ComptestBean> comptestBeans, ProNrDataInfoBean proNrDataInfoBean,
			ProLteDataInfoBean proLteDataInfoBeans, List<SignalEventBean> signalEventBeans,
			List<SignalBean> signalBeans) {
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
		this.nrDataInfoBean = nrDataInfoBean;
		this.lteDataInfoBean = lteDataInfoBean;
		this.infoBean = infoBean;
		this.comptestBeans = comptestBeans;
		this.proNrDataInfoBean = proNrDataInfoBean;
		this.proLteDataInfoBean = proLteDataInfoBeans;
		this.signalEventBeans = signalEventBeans;
		this.signalBeans = signalBeans;
	}

	public NrLogBodyBean() {
		super();
	}

	public NrLogBodyBean(String pid, String id, String latitude, String longitude, String speed, String height,
			String testTime, String downLoadSpeed, String upLoadSpeed, String normalEventType, String abNormalEventType,
			NrDataInfoBean nrDataInfoBean, LteDataInfoBean lteDataInfoBean, LteDataInfoBean infoBean,
			List<ComptestBean> comptestBeans, ProNrDataInfoBean proNrDataInfoBean,
			ProLteDataInfoBean proLteDataInfoBeans, List<SignalEventBean> signalEventBeans,
			List<SignalBean> signalBeans) {
		super();
		this.pid = pid;
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
		this.height = height;
		this.testTime = testTime;
		this.downLoadSpeed = downLoadSpeed;
		this.upLoadSpeed = upLoadSpeed;
		this.normalEventType = normalEventType;
		this.abNormalEventType = abNormalEventType;
		this.nrDataInfoBean = nrDataInfoBean;
		this.lteDataInfoBean = lteDataInfoBean;
		this.infoBean = infoBean;
		this.comptestBeans = comptestBeans;
		this.proNrDataInfoBean = proNrDataInfoBean;
		this.proLteDataInfoBean = proLteDataInfoBeans;
		this.signalEventBeans = signalEventBeans;
		this.signalBeans = signalBeans;
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

	public NrDataInfoBean getNrDataInfoBean() {
		return nrDataInfoBean;
	}

	public void setNrDataInfoBean(NrDataInfoBean nrDataInfoBean) {
		this.nrDataInfoBean = nrDataInfoBean;
	}

	public LteDataInfoBean getLteDataInfoBean() {
		return lteDataInfoBean;
	}

	public void setLteDataInfoBean(LteDataInfoBean lteDataInfoBean) {
		this.lteDataInfoBean = lteDataInfoBean;
	}

	public LteDataInfoBean getInfoBean() {
		return infoBean;
	}

	public void setInfoBean(LteDataInfoBean infoBean) {
		this.infoBean = infoBean;
	}

	public List<ComptestBean> getComptestBeans() {
		return comptestBeans;
	}

	public void setComptestBeans(List<ComptestBean> comptestBeans) {
		this.comptestBeans = comptestBeans;
	}

	public ProNrDataInfoBean getProNrDataInfoBean() {
		return proNrDataInfoBean;
	}

	public void setProNrDataInfoBean(ProNrDataInfoBean proNrDataInfoBean) {
		this.proNrDataInfoBean = proNrDataInfoBean;
	}

	public ProLteDataInfoBean getProLteDataInfoBean() {
		return proLteDataInfoBean;
	}

	public void setProLteDataInfoBean(ProLteDataInfoBean proLteDataInfoBean) {
		this.proLteDataInfoBean = proLteDataInfoBean;
	}

	public List<SignalEventBean> getSignalEventBeans() {
		return signalEventBeans;
	}

	public void setSignalEventBeans(List<SignalEventBean> signalEventBeans) {
		this.signalEventBeans = signalEventBeans;
	}

	public List<SignalBean> getSignalBeans() {
		return signalBeans;
	}

	public void setSignalBeans(List<SignalBean> signalBeans) {
		this.signalBeans = signalBeans;
	}

	/**
	 * 讲long的字符串转为   yyyy-MM-dd HH:mm:ss
	* @Description: TODO
	* @author weichengz
	* @date 2020年7月23日 上午10:51:40
	 */
	public String dealLongTimeToStr(String testTime2) {
		if (StringUtils.isNotBlank(testTime2)) {
			try {
				Long timeLong=Long.valueOf(testTime2);
				return MyDateUtil.formatDate(new Date(timeLong), "yyyy-MM-dd HH:mm:ss");
			} catch (Exception e) {
				throw new IllegalArgumentException("TestTime时间格式不正确");
			}
		}
		return "";
	}
	


}
