package com.example.Logging_server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id; // Исправлено

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "logs")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String action;

    private LocalDateTime timestamp;

    public LogEntry() {
    }

    public LogEntry(Long userId, String action) {
        this.userId = userId;
        this.action = action;
        this.timestamp = LocalDateTime.now();
    }
}
