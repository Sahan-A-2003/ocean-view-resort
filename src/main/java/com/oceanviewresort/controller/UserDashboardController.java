package com.oceanviewresort.controller;

import com.oceanviewresort.config.DBConnection;
import com.oceanviewresort.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDashboardController {

    @FXML private Label userRoleLabel;
    @FXML private StackPane contentArea;
    @FXML private FlowPane availableRoomsPane;

    @FXML
    public void initialize() {
        // Set user role
        String role = SessionManager.getInstance().getRole();
        userRoleLabel.setText(role);
    }

    public void setUser(String username) {
        String role = SessionManager.getInstance().getRole();
        userRoleLabel.setText("Welcome, " + username + " | Role: " + role);
    }

    // ------------------- LOAD VIEWS -------------------
    private void loadView(String fxml) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/view/" + fxml));
            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private void loadHome() {
        loadAvailableRooms();       // load available rooms
        loadAvailableDiscounts();   // load discounts under rooms
    }

    @FXML private void loadReservations() {
        loadView("ReservationTableView.fxml");
    }

    @FXML private void loadBilling() {
        loadView("BillingView.fxml");
    }

    @FXML private void loadHelp() {
        loadView("HelpView.fxml");
    }

    // ------------------- LOGOUT -------------------
    @FXML
    private void logout(javafx.event.ActionEvent event) {
        try {
            // Clear session
            SessionManager.getInstance().clearSession();

            // Load login view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            Parent loginRoot = loader.load();

            // Get current stage from the button that triggered the event
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();
            javafx.stage.Stage stage = (javafx.stage.Stage) source.getScene().getWindow();

            stage.getScene().setRoot(loginRoot);

            stage.setWidth(400);
            stage.setHeight(300);
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------- LOAD AVAILABLE ROOMS -------------------
    private void loadAvailableRooms() {
        if (availableRoomsPane == null) return;

        availableRoomsPane.getChildren().clear();

        String sql = "SELECT * FROM Room WHERE status = 'Available'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String roomType = rs.getString("roomType");
                double price = rs.getDouble("pricePerNight");
                int beds = rs.getInt("bedCount");
                int roomNumber = rs.getInt("roomNumber");

                VBox card = new VBox(8);
                card.getStyleClass().add("card");

                Label title = new Label(roomType + " (Room " + roomNumber + ")");
                title.getStyleClass().add("card-title");

                Label priceLabel = new Label("Price: $" + price + " / night");
                priceLabel.getStyleClass().add("card-text");

                Label bedLabel = new Label("Beds: " + beds);
                bedLabel.getStyleClass().add("card-text");

                card.getChildren().addAll(title, priceLabel, bedLabel);
                availableRoomsPane.getChildren().add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------- LOAD AVAILABLE DISCOUNTS -------------------
    private void loadAvailableDiscounts() {
        if (availableRoomsPane == null) return;

        String sql = "SELECT * FROM Billing WHERE discountAmount > 0";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                double discount = rs.getDouble("discountAmount");
                Label discountLabel = new Label("Available Discount: $" + discount);
                discountLabel.getStyleClass().add("card-text");
                availableRoomsPane.getChildren().add(discountLabel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}