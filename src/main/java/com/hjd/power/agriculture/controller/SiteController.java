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
import com.hjd.power.agriculture.domain.SearchVO;
import com.hjd.power.agriculture.domain.SiteVO;
import com.hjd.power.agriculture.service.ISiteService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/site")
public class SiteController {
	@Autowired
	private ISiteService siteService;

	@ApiOperation(value = "查询站点信息", notes = "根据入参ID查询站点信息")
	@GetMapping("/{siteId}")
	public SiteVO find(@PathVariable("siteId") Integer siteId) throws Exception {
		return siteService.find(siteId);
	}

	@ApiOperation(value = "查询站点信息集合", notes = "查询站点信息集合")
	@GetMapping("/list")
	public List<SiteVO> findList() throws Exception {
		return siteService.findList();
	}

	@ApiOperation(value = "查询站点信息集合", notes = "查询站点信息集合")
	@GetMapping("/search")
	public List<SiteVO> findListBySearch(SearchVO vo) throws Exception {
		return siteService.findListBySearch(vo);
	}

	@ApiOperation(value = "创建站点信息", notes = "创建站点信息")
	@PostMapping()
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public SiteVO create(@RequestBody SiteVO vo) throws Exception {
		siteService.create(vo);
		return vo;
	}

	@ApiOperation(value = "更新站点信息", notes = "更新站点信息")
	@PutMapping()
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public Integer update(@RequestBody SiteVO vo) throws Exception {
		return siteService.update(vo);
	}

	@ApiOperation(value = "删除站点信息", notes = "删除站点信息")
	@DeleteMapping("/{siteId}")
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public Integer delete(@PathVariable("siteId") Integer siteId) throws Exception {
		return siteService.delete(siteId);
	}
}
