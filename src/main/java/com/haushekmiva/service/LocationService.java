package com.haushekmiva.service;

import com.haushekmiva.dto.*;

import java.math.BigDecimal;
import java.util.List;

public interface LocationService {
    List<SearchedLocationDto> searchLocations(String name);
    void addLocation(FoundLocationDto location, UserDto user);
    void removeLocation(int locationId, UserDto user);
    List<LocationWeatherDto> getUserLocationsWeather(int userId);
}
