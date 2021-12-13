package com.i2f.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//多数据源配置，需要去除自动数据源配置的项
//统一验证鉴权登录移到zuul网关，因此这里去除SpringSecurity自动配置
@SpringBootApplication(scanBasePackages = "com",
		exclude = {
				DataSourceAutoConfiguration.class,
				DataSourceTransactionManagerAutoConfiguration.class,

				SecurityAutoConfiguration.class,
				SecurityFilterAutoConfiguration.class,
				ManagementWebSecurityAutoConfiguration.class})
@EnableAspectJAutoProxy
@EnableFeignClients
@EnableDiscoveryClient
//@MapperScan({"com.**.dao","com.**.mapper"}) //多数据源配置不再需要此配置
public class I2fAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(I2fAppApplication.class, args);
	}

}
