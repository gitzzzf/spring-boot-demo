package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-16 下午4:19
 **/

@Configuration
// 配置对异步任务的支持
@EnableAsync
public class DemoExecutorConfig {

    @Bean(name = "demoExecutor", initMethod = "initialize", destroyMethod = "shutdown")
    @ConfigurationProperties(prefix = "spring.demoexecutor")
    public ThreadPoolTaskExecutor createExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 拒绝策略
        /**
         * CallerRunsPolicy:
         * A handler for rejected tasks that runs the rejected task
         * directly in the calling thread of the {@code execute} method,
         * unless the executor has been shut down, in which case the task
         * is discarded.
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        return executor;
    }

}
