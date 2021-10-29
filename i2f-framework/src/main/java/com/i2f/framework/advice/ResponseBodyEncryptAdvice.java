package com.i2f.framework.advice;

import com.i2f.common.data.RespData;
import com.i2f.common.util.AESUtil;
import com.i2f.framework.advice.annotation.SecureParams;
import com.i2f.framework.advice.annotation.StandardResponse;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ResponseBodyEncryptAdvice implements ResponseBodyAdvice<Object> {
    public static final String SECURE_DATA_HEADER="secure-data";

    public static String AES_KEY=AESUtil.genKey("ltb12315");
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(methodParameter.getMethod().isAnnotationPresent(StandardResponse.class)){
            o=RespData.success(o);
        }
        if(o==null){
            return o;
        }
        if(o instanceof RespData){
            RespData resp=(RespData)o;
            if(methodParameter.getMethod().isAnnotationPresent(SecureParams.class)){
                SecureParams secureParams=methodParameter.getMethodAnnotation(SecureParams.class);
                if(secureParams.out()){
                    List<String> headers=new ArrayList<>();
                    headers.add(SECURE_DATA_HEADER);
                    serverHttpResponse.getHeaders().setAccessControlExposeHeaders(headers);
                    serverHttpResponse.getHeaders().set(SECURE_DATA_HEADER,"true");
                    Object data=resp.getData();
                    String enData=AESUtil.encryptObj(data,AES_KEY);
                    resp.setData(enData);
                    Map<String,Object> kvs=resp.getKvs();
                    Map<String,Object> enKvs=new HashMap<>();
                    for(Map.Entry<String,Object> item : kvs.entrySet()){
                        Object idata=item.getValue();
                        String ienData=AESUtil.encryptObj(idata,AES_KEY);
                        enKvs.put(item.getKey(),ienData);
                    }
                    resp.setKvs(enKvs);
                }
            }
            return resp;
        }
        return o;
    }

}
