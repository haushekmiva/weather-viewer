package com.haushekmiva.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
        @NotBlank
        String login,

        @NotBlank
        String password
) {
}
