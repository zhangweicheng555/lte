package com.boot.kaizen.business.es.model.sim;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * sim同步的时候的工参存储信息
 * 
 * @author weichengz
 * @date 2019年11月19日 下午3:23:25
 */
public class GcModel extends CommonModel implements Serializable {

	private static final long serialVersionUID = 1L;

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

}
