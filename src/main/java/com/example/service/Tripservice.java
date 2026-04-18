package daytrips;

import java.sql.SQLException;
import java.util.List;

import models.TripEvent;

public interface TripSearchService {
    List<TripEvent> searchTrips(String name, String location, String date, int groupSize) throws SQLException;
}
