package com.oceanviewresort.controller;

import com.oceanviewresort.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Simulate authentication (replace with real DB auth)
        String role;
        if (username.equals("admin")) {
            role = "ADMIN";
        } else if (username.equals("user")) {
            role = "USER";
        } else {
            messageLabel.setText("Invalid credentials!");
            return;
        }

        // Store user session
        SessionManager.getInstance().setUser(username, role);

        // Redirect based on role
        if (role.equals("ADMIN")) {
            openDashboard("/view/Dashboard.fxml", username, role);
        } else if (role.equals("USER")) {
            openDashboard("/view/UserDashboard.fxml", username, role);
        }
    }

    /**
     * Generic method to open dashboard FXML and pass username/role
     */
    private void openDashboard(String fxmlPath, String username, String role) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Get controller and pass user info
            Object controller = loader.getController();
            if (controller instanceof DashboardController && role.equals("ADMIN")) {
                ((DashboardController) controller).setUser(username);
            } else if (controller instanceof UserDashboardController && role.equals("USER")) {
                ((UserDashboardController) controller).setUser(username);
            }

            // Set scene
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(role.equals("ADMIN") ? "Admin Dashboard" : "User Dashboard");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Failed to load dashboard!");
        }
    }
}