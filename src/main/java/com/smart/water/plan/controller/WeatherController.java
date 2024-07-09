package com.smart.water.plan.controller;

import com.smart.water.plan.service.WeatherGeoService;
import com.smart.water.plan.service.WeatherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/weather")
@AllArgsConstructor
@Slf4j
public class WeatherController {

    private final WeatherService weatherService;
    private final WeatherGeoService geoService;

    @GetMapping("/test")
    public String test() {
        return geoService.gerWeatherByZipCode("12345");
    }

    @GetMapping("/forecast")
    public String forecast(@RequestParam(value = "lat") double lat, @RequestParam(value = "lon") double lon) {
        log.info("Received request for forecast with latitude: {} and longitude: {}", lat, lon);
        return geoService.getForecastUrl(lat, lon);
    }

    @GetMapping("/water-array")
    public List<Integer> watering(@RequestParam(value = "lat") double lat, @RequestParam(value = "lon") double lon,
                                  @RequestParam(value = "daysWithoutRain") int daysWithoutRain,
                                  @RequestParam(value = "userId", required = true) UUID userId) {
        log.info("Received request for water array with latitude: {}, longitude: {}, days without rain: {}, and user id: {}", lat, lon, daysWithoutRain, userId);
        List<Integer> weatherArray = weatherService.getWeatherArray(lat, lon, daysWithoutRain, userId);
        log.info("Returning water array: {}", weatherArray);
        return weatherArray;
    }
}
