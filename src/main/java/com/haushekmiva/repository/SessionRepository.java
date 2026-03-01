package com.haushekmiva.repository;

import com.haushekmiva.model.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository {
    void create(Session session);
    Optional<Session> getById(UUID id);
    void remove(UUID id);
}
