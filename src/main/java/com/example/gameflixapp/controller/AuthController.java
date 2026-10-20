package com.example.gameflixapp.controller;

import com.example.gameflixapp.dto.AuthResponse;
import com.example.gameflixapp.dto.LoginRequest;
import com.example.gameflixapp.dto.RegisterRequest;
import com.example.gameflixapp.dto.RegisterResponse;
import com.example.gameflixapp.service.AuthService;

import jakarta.validation.Valid;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register (
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterResponse("Registration successful", request.email()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
