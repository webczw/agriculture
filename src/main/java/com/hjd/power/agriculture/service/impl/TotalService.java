package com.hjd.power.agriculture.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjd.power.agriculture.Enums;
import com.hjd.power.agriculture.Enums.ConfigEnum;
import com.hjd.power.agriculture.Enums.StatusEnum;
import com.hjd.power.agriculture.dao.ISiteDao;
import com.hjd.power.agriculture.dao.ITotalDao;
import com.hjd.power.agriculture.domain.SiteVO;
import com.hjd.power.agriculture.domain.TotalVO;
import com.hjd.power.agriculture.service.ITotalService;
import com.hjd.power.agriculture.utils.CommonUtils;
import com.hjd.power.agriculture.utils.ExcelUtils;

@Service
public class TotalService implements ITotalService {
	@Autowired
	private ITotalDao totalDao;

	@Autowired
	private ISiteDao siteDao;

	@Override
	public List<TotalVO> findList() throws Exception {
		List<TotalVO> list = new ArrayList<>();
		TotalVO totalVO = totalDao.findTotal();
		totalVO.setLinkStatus(StatusEnum.NORMAL.getCode());
		list.add(totalVO);
		return list;
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

		List<TotalVO> dataset = this.findList();
		if (CollectionUtils.isEmpty(dataset)) {
			return null;
		}
		String excelHeaders = CommonUtils.getSysConfig(ConfigEnum.TOTAL_EXCEL_HEADERS.getCode());
		String[] headers = excelHeaders.split(",");
		int headerLength = headers.length;
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = ExcelUtils.createExcelTitle(headerLength, workbook);
		ExcelUtils.createExcelHeader(workbook, sheet, headers, headers.length, 1, 0);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int index = 1;

		CellStyle cellStyle = ExcelUtils.boderCellStyle(workbook, HSSFColorPredefined.BLUE);

		for (TotalVO vo : dataset) {
			index++;
			XSSFRow row = sheet.createRow(index);
			for (int i = 0; i < headerLength; i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(cellStyle);
				String value = null;
				if (i == 0) {
					value = vo.getTotalSite().toString();
				} else if (i == 1) {
					value = vo.getLinkSite().toString();
				} else if (i == 2) {
					value = vo.getFaultSite().toString();
				} else if (i == 3) {
					value = vo.getWaitPpen().toString();
				} else if (i == 4) {
					value = vo.getDistributedRate().toString();
				} else if (i == 5) {
					value = Enums.StatusEnum.NORMAL.getName();
				} else if (i == 6) {
					value = sdf.format(new Date());
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
		}

		List<SiteVO> siteList = siteDao.findList();
		if (CollectionUtils.isNotEmpty(siteList)) {
			for (SiteVO vo : siteList) {
				index++;
				XSSFRow row = sheet.createRow(index);
				for (int i = 0; i < headerLength; i++) {
					XSSFCell cell = row.createCell(i);
					cell.setCellStyle(cellStyle);
					String value = null;
					if (i == 0) {
						value = vo.getSiteCode();
					} else if (i == 1) {
						value = vo.getSiteName();
					} else if (i == 2) {
						value = vo.getProvince();
					} else if (i == 3) {
						value = vo.getCity();
					} else if (i == 4) {
						value = vo.getCounty();
					} else if (i == 5) {
						value = Enums.StatusEnum.getName(vo.getLinkStatus());
					} else if (i == 6) {
						value = " ";
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
			}
		}

		ExcelUtils.workbookWrite(fileName, dataset.size(), workbook);
		return workbook;
	}

	@Override
	public TotalVO findTotal() throws Exception {
		TotalVO totalVO = totalDao.findTotal();
		totalVO.setLinkStatus(StatusEnum.NORMAL.getCode());
		return totalVO;
	}

	@Override
	public List<String> findProvince() throws Exception {
		return totalDao.findProvince();
	}

}
