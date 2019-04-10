package com.example.demo.config;

import com.alibaba.nacos.spring.core.env.NacosPropertySource;
import com.example.demo.distributed.DemoFutureDispatch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-24 上午12:51
 **/
@Configuration
public class BeanConfig {

    @Bean(name = "demoFutureDispatch")
    public DemoFutureDispatch demoFutureDispatch(){
        DemoFutureDispatch dispatch = new DemoFutureDispatch();
        return dispatch;
    }

}
