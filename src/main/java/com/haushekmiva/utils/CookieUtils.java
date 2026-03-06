package com.haushekmiva.utils;

import jakarta.servlet.http.Cookie;

public final class CookieUtils {

    private CookieUtils() {}

    public static Cookie cookie(String name, String value, int maxAge, String path) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        cookie.setAttribute("SameSite", "Strict");
        cookie.setHttpOnly(true);
        return cookie;
    }
}
