package com.boot.kaizen.business.es.model.sim;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.boot.kaizen.util.LngLatUtil;

/**
 * sim同步的时候的工参存储信息 注意这里考虑到一个问题 sim工参添加得时候 得有个地市得标记 因为 联通电信移动 可能都会存在想相同得地市
 * 
 * @author weichengz
 * @date 2019年11月19日 下午3:23:25
 */
public class GcModel extends CommonModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 工参定义得额外信息在这里配置
	 */
	private Map<String, Double> location;
	private String cityId;// 地市主键 就是原来项目主键    这个改为项目主键20200215    用于项目管理员登陆的时候看到全省的数据

	public Map<String, Double> getLocation() {
		return location;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public GcModel() {

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
			// 这个地方将百度经纬度转为WGS84
			String bd2wgs84 = LngLatUtil.bd2wgs84(Double.valueOf(lte_longitude2.toString()),
					Double.valueOf(lte_latitude2.toString()));
			if (bd2wgs84 != null) {
				String[] split = bd2wgs84.split("_");
				if (split != null && split.length == 2) {
					paramMap.put("lat", Double.valueOf(split[1]));
					paramMap.put("lon", Double.valueOf(split[0]));
				}
			}

		}
		this.location = paramMap;
	}

	public void setLocation(Map<String, Double> location) {
		this.location = location;
	}

	public GcModel(String lte_city_name, String lte_net, String lte_enodebid, String lte_sector_id, String lte_cell,
			String lte_ci, String lte_ecgi, String lte_phycellid, String lte_longitude2, String lte_latitude2,
			String lte_longitude, String lte_latitude, String lte_site_tall, String lte_azimuth,
			String lte_mechanical_downdip, String lte_electronic_downdip, String lte_total_downdip, String lte_tac,
			String lte_sys, String lte_site_type, String lte_earfcn, String lte_derrick_type, String lte_address,
			String lte_scene, String lte_grid, String lte_firm, Map<String, Double> location, String cityId) {
		super(lte_city_name, lte_net, lte_enodebid, lte_sector_id, lte_cell, lte_ci, lte_ecgi, lte_phycellid,
				lte_longitude2, lte_latitude2, lte_longitude, lte_latitude, lte_site_tall, lte_azimuth,
				lte_mechanical_downdip, lte_electronic_downdip, lte_total_downdip, lte_tac, lte_sys, lte_site_type,
				lte_earfcn, lte_derrick_type, lte_address, lte_scene, lte_grid, lte_firm);
		this.location = location;
		this.cityId = cityId;
	}

	public GcModel(CommonModel commonModel) {
		super(commonModel.getLte_city_name(), commonModel.getLte_net(), commonModel.getLte_enodebid(),
				commonModel.getLte_sector_id(), commonModel.getLte_cell(), commonModel.getLte_ci(),
				commonModel.getLte_ecgi(), commonModel.getLte_phycellid(), commonModel.getLte_longitude2(),
				commonModel.getLte_latitude2(), commonModel.getLte_longitude(), commonModel.getLte_latitude(),
				commonModel.getLte_site_tall(), commonModel.getLte_azimuth(), commonModel.getLte_mechanical_downdip(),
				commonModel.getLte_electronic_downdip(), commonModel.getLte_total_downdip(), commonModel.getLte_tac(),
				commonModel.getLte_sys(), commonModel.getLte_site_type(), commonModel.getLte_earfcn(),
				commonModel.getLte_derrick_type(), commonModel.getLte_address(), commonModel.getLte_scene(),
				commonModel.getLte_grid(), commonModel.getLte_firm(),commonModel.getLte_site_name());
		// this.location = location;
		// this.cityId = cityId;
		dealLocation();// 处理经纬度
	}

	/**
	 * 
	 * @Description: 将百度地图转为wgs84
	 * @author weichengz
	 * @date 2019年12月27日 下午4:44:28
	 */
	public void dealLngLatBdToWgs84() {
		if (StringUtils.isNotBlank(lte_latitude2) && StringUtils.isNotBlank(lte_longitude2)) {
			String bd2wgs84 = LngLatUtil.bd2wgs84(Double.valueOf(lte_longitude2), Double.valueOf(lte_latitude2));
			if (bd2wgs84 != null) {
				String[] split = bd2wgs84.split("_");
				if (split != null && split.length == 2) {
					lte_latitude2 = split[1];
					lte_longitude2 = split[0];
				}
			}
		}
	}

}
