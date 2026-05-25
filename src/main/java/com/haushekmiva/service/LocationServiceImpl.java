package com.haushekmiva.service;

import com.haushekmiva.dto.LocationDto;
import com.haushekmiva.dto.UserDto;
import com.haushekmiva.dto.WeatherDto;
import com.haushekmiva.model.Location;
import com.haushekmiva.repository.LocationRepository;
import com.haushekmiva.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final OpenWeatherApiService openWeatherApiService;

    @Override
    public List<LocationDto> searchLocations(String name) {
        return openWeatherApiService.getLocationByName(name);
    }

    @Override
    public void addLocation(String name, BigDecimal lat, BigDecimal lon, UserDto user) {
        locationRepository.create(new Location(name, lat, lon, userRepository.getReferenceById(user.id())));
    }

    @Override
    public void removeLocation(int locationId, UserDto user) {
        locationRepository.remove(locationId, user.id());
    }

    @Override
    public WeatherDto getLocationWeather(String name, BigDecimal lat, BigDecimal lon) {
        return openWeatherApiService.getWeatherByLocation(new LocationDto(name, lat, lon));
    }
}
