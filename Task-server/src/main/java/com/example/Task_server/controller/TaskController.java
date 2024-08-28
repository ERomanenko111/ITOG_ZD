package com.example.Task_server.controller;

import com.example.Task_server.model.Task;
import com.example.Task_server.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        // logic to create a task
        return null;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        // logic to get all tasks
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task task) {
        // logic to update task
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        // logic to delete task
        return null;
    }

    @GetMapping(params = {"status", "from", "to"})
    public ResponseEntity<List<Task>> getTasksByStatusAndDate(
            @RequestParam String status,
            @RequestParam String from,
            @RequestParam String to) {
        // logic to get tasks by status and date range
        return null;
    }
}