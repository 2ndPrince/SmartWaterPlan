package com.smart.water.plan.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WateringUrgencyTest {

    @Test
    public void testFromInteger() {
        assertEquals(WateringUrgency.IMMEDIATE, WateringUrgency.fromInteger(5));
        assertEquals(WateringUrgency.IMMEDIATE, WateringUrgency.fromInteger(6));
        assertEquals(WateringUrgency.IMMEDIATE, WateringUrgency.fromInteger(14));
        assertEquals(WateringUrgency.URGENT, WateringUrgency.fromInteger(4));
        assertEquals(WateringUrgency.URGENT, WateringUrgency.fromInteger(3));
        assertEquals(WateringUrgency.ON_SCHEDULE, WateringUrgency.fromInteger(2));
        assertEquals(WateringUrgency.ON_SCHEDULE, WateringUrgency.fromInteger(1));
        assertEquals(WateringUrgency.NO_NEED_TO_WATER, WateringUrgency.fromInteger(0));
        assertEquals(WateringUrgency.NO_NEED_TO_WATER, WateringUrgency.fromInteger(-14));
    }
}