package com.example.demo.bean;

import com.example.demo.DemoApplication;
import com.example.demo.bean.model.ZzzfProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DemoApplication.class})
public class ZzzfPropertiesTest {

    @Autowired
    private ZzzfProperties properties;

    @Test
    public void properties(){
        assertEquals("zhoufeng", properties.getName());
        System.out.println(properties.getDesc());
        System.out.println("我是无敌的前缀：" + properties.getAge());

    }


}