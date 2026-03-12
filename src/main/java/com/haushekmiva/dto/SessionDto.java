package com.haushekmiva.dto;

import java.util.UUID;

public record SessionDto(
        UUID sessionId,
        int maxAge
) {
}
