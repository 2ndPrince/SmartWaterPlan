package com.smart.water.plan.util;

import com.smart.water.plan.model.weather.forecast.ForecastResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.smart.water.plan.util.JsonUtil.readForecastResponse;
import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {

    @Test
    void testReadForecastResponse() throws IOException {
        ForecastResponse forecastResponse = readForecastResponse("src/test/resources/example-forecast-response.json");
        assertEquals(14, forecastResponse.getProperties().getPeriods().size());
    }

}