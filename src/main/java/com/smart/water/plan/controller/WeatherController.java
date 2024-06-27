package com.smart.water.plan.controller;

import com.smart.water.plan.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@AllArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/test")
    public String weather() {
        return weatherService.gerWeatherByZipCode("12345");
    }
}
