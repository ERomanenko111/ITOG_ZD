package com.example.Logging_server.controller;

import com.example.Logging_server.model.Logging;
import com.example.Logging_server.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.auth.BearerToken;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class LoggingController {
    private final LoggingService loggingService;

    @PostMapping
    public ResponseEntity<Void> logAction(@RequestBody Logging logging) {
        loggingService.logAction(logging.getUsername(), logging.getAction(), logging.getDetails());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Logging>> getLogsByUser(@PathVariable String username) {
        return ResponseEntity.ok(loggingService.getLogsByUser(username));
    }

    @GetMapping("/period")
    public ResponseEntity<List<Logging>> getLogsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(loggingService.getLogsByPeriod(start, end));
    }

    @GetMapping("/user-period")
    public ResponseEntity<List<Logging>> getLogsByUserAndPeriod(
            @RequestParam String username,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(loggingService.getLogsByUserAndPeriod(username, start, end));
    }
}
