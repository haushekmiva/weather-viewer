package com.haushekmiva.repository;

import com.haushekmiva.model.User;

import java.util.Optional;

public interface UserRepository {
    void create(User user);
    Optional<User> getByLogin(String login);
}
