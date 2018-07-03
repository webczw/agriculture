package com.hjd.power.agriculture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.hjd.power.agriculture.service.IConfigService;

@Component
public class AgricultureRunner implements ApplicationRunner {
	@Autowired
	private IConfigService configService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		configService.sysConfig();
	}

}
