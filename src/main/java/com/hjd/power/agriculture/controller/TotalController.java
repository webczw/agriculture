package com.hjd.power.agriculture.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjd.power.agriculture.domain.TotalVO;
import com.hjd.power.agriculture.service.ITotalService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/total")
public class TotalController {
	private Logger logger = LoggerFactory.getLogger(TotalController.class);
	@Autowired
	private ITotalService totalService;

	@ApiOperation(value = "查询汇总信息", notes = "根据入参ID查询汇总信息")
	@GetMapping("/{totalId}")
	public TotalVO find(@PathVariable("totalId") Integer totalId) throws Exception {
		try {
			return totalService.find(totalId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TotalController_find_error", e);
			throw e;
		}
	}

	@ApiOperation(value = "查询汇总信息集合", notes = "查询汇总信息集合")
	@GetMapping("/list")
	public List<TotalVO> findList() throws Exception {
		try {
			return totalService.findList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TotalController_findList_error", e);
			throw e;
		}
	}

	@ApiOperation(value = "创建汇总信息", notes = "创建汇总信息")
	@PostMapping()
	public Integer create(@RequestBody TotalVO vo) throws Exception {
		try {
			return totalService.create(vo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TotalController_create_error", e);
			throw e;
		}
	}

	@ApiOperation(value = "更新汇总信息", notes = "更新汇总信息")
	@PutMapping()
	public Integer update(@RequestBody TotalVO vo) throws Exception {
		try {
			return totalService.update(vo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TotalController_update_error", e);
			throw e;
		}
	}

	@ApiOperation(value = "删除汇总信息", notes = "删除汇总信息")
	@DeleteMapping("/{totalId}")
	public Integer delete(@PathVariable("totalId") Integer totalId) throws Exception {
		try {
			return totalService.delete(totalId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("TotalController_delete_error", e);
			throw e;
		}
	}
}
