package com.haushekmiva.utils;


import java.math.BigDecimal;
import java.util.UUID;

public final class ValidUtils {

    private ValidUtils() {
    }

    public static boolean isUuidValid(String uuid) {
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    public static boolean isCoordinatesValid(BigDecimal lat, BigDecimal lon) {
        if (lat == null || lon == null) return false;

        return  (lat.compareTo(BigDecimal.valueOf(-90)) >= 0 && lat.compareTo(BigDecimal.valueOf(90)) <= 0
                && lon.compareTo(BigDecimal.valueOf(-180)) >= 0 && lon.compareTo(BigDecimal.valueOf(180)) <= 0);
    }

    public static boolean isStringEmpty(String s) {
        return s == null || s.isBlank();
    }

}
