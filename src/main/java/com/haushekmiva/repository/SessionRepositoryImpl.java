package com.haushekmiva.repository;

import com.haushekmiva.model.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class SessionRepositoryImpl implements SessionRepository {

    private final SessionFactory sessionFactory;

    public SessionRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Session session) {
        sessionFactory.getCurrentSession().persist(session);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Session> getById(UUID id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Session.class, id));
    }

    @Override
    public void delete(UUID id) {
        sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM Session s WHERE s.id = :id", Session.class)
                .setParameter("id", id)
                .executeUpdate();
    }
}
