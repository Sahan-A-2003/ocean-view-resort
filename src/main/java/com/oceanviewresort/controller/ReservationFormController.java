package com.oceanviewresort.controller;

import com.oceanviewresort.config.DBConnection;
import com.oceanviewresort.model.Reservation;
import com.oceanviewresort.service.EmailService;
import com.oceanviewresort.service.ReservationService;
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
    @FXML
    private TextField guestEmailField;
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
            if (guestNameField.getText().isEmpty() ||
                    guestEmailField.getText().isEmpty() ||
                    contactField.getText().isEmpty() ||
                    roomTypeBox.getValue() == null ||
                    checkInPicker.getValue() == null ||
                    checkOutPicker.getValue() == null ||
                    roomIdBox.getValue() == null) {

                messageLabel.setText("Please fill all required fields!");
                return;
            }

            Reservation reservation = new Reservation();

            reservation.setUserID(SessionManager.getInstance().getUserId());
            reservation.setRoomID(roomIdBox.getValue());
            reservation.setGuestName(guestNameField.getText());
            reservation.setGuestEmail(guestEmailField.getText());
            reservation.setAddress(addressField.getText());
            reservation.setContact(contactField.getText());
            reservation.setRoomType(roomTypeBox.getValue());
            reservation.setCheckInDate(checkInPicker.getValue());
            reservation.setCheckOutDate(checkOutPicker.getValue());
            reservation.setNumberOfRooms(roomsSpinner.getValue());
            reservation.setNumberOfGuests(guestsSpinner.getValue());

            // ✅ USE SERVICE INSTEAD OF DAO
            ReservationService service = new ReservationService();

            // 🔥 REGISTER EMAIL SERVICE
            service.registerObserver(new EmailService());

            boolean success = service.createReservation(reservation);

            if (success) {
                messageLabel.setText("Reservation saved successfully!");
                Stage stage = (Stage) guestNameField.getScene().getWindow();
                stage.close();
            } else {
                messageLabel.setText("Not enough rooms available!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error saving reservation!");
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