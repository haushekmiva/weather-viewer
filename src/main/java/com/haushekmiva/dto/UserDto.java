package com.haushekmiva.dto;


import java.util.List;

public record UserDto(String login, List<LocationDto> locations) {
}
