package com.smart.water.plan.service;

import com.smart.water.plan.model.weather.forecast.ForecastResponsePeriod;
import com.smart.water.plan.transaction.TransactionService;
import com.smart.water.plan.transaction.WeatherTransaction;
import com.smart.water.plan.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WeatherService {

    private final WeatherGeoService geoService;
    private final WeatherForecastService forecastService;
    private final UserService userService;
    private final TransactionService transactionService;

    public List<Integer> getWeatherArray(double lat, double lon, int daysWithoutRain, UUID userId) {
        List<ForecastResponsePeriod> forecastPeriods = geoService.getForecastPeriods(lat, lon);
        int rainThresholdInPercent = userService.getRainThreshold(userId);
        List<Integer> weatherArray = forecastService.getWeatherArray(forecastPeriods, daysWithoutRain, rainThresholdInPercent);
        WeatherTransaction transaction = WeatherTransaction.builder()
                .userId(userId)
                .latitude(lat)
                .longitude(lon)
                .daysWithoutRain(daysWithoutRain)
                .forecastPeriods(forecastPeriods)
                .weatherArray(weatherArray)
                .build();
        transactionService.saveTransaction(transaction);
        return weatherArray;
    }
}
