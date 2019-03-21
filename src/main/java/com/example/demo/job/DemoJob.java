package com.example.demo.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * demo定时任务
 *
 * @author zhoufeng
 * @date 2019-03-20 下午3:01
 **/

@Component
public class DemoJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("定时任务已经正常执行了！");
    }
}
