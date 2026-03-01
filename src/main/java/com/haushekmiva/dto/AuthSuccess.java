package com.haushekmiva.dto;

import java.util.UUID;

public record AuthSuccess(UUID sessionId) implements AuthResponse {
}
