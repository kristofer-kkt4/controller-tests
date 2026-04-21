package com.example.ui.controllers;

import com.example.ui.MainApp;
import com.example.ui.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML private Label         profileNameLabel;
    @FXML private TextField     nameField;
    @FXML private TextField     emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField     preferencesField;
    @FXML private Label         statusLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        profileNameLabel.setText(SessionManager.getCurrentUserName());
        nameField.setText(SessionManager.getCurrentUserName());
        emailField.setText(SessionManager.getCurrentUserEmail());
        preferencesField.setText("Adventure, Culture");
    }

    @FXML
    private void handleUpdateProfile() {
        String name  = nameField.getText().trim();
        String email = emailField.getText().trim();
        if (name.isEmpty() || email.isEmpty()) {
            showStatus("Name and email are required.", true); return;
        }
        SessionManager.login(SessionManager.getCurrentUserId(), name, email, SessionManager.isAdmin());
        profileNameLabel.setText(name);
        showStatus("✓  Profile updated successfully.", false);
    }

    @FXML
    private void handleDeleteAccount() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Account");
        confirm.setHeaderText("Are you sure?");
        confirm.setContentText("This will permanently delete your account.");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                SessionManager.logout();
                navigate("Login");
            }
        });
    }

    @FXML private void goToDashboard() { navigate("Dashboard"); }
    @FXML private void goToMyPlans()   { navigate("MyPlans"); }
    @FXML private void handleLogout()  { SessionManager.logout(); navigate("Login"); }

    private void navigate(String s) {
        try { MainApp.navigateTo(s); } catch (Exception ignored) {}
    }
    private void showStatus(String msg, boolean isError) {
        statusLabel.setText(msg);
        statusLabel.getStyleClass().removeAll("error-label", "success-label");
        statusLabel.getStyleClass().add(isError ? "error-label" : "success-label");
    }
}
