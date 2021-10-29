package com.i2f.mq.rabbit.util;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ltb
 * @date 2021/9/13
 */
@Component
public class RabbitMqUtil {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String exchange,String routing,Object msg){
        rabbitTemplate.convertAndSend(exchange, routing, msg);
    }
}
