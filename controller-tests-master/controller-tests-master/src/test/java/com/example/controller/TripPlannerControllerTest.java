package com.example.controller;

import com.example.model.TripSearchRequest;
import com.example.model.TripSearchResult;
import com.example.service.FlightComponentFacade;
import com.example.service.HotelSearchEngine;
import com.example.service.SimpleTripSearchService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class TripPlannerControllerTest {

    @Test
    void searchTripReturnsNonEmptyResults() {
        TripPlannerController controller = new TripPlannerController(
                new FlightComponentFacade(),
                new HotelSearchEngine(),
                new SimpleTripSearchService()
        );

        TripSearchRequest request = new TripSearchRequest(
                "KEF",
                "LHR",
                "Iceland",
                "London",
                "Museum Tour",
                "London",
                LocalDate.of(2026, 5, 10),
                LocalDate.of(2026, 5, 15),
                2
        );

        TripSearchResult result = controller.searchTrip(request);

        assertFalse(result.getFlights().isEmpty());
        assertFalse(result.getHotels().isEmpty());
        assertFalse(result.getTours().isEmpty());
    }
}