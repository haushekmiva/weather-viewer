package com.haushekmiva.repository;

import com.haushekmiva.dto.FoundLocationDto;
import com.haushekmiva.model.Location;

import java.util.List;

public interface LocationRepository {
    void create(Location location);
    void remove(int locationId, int userId);
    List<Location> getUserLocations(int id);
}
