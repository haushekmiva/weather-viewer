package com.haushekmiva.interceptor;

import com.haushekmiva.service.AuthService;
import com.haushekmiva.utils.ValidUtils;
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
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws IOException {

        Cookie sessionCookie = WebUtils.getCookie(request, "session_id");

        if (sessionCookie == null) {
            response.sendRedirect(request.getContextPath() + "/sign-in");
            return false;
        }

        if(!ValidUtils.isUuidValid(sessionCookie.getValue())) {
            response.sendRedirect(request.getContextPath() + "/sign-in");
            return false;
        }

        UUID sessionId = UUID.fromString(sessionCookie.getValue());

        if (authService.getUser(sessionId).isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/sign-in");
            return false;
        }

        return true;
    }

}
