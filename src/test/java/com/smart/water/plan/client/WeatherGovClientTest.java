package com.smart.water.plan.client;

import com.smart.water.plan.model.weather.forecast.ForecastResponse;
import com.smart.water.plan.model.weather.points.PointsResponse;
import com.smart.water.plan.model.weather.points.PointsResponseProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class WeatherGovClientTest {

    private WeatherGovClient weatherGovClient;
    private RestTemplate restTemplate;
    private String weatherApiUrl = "http://example.com";

    @BeforeEach
    public void setup() {
        restTemplate = Mockito.mock(RestTemplate.class);
        weatherGovClient = new WeatherGovClient(restTemplate);
    }

    @Test
    public void testGetForecastUrlByLatLon() {
        PointsResponse pointsResponse = new PointsResponse(new PointsResponseProperties());

        ResponseEntity<PointsResponse> responseEntity = new ResponseEntity<>(pointsResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(weatherApiUrl + "/points/1.0,1.0", PointsResponse.class)).thenReturn(responseEntity);

        String forecastUrl = weatherGovClient.getForecastUrlByLatLon(1.0, 1.0);

        assertEquals("http://example.com/forecast", forecastUrl);
    }

    @Test
    public void testGetSevenDayForecast() {
        ForecastResponse forecastResponse = new ForecastResponse();

        ResponseEntity<ForecastResponse> responseEntity = new ResponseEntity<>(forecastResponse, HttpStatus.OK);

        when(restTemplate.getForEntity("http://example.com/forecast", ForecastResponse.class)).thenReturn(responseEntity);

        ForecastResponse result = weatherGovClient.getSevenDayForecast("http://example.com/forecast");

        assertEquals(forecastResponse, result);
    }
}