
package com.example.User_server.service;

import com.example.User_server.model.User;
import com.example.User_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.User_server.Token.JwtUtil;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        logger.info("Attempting login for username: {}", username);
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                logger.info("Login successful for username: {}", username);
                return jwtUtil.generateToken(user.getId(), username);
            } else {
                logger.warn("Invalid password for username: {}", username);
            }
        } else {
            logger.warn("User not found: {}", username);
        }

        throw new RuntimeException("Invalid credentials");
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Хэшируем пароль перед сохранением
        return userRepository.save(user);
    }
    public User updateUser(Long id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFullName(user.getFullName());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword())); // если пароль обновляется
            existingUser.setRole(user.getRole());
            return userRepository.save(existingUser);
        }).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
