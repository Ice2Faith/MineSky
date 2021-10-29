package com.i2f.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication(scanBasePackages = "com")
@EnableZuulProxy
@EnableEurekaClient
public class I2fZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(I2fZuulApplication.class, args);
	}

}
