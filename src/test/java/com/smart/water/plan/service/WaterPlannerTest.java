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
    public void testPlanWhenToWater() {
        // Create a mock list of ForecastResponsePeriod
        ForecastResponsePeriod period1 = Mockito.mock(ForecastResponsePeriod.class);
        Mockito.when(period1.getProbabilityOfPrecipitation()).thenReturn(new ProbabilityOfPrecipitation("wmoUnit:percent", 50));
        Mockito.when(period1.getNumber()).thenReturn(1);

        ForecastResponsePeriod period2 = Mockito.mock(ForecastResponsePeriod.class);
        Mockito.when(period2.getProbabilityOfPrecipitation()).thenReturn(new ProbabilityOfPrecipitation("wmoUnit:percent",70));
        Mockito.when(period2.getNumber()).thenReturn(2);

        List<ForecastResponsePeriod> forecastPeriods = Arrays.asList(period1, period2);

        // Test planWhenToWater method
        int result = waterPlanner.planWhenToWater(forecastPeriods, WateringUrgency.IMMEDIATE, 60);
        assertEquals(0, result);

        result = waterPlanner.planWhenToWater(forecastPeriods, WateringUrgency.URGENT, 60);
        assertEquals(2, result);

        result = waterPlanner.planWhenToWater(forecastPeriods, WateringUrgency.ON_SCHEDULE, 60);
        assertEquals(4, result);

        result = waterPlanner.planWhenToWater(forecastPeriods, WateringUrgency.NO_NEED_TO_WATER, 60);
        assertEquals(6, result);
    }
}