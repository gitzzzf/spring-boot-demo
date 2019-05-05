package com.example.demo.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-05-03 下午2:22
 **/
@Configuration
public class ESConfig {

    /**
     * java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
     * 这个报错在单独整合es的时候不会出现，但是在工程里面同时整合了redis/mq等其他的中间件的时候就会出现这个报错。
     *
     * 原因分析：
     * SpringBoot 2.X 的 spring-boot-starter-data-redis 默认是以 lettuce 作为连接池的，
     * 而在lettuce和elasticsearch transport 中都会依赖netty,
     * 二者的netty 版本不一致，不能够兼容
     *
     * 解决办法如下：当设置为false时，就不会再调用setAvailableProcessors方法了
     */
    @PostConstruct
    void init(){

        // 设置环境变量，解决Es的netty与Netty服务本身不兼容问题
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
}
