package com.haushekmiva.repository;

import com.haushekmiva.model.User;

import java.util.Optional;

public interface UserRepository {
    void createUser(User user);
    Optional<User> getUserByLogin(String login);
    boolean isUserExists(String login);
}
