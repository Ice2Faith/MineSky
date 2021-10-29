package com.i2f.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.i2f.dubbo.api.ITestService;

/**
 * @author ltb
 * @date 2021/9/22
 */
@Service
public class TestServiceImpl implements ITestService {
    @Override
    public String getData(String data) {
        return data+":"+System.currentTimeMillis();
    }
}
