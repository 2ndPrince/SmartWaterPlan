package com.smart.water.plan.service;

import com.smart.water.plan.client.WeatherGovClient;
import com.smart.water.plan.model.weather.forecast.ForecastResponsePeriod;
import com.smart.water.plan.user.User;
import com.smart.water.plan.user.UserService;
import com.smart.water.plan.util.validator.WeatherValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WeatherService {

    private final WeatherGovClient weatherClient;
    private final WaterPlanner waterPlanner;
    private final UserService userService;

    @Value("${water.raining.threshold.percentage}")
    private int rainDefaultThreshold;

    public WeatherService(WeatherGovClient weatherClient, WaterPlanner waterPlanner, UserService userService) {
        this.weatherClient = weatherClient;
        this.waterPlanner = waterPlanner;
        this.userService = userService;
    }


    // TODO: Use GeoCoding to support address translation to LatLon
    public String gerWeatherByZipCode(String zipCode) {
        WeatherValidator.validateZipCode(zipCode);
        return weatherClient.getWeatherForecast(zipCode);
    }

    public int getWateringPeriods(double lat, double lon, int daysWithoutRain, UUID userId) {
        String forecastUrl = getForecastUrl(lat, lon);
        List<ForecastResponsePeriod> sevenDayPeriods = getSevenDayPeriods(forecastUrl);
        Optional<User> user = userService.findUserById(userId);
        int rainThreshold = user.map(User::getThreshold).orElse(rainDefaultThreshold);
        return getPeriodsToWater(sevenDayPeriods, daysWithoutRain, rainThreshold);
    }

    public int getPeriodsToWater(List<ForecastResponsePeriod> forecastPeriods, int daysWithoutRain, int rainThreshold) {
        int periodsWithoutRain = daysWithoutRain * 2;
        WateringUrgency wateringUrgency = WateringUrgency.fromInteger(periodsWithoutRain);
        return waterPlanner.planWhenToWater(forecastPeriods, wateringUrgency, rainThreshold);
    }

    // TODO: verify and modify this method
    public List<Integer> getWeatherArray(List<ForecastResponsePeriod> forecastPeriods, int daysWithoutRain, int rainThreshold) {
        List<Integer> pastWeatherArray = generatePastWeatherArray(daysWithoutRain);
        List<Integer> futureWeatherArray = generateFutureWeatherArray(forecastPeriods, rainThreshold);
        List<Integer> probabilityValues = extractProbabilityValues(forecastPeriods);
        List<Integer> thresholdValues = applyThreshold(probabilityValues, rainThreshold);
        List<Integer> compressedArray = compressForecastArray(futureWeatherArray);
        List<Integer> weatherArray = new ArrayList<>();
        weatherArray.addAll(pastWeatherArray);
        weatherArray.addAll(thresholdValues);
        weatherArray.addAll(compressedArray);
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

    public List<Integer> generateFutureWeatherArray(List<ForecastResponsePeriod> forecastPeriods, int rainThreshold) {
        List<Integer> futureWeatherArray = new ArrayList<>();
        for (ForecastResponsePeriod period : forecastPeriods) {
            if (period.getProbabilityOfPrecipitation().getValue() > rainThreshold) {
                futureWeatherArray.add(1);
            } else {
                futureWeatherArray.add(0);
            }
        }
        return futureWeatherArray;
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

    public int getFirstRainingDay(double lat, double lon, int rainThreshold) {
        String forecastUrl = getForecastUrl(lat, lon);
        List<ForecastResponsePeriod> sevenDayPeriods = getSevenDayPeriods(forecastUrl);
        return getFirstRainingDay(sevenDayPeriods, rainThreshold);
    }

    private int getFirstRainingDay(List<ForecastResponsePeriod> forecastPeriods, int rainThreshold) {
        for (ForecastResponsePeriod period : forecastPeriods) {
            if (period.getProbabilityOfPrecipitation().getValue() > rainThreshold) {
                return period.getNumber();
            }
        }
        return -1;
    }

    public String getForecastUrl(double lat, double lon) {
        WeatherValidator.validateLatLon(lat, lon);
        return weatherClient.getForecastUrlByLatLon(lat, lon);
    }

    private List<ForecastResponsePeriod> getSevenDayPeriods(String forecastUrl) {
        return weatherClient.getSevenDayForecast(forecastUrl).getProperties().getPeriods();
    }
}
