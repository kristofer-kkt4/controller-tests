package hi.hotelsearch;

import java.util.ArrayList;
import java.time.LocalDate;
import hi.hotelsearch.controller.HotelController;
import hi.hotelsearch.db.HotelDB;
import hi.hotelsearch.model.Room;
import hi.hotelsearch.model.Hotel;
import hi.hotelsearch.model.Booking;


public class HotelSearchEngine {
	
	private HotelController hotelController;
	public HotelSearchEngine() {
		hotelController = new HotelController(new HotelDB());
	}
	public ArrayList<Hotel> getAllHotels() {	
		ArrayList<Hotel> hotels = hotelController.getAllHotels();
		for(Hotel hotel : hotels) {
			hotel.getAllRooms();
		}

		return hotels;
	}
	public ArrayList<Hotel> search(String country, String city, LocalDate from, LocalDate to) {
		try {
			ArrayList<Hotel> hotels = hotelController.selectByCountryCity(country, city);
			for(Hotel hotel : hotels) {
				hotel.getRoomsByAvailability(from, to);
			}

			return hotels;
		} catch(Exception e) {
			return null;
		}
	}
}
