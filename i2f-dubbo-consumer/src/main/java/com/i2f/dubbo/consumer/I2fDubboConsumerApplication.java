package com.i2f.dubbo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com")
public class I2fDubboConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(I2fDubboConsumerApplication.class, args);
	}
}
