package com.demo.messanger.controllers;

import com.demo.messanger.dto.UserDTO;
import com.demo.messanger.dto.request.LoginRequest;
import com.demo.messanger.dto.request.RegisterRequest;
import com.demo.messanger.dto.response.LoginResponse;
import com.demo.messanger.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterRequest request) {
        return authService.register(request.getUsername(), request.getEmail(), request.getPassword());
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request.getEmail(), request.getPassword());
    }
}
