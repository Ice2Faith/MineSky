package com.i2f.quartz.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author ltb
 * @date 2021/9/6
 */
@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactory getStdSchedulerFactory(){
        return new StdSchedulerFactory();
    }


}
