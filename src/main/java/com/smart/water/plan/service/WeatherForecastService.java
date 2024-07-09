package com.smart.water.plan.service;

import com.smart.water.plan.model.weather.forecast.ForecastResponsePeriod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WeatherForecastService {

    public List<Integer> getWeatherArray(List<ForecastResponsePeriod> forecastPeriods, int daysWithoutRain, int rainThreshold) {
        List<Integer> weatherArray = new ArrayList<>();
        List<Integer> pastDailyWeatherArray = generatePastWeatherArray(daysWithoutRain);
        weatherArray.addAll(pastDailyWeatherArray);

        List<Integer> probabilityValues = extractProbabilityValues(forecastPeriods);
        List<Integer> thresholdValues = applyThreshold(probabilityValues, rainThreshold);
        List<Integer> futureDailyWeatherArray = compressForecastArray(thresholdValues);
        weatherArray.addAll(futureDailyWeatherArray);

        return weatherArray;
    }

    public List<Integer> generatePastWeatherArray(int daysWithoutRain) {
        List<Integer> weatherArray = new ArrayList<>();
        int index = 6 - daysWithoutRain;
        for (int i = 0; i < 7; i++) {
            if (i == index) {
                weatherArray.add(1);
            } else {
                weatherArray.add(0);
            }
        }
        return weatherArray;
    }

    public List<Integer> extractProbabilityValues(List<ForecastResponsePeriod> forecastPeriods) {
        List<Integer> probabilityValues = new ArrayList<>();
        for (ForecastResponsePeriod period : forecastPeriods) {
            probabilityValues.add(period.getProbabilityOfPrecipitation().getValue());
        }
        return probabilityValues;
    }

    public List<Integer> applyThreshold(List<Integer> probabilityValues, int rainThreshold) {
        List<Integer> thresholdValues = new ArrayList<>();
        for (int value : probabilityValues) {
            if (value >= rainThreshold) {
                thresholdValues.add(1);
            } else {
                thresholdValues.add(0);
            }
        }
        return thresholdValues;
    }

    public List<Integer> compressForecastArray(List<Integer> futureWeatherArray) {
        List<Integer> compressedArray = new ArrayList<>();
        for (int i = 0; i < futureWeatherArray.size(); i += 2) {
            int first = futureWeatherArray.get(i);
            int second = futureWeatherArray.get(i + 1);
            compressedArray.add(first | second);
        }
        return compressedArray;
    }
}
