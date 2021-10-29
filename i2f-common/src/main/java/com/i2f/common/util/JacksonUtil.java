package com.i2f.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.i2f.common.data.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * @author ltb
 * @date 2021/8/31
 */
public class JacksonUtil {
    private static Logger logger=LoggerFactory.getLogger(JacksonUtil.class);
    private static volatile ObjectMapper objectMapper;
    public static ObjectMapper getObjectMapper(){
        if(objectMapper==null){
            synchronized (JacksonUtil.class){
                if(objectMapper==null){
                    objectMapper=new ObjectMapper();
                }
            }
        }
        objectMapper.setDateFormat(new SimpleDateFormat(Constants.PROJ_DEFAULT_DATE_FMT));
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        return objectMapper;
    }
    public static String toJson(Object obj){
        String json=null;
        try {
            json=getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("JacksonUtil:toJson:error",e.getMessage());
        }
        return json;
    }
    public static <T> T parseJson(String json,Class<? extends T> clazz){
        T obj=null;
        try {
            obj=getObjectMapper().readValue(json,clazz);
        } catch (JsonProcessingException e) {
            logger.error("JacksonUtil:parseJson:error",e.getMessage());
        }
        return obj;
    }
    public static <T> T parseRefJson(String json, TypeReference<T> type){
        T ret=null;
        try {
            ret=getObjectMapper().readValue(json,type);
        } catch (JsonProcessingException e) {
            logger.error("JacksonUtil:parseRefJson:error",e.getMessage());
        }
        return ret;
    }
}
