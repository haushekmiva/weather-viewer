package com.haushekmiva.service;

import com.haushekmiva.dto.FoundLocationDto;
import com.haushekmiva.dto.SearchedLocationDto;
import com.haushekmiva.dto.UserDto;
import com.haushekmiva.dto.WeatherDto;

import java.math.BigDecimal;
import java.util.List;

public interface LocationService {
    List<SearchedLocationDto> searchLocations(String name);
    void addLocation(String name, BigDecimal lat, BigDecimal lon, UserDto user);
    void removeLocation(int locationId, UserDto user);
    WeatherDto getLocationWeather(String name, BigDecimal lat, BigDecimal lon);
}
