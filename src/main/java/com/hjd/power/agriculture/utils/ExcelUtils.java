package com.hjd.power.agriculture.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hjd.power.agriculture.Enums.ConfigEnum;
import com.hjd.power.agriculture.domain.SimpleMailMessageVO;
import com.hjd.power.agriculture.service.IEmailService;

@Component
public class ExcelUtils {
	public static IEmailService emailService;

	public ExcelUtils() {
	}

	@Autowired
	public ExcelUtils(IEmailService emailService) {
		ExcelUtils.emailService = emailService;
	}

	public static XSSFSheet createExcelTitle(int lastCol, XSSFWorkbook workbook) {
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		sheet.setDefaultColumnWidth((short) 15);

		// 表头的样式
		XSSFCellStyle titleStyle = workbook.createCellStyle();// 样式对象
		titleStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置字体
		XSSFFont titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 15);
		titleFont.setBold(true);
		titleFont.setColor(HSSFColorPredefined.WHITE.getIndex());
		titleStyle.setFont(titleFont);
		titleStyle.setFillForegroundColor(HSSFColorPredefined.ROYAL_BLUE.getIndex());
		titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, lastCol - 1));
		// 指定合并区域
		XSSFRow rowHeader = sheet.createRow(0);
		rowHeader.setHeight((short) 600);
		XSSFCell cellHeader = rowHeader.createCell(0);
		XSSFRichTextString textHeader = new XSSFRichTextString(
				CommonUtils.getSysConfig(ConfigEnum.SYSTEM_NAME.getCode()));
		cellHeader.setCellStyle(titleStyle);
		cellHeader.setCellValue(textHeader);
		return sheet;
	}

	public static void createExcelHeader(XSSFWorkbook workbook, XSSFSheet sheet, String[] headers, int headersLength,
			int rowIndex, int offset) {
		XSSFRow row = sheet.createRow(rowIndex);
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
		cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
		cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		for (int i = 0; i < headersLength; i++) {
			XSSFCell cell = row.createCell(i);
			if (i >= offset && i <= headers.length) {

				cell.setCellValue(headers[i - offset]);
			}
			cell.setCellStyle(cellStyle);
		}
	}

	public static CellStyle cellStyle(XSSFWorkbook workbook, HSSFColorPredefined predefined) {
		XSSFFont font3 = workbook.createFont();
		font3.setColor(predefined.getIndex());

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font3);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		return cellStyle;
	}

	public static CellStyle lighthouseCellStyle(XSSFWorkbook workbook, HSSFColorPredefined predefined) {
		XSSFFont font3 = workbook.createFont();
		font3.setColor(predefined.getIndex());

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font3);

		// cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
		cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
		cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		cellStyle.setFillForegroundColor(HSSFColorPredefined.SKY_BLUE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return cellStyle;
	}

	public static CellStyle boderCellStyle(XSSFWorkbook workbook, HSSFColorPredefined predefined) {
		XSSFFont font3 = workbook.createFont();
		font3.setColor(predefined.getIndex());

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font3);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		// cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
		cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
		cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
		return cellStyle;
	}

	public static CellStyle formatFillCellStyle(XSSFWorkbook workbook, HSSFColorPredefined predefined, String format) {

		XSSFFont font3 = workbook.createFont();
		font3.setColor(predefined.getIndex());
		XSSFDataFormat dataFormat = workbook.createDataFormat();
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font3);
		cellStyle.setDataFormat(dataFormat.getFormat(format));
		// cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
		cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
		cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		cellStyle.setFillForegroundColor(HSSFColorPredefined.SKY_BLUE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return cellStyle;
	}

	public static CellStyle formatCellStyle(XSSFWorkbook workbook, HSSFColorPredefined predefined, String format) {
		XSSFFont font3 = workbook.createFont();
		XSSFDataFormat dataFormat = workbook.createDataFormat();
		font3.setColor(predefined.getIndex());

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font3);
		cellStyle.setDataFormat(dataFormat.getFormat(format));
		// cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
		cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
		cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
		cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		return cellStyle;
	}

	public static void workbookWrite(String fileName, int size, XSSFWorkbook workbook) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String dateTime = dateFormat.format(new Date());
		File f = new File("src/main/resources/static/export/" + dateTime + "/"
				+ UUID.randomUUID().toString().replaceAll("-", "") + "/" + fileName);
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(f);
		workbook.write(out);
		out.close();

		SimpleMailMessageVO vo = new SimpleMailMessageVO();
		vo.setSubject("Export Total Data " + UUID.randomUUID().toString());
		vo.setText(UUID.randomUUID().toString() + "Export Total Data Rows Size " + size);
		vo.setTo(CommonUtils.getSysConfig(ConfigEnum.EMAIL_ADDRESS.getCode()));
		emailService.sendMail(vo, f);
	}
}
