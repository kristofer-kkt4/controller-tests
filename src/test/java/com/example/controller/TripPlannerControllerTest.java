package com.example.controller;

import com.example.service.MockFlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripPlannerControllerTest {

    private MockFlightService mockFlightService;
    private TripPlannerController controller;

    @BeforeEach
    void setUp() {
        mockFlightService = new MockFlightService();
        controller = new TripPlannerController(mockFlightService);
    }

    @Test
    void searchTrip_shouldReturnFlights() {
        List<String> result = controller.searchTrip("KEF", "CPH", "2026-05-10");

        assertEquals(2, result.size());
        assertTrue(result.contains("FI204"));
        assertTrue(result.contains("SK596"));
    }

    @Test
    void searchTrip_shouldCallFlightServiceCorrectly() {
        controller.searchTrip("KEF", "OSL", "2026-06-01");

        assertEquals("KEF", mockFlightService.lastDepartureCode);
        assertEquals("OSL", mockFlightService.lastArrivalCode);
        assertEquals("2026-06-01", mockFlightService.lastDate);
    }
}