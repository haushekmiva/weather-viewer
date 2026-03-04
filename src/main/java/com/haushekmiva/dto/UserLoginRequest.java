package com.haushekmiva.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;


@Data
public class UserLoginRequest {

        @NotBlank
        private String username;

        @NotBlank
        @ToString.Exclude
        private String password;

}
