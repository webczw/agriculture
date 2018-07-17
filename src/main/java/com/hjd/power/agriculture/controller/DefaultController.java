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

import com.hjd.power.agriculture.Constants;
import com.hjd.power.agriculture.annotation.Access;
import com.hjd.power.agriculture.domain.SimpleMailMessageVO;
import com.hjd.power.agriculture.service.IEmailService;
import com.hjd.power.agriculture.service.IFeignService;
import com.hjd.power.agriculture.utils.AESUtils;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/connect")
public class DefaultController {
	private Logger logger = LoggerFactory.getLogger(DefaultController.class);
	@Autowired
	private IEmailService emailServicemailService;
	@Autowired
	IFeignService feignService;

	@GetMapping
	public String connect() throws Exception {
		return feignService.connect();
	}

	@ApiOperation(value = "日志测试", notes = "根据入参内容测试日志")
	@GetMapping("/test/{name}")
	@Access(roles = { Constants.ACCESS_ROLE_USER })
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
	@Access(roles = { Constants.ACCESS_ROLE_USER })
	public void send(@RequestBody SimpleMailMessageVO vo) throws Exception {
		emailServicemailService.sendSimpleMail(vo);
	}

	@ApiOperation(value = "加密", notes = "加密")
	@GetMapping("/encrypt/{key}")
	public String encrypt(@PathVariable("key") String key) {
		return AESUtils.encrypt(key, Constants.AES_KEY, Constants.AES_IV);
	}
}
