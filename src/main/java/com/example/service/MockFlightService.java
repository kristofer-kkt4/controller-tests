package com.example.service;

import java.util.ArrayList;
import java.util.List;

/**
 * MockFlightService simulates an external flight service.
 *
 * It returns predefined data instead of calling a real API.
 * This allows the controller to be tested in isolation.
 */
public class MockFlightService implements FlightService {

    /** Last departure code received by the mock. */
    public String lastDepartureCode;

    /** Last arrival code received by the mock. */
    public String lastArrivalCode;

    /** Last date received by the mock. */
    public String lastDate;

    /**
     * Simulates a search for flights.
     *
     * @param departureCode departure airport code
     * @param arrivalCode arrival airport code
     * @param date travel date
     * @return predefined list of flight IDs
     */
    @Override
    public List<String> searchFlights(String departureCode, String arrivalCode, String date) {
        this.lastDepartureCode = departureCode;
        this.lastArrivalCode = arrivalCode;
        this.lastDate = date;

        List<String> flights = new ArrayList<>();
        flights.add("FI204");
        flights.add("SK596");
        return flights;
    }
}