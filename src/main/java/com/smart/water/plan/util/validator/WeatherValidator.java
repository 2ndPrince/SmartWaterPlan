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
}
