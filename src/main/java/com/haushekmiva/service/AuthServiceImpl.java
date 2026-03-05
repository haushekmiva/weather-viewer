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

    public static final int DAYS_IN_WEEK = 7;
    public static final int SECONDS_IN_WEEK = 60 * 60 * 24 * 7;

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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

        UUID sessionId = UUID.randomUUID();
        LocalDateTime expiresAt = LocalDateTime.now().plusDays(DAYS_IN_WEEK);
        sessionRepository.create(new Session(sessionId, expiresAt, user));

        return new AuthSuccess(sessionId, SECONDS_IN_WEEK);
    }

    @Override
    @Transactional
    public AuthResponse loginUser(UserLoginRequest userLoginRequest) {
        Optional<User> user = userRepository.getByLogin(userLoginRequest.getUsername());

        if (user.isEmpty() || !passwordEncoder.matches(userLoginRequest.getPassword(), user.get().getPassword())) {
            return new AuthError("Invalid username or password.");
        }

        UUID sessionId = UUID.randomUUID();
        sessionRepository.create(new Session(sessionId, LocalDateTime.now().plusDays(DAYS_IN_WEEK), user.get()));

        return new AuthSuccess(sessionId, SECONDS_IN_WEEK);
    }

    @Override
    public void logoutUser(UUID sessionId) {
        sessionRepository.remove(sessionId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserDto> getUserBySessionId(UUID sessionId) {
        Optional<Session> session = sessionRepository.getById(sessionId);

        if (session.isPresent() && session.get().getExpiresAt().isAfter(LocalDateTime.now())) {
            return Optional.of(userMapper.toDto(session.get().getUserId()));
        }

        return Optional.empty();
    }
}
