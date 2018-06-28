package com.hjd.power.agriculture.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.hjd.power.agriculture.dao.ILighthouseDao;
import com.hjd.power.agriculture.domain.LighthouseQueryVO;
import com.hjd.power.agriculture.domain.LighthouseVO;
import com.hjd.power.agriculture.domain.SensorVO;
import com.hjd.power.agriculture.service.ILighthouseService;
import com.hjd.power.agriculture.utils.CommonUtils;
import com.hjd.power.agriculture.utils.ExcelUtils;

@Service
public class LighthouseService implements ILighthouseService {
	@Autowired
	private ILighthouseDao lighthouseDao;

	@Value("${agriculture.lighthouse.excel.headers}")
	private String lighthouseExcelHeaders;

	@Value("${agriculture.sensor.excel.headers}")
	private String sensorExcelHeaders;

	@Override
	public List<LighthouseVO> findList() throws Exception {
		return lighthouseDao.findList();
	}

	@Override
	public LighthouseVO find(Integer lighthouseId) throws Exception {
		return lighthouseDao.find(lighthouseId);
	}

	@Override
	public Integer create(LighthouseVO vo) throws Exception {
		CommonUtils.initCreate(vo);
		return lighthouseDao.create(vo);
	}

	@Override
	public Integer update(LighthouseVO vo) throws Exception {
		CommonUtils.initUpdate(vo);
		return lighthouseDao.update(vo);
	}

	@Override
	public Integer delete(Integer lighthouseId) throws Exception {
		LighthouseVO vo = new LighthouseVO();
		vo.setLighthouseId(lighthouseId);
		CommonUtils.initUpdate(vo);
		return lighthouseDao.delete(vo);
	}

	@Override
	public List<LighthouseVO> findListDetail(LighthouseQueryVO vo) throws Exception {
		return lighthouseDao.findListDetail(vo);
	}

	@Override
	public XSSFWorkbook workbook(LighthouseQueryVO queryVO, String fileName) throws Exception {
		List<LighthouseVO> list = this.findListDetail(queryVO);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		String[] headers = lighthouseExcelHeaders.split(",");
		String[] sensorHeaders = sensorExcelHeaders.split(",");
		int headerLength = headers.length;
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = ExcelUtils.createExcelTitle(headerLength, workbook);
		ExcelUtils.createExcelHeader(workbook, sheet, headers, headers.length, 1, 0);
		int index = 1;

		CellStyle lighthouseCellStyle = ExcelUtils.lighthouseCellStyle(workbook, HSSFColorPredefined.BLUE);
		CellStyle boderCellStyle = ExcelUtils.boderCellStyle(workbook, HSSFColorPredefined.BLUE);
		CellStyle formatCellStyle = ExcelUtils.formatCellStyle(workbook, HSSFColorPredefined.BLUE, "0.00%");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (LighthouseVO vo : list) {
			index++;
			XSSFRow row = sheet.createRow(index);
			for (int i = 0; i < headerLength; i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(lighthouseCellStyle);
				String value = null;
				if (i == 0) {
					value = vo.getLinkStatus().toString();
				} else if (i == 1) {
					value = vo.getSiteNumber();
				} else if (i == 2) {
					value = vo.getProvince();
				} else if (i == 3) {
					value = vo.getCity();
				} else if (i == 4) {
					value = vo.getCounty();
				} else if (i == 5) {
					value = vo.getSiteCode();
				} else if (i == 6) {
					value = vo.getClientName();
				} else if (i == 7) {
					value = sdf.format(vo.getDateTime());
				} else if (i == 8) {
					value = vo.getTemperature();
				} else if (i == 9) {
					value = vo.getVoltage();
				} else if (i == 10) {
					value = vo.getLightStatus().toString();
				} else if (i == 11) {
					value = vo.getPhotovoltaic();
				} else if (i == 12) {
					value = vo.getSensorStatus();
				}
				if (value == null) {
					continue;
				}
				if (CommonUtils.isNumeric(value)) {
					// 是数字当作double处理
					cell.setCellValue(Double.parseDouble(value));
				} else {
					cell.setCellValue(value);
				}
			}
			List<SensorVO> sensorList = vo.getSensorList();
			if (!CollectionUtils.isEmpty(sensorList)) {
				index++;
				ExcelUtils.createExcelHeader(workbook, sheet, sensorHeaders, headerLength, index, 1);
				for (SensorVO sensorVO : sensorList) {
					index++;
					XSSFRow sensorRow = sheet.createRow(index);
					for (int j = 0; j < headerLength; j++) {
						XSSFCell sensorCell = sensorRow.createCell(j);
						sensorCell.setCellStyle(boderCellStyle);
						String sensorValue = null;
						if (j == 1) {
							sensorValue = sensorVO.getNumber();
						} else if (j == 2) {
							sensorValue = sensorVO.getAddressCode();
						} else if (j == 3) {
							sensorValue = sensorVO.getPhotovoltaic();
						} else if (j == 4) {
							sensorValue = sensorVO.getVoltage();
						} else if (j == 5) {
							sensorValue = sensorVO.getHumidity();
							if (!StringUtils.isEmpty(sensorValue)) {
								sensorValue = sensorValue.replace("%", "");
								Double humidity = Double.parseDouble(sensorValue);
								if (humidity > 0) {
									sensorValue = String.valueOf((humidity / 100));
								}
								sensorCell.setCellStyle(formatCellStyle);
							}
						} else if (j == 6) {
							sensorValue = sensorVO.getTemperature();
						} else if (j == 7) {
							sensorValue = sensorVO.getPhValue().toString();
						} else if (j == 8) {
							sensorValue = sensorVO.getFault();
						}
						if (sensorValue == null) {
							continue;
						}
						if (CommonUtils.isNumeric(sensorValue)) {
							// 是数字当作double处理
							sensorCell.setCellValue(Double.parseDouble(sensorValue));
						} else {
							sensorCell.setCellValue(sensorValue);
						}
					}
				}
			}
		}
		ExcelUtils.workbookWrite(fileName, list.size(), workbook);
		return workbook;
	}

}
