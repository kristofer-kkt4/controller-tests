package com.example.service;

import java.util.List;

public interface FlightService {
    List<String> searchFlights(String departureCode, String arrivalCode, String date);
}