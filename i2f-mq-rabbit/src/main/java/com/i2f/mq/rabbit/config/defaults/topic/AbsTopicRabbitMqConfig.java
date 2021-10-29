package com.i2f.mq.rabbit.config.defaults.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;

/**
 * @author ltb
 * @date 2021/9/13
 */
public abstract class AbsTopicRabbitMqConfig {

    protected abstract String getTopic();

    protected abstract String getExchange();
    @Bean
    public Queue topicQueue(){
        return new Queue(getTopic());
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(getExchange());
    }

    @Bean
    public Binding bindingExchangeMessage(){
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with(getTopic());
    }

}
