package com.i2f.mq.rabbit.config.defaults.direct.impl;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Map;

/**
 * @author ltb
 * @date 2021/9/13
 */
@RabbitListener(queues = DefaultDirectRabbitMqConfig.DEFAULT_DIRECT_QUEUE_NAME)
public class DefaultDirectRabbitMqReceiver {

    @RabbitHandler
    public void handleMsg(Map<String,Object> map){
        Object data=map.get("data");
        process(map);
        processData(data);
    }

    protected void process(Map<String,Object> map){
        System.out.println("DefaultDirectRabbitMqReceiver:"+map);
    }

    protected void processData(Object obj){
        System.out.println("DefaultDirectRabbitMqReceiver:data:"+obj);
    }
}
