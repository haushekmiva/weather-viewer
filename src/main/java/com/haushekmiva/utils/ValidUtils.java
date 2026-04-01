package com.haushekmiva.utils;


import java.util.UUID;

public final class ValidUtils {

    private ValidUtils() {}

    public static boolean isUuidValid(String uuid) {
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    public static boolean isStringEmpty(String s) {
        return s == null || s.isBlank();
    }

}
