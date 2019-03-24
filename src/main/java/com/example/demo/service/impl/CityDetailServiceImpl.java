package com.example.demo.service.impl;

import com.example.demo.bean.po.CityPO;
import com.example.demo.dao.ICityDAO;
import com.example.demo.service.ICityDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-23 下午4:08
 **/
@Service
public class CityDetailServiceImpl implements ICityDetailService {
    @Resource
    private ICityDAO cityDAO;

    @Override
    public CityPO getCityDetailByProvinceId(int provinceId) {
        CityPO cityPO = new CityPO();
        System.out.println("参数为:" + provinceId);
        try {
            cityPO = cityDAO.selectByProvinceId(provinceId);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("结果为：" + cityPO.getProvinceId() + ", " + cityPO.getCityName());
        return cityPO;
    }
}
