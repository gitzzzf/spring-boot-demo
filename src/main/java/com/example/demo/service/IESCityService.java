package com.example.demo.service;

import com.example.demo.bean.domain.City;

import java.util.List;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-05-03 上午11:34
 **/
public interface IESCityService {
    /**
     * save
     * @return city主键id
     */
    int saveCity(City city);

    /**
     * search
     * @param pageNum
     * @param pageSize
     * @param searchContent
     * @return
     */
    List<City> searchCity(int pageNum, int pageSize, String searchContent);

}
