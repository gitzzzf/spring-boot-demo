package com.example.demo.repository;

import com.example.demo.bean.domain.City;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * < ES 数据操作层>
 *
 * @author zhoufeng
 * @date 2019-05-03 上午11:27
 **/
@Component
public interface CityRepository extends ElasticsearchRepository<City, Integer>{
    //use default method
}
