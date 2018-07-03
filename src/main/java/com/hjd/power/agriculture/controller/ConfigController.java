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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hjd.power.agriculture.Constants;
import com.hjd.power.agriculture.annotation.Access;
import com.hjd.power.agriculture.domain.ConfigVO;
import com.hjd.power.agriculture.domain.ListVO;
import com.hjd.power.agriculture.service.IConfigService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/config")
public class ConfigController {
	@Autowired
	private IConfigService configService;

	@ApiOperation(value = "查询配置信息", notes = "根据入参ID查询配置信息")
	@GetMapping("/{configId}")
	public ConfigVO find(@PathVariable("configId") Integer configId) throws Exception {
		return configService.find(configId);
	}

	@ApiOperation(value = "查询配置信息集合", notes = "查询配置信息集合")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<ConfigVO> findList() throws Exception {
		String configType = "MAIN_CONFIG";
		return configService.findList(configType);
	}

	@ApiOperation(value = "创建配置信息", notes = "创建配置信息")
	@PostMapping()
	@Access(roles = { Constants.ACCESS_ROLE_ADMIN })
	public Integer create(@RequestBody ConfigVO vo) throws Exception {
		return configService.create(vo);
	}

	@ApiOperation(value = "更新配置信息", notes = "更新配置信息")
	@PutMapping()
	@Access(roles = { Constants.ACCESS_ROLE_ADMIN })
	public Integer update(@RequestBody ListVO<ConfigVO> vo) throws Exception {
		return configService.batchUpdate(vo.getList());
	}

	@ApiOperation(value = "删除配置信息", notes = "删除配置信息")
	@DeleteMapping("/{configId}")
	@Access(roles = { Constants.ACCESS_ROLE_ADMIN })
	public Integer delete(@PathVariable("configId") Integer configId) throws Exception {
		return configService.delete(configId);
	}

	@ApiOperation(value = "刷新系統配置緩存", notes = "刷新系統配置緩存")
	@GetMapping("/sysConfig")
	public void find() throws Exception {
		configService.sysConfig();
	}
}
