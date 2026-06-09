package com.haushekmiva.service;

import com.haushekmiva.dto.*;
import com.haushekmiva.mapper.LocationWeatherMapper;
import com.haushekmiva.model.Location;
import com.haushekmiva.repository.LocationRepository;
import com.haushekmiva.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final OpenWeatherApiService openWeatherApiService;

    private final LocationWeatherMapper locationWeatherMapper;

    @Override
    public List<SearchedLocationDto> searchLocations(String name) {
        return openWeatherApiService.getLocationByName(name);
    }

    @Override
    public void addLocation(FoundLocationDto location, UserDto user) {
        locationRepository.create(new Location(location.name(), location.lat(), location.lon(), userRepository.getReferenceById(user.id())));
    }

    @Override
    public void removeLocation(int locationId, UserDto user) {
        locationRepository.remove(locationId, user.id());
    }

    @Override
    public List<LocationWeatherDto> getUserLocationsWeather(int userId) {
        List<LocationWeatherDto> locationWeathers = new ArrayList<>();

        List<Location> locations = locationRepository.getUserLocations(userId);

        for (Location location : locations) {
            WeatherDto weather = openWeatherApiService.getWeatherByLocation(
                    new FoundLocationDto(location.getName(), location.getLatitude(), location.getLongitude())
            );

            locationWeathers.add(locationWeatherMapper.toDto(location.getName(), weather, location.getId()));
        }

        return locationWeathers;
    }
}
