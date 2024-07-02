package com.smart.water.plan.model.weather.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ForecastResponsePeriod {
    private int number;
    private String name;
    @JsonProperty("startTime")
    private String startTime;
    @JsonProperty("endTime")
    private String endTime;
    @JsonProperty("isDaytime")
    private boolean isDaytime;
    private int temperature;
    @JsonProperty("temperatureUnit")
    private String temperatureUnit;
    @JsonProperty("temperatureTrend")
    private String temperatureTrend;
    @JsonProperty("probabilityOfPrecipitation")
    private ProbabilityOfPrecipitation probabilityOfPrecipitation;
    @JsonProperty("windSpeed")
    private String windSpeed;
    @JsonProperty("windDirection")
    private String windDirection;
    private String icon;
    @JsonProperty("shortForecast")
    private String shortForecast;
    @JsonProperty("detailedForecast")
    private String detailedForecast;
}
