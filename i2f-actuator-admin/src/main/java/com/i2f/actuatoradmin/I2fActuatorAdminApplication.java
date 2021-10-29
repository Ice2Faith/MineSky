package com.i2f.actuatoradmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class I2fActuatorAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(I2fActuatorAdminApplication.class, args);
	}

}
