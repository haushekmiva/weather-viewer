package com.haushekmiva.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchedLocationDto(
    String name,
    BigDecimal lat,
    BigDecimal lon,
    String country,
    String state
) {
}
