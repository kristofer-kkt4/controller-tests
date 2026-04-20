package com.example.model;

import java.time.LocalDate;

public class TripSearchRequest {
    private String departureCode;
    private String arrivalCode;
    private String country;
    private String city;
    private String tourName;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private int groupSize;

    public TripSearchRequest() {
    }

    public TripSearchRequest(String departureCode, String arrivalCode, String country, String city,
                             String tourName, String location, LocalDate startDate, LocalDate endDate,
                             int groupSize) {
        this.departureCode = departureCode;
        this.arrivalCode = arrivalCode;
        this.country = country;
        this.city = city;
        this.tourName = tourName;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.groupSize = groupSize;
    }

    public String getDepartureCode() {
        return departureCode;
    }

    public String getArrivalCode() {
        return arrivalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getTourName() {
        return tourName;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getGroupSize() {
        return groupSize;
    }
}