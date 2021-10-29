package com.i2f.security.core.filter;

import com.i2f.common.util.Base64Util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ltb
 * @date 2021/10/9
 */
//@WebFilter
public class EncodeRequestUrlForwardFilter implements Filter{
    public static volatile String ENCODE_URL_PREFIX="/enc/";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        String url=request.getRequestURI();
//        String params=request.getQueryString();
//        if(params!=null && !"".equals(params)){
//            url=url+"?"+params;
//        }

        System.out.println("encode:filter:come...");
        System.out.println("url:"+url);
        if(url.startsWith(ENCODE_URL_PREFIX)){
            String encUrl=url.substring(ENCODE_URL_PREFIX.length());
            String trueUrl= new String(Base64Util.decodeUrl(encUrl),"UTF-8");
            trueUrl="/"+trueUrl;
            System.out.println("encode:url:\n\t"+encUrl+"\n\t->\n\t"+trueUrl);
            request.getRequestDispatcher(trueUrl).forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
