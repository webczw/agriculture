package com.hjd.power.agriculture.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjd.power.agriculture.domain.SimpleMailMessageVO;
import com.hjd.power.agriculture.service.IEmailService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/main")
public class DefaultController {
	private Logger logger = LoggerFactory.getLogger(DefaultController.class);
	@Autowired
	private IEmailService emailServicemailService;

	@ApiOperation(value = "日志测试", notes = "根据入参内容测试日志")
	@GetMapping("/test/{name}")
	public String test(@PathVariable("name") String name) throws Exception {
		logger.trace(name + "日志输出 trace");
		logger.debug(name + "日志输出 debug.");
		logger.info(name + "日志输出 info.");
		logger.warn(name + "日志输出 warn.");
		logger.error(name + "日志输出 error.");
		return "My , " + name;
	}

	@ApiOperation(value = "发送邮件", notes = "根据入参内容发送邮件")
	@PostMapping("/send")
	public void send(@RequestBody SimpleMailMessageVO vo) throws Exception {
		emailServicemailService.sendSimpleMail(vo);
	}
}
