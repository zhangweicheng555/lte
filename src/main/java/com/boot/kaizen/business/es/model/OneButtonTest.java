package com.boot.kaizen.business.es.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotations.TableName;
import com.boot.kaizen.util.LngLatUtil;

/**
 * 一键测试  onebuttontest
 * 
 * @author weichengz
 * @date 2019年11月13日 上午9:23:36
 */
@TableName("buss_es_log_onebuttontest")
public class OneButtonTest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	//新增地市编号主键20191224
	private String cityId;// 地市主键  就是原来项目主键   目前根据登陆得账户获取
	
	private String operatorService;// 运营商 无           【必须填写】
	private String netWorkType;// 网络类型 netWorkType   就是我们的网络类型   1 LTE  2 WCDMA   3GSM  4 5G   5  CDMA
	private String city;// 城市 无
	private String county;// 区县 无
	private String testPerson;// 测试人员 无
	private String phoneType;// 手机型号 无
	private String phoneNo;// 手机号码
	private String imsi;// IMSI 无
	private String testTime;// 测试时间
	private String latitude;// 纬度   百度
	private String longitude;// 经度  百度
	private String testLocation;// 测试位置
	private String downRate;// 下载速度
	private String upRate;// 上传速度
	private String pingFlag;// PIng
	private String httpFlag;// http
	private String avgRsrp;// 平均RSRP
	private String avgSinr;// 平均SINR
	private String cellName;// 小区名字
	private String enodebid;// 站号
	private String ci;// CI
	private String frequencyBand;// 频段
	private String pci;// PCI
	private String tac;// TAC

	private Long testTimeQuery;// 测试时间查询字段使用  20200416暂时用不到
	
	
	

	/**
	 * 
	 * @Description: 将百度地图转为wgs84
	 * @author weichengz
	 * @date 2019年12月27日 下午4:44:28
	 */
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
	
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Long getTestTimeQuery() {
		return testTimeQuery;
	}

	public void setTestTimeQuery(Long testTimeQuery) {
		this.testTimeQuery = testTimeQuery;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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

	public String getTestLocation() {
		return testLocation;
	}

	public void setTestLocation(String testLocation) {
		this.testLocation = testLocation;
	}

	public String getDownRate() {
		return downRate;
	}

	public void setDownRate(String downRate) {
		this.downRate = downRate;
	}

	public String getUpRate() {
		return upRate;
	}

	public void setUpRate(String upRate) {
		this.upRate = upRate;
	}

	public String getPingFlag() {
		return pingFlag;
	}

	public void setPingFlag(String pingFlag) {
		this.pingFlag = pingFlag;
	}

	public String getHttpFlag() {
		return httpFlag;
	}

	public void setHttpFlag(String httpFlag) {
		this.httpFlag = httpFlag;
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

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getEnodebid() {
		return enodebid;
	}

	public void setEnodebid(String enodebid) {
		this.enodebid = enodebid;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getFrequencyBand() {
		return frequencyBand;
	}

	public void setFrequencyBand(String frequencyBand) {
		this.frequencyBand = frequencyBand;
	}

	public String getPci() {
		return pci;
	}

	public void setPci(String pci) {
		this.pci = pci;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	public OneButtonTest(String id, String operatorService, String netWorkType, String city, String county,
			String testPerson, String phoneType, String phoneNo, String imsi, String testTime, String latitude,
			String longitude, String testLocation, String downRate, String upRate, String pingFlag, String httpFlag,
			String avgRsrp, String avgSinr, String cellName, String enodebid, String ci, String frequencyBand,
			String pci, String tac) {
		super();
		this.id = id;
		this.operatorService = operatorService;
		this.netWorkType = netWorkType;
		this.city = city;
		this.county = county;
		this.testPerson = testPerson;
		this.phoneType = phoneType;
		this.phoneNo = phoneNo;
		this.imsi = imsi;
		this.testTime = testTime;
		this.latitude = latitude;
		this.longitude = longitude;
		this.testLocation = testLocation;
		this.downRate = downRate;
		this.upRate = upRate;
		this.pingFlag = pingFlag;
		this.httpFlag = httpFlag;
		this.avgRsrp = avgRsrp;
		this.avgSinr = avgSinr;
		this.cellName = cellName;
		this.enodebid = enodebid;
		this.ci = ci;
		this.frequencyBand = frequencyBand;
		this.pci = pci;
		this.tac = tac;
	}

	public OneButtonTest() {
		super();
	}

}
