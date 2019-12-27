package com.boot.kaizen.util;

import org.apache.commons.lang3.StringUtils;

public class LngLatUtil {
	// 定义一些常量
	static double x_PI = 3.14159265358979324 * 3000.0 / 180.0;
	static double PI = 3.1415926535897932384626;
	static double a = 6378245.0;
	static double ee = 0.00669342162296594323;

	/**
	 * 
	 * @Description: 百度转为wgs84
	 * @author weichengz
	 * @date 2019年12月27日 下午3:58:15
	 */
	public static String bd2wgs84(double bd_lon, double bd_lat) {
		String bd09togcj02 = bd09togcj02(bd_lon, bd_lat);
		if (StringUtils.isNotBlank(bd09togcj02)) {
			String[] lnglatArray = bd09togcj02.trim().split("_");
			if (lnglatArray != null && lnglatArray.length == 2) {
				return gcj02towgs84(Double.valueOf(lnglatArray[0]), Double.valueOf(lnglatArray[1]));
			}
		}
		return "";
	};

	
	/**
	 * wgs84转为百度的
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年12月27日 下午4:33:56
	 */
	public static String wgs842bd(double bd_lon, double bd_lat) {
		String bd09togcj02 = wgs84togcj02(bd_lon, bd_lat);
		if (StringUtils.isNotBlank(bd09togcj02)) {
			String[] lnglatArray = bd09togcj02.trim().split("_");
			if (lnglatArray != null && lnglatArray.length == 2) {
				return gcj02tobd09(Double.valueOf(lnglatArray[0]), Double.valueOf(lnglatArray[1]));
			}
		}
		return "";
	};

	/**
	 * 
	 * @Description: 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换
	 * @author weichengz
	 * @date 2019年12月27日 下午3:57:43
	 */
	public static String bd09togcj02(double bd_lon, double bd_lat) {
		double x = bd_lon - 0.0065;
		double y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_PI);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_PI);
		double gg_lng = z * Math.cos(theta);
		double gg_lat = z * Math.sin(theta);
		return gg_lng + "_" + gg_lat + "";
	};

	public static String gcj02tobd09(double lng, double lat) {
		double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * x_PI);
		double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * x_PI);
		double bd_lng = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		return bd_lng + "_" + bd_lat + "";
	};

	public static String wgs84togcj02(double lng, double lat) {
		if (out_of_china(lng, lat)) {
			return lng + "_" + lat + "";
		} else {
			double dlat = transformlat(lng - 105.0, lat - 35.0);
			double dlng = transformlng(lng - 105.0, lat - 35.0);
			double radlat = lat / 180.0 * PI;
			double magic = Math.sin(radlat);
			magic = 1 - ee * magic * magic;
			double sqrtmagic = Math.sqrt(magic);
			dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
			dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
			double mglat = lat + dlat;
			double mglng = lng + dlng;
			return mglng + "_" + mglat + "";

		}

	};

	public static String gcj02towgs84(double lng, double lat) {
		if (out_of_china(lng, lat)) {
			return lng + lat + "";// 不再国内不做偏移
		} else {
			double dlat = transformlat(lng - 105.0, lat - 35.0);
			double dlng = transformlng(lng - 105.0, lat - 35.0);
			double radlat = lat / 180.0 * PI;
			double magic = Math.sin(radlat);
			magic = 1 - ee * magic * magic;
			double sqrtmagic = Math.sqrt(magic);
			dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
			dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
			double mglat = lat + dlat;
			double mglng = lng + dlng;
			return (lng * 2 - mglng) + "_" + (lat * 2 - mglat) + "";
		}

	};

	/**
	 * 判断是否在国内，不在国内则不做偏移
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年12月27日 下午3:57:22
	 */
	public static boolean out_of_china(double lng, double lat) {
		return !(lng > 73.66 && lng < 135.05 && lat > 3.86 && lat < 53.55);
	};

	public static double transformlat(double lng, double lat) {
		double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat
				+ 0.2 * Math.sqrt(Math.abs(lng));
		ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
		return ret;
	};

	public static double transformlng(double lng, double lat) {
		double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
		ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
		return ret;
	};

}
