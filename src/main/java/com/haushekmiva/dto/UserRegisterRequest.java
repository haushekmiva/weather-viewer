package com.haushekmiva.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

        @NotBlank
        @Size(min = 4, max = 32, message = "Login must be between 4 and 32 characters.")
        private String username;

        @NotBlank
        @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters.")
        @ToString.Exclude
        private String password;

        @NotBlank
        @ToString.Exclude
        private String repeatPassword;

}