package com.boot.kaizen.business.es.service;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.boot.kaizen.business.es.model.OneButtonTest;
import com.boot.kaizen.business.es.model.OutHomeLogModel;
import com.boot.kaizen.business.es.model.QueryParamData;

/**
 * es 业务逻辑层
 * 
 * @author weichengz
 * @date 2019年11月12日 上午10:29:13
 */
@Service
public class EsBussService implements IEsBussService {

	@Cacheable(value = "queryPeopleNumByTimeRange", key = "#pid")
	@Override
	public List<Map<String, Object>> queryMainLog(String pid, QueryParamData queryParamData) {
		return Esutil.scrollQuery(queryParamData);
	}

	@Override
	public void exportOneButtonTest(String[] headers, Collection<OneButtonTest> dataset, String title,
			HttpServletResponse response) throws Exception {

		OutputStream out = response.getOutputStream();
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth((int) 15);/*** 不变 */

		/*** 标题样式 */
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);

		/** 内容样式 */
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style2.setFont(font2);

		/*** 声明一个画图的顶级管理器 */
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		comment.setString(new HSSFRichTextString("一共导出" + dataset.size() + "条数据,导出时间" + dateformat.format(new Date())
				+ "                         注意：如果导出的数据量不符合查询要求,请重新查询一遍在导出"));

		/** 产生标题行 */
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		/*** 遍历集合数据 */
		Iterator<OneButtonTest> it = dataset.iterator();
		int index = 0;
		HSSFCell cell;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			OneButtonTest t = it.next();

			cell = row.createCell((int) 0);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getOperatorService());

			cell = row.createCell((int) 1);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getNetWorkType());

			cell = row.createCell((int) 2);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getCity());

			cell = row.createCell((int) 3);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getCounty());

			cell = row.createCell((int) 4);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getTestPerson());

			cell = row.createCell((int) 5);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getPhoneType());

			cell = row.createCell((int) 6);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getPhoneNo());

			cell = row.createCell((int) 7);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getImsi());

			cell = row.createCell((int) 8);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getTestTime());

			cell = row.createCell((int) 9);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getLatitude());

			cell = row.createCell((int) 10);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getLongitude());

			cell = row.createCell((int) 11);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getTestLocation());

			cell = row.createCell((int) 12);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getDownRate());

			cell = row.createCell((int) 13);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getUpRate());

			cell = row.createCell((int) 14);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getPingFlag());

			cell = row.createCell((int) 15);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getHttpFlag());

			cell = row.createCell((int) 16);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getAvgRsrp());

			cell = row.createCell((int) 17);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getAvgSinr());

			cell = row.createCell((int) 18);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getCellName());

			cell = row.createCell((int) 19);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getEnodebid());

			cell = row.createCell((int) 20);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getCi());

			cell = row.createCell((int) 21);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getFrequencyBand());

			cell = row.createCell((int) 22);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getPci());

			cell = row.createCell((int) 23);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getTac());

		}
		try {
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + UUID.randomUUID().toString().replace("-", "") + ".xls");
			out = response.getOutputStream();
			workbook.write(out);
		} catch (IOException e) {

		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void exportLogoutHomeTest(String[] headers, Collection<OutHomeLogModel> dataset, String title,
			HttpServletResponse response) throws Exception {
		
		OutputStream out = response.getOutputStream();
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth((int) 15);/*** 不变 */
		
		/*** 标题样式 */
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		
		/** 内容样式 */
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style2.setFont(font2);
		
		/*** 声明一个画图的顶级管理器 */
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		comment.setString(new HSSFRichTextString("一共导出" + dataset.size() + "条数据,导出时间" + dateformat.format(new Date())
		+ "                         注意：如果导出的数据量不符合查询要求,请重新查询一遍在导出"));
		
		/** 产生标题行 */
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		
		/*** 遍历集合数据 */
		Iterator<OutHomeLogModel> it = dataset.iterator();
		int index = 0;
		HSSFCell cell;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			OutHomeLogModel t = it.next();
			
			cell = row.createCell((int) 0);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getFileName());
			
			cell = row.createCell((int) 1);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getFileUpTime());
			
			cell = row.createCell((int) 2);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getOperatorService());
			
			cell = row.createCell((int) 3);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getNetWorkType());
			
			cell = row.createCell((int) 4);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getCity());
			
			cell = row.createCell((int) 5);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getCounty());
			
			cell = row.createCell((int) 6);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getTestPerson());
			
			cell = row.createCell((int) 7);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getPhoneType());
			
			cell = row.createCell((int) 8);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getImsi());
			
			cell = row.createCell((int) 9);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getTestTime());
			
			cell = row.createCell((int) 10);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getBeginTime());
			
			cell = row.createCell((int) 11);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getEndTime());
			
			cell = row.createCell((int) 12);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getTotalMileage());
			
			cell = row.createCell((int) 13);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getCoverPersent());
			
			cell = row.createCell((int) 14);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getAvgRsrp());
			
			cell = row.createCell((int) 15);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getAvgSinr());
			
			cell = row.createCell((int) 16);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getAvgDownRate());
			
			cell = row.createCell((int) 17);
			cell.setCellStyle(style2);
			cell.setCellValue(t.getAvgUpRate());
		}
		try {
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + UUID.randomUUID().toString().replace("-", "") + ".xls");
			out = response.getOutputStream();
			workbook.write(out);
		} catch (IOException e) {
			
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
