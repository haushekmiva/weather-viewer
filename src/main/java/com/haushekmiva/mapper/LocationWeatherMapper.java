package com.haushekmiva.mapper;

import com.haushekmiva.dto.LocationWeatherDto;
import com.haushekmiva.dto.WeatherDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",  injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LocationWeatherMapper {

    LocationWeatherDto toDto(WeatherDto weatherDto, int id);

}
