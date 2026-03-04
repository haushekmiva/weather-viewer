package com.haushekmiva.interceptors;


import com.haushekmiva.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws IOException {

        Cookie sessionCookie = WebUtils.getCookie(request, "session_id");

        if (sessionCookie == null) {
            return true;
        }

        UUID sessionId = UUID.fromString(sessionCookie.getValue());

        if (authService.getUserBySessionId(sessionId).isEmpty()) {
            return true;
        }

        response.sendRedirect(request.getContextPath() + "/");
        return false;
    }

}
