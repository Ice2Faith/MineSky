package com.i2f.dubbo.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com")
@EnableDubbo //作为服务提供者时，必须在启动类上加注解
public class I2fDubboProviderApplication {

	public static void main(String[] args)  {
		SpringApplication.run(I2fDubboProviderApplication.class, args);
	}

}
