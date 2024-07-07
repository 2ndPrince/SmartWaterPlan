package com.smart.water.plan.service;

import com.smart.water.plan.model.weather.forecast.ForecastResponsePeriod;
import com.smart.water.plan.model.weather.forecast.ProbabilityOfPrecipitation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WaterPlannerTest {

    private WaterPlanner waterPlanner;

    @BeforeEach
    public void setup() {
        waterPlanner = new WaterPlanner();
    }

    @Test
    public void givenImmediateNeedAndRainAboveThreshold_whenPlanningToWater_thenShouldWaterToday() {
        // Given
        List<ForecastResponsePeriod> forecastPeriods = setupForecastPeriods(70, 70);

        // When
        int result = waterPlanner.planWhenToWater(forecastPeriods, WateringUrgency.IMMEDIATE, 60);

        // Then
        assertEquals(0, result, "Should water today due to immediate need and rain above threshold.");
    }

    @Test
    public void givenUrgentNeedAndRainBelowThreshold_whenPlanningToWater_thenShouldWaterTomorrow() {
        // Given
        List<ForecastResponsePeriod> forecastPeriods = setupForecastPeriods(30, 30);

        // When
        int result = waterPlanner.planWhenToWater(forecastPeriods, WateringUrgency.URGENT, 40);

        // Then
        assertEquals(2, result, "Should water tomorrow due to urgent need and rain below threshold.");
    }

    @Test
    public void givenOnScheduleNeedAndRainExpectedToday_whenPlanningToWater_thenShouldWaterInTwoDays() {
        // Given
        List<ForecastResponsePeriod> forecastPeriods = setupForecastPeriods(70, 0);

        // When
        int result = waterPlanner.planWhenToWater(forecastPeriods, WateringUrgency.ON_SCHEDULE, 60);

        // Then
        assertEquals(4, result, "Should water in two days due to on-schedule need and rain expected today.");
    }

    public void givenNoNeedToWaterAndRainExpectedAllDays_whenPlanningToWater_thenNoNeedToWaterInTheNextThreeDays() {
        // Given
        List<ForecastResponsePeriod> forecastPeriods = setupForecastPeriods(70, 70, 70);

        // When
        int result = waterPlanner.planWhenToWater(forecastPeriods, WateringUrgency.NO_NEED_TO_WATER, 60);

        // Then
        assertEquals(6, result, "No need to water in the next three days due to rain expected all days.");
    }

    private List<ForecastResponsePeriod> setupForecastPeriods(int... rainProbabilities) {
        List<ForecastResponsePeriod> forecastPeriods = Arrays.asList(new ForecastResponsePeriod[rainProbabilities.length]);
        for (int i = 0; i < rainProbabilities.length; i++) {
            ForecastResponsePeriod period = Mockito.mock(ForecastResponsePeriod.class);
            Mockito.when(period.getProbabilityOfPrecipitation()).thenReturn(new ProbabilityOfPrecipitation("wmoUnit:percent", rainProbabilities[i]));
            Mockito.when(period.getNumber()).thenReturn(i + 1);
            forecastPeriods.set(i, period);
        }
        return forecastPeriods;
    }
}