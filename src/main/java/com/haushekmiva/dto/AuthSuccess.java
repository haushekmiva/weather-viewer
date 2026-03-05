package com.haushekmiva.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuthSuccess(UUID sessionId, int maxAge) implements AuthResponse {
}
