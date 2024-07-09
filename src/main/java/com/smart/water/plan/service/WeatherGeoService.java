package com.smart.water.plan.service;

import com.smart.water.plan.client.WeatherGovClient;
import com.smart.water.plan.model.weather.forecast.ForecastResponsePeriod;
import com.smart.water.plan.util.validator.WeatherValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WeatherGeoService {

    private final WeatherGovClient weatherClient;

    public List<ForecastResponsePeriod> getForecastPeriods(double lat, double lon) {
        String forecastUrl = getForecastUrl(lat, lon);
        return weatherClient.getSevenDayForecast(forecastUrl).getProperties().getPeriods();
    }

    public String getForecastUrl(double lat, double lon) {
        WeatherValidator.validateLatLon(lat, lon);
        return weatherClient.getForecastUrlByLatLon(lat, lon);
    }

    // TODO: Use GeoCoding to support address translation to LatLon
    public String gerWeatherByZipCode(String zipCode) {
        WeatherValidator.validateZipCode(zipCode);
        return weatherClient.getWeatherForecast(zipCode);
    }
}
