package com.oceanviewresort.controller;

import com.oceanviewresort.config.DBConnection;
import com.oceanviewresort.dao.DiscountDAO;
import com.oceanviewresort.dao.impl.DiscountDAOImpl;
import com.oceanviewresort.model.Discount;
import com.oceanviewresort.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class DashboardController {

    @FXML private Label userRoleLabel;
    @FXML private StackPane contentArea;
    @FXML private FlowPane availableRoomsPane;
    @FXML private FlowPane availableDiscountPane;
    @FXML private BorderPane mainBorderPane;

    @FXML
    public void initialize() {

        String role = SessionManager.getInstance().getRole();
        userRoleLabel.setText("Role: " + role);

        loadAvailableRooms();

        loadAvailableDiscounts();
    }

    private void loadView(String fxml) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/view/" + fxml));
            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public void setUser(String username) {
        String role = SessionManager.getInstance().getRole();
        userRoleLabel.setText("Welcome, " + username + " | Role: " + role);
    }

    private void loadAvailableRooms() {
        if (availableRoomsPane == null) return;

        availableRoomsPane.getChildren().clear();

        // Group by room type to count available rooms of each type
        String sql = "SELECT roomType, COUNT(*) AS availableCount, bedCount, pricePerNight " +
                "FROM Room WHERE status = 'Available' " +
                "GROUP BY roomType, bedCount, pricePerNight";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String roomType = rs.getString("roomType");
                int availableCount = rs.getInt("availableCount");
                int beds = rs.getInt("bedCount");
                double price = rs.getDouble("pricePerNight");

                VBox card = new VBox(12);
                card.setPrefWidth(220);
                card.setPrefHeight(150);

                // Base clean style
                card.setStyle(
                        "-fx-background-color: white;" +
                                "-fx-padding: 18;" +
                                "-fx-background-radius: 14;" +
                                "-fx-border-radius: 14;" +
                                "-fx-border-color: #e6e6e6;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 10,0,0,4);"
                );

                // 🔹 Room Type (Title)
                Label typeLabel = new Label(roomType);
                typeLabel.setStyle(
                        "-fx-font-size: 17px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-text-fill: #2c3e50;"
                );

                // 🔹 Availability (important → colored)
                Label countLabel = new Label("Available: " + availableCount);
                countLabel.setStyle(
                        "-fx-font-size: 13px;" +
                                "-fx-text-fill: #27ae60;"   // green = available
                );

                // 🔹 Beds
                Label bedLabel = new Label("Beds: " + beds);
                bedLabel.setStyle(
                        "-fx-font-size: 13px;" +
                                "-fx-text-fill: #7f8c8d;"
                );

                // 🔹 Price (highlighted)
                Label priceLabel = new Label("$" + price + " / night");
                priceLabel.setStyle(
                        "-fx-font-size: 15px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-text-fill: #2980b9;"
                );

                card.getChildren().addAll(typeLabel, countLabel, bedLabel, priceLabel);

                // 🔥 HOVER EFFECT (premium feel)
                card.setOnMouseEntered(e -> {
                    card.setStyle(
                            "-fx-background-color: #f9fbfd;" +
                                    "-fx-padding: 18;" +
                                    "-fx-background-radius: 14;" +
                                    "-fx-border-radius: 14;" +
                                    "-fx-border-color: #3498db;" +
                                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20,0,0,6);" +
                                    "-fx-scale-x: 1.04;" +
                                    "-fx-scale-y: 1.04;"
                    );
                });

                card.setOnMouseExited(e -> {
                    card.setStyle(
                            "-fx-background-color: white;" +
                                    "-fx-padding: 18;" +
                                    "-fx-background-radius: 14;" +
                                    "-fx-border-radius: 14;" +
                                    "-fx-border-color: #e6e6e6;" +
                                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 10,0,0,4);" +
                                    "-fx-scale-x: 1;" +
                                    "-fx-scale-y: 1;"
                    );
                });

                availableRoomsPane.getChildren().add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAvailableDiscounts() {
        if (availableDiscountPane == null) return;

        availableDiscountPane.getChildren().clear();

        DiscountDAO discountDAO = new DiscountDAOImpl();
        List<Discount> discounts = discountDAO.getActiveDiscounts();

        for (Discount discount : discounts) {

            VBox card = new VBox(10);
            card.setPrefWidth(200);
            card.setPrefHeight(140);

            // Base style (clean + modern)
            card.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-padding: 15;" +
                            "-fx-background-radius: 12;" +
                            "-fx-border-radius: 12;" +
                            "-fx-border-color: #e0e0e0;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 10,0,0,4);"
            );

            // Title (Discount Code)
            Label codeLabel = new Label(discount.getCode());
            codeLabel.setStyle(
                    "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #2c3e50;"
            );

            // Description
            Label descLabel = new Label(discount.getDescription());
            descLabel.setStyle(
                    "-fx-font-size: 12px;" +
                            "-fx-text-fill: #7f8c8d;"
            );
            descLabel.setWrapText(true);

            // Percentage (highlighted)
            Label percentLabel = new Label(discount.getPercentage() + "% OFF");
            percentLabel.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #27ae60;"
            );

            // Valid dates
            Label validLabel = new Label(
                    discount.getValidFrom() + " → " + discount.getValidTo()
            );
            validLabel.setStyle(
                    "-fx-font-size: 11px;" +
                            "-fx-text-fill: #95a5a6;"
            );

            card.getChildren().addAll(codeLabel, descLabel, percentLabel, validLabel);

            // HOVER EFFECT
            card.setOnMouseEntered(e -> {
                card.setStyle(
                        "-fx-background-color: #f9fbfd;" +
                                "-fx-padding: 15;" +
                                "-fx-background-radius: 12;" +
                                "-fx-border-radius: 12;" +
                                "-fx-border-color: #3498db;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20,0,0,6);" +
                                "-fx-scale-x: 1.03;" +
                                "-fx-scale-y: 1.03;"
                );
            });

            card.setOnMouseExited(e -> {
                card.setStyle(
                        "-fx-background-color: white;" +
                                "-fx-padding: 15;" +
                                "-fx-background-radius: 12;" +
                                "-fx-border-radius: 12;" +
                                "-fx-border-color: #e0e0e0;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 10,0,0,4);" +
                                "-fx-scale-x: 1;" +
                                "-fx-scale-y: 1;"
                );
            });

            availableDiscountPane.getChildren().add(card);
        }
    }

    @FXML
    private void loadBilling() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BillingView.fxml"));
            Parent  billingContent = loader.load();
            mainBorderPane.setCenter(billingContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadHome() {
        try {
            // Load your default Dashboard center content FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashboardView.fxml"));
            Parent  homeContent = loader.load();

            mainBorderPane.setCenter(homeContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadHelp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HelperView.fxml"));
            Parent helpContent = loader.load();
            mainBorderPane.setCenter(helpContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadAdminPanel() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminPanelView.fxml"));
            Parent view = loader.load();

            mainBorderPane.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadReservations() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/ReservationsView.fxml"));

            Parent view = loader.load();

            mainBorderPane.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
