package com.i2f.mq.rabbit.config.defaults.direct.impl;

import com.i2f.mq.rabbit.config.defaults.direct.AbsDirectRabbitMqConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ltb
 * @date 2021/9/13
 */
@Configuration
public class DefaultDirectRabbitMqConfig extends AbsDirectRabbitMqConfig {
    public static final String DEFAULT_DIRECT_QUEUE_NAME="defaultDirectQueue";
    public static final String DEFAULT_DIRECT_EXCHANGE_NAME="defaultDirectExchange";
    public static final String DEFAULT_DIRECT_ROUTING_NAME="defaultDirectRouting";
    public static final String DEFAULT_LONELY_DIRECT_EXCHANGE_NAME="defaultLonelyDirectExchange";

    @Override
    protected String getQueueName() {
        return DEFAULT_DIRECT_QUEUE_NAME;
    }

    @Override
    protected String getExchangeName() {
        return DEFAULT_DIRECT_EXCHANGE_NAME;
    }

    @Override
    protected String getRoutingName() {
        return DEFAULT_DIRECT_ROUTING_NAME;
    }

    @Bean
    public DirectExchange lonelyDefaultDirectExchange() {
        return new DirectExchange(DEFAULT_LONELY_DIRECT_EXCHANGE_NAME);
    }

}
