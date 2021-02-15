package com.supyp.bghouse.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalTime;

/*
* 每天早上十点
* 预约超时提醒
* 入住提醒
* 预警提醒
* */
public class MyJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalTime now = LocalTime.now();
        System.out.println(now.toString()+"myjob1正在执行");
    }
}
