package com.smart.water.plan.transaction;

import com.smart.water.plan.model.weather.forecast.ForecastResponsePeriod;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document(collection = "weatherTransactions")
@Data
@Builder
public class WeatherTransaction {
    @Id
    private String id;

    private UUID userId;
    private double latitude;
    private double longitude;
    private int daysWithoutRain;
    private Date transactionTime = new Date();
    private List<ForecastResponsePeriod> forecastPeriods;
    private List<Integer> weatherArray;
}
