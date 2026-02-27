package com.haushekmiva.dto;

public record UserRegisterRequest(String username, String password, String repeatPassword) {
}
