package com.example.service;

import java.time.LocalDate;
import java.util.ArrayList;

public class HotelSearchEngine {

	public HotelSearchEngine() {
	}

	public ArrayList<Hotel> getAllHotels() {
		return new ArrayList<>();
	}

	public ArrayList<Hotel> search(String country, String city, LocalDate from, LocalDate to) {
		ArrayList<Hotel> hotels = new ArrayList<>();
		hotels.add(new Hotel(1, "Test Hotel", country, city));
		return hotels;
	}
}