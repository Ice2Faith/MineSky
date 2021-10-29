package com.i2f.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com")
public class I2fBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(I2fBatchApplication.class, args);
	}

}
