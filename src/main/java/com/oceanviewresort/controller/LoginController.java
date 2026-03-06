package com.oceanviewresort.controller;

import com.oceanviewresort.model.User;
import com.oceanviewresort.service.impl.AuthServiceImpl;
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

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter username and password!");
            return;
        }

        try {
            AuthServiceImpl authService = new AuthServiceImpl();
            User user = authService.login(username, password);

            if (user == null) {
                messageLabel.setText("Invalid username or password!");
                return;
            }

            // Store session
            SessionManager.getInstance()
                    .setUser(user.getUserID(), user.getUsername(), user.getRole());

            // Redirect based on role
            if (user.getRole().equalsIgnoreCase("ADMIN")) {
                openDashboard("/view/DashboardView.fxml",
                        user.getUsername(),
                        user.getRole());
            } else {
                openDashboard("/view/UserDashboard.fxml",
                        user.getUsername(),
                        user.getRole());
            }

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Database error!");
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

            stage.setMaximized(false);
            stage.setWidth(1200);
            stage.setHeight(700);

            stage.setScene(new Scene(root));
            stage.setTitle(role.equals("ADMIN") ? "Admin Dashboard" : "User Dashboard");

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Failed to load dashboard!");
        }
    }
}