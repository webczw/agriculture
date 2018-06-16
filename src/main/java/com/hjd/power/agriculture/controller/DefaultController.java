package com.hjd.power.agriculture.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class DefaultController {
	private Logger logger = LoggerFactory.getLogger(DefaultController.class);
	@GetMapping("/test/{name}")
	public String test(@PathVariable("name") String name) throws Exception {
		logger.trace(name+"日志输出 trace");
        logger.debug(name+"日志输出 debug");
        logger.info(name+"日志输出 info");
        logger.warn(name+"日志输出 warn");
        logger.error(name+"日志输出 error");
		return "My , "+ name;
	}
}
