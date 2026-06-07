package com.haushekmiva.dto;

public record LocationWeatherDto(
        int id,
        String name,
        String country,
        double temp,
        double feelsLike,
        int humidity,
        String description,
        String icon
) {
}
