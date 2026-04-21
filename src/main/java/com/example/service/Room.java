package com.example.service;

public class Room {

    private int id;
    private int hotelId;
    private String number;
    private double pricePerNight;
    private int capacity;

    public Room(int id, int hotelId, String number, double pricePerNight, int capacity) {
        this.id = id;
        this.hotelId = hotelId;
        this.number = number;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
    }

    public String getNumber() {
        return number;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getHotelId() {
        return hotelId;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return id + " " + hotelId + " " + number + " " + pricePerNight + " " + capacity;
    }
}