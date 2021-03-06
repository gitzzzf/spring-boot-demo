package com.example.demo.config;

import com.example.demo.common.MultipleDataSource;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-15 下午5:54
 **/

@Configuration
// mybatis的配置加载要在Druid配置之后
@AutoConfigureAfter(DruidConfig.class)
// @MapperScan这个注解实际上是代替了MapperScannerConfigurer的Bean配置
@MapperScan(basePackages = "com.example.demo.dao", sqlSessionFactoryRef="sqlSessionFactory")
/*
 * Enables Spring's annotation-driven transaction management capability, similar to
 * the support found in Spring's {@code <tx:*>} XML namespace.
 */
@EnableTransactionManagement
public class MyBatisConfig {
    @Autowired
    Environment environment;

    /**
     * 事务管理
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(MultipleDataSource multipleDataSource){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(multipleDataSource);
        return transactionManager;
    }

    /**
     * 使application.yml中"mybatis.configuration"相关的配置生效。
     * 如果只有一个数据源，没有必要去指定SqlSessionFactory 或 SqlSessionTemplate ,
     * 因为 MapperScannerConfigurer 将会创建MapperFactoryBean，之后自动装配。
     * 但是,如果你使用了一个以上的 DataSource，那 么自动装配可能会失效 。
     * 这种情况下 ，你可以使用 sqlSessionFactoryBeanName 或 sqlSessionTemplateBeanName 属性来设置正确的 bean 名称来使用。
     * 比如在spring-boot中在application.yml中配置了”mybatis.configuration“的一些属性，
     * 在只有一个数据源，不需要配置SqlSessionFactory的时候，其实这些配置是自动装配了，
     * 但是在多数据源的情况下，也就是说SqlSessionFactory需要指定数据源的时候，自动装配就失效了，
     * 需要自己配置SqlSessionFactory的时候，把原来在”mybatis.configuration“中设置的一些属性自己加进来。
     */
    @Bean(name = "sessionConfiguration")
    @ConfigurationProperties("mybatis.configuration")
    public org.apache.ibatis.session.Configuration configuration(){
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        return configuration;
    }

    /**
     * SqlSessionFactoryBean
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("multipleDataSource") MultipleDataSource multipleDataSource,
                                                       @Qualifier("sessionConfiguration") org.apache.ibatis.session.Configuration configuration){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(multipleDataSource);
        try {
            // type aliased package
            sqlSessionFactoryBean.setTypeAliasesPackage(environment.getProperty("mybatis.type-aliases-package"));
            // 这个类可以看下
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            // resources, 自动扫描mapper/*.xml文件
            Resource[] resources = resolver.getResources("classpath*:mapper/*.xml");
            // 设置mapperLocations
            /**
             * Set locations of MyBatis mapper files that are going to be merged into the {@code SqlSessionFactory}
             * configuration at runtime.
             *
             * This is an alternative to specifying "&lt;sqlmapper&gt;" entries in an MyBatis config file.
             * This property being based on Spring's resource abstraction also allows for specifying
             * resource patterns here: e.g. "classpath*:sqlmap/*-mapper.xml".
             */
            sqlSessionFactoryBean.setMapperLocations(resources);
            // 设置在该sqlSessionFactory中生效的一些配置信息
            sqlSessionFactoryBean.setConfiguration(configuration);

        }catch (Exception e){

        }
        return sqlSessionFactoryBean;

    }

}
