package com.haushekmiva.service;

import com.haushekmiva.dto.*;

import java.util.Optional;
import java.util.UUID;

public interface AuthService {
    AuthResponse registerUser(UserRegisterRequest userRegisterRequest);
    AuthResponse loginUser(UserLoginRequest userLoginRequest);
    void logoutUser(UUID sessionId);
    Optional<UserDto> getUser(UUID sessionId);
    Optional<UserDtoWithLocations> getUserWithLocations(UUID sessionId);
}
