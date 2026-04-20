package com.example.service;

import java.util.ArrayList;
import java.util.List;

public class SimpleTripSearchService implements TripSearchService {

    @Override
    public List<TripEvent> searchTrips(String name, String location, String date, int groupSize) {
        return new ArrayList<>();
    }
}
