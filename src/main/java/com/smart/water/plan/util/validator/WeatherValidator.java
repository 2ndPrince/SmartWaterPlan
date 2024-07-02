package com.smart.water.plan.util.validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeatherValidator {

    public static void validateZipCode(String zipCode) {
        log.debug("Validating zip code: {}", zipCode);
        if (!zipCode.matches("\\d{5}")) {
            throw new IllegalArgumentException("Invalid zip code");
        }
    }

    public static void validateLatLon(double lat, double lon) {
        log.debug("Validating latitude and longitude: {}, {}", lat, lon);
        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
            throw new IllegalArgumentException("Invalid latitude or longitude");
        }
    }
}
