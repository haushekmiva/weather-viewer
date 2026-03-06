package com.haushekmiva.resolver;

import com.haushekmiva.annotation.CurrentUser;
import com.haushekmiva.dto.UserDto;
import com.haushekmiva.dto.UserDtoWithLocations;
import com.haushekmiva.service.AuthService;
import com.haushekmiva.utils.ValidUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.WebUtils;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Nullable
    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  @Nullable ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest,
                                  @Nullable WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Cookie sessionCookie = WebUtils.getCookie(request, "session_id");

        if (sessionCookie == null) {
            return null;
        }

        if (!ValidUtils.isUuidValid(sessionCookie.getValue())) {
            return null;
        }

        UUID sessionId = UUID.fromString(sessionCookie.getValue());

        if (parameter.getParameterType().equals(UserDto.class)) {
            return authService.getUser(sessionId).orElse(null);
        }

        if (parameter.getParameterType().equals(UserDtoWithLocations.class)) {
            return authService.getUserWithLocations(sessionId).orElse(null);
        }

        return null;
    }
}
