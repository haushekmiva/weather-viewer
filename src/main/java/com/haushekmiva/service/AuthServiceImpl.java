package com.haushekmiva.service;

import com.haushekmiva.dto.*;
import com.haushekmiva.mapper.UserMapper;
import com.haushekmiva.model.Session;
import com.haushekmiva.model.User;
import com.haushekmiva.repository.SessionRepository;
import com.haushekmiva.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public static final int DAYS_IN_WEEK = 7;

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthResponse registerUser(UserRegisterRequest userRegisterRequest) {
        if (!Objects.equals(userRegisterRequest.password(), userRegisterRequest.repeatPassword())) {
            return new AuthError("Passwords do not match.");
        }

        String passwordHash = passwordEncoder.encode(userRegisterRequest.password());

        User user = new User(userRegisterRequest.username(), passwordHash);
        try {
            userRepository.create(user);
        } catch (DataIntegrityViolationException e) {
            return new AuthError("User with login %s already exists.".formatted(userRegisterRequest.username()));
        }

        UUID sessionId = UUID.randomUUID();
        sessionRepository.create(new Session(sessionId, LocalDateTime.now().plusDays(DAYS_IN_WEEK), user));

        return new AuthSuccess(sessionId);
    }

    @Override
    @Transactional
    public AuthResponse loginUser(UserLoginRequest userLoginRequest) {
        Optional<User> user = userRepository.getByLogin(userLoginRequest.login());

        if (user.isEmpty() || !passwordEncoder.matches(userLoginRequest.password(), user.get().getPassword())) {
            return new AuthError("Invalid username or password.");
        }

        UUID sessionId = UUID.randomUUID();
        sessionRepository.create(new Session(sessionId, LocalDateTime.now().plusDays(DAYS_IN_WEEK), user.get()));

        return new AuthSuccess(sessionId);
    }

    @Override
    public void logoutUser(UUID sessionId) {
        sessionRepository.remove(sessionId);
    }

    @Override
    public Optional<UserDto> isUserAuthenticated(UUID sessionId) {
        return Optional.empty();
    }
}
