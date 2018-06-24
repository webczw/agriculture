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
import com.hjd.power.agriculture.domain.MainVO;
import com.hjd.power.agriculture.service.IMainService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/main")
public class MainController {
	@Autowired
	private IMainService mainService;

	@ApiOperation(value = "查询主站点信息", notes = "根据入参ID查询主站点信息")
	@GetMapping("/{mainId}")
	public MainVO find(@PathVariable("mainId") Integer mainId) throws Exception {
		return mainService.find(mainId);
	}

	@ApiOperation(value = "查询主站点信息集合", notes = "查询主站点信息集合")
	@GetMapping("/list")
	public List<MainVO> findList() throws Exception {
		return mainService.findList();
	}

	@ApiOperation(value = "创建主站点信息", notes = "创建主站点信息")
	@PostMapping()
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public Integer create(@RequestBody MainVO vo) throws Exception {
		return mainService.create(vo);
	}

	@ApiOperation(value = "更新主站点信息", notes = "更新主站点信息")
	@PutMapping()
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public Integer update(@RequestBody MainVO vo) throws Exception {
		return mainService.update(vo);
	}

	@ApiOperation(value = "删除主站点信息", notes = "删除主站点信息")
	@DeleteMapping("/{mainId}")
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public Integer delete(@PathVariable("mainId") Integer mainId) throws Exception {
		return mainService.delete(mainId);
	}
}
