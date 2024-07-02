package com.smart.water.plan.controller;

import com.smart.water.plan.service.WeatherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/weather")
@AllArgsConstructor
@Slf4j
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/test")
    public String test() {
        return weatherService.gerWeatherByZipCode("12345");
    }

    @GetMapping("/forecast")
    public String forecast(@RequestParam(value = "lat") double lat, @RequestParam(value = "lon") double lon) {
        log.info("Received request for forecast with latitude: {} and longitude: {}", lat, lon);
        return weatherService.getForecastUrl(lat, lon);
    }

    @GetMapping("/watering")
    public int watering(@RequestParam(value = "lat") double lat, @RequestParam(value = "lon") double lon,
                        @RequestParam(value = "daysWithoutRain") int daysWithoutRain,
                        @RequestParam(value = "userId", required = false) UUID userId) {
        log.info("Received request for watering with latitude: {}, longitude: {} and days without rain: {}", lat, lon, daysWithoutRain);
        return weatherService.getWateringPeriods(lat, lon, daysWithoutRain, userId);
    }

    @GetMapping("/firstRainingDay")
    public int firstRainingDay(@RequestParam(value = "lat") double lat, @RequestParam(value = "lon") double lon,
                               @RequestParam(value = "threshold", required = false, defaultValue = "60") int threshold) {
        log.info("Received request for first raining day with latitude: {} and longitude: {}", lat, lon);
        return weatherService.getFirstRainingDay(lat, lon, threshold);
    }
}
