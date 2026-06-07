package com.haushekmiva.repository;

import com.haushekmiva.dto.FoundLocationDto;
import com.haushekmiva.model.Location;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void remove(int locationId, int userId) {
        sessionFactory.getCurrentSession()
                .createMutationQuery("DELETE FROM Location l WHERE l.id = :locationId AND l.user.id = :userId")
                .setParameter("locationId", locationId)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public List<Location> getUserLocations(int userId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Location l WHERE l.user.id = :userId", Location.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
