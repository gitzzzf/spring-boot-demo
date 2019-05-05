package com.example.demo.bean.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * < ES 数据实体 - 城市>
 *
 * @author zhoufeng
 * @date 2019-05-03 上午10:58
 **/
@Data
@Document(indexName = "province", type = "city")
public class City implements Serializable{
    private static final long serialVersionUID = -4064389332188467314L;
    // 主键id
    @Field(store = true)
    private int id;
    // 城市编码
    @Field(store = true)
    private int code;
    // 城市名
    @Field(store = true, type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String name;
    // 城市简介
    @Field(store = true, type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String description;
}
