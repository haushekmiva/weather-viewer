package com.haushekmiva.repository;

import com.haushekmiva.exception.custom.DataBaseException;
import com.haushekmiva.exception.custom.DuplicateEntryException;
import com.haushekmiva.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
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
        try {
            sessionFactory.getCurrentSession().persist(user);
        } catch (ConstraintViolationException e) {
            throw new DuplicateEntryException("Constraint violation on insert: duplicate login '%s'"
                    .formatted(user.getLogin()), e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUserByLogin(String login) {
        try {
            return sessionFactory.getCurrentSession()
                    .createQuery("FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResultOptional();
        } catch (HibernateException e) {
            throw new DataBaseException("An error occurred while getting user '%s'.".formatted(login), e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isUserExists(String login) {
        try {
            Long userCount = (sessionFactory.getCurrentSession()
                    .createQuery("SELECT COUNT(u) FROM User u WHERE u.login = :login", Long.class)
                    .setParameter("login", login)
                    .uniqueResult()
            );

            return userCount > 0;
        } catch (HibernateException e) {
            throw new DataBaseException("An error occurred while checking if the user '%s' exists.".formatted(login),
                    e);
        }
    }
}
