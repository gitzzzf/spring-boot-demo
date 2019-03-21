package com.example.demo.bean.model;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-14 下午8:21
 **/

@Component
@ConfigurationProperties(prefix = "com.zzzf")
public class ZzzfProperties {
//    @Value("${com.zzzf.name}")
    private String name;

//    @Value("${com.zzzf.sex}")
    private String sex;

//    @Value("${com.random.int}")
    private int age;

//    @Value("${com.zzzf.desc}")
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
