package com.example.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightComponentFacade {

    public List<Flight> searchFlights(String departureCode, String arrivalCode, ZonedDateTime date) {
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight());
        return flights;
    }
}