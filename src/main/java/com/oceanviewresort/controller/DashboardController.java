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

    @FXML private void loadHome() { loadView("HomeView.fxml"); }
    @FXML private void loadBilling() { loadView("BillingView.fxml"); }
    @FXML private void loadReports() { loadView("ReportsView.fxml"); }
    @FXML private void loadAdminPanel() { loadView("AdminPanelView.fxml"); }

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

                // Create card
                VBox card = new VBox(10);
                card.getStyleClass().add("card");
                card.setPrefWidth(180);
                card.setStyle("-fx-background-color: #f1faee; -fx-padding: 15; -fx-background-radius: 10;");

                // Room type
                Label typeLabel = new Label(roomType);
                typeLabel.getStyleClass().add("card-title");
                typeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

                // Available count
                Label countLabel = new Label("Available: " + availableCount);
                countLabel.getStyleClass().add("card-text");

                // Beds
                Label bedLabel = new Label("Beds: " + beds);
                bedLabel.getStyleClass().add("card-text");

                // Price
                Label priceLabel = new Label("Price: $" + price + " / night");
                priceLabel.getStyleClass().add("card-text");

                card.getChildren().addAll(typeLabel, countLabel, bedLabel, priceLabel);

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
            VBox card = new VBox(8);
            card.getStyleClass().add("card");
            card.setPrefWidth(180);
            card.setStyle("-fx-background-color: #fefae0; -fx-padding: 15; -fx-background-radius: 10;");

            Label codeLabel = new Label(discount.getCode());
            codeLabel.getStyleClass().add("card-title");
            codeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

            Label descLabel = new Label(discount.getDescription());
            descLabel.getStyleClass().add("card-text");

            Label percentLabel = new Label("Discount: " + discount.getPercentage() + "%");
            percentLabel.getStyleClass().add("card-text");

            Label validLabel = new Label("Valid: " + discount.getValidFrom() + " to " + discount.getValidTo());
            validLabel.getStyleClass().add("card-text");

            card.getChildren().addAll(codeLabel, descLabel, percentLabel, validLabel);

            availableDiscountPane.getChildren().add(card);
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
