package com.example.ui.controllers;

import com.example.ui.MainApp;
import com.example.ui.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import java.net.URL;
import java.util.*;

public class MyPlansController implements Initializable {

    @FXML private Label welcomeLabel;
    @FXML private VBox  plansContainer;
    @FXML private Label emptyLabel;
    @FXML private Label planCountLabel;

    // In-memory saved plans (per session)
    // Key: plan title, Value: map of details
    public static final List<Map<String, String>> savedPlans = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        welcomeLabel.setText("Welcome back, " + SessionManager.getCurrentUserName());
        loadPlans();
    }

    private void loadPlans() {
        plansContainer.getChildren().clear();
        if (savedPlans.isEmpty()) {
            emptyLabel.setVisible(true);
            planCountLabel.setText("");
            return;
        }
        emptyLabel.setVisible(false);
        planCountLabel.setText(savedPlans.size() + " saved plan" + (savedPlans.size() != 1 ? "s" : ""));
        for (Map<String, String> plan : new ArrayList<>(savedPlans)) {
            plansContainer.getChildren().add(buildPlanCard(plan));
        }
    }

    public static void addPlan(Map<String, String> plan) {
        savedPlans.add(plan);
    }

    private VBox buildPlanCard(Map<String, String> plan) {
        VBox card = new VBox(0);
        card.getStyleClass().add("package-card");

        // Header
        HBox header = new HBox();
        header.getStyleClass().add("card-header");
        Label title = new Label(plan.getOrDefault("title", "Trip Package"));
        title.getStyleClass().add("card-title");
        Region sp = new Region(); HBox.setHgrow(sp, Priority.ALWAYS);
        VBox priceBox = new VBox(2);
        priceBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        Label price = new Label(plan.getOrDefault("price", "—"));
        price.getStyleClass().add("card-price");
        Label sub = new Label("est. total");
        sub.getStyleClass().add("card-price-sub");
        priceBox.getChildren().addAll(price, sub);
        header.getChildren().addAll(title, sp, priceBox);

        // Body
        HBox body = new HBox(0);
        body.getStyleClass().add("card-body");

        VBox fCol = makeDetailColumn("✈  FLIGHT", new String[][]{
            {"Flight Number", plan.getOrDefault("flightNumber", "—")},
            {"Route",         plan.getOrDefault("route", "—")}
        }); fCol.getStyleClass().add("detail-col");

        Separator d1 = new Separator(javafx.geometry.Orientation.VERTICAL);
        d1.getStyleClass().add("col-divider");

        VBox hCol = makeDetailColumn("🏨  HOTEL", new String[][]{
            {"Name",    plan.getOrDefault("hotelName", "—")},
            {"City",    plan.getOrDefault("city", "—")},
            {"Country", plan.getOrDefault("country", "—")},
            {"Price",   plan.getOrDefault("hotelPrice", "—")}
        }); hCol.getStyleClass().add("detail-col");

        Separator d2 = new Separator(javafx.geometry.Orientation.VERTICAL);
        d2.getStyleClass().add("col-divider");

        VBox tCol = makeDetailColumn("🗺  DAY TOUR", new String[][]{
            {"Tour Name", plan.getOrDefault("tourName", "—")}
        }); tCol.getStyleClass().add("detail-col");

        HBox.setHgrow(fCol, Priority.ALWAYS);
        HBox.setHgrow(hCol, Priority.ALWAYS);
        HBox.setHgrow(tCol, Priority.ALWAYS);
        body.getChildren().addAll(fCol, d1, hCol, d2, tCol);

        // Footer
        HBox footer = new HBox(12);
        footer.getStyleClass().add("card-footer");
        footer.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        Label saved = new Label("Saved on: " + plan.getOrDefault("savedDate", "—"));
        saved.getStyleClass().add("calc-label");
        Region footSp = new Region(); HBox.setHgrow(footSp, Priority.ALWAYS);
        Button del = new Button("🗑  Remove Plan");
        del.getStyleClass().add("btn-danger");
        del.setOnAction(e -> {
            savedPlans.remove(plan);
            plansContainer.getChildren().remove(card);
            if (plansContainer.getChildren().isEmpty()) {
                emptyLabel.setVisible(true);
                planCountLabel.setText("");
            } else {
                planCountLabel.setText(savedPlans.size() + " saved plan" + (savedPlans.size() != 1 ? "s" : ""));
            }
        });
        footer.getChildren().addAll(saved, footSp, del);
        card.getChildren().addAll(header, body, footer);
        return card;
    }

    private VBox makeDetailColumn(String heading, String[][] rows) {
        VBox col = new VBox(10);
        col.setPadding(new Insets(18));
        Label h = new Label(heading); h.getStyleClass().add("col-heading");
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

    @FXML private void goToDashboard() { navigate("Dashboard"); }
    @FXML private void goToProfile()   { navigate("Profile"); }
    @FXML private void handleLogout()  { SessionManager.logout(); navigate("Login"); }

    private void navigate(String s) {
        try { MainApp.navigateTo(s); } catch (Exception ignored) {}
    }
}
