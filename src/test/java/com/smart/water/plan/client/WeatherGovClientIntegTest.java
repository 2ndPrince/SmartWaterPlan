package com.smart.water.plan.client;

import com.smart.water.plan.model.weather.forecast.ForecastResponse;
import com.smart.water.plan.model.weather.forecast.ForecastResponsePeriod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class WeatherGovClientIntegTest {

    @Autowired
    private WeatherGovClient weatherGovClient = new WeatherGovClient(new RestTemplate());

    private static final String FORECAST_TEST_URL = "https://api.weather.gov/gridpoints/FFC/47,103/forecast";

    @Test
    public void testGetForecastUrlByLatLon() {
        // Given and When
        String forecastUrl = weatherGovClient.getForecastUrlByLatLon(34.1111, -84.4446);

        // Then
        assertEquals(FORECAST_TEST_URL, forecastUrl);
    }

    @Test
    public void testGetSevenDayForecast() {
        // Given and When
        ForecastResponse response = weatherGovClient.getSevenDayForecast(FORECAST_TEST_URL);
        List<ForecastResponsePeriod> sevenDayForecasts = response.getProperties().getPeriods();

        // Then
        assertEquals(14, sevenDayForecasts.size());
        assertTrue(sevenDayForecasts.get(0).getStartTime().startsWith(LocalDate.now().toString()));
    }
}