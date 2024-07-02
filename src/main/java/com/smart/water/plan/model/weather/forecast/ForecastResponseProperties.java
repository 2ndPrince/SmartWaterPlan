package com.smart.water.plan.model.weather.forecast;

import lombok.Data;

import java.util.List;

@Data
public class ForecastResponseProperties {
    List<ForecastResponsePeriod> periods;
}
