package com.boot.kaizen.business.es.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.boot.kaizen.business.es.model.logModel.FtpzbBean;
import com.boot.kaizen.business.es.model.logModel.SignalDataBean;
import com.boot.kaizen.business.es.model.logModel.WxzbBean;
import com.boot.kaizen.util.MyDateUtil;

/**
 * 室外测试列表的实体类
 * 
 * @author weichengz
 * @date 2019年11月11日 上午9:48:20
 */
public class OutHomeLogModel {

	// 注意室外测试的id pk 是一样的
	private String id;// 主键
	private String fileName;// 文件名
	private Long fileUpTime;// 文件上传日期
	private String filePath;// 文件路径 这个不导出
	private String operatorService;// 运营商 无 【这个必须填写】
	private String netWorkType;// 网络类型 netWorkType 【 这个必须填写 这个目前数据的LTE】
	private String city;// 城市 无
	private String county;// 地市 无
	private String testPerson;// 测试人员 无
	private String phoneType;// 手机型号 无
	private String imsi;// IMSI 无
	private String testTime;// 测试时长 cssc
	private Long beginTime;// 开始时间 应该是 当前时间testTime
	private Long endTime;// 结束时间 应该是最后一条记录的当前时间
	private String totalMileage;// 总里程 最后一条记录的zlc
	private String coverPersent;// 覆盖率（RSRP>= -） SINR >=-3 fgl 最后一条记录的覆盖率
	private String avgRsrp;// 平均RSRP rsrpp
	private String avgSinr;// 平均SINR sinrp
	private String avgDownRate;// 下载平均速率 downloadAvg
	private String avgUpRate;// 上传平均速率 uploadAvg

	// 新增20191224
	private String cityId;// 新增地市id
	private String isMsgEvent="0";// 是否存在信令、事件  默认是0不存在

	
	public String getIsMsgEvent() {
		return isMsgEvent;
	}

	public void setIsMsgEvent(String isMsgEvent) {
		this.isMsgEvent = isMsgEvent;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Long getFileUpTime() {
		return fileUpTime;
	}

	public void setFileUpTime(Long fileUpTime) {
		this.fileUpTime = fileUpTime;
	}

	public OutHomeLogModel(SignalDataBean signalDataBeanFinal, String beginTime) {
		if (signalDataBeanFinal != null) {
			if (StringUtils.isNoneBlank(beginTime)) {
				Date date = MyDateUtil.stringToDate(beginTime, "yyyy-MM-dd HH:mm:ss");
				this.beginTime = date.getTime();
			}
			String testTime2 = signalDataBeanFinal.getTestTime();
			if (StringUtils.isNoneBlank(testTime2)) {
				Date date = MyDateUtil.stringToDate(testTime2, "yyyy-MM-dd HH:mm:ss");
				this.endTime = date.getTime();
			}

			this.id = signalDataBeanFinal.getPid();
			this.fileName = "";
			this.filePath = "";
			this.operatorService = "";
			this.netWorkType = signalDataBeanFinal.getNetWorkType();
			this.city = "";
			this.county = "";
			this.testPerson = "";
			this.phoneType = "";
			this.imsi = "";

			WxzbBean wxzbBean = signalDataBeanFinal.getWxzbBean();
			if (wxzbBean != null) {
				this.testTime = wxzbBean.getCssc();
				this.totalMileage = wxzbBean.getZlc();
				this.coverPersent = wxzbBean.getFgl();
				this.avgRsrp = wxzbBean.getRsrpp();
				this.avgSinr = wxzbBean.getSinrp();
			}
			FtpzbBean ftpzbBean = signalDataBeanFinal.getFtpzbBean();
			if (ftpzbBean != null) {
				this.avgDownRate = ftpzbBean.getDownloadAvg();
				this.avgUpRate = ftpzbBean.getUploadAvg();
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOperatorService() {
		return operatorService;
	}

	public void setOperatorService(String operatorService) {
		this.operatorService = operatorService;
	}

	public String getNetWorkType() {
		return netWorkType;
	}

	public void setNetWorkType(String netWorkType) {
		this.netWorkType = netWorkType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getTestPerson() {
		return testPerson;
	}

	public void setTestPerson(String testPerson) {
		this.testPerson = testPerson;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getTotalMileage() {
		return totalMileage;
	}

	public void setTotalMileage(String totalMileage) {
		this.totalMileage = totalMileage;
	}

	public String getCoverPersent() {
		return coverPersent;
	}

	public void setCoverPersent(String coverPersent) {
		this.coverPersent = coverPersent;
	}

	public String getAvgRsrp() {
		return avgRsrp;
	}

	public void setAvgRsrp(String avgRsrp) {
		this.avgRsrp = avgRsrp;
	}

	public String getAvgSinr() {
		return avgSinr;
	}

	public void setAvgSinr(String avgSinr) {
		this.avgSinr = avgSinr;
	}

	public String getAvgDownRate() {
		return avgDownRate;
	}

	public void setAvgDownRate(String avgDownRate) {
		this.avgDownRate = avgDownRate;
	}

	public String getAvgUpRate() {
		return avgUpRate;
	}

	public void setAvgUpRate(String avgUpRate) {
		this.avgUpRate = avgUpRate;
	}

	public OutHomeLogModel(String id, String fileName, String filePath, String operatorService, String netWorkType,
			String city, String county, String testPerson, String phoneType, String imsi, String testTime,
			Long beginTime, Long endTime, String totalMileage, String coverPersent, String avgRsrp, String avgSinr,
			String avgDownRate, String avgUpRate) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.filePath = filePath;
		this.operatorService = operatorService;
		this.netWorkType = netWorkType;
		this.city = city;
		this.county = county;
		this.testPerson = testPerson;
		this.phoneType = phoneType;
		this.imsi = imsi;
		this.testTime = testTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.totalMileage = totalMileage;
		this.coverPersent = coverPersent;
		this.avgRsrp = avgRsrp;
		this.avgSinr = avgSinr;
		this.avgDownRate = avgDownRate;
		this.avgUpRate = avgUpRate;
	}

	public OutHomeLogModel() {
		super();
	}

}
