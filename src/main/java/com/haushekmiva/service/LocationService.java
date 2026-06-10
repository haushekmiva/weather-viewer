package com.haushekmiva.service;

import com.haushekmiva.dto.FoundLocationDto;
import com.haushekmiva.dto.LocationWeatherDto;
import com.haushekmiva.dto.SearchedLocationDto;
import com.haushekmiva.dto.UserDto;

import java.util.List;

public interface LocationService {
    List<SearchedLocationDto> searchLocations(String name);
    void addLocation(FoundLocationDto location, UserDto user);
    void removeLocation(int locationId, UserDto user);
    List<LocationWeatherDto> getUserLocationsWeather(int userId);
}
