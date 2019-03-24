package com.example.demo.service;

import com.example.demo.bean.model.CityModel;
import com.example.demo.bean.po.CityPO;

import java.util.List;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-15 下午6:05
 **/
public interface ICityService {

    CityModel getSimpleCity();

    List<CityPO> getMoreCity();

    void testCityMq();

    CityPO testDistributed() throws Exception;
}
