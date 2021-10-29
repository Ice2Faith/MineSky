package com.i2f.framework.converter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ltb
 * @date 2021/9/2
 */
@Configuration
public class DateConverter implements Converter<String, Date> {
    public static String[] supportsDateFmtList={
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd",
            "yyyy-MM-dd HH:mm:ss SSS",
            "yyyy-MM",
            "yyyyMMdd",
            "yyyyMM",
            "yyyyMMddHH",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd HH",
            "yyyy"
    };
    @Override
    public Date convert(String s) {
        for(String fmt : supportsDateFmtList){
            SimpleDateFormat fmter = new SimpleDateFormat(fmt);
            try{
                return fmter.parse(s);
            }catch(Exception e){

            }
        }
        System.out.println("unsupport date format of:"+s);
        return null;
    }


}
