package com.example.demo.bean.msg;

import java.util.Date;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-19 下午2:09
 **/
public class CityMsg extends BaseMsg{
    private int provinceId;
    private String cityName;

    public CityMsg(String type) {
        super(type, new Date().getTime());
    }

    public int getProvinceId() {
        return provinceId;
    }

    public CityMsg setProvinceId(int provinceId) {
        this.provinceId = provinceId;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public CityMsg setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }
}
