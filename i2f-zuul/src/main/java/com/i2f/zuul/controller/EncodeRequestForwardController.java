package com.i2f.zuul.controller;

import com.i2f.common.data.RespData;
import com.i2f.common.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ltb
 * @date 2021/10/8
 */
@Controller
public class EncodeRequestForwardController {
    private Logger logger=LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/enc/{encodeUrl}")
    public void encodeUrlForward(@PathVariable("encodeUrl")String encodeUrl, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] data=Base64Util.decodeUrl(encodeUrl);
        String trueUrl= new String(data,"UTF-8");
        logger.info("forward:\n\tsrc:"+encodeUrl+"\n\t"+"dst:"+trueUrl);
        request.getRequestDispatcher(trueUrl).forward(request,response);
    }

    @ResponseBody
    @RequestMapping("test/enc")
    public RespData testEnc(String val){
        return RespData.success("enc:"+val);
    }
}
