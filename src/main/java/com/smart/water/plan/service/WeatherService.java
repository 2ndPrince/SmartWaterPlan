package com.smart.water.plan.service;

import com.smart.water.plan.client.WeatherGovClient;
import com.smart.water.plan.model.weather.forecast.ForecastResponsePeriod;
import com.smart.water.plan.user.User;
import com.smart.water.plan.user.UserService;
import com.smart.water.plan.util.validator.WeatherValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
