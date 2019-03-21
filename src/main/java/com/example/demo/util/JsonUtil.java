package com.example.demo.util;

/**
 * <描述信息>
 *
 * @author zhoufeng
 * @date 2019-03-19 下午6:26
 **/
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonUtil {
    public static final String DATEFORMAT_ISO8601 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATEFORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss'+08:00'";
    static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setDateFormat(new SimpleDateFormat(DATEFORMAT_UTC));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static void setDateFormat(String dateFormat) {
        mapper.setDateFormat(new SimpleDateFormat(dateFormat));
    }

    public static String toJson(Object obj) {
        if (obj == null) return null;
        if (obj.getClass() == String.class) return obj.toString();
        String json;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }


    public static <T> T fromJson(String content, Class<T> classType) {
        if (!jsonCheck(content)) return null;
        if (classType == String.class) return (T) content;
        try {
            return mapper.readValue(content, classType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> T fromJson(String content, TypeReference<T> valueTypeRef) {
        if (!jsonCheck(content)) return null;
        try {
            return (T) mapper.readValue(content, valueTypeRef);
        } catch (IOException e) {
            throw new RuntimeException(String.format("json:%s\nmsg:%s", content, e.getMessage()),e);
        }
    }

    static boolean jsonCheck(String content) {
        if (content == null || content.equals("")) return false;
        return true;
    }
}
