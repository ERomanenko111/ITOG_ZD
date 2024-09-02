package com.example.Task_server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogRequest {
    private String username;
    private LocalDateTime timestamp;
    private String action;
    private String details;
}
