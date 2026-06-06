package com.haushekmiva.mapper;

import com.haushekmiva.dto.OpenWeatherDto;
import com.haushekmiva.dto.WeatherDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")

public interface WeatherMapper {

    @Mapping(source = "sysInfo.country", target = "country")
    @Mapping(source = "mainInfo.temp", target = "temp")
    @Mapping(source = "mainInfo.feelsLike", target = "feelsLike")
    @Mapping(source = "mainInfo.humidity", target = "humidity")
    @Mapping(source = "weather", target = "description", qualifiedByName = "firstWeatherDescription")
    @Mapping(source = "weather", target = "icon", qualifiedByName = "firstWeatherIcon")
    WeatherDto toDto(OpenWeatherDto openWeatherDto);

    @Named("firstWeatherDescription")
    default String extractFirstWeather(List<OpenWeatherDto.WeatherElement> weathers) {
        if (weathers == null || weathers.isEmpty()) {
            return null;
        }

        return weathers.getFirst().getDescription();
    }

    @Named("firstWeatherIcon")
    default String extractFirstIcon(List<OpenWeatherDto.WeatherElement> weathers) {
        if (weathers == null || weathers.isEmpty()) {
            return null;
        }

        return weathers.getFirst().getIcon();
    }


}
