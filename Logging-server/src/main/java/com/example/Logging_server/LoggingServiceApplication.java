package com.example.Logging_server;

import com.example.Logging_server.repository.EnableEurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.example.Logging_server", "com.example.User_server.Token"})
public class LoggingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoggingServiceApplication.class, args);
	}
}