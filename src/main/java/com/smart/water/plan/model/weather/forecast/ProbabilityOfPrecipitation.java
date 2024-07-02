package com.smart.water.plan.model.weather.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProbabilityOfPrecipitation {
    @JsonProperty("unitCode")
    private String unitCode;
    private int value;

    public ProbabilityOfPrecipitation(String unitCode, int value) {
        this.unitCode = unitCode;
        this.value = value;
    }
}
