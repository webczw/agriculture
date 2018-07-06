package com.hjd.power.agriculture.service.impl;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.hjd.power.agriculture.Enums.QuartzConfigEnum;
import com.hjd.power.agriculture.dao.IQuartzConfigDao;
import com.hjd.power.agriculture.domain.QuartzConfigVO;
import com.hjd.power.agriculture.interceptor.QuartzJobFactory;
import com.hjd.power.agriculture.service.IQuartzService;

@Service
public class QuartzService implements IQuartzService {
	@Autowired
	SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	IQuartzConfigDao quartzConfigDao;

	@Override
	public void update(Long id, String status) throws Exception {
		// 判断是否有此ID的数据
		QuartzConfigVO config = quartzConfigDao.findOne(id);
		if (config == null) {
			new RuntimeException("未找到此定时任务");
		}
		if (QuartzConfigEnum.STATUS_STOP.getCode().equals(status)) {
			// stop 禁用
			config.setStatus(QuartzConfigEnum.STATUS_STOP.getCode());
			deleteJob(config);
		} else {
			// start 启用
			config.setStatus(QuartzConfigEnum.STATUS_START.getCode());
			addJob(config);
		}
		quartzConfigDao.setStatusById(config.getStatus(), config.getId());

	}

	/**
	 *
	 * 启动所有的任务
	 * 
	 * @return: void
	 */
	@Override
	public void startJobs() {
		List<QuartzConfigVO> configList = quartzConfigDao.findAll();
		for (QuartzConfigVO config : configList) {
			if (QuartzConfigEnum.STATUS_START.getCode().equals(config.getStatus())) {
				try {
					addJob(config);
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 修改cron表达式
	 * 
	 * @param id
	 * @param cronSchedule
	 */
	@Override
	public void updateCron(Long id, String cronSchedule) {
		int i = quartzConfigDao.setScheduleById(cronSchedule, id);
		if (i <= 0) {
			throw new RuntimeException("500!");
		}
	}

	private void addJob(QuartzConfigVO config) throws SchedulerException {
		// 得到调度器
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = this.getJobKey(config);
		// 获得触发器
		TriggerKey triggerKey = TriggerKey.triggerKey(config.getName(), config.getGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		// 判断触发器是否存在（如果存在说明之前运行过但是在当前被禁用了，如果不存在说明一次都没运行过）
		if (trigger == null) {
			// 新建一个工作任务 指定任务类型为串接进行的
			JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(jobKey).build();
			// 将工作添加到工作任务当中去
			jobDetail.getJobDataMap().put(QuartzJobFactory.SCHEDULEJOBKEY, config);
			// 将cron表达式进行转换
			CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(config.getCron());
			// 创建触发器并将cron表达式对象给塞入
			trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
			// 在调度器中将触发器和任务进行组合
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(config.getCron());
			// 按照新的规则进行
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
			// 重启
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	/**
	 * 删除任务
	 *
	 * @param :
	 *            com.study.www.model.config
	 * @Date: 2018/2/24 18:23
	 * @return: void
	 */
	private void deleteJob(QuartzConfigVO config) throws SchedulerException {
		// 得到调度器
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		// 找到key值
		JobKey jobKey = this.getJobKey(config);
		// 从触发器找到此任务然后进行删除
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 根据name和group得到任务的key
	 *
	 * @param :com.study.www.model.config
	 * @Date: 2018/2/24 18:27
	 * @return: org.quartz.JobKey
	 */
	private JobKey getJobKey(QuartzConfigVO config) {
		return getJobKey(config.getName(), config.getGroup());
	}

	private JobKey getJobKey(String name, String group) {
		return JobKey.jobKey(name, group);
	}

	@Override
	public List<QuartzConfigVO> findAll() throws Exception {
		return quartzConfigDao.findAll();
	}
}
