package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-14 下午7:30
 **/

@RestController
public class DemoController {

    @RequestMapping("/hello")
    public String sayHello(){
        return "hello";
    }
}

