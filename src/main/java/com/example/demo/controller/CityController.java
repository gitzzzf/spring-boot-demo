package com.example.demo.controller;

import com.example.demo.bean.model.CityModel;
import com.example.demo.bean.po.CityPO;
import com.example.demo.redis.DemoRedisDAO;
import com.example.demo.service.ICityService;
import com.zzzf.demo.Test;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-15 下午5:58
 **/
@RestController
@RequestMapping(value = "/city")
public class CityController {

    @Resource
    private ICityService cityService;
    @Resource
    private DemoRedisDAO demoRedisDAO;

    @ApiOperation(value = "获取简单城市信息", notes = "这是一个通过城市id获取城市信息的方法，id为0时，报参数错误")
    @RequestMapping(value = "/simple", method = RequestMethod.GET)
    public Object getSimpleCity(@RequestParam(value = "id") int id){
        System.out.println(Test.myTest());
        if (id == 0){
            return "参数错误";
        }
        CityModel city = cityService.getSimpleCity();
        return city.toString();
    }


    @ApiOperation(value = "多线程获取城市信息")
    @RequestMapping(value = "/more", method = RequestMethod.GET)
    public Object getMoreCity(){
        List<CityPO> cityList = cityService.getMoreCity();
        StringBuilder stringBuilder = new StringBuilder();
        for (CityPO po : cityList) {
            stringBuilder.append(po.toString());
        }
        return stringBuilder.toString();
    }


    @ApiOperation(value = "测试Redis集成")
    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    public Object getRedisCity(){
        demoRedisDAO.setValueOps("zhoufeng", "test redis", 60L, TimeUnit.SECONDS);
        return "success";
    }

    @ApiOperation(value = "测试rabbitmq集成")
    @RequestMapping(value = "/mq")
    public Object mqCity(){
        cityService.testCityMq();
        return "success";
    }

    @ApiOperation(value = "测试dispatch")
    @RequestMapping(value = "/dispatch")
    public Object dispatch(){
        try {
            CityPO cityPO = cityService.testDistributed();
            return cityPO.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
