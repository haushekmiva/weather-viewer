package com.haushekmiva.repository;

import com.haushekmiva.model.Location;

public interface LocationRepository {
    void create(Location location);
    void remove(int id);
}
