package com.haushekmiva.repository;

import com.haushekmiva.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Override
    public void create(User user) {
            sessionFactory.getCurrentSession().persist(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getByLogin(String login) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User u WHERE u.login = :login", User.class)
                .setParameter("login", login)
                .uniqueResultOptional();
    }
}
