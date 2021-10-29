package com.i2f.mq.rabbit.config.defaults.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * @author ltb
 * @date 2021/9/13
 */
public abstract class AbsFanoutRabbitMqConfig {

    protected abstract String getQueueName();

    protected abstract String getQueueExchange();

    @Bean
    public Queue fanoutQueue(){
        return new Queue(getQueueName());
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(getQueueExchange());
    }

    @Bean
    public Binding bindingExchange(){
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }
}
