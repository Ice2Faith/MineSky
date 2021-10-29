package com.i2f.mq.rabbit.config.defaults.topic.impl;

import com.i2f.mq.rabbit.config.defaults.topic.AbsTopicRabbitMqConfig;
import org.springframework.context.annotation.Configuration;

/**
 * @author ltb
 * @date 2021/9/13
 */
@Configuration
public class DefaultTopicRabbitMqConfig extends AbsTopicRabbitMqConfig {
    public static final String DEFAULT_TOPIC_NAME="defaultTopic";
    public static final String DEFAULT_TOPIC_EXCHANGE_NAME="defaultTopicExchange";

    @Override
    protected String getTopic() {
        return DEFAULT_TOPIC_NAME;
    }

    @Override
    protected String getExchange() {
        return DEFAULT_TOPIC_EXCHANGE_NAME;
    }
}
