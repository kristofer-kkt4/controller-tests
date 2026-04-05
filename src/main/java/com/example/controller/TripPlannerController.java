package com.example.controller;

import com.example.service.FlightService;

import java.util.List;

public class TripPlannerController {

    private final FlightService flightService;

    public TripPlannerController(FlightService flightService) {
        this.flightService = flightService;
    }

    public List<String> searchTrip(String departureCode, String arrivalCode, String date) {
        return flightService.searchFlights(departureCode, arrivalCode, date);
    }
}