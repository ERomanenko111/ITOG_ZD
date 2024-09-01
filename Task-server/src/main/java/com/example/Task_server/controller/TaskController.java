package com.example.Task_server.controller;

import com.example.Task_server.model.Task;
import com.example.Task_server.repository.TaskRepository;
import com.example.User_server.Token.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    @PreAuthorize("isAuthenticated()") // Требуется аутентификация
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization") String token) {
        String tokenWithoutBearer = token.startsWith("Bearer ") ? token.substring(7) : token;

        String username = jwtUtil.extractUsername(tokenWithoutBearer);
        if (username == null || !jwtUtil.validateToken(tokenWithoutBearer, username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            Task savedTask = taskRepository.save(task);
            return ResponseEntity.ok(savedTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            Task updatedTask = existingTask.get();
            updatedTask.setText(task.getText());
            updatedTask.setDueDate(task.getDueDate());
            updatedTask.setStatus(task.getStatus());
            return ResponseEntity.ok(taskRepository.save(updatedTask));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(params = {"status", "from", "to"})
    public ResponseEntity<List<Task>> getTasksByStatusAndDate(
            @RequestParam String status,
            @RequestParam String from,
            @RequestParam String to) {
        LocalDateTime fromDateTime = LocalDateTime.parse(from);
        LocalDateTime toDateTime = LocalDateTime.parse(to);
        List<Task> tasks = taskRepository.findByStatusAndDueDateBetween(status, fromDateTime, toDateTime);
        return ResponseEntity.ok(tasks);
    }
}