package com.haushekmiva.dto;

import java.util.UUID;

public record AuthSuccess(String login, UUID sessionId) implements AuthResponse {
}
