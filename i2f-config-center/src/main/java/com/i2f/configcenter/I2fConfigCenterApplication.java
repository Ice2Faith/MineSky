package com.i2f.configcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
public class I2fConfigCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(I2fConfigCenterApplication.class, args);
	}

}
