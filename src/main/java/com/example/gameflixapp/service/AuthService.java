package com.example.gameflixapp.service;

import com.example.gameflixapp.dto.AuthResponse;
import com.example.gameflixapp.dto.LoginRequest;
import com.example.gameflixapp.dto.RegisterRequest;
import com.example.gameflixapp.entity.User;
import com.example.gameflixapp.repository.UserRepository;
import com.example.gameflixapp.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.nio.channels.ReadPendingException;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Registration error. Please check your information and try again."
            );
        }

        String hashPass = passwordEncoder.encode(request.password());

        User user = new User (
            request.firstName(), request.lastName(), request.email(), request.username(), hashPass
        );

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid Email Or Password. "
                ));
        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid Email Or Password. "
            );
        }
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, "Bearer");

    }
}
