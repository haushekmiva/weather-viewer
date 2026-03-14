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
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;

    @Override
    @Transactional
    public AuthResponse registerUser(UserRegisterRequest userRegisterRequest) {
        if (!Objects.equals(userRegisterRequest.getPassword(), userRegisterRequest.getRepeatPassword())) {
            return new AuthError("Passwords do not match.");
        }

        String passwordHash = passwordEncoder.encode(userRegisterRequest.getPassword());

        User user = new User(userRegisterRequest.getUsername(), passwordHash);
        try {
            userRepository.create(user);
        } catch (DataIntegrityViolationException e) {
            return new AuthError("User with login %s already exists.".formatted(userRegisterRequest.getUsername()));
        }

        SessionDto session = sessionService.createSession(user);

        return new AuthSuccess(session.sessionId(), session.maxAge());
    }

    @Override
    @Transactional
    public AuthResponse loginUser(UserLoginRequest userLoginRequest) {
        Optional<User> user = userRepository.getByLogin(userLoginRequest.getUsername());

        if (user.isEmpty() || !passwordEncoder.matches(userLoginRequest.getPassword(), user.get().getPassword())) {
            return new AuthError("Invalid username or password.");
        }

        SessionDto session = sessionService.createSession(user.get());

        return new AuthSuccess(session.sessionId(), session.maxAge());
    }

    @Override
    public void logoutUser(UUID sessionId) {
        sessionService.removeSession(sessionId);
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<UserDto> getUser(UUID sessionId) {
        Optional<User> user = sessionService.getUserBySessionId(sessionId);

        if (user.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(userMapper.toDto(user.get()));

    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserDtoWithLocations> getUserWithLocations(UUID sessionId) {
        Optional<User> user = sessionService.getUserBySessionId(sessionId);

        if (user.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(userMapper.toDtoWithLocations(user.get()));

    }
}
