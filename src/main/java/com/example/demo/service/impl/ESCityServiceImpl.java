package com.example.demo.service.impl;

import com.example.demo.bean.domain.City;
import com.example.demo.repository.CityRepository;
import com.example.demo.service.IESCityService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-05-03 上午11:34
 **/
@Service
public class ESCityServiceImpl implements IESCityService{
    public static final Logger LOGGER = LoggerFactory.getLogger(ESCityServiceImpl.class);
    /* 分页参数 */
    Integer PAGE_SIZE = 12;          // 每页数量
    Integer DEFAULT_PAGE_NUM = 0; // 默认当前页码
    /* 搜索模式 */
    String SCORE_MODE_SUM = "sum"; // 权重分求和模式
    Float  MIN_SCORE = 10.0F;      // 由于无相关性的分值默认为 1 ，设置权重分最小值为 10

    @Autowired
    private CityRepository cityRepository;

    @Override
    public int saveCity(City city) {
        City cityRes = cityRepository.save(city);
        return cityRes.getId();
    }

    @Override
    public List<City> searchCity(int pageNum, int pageSize, String searchContent) {
        // 校验分页参数
        if (pageSize <= 0) {
            pageSize = PAGE_SIZE;
        }
        if (pageNum < DEFAULT_PAGE_NUM) {
            pageNum = DEFAULT_PAGE_NUM;
        }
        // 构建搜索参数
        SearchQuery citySearchQuery = getCitySearchQuery(pageNum, pageSize, searchContent);
        Page<City> cities = cityRepository.search(citySearchQuery);
        Iterator<City> cityIterator = cities.iterator();
        while (cityIterator.hasNext()){
            City city = cityIterator.next();
            System.out.println("匹配城市：" + city);
        }
        return cities.getContent();
    }

    /**
     * 构建搜索参数
     * @param pageNum
     * @param pageSize
     * @param searchContent
     * @return
     */
    private SearchQuery getCitySearchQuery(int pageNum, int pageSize, String searchContent) {
        // 分页
        Pageable page = PageRequest.of(pageNum, pageSize);
        // 搜索查询
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(searchContent);

        return new NativeSearchQueryBuilder()
                .withPageable(page)
                .withQuery(queryBuilder)
                .build();
    }

}
