package com.smart.water.plan.client;

import org.springframework.stereotype.Service;

@Service
public class WeatherGovClient implements WeatherClient{

    @Override
    public String getWeatherForecast(String zipCode) {
        return "";
    }
}
