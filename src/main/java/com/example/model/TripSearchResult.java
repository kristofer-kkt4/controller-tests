package com.example.model;

import java.util.List;
import com.example.service.Flight;
import com.example.service.Hotel;
import com.example.service.TripEvent;

public class TripSearchResult {

    private List<Flight> flights;
    private List<Hotel> hotels;
    private List<TripEvent> tours;

    public TripSearchResult(List<Flight> flights, List<Hotel> hotels, List<TripEvent> tours) {
        this.flights = flights;
        this.hotels = hotels;
        this.tours = tours;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public List<TripEvent> getTours() {
        return tours;
    }
}