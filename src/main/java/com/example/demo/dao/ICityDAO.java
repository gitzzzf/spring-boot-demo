package com.example.demo.dao;

import com.example.demo.bean.po.CityPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-15 下午5:49
 **/

@Mapper
public interface ICityDAO {

//    @Select("select * from test.city where province_id = #{provinceId}")
    CityPO selectByProvinceId(@Param(value = "provinceId") int provinceId);

}
