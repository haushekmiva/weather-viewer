package com.haushekmiva.service;

import com.haushekmiva.dto.AuthResponse;

import java.util.UUID;

public interface AuthService {
    AuthResponse registerUser(UserRegisterRequest userRegisterRequest);
    AuthResponse loginUser(UserLoginRequest userAuthResponse);
    boolean logoutUser(UserLogoutRequest userLogoutRequest);
    boolean isUserAuthenticated(UUID sessionId);
}
