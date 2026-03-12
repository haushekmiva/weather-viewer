package com.haushekmiva.service;

import com.haushekmiva.dto.SessionDto;
import com.haushekmiva.model.Session;
import com.haushekmiva.model.User;
import com.haushekmiva.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    @Value("${session.max_age_seconds}")
    private int sessionMaxAge;

    private final SessionRepository sessionRepository;

    @Override
    public SessionDto createSession(User user) {
        UUID sessionId = UUID.randomUUID();
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(sessionMaxAge);
        sessionRepository.create(new Session(sessionId, expiresAt, user));
        return new SessionDto(sessionId, sessionMaxAge);
    }

    @Override
    public void removeSession(UUID sessionId) {
        sessionRepository.remove(sessionId);
    }

    @Override
    public Optional<User> getUserBySessionId(UUID sessionId) {
        Optional<Session> session = sessionRepository.getById(sessionId);
        if (session.isPresent() && isSessionActive(session.get())) {
            return Optional.of(session.get().getUser());
        }

        return Optional.empty();
    }

    @Override
    public void removeExpiredSessions() {
        sessionRepository.removeExpiredSessions();
    }

    private boolean isSessionActive(Session session) {
        return session.getExpiresAt().isAfter(LocalDateTime.now());
    }
}
