package com.smart.water.plan.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WaterLawnPlanner {

    // Constant for the number of days to check before and after today
    private static final int DAYS_TO_CHECK = 3;

    // Method to determine if watering is needed
    public static boolean shouldWater(int[] weatherArray) {
        int todayIndex = weatherArray.length / 2; // Index of today, assuming weatherArray always has an odd length and today is the middle element

        // Check if it rained today
        if (weatherArray[todayIndex] == 1) {
            return false; // No need to water today
        }

        // Check if it rained in the past DAYS_TO_CHECK days
        for (int i = todayIndex - 1; i >= todayIndex - DAYS_TO_CHECK && i >= 0; i--) {
            if (weatherArray[i] == 1) {
                return false; // It rained within the last DAYS_TO_CHECK days, no need to water today
            }
        }

        // If no rain in the past DAYS_TO_CHECK days, water today
        return true;
    }
}