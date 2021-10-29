package com.i2f.framework.resolver;

import com.i2f.common.data.RespData;
import com.i2f.common.exception.ServiceException;
import com.i2f.common.util.JacksonUtil;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URLEncoder;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author ltb
 * @date 2021/9/2
 */
@RestController
@ControllerAdvice
public class ServerExceptionResolver implements HandlerExceptionResolver, ErrorController {

    @RequestMapping("/error")
    public void whenError(HttpServletRequest request,HttpServletResponse response){
        this.onRequestNotFound404(null,request,response,null,null);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();
        this.preOnProcess(mv, httpServletRequest, httpServletResponse, o, e);
        if (e instanceof NoHandlerFoundException) {
            this.onRequestNotFound404(mv, httpServletRequest, httpServletResponse, o, (NoHandlerFoundException)e);
        } else if (e instanceof UndeclaredThrowableException) {
            this.onAopException(mv, httpServletRequest, httpServletResponse, o, (Exception)((UndeclaredThrowableException)e).getUndeclaredThrowable());
        } else if (e instanceof ServiceException) {
            this.onServerException(mv, httpServletRequest, httpServletResponse, o, (ServiceException)e);
        } else if (e instanceof SQLIntegrityConstraintViolationException) {
            this.onSqlConstraintViolationException(mv, httpServletRequest, httpServletResponse, o, (SQLIntegrityConstraintViolationException)e);
        } else if (e instanceof RuntimeException) {
            this.onRuntimeException(mv, httpServletRequest, httpServletResponse, o, (RuntimeException)e);
        } else {
            this.onOtherExceptions(mv, httpServletRequest, httpServletResponse, o, e);
        }

        return mv;
    }

    protected void responseException(HttpServletResponse response, RespData data) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(200);
            response.getWriter().write(JacksonUtil.toJson(data));
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    protected void preOnProcess(ModelAndView mv, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        System.out.println("---------ex:" + e.getMessage());
        System.out.println("---Global Exception:");
        e.printStackTrace();
    }

    protected void onRequestNotFound404(ModelAndView mv, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, NoHandlerFoundException e) {
        this.responseException(httpServletResponse, RespData.error("404,连接走丢了，请检查URL是否正确或联系管理员."));
    }

    protected void onAopException(ModelAndView mv, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        this.responseException(httpServletResponse, RespData.error("AOP异常:" + e.getMessage()));
    }

    protected void onServerException(ModelAndView mv, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ServiceException e) {
        this.responseException(httpServletResponse, RespData.error("业务异常:" + e.getMessage()));
    }

    protected void onSqlConstraintViolationException(ModelAndView mv, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, SQLIntegrityConstraintViolationException e) {
        this.responseException(httpServletResponse, RespData.error("SQL键约束异常:" + e.getMessage()));
    }

    protected void onRuntimeException(ModelAndView mv, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, RuntimeException e) {
        this.responseException(httpServletResponse, RespData.error("系统运行异常:" + e.getMessage()));
    }

    protected void onOtherExceptions(ModelAndView mv, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        try {
            mv.setViewName("redirect:http://www.baidu.com/s?wd=" + URLEncoder.encode(e.getMessage(), "utf-8"));
        } catch (Exception var7) {
            System.out.println(e.getMessage());
        }

    }
}
