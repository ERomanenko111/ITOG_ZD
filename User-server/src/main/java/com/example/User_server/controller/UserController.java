package com.example.User_server.controller;

import com.example.User_server.model.User;
import com.example.User_server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Пользователи")
public class UserController {
    private final UserService service;

    @Operation(summary = "Получение пользователя")
    @GetMapping("/{username}")
    public User getByUsername(@PathVariable String username) {
        return service.getByUsername(username);
    }

}
