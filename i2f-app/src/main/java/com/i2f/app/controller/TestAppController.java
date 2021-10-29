package com.i2f.app.controller;

import com.i2f.service.IAppTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ltb
 * @date 2021/8/30
 */
@RestController
@RequestMapping("app/test")
public class TestAppController {

    @Autowired
    private IAppTestService appTestService;

    @GetMapping("svc")
    public Object testSvc(String val){
        Object rs= appTestService.test(val);
        return rs;
    }

    @GetMapping("db")
    public Object testDb(String val){
        Object rs= appTestService.testDb(val);
        return rs;
    }
}
