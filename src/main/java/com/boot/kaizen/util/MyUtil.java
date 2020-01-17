package com.boot.kaizen.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.entity.RequestParamEntity;
import com.boot.kaizen.enump.Constant;

/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:37:32
 */
public class MyUtil {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");
	private static double EARTH_RADIUS = 6378.137;// 单位千米

	/**
	 * 校验map的kv 将空的remove掉
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月21日 下午2:56:08
	 */
	public static Map<String, Object> clearMapEmptyVal(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<>();
		if (map != null && !map.isEmpty()) {
			Set<Entry<String, Object>> entrySet = map.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (StringUtils.isNoneBlank(key)) {
					if (value != null) {
						String val = value.toString();
						if (StringUtils.isNoneBlank(val)) {
							resultMap.put(key, value);
						}
					}
				}
			}
		}
		return resultMap;
	}

	/**
	 * 创建EntityWrapper 条件构造器（针对mybatis-plus） conditionSymple:目前仅支持 LIKE,AND这两个条件
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @param <T>
	 * @date 2019年10月22日 上午11:54:40
	 */
	public static <T> EntityWrapper<T> createEntityWrapper(Map<String, Object> conditionLikeMap, String defultFiledDesc,
			String conditionSymple) {
		EntityWrapper<T> qryWrapper = new EntityWrapper<>();
		qryWrapper.orderDesc(Arrays.asList(defultFiledDesc));
		if (conditionLikeMap != null && !conditionLikeMap.isEmpty()) {
			Set<Entry<String, Object>> entrySet = conditionLikeMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				if (("AND").equals(conditionSymple)) {
					qryWrapper.and(entry.getKey() + "={0}", entry.getValue());
				} else {
					qryWrapper.like(entry.getKey(), entry.getValue().toString());
				}
			}
		}
		return qryWrapper;
	}

	/**
	 *
	 * @Description: 针对mybatis plus构造查询器 这个对map 参数 不处理 不包含这个参数
	 * @author weichengz
	 * @date 2019年12月6日 下午2:05:56
	 */
	public static <T> EntityWrapper<T> createQueryPlus(RequestParamEntity param) {
		EntityWrapper<T> qryWrapper = new EntityWrapper<>();
		// 对等值查询条件进行过滤 "" null的 全部移除
		Map<String, Object> mapAnd = param.getMapAnd();
		for (Iterator<Map.Entry<String, Object>> it = mapAnd.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> item = it.next();
			String key = item.getKey();
			Object value = item.getValue();
			if (StringUtils.isNotBlank(key)) {
				if (value != null) {
					if (StringUtils.isNotBlank(value.toString().trim())) {// 添加查询的条件
						qryWrapper.and(key + "={0}", value);
					}
				}
			}
		}
		// 不等于判断
		Map<String, Object> mapNo = param.getMapNo();
		for (Iterator<Map.Entry<String, Object>> it = mapNo.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> item = it.next();
			String key = item.getKey();
			Object value = item.getValue();
			if (StringUtils.isNotBlank(key)) {
				if (value != null) {
					if (StringUtils.isNotBlank(value.toString().trim())) {// 添加查询的条件
						qryWrapper.ne(key, value.toString());
					}
				}
			}
		}
		// 对模糊条件进行过滤 "" null的 全部移除
		Map<String, Object> mapLike = param.getMapLike();
		for (Iterator<Map.Entry<String, Object>> it = mapLike.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> item = it.next();
			String key = item.getKey();
			Object value = item.getValue();
			if (StringUtils.isNotBlank(key)) {
				if (value != null) {
					if (StringUtils.isNotBlank(value.toString().trim())) {// 添加查询的条件
						qryWrapper.like(key, value.toString().trim());
					}
				}
			}
		}
		
		// 对范围条件进行处理 仅仅支持LTE GTE 范围只有一个 那么就是大于或者小于查询 范围两个就是范围的查询
		Map<String, Map<String, Object>> mapRange = param.getMapRange();
		for (Iterator<Map.Entry<String, Map<String, Object>>> it = mapRange.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Map<String, Object>> item = it.next();
			String key = item.getKey();
			Map<String, Object> valueMap = item.getValue();

			if (StringUtils.isBlank(key)) {
				if (valueMap != null && !valueMap.isEmpty()) {
					if (valueMap.size() == 2) {// 这个是范围的查询
						Set<Entry<String, Object>> entrySet = valueMap.entrySet();
						String minVal = null;
						String maxVal = null;
						for (Entry<String, Object> entry : entrySet) {
							String key2 = entry.getKey();
							Object value = entry.getValue();
							if (StringUtils.isNotBlank(key2)) {
								if (value != null) {
									if (StringUtils.isNotBlank(value.toString())) {
										if (("LTE").equals(key2)) {
											maxVal = value.toString();
										}
										if (("GTE").equals(key2)) {
											minVal = value.toString();
										}
									}
								}
							}
						}
						if (StringUtils.isNotBlank(minVal) && StringUtils.isNotBlank(maxVal)) {// 范围查询
							qryWrapper.between(key, minVal, maxVal);
						}
					}
					if (valueMap.size() == 1) {// 这个是大于小于的查询
						Set<Entry<String, Object>> entrySet = valueMap.entrySet();
						for (Entry<String, Object> entry : entrySet) {
							String key2 = entry.getKey();
							Object value = entry.getValue();
							if (StringUtils.isNotBlank(key2)) {
								if (value != null) {
									if (StringUtils.isNotBlank(value.toString())) {
										if (("LTE").equals(key2)) {
											qryWrapper.le(key, value.toString());
										}
										if (("GTE").equals(key2)) {
											qryWrapper.ge(key, value.toString());
										}
									}
								}
							}
						}
					}
				}
			}
		}

		List<String> orders = param.getOrders();
		if (orders != null && orders.size() > 0) {// 这里处理排序
			qryWrapper.orderDesc(orders);
		}
		return qryWrapper;
	}

	public static <T> EntityWrapper<T> createEntityWrapperDeep(Map<String, Object> conditionLikeMap,
			List<String> orderFields, String order, String conditionSymple) {
		EntityWrapper<T> qryWrapper = new EntityWrapper<>();
		if (("ASC").equals(order)) {
			qryWrapper.orderAsc(orderFields);
		} else {
			qryWrapper.orderDesc(orderFields);
		}
		if (conditionLikeMap != null && !conditionLikeMap.isEmpty()) {
			Set<Entry<String, Object>> entrySet = conditionLikeMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				if (("AND").equals(conditionSymple)) {
					qryWrapper.and(entry.getKey() + "={0}", entry.getValue());
				} else {
					qryWrapper.like(entry.getKey(), entry.getValue().toString());
				}
			}
		}
		return qryWrapper;
	}

	public static String getUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static Long getDealProjId(LoginUser user) {
		if (Constant.SYSTEM_ID_PROJECT != user.getProjId()) {
			return user.getProjId();
		}
		return null;
	};

	/**
	 * 将图片填充到excel
	 * 做列；做行，右列，右行
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年10月29日 下午1:57:55
	 */
	public static void picToExcel(HSSFWorkbook workbook, HSSFPatriarch patriarch,String basePath,String dbFileName,int ydbOne,int ydbTwo,int xdbOne,int xdbTwo)
			throws IOException {
		if (StringUtils.isNoneBlank(dbFileName)) {
			File file = new File(basePath + dbFileName);
			if (file.exists()) {
				dbFileName = file.getAbsolutePath();
			}
		}
		if (StringUtils.isNoneBlank(dbFileName)) {
			MyUtil.createExcelPic(workbook, patriarch, dbFileName, (short) ydbOne, ydbTwo, (short) xdbOne, xdbTwo);
		}
	}

	/**
	 * 经纬度计算两点之间的距离
	 * 
	 * 单位为米
	 * 
	 * 纬度lat1 经度lng1
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = getRadian(lat1);
		double radLat2 = getRadian(lat2);
		double a = radLat1 - radLat2;// 两点纬度差
		double b = getRadian(lng1) - getRadian(lng2);// 两点的经度差
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return s * 1000;
	}

	private static double getRadian(double degree) {
		return degree * Math.PI / 180.0;
	}

	/**
	 * 
	 * @Description: 如果字段为null就设置为 ""
	 * @author weichengz
	 * @date 2019年2月21日 下午11:05:12
	 */
	public static String nullToEmpty(String field) {
		if (StringUtils.isBlank(field)) {
			return "";
		}
		return field;
	}

	/**
	 * 求两个数字的百分数 保留两位小数
	 */
	public static String getNumPercent(String a, String b) {
		String result = "";
		try {
			if (StringUtils.isNoneBlank(a) && StringUtils.isNoneBlank(b)) {
				Double aNum = Double.valueOf(a.toString());
				Double bNum = Double.valueOf(b.toString());
				Double percent = aNum / bNum;
				NumberFormat nt = NumberFormat.getPercentInstance();
				nt.setMinimumFractionDigits(2);
				result = nt.format(percent);
			}
		} catch (Exception e) {
			log.info("异常：" + e);
		}
		return result;
	};

	/**
	 * 导出图片到excel
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年11月17日 下午8:10:22
	 */
	public static void createExcelPic(HSSFWorkbook workbook, HSSFPatriarch patriarch, String sinrFtpUpImage1,
			Short leftCol, Integer leftRow, Short rughtCol, Integer rightRow) throws IOException {
		if (StringUtils.isNoneBlank(sinrFtpUpImage1)) {
			BufferedImage bufferImg3 = null;
			ByteArrayOutputStream byteArrayOut3 = new ByteArrayOutputStream();
			File file = new File(sinrFtpUpImage1);
			if (file.exists()) {
				bufferImg3 = ImageIO.read(file);
				ImageIO.write(bufferImg3, getPictureType(sinrFtpUpImage1), byteArrayOut3);
				HSSFClientAnchor anchor3 = new HSSFClientAnchor(0, 0, 0, 0, leftCol, leftRow, rughtCol, rightRow);
				if (("png").equals(getPictureType(sinrFtpUpImage1))) {
					patriarch.createPicture(anchor3,
							workbook.addPicture(byteArrayOut3.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
				} else {
					patriarch.createPicture(anchor3,
							workbook.addPicture(byteArrayOut3.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
				}
			}
		}
	}

	/**
	 * 得到图片的类型
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2018年11月17日 下午8:10:31
	 */
	public static String getPictureType(String fileName) {
		String proType = fileName.substring(fileName.lastIndexOf(".") + 1);
		return proType;
	}

	/**
	 * 将kv信息传放到map里面，注意kv信息以【~】分隔 k必须存在
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年3月17日 下午1:40:16
	 */
	public static Map<String, Object> createHashMap(String... params) {
		Map<String, Object> paramMap = new HashMap<>();
		if (params != null && params.length > 0) {
			for (String p : params) {
				String[] arrayKv = p.split("~");
				if (arrayKv.length == 1) {
					paramMap.put(arrayKv[0], "");
					continue;
				}
				if (arrayKv.length == 2) {
					paramMap.put(arrayKv[0], arrayKv[1]);
					continue;
				}
				paramMap.clear();
				throw new IllegalArgumentException("kv信息异常");
			}
		}
		return paramMap;
	}

	/**
	 * 处理空字符串未指定的字符串
	 * 
	 * @Description: TODO
	 * @author weichengz
	 * @date 2019年3月17日 下午1:52:48
	 */
	public static String dealEmptyStr(String str, String replaceStrIfEmpty) {
		return StringUtils.isBlank(str) ? replaceStrIfEmpty : str;
	}

}
