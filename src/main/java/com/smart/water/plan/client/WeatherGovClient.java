package com.smart.water.plan.client;

import com.smart.water.plan.model.weather.forecast.ForecastResponse;
import com.smart.water.plan.model.weather.points.PointsResponse;
import com.smart.water.plan.util.validator.ResponseEntityValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
public class WeatherGovClient {

    @Value("${weather.api.url}")
    private String weatherApiUrl;
    private static final String POINT_CONTEXT = "/points/";

    private final RestTemplate restTemplate;

    public WeatherGovClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getWeatherForecast(String zipCode) {
        return "";
    }

    public String getForecastUrlByLatLon(double lat, double lon) {
        String requestUrl = weatherApiUrl + POINT_CONTEXT + lat + "," + lon;

        log.info("Requesting weather forecast from: {}", requestUrl);
        ResponseEntity<PointsResponse> responseEntity
                = restTemplate.getForEntity(requestUrl, PointsResponse.class);
        ResponseEntityValidator.validateResponse(responseEntity);
        String forecastUrl = Objects.requireNonNull(responseEntity.getBody()).getProperties().getForecast();
        log.info("Received forecast URL: {}", forecastUrl);
        return forecastUrl;
    }

    public ForecastResponse getSevenDayForecast(String forecastUrl) {
        ResponseEntity<ForecastResponse> responseEntity = restTemplate.getForEntity(forecastUrl, ForecastResponse.class);
        ResponseEntityValidator.validateResponse(responseEntity);
        ForecastResponse forecastResponse = Objects.requireNonNull(responseEntity.getBody());
        log.info("Received forecast response: {}", forecastResponse);
        return forecastResponse;
    }
}
