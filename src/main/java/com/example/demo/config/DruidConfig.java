package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.example.demo.common.MultipleDataSource;
import org.aspectj.lang.annotation.DeclareParents;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * http://localhost:8081/druid/
 *
 * @author zhoufeng
 * @date 2019-03-16 下午2:17
 **/

@Configuration
public class DruidConfig {

    @Autowired
    private Environment environment;

    /**
     * 基于yml文件里面的"spring.datasource.druid"配置构建一个DruidDataSource：
     *  这个地方可以对yml里面的配置进行补充，比如"spring.datasource.druid"里面没有配置keep alive属性，那么这个地方可以进行补充，
     *  不过有一点，如果"spring.datasource.druid"里面配置了，这个类文件里面也配置了同样的属性，比如maxActive, yml里面设置的为10，这个地方为11，最终以yml里面为准
     *  这个跟spring boot加载配置文件顺序有关。
     * @return
     */
    // parent druid datasource
    @Bean(name = "druidDataSource", destroyMethod = "close")
    public DruidDataSource createDruidDataSource(){
        // 获取builder
        DruidDataSourceBuilder builder = DruidDataSourceBuilder.create();
        DruidDataSource druidDataSource = builder.build(environment, "spring.datasource.druid");
        druidDataSource.setKeepAlive(true);
        druidDataSource.setMaxActive(11);
        return druidDataSource;
    }

    /**
     * ###############上面是测试单数据源，下面是测试多数据源##########################
     */

    @Bean(name = "masterDataSource")
    public DruidDataSource masterDataSource(){
        // 获取builder
        DruidDataSourceBuilder builder = DruidDataSourceBuilder.create();
        DruidDataSource master = builder.build(environment, "spring.datasource.druid");
        master.setUrl("jdbc:mysql://127.0.0.1:3306/quartz");
        master.setUsername("root");
        master.setPassword("");
        return master;
    }

    @Bean(name = "slaveDataSource")
    public DruidDataSource slaveDataSource(){
        // 获取builder
        DruidDataSourceBuilder builder = DruidDataSourceBuilder.create();
        DruidDataSource slave = builder.build(environment, "spring.datasource.druid");
        slave.setUrl("jdbc:mysql://127.0.0.1:3306/quartz");
        slave.setUsername("root");
        slave.setPassword("");
        return slave;
    }

    @Bean(name = "multipleDataSource")
    public MultipleDataSource multipleDataSource(@Qualifier("masterDataSource") DruidDataSource masterDataSource,
                                                 @Qualifier("slaveDataSource") DruidDataSource slaveDataSource){
        MultipleDataSource multiple = new MultipleDataSource();
        // default
        multiple.setDefaultTargetDataSource(masterDataSource);
        // targetDateSources
        Map<Object, Object> targetDataSourceMap = new HashMap<>();
        targetDataSourceMap.put("masterDataSource", masterDataSource);
        targetDataSourceMap.put("slaveDataSource", slaveDataSource);
        multiple.setTargetDataSources(targetDataSourceMap);
        return multiple;
    }
}
