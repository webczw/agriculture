package com.hjd.power.agriculture.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hjd.power.agriculture.dao.ITotalDao;
import com.hjd.power.agriculture.domain.SimpleMailMessageVO;
import com.hjd.power.agriculture.domain.TotalVO;
import com.hjd.power.agriculture.service.ITotalService;
import com.hjd.power.agriculture.utils.CommonUtils;

@Service
public class TotalService implements ITotalService {
	@Autowired
	private ITotalDao totalDao;

	@Value("${agriculture.total.excel.title}")
	private String excelHeaders;

	@Value("${agriculture.system.name}")
	private String systemName;

	@Override
	public List<TotalVO> findList() throws Exception {
		return totalDao.findList();
	}

	@Override
	public TotalVO find(Integer totalId) throws Exception {
		return totalDao.find(totalId);
	}

	@Override
	public Integer create(TotalVO vo) throws Exception {
		CommonUtils.initCreate(vo);
		return totalDao.create(vo);
	}

	@Override
	public Integer update(TotalVO vo) throws Exception {
		CommonUtils.initUpdate(vo);
		return totalDao.update(vo);
	}

	@Override
	public Integer delete(Integer totalId) throws Exception {
		TotalVO totalVO = new TotalVO();
		totalVO.setTotalId(totalId);
		CommonUtils.initUpdate(totalVO);
		return totalDao.delete(totalVO);
	}

	@Override
	public XSSFWorkbook workbook(String fileName) throws Exception {

		String[] headers = excelHeaders.split(",");
		List<TotalVO> dataset = this.findList();
		if (CollectionUtils.isEmpty(dataset)) {
			return null;
		}
		XSSFWorkbook workbook = new XSSFWorkbook();
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
		titleStyle.setFont(titleFont);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));
		// 指定合并区域
		Row rowHeader = sheet.createRow(0);
		rowHeader.setHeight((short) 600);
		Cell cellHeader = rowHeader.createCell(0);
		XSSFRichTextString textHeader = new XSSFRichTextString(systemName);
		cellHeader.setCellStyle(titleStyle);
		cellHeader.setCellValue(textHeader);

		XSSFRow row = sheet.createRow(1);
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		for (int i = 0; i < headers.length; i++) {
			XSSFCell cell = row.createCell(i);
			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
			text.applyFont(font);
			cell.setCellValue(text);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int index = 1;
		XSSFFont font3 = workbook.createFont();
		font3.setColor(HSSFColorPredefined.BLUE.getIndex());

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font3);

		for (TotalVO vo : dataset) {
			index++;
			row = sheet.createRow(index);
			for (int i = 0; i < headers.length; i++) {
				XSSFCell cell = row.createCell(i);
				String value = null;
				if (i == 0) {
					value = vo.getTotalId().toString();
				} else if (i == 1) {
					value = vo.getTotalSite().toString();
				} else if (i == 2) {
					value = vo.getLinkSite().toString();
				} else if (i == 3) {
					value = vo.getFaultSite().toString();
				} else if (i == 4) {
					value = vo.getWaitPpen().toString();
				} else if (i == 5) {
					value = vo.getDistributedRate().toString();
				} else if (i == 6) {
					value = vo.getLinkStatus().toString();
				} else if (i == 7) {
					value = sdf.format(vo.getCreateDate());
				} else if (i == 8) {
					value = sdf.format(vo.getLastUpdateDate());
				}

				if (CommonUtils.isNumeric(value)) {
					// 是数字当作double处理
					cell.setCellValue(Double.parseDouble(value));
					cell.setCellStyle(cellStyle);
				} else {
					XSSFRichTextString richString = new XSSFRichTextString(value);
					richString.applyFont(font3);
					cell.setCellValue(richString);
				}

			}

		}

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
		vo.setText(UUID.randomUUID().toString() + "Export Total Data Rows Size " + dataset.size());
		vo.setTo("305805395@qq.com");
		// emailService.sendMail(vo, f);
		return workbook;
	}

}
