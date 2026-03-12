package com.haushekmiva.service;

import com.haushekmiva.dto.SessionDto;
import com.haushekmiva.model.User;

import java.util.Optional;
import java.util.UUID;

public interface SessionService {
    SessionDto createSession(User user);
    void removeSession(UUID sessionId);
    Optional<User> getUserBySessionId(UUID sessionId);
    void removeExpiredSessions();
}
