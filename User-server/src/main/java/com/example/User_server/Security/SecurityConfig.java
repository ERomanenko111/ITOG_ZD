package com.example.User_server.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable() // отключаем CSRF защиту
                .authorizeHttpRequests() // заменяем authorizeRequests на authorizeHttpRequests
                .requestMatchers("/users/register", "/users/login").permitAll() // здесь заменяем mvcMatchers на requestMatchers
                .anyRequest().authenticated()
                .and()
                .build();
    }
}
