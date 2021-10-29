package com.i2f.common.api.test;

import com.i2f.common.data.RespData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ltb
 * @date 2021/9/7
 */
@RestController
@RequestMapping("common/api/test")
public class TestController {
    @RequestMapping("echo")
    public RespData echo(String val){
        return RespData.success(val);
    }
}
