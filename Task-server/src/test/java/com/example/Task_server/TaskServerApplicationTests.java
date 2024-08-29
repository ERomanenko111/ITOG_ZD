// TaskControllerTests.java
package com.example.Task_server;

import com.example.Task_server.controller.TaskController;
import com.example.Task_server.model.Task;
import com.example.Task_server.repository.TaskRepository;
import com.example.User_server.Token.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskControllerTests {

	@InjectMocks
	private TaskController taskController;

	@Mock
	private TaskRepository taskRepository;

	@Mock
	private JwtUtil jwtUtil;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllTasks() {
		Task task1 = new Task("Task 1", LocalDateTime.now().plusDays(1), "NEW");
		Task task2 = new Task("Task 2", LocalDateTime.now().plusDays(2), "COMPLETED");
		List<Task> tasks = Arrays.asList(task1, task2);

		when(taskRepository.findAll()).thenReturn(tasks);

		ResponseEntity<List<Task>> response = taskController.getAllTasks();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(tasks, response.getBody());
	}

	// Другие тесты для методов контроллера...
}