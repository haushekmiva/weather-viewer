package com.haushekmiva.dto;

import java.util.List;

public record UserDtoWithLocations(String login, List<LocationDto> locations) {
}
