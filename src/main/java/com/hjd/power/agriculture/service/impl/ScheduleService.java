package com.hjd.power.agriculture.service.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hjd.power.agriculture.Enums.ConfigEnum;
import com.hjd.power.agriculture.service.IScheduleService;
import com.hjd.power.agriculture.service.ITotalService;
import com.hjd.power.agriculture.utils.CommonUtils;

@Service
public class ScheduleService implements IScheduleService {
	private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
	@Autowired
	private ITotalService totalService;

	@Override
	public void sendEmail() throws Exception {
		String fileName = CommonUtils.getSysConfig(ConfigEnum.TOTAL_EXCEL_FILENAME.getCode());
		totalService.workbook(fileName);
		LocalDateTime time = LocalDateTime.now();
		logger.warn("sendEmail:当前时间=" + time.getHour() + "时" + time.getMinute() + "分" + time.getSecond() + "秒");
	}

}
