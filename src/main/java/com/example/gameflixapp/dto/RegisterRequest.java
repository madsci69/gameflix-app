package com.example.gameflixapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;

public record RegisterRequest(

        @NotBlank
        @Size(max = 100)
        String firstName,
        @NotBlank
        @Size(max=100)
        String lastName,

        @NotBlank
        @Email
        @Size(max = 254)
        String email,

        @NotBlank
        @Size(max=40)
        String username,

        @NotBlank
        @Size(min = 8, max = 128)
        String password
) {
}
