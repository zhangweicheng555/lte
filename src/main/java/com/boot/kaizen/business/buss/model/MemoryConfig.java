package com.boot.kaizen.business.buss.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import com.boot.kaizen.config.SuperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 记忆模块实体类设计
 * 
 * @author weichengz
 * @date 2020年6月28日 上午9:41:04
 */
 @TableName("es_buss_memory_config")
public class MemoryConfig extends SuperEntity<MemoryConfig> {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private String projId;// 项目的id
	@JsonIgnore
	private String userId;// 用户的id
	@JsonIgnore
	private Date createTime;// 创建时间

	// 图例模块
	@JsonIgnore
	private String showFlag;// 显示 默认是1    1是显示
	@JsonIgnore
	private String pointDetail;// 采样点详情 默认是1    1是显示
	
	
	private String itemType;// 阈值类型 默认是RSRP
	
	
	//折线图的记忆模块
	@JsonIgnore
	private String minTime;// 默认的分钟数  3
	@JsonIgnore
	private String itemTypeOne;// 指标1  默认SSRSRP
	@JsonIgnore
	private String itemTypeTwo;// 指标2
	@JsonIgnore
	private String rsrpMin;//    //- 140   
	@JsonIgnore
	private String rsrpMax;// -40
	@JsonIgnore
	private String sinrMin;// -20
	@JsonIgnore
	private String sinrMax;// 30
	@JsonIgnore
	private String ssRsrpMin;// -140
	@JsonIgnore
	private String ssRsrpMax;// -40
	@JsonIgnore
	private String ssSinrMin;// -20
	@JsonIgnore
	private String ssSinrMax;// 30
	@JsonIgnore
	private String ssUlMin;// 0
	@JsonIgnore
	private String ssUlMax;// 1200
	@JsonIgnore
	private String ssDlMin;// 0
	@JsonIgnore
	private String ssDlMax;// 1200
	
	
	
	
	//地图模块    stationFlag4g\eventNormalFlag这个默认是0 其余的全是默认1
	private String stationFlag4g;//基站类型4g   0是否 1是是  
	private String stationFlag5g;//基站类型5g   0是否 1是是 
	private String indoorFlag;//室内  0是否 1是是 
	private String outdoorFlag;//室外   0是否 1是是 
	private String serviceCellFlag;//服务小区  0是否 1是是 
	private String neighborhoodFlag;//邻区  0是否 1是是 
	private String showPointValFlag;//显示轨迹数值  0是否 1是是 
	private String showPointSizeFlag;//轨迹大小  0是否 1是是 
	private String eventAbnormalFlag;//异常事件标记  0是否 1是是
	private String eventNormalFlag;//正常事件标记  0是否 1是是
	
	

	
	
	public MemoryConfig(String projId, String userId, Date createTime, String showFlag, String pointDetail,
			String itemType, String minTime, String itemTypeOne, String itemTypeTwo, String rsrpMin, String rsrpMax,
			String sinrMin, String sinrMax, String ssRsrpMin, String ssRsrpMax, String ssSinrMin, String ssSinrMax,
			String ssUlMin, String ssUlMax, String ssDlMin, String ssDlMax, String stationFlag4g, String stationFlag5g,
			String indoorFlag, String outdoorFlag, String serviceCellFlag, String neighborhoodFlag,
			String showPointValFlag, String showPointSizeFlag, String eventAbnormalFlag, String eventNormalFlag) {
		super();
		this.projId = projId;
		this.userId = userId;
		this.createTime = createTime;
		this.showFlag = showFlag;
		this.pointDetail = pointDetail;
		this.itemType = itemType;
		this.minTime = minTime;
		this.itemTypeOne = itemTypeOne;
		this.itemTypeTwo = itemTypeTwo;
		this.rsrpMin = rsrpMin;
		this.rsrpMax = rsrpMax;
		this.sinrMin = sinrMin;
		this.sinrMax = sinrMax;
		this.ssRsrpMin = ssRsrpMin;
		this.ssRsrpMax = ssRsrpMax;
		this.ssSinrMin = ssSinrMin;
		this.ssSinrMax = ssSinrMax;
		this.ssUlMin = ssUlMin;
		this.ssUlMax = ssUlMax;
		this.ssDlMin = ssDlMin;
		this.ssDlMax = ssDlMax;
		this.stationFlag4g = stationFlag4g;
		this.stationFlag5g = stationFlag5g;
		this.indoorFlag = indoorFlag;
		this.outdoorFlag = outdoorFlag;
		this.serviceCellFlag = serviceCellFlag;
		this.neighborhoodFlag = neighborhoodFlag;
		this.showPointValFlag = showPointValFlag;
		this.showPointSizeFlag = showPointSizeFlag;
		this.eventAbnormalFlag = eventAbnormalFlag;
		this.eventNormalFlag = eventNormalFlag;
	}
	public String getMinTime() {
		return minTime;
	}
	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}
	public String getItemTypeOne() {
		return itemTypeOne;
	}
	public void setItemTypeOne(String itemTypeOne) {
		this.itemTypeOne = itemTypeOne;
	}
	public String getItemTypeTwo() {
		return itemTypeTwo;
	}
	public void setItemTypeTwo(String itemTypeTwo) {
		this.itemTypeTwo = itemTypeTwo;
	}
	public String getRsrpMin() {
		return rsrpMin;
	}
	public void setRsrpMin(String rsrpMin) {
		this.rsrpMin = rsrpMin;
	}
	public String getRsrpMax() {
		return rsrpMax;
	}
	public void setRsrpMax(String rsrpMax) {
		this.rsrpMax = rsrpMax;
	}
	public String getSinrMin() {
		return sinrMin;
	}
	public void setSinrMin(String sinrMin) {
		this.sinrMin = sinrMin;
	}
	public String getSinrMax() {
		return sinrMax;
	}
	public void setSinrMax(String sinrMax) {
		this.sinrMax = sinrMax;
	}
	public String getSsRsrpMin() {
		return ssRsrpMin;
	}
	public void setSsRsrpMin(String ssRsrpMin) {
		this.ssRsrpMin = ssRsrpMin;
	}
	public String getSsRsrpMax() {
		return ssRsrpMax;
	}
	public void setSsRsrpMax(String ssRsrpMax) {
		this.ssRsrpMax = ssRsrpMax;
	}
	public String getSsSinrMin() {
		return ssSinrMin;
	}
	public void setSsSinrMin(String ssSinrMin) {
		this.ssSinrMin = ssSinrMin;
	}
	public String getSsSinrMax() {
		return ssSinrMax;
	}
	public void setSsSinrMax(String ssSinrMax) {
		this.ssSinrMax = ssSinrMax;
	}
	public String getSsUlMin() {
		return ssUlMin;
	}
	public void setSsUlMin(String ssUlMin) {
		this.ssUlMin = ssUlMin;
	}
	public String getSsUlMax() {
		return ssUlMax;
	}
	public void setSsUlMax(String ssUlMax) {
		this.ssUlMax = ssUlMax;
	}
	public String getSsDlMin() {
		return ssDlMin;
	}
	public void setSsDlMin(String ssDlMin) {
		this.ssDlMin = ssDlMin;
	}
	public String getSsDlMax() {
		return ssDlMax;
	}
	public void setSsDlMax(String ssDlMax) {
		this.ssDlMax = ssDlMax;
	}
	public MemoryConfig(String projId, String userId, Date createTime, String showFlag, String pointDetail,
			String itemType, String stationFlag4g, String stationFlag5g, String indoorFlag, String outdoorFlag,
			String serviceCellFlag, String neighborhoodFlag, String showPointValFlag, String showPointSizeFlag,
			String eventAbnormalFlag, String eventNormalFlag) {
		super();
		this.projId = projId;
		this.userId = userId;
		this.createTime = createTime;
		this.showFlag = showFlag;
		this.pointDetail = pointDetail;
		this.itemType = itemType;
		this.stationFlag4g = stationFlag4g;
		this.stationFlag5g = stationFlag5g;
		this.indoorFlag = indoorFlag;
		this.outdoorFlag = outdoorFlag;
		this.serviceCellFlag = serviceCellFlag;
		this.neighborhoodFlag = neighborhoodFlag;
		this.showPointValFlag = showPointValFlag;
		this.showPointSizeFlag = showPointSizeFlag;
		this.eventAbnormalFlag = eventAbnormalFlag;
		this.eventNormalFlag = eventNormalFlag;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public MemoryConfig(String projId, String userId, Date createTime, String showFlag, String pointDetail,
			String stationFlag4g, String stationFlag5g, String indoorFlag, String outdoorFlag, String serviceCellFlag,
			String neighborhoodFlag, String showPointValFlag, String showPointSizeFlag, String eventAbnormalFlag,
			String eventNormalFlag) {
		super();
		this.projId = projId;
		this.userId = userId;
		this.createTime = createTime;
		this.showFlag = showFlag;
		this.pointDetail = pointDetail;
		this.stationFlag4g = stationFlag4g;
		this.stationFlag5g = stationFlag5g;
		this.indoorFlag = indoorFlag;
		this.outdoorFlag = outdoorFlag;
		this.serviceCellFlag = serviceCellFlag;
		this.neighborhoodFlag = neighborhoodFlag;
		this.showPointValFlag = showPointValFlag;
		this.showPointSizeFlag = showPointSizeFlag;
		this.eventAbnormalFlag = eventAbnormalFlag;
		this.eventNormalFlag = eventNormalFlag;
	}
	public String getEventAbnormalFlag() {
		return eventAbnormalFlag;
	}
	public void setEventAbnormalFlag(String eventAbnormalFlag) {
		this.eventAbnormalFlag = eventAbnormalFlag;
	}
	public String getEventNormalFlag() {
		return eventNormalFlag;
	}
	public void setEventNormalFlag(String eventNormalFlag) {
		this.eventNormalFlag = eventNormalFlag;
	}
	
	public String getStationFlag4g() {
		return stationFlag4g;
	}
	public void setStationFlag4g(String stationFlag4g) {
		this.stationFlag4g = stationFlag4g;
	}
	public String getStationFlag5g() {
		return stationFlag5g;
	}
	public void setStationFlag5g(String stationFlag5g) {
		this.stationFlag5g = stationFlag5g;
	}
	public String getIndoorFlag() {
		return indoorFlag;
	}
	public void setIndoorFlag(String indoorFlag) {
		this.indoorFlag = indoorFlag;
	}
	public String getOutdoorFlag() {
		return outdoorFlag;
	}
	public void setOutdoorFlag(String outdoorFlag) {
		this.outdoorFlag = outdoorFlag;
	}
	public String getServiceCellFlag() {
		return serviceCellFlag;
	}
	public void setServiceCellFlag(String serviceCellFlag) {
		this.serviceCellFlag = serviceCellFlag;
	}
	public String getNeighborhoodFlag() {
		return neighborhoodFlag;
	}
	public void setNeighborhoodFlag(String neighborhoodFlag) {
		this.neighborhoodFlag = neighborhoodFlag;
	}
	public String getShowPointValFlag() {
		return showPointValFlag;
	}
	public void setShowPointValFlag(String showPointValFlag) {
		this.showPointValFlag = showPointValFlag;
	}
	public String getShowPointSizeFlag() {
		return showPointSizeFlag;
	}
	public void setShowPointSizeFlag(String showPointSizeFlag) {
		this.showPointSizeFlag = showPointSizeFlag;
	}
	
	public String getProjId() {
		return projId;
	}
	public void setProjId(String projId) {
		this.projId = projId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getShowFlag() {
		return showFlag;
	}
	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}
	public String getPointDetail() {
		return pointDetail;
	}
	public void setPointDetail(String pointDetail) {
		this.pointDetail = pointDetail;
	}
	public MemoryConfig(String projId, String userId, Date createTime, String showFlag, String pointDetail) {
		super();
		this.projId = projId;
		this.userId = userId;
		this.createTime = createTime;
		this.showFlag = showFlag;
		this.pointDetail = pointDetail;
	}
	public MemoryConfig() {
		super();
	}

	
	

}
