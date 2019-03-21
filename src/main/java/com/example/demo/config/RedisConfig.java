package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-16 下午7:31
 **/

@Configuration
public class RedisConfig {

    @Autowired
    private Environment env;


    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig pool = new JedisPoolConfig();
        pool.setMaxIdle(env.getProperty("spring.redis.jedis.pool.max-idle", Integer.TYPE));
        pool.setMinIdle(env.getProperty("spring.redis.jedis.pool.min-idle", Integer.TYPE));
        pool.setMaxTotal(env.getProperty("spring.redis.jedis.pool.max-total", Integer.TYPE));
        pool.setMaxWaitMillis(env.getProperty("spring.redis.jedis.pool.max-wait", Long.TYPE));
        pool.setTestOnBorrow(env.getProperty("spring.redis.jedis.pool.test-on-borrow", Boolean.TYPE));
        return pool;
    }

    /**
     * JedisConnectionFactory里面原先构建connectionFactory的一些set方法, 比如setHostName, setPort等一些方法since 2.0被deprecated了，
     * 所以构建factory的时候，要换构造方法了，这里采用的是传入RedisStandaloneConfiguration参数的方式。
     * 比如下面是JedisConnectionFactory原先setHostname的方法，现在注释：
     * Sets the Redis hostname.
     * @param hostName the hostname to set.
     * @deprecated since 2.0, configure the hostname using {@link RedisStandaloneConfiguration}.
     *
     * @return
     */
    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory  jedisConnectionFactory(){
        // 第一步：构建RedisStandaloneConfiguration，用于下一步构建JedisConnectionFactory
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(env.getProperty("spring.redis.host"));
//        redisStandaloneConfiguration.setPort(env.getProperty("spring.redis.port", Integer.TYPE));
        redisStandaloneConfiguration.setPort(6379);
        redisStandaloneConfiguration.setPassword(env.getProperty("spring.redis.password"));
        redisStandaloneConfiguration.setDatabase(14);
        // 第二步：构建factory
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return factory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate redisTemplate(@Autowired @Qualifier("jedisConnectionFactory") JedisConnectionFactory factory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}
