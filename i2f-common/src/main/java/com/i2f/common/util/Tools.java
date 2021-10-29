package com.i2f.common.util;

import com.i2f.common.data.Constants;

import java.text.SimpleDateFormat;

/**
 * @author ltb
 * @date 2021/9/7
 */
public class Tools {
    public static ThreadLocal<SimpleDateFormat> DATE_FMT=ThreadLocal.withInitial(()->{
       return new SimpleDateFormat(Constants.PROJ_DEFAULT_DATE_FMT);
    });
}
