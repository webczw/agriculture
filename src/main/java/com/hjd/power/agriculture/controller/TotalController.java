package com.hjd.power.agriculture.controller;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.hjd.power.agriculture.domain.TotalVO;
import com.hjd.power.agriculture.service.ITotalService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/total")
public class TotalController {
	@Autowired
	private ITotalService totalService;

	@Value("${agriculture.total.excel.fileName:TotalDownload.xlsx}")
	private String fileName;

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

	@ApiOperation(value = "导出下载Excel文档", notes = "导出下载Excel文档")
	@GetMapping("/download")
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public void downstudents(HttpServletResponse response) throws Exception {
		XSSFWorkbook workbook = totalService.workbook(fileName);
		if (workbook == null) {
			return;
		}
		response.setHeader("content-Type", "application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}
}
