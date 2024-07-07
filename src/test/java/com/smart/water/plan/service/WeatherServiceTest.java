package com.smart.water.plan.service;

import com.smart.water.plan.client.WeatherGovClient;
import com.smart.water.plan.model.weather.forecast.ForecastResponse;
import com.smart.water.plan.model.weather.forecast.ForecastResponsePeriod;
import com.smart.water.plan.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.smart.water.plan.util.JsonUtil.readForecastResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherGovClient weatherGovClient;

    @Mock
    private WaterPlanner waterPlanner;

    @Mock
    private UserService userService;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    public void setup() {
        // Example of configuring mock behavior
        // Mockito.when(weatherGovClient.getWeatherForecast(anyString())).thenReturn("Sunny");
    }

    @Test
    public void testGeneratePastWeatherArray() {
        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0, 1), weatherService.generatePastWeatherArray(0));
        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 1, 0), weatherService.generatePastWeatherArray(1));
        assertEquals(Arrays.asList(0, 0, 0, 0, 1, 0, 0), weatherService.generatePastWeatherArray(2));
        assertEquals(Arrays.asList(0, 0, 0, 1, 0, 0, 0), weatherService.generatePastWeatherArray(3));
        assertEquals(Arrays.asList(0, 0, 1, 0, 0, 0, 0), weatherService.generatePastWeatherArray(4));
        assertEquals(Arrays.asList(0, 1, 0, 0, 0, 0, 0), weatherService.generatePastWeatherArray(5));
        assertEquals(Arrays.asList(1, 0, 0, 0, 0, 0, 0), weatherService.generatePastWeatherArray(6));
    }

    @Test
    public void testExtractProbabilityValues() throws IOException {
        // Given
        ForecastResponse forecastResponse = readForecastResponse("src/test/resources/example-forecast-response.json");
        List<ForecastResponsePeriod> periods = forecastResponse.getProperties().getPeriods();

        // When
        List<Integer> integers = weatherService.extractProbabilityValues(periods);
        assertEquals(Arrays.asList(0, 50, 50, 70, 70, 60, 60, 60, 60, 40, 40, 50, 50, 60), integers);
    }

    @Test
    public void testApplyThreshold() {
        List<Integer> integers = weatherService.applyThreshold(
                Arrays.asList(0, 50, 50, 70, 70, 60, 60, 60, 60, 40, 40, 50, 50, 60), 50);
        assertEquals(
                Arrays.asList(0,  1,  1,  1,  1,  1,  1,  1,  1,  0,  0,  1,  1,  1), integers);
    }

    @Test
    public void testCompressForecastArray() {
        List<Integer> integers = weatherService.compressForecastArray(
                Arrays.asList(0,  1,  1,  1,  1,  1,  1,  1,  1,  0,  0,  0,  1,  1));
        assertEquals(
                Arrays.asList(    1,      1,      1,      1,      1,      0,      1), integers);

    }
}