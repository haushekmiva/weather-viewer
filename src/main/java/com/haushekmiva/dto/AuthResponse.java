package com.haushekmiva.dto;

public sealed interface AuthResponse permits AuthSuccess, AuthError {
}