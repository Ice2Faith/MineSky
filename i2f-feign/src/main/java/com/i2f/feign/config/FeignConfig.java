package com.i2f.feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author ltb
 * @date 2021/10/15
 */
@Configuration
@EnableFeignClients("com.i2f.feign.api")
public class FeignConfig {
}
