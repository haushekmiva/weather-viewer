package com.haushekmiva.service;

import com.haushekmiva.dto.AuthResponse;
import com.haushekmiva.dto.UserLoginRequest;
import com.haushekmiva.dto.UserRegisterRequest;

import java.util.UUID;

public interface AuthService {
    AuthResponse registerUser(UserRegisterRequest userRegisterRequest);
    AuthResponse loginUser(UserLoginRequest userAuthResponse);
    boolean logoutUser(UUID sessionId);
    boolean isUserAuthenticated(UUID sessionId);
}
