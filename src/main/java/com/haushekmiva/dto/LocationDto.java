package com.haushekmiva.dto;

import java.math.BigDecimal;

public record LocationDto(String name, BigDecimal latitude, BigDecimal longitude) {
}
