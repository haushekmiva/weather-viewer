package com.haushekmiva.service;

import com.haushekmiva.dto.FoundLocationDto;
import com.haushekmiva.dto.SearchedLocationDto;
import com.haushekmiva.dto.WeatherDto;

import java.util.List;

public interface OpenWeatherApiService {
    List<SearchedLocationDto> getLocationByName(String name);
    WeatherDto getWeatherByLocation(FoundLocationDto location);
}
