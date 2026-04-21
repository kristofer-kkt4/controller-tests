package com.example.ui.controllers;

import com.example.ui.MainApp;
import com.example.ui.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML private TextField     emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label         errorLabel;

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String pass  = passwordField.getText();

        if (email.isEmpty() || pass.isEmpty()) {
            showError("Please fill in all fields."); return;
        }

        // Demo login — replace with real UserController when User model is added
        if (email.equals("admin@test.com") && pass.equals("admin")) {
            SessionManager.login("admin-001", "Admin", email, true);
            navigate("AdminPanel");
        } else if (email.equals("test@test.com") && pass.equals("1234")) {
            SessionManager.login("user-001", "Traveler", email, false);
            navigate("Dashboard");
        } else {
            showError("Invalid credentials.  Try test@test.com / 1234");
        }
    }

    @FXML private void goToRegister() { navigate("Register"); }

    private void navigate(String s) {
        try { MainApp.navigateTo(s); }
        catch (Exception e) { showError("Navigation error: " + e.getMessage()); }
    }
    private void showError(String m) { errorLabel.setText(m); }
}
