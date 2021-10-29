package com.i2f.common.util;

/**
 * @author ltb
 * @date 2021/9/2
 */
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Base64;

public class Base64Util {
    public static final String CHAR_SET="UTF-8";
    public Base64Util() {
    }

    public static byte[] decodeUrl(String urlBs4){
        return Base64.getUrlDecoder().decode(urlBs4);
    }

    public static String encodeUrl(byte[] data){
        return Base64.getUrlEncoder().encodeToString(data);
    }

    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    public static byte[] decode(byte[] base64) {
        return Base64.getDecoder().decode(base64);
    }

    public static String encodeObj(Object obj){
        try{
            String json=JacksonUtil.toJson(obj);
            return encode(json.getBytes(CHAR_SET));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T decodeObj(String str,Class<T> clazz){
        try{
            byte[] data=decode(str);
            String json=new String(data,CHAR_SET);
            return JacksonUtil.parseJson(json,clazz);
        }catch(Exception e){
           e.printStackTrace();
        }
        return null;
    }

    public static <T> T decodeRefObj(String str, TypeReference<T> type){
        try{
            byte[] data=decode(str);
            String json=new String(data,CHAR_SET);
            return JacksonUtil.parseRefJson(json,type);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
