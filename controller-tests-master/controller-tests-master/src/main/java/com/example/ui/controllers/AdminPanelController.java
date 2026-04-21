package com.example.ui.controllers;

import com.example.service.Flight;
import com.example.service.FlightComponentFacade;
import com.example.service.Hotel;
import com.example.service.HotelSearchEngine;
import com.example.service.TripEvent;
import com.example.service.SimpleTripSearchService;
import com.example.ui.MainApp;
import com.example.ui.SessionManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.net.URL;
import java.util.*;

public class AdminPanelController implements Initializable {

    @FXML private Label totalUsersLabel;
    @FXML private Label totalPackagesLabel;
    @FXML private Label totalPlansLabel;
    @FXML private Label servicesStatusLabel;
    @FXML private VBox  usersContainer;
    @FXML private VBox  packagesContainer;
    @FXML private TextField newPackageTitleField;
    @FXML private TextField newPackagePriceField;
    @FXML private Label pkgStatusLabel;

    // Real service instances to check availability
    private final FlightComponentFacade flightService    = new FlightComponentFacade();
    private final HotelSearchEngine     hotelService     = new HotelSearchEngine();
    private final SimpleTripSearchService tourService    = new SimpleTripSearchService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        totalUsersLabel.setText("3");
        totalPackagesLabel.setText("3");
        totalPlansLabel.setText(String.valueOf(MyPlansController.savedPlans != null ? 0 : 0));
        servicesStatusLabel.setText("All Online ✓");
        loadUsers();
        loadPackages();
    }

    private void loadUsers() {
        String[][] users = {
            {"user-001", "Traveler",  "test@test.com",  "Traveler"},
            {"user-002", "Ibrahim",   "ib@test.com",    "Traveler"},
            {"admin-001","Admin",     "admin@test.com", "Admin"},
        };
        for (String[] u : users) usersContainer.getChildren().add(buildUserRow(u));
    }

    private HBox buildUserRow(String[] u) {
        HBox row = new HBox(16);
        row.getStyleClass().add("admin-row");
        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        Label id    = new Label(u[0]); id.getStyleClass().add("admin-id");
        Label name  = new Label(u[1]); name.getStyleClass().add("admin-val");  HBox.setHgrow(name,  Priority.ALWAYS);
        Label email = new Label(u[2]); email.getStyleClass().add("admin-val"); HBox.setHgrow(email, Priority.ALWAYS);
        Label role  = new Label(u[3]); role.getStyleClass().add(u[3].equals("Admin") ? "tag-admin" : "tag-user");
        Button del  = new Button("Remove"); del.getStyleClass().add("btn-danger-sm");
        del.setOnAction(e -> {
            usersContainer.getChildren().remove(row);
            int cur = Integer.parseInt(totalUsersLabel.getText());
            totalUsersLabel.setText(String.valueOf(cur - 1));
        });
        row.getChildren().addAll(id, name, email, role, del);
        return row;
    }

    private void loadPackages() {
        // Seed with real service data — call real services
        try {
            List<Flight> flights = flightService.searchFlights("KEF", "LHR", java.time.ZonedDateTime.now());
            List<Hotel>  hotels  = hotelService.search("Iceland", "Reykjavik",
                java.time.LocalDate.now(), java.time.LocalDate.now().plusDays(7));
            List<TripEvent> tours = tourService.searchTrips("City Tour", "Reykjavik",
                java.time.LocalDate.now().toString(), 1);

            int idx = 1;
            for (int i = 0; i < Math.max(flights.size(), Math.max(hotels.size(), tours.size())); i++) {
                String flightNum = i < flights.size() ? flights.get(i).getFlightNumber() : "—";
                String hotelName = i < hotels.size()  ? hotels.get(i).getName()          : "—";
                String tourName  = i < tours.size()   ? tours.get(i).getName()           : "—";
                String label     = "Package #" + idx + " — " + flightNum + " + " + hotelName + " + " + tourName;
                packagesContainer.getChildren().add(buildPackageRow(new String[]{"pkg-00" + idx, label, "—"}));
                idx++;
            }
            totalPackagesLabel.setText(String.valueOf(packagesContainer.getChildren().size()));
        } catch (Exception e) {
            packagesContainer.getChildren().add(new Label("Could not load packages: " + e.getMessage()));
        }
    }

    private HBox buildPackageRow(String[] p) {
        HBox row = new HBox(16);
        row.getStyleClass().add("admin-row");
        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        Label id    = new Label(p[0]); id.getStyleClass().add("admin-id");
        Label title = new Label(p[1]); title.getStyleClass().add("admin-val"); HBox.setHgrow(title, Priority.ALWAYS);
        Label price = new Label(p[2]); price.getStyleClass().add("card-price");
        Button del  = new Button("Remove"); del.getStyleClass().add("btn-danger-sm");
        del.setOnAction(e -> {
            packagesContainer.getChildren().remove(row);
            int cur = Integer.parseInt(totalPackagesLabel.getText());
            totalPackagesLabel.setText(String.valueOf(Math.max(0, cur - 1)));
        });
        row.getChildren().addAll(id, title, price, del);
        return row;
    }

    @FXML
    private void handleAddPackage() {
        String title = newPackageTitleField.getText().trim();
        String price = newPackagePriceField.getText().trim();
        if (title.isEmpty() || price.isEmpty()) {
            pkgStatusLabel.setText("Please fill in both fields.");
            pkgStatusLabel.getStyleClass().removeAll("success-label");
            pkgStatusLabel.getStyleClass().add("error-label");
            return;
        }
        String newId = "pkg-" + (packagesContainer.getChildren().size() + 1);
        packagesContainer.getChildren().add(buildPackageRow(new String[]{newId, title, "€ " + price}));
        newPackageTitleField.clear(); newPackagePriceField.clear();
        int cur = Integer.parseInt(totalPackagesLabel.getText());
        totalPackagesLabel.setText(String.valueOf(cur + 1));
        pkgStatusLabel.setText("✓  Package added.");
        pkgStatusLabel.getStyleClass().removeAll("error-label");
        pkgStatusLabel.getStyleClass().add("success-label");
    }

    @FXML
    private void handleMonitorServices() {
        // Ping each real service
        StringBuilder sb = new StringBuilder();
        try { flightService.searchFlights("KEF","LHR", java.time.ZonedDateTime.now()); sb.append("✓  FlightComponentFacade  — Online\n"); }
        catch (Exception e) { sb.append("✗  FlightComponentFacade  — ERROR: ").append(e.getMessage()).append("\n"); }
        try { hotelService.search("Iceland","Reykjavik", java.time.LocalDate.now(), java.time.LocalDate.now().plusDays(1)); sb.append("✓  HotelSearchEngine      — Online\n"); }
        catch (Exception e) { sb.append("✗  HotelSearchEngine      — ERROR: ").append(e.getMessage()).append("\n"); }
        try { tourService.searchTrips("Tour","City", java.time.LocalDate.now().toString(), 1); sb.append("✓  SimpleTripSearchService — Online\n"); }
        catch (Exception e) { sb.append("✗  SimpleTripSearchService — ERROR: ").append(e.getMessage()).append("\n"); }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Service Monitor");
        alert.setHeaderText("Live Service Status");
        alert.setContentText(sb.toString());
        alert.showAndWait();
    }

    @FXML private void handleLogout() { SessionManager.logout(); navigate("Login"); }

    private void navigate(String s) {
        try { MainApp.navigateTo(s); } catch (Exception ignored) {}
    }
}
