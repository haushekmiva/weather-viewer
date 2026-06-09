package com.haushekmiva.dto;

public record WeatherDto(
        String country,
        double temp,
        double feelsLike,
        int humidity,
        String description,
        String icon
) {
}
