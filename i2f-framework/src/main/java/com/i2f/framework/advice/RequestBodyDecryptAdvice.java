package com.i2f.framework.advice;

import com.i2f.common.util.AESUtil;
import com.i2f.framework.advice.annotation.SecureParams;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

//注意，接受参数需要加上 @RequestBody注解，否则不会被拦截，另外需要以POST方式application/json方式提交
//原因间此类中的调用：RequestResponseBodyMethodProcessor
@ControllerAdvice
public class RequestBodyDecryptAdvice implements RequestBodyAdvice {

    public static String AES_KEY=AESUtil.genKey("ltb12315");

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        if(methodParameter.getMethod().isAnnotationPresent(SecureParams.class)){
            SecureParams secureParams=methodParameter.getMethodAnnotation(SecureParams.class);
            if(secureParams.in()){
                return new RequestHttpInputMessage(httpInputMessage);
            }
        }
        return httpInputMessage;
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    public class RequestHttpInputMessage implements HttpInputMessage{
        public InputStream body;
        public HttpHeaders headers;
        public RequestHttpInputMessage(HttpInputMessage inputMessage) throws IOException {
            this.headers=inputMessage.getHeaders();
            String bodyStr= FileCopyUtils.copyToString(new InputStreamReader(inputMessage.getBody()));
            System.out.println("bodyStr:"+bodyStr);
            String data=AESUtil.decryptObj(bodyStr,AES_KEY,String.class);
            System.out.println("data:"+data);
            byte[] deData=data.getBytes();
            this.body=new ByteArrayInputStream(deData);
        }
        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
