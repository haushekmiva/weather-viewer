package com.haushekmiva.dto;

import java.util.UUID;

public record AuthSuccess(UUID sessionId, int maxAge) implements AuthResponse {
}
