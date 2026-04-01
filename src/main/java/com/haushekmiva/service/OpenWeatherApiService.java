package com.haushekmiva.service;

import com.haushekmiva.dto.LocationDto;
import com.haushekmiva.dto.WeatherDto;

import java.util.List;

public interface OpenWeatherApiService {
    List<LocationDto> getLocationByName(String name);
    WeatherDto getWeatherByLocation(LocationDto location);
}
