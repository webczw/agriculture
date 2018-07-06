package com.hjd.power.agriculture.service.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hjd.power.agriculture.service.IScheduleService;

@Service
public class ScheduleService implements IScheduleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);

	@Override
	public void sendEmail() throws Exception {
		LocalDateTime time = LocalDateTime.now();
		LOGGER.warn("good!good!study!:当前时间=" + time.getHour() + "时" + time.getMinute() + "分" + time.getSecond() + "秒");

	}

}
