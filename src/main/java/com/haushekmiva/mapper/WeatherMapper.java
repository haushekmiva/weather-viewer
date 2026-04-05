package com.haushekmiva.mapper;

import com.haushekmiva.dto.OpenWeatherDto;
import com.haushekmiva.dto.WeatherDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface WeatherMapper {

    @Mapping(source = "sysInfo.country", target = "country")
    @Mapping(source = "mainInfo.temp", target = "temp")
    @Mapping(source = "mainInfo.feelsLike", target = "feelsLike")
    @Mapping(source = "mainInfo.humidity", target = "humidity")
    WeatherDto toDto(OpenWeatherDto openWeatherDto);
}
