package com.smart.water.plan.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WaterLawnPlannerTest {

    @Test
    void test() {
        WaterLawnPlanner planner = new WaterLawnPlanner();

        int[][] testCases = {
                {1, 0, 0, 1, 0, 0, 1}, // Today is rainy
                {0, 0, 0, 0, 0, 0, 0}, // No rain at all
                {0, 0, 0, 0, 1, 0, 0}, // Rain expected tomorrow, should water today
                {0, 1, 0, 0, 0, 0, 0}, // Rain yesterday, should not water today
                {0, 0, 0, 0, 0, 0, 1}, // Rain in 3 days, should water today
                {0, 0, 1, 0, 0, 0, 0}, // Rain 2 days ago, should not water today
        };

        boolean[] expectedResults = {
                false, // Today is rainy
                true,  // No rain at all
                true,  // Rain expected tomorrow, should water today
                false, // Rain yesterday, should not water today
                true,  // Rain in 3 days, should water today
                false, // Rain 2 days ago, should not water today
        };

        boolean[] actualResults = new boolean[testCases.length];
        for (int i = 0; i < testCases.length; i++) {
            actualResults[i] = planner.shouldWater(testCases[i]);
        }

        Assertions.assertArrayEquals(expectedResults, actualResults);
    }
}