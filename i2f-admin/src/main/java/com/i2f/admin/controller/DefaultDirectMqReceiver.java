package com.i2f.admin.controller;

import com.i2f.mq.rabbit.config.defaults.direct.impl.DefaultDirectRabbitMqReceiver;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ltb
 * @date 2021/9/13
 */
@Component
public class DefaultDirectMqReceiver extends DefaultDirectRabbitMqReceiver {
    @Override
    protected void process(Map<String, Object> map) {
        System.out.println("process:"+map);
    }

    @Override
    protected void processData(Object obj) {
        System.out.println("processData:"+obj);
    }
}
