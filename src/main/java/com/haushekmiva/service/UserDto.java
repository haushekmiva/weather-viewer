package com.haushekmiva.service;


import java.util.List;

public record UserDto(String login, List<LocationDto> locations) {
}
