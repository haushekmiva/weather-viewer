package com.haushekmiva.dto;

public record WeatherDto(
        String name,
        String country,
        double temp,
        double feelsLike,
        int humidity
) {
}
