package com.example.Task_server.service;

import com.example.Task_server.model.LogRequest;
import com.example.Task_server.model.Status;
import com.example.Task_server.model.Task;
import com.example.Task_server.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    private static final String LOG_SERVICE_URL =  "http://logging-service/logs";

    private void logAction(String username, String action, String details) {
        LogRequest logEntry = new LogRequest(username, LocalDateTime.now(), action, details);
        restTemplate.postForObject(LOG_SERVICE_URL, logEntry, Void.class);
    }

    public Task createTask(Task task, String authToken) throws Exception {
        String token = authToken.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);

        if (username.equals(userService.getUserData(username, token).getUsername())){
            task.setCreationTime(LocalDateTime.now());
            task.setStatus(Status.IN_PROGRESS);
            task.setUsername(username);
            Task createdTask = taskRepository.save(task);

            logAction(username, "CREATE_TASK", "Created task with ID:" + createdTask.getId());

            return createdTask;
        } else {
            throw new Exception("User not found or token invalid");
        }
    }

    public Optional<Task> updateTask(Long id, Task task, String authToken) throws Exception  {
        String token = authToken.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);

        if (username.equals(userService.getUserData(username, token).getUsername())){
        return taskRepository.findById(id).map(existingTask -> {
            if (task.getText() != null) {
                existingTask.setText(task.getText());
            }
            if (task.getStatus() != null) {
                if (existingTask.getStatus() == Status.IN_PROGRESS && task.getStatus() == Status.COMPLETED) {
                    existingTask.setCompletionTime(LocalDateTime.now());
                }
                existingTask.setStatus(task.getStatus());
            }
            Task updatedTask = taskRepository.save(existingTask);

            logAction(username, "UPDATE_TASK", "Updated task with ID: " + updatedTask.getId());

            return updatedTask;
        });
        } else {
            throw new Exception("User not found or token invalid");
        }
    }

    public void deleteTask(Long id, String authToken) throws Exception {
        Optional<Task> task = taskRepository.findById(id);
        String token = authToken.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);

        if (username.equals(userService.getUserData(username, token).getUsername())){
            task.ifPresent(t -> {
                taskRepository.deleteById(id);
            logAction(t.getUsername(), "DELETE_TASK", "Deleted task with ID: " + id);});
        } else {
            throw new Exception("User not found or token invalid");
        }
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByPeriod(LocalDateTime start, LocalDateTime end) {
        return taskRepository.findByPeriod(Status.IN_PROGRESS, start, end);
    }

    public List<Task> getActiveTasks() {
        return taskRepository.findByStatus(Status.IN_PROGRESS);
    }
//    public User getUserById(Long userId) {
//        return restTemplate.getForObject()
//    }
}
