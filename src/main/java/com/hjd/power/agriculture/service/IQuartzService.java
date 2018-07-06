package com.hjd.power.agriculture.service;

import java.util.List;

import com.hjd.power.agriculture.domain.QuartzConfigVO;

public interface IQuartzService {
	void update(Long id, String status) throws Exception;

	void startJobs() throws Exception;

	void updateCron(Long id, String cronSchedule) throws Exception;

	List<QuartzConfigVO> findAll() throws Exception;
}
