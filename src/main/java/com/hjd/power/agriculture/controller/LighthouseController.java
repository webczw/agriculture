package com.hjd.power.agriculture.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjd.power.agriculture.domain.LighthouseVO;
import com.hjd.power.agriculture.service.ILighthouseService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/lighthouse")
public class LighthouseController {
	@Autowired
	private ILighthouseService lighthouseService;

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
	public Integer create(@RequestBody LighthouseVO vo) throws Exception {
		return lighthouseService.create(vo);
	}

	@ApiOperation(value = "更新灯塔站点信息", notes = "更新灯塔站点信息")
	@PutMapping()
	public Integer update(@RequestBody LighthouseVO vo) throws Exception {
		return lighthouseService.update(vo);
	}

	@ApiOperation(value = "删除灯塔站点信息", notes = "删除灯塔站点信息")
	@DeleteMapping("/{lighthouseId}")
	public Integer delete(@PathVariable("lighthouseId") Integer lighthouseId) throws Exception {
		return lighthouseService.delete(lighthouseId);
	}
}
