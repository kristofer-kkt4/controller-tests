package com.example.service;

import java.sql.SQLException;
import java.util.List;

public interface TripSearchService {
    List<TripEvent> searchTrips(String name, String location, String date, int groupSize) throws SQLException;
}
