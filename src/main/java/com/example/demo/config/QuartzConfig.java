package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.demo.common.MultipleDataSource;
import com.example.demo.job.DemoJob;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-20 下午2:09
 **/

@Configuration
public class QuartzConfig {

    /**
     * 定时任务总调度器
     */
    @Bean(name = "quartzScheduler")
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("multipleDataSource") MultipleDataSource druidDataSource,
                                                     @Qualifier("demoExecutor") ThreadPoolTaskExecutor demoExecutor,
                                                     @Qualifier("demoJobTrigger") Trigger demoTrigger){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        // dataSource
        factoryBean.setDataSource(druidDataSource);
        // quartzProperties
        Properties properties = new Properties();
        properties.setProperty("org.quartz.scheduler.instanceName", "quartz-cluster");
        properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
        properties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        properties.setProperty("org.quartz.threadPool.threadCount", "10");
        properties.setProperty("org.quartz.threadPool.threadPriority", "7");
        properties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        properties.setProperty("org.quartz.jobStore.isClustered", "true");
        properties.setProperty("org.quartz.jobStore.clusterCheckinInterval", "600000");
        properties.setProperty("org.quartz.jobStore.maxMisfiresToHandleAtATime", "2");
        properties.setProperty("org.quartz.jobStore.misfireThreshold", "600000");
        properties.setProperty("org.quartz.scheduler.skipUpdateCheck", "true");
        properties.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
        factoryBean.setQuartzProperties(properties);
        // 调度器name
        factoryBean.setSchedulerName("clusterScheduler");
        // 启动延时
        factoryBean.setStartupDelay(10);
        // 通过applicationContextSchedulerContextKey属性配置spring上下文
        factoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        // 是否重写数据库已存在的job，如果这个覆盖配置为false，quratz启动以后将以数据库的数据为准，配置文件的修改不起作用
        factoryBean.setOverwriteExistingJobs(true);
        // 线程池
        factoryBean.setTaskExecutor(demoExecutor);
        // Set whether to automatically start the scheduler after initialization.
        factoryBean.setAutoStartup(true);
        // Register a list of Trigger objects with the Scheduler that this FactoryBean creates.
        factoryBean.setTriggers(demoTrigger);// 如果有新的任务触发器，就往这个里面加就行了
        return factoryBean;
    }

    /**
     * 任务触发器
     */
    @Bean(name = "demoJobTrigger")
    public CronTriggerFactoryBean triggerFactoryBean(@Qualifier("demoJobDetail") JobDetail jobDetail){
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        // cron表达式：指定触发时间
        trigger.setCronExpression("3 3 3 * * ?");
        // 设置任务
        trigger.setJobDetail(jobDetail);
        return trigger;
    }


    /*
     * JobDetailFactoryBean:
     *
     * A Spring {@link org.springframework.beans.factory.FactoryBean} for creating a Quartz {@link org.quartz.JobDetail}
     * instance, supporting bean-style usage for JobDetail configuration.
     *
     * <p>{@code JobDetail(Impl)} itself is already a JavaBean but lacks
     * sensible defaults. This class uses the Spring bean name as job name,
     * and the Quartz default group ("DEFAULT") as job group if not specified.
     */
    /**
     * 构建任务
     */
    @Bean(name = "demoJobDetail")
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
        // 指定自己写的任务的类【里面实现定时任务的具体逻辑】
        jobDetail.setJobClass(DemoJob.class);
        jobDetail.setRequestsRecovery(true);
        jobDetail.setGroup("demo");
        jobDetail.setDurability(true);
        return jobDetail;
    }

}
