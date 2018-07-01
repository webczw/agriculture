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

import com.hjd.power.agriculture.Constants;
import com.hjd.power.agriculture.annotation.Access;
import com.hjd.power.agriculture.domain.SensorVO;
import com.hjd.power.agriculture.service.ISensorService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sensor")
public class SensorController {
	@Autowired
	private ISensorService sensorService;

	@ApiOperation(value = "查询传感器信息", notes = "根据入参ID查询传感器信息")
	@GetMapping("/{sensorId}")
	public SensorVO find(@PathVariable("sensorId") Integer sensorId) throws Exception {
		return sensorService.find(sensorId);
	}

	@ApiOperation(value = "查询传感器信息集合", notes = "查询传感器信息集合")
	@GetMapping("/list")
	public List<SensorVO> findList() throws Exception {
		return sensorService.findList();
	}

	@ApiOperation(value = "创建传感器信息", notes = "创建传感器信息")
	@PostMapping()
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public SensorVO create(@RequestBody SensorVO vo) throws Exception {
		sensorService.create(vo);
		return vo;
	}

	@ApiOperation(value = "更新传感器信息", notes = "更新传感器信息")
	@PutMapping()
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public Integer update(@RequestBody SensorVO vo) throws Exception {
		return sensorService.update(vo);
	}

	@ApiOperation(value = "删除传感器信息", notes = "删除传感器信息")
	@DeleteMapping("/{sensorId}")
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public Integer delete(@PathVariable("sensorId") Integer sensorId) throws Exception {
		return sensorService.delete(sensorId);
	}
}
