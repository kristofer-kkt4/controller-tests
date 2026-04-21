package com.example;

import com.example.controller.TripPlannerController;
import com.example.model.TripSearchRequest;
import com.example.model.TripSearchResult;
import com.example.service.FlightComponentFacade;
import com.example.service.HotelSearchEngine;
import com.example.service.SimpleTripSearchService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
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

        System.out.println("Flights: " + result.getFlights().size());
        System.out.println("Hotels: " + result.getHotels().size());
        System.out.println("Tours: " + result.getTours().size());
    }
}
