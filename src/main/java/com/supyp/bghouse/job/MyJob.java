package com.supyp.bghouse.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalTime;

/*
* 测试用例 ， 无意义
* */
public class MyJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalTime now = LocalTime.now();
        System.out.println(now.toString()+"myjob1正在执行");
    }
}
