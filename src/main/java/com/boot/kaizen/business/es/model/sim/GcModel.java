package com.boot.kaizen.business.es.model.sim;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * sim同步的时候的工参存储信息
 * 
 * @author weichengz
 * @date 2019年11月19日 下午3:23:25
 */
public class GcModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String lte_city_name;// 城市
	private String lte_net;// 网络
	private String lte_enodebid;// eNodeBID
	private String lte_sector_id;// Sector ID
	private String lte_cell;// Cell
	private String lte_ci;// CI
	private String lte_ecgi;// lte_ecgi
	private String lte_phycellid;// PhyCellId 这个就是PCI
	private String lte_longitude2;// 经度 百度的
	private String lte_latitude2;// 纬度 百度的
	private String lte_longitude;
	private String lte_latitude;
	private String lte_site_tall;// 站高
	private String lte_azimuth;// 方位角
	private String lte_mechanical_downdip;// 机械下倾角
	private String lte_electronic_downdip;// 电子倾角
	private String lte_total_downdip;// 总下倾角
	private String lte_tac;// TAC
	private String lte_sys;// 制式
	private String lte_site_type;// 站型
	private String lte_earfcn;// EARFCN
	private String lte_derrick_type;// 抱杆类型
	private String lte_address;// 详细地址
	private String lte_scene;// 场景归属
	private String lte_grid;// 网格归属
	private String lte_firm;// 厂商
	// private String lte_enodeb_name;// eNodeB Name
	// private String lte_site_name;// 站名

	private Map<String, Double> location;

	public Map<String, Double> getLocation() {
		return location;
	}

	/**
	 * 经纬度做一下处理
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年11月21日 上午10:52:33 108.265221 22.89169
	 */
	public void dealLocation() {
		Map<String, Double> paramMap = new LinkedHashMap<String, Double>();
		if (StringUtils.isNotBlank(lte_latitude2) && StringUtils.isNotBlank(lte_longitude2)) {
			paramMap.put("lat", Double.valueOf(lte_latitude2.toString()));
			paramMap.put("lon", Double.valueOf(lte_longitude2.toString()));
		}
		this.location = paramMap;
	}

	public void setLocation(Map<String, Double> location) {
		this.location = location;
	}

	public GcModel(String lte_city_name, String lte_net, String lte_enodebid, String lte_sector_id, String lte_cell,
			String lte_ci, String lte_phycellid, String lte_longitude2, String lte_longitude, String lte_latitude2,
			String lte_latitude, String lte_site_tall, String lte_azimuth, String lte_mechanical_downdip,
			String lte_electronic_downdip, String lte_total_downdip, String lte_tac, String lte_sys,
			String lte_site_type, String lte_earfcn, String lte_derrick_type, String lte_address, String lte_scene,
			String lte_grid, String lte_firm) {
		super();
		this.lte_city_name = lte_city_name;
		this.lte_net = lte_net;
		this.lte_enodebid = lte_enodebid;
		this.lte_sector_id = lte_sector_id;
		this.lte_cell = lte_cell;
		this.lte_ci = lte_ci;
		this.lte_phycellid = lte_phycellid;
		this.lte_longitude2 = lte_longitude2;
		this.lte_longitude = lte_longitude;
		this.lte_latitude2 = lte_latitude2;
		this.lte_latitude = lte_latitude;
		this.lte_site_tall = lte_site_tall;
		this.lte_azimuth = lte_azimuth;
		this.lte_mechanical_downdip = lte_mechanical_downdip;
		this.lte_electronic_downdip = lte_electronic_downdip;
		this.lte_total_downdip = lte_total_downdip;
		this.lte_tac = lte_tac;
		this.lte_sys = lte_sys;
		this.lte_site_type = lte_site_type;
		this.lte_earfcn = lte_earfcn;
		this.lte_derrick_type = lte_derrick_type;
		this.lte_address = lte_address;
		this.lte_scene = lte_scene;
		this.lte_grid = lte_grid;
		this.lte_firm = lte_firm;
	}

	public String getLte_ecgi() {
		return lte_ecgi;
	}

	public void setLte_ecgi(String lte_ecgi) {
		this.lte_ecgi = lte_ecgi;
	}

	public GcModel() {
		super();
	}

	public String getLte_city_name() {
		return lte_city_name;
	}

	public void setLte_city_name(String lte_city_name) {
		this.lte_city_name = lte_city_name;
	}

	public String getLte_net() {
		return lte_net;
	}

	public void setLte_net(String lte_net) {
		this.lte_net = lte_net;
	}

	public String getLte_enodebid() {
		return lte_enodebid;
	}

	public void setLte_enodebid(String lte_enodebid) {
		this.lte_enodebid = lte_enodebid;
	}

	public String getLte_sector_id() {
		return lte_sector_id;
	}

	public void setLte_sector_id(String lte_sector_id) {
		this.lte_sector_id = lte_sector_id;
	}

	public String getLte_cell() {
		return lte_cell;
	}

	public void setLte_cell(String lte_cell) {
		this.lte_cell = lte_cell;
	}

	public String getLte_ci() {
		return lte_ci;
	}

	public void setLte_ci(String lte_ci) {
		this.lte_ci = lte_ci;
	}

	public String getLte_phycellid() {
		return lte_phycellid;
	}

	public void setLte_phycellid(String lte_phycellid) {
		this.lte_phycellid = lte_phycellid;
	}

	public String getLte_longitude2() {
		return lte_longitude2;
	}

	public void setLte_longitude2(String lte_longitude2) {
		this.lte_longitude2 = lte_longitude2;
	}

	public String getLte_longitude() {
		return lte_longitude;
	}

	public void setLte_longitude(String lte_longitude) {
		this.lte_longitude = lte_longitude;
	}

	public String getLte_latitude2() {
		return lte_latitude2;
	}

	public void setLte_latitude2(String lte_latitude2) {
		this.lte_latitude2 = lte_latitude2;
	}

	public String getLte_latitude() {
		return lte_latitude;
	}

	public void setLte_latitude(String lte_latitude) {
		this.lte_latitude = lte_latitude;
	}

	public String getLte_site_tall() {
		return lte_site_tall;
	}

	public void setLte_site_tall(String lte_site_tall) {
		this.lte_site_tall = lte_site_tall;
	}

	public String getLte_azimuth() {
		return lte_azimuth;
	}

	public void setLte_azimuth(String lte_azimuth) {
		this.lte_azimuth = lte_azimuth;
	}

	public String getLte_mechanical_downdip() {
		return lte_mechanical_downdip;
	}

	public void setLte_mechanical_downdip(String lte_mechanical_downdip) {
		this.lte_mechanical_downdip = lte_mechanical_downdip;
	}

	public String getLte_electronic_downdip() {
		return lte_electronic_downdip;
	}

	public void setLte_electronic_downdip(String lte_electronic_downdip) {
		this.lte_electronic_downdip = lte_electronic_downdip;
	}

	public String getLte_total_downdip() {
		return lte_total_downdip;
	}

	public void setLte_total_downdip(String lte_total_downdip) {
		this.lte_total_downdip = lte_total_downdip;
	}

	public String getLte_tac() {
		return lte_tac;
	}

	public void setLte_tac(String lte_tac) {
		this.lte_tac = lte_tac;
	}

	public String getLte_sys() {
		return lte_sys;
	}

	public void setLte_sys(String lte_sys) {
		this.lte_sys = lte_sys;
	}

	public String getLte_site_type() {
		return lte_site_type;
	}

	public void setLte_site_type(String lte_site_type) {
		this.lte_site_type = lte_site_type;
	}

	public String getLte_earfcn() {
		return lte_earfcn;
	}

	public void setLte_earfcn(String lte_earfcn) {
		this.lte_earfcn = lte_earfcn;
	}

	public String getLte_derrick_type() {
		return lte_derrick_type;
	}

	public void setLte_derrick_type(String lte_derrick_type) {
		this.lte_derrick_type = lte_derrick_type;
	}

	public String getLte_address() {
		return lte_address;
	}

	public void setLte_address(String lte_address) {
		this.lte_address = lte_address;
	}

	public String getLte_scene() {
		return lte_scene;
	}

	public void setLte_scene(String lte_scene) {
		this.lte_scene = lte_scene;
	}

	public String getLte_grid() {
		return lte_grid;
	}

	public void setLte_grid(String lte_grid) {
		this.lte_grid = lte_grid;
	}

	public String getLte_firm() {
		return lte_firm;
	}

	public void setLte_firm(String lte_firm) {
		this.lte_firm = lte_firm;
	}

}
