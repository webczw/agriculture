package com.hjd.power.agriculture.controller;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjd.power.agriculture.Enums.QuartzConfigEnum;
import com.hjd.power.agriculture.domain.QuartzConfigVO;
import com.hjd.power.agriculture.service.IQuartzService;

@RestController
@RequestMapping("/quartz")
public class QuartzController {
	@Autowired
	private IQuartzService quartzService;

	@GetMapping("/find/list")
	public List<QuartzConfigVO> table() throws Exception {
		List<QuartzConfigVO> configs = quartzService.findAll();
		for (QuartzConfigVO config : configs) {
			String message = QuartzConfigEnum.findByMessage(config.getStatus());
			config.setStatus(message);
		}
		return configs;
	}

	@PostMapping("/update/status")
	public void updateStatus(Long id, String status) throws Exception {
		quartzService.update(id, status);
	}

	@PostMapping("/update/cron")
	public void updateQuartz(Long id, String cron) throws Exception {
		CronScheduleBuilder.cronSchedule(cron);
		quartzService.updateCron(id, cron);
	}
}
