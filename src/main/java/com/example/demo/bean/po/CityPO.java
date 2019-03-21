package com.example.demo.bean.po;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-15 下午5:47
 **/
public class CityPO {

    private int id;
    private int provinceId;
    private String cityName;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("provinceId", provinceId)
                .append("cityName", cityName)
                .append("description", description)
                .toString();
    }
}
