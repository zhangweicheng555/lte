package com.boot.kaizen.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
/**
 * csv导出工具累
 * @author weichengz
 * @date 2020年2月25日 上午10:23:29
 */
public class CsvUtils {

	public static void simpleExport(boolean quoteAllFields, String lineSeparator, String[] heads, List<Object[]> data,
			String fileName, OutputStream outputStream) throws IOException {

		CsvWriterSettings settings = new CsvWriterSettings();

		settings.setQuoteAllFields(quoteAllFields);

		// 分割线使用系统默认

		settings.getFormat().setLineSeparator(lineSeparator);

		settings.setIgnoreLeadingWhitespaces(false);

		settings.setIgnoreTrailingWhitespaces(false);

		settings.setHeaders(heads);

		OutputStream csvResult = outputStream;
		//新增开始解决excel打开csv乱码的问题
		byte [] bs = { (byte)0xEF, (byte)0xBB, (byte)0xBF};   //new added
		csvResult.write(bs);
		//新增结束
		// FileOutputStream csvResult = new FileOutputStream("C:\\temp\\test.csv");
		// ByteArrayOutputStream csvResult = new ByteArrayOutputStream();
		CsvWriter writer = new CsvWriter(new OutputStreamWriter(csvResult, "UTF-8"), settings);

		writer.writeHeaders();

		writer.writeRows(data);

		writer.close();
		try {
			csvResult.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
