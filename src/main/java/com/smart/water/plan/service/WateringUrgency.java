package com.smart.water.plan.service;

public enum WateringUrgency {
    IMMEDIATE,
    URGENT,
    ON_SCHEDULE,
    NO_NEED_TO_WATER;

    private static final int IMMEDIATE_THRESHOLD = 5;
    private static final int URGENT_THRESHOLD = 3;
    private static final int ON_SCHEDULE_THRESHOLD = 1;

    public static WateringUrgency fromInteger(int periodsToWater) {
        if (periodsToWater >= IMMEDIATE_THRESHOLD) { // 5, 6 or more
            return IMMEDIATE;
        } else if (periodsToWater >= URGENT_THRESHOLD) { // 3, or 4
            return URGENT;
        } else if (periodsToWater >= ON_SCHEDULE_THRESHOLD) { // 1 or 2
            return ON_SCHEDULE;
        } else {
            return NO_NEED_TO_WATER; // 0
        }
    }
}
