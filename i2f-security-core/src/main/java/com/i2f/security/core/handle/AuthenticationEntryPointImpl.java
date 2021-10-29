package com.i2f.security.core.handle;

import com.i2f.common.data.HttpStatus;
import com.i2f.common.data.RespData;
import com.i2f.common.util.JacksonUtil;
import com.i2f.common.util.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author ltb
 * @date 2021/8/31
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        System.out.println("============commence==========");
    	int code = HttpStatus.UNAUTHORIZED;
        String msg = String.format("请求访问：%s，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.respJson(response, JacksonUtil.toJson(RespData.error(code,msg)));
    }
}
