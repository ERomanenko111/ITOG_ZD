package com.example.Task_server;

import com.example.Task_server.repository.EnableEurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TaskServiceApplication.java
@SpringBootApplication
@EnableEurekaClient
public class TaskServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskServiceApplication.class, args);
	}
}
