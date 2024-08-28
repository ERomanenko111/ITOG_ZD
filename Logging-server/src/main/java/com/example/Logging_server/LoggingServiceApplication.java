package com.example.Logging_server;

import com.example.Logging_server.repository.EnableEurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
public class LoggingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoggingServiceApplication.class, args);
	}
}