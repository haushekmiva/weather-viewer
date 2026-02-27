package com.haushekmiva.service;

import java.util.UUID;

public interface AuthService {
    AuthResponse registerUser(UserRegisterRequest userRegisterRequest);
    AuthResponse loginUser(UserLogin userAuthResponse);
    boolean logoutUser(UserLogoutRequest userLogoutRequest);
    boolean isUserAuthenticated(UUID sessionId);
}
