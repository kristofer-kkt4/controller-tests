package com.example.ui.controllers;

import com.example.ui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {

    @FXML private TextField     nameField;
    @FXML private TextField     emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField     preferencesField;
    @FXML private Label         errorLabel;
    @FXML private Label         successLabel;

    @FXML
    private void handleRegister() {
        String name  = nameField.getText().trim();
        String email = emailField.getText().trim();
        String pass  = passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            showError("Please fill in all required fields."); return;
        }
        if (!email.contains("@")) { showError("Enter a valid email."); return; }
        if (pass.length() < 4)    { showError("Password must be at least 4 characters."); return; }

        errorLabel.setText("");
        successLabel.setText("✓  Account created! You can now sign in.");
    }

    @FXML private void goToLogin() {
        try { MainApp.navigateTo("Login"); }
        catch (Exception e) { showError("Navigation error."); }
    }

    private void showError(String m) { errorLabel.setText(m); successLabel.setText(""); }
}
