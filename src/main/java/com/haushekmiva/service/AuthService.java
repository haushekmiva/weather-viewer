package com.haushekmiva.service;

import com.haushekmiva.dto.AuthResponse;
import com.haushekmiva.dto.UserDto;
import com.haushekmiva.dto.UserLoginRequest;
import com.haushekmiva.dto.UserRegisterRequest;

import java.util.Optional;
import java.util.UUID;

public interface AuthService {
    AuthResponse registerUser(UserRegisterRequest userRegisterRequest);
    AuthResponse loginUser(UserLoginRequest userLoginRequest);
    boolean logoutUser(UUID sessionId);
    Optional<UserDto> isUserAuthenticated(UUID sessionId);
}
