// LoggingControllerTests.java
package com.example.Logging_server;

import com.example.Logging_server.controller.LoggingController;
import com.example.Logging_server.model.LogEntry;
import com.example.Logging_server.repository.LogRepository;
import com.example.User_server.Token.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoggingControllerTests {

	@InjectMocks
	private LoggingController loggingController;

	@Mock
	private LogRepository logRepository;

	@Mock
	private JwtUtil jwtUtil;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testLogAction() {
		LogEntry logEntry = new LogEntry(1L, "Test Action");
		when(jwtUtil.validateToken(anyString(), anyString())).thenReturn(true);
		when(logRepository.save(logEntry)).thenReturn(logEntry);

		ResponseEntity<LogEntry> response = loggingController.logAction(logEntry, "Bearer token");

		assertEquals(200, response.getStatusCodeValue());
	}

	// Другие тесты для методов контроллера...
}