package com.i2f.mq.rabbit.config.defaults.direct.impl;

import com.i2f.mq.rabbit.util.RabbitMqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ltb
 * @date 2021/9/13
 */
@Component
public class DefaultDirectRabbitMqSender {

    @Autowired
    private RabbitMqUtil rabbitMqUtil;

    public void send(Map<String,Object> msg){
        rabbitMqUtil.send(DefaultDirectRabbitMqConfig.DEFAULT_DIRECT_EXCHANGE_NAME,
                DefaultDirectRabbitMqConfig.DEFAULT_DIRECT_ROUTING_NAME,
                msg);
    }
    public void sendData(Object data){
        Map<String,Object> map=new HashMap<>();
        map.put("data",data);
        send(map);
    }
}
