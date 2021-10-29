package com.i2f.mq.rabbit.config.defaults.topic.impl;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Map;

/**
 * @author ltb
 * @date 2021/9/13
 */
@RabbitListener(queues = DefaultTopicRabbitMqConfig.DEFAULT_TOPIC_NAME)
public class DefaultTopicRabbitMqReceiver {

    @RabbitHandler
    public void handleMsg(Map<String,Object> map){
        Object data=map.get("data");
        process(map);
        processData(data);
    }

    protected void process(Map<String,Object> map){
        System.out.println("DefaultTopicRabbitMqReceiver:"+map);
    }

    protected void processData(Object obj){
        System.out.println("DefaultTopicRabbitMqReceiver:data:"+obj);
    }
}
