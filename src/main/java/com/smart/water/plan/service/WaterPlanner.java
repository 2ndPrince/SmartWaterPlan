package com.smart.water.plan.service;

import com.smart.water.plan.model.weather.forecast.ForecastResponsePeriod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WaterPlanner {

    public int planWhenToWater(List<ForecastResponsePeriod> forecastPeriods, WateringUrgency urgency, int rainThreshold) {
        List<Integer> rainyPeriods = getRainyForecastPeriods(forecastPeriods, rainThreshold);

        if (rainyPeriods.isEmpty()) {
            if (urgency == WateringUrgency.IMMEDIATE) {
                return 0; // Water today
            } else if (urgency == WateringUrgency.URGENT) {
                return 2; // Water tomorrow
            } else if (urgency == WateringUrgency.ON_SCHEDULE) {
                return 4; // Water in two days
            } else {
                return 6; // No need to water in the next three days
            }
        }

        int firstRainingFuturePeriod = rainyPeriods.get(0);

        if (urgency == WateringUrgency.IMMEDIATE) {
            return returnZeroIfNegative(firstRainingFuturePeriod - 6);
        } else if (urgency == WateringUrgency.URGENT) {
            return returnZeroIfNegative(firstRainingFuturePeriod - 4);
        } else if (urgency == WateringUrgency.ON_SCHEDULE) {
            return returnZeroIfNegative(firstRainingFuturePeriod - 2);
        } else {
            return returnZeroIfNegative(firstRainingFuturePeriod);
        }
    }

    private List<Integer> getRainyForecastPeriods(List<ForecastResponsePeriod> forecastPeriods, int rainThreshold) {
        List<Integer> rainyPeriods = new ArrayList<>();

        for (ForecastResponsePeriod period : forecastPeriods) {
            if (period.getProbabilityOfPrecipitation().getValue() > rainThreshold) {
                rainyPeriods.add(period.getNumber());
            }
        }

        return rainyPeriods;
    }

    private int returnZeroIfNegative(int value) {
        return Math.max(value, 0);
    }
}