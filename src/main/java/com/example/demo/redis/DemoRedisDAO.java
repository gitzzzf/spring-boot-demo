package com.example.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-16 下午8:14
 **/

@Component
public class DemoRedisDAO {

    @Resource(name = "redisTemplate")
    private RedisTemplate redis;

    public String getValueOps(String key) {
        ValueOperations<String, String> valueOps = redis.opsForValue();
        String value = valueOps.get(key);
        return value;
    }


    public void setValueOps(String key, String value, Long expire, TimeUnit timeUnit) {
        ValueOperations<String, String> valueOps = redis.opsForValue();
        valueOps.set(key, value, expire, timeUnit);
        System.out.println("我已经被成功放入Redis了！");
    }



}
