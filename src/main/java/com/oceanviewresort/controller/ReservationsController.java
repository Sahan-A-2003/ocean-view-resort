package com.oceanviewresort.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ReservationsController {

    @FXML
    private TableView reservationTable;

    @FXML
    private TextField searchField;

    @FXML
    public void initialize() {

        // Later we load reservations from database
        System.out.println("Reservations page loaded");

    }

    @FXML
    private void handleSearch() {
        System.out.println("Searching: " + searchField.getText());
    }

    @FXML
    private void handleAddReservation() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/ReservationFormView.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Reservation");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}