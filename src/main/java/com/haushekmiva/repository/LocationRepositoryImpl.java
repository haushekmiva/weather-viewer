package com.haushekmiva.repository;

import com.haushekmiva.model.Location;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

    private final SessionFactory sessionFactory;

    @Override
    public void create(Location location) {
        sessionFactory.getCurrentSession().persist(location);
    }

    @Override
    public void remove(int id) {
        sessionFactory.getCurrentSession()
                .createMutationQuery("DELETE FROM Location l WHERE l.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
