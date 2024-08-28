package com.example.User_server.service;

import com.example.User_server.model.User;
import com.example.User_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        // хэширование пароля перед сохранением пользователя
        String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public String login(String fullName, String username, String password) {
        return username;
    }

    public User updateUser(Long id, User user) {
        // логика для обновления пользователя
        return user;
    }
}