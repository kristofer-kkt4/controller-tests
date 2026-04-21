package com.example.ui.controllers;

import com.example.controller.TripPlannerController;
import com.example.model.TripSearchRequest;
import com.example.model.TripSearchResult;
import com.example.service.Flight;
import com.example.service.FlightComponentFacade;
import com.example.service.Hotel;
import com.example.service.HotelSearchEngine;
import com.example.service.SimpleTripSearchService;
import com.example.service.TripEvent;
import com.example.ui.MainApp;
import com.example.ui.SessionManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Label      welcomeLabel;
    @FXML private TextField  departureField;
    @FXML private TextField  arrivalField;
    @FXML private TextField  countryField;
    @FXML private TextField  cityField;
    @FXML private TextField  tourNameField;
    @FXML private TextField  groupSizeField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Label      statusLabel;
    @FXML private Label      resultCountLabel;
    @FXML private VBox       resultsContainer;
    @FXML private Label      noResultsLabel;

    // Real backend controller — wired to actual services
    private final TripPlannerController tripPlannerController = new TripPlannerController(
        new FlightComponentFacade(),
        new HotelSearchEngine(),
        new SimpleTripSearchService()
    );

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        welcomeLabel.setText("Welcome back, " + SessionManager.getCurrentUserName());
        // Sensible defaults
        startDatePicker.setValue(LocalDate.now().plusDays(7));
        endDatePicker.setValue(LocalDate.now().plusDays(14));
    }

    @FXML
    private void handleSearch() {
        // Validate required fields
        String departure = departureField.getText().trim();
        String arrival   = arrivalField.getText().trim();
        String country   = countryField.getText().trim();
        String city      = cityField.getText().trim();

        if (departure.isEmpty() || arrival.isEmpty() || country.isEmpty() || city.isEmpty()) {
            statusLabel.setText("Please fill in Departure, Arrival, Country and City.");
            return;
        }

        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate   = endDatePicker.getValue();
        if (startDate == null || endDate == null) {
            statusLabel.setText("Please select start and end dates.");
            return;
        }
        if (endDate.isBefore(startDate)) {
            statusLabel.setText("End date must be after start date.");
            return;
        }

        String tourName  = tourNameField.getText().trim();
        if (tourName.isEmpty()) tourName = city + " Tour";

        int groupSize = 1;
        try {
            String gs = groupSizeField.getText().trim();
            if (!gs.isEmpty()) groupSize = Integer.parseInt(gs);
        } catch (NumberFormatException e) {
            statusLabel.setText("Group size must be a number.");
            return;
        }

        statusLabel.setText("Searching...");
        resultsContainer.getChildren().clear();
        noResultsLabel.setVisible(false);
        resultCountLabel.setText("");

        // ── REAL BACKEND CALL ──────────────────────────────────────
        TripSearchRequest request = new TripSearchRequest(
            departure, arrival, country, city,
            tourName, city, startDate, endDate, groupSize
        );

        TripSearchResult result = tripPlannerController.searchTrip(request);
        // ──────────────────────────────────────────────────────────

        List<Flight>    flights = result.getFlights();
        List<Hotel>     hotels  = result.getHotels();
        List<TripEvent> tours   = result.getTours();

        if (flights.isEmpty() && hotels.isEmpty() && tours.isEmpty()) {
            noResultsLabel.setText("No results found. Try different search criteria.");
            noResultsLabel.setVisible(true);
            statusLabel.setText("");
            return;
        }

        // Build one card per combination (match by index, pad with dashes if uneven)
        int count = Math.max(flights.size(), Math.max(hotels.size(), tours.size()));
        for (int i = 0; i < count; i++) {
            Flight    f = i < flights.size() ? flights.get(i) : null;
            Hotel     h = i < hotels.size()  ? hotels.get(i)  : null;
            TripEvent t = i < tours.size()   ? tours.get(i)   : null;
            resultsContainer.getChildren().add(buildResultCard(f, h, t, i + 1));
        }

        statusLabel.setText("");
        resultCountLabel.setText(count + " package" + (count != 1 ? "s" : "") + " found");
    }

    private VBox buildResultCard(Flight f, Hotel h, TripEvent t, int index) {
        VBox card = new VBox(0);
        card.getStyleClass().add("package-card");

        // ── Header ──
        HBox header = new HBox();
        header.getStyleClass().add("card-header");
        Label title = new Label("Trip Package #" + index);
        title.getStyleClass().add("card-title");
        Region sp = new Region(); HBox.setHgrow(sp, Priority.ALWAYS);

        // Calculate a simple total from available data
        double total = 0;
        if (h != null && !h.getRooms().isEmpty()) {
            total += h.getRooms().get(0).getPricePerNight() * 7;
        }
        Label priceLabel = new Label(total > 0 ? String.format("€ %.0f", total) : "—");
        priceLabel.getStyleClass().add("card-price");
        Label priceSub = new Label("est. total");
        priceSub.getStyleClass().add("card-price-sub");
        VBox priceBox = new VBox(2);
        priceBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        priceBox.getChildren().addAll(priceLabel, priceSub);
        header.getChildren().addAll(title, sp, priceBox);

        // ── Body: 3 columns ──
        HBox body = new HBox(0);
        body.getStyleClass().add("card-body");

        // Flight column
        VBox flightCol = makeDetailColumn("✈  FLIGHT", new String[][]{
            {"Flight Number", f != null ? f.getFlightNumber() : "—"},
            {"Route",         f != null ? departure() + " → " + arrival() : "—"}
        });
        flightCol.getStyleClass().add("detail-col");

        Separator d1 = new Separator(javafx.geometry.Orientation.VERTICAL);
        d1.getStyleClass().add("col-divider");

        // Hotel column
        String hotelRooms = "—";
        String hotelPrice = "—";
        if (h != null && !h.getRooms().isEmpty()) {
            hotelRooms = h.getRooms().size() + " room(s) available";
            hotelPrice = "€ " + String.format("%.2f", h.getRooms().get(0).getPricePerNight()) + " / night";
        }
        VBox hotelCol = makeDetailColumn("🏨  HOTEL", new String[][]{
            {"Hotel ID",  h != null ? String.valueOf(h.getId()) : "—"},
            {"Name",      h != null ? h.getName()    : "—"},
            {"City",      h != null ? h.getCity()    : "—"},
            {"Country",   h != null ? h.getCountry() : "—"},
            {"Rooms",     hotelRooms},
            {"Price",     hotelPrice}
        });
        hotelCol.getStyleClass().add("detail-col");

        Separator d2 = new Separator(javafx.geometry.Orientation.VERTICAL);
        d2.getStyleClass().add("col-divider");

        // Tour column
        VBox tourCol = makeDetailColumn("🗺  DAY TOUR", new String[][]{
            {"Tour Name", t != null ? t.getName() : "—"}
        });
        tourCol.getStyleClass().add("detail-col");

        HBox.setHgrow(flightCol, Priority.ALWAYS);
        HBox.setHgrow(hotelCol,  Priority.ALWAYS);
        HBox.setHgrow(tourCol,   Priority.ALWAYS);
        body.getChildren().addAll(flightCol, d1, hotelCol, d2, tourCol);

        // ── Footer ──
        HBox footer = new HBox(12);
        footer.getStyleClass().add("card-footer");
        footer.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        Label info = new Label("Flight + Hotel + Tour combined package");
        info.getStyleClass().add("calc-label");
        Region footSp = new Region(); HBox.setHgrow(footSp, Priority.ALWAYS);
        Button saveBtn = new Button("＋  Save to My Plans");
        saveBtn.getStyleClass().add("btn-save");
        saveBtn.setOnAction(e -> {
            saveBtn.setText("✓  Saved!");
            saveBtn.setDisable(true);
            saveBtn.getStyleClass().add("btn-saved");
        });
        footer.getChildren().addAll(info, footSp, saveBtn);

        card.getChildren().addAll(header, body, footer);
        return card;
    }

    // Helper: get current field values for display in cards
    private String departure() { return departureField.getText().trim().toUpperCase(); }
    private String arrival()   { return arrivalField.getText().trim().toUpperCase(); }

    private VBox makeDetailColumn(String heading, String[][] rows) {
        VBox col = new VBox(10);
        col.setPadding(new Insets(18));
        Label h = new Label(heading);
        h.getStyleClass().add("col-heading");
        col.getChildren().add(h);
        for (String[] row : rows) {
            VBox item = new VBox(2);
            Label k = new Label(row[0]); k.getStyleClass().add("detail-key");
            Label v = new Label(row[1]); v.getStyleClass().add("detail-val");
            item.getChildren().addAll(k, v);
            col.getChildren().add(item);
        }
        return col;
    }

    @FXML private void goToMyPlans()  { navigate("MyPlans"); }
    @FXML private void goToProfile()  { navigate("Profile"); }
    @FXML private void handleLogout() { SessionManager.logout(); navigate("Login"); }

    private void navigate(String s) {
        try { MainApp.navigateTo(s); }
        catch (Exception e) { statusLabel.setText("Navigation error: " + e.getMessage()); }
    }
}
