package com.i2f.zuul.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class ParamsSpyInterceptor extends HandlerInterceptorAdapter {
    //before come into controller to handle,you can check parameters
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    //before response,you can modify response content
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }


    //all done ,cannot modify response
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);

        //here print request log info
        String reqUrl=request.getRequestURI();
        String method=request.getMethod();
        System.out.println("---- request:"+method+" : "+reqUrl+" ----");
        Map<String,String[]> reqParams=request.getParameterMap();
        if(reqParams!=null && reqParams.size()>0){
            System.out.println("+parameters:");
            for(String key : reqParams.keySet()){
                System.out.println("+\t"+key+":");
                String[] vals=reqParams.get(key);
                for(String val : vals){
                    System.out.println("+\t\t"+val);
                }
            }
            System.out.println("--------");
        }
        if(ex!=null){
            System.out.println("--Exception-- request:"+reqUrl+" ----");
            ex.printStackTrace();
        }

    }
}
