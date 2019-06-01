package com.boot.kaizen.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;

/**
 * excel处理工具类
 * @author a-zhangweicheng
 *
 */
public class ExcelUtil {

	@SuppressWarnings("deprecation")
	public static String cell_string(HSSFCell cell) {
		if (cell == null) {
			return "";
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell.getStringCellValue();
	}

	public static String dealDateToString(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.YEAR, 1900);
		cal1.set(Calendar.DAY_OF_MONTH, 1);
		cal1.set(Calendar.DAY_OF_YEAR, 1);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);

		cal1.add(Calendar.DAY_OF_YEAR, Integer.parseInt(strDate) - 2);
		Date date = cal1.getTime();
		return sdf.format(date);
	}
}
