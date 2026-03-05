package com.oceanviewresort.controller;

import com.oceanviewresort.config.DBConnection;
import com.oceanviewresort.util.SessionManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class ReservationFormController {

    @FXML private TextField guestNameField;
    @FXML private TextField addressField;
    @FXML private TextField contactField;
    @FXML private ComboBox<String> roomTypeBox;
    @FXML private DatePicker checkInPicker;
    @FXML private DatePicker checkOutPicker;
    @FXML private Spinner<Integer> roomsSpinner;
    @FXML private ComboBox<Integer> roomIdBox;
    @FXML private Spinner<Integer> guestsSpinner;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        roomTypeBox.getItems().addAll("Standard", "Deluxe", "Suite");

        // Add listener to roomTypeBox
        roomTypeBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadAvailableRoomIds(newVal);
            }
        });

        roomsSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));

        guestsSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));

    }

    private void loadRoomTypes() {
        ObservableList<String> roomTypes = FXCollections.observableArrayList();

        String sql = "SELECT DISTINCT roomType FROM Room";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                roomTypes.add(rs.getString("roomType"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        roomTypeBox.setItems(roomTypes);
    }

    private void loadAvailableRoomIds(String roomType) {
        ObservableList<Integer> roomIds = FXCollections.observableArrayList();

        String sql = "SELECT roomID FROM Room WHERE roomType = ? AND status = 'Available'";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, roomType);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    roomIds.add(rs.getInt("roomID")); // <-- fetch roomID, not roomNumber
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        roomIdBox.setItems(roomIds);
        roomIdBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleSaveReservation() {
        try {
            // 1️⃣ Validate required fields
            if (guestNameField.getText().isEmpty() ||
                    contactField.getText().isEmpty() ||
                    roomTypeBox.getValue() == null ||
                    checkInPicker.getValue() == null ||
                    checkOutPicker.getValue() == null ||
                    roomIdBox.getValue() == null) {

                messageLabel.setText("Please fill all required fields!");
                return;
            }

            // 2️⃣ Parse numeric fields safely
            Integer roomID = roomIdBox.getValue();
            if (roomID == null) {
                messageLabel.setText("Please select a room!");
                return;
            }
            int numberOfRooms = roomsSpinner.getValue();
            int numberOfGuests = guestsSpinner.getValue();
            int userID = SessionManager.getInstance().getUserId();

            LocalDate checkIn = checkInPicker.getValue();
            LocalDate checkOut = checkOutPicker.getValue();

            String guestName = guestNameField.getText();
            String address = addressField.getText();
            String contact = contactField.getText();
            String roomType = roomTypeBox.getValue();

            // 3️⃣ Insert into database
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = """
                    INSERT INTO reservation
                    (userID, roomID, guestName, address, contact, roomType,
                     checkInDate, checkOutDate, numberOfRooms, numberOfGuests, status)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userID);
                stmt.setInt(2, roomID);
                stmt.setString(3, guestName);
                stmt.setString(4, address);
                stmt.setString(5, contact);
                stmt.setString(6, roomType);
                stmt.setDate(7, java.sql.Date.valueOf(checkIn));
                stmt.setDate(8, java.sql.Date.valueOf(checkOut));
                stmt.setInt(9, numberOfRooms);
                stmt.setInt(10, numberOfGuests);
                stmt.setString(11, "Booked");

                stmt.executeUpdate();

                String updateRoomSql = "UPDATE Room SET status = 'Booked' WHERE roomID = ?";
                try (PreparedStatement stmt2 = conn.prepareStatement(updateRoomSql)) {
                    stmt2.setInt(1, roomID);
                    stmt2.executeUpdate();
                }
            }


            messageLabel.setText("Reservation saved successfully!");

            Stage stage = (Stage) guestNameField.getScene().getWindow();
            stage.close();

            System.out.println("Reservation Saved Successfully!");

            // Optional: Clear form
            clearForm();

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error saving reservation! Check console.");
        }
    }

    private void clearForm() {
        guestNameField.clear();
        addressField.clear();
        contactField.clear();
        roomTypeBox.setValue(null);
        checkInPicker.setValue(null);
        checkOutPicker.setValue(null);
        roomsSpinner.getValueFactory().setValue(1);
        guestsSpinner.getValueFactory().setValue(1);
        roomIdBox.getSelectionModel().clearSelection();
    }
}