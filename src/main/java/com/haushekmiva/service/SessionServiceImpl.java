package com.haushekmiva.service;

import com.haushekmiva.dto.SessionDto;
import com.haushekmiva.model.Session;
import com.haushekmiva.model.User;
import com.haushekmiva.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {


    public static final int DAYS_IN_WEEK = 7;
    public static final int SECONDS_IN_WEEK = 60 * 60 * 24 * 7;

    private final SessionRepository sessionRepository;

    @Override
    public SessionDto createSession(User user) {
        UUID sessionId = UUID.randomUUID();
        LocalDateTime expiresAt = LocalDateTime.now().plusDays(DAYS_IN_WEEK);
        sessionRepository.create(new Session(sessionId, expiresAt, user));
        return new SessionDto(sessionId, SECONDS_IN_WEEK);
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
