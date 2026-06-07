package com.haushekmiva.dto;

import java.util.List;

public record SearchResultDto(
        List<SearchedLocationDto> locations
) {
}
