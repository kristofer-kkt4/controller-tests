package com.example.controller;

import com.example.model.TripSearchRequest;
import com.example.model.TripSearchResult;
import com.example.service.Flight;
import com.example.service.FlightComponentFacade;
import com.example.service.Hotel;
import com.example.service.HotelSearchEngine;
import com.example.service.TripEvent;
import com.example.service.TripSearchService;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TripPlannerController {

    private final FlightComponentFacade flightComponentFacade;
    private final HotelSearchEngine hotelSearchEngine;
    private final TripSearchService tripSearchService;

    public TripPlannerController(FlightComponentFacade flightComponentFacade,
                                 HotelSearchEngine hotelSearchEngine,
                                 TripSearchService tripSearchService) {
        this.flightComponentFacade = flightComponentFacade;
        this.hotelSearchEngine = hotelSearchEngine;
        this.tripSearchService = tripSearchService;
    }

    public TripSearchResult searchTrip(TripSearchRequest request) {
        List<Flight> flights = new ArrayList<>();
        List<Hotel> hotels = new ArrayList<>();
        List<TripEvent> tours = new ArrayList<>();

        try {
            ZonedDateTime flightDateTime = request.getStartDate()
                    .atTime(LocalTime.NOON)
                    .atZone(ZoneId.systemDefault());

            List<Flight> flightResults = flightComponentFacade.searchFlights(
                    request.getDepartureCode(),
                    request.getArrivalCode(),
                    flightDateTime
            );

            if (flightResults != null) {
                flights = flightResults;
            }
        } catch (Exception e) {
            flights = new ArrayList<>();
        }

        try {
            List<Hotel> hotelResults = hotelSearchEngine.search(
                    request.getCountry(),
                    request.getCity(),
                    request.getStartDate(),
                    request.getEndDate()
            );

            if (hotelResults != null) {
                hotels = hotelResults;
            }
        } catch (Exception e) {
            hotels = new ArrayList<>();
        }

        try {
            List<TripEvent> tourResults = tripSearchService.searchTrips(
                    request.getTourName(),
                    request.getLocation(),
                    request.getStartDate().toString(),
                    request.getGroupSize()
            );

            if (tourResults != null) {
                tours = tourResults;
            }
        } catch (Exception e) {
            tours = new ArrayList<>();
        }

        return new TripSearchResult(flights, hotels, tours);
    }
}