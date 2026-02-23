package com.haushekmiva.repository;

import com.haushekmiva.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void createUser(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }
    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserByLogin(String login) {
        return Optional.ofNullable(sessionFactory.getCurrentSession()
                .createQuery("FROM User u WHERE u.login = :login", User.class)
                .setParameter("login", login)
                .uniqueResult()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isUserExists(String login) {
        Long userCount = (sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(u) FROM User u WHERE u.login = :login", Long.class)
                .setParameter("login", login)
                .uniqueResult()
        );

        return userCount > 0;
    }
}
