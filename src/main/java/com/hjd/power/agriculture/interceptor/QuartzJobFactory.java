package com.hjd.power.agriculture.interceptor;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hjd.power.agriculture.domain.QuartzConfigVO;
import com.hjd.power.agriculture.utils.TaskUtils;

//当上一个任务未结束时下一个任务需进行等待
@DisallowConcurrentExecution
public class QuartzJobFactory implements Job {
	public static final String SCHEDULEJOBKEY = "scheduleJob";

	// execute会根据cron的规则进行执行
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		// TODO Auto-generated method stub
		QuartzConfigVO config = (QuartzConfigVO) jobExecutionContext.getMergedJobDataMap().get(SCHEDULEJOBKEY);
		TaskUtils.invokMethod(config);
	}

}
