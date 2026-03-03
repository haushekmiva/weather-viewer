package com.haushekmiva.advice;

import com.haushekmiva.dto.UserDto;
import com.haushekmiva.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.UUID;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAdvice {

    private final AuthService authService;

    @ModelAttribute("user")
    public UserDto user(@CookieValue(name = "session_id", required = false) UUID sessionId) {
        if (sessionId == null) {
            return null;
        }

        return authService.getUserBySessionId(sessionId).orElse(null);
    }

}
