package com.haushekmiva.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(

        @NotBlank
        @Size(min = 4, max = 32, message = "Login must be between 4 and 32 characters.")
        String username,

        @NotBlank
        @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters.")
        String password,

        @NotBlank
        String repeatPassword
) {
}