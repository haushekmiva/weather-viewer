package com.haushekmiva.repository;

import com.haushekmiva.model.Session;

import java.util.UUID;

public interface SessionRepository {
    void create(Session session);
    Session getById(UUID id);
    void delete(UUID id);
}
