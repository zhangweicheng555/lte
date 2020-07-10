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
	private String showFlag;// 显示 默认是1    1是显示
	private String pointDetail;// 采样点详情 默认是1    1是显示
	private String itemType;// 阈值类型 默认是RSRP
	
	
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
