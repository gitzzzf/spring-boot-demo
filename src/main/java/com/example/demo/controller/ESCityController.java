package com.example.demo.controller;

import com.example.demo.service.IESCityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <es controller>
 *
 * @author zhoufeng
 * @date 2019-05-03 下午12:40
 **/
@RestController
@RequestMapping("/es")
public class ESCityController {
    @Resource
    private IESCityService esCityService;


    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ApiOperation("测试ES")
    public Object testES(@RequestParam(value = "search") String search){
        return esCityService.searchCity(0 ,0, search);
    }
}
