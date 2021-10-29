package com.i2f.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author ltb
 * @date 2021/10/15
 */
@Configuration
public class RestClientConfig {

    @Bean
    public RestTemplate getRestTemplate(){
        RestTemplate rest=new RestTemplate();
        return rest;
    }

}
