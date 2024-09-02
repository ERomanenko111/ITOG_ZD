package com.example.User_server.service;

import com.example.User_server.model.Logging;
import com.example.User_server.model.Role;
import com.example.User_server.model.User;
import com.example.User_server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    public User save(User user) {
        return repository.save(user);
    }

    public User register(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }
        return save(user);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByUsername(username);
    }
    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }

    public List<Logging> getUserLogs(String username) {
        String url = "http://logging-service/logs/user/" + username;
        ResponseEntity<List<Logging>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Logging>>() {}
        );
        return response.getBody();
    }

    public List<Logging> getLogsByPeriod(LocalDateTime start, LocalDateTime end) {
        String url = String.format("http://logging-service/logs/period?start=%s&end=%s", start, end);

        ResponseEntity<List<Logging>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Logging>>() {}
        );

        return response.getBody();
    }

    public List<Logging> getLogsByUserAndPeriod(String username, LocalDateTime start, LocalDateTime end) {
        String url = String.format(
                "http://logging-service/logs/user-period?username=%s&start=%s&end=%s",
                username,
                start,
                end
        );

        ResponseEntity<List<Logging>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Logging>>() {}
        );

        return response.getBody();
    }
}
