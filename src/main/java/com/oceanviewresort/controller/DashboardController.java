package com.oceanviewresort.controller;

import com.oceanviewresort.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class DashboardController {

    @FXML private Label userRoleLabel;
    @FXML private StackPane contentArea;
    @FXML
    private Label welcomeLabel;

    @FXML
    public void initialize() {

        String role = SessionManager.getInstance().getRole();
        userRoleLabel.setText("Role: " + role);

        if (!role.equals("ADMIN")) {
            // Hide admin button if user
        }
    }

    private void loadView(String fxml) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/view/" + fxml));
            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void loadHome() { loadView("HomeView.fxml"); }
    @FXML private void loadReservations() { loadView("ReservationTableView.fxml"); }
    @FXML private void loadBilling() { loadView("BillingView.fxml"); }
    @FXML private void loadReports() { loadView("ReportsView.fxml"); }
    @FXML private void loadAdminPanel() { loadView("AdminPanelView.fxml"); }

    @FXML
    private void logout() {
        System.exit(0);
    }

    public void setUser(String username) {
        welcomeLabel.setText("Welcome, " + username + " (User)");
    }
}
