package com.smart.water.plan.service;

import com.smart.water.plan.client.WeatherClient;
import com.smart.water.plan.util.validator.WeatherValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;

    public String gerWeatherByZipCode(String zipCode) {
        WeatherValidator.validateZipCode(zipCode);
        return weatherClient.getWeatherForecast(zipCode);
    }
}
