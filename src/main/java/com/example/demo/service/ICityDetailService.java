package com.example.demo.service;

import com.example.demo.bean.po.CityPO;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-23 下午4:06
 **/
public interface ICityDetailService {
    CityPO getCityDetailByProvinceId(int provinceId);
}
