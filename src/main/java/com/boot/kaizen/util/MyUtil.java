package com.boot.kaizen.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.boot.kaizen.entity.LoginUser;
import com.boot.kaizen.enump.Constant;
/**
 * 
 * @author weichengz
 * @date 2019年5月31日 上午11:37:32
 */
public class MyUtil {

	private static double EARTH_RADIUS = 6378.137;// 单位千米
	
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
	 * 经纬度计算两点之间的距离
	 * 
	 * 单位为米
	 * 
	 * 纬度lat1  经度lng1
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = getRadian(lat1);
		double radLat2 = getRadian(lat2);
		double a = radLat1 - radLat2;// 两点纬度差
		double b = getRadian(lng1) - getRadian(lng2);// 两点的经度差
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
				* Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
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
			e.printStackTrace();
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
				System.out.println(p);
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
