// UserControllerTests.java
package com.example.User_server;

import com.example.User_server.controller.UserController;
import com.example.User_server.model.User;
import com.example.User_server.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTests {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testRegister() {
		User user = new User();
		user.setUsername("testuser");

		when(userService.register(user)).thenReturn(user);

		ResponseEntity<User> response = userController.register(user);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(user, response.getBody());
	}

	// Другие тесты для методов контроллера...
}