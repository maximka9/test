package com.company.test.controller;

import com.company.test.service.AuthService;
import com.company.test.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Регистрация нового пользователя
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestParam String email, @RequestParam String password, @RequestParam Role role) {
        return authService.register(email, password, role);
    }

    // Аутентификация и получение JWT токена
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        return authService.authenticate(email, password);
    }
}
