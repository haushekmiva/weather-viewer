package com.haushekmiva.dto;

import java.util.List;

public record IndexPageDto(
        List<LocationWeatherDto> locationWeathers
) {
}
