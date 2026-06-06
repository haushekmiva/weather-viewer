package com.haushekmiva.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FoundLocationDto(String name, BigDecimal lat, BigDecimal lon) {
}
