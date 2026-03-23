package com.haushekmiva.service;

import com.haushekmiva.dto.LocationDto;
import com.haushekmiva.dto.WeatherDto;

public interface OpenWeatherApiService {
    LocationDto getLocationByName(String name);
    WeatherDto getWeatherByLocation(LocationDto location);
}
