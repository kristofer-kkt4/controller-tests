package com.example.service;

import java.util.ArrayList;

import hi.hotelsearch.db.RoomDB;
import hi.hotelsearch.controller.RoomController;

import java.time.LocalDate;

public class Hotel {
	private int id;
    private String name;
	private String country;
    private String city;
    private ArrayList<Room> rooms;
	private RoomController roomController;

    public Hotel(int id, String name, String country, String city) {
		this.id = id;
        this.name = name;
		this.country = country;
        this.city = city;
        this.rooms = new ArrayList<>();
    }

	public void getRoomsByAvailability(LocalDate from, LocalDate to) throws Exception {
		rooms = this.roomController.getRoomsByAvailability(id, from, to);
	}

	public void getAllRooms() {
		rooms = this.roomController.getAllRooms(id);
	}


    public void addRoom(Room room) {
        rooms.add(room);
    }

    public String getName() {
        return name;
    }
    public String getCountry() {
        return country;
    }
    public String getCity() {
        return city;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}

	public void setRoomController(RoomController roomController) {
		this.roomController = roomController;
	}

	public String toString() {
		return id + " " + name + " " + country + " " + city;
	}
}
