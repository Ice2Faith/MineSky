package com.i2f.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.i2f.dubbo.api.ITestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ltb
 * @date 2021/9/22
 */
@RestController
public class TestController {

    @Reference
    private ITestService testService;

    @RequestMapping("test")
    private Object doTest(){
        return testService.getData("aaa");
    }
}
