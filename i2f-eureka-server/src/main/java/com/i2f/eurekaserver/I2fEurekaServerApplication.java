package com.i2f.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(scanBasePackages = "com.i2f")
@EnableEurekaServer
public class I2fEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(I2fEurekaServerApplication.class, args);
	}

}
