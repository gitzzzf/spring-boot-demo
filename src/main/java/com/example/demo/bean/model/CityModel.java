package com.example.demo.bean.model;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-15 下午6:01
 **/
public class CityModel {
    private int id;
    private int provinceId;
    private String cityName;
    private String description;

    public int getId() {
        return id;
    }

    public CityModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public CityModel setProvinceId(int provinceId) {
        this.provinceId = provinceId;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public CityModel setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CityModel setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("id", id)
                .append("provinceId", provinceId)
                .append("cityName", cityName)
                .append("description", description)
                .toString();
    }
}
