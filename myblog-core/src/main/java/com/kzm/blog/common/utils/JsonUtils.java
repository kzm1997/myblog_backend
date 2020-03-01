package com.kzm.blog.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Author: kouzm
 * @Description:
 * @Date: Created in 22:38 2020/2/26
 * @Version
 */
@Slf4j
public class JsonUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * json字符串转化成对象
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObj(String jsonString, Class<T> clazz) {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error("json字符串转化对象出错");
        }
        return null;
    }

    /**
     * javaBean 转化成json字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        if (obj instanceof Integer || obj instanceof Long || obj instanceof Float
                || obj instanceof Double || obj instanceof Boolean || obj instanceof String){
            return String.valueOf(obj);
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("javabean转化成json字符串出错");
        }
        return null;
    }

}
