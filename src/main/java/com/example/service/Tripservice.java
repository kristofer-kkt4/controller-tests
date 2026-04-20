package com.example.service;

import java.sql.SQLException;
import java.util.List;

import com.example.service.TripEvent;

public interface TripSearchService {
    List<TripEvent> searchTrips(String name, String location, String date, int groupSize) throws SQLException;
}
