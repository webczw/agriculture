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
import com.hjd.power.agriculture.domain.LighthouseQueryVO;
import com.hjd.power.agriculture.domain.LighthouseVO;
import com.hjd.power.agriculture.service.ILighthouseService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/lighthouse")
public class LighthouseController {
	@Autowired
	private ILighthouseService lighthouseService;
	@Value("${agriculture.lighthouse.excel.fileName:LighthouseDownload.xlsx}")
	private String fileName;

	@ApiOperation(value = "查询灯塔站点信息", notes = "根据入参ID查询灯塔站点信息")
	@GetMapping("/{lighthouseId}")
	public LighthouseVO find(@PathVariable("lighthouseId") Integer lighthouseId) throws Exception {
		return lighthouseService.find(lighthouseId);
	}

	@ApiOperation(value = "查询灯塔站点信息集合", notes = "查询灯塔站点信息集合")
	@GetMapping("/list")
	public List<LighthouseVO> findList() throws Exception {
		return lighthouseService.findList();
	}

	@ApiOperation(value = "创建灯塔站点信息", notes = "创建灯塔站点信息")
	@PostMapping()
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public Integer create(@RequestBody LighthouseVO vo) throws Exception {
		return lighthouseService.create(vo);
	}

	@ApiOperation(value = "更新灯塔站点信息", notes = "更新灯塔站点信息")
	@PutMapping()
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public Integer update(@RequestBody LighthouseVO vo) throws Exception {
		return lighthouseService.update(vo);
	}

	@ApiOperation(value = "删除灯塔站点信息", notes = "删除灯塔站点信息")
	@DeleteMapping("/{lighthouseId}")
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public Integer delete(@PathVariable("lighthouseId") Integer lighthouseId) throws Exception {
		return lighthouseService.delete(lighthouseId);
	}

	@ApiOperation(value = "根据主站点ID查询灯塔站点信息集合详情", notes = "根据主站点ID查询灯塔站点信息集合详情，包含传感器信息")
	@GetMapping("/list/detail")
	public List<LighthouseVO> findListDetail(LighthouseQueryVO vo) throws Exception {
		return lighthouseService.findListDetail(vo);
	}

	@ApiOperation(value = "导出下载Excel文档", notes = "导出下载Excel文档")
	@GetMapping("/download")
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public void downstudents(HttpServletResponse response, LighthouseQueryVO vo) throws Exception {
		XSSFWorkbook workbook = lighthouseService.workbook(vo, fileName);
		if (workbook == null) {
			return;
		}
		response.setHeader("content-Type", "application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}

}
