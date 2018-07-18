package com.hjd.power.agriculture.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hjd.power.agriculture.Enums.ConfigEnum;
import com.hjd.power.agriculture.Enums.StatusEnum;
import com.hjd.power.agriculture.dao.ILighthouseDao;
import com.hjd.power.agriculture.dao.ISensorDao;
import com.hjd.power.agriculture.dao.ISiteDao;
import com.hjd.power.agriculture.domain.LighthouseQueryVO;
import com.hjd.power.agriculture.domain.LighthouseVO;
import com.hjd.power.agriculture.domain.SensorVO;
import com.hjd.power.agriculture.domain.SiteVO;
import com.hjd.power.agriculture.service.IFeignService;
import com.hjd.power.agriculture.service.ILighthouseService;
import com.hjd.power.agriculture.utils.CommonUtils;
import com.hjd.power.agriculture.utils.ExcelUtils;

@Service
public class LighthouseService implements ILighthouseService {
	private Logger logger = LoggerFactory.getLogger(LighthouseService.class);
	@Autowired
	private ILighthouseDao lighthouseDao;
	@Autowired
	private ISensorDao sensorDao;
	@Autowired
	private ISiteDao siteDao;
	@Autowired
	IFeignService feignService;

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
		Integer count = lighthouseDao.update(vo);
		LighthouseVO oldVO = this.find(vo.getLighthouseId());
		String resut = feignService.updateLightHouse(oldVO.getSiteCode(), vo.getOnOffFlag(), vo.getDelay(),
				vo.getBootDateDelay());
		logger.info("updateLightHouse_resut=" + resut);
		return count;
	}

	@Override
	public Integer delete(Integer lighthouseId) throws Exception {
		Integer count = sensorDao.findCountByLighthouseId(lighthouseId);
		if (count > 0) {
			throw new Exception("该灯塔站点下有传感器信息，删除失败.");
		}
		LighthouseVO vo = new LighthouseVO();
		vo.setLighthouseId(lighthouseId);
		CommonUtils.initUpdate(vo);
		return lighthouseDao.delete(vo);
	}

	@Override
	public List<LighthouseVO> findListDetail(LighthouseQueryVO vo) throws Exception {
		if (vo.getSiteId() == null && vo.getLighthouseId() == null) {
			Integer siteId = siteDao.findDefualtSiteId();
			vo.setSiteId(siteId);
		}
		return lighthouseDao.findListDetail(vo);
	}

	@Override
	public XSSFWorkbook workbook(LighthouseQueryVO queryVO, String fileName) throws Exception {
		List<LighthouseVO> list = this.findListDetail(queryVO);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		String lighthouseExcelHeaders = CommonUtils.getSysConfig(ConfigEnum.LIGHTHOUSE_EXCEL_HEADERS.getCode());
		String sensorExcelHeaders = CommonUtils.getSysConfig(ConfigEnum.SENSOR_EXCEL_HEADERS.getCode());
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

		CellStyle photovoltaicFillCellStyle = ExcelUtils.formatFillCellStyle(workbook, HSSFColorPredefined.BLUE, "0V");
		CellStyle voltageFillCellStyle = ExcelUtils.formatFillCellStyle(workbook, HSSFColorPredefined.BLUE, "0.00V");
		CellStyle temperatureFillCellStyle = ExcelUtils.formatFillCellStyle(workbook, HSSFColorPredefined.BLUE, "0℃");

		CellStyle photovoltaicCellStyle = ExcelUtils.formatCellStyle(workbook, HSSFColorPredefined.BLUE, "0V");
		CellStyle voltageCellStyle = ExcelUtils.formatCellStyle(workbook, HSSFColorPredefined.BLUE, "0.00V");
		CellStyle temperatureCellStyle = ExcelUtils.formatCellStyle(workbook, HSSFColorPredefined.BLUE, "0℃");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int siteNumber = 1;
		for (LighthouseVO vo : list) {
			index++;
			XSSFRow row = sheet.createRow(index);
			for (int i = 0; i < headerLength; i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(lighthouseCellStyle);
				String value = null;
				if (i == 0) {
					value = StatusEnum.getName(vo.getLinkStatus());
				} else if (i == 1) {
					value = (siteNumber++) + "";
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
					if (vo.getDateTime() != null) {
						value = sdf.format(vo.getDateTime());
					}
				} else if (i == 8) {
					if (vo.getTemperature() != null) {
						value = vo.getTemperature().toString();
					}
					cell.setCellStyle(temperatureFillCellStyle);
				} else if (i == 9) {
					if (vo.getVoltage() != null) {
						value = vo.getVoltage().toString();
					}
					cell.setCellStyle(voltageFillCellStyle);
				} else if (i == 10) {
					value = vo.getLightStatus() + "";// StatusEnum.getName(vo.getLightStatus());
				} else if (i == 11) {
					if (vo.getPhotovoltaic() != null) {
						value = vo.getPhotovoltaic().toString();
					}
					cell.setCellStyle(photovoltaicFillCellStyle);
				} else if (i == 12) {
					value = StatusEnum.getName(vo.getSensorStatus());
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
			if (CollectionUtils.isNotEmpty(sensorList)) {
				index++;
				int number = 1;
				ExcelUtils.createExcelHeader(workbook, sheet, sensorHeaders, headerLength, index, 1);
				for (SensorVO sensorVO : sensorList) {
					index++;
					XSSFRow sensorRow = sheet.createRow(index);
					for (int j = 0; j < headerLength; j++) {
						XSSFCell sensorCell = sensorRow.createCell(j);
						sensorCell.setCellStyle(boderCellStyle);
						String sensorValue = null;
						if (j == 1) {
							sensorValue = StatusEnum.getName(sensorVO.getLinkStatus());
						} else if (j == 2) {
							sensorValue = (number++) + "";
						} else if (j == 3) {
							sensorValue = sensorVO.getAddressCode();
						} else if (j == 4) {
							if (sensorVO.getPhotovoltaic() != null) {
								sensorValue = sensorVO.getPhotovoltaic().toString();
							}
							sensorCell.setCellStyle(photovoltaicCellStyle);
						} else if (j == 5) {
							if (sensorVO.getVoltage() != null) {
								sensorValue = sensorVO.getVoltage().toString();
							}
							sensorCell.setCellStyle(voltageCellStyle);
						} else if (j == 6) {
							if (sensorVO.getTemperature() != null) {
								sensorValue = sensorVO.getTemperature().toString();
							}
							sensorCell.setCellStyle(temperatureCellStyle);
						} else if (j == 7) {
							if (sensorVO.getHumidity() != null) {
								sensorValue = sensorVO.getHumidity().toString();
								if (!StringUtils.isEmpty(sensorValue)) {
									Double humidity = Double.parseDouble(sensorValue);
									if (humidity > 0) {
										sensorValue = String.valueOf((humidity / 100));
									}
								}
							}
							sensorCell.setCellStyle(formatCellStyle);
						} else if (j == 8) {
							if (sensorVO.getPhValue() != null) {
								sensorValue = sensorVO.getPhValue().toString();
							}
						} else if (j == 9) {
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

	@Override
	public SiteVO findSite(LighthouseQueryVO vo) throws Exception {
		Integer siteId = vo.getSiteId();
		if (siteId == null) {
			siteId = siteDao.findDefualtSiteId();
			vo.setSiteId(siteId);
		}
		SiteVO siteVO = siteDao.find(siteId);
		// 默认故障
		siteVO.setSiteStatus(StatusEnum.FAULT.getCode());
		List<LighthouseVO> lighthouseList = lighthouseDao.findListDetail(vo);
		if (CollectionUtils.isNotEmpty(lighthouseList)) {
			int i = 1;
			for (LighthouseVO lighthouseVO : lighthouseList) {
				lighthouseVO.setSiteNumber((i++) + "");
				Integer refreshDate = lighthouseVO.getRefreshDate();
				Double temperature = lighthouseVO.getTemperature();
				// 有下发，有采集数据返回，标识状态正常
				if (refreshDate != null && temperature != null) {
					siteVO.setSiteStatus(StatusEnum.NORMAL.getCode());
				}
				List<SensorVO> sensorList = lighthouseVO.getSensorList();
				Integer sensorStatus = 0;
				if (CollectionUtils.isNotEmpty(sensorList)) {
					int j = 1;
					for (SensorVO sensorVO : sensorList) {
						sensorVO.setNumber((j++) + "");
						Integer linkStatus = sensorVO.getLinkStatus();
						String fault = sensorVO.getFault();
						if (StatusEnum.NORMAL.getCode().equals(linkStatus)
								&& StatusEnum.NORMAL.getCode().toString().equals(fault)) {
							sensorStatus = StatusEnum.NORMAL.getCode();
						}
					}
				}

				lighthouseVO.setSensorStatus(sensorStatus);
			}
		}
		siteVO.setLighthouseList(lighthouseList);
		return siteVO;
	}

}
