package com.hjd.power.agriculture.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjd.power.agriculture.Constants;
import com.hjd.power.agriculture.annotation.Access;
import com.hjd.power.agriculture.domain.SimpleMailMessageVO;
import com.hjd.power.agriculture.domain.TotalVO;
import com.hjd.power.agriculture.service.IEmailService;
import com.hjd.power.agriculture.service.ITotalService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/total")
public class TotalController {
	@Autowired
	private ITotalService totalService;
	@Autowired
	private IEmailService emailService;

	@ApiOperation(value = "查询汇总信息", notes = "根据入参ID查询汇总信息")
	@GetMapping("/{totalId}")
	public TotalVO find(@PathVariable("totalId") Integer totalId) throws Exception {
		return totalService.find(totalId);
	}

	@ApiOperation(value = "查询汇总信息集合", notes = "查询汇总信息集合")
	@GetMapping("/list")
	public List<TotalVO> findList() throws Exception {
		return totalService.findList();
	}

	@ApiOperation(value = "创建汇总信息", notes = "创建汇总信息")
	@PostMapping()
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public Integer create(@RequestBody TotalVO vo) throws Exception {
		return totalService.create(vo);
	}

	@ApiOperation(value = "更新汇总信息", notes = "更新汇总信息")
	@PutMapping()
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public Integer update(@RequestBody TotalVO vo) throws Exception {
		return totalService.update(vo);
	}

	@ApiOperation(value = "删除汇总信息", notes = "删除汇总信息")
	@DeleteMapping("/{totalId}")
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public Integer delete(@PathVariable("totalId") Integer totalId) throws Exception {
		return totalService.delete(totalId);
	}

	@SuppressWarnings("resource")
	@ApiOperation(value = "导出下载Excel文档", notes = "导出下载Excel文档")
	@GetMapping("/download")
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public void downstudents(HttpServletResponse response) throws Exception {
		String fileName = "download.xlsx";

		String[] headers = { "主键标识", "总站点", "连接站点", "故障站点", "待开通站点", "全国分布率", "连接状态", "创建日期	", "最后更新日期" };
		List<TotalVO> dataset = totalService.findList();
		if (CollectionUtils.isEmpty(dataset)) {
			return;
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
		XSSFRichTextString textHeader = new XSSFRichTextString("湖南保的农业科技有限公司数据监控中心");
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

				if (isNumeric(value)) {
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

		response.setHeader("content-Type", "application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}

	public boolean isNumeric(String str) {
		// 就是判断是否为整数(正负)
		Pattern pattern = Pattern.compile("^\\d+$|-\\d+$");
		// 判断是否为小数(正负)
		Pattern pattern2 = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");
		return (pattern.matcher(str).matches() || pattern2.matcher(str).matches());
	}
}
